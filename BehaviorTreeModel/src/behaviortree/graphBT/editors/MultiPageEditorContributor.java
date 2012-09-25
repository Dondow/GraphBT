package behaviortree.graphBT.editors;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import btdebuggertool.commandHandler.*;
import behaviortree.saltranslator.bt2sal.*;
//import org.be.textbe.bt.textbt.presentation.TextbtEditor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.osgi.framework.Bundle;

import behaviortree.BEModel;
import behaviortree.GraphBTUtil;
import behaviortree.StandardNode;
import behaviortree.graphBT.wizards.createcomponent.CreateComponentGraphBTWizard;
import behaviortree.graphBT.wizards.managecomponents.ManageComponentsGraphBTWizard;
import behaviortree.graphBT.wizards.managelibrary.ManageLibraryGraphBTWizard;
import behaviortree.graphBT.wizards.managerequirements.ManageRequirementsGraphBTWizard;
import behaviortree.graphBT.wizards.verifymodel.VerifyModelGraphBTWizard;

/**
 * Manages the installation/deinstallation of global actions for multi-page editors.
 * Responsible for the redirection of global actions to the active editor.
 * Multi-page contributor replaces the contributors for the individual editors in the multi-page editor.
 */
public class MultiPageEditorContributor extends MultiPageEditorActionBarContributor {
	private IEditorPart activeEditorPart;
	private Action generateBTCode;
	private Action addNewComponent;
	private Action manageComponents;
	private Action manageRequirements;
	private Action manageLibrary;
	private Action validateBT;
	private Action debugBT;
	private Action generateJavaCode;
	private Action verifyModel;
	private Action correctLayout;
	private Action extractFromBTFile;
	private IFile btIFile;
	private Action clearDiagram;
	private Action generateSALCode;
	private Action runCode;
	/**
	 * Creates a multi-page contributor.
	 */
	public MultiPageEditorContributor() {
		super();
		createActions();
	}
	/**
	 * Returns the action registed with the given text editor.
	 * @return IAction or null if editor is null.
	 */
	protected IAction getAction(ITextEditor editor, String actionID) {
		return (editor == null ? null : editor.getAction(actionID));
	}
	/* (non-JavaDoc)
	 * Method declared in AbstractMultiPageEditorActionBarContributor.
	 */

	public IEditorPart getActivePage()
	{
		return activeEditorPart;
	}
	public void setActivePage(IEditorPart part) {
		if (activeEditorPart == part)
			return;

		/*if(activeEditorPart instanceof DiagramEditor && part instanceof TextbtEditor)
		{

		}*/
		activeEditorPart = part;

		IActionBars actionBars = getActionBars();
		if (actionBars != null) {

			ITextEditor editor = (part instanceof ITextEditor) ? (ITextEditor) part : null;

			actionBars.setGlobalActionHandler(
					ActionFactory.DELETE.getId(),
					getAction(editor, ITextEditorActionConstants.DELETE));
			actionBars.setGlobalActionHandler(
					ActionFactory.UNDO.getId(),
					getAction(editor, ITextEditorActionConstants.UNDO));
			actionBars.setGlobalActionHandler(
					ActionFactory.REDO.getId(),
					getAction(editor, ITextEditorActionConstants.REDO));
			actionBars.setGlobalActionHandler(
					ActionFactory.CUT.getId(),
					getAction(editor, ITextEditorActionConstants.CUT));
			actionBars.setGlobalActionHandler(
					ActionFactory.COPY.getId(),
					getAction(editor, ITextEditorActionConstants.COPY));
			actionBars.setGlobalActionHandler(
					ActionFactory.PASTE.getId(),
					getAction(editor, ITextEditorActionConstants.PASTE));
			actionBars.setGlobalActionHandler(
					ActionFactory.SELECT_ALL.getId(),
					getAction(editor, ITextEditorActionConstants.SELECT_ALL));
			actionBars.setGlobalActionHandler(
					ActionFactory.FIND.getId(),
					getAction(editor, ITextEditorActionConstants.FIND));
			actionBars.setGlobalActionHandler(
					IDEActionFactory.BOOKMARK.getId(),
					getAction(editor, IDEActionFactory.BOOKMARK.getId()));
			actionBars.updateActionBars();
		}
	}

	private void createActions() {

		generateBTCode = new Action() {
			public void run(){
				if(activeEditorPart instanceof DiagramEditor)
				{
					Diagram d = ((DiagramEditor)activeEditorPart).getDiagramTypeProvider().getDiagram();
					if(GraphBTUtil.isValid(d)>0)
					{
						MessageDialog.openError(null, "BT generation error", "The model is not valid, validate the model first to check error");
						return;
					}
					String content = GraphBTUtil.getBTText(d);
					URI uri = d.eResource().getURI();
					uri = uri.trimFragment();
					uri = uri.trimFileExtension();
					uri = uri.appendFileExtension("bt");
					final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

					IResource file = workspaceRoot.findMember(uri.toPlatformString(true));
					{
						Path path = new Path(uri.toPlatformString(true));
						btIFile = workspaceRoot.getFile(path);
						InputStream in = new ByteArrayInputStream(content.getBytes());
						try {
							if (file == null || !file.exists()) 
							{
								btIFile.create(in,false,null);
							}	
							else
							{
								btIFile.setContents(in, false, false, null);
							}	
						} catch (CoreException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}}
			// Get the currently selected file from the editor
		};	

		generateBTCode.setText("Generate BT Code");
		generateBTCode.setToolTipText("Generate the corresponding BT Code of the BE model");
		generateBTCode.setImageDescriptor(getImageDescriptor("icons/generateBTCode.gif"));

		addNewComponent = new Action() {
			public void run() {
				//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "Sample Action Executed");
				if(activeEditorPart instanceof DiagramEditor)
				{
					DiagramEditor de = (DiagramEditor)activeEditorPart;
					// Get the currently selected file from the editor
					Diagram d = de.getDiagramTypeProvider().getDiagram();
					HashMap <Integer,String> map = new HashMap<Integer, String>();
					//String ketemu="";

					WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().
							getActiveWorkbenchWindow().getShell(),
							new CreateComponentGraphBTWizard(map, d));
					if(wizardDialog.open() != Window.OK)
					{
						return;
					}
				}
			}
		};

		addNewComponent.setText("Add new Component");
		addNewComponent.setToolTipText("Add new component to the model");
		addNewComponent.setImageDescriptor(getImageDescriptor("icons/newComponent.gif"));

		manageComponents = new Action(){
			public void run() {
				//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "Sample Action Executed");
				if(activeEditorPart instanceof DiagramEditor)
				{
					DiagramEditor de = (DiagramEditor)activeEditorPart;
					// Get the currently selected file from the editor
					Diagram d = de.getDiagramTypeProvider().getDiagram();
					//String ketemu="";
					if(d!=null)
					{
						WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().
								getActiveWorkbenchWindow().getShell(),
								new ManageComponentsGraphBTWizard(d));
						if(wizardDialog.open() != Window.OK)
						{
							return;
						}
					}
				}
			}
		};

		manageComponents.setText("Manage Components");
		manageComponents.setToolTipText("Manage components of the model");
		manageComponents.setImageDescriptor(getImageDescriptor("icons/component.gif"));
		
		manageRequirements = new Action(){
			public void run() {
				//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "Sample Action Executed");
				if(activeEditorPart instanceof DiagramEditor)
				{
					DiagramEditor de = (DiagramEditor)activeEditorPart;
					// Get the currently selected file from the editor
					Diagram d = de.getDiagramTypeProvider().getDiagram();
					HashMap <Integer,String> map = new HashMap<Integer, String>();
					//String ketemu="";
					if(d!=null)
					{
						WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().
								getActiveWorkbenchWindow().getShell(),
								new ManageRequirementsGraphBTWizard(map, d));
						if(wizardDialog.open() != Window.OK)
						{
							return;
						}
						GraphBTUtil.getBEModel(d);
					}
				}
			}
		};
		manageRequirements.setText("Manage Requirements");
		manageRequirements.setToolTipText("Manage Requirements of The Model");
		manageRequirements.setImageDescriptor(getImageDescriptor("icons/requirement.gif"));

		manageLibrary = new Action(){
			public void run() {
				//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "Sample Action Executed");
				if(activeEditorPart instanceof DiagramEditor)
				{
					DiagramEditor de = (DiagramEditor)activeEditorPart;
					// Get the currently selected file from the editor
					Diagram d = de.getDiagramTypeProvider().getDiagram();
					HashMap <Integer,String> map = new HashMap<Integer, String>();
					//String ketemu="";
					if(d!=null)
					{
						WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().
								getActiveWorkbenchWindow().getShell(),
								new ManageLibraryGraphBTWizard(map, d));
						if(wizardDialog.open() != Window.OK)
						{
							return;
						}
						GraphBTUtil.getBEModel(d);
					}
				}
			}
		};

		manageLibrary.setText("Manage Library");
		manageLibrary.setToolTipText("Manage library");
		manageLibrary.setImageDescriptor(getImageDescriptor("icons/manageLibrary.gif"));
		
		verifyModel = new Action() {
			public void run() {
				//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "Sample Action Executed");
				if(activeEditorPart instanceof DiagramEditor)
				{
					DiagramEditor de = (DiagramEditor)activeEditorPart;
					// Get the currently selected file from the editor
					Diagram d = de.getDiagramTypeProvider().getDiagram();
					HashMap <Integer,String> map = new HashMap<Integer, String>();
					generateSALCode.run();
					if(d!=null)
					{
						WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().
								getActiveWorkbenchWindow().getShell(),
								new VerifyModelGraphBTWizard(map, d));
						if(wizardDialog.open() != Window.OK)
						{
							return;
						}
						GraphBTUtil.getBEModel(d);
					}
					//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "path: " + path+"\n"+ketemu);
				}
			}
		};

		verifyModel.setText("Verify Model");
		verifyModel.setToolTipText("Verify model");
		verifyModel.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(IDE.SharedImages.IMG_OPEN_MARKER));
		
		validateBT = new Action() {
			public void run() {
				//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "Sample Action Executed");
				if(activeEditorPart instanceof DiagramEditor)
				{
					DiagramEditor de = (DiagramEditor)activeEditorPart;
					// Get the currently selected file from the editor
					Diagram d = de.getDiagramTypeProvider().getDiagram();
					
					int isValid = GraphBTUtil.isValid(d);
					
					if(isValid == 1)
					{
						MessageDialog.openError(null, "Error in validate BT", "The model should contain only one root!");
					}
					else if(isValid == 2) {
						MessageDialog.openError(null, "Error in validate BT", "The model contain invalid reversion node!");
					}
					else
					{
						MessageDialog.openInformation(null, "Validation info", "The model is valid");
					}
				}
			}
		};
		validateBT.setText("Validate BT");
		validateBT.setToolTipText("Validate BT");
		validateBT.setImageDescriptor(getImageDescriptor("icons/validate.gif"));

		this.debugBT = new Action() {
			public void run() {
				//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "Sample Action Executed");
				if(activeEditorPart instanceof DiagramEditor)
				{
					Diagram d = ((DiagramEditor)activeEditorPart).getDiagramTypeProvider().getDiagram();
					if(GraphBTUtil.isValid(d)>0)
					{
						MessageDialog.openError(null, "Debug error", "The model is not valid, validate the model first to check error");
						return;
					}
					StartPointParseXML debugger = new StartPointParseXML ();
					generateBTCode.run(); //generate the bt code first
					debugger.showDebugger(btIFile, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
					//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "path: " + path+"\n"+ketemu);
				}
			}
		};
		debugBT.setText("BT Debugger");
		debugBT.setToolTipText("Debug and simulate the model");
		debugBT.setImageDescriptor(getImageDescriptor("icons/debug.gif"));
		
		this.generateJavaCode = new Action() {
			public void run() {
				//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "Sample Action Executed");
				if(activeEditorPart instanceof DiagramEditor)
				{
					Diagram d = ((DiagramEditor)activeEditorPart).getDiagramTypeProvider().getDiagram();
					if(GraphBTUtil.isValid(d)>0)
					{
						MessageDialog.openError(null, "Code generation error", "The model is not valid, validate the model first to check error");
						return;
					}
					codegenerator.commandHandler.StartPointParseXML debugger = new codegenerator.commandHandler.StartPointParseXML ();
					generateBTCode.run(); //generate the bt code first
					debugger.generateCodeFromBT(btIFile);
					//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "path: " + path+"\n"+ketemu);
				}
			}
		};
		generateJavaCode.setText("BT Code Generator");
		generateJavaCode.setToolTipText("Generate the Java Code");
		generateJavaCode.setImageDescriptor(getImageDescriptor("icons/generateCode.gif"));
		correctLayout = new Action() {
			public void run() {
				//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "Sample Action Executed");
				if(activeEditorPart instanceof DiagramEditor)
				{
					DiagramEditor de = (DiagramEditor)activeEditorPart;
					// Get the currently selected file from the editor
					Diagram d = de.getDiagramTypeProvider().getDiagram();
					GraphBTUtil.applyTreeLayout(d);
					
					//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "path: " + path+"\n"+ketemu);
				}
			}
		};
		correctLayout.setText("Apply Layout");
		correctLayout.setToolTipText("Apply layout to the graphical model");
		correctLayout.setImageDescriptor(getImageDescriptor("icons/layout.gif"));
		extractFromBTFile = new Action() {
			public void run() {
				//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "Sample Action Executed");
				if(activeEditorPart instanceof DiagramEditor)
				{
					String filePath = handleBrowse();
					File f = new File(filePath);
					IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
					
					IPath path = Path.fromOSString(URI.createFileURI(f.getAbsolutePath()).devicePath());
					
					
					IFile file = root.getFileForLocation(path);
					final DiagramEditor d = ((DiagramEditor)activeEditorPart);
					URI uri = d.getDiagramTypeProvider().getDiagram().eResource().getURI();
					uri.trimFragment();
					uri.trimFileExtension();
					uri.appendFileExtension("model");
					IResource res = root.findMember(uri.toPlatformString(true));
					
					if(filePath == null)
						return;
					
					if(file==null)
					{
						IPath copy = Path.fromPortableString("/"+res.getProject().getName()+"/rbt/"+f.getName());
						file = root.getFile(copy);
						try {
							file.create(new java.io.FileInputStream(f), true, null);
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (CoreException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					BEModel mod = GraphBTUtil.getBEModel(d.getDiagramTypeProvider().getDiagram());
					if(d.getDiagramTypeProvider().getDiagram().getChildren().size() > 0 || mod.getComponentList().getComponents().size() > 0 || mod.getRequirementList().getRequirements().size() > 0)
					{
						boolean overide=MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Extract confirmation", "Diagram is not empty, are you sure you want to overide current diagram");
						if(!overide)
						{
							return;
						}
					}
					if(filePath.endsWith(".bt"))
					{
						d.getEditingDomain().getCommandStack().execute(new RecordingCommand(d.getEditingDomain(),"clear diagram editor"){
							@Override
							protected void doExecute() {
								BEModel mod = GraphBTUtil.getBEModel(d.getDiagramTypeProvider().getDiagram());
								mod.setComponentList(GraphBTUtil.getBEFactory().createComponentList());
								mod.setRequirementList(GraphBTUtil.getBEFactory().createRequirementList());
								GraphBTUtil.reversionNode.clear();
								GraphBTUtil.errorReversionNode.clear();
								Diagram diag = d.getDiagramTypeProvider().getDiagram(); 
								diag.getChildren().clear();
								diag.getConnections().clear();
								diag.getAnchors().clear();
								diag.getPictogramLinks().clear();
								diag.getProperties().clear();
								URI uri = d.getDiagramTypeProvider().getDiagram().eResource().getURI();
								uri = uri.trimFragment();
								uri = uri.trimFileExtension();
								uri = uri.appendFileExtension("model");
								diag.eResource().getResourceSet().getResource(uri, true).getContents().clear();
							}
						});
						GraphBTUtil.generateFromBTFile(file, d);
					}
					else if(!filePath.endsWith(".bt"))
					{
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error reading file", "Only bt file is supported");
					}
					//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "path: " + path+"\n"+ketemu);
				}
			}
		};
		extractFromBTFile.setText("Extract BT File");
		extractFromBTFile.setToolTipText("Extract model from BT File");
		extractFromBTFile.setImageDescriptor(getImageDescriptor("icons/extract.gif"));
		clearDiagram = new Action() {
			public void run() {
				//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "Sample Action Executed");
				if(activeEditorPart instanceof DiagramEditor)
				{
					Diagram d = ((DiagramEditor)activeEditorPart).getDiagramTypeProvider().getDiagram();
					
					if(GraphBTUtil.getRoots(d.eResource().getResourceSet()).size() !=0 ){
						boolean overide=MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Clear confirmation", "Are you sure you want to clear the current diagram? Components and requirements will not be cleared too.");
						if(!overide)
							{
								return;
							}
							
						}
						clearDiagram((DiagramEditor)activeEditorPart);	
					}
					
				}
		};
		clearDiagram.setText("Clear editor");
		clearDiagram.setToolTipText("Clear the diagram editor");
		clearDiagram.setImageDescriptor(getImageDescriptor("icons/clear.gif"));
		
		generateSALCode = new Action() {
			public void run() {
				//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "Sample Action Executed");
				if(activeEditorPart instanceof DiagramEditor)
				{
					Diagram d = ((DiagramEditor)activeEditorPart).getDiagramTypeProvider().getDiagram();
					if(GraphBTUtil.isValid(d)>0)
					{
						MessageDialog.openError(null, "SAL generation error", "The model is not valid, validate the model first to check error");
						return;
					}
					new codegenerator.commandHandler.StartPointParseXML ();
					generateBTCode.run(); //generate the bt code first
					URI uri = d.eResource().getURI();
					uri = uri.trimFragment();
					uri = uri.trimFileExtension();
					uri = uri.appendFileExtension("sal");
					
					final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
					IFile file = (IFile) workspaceRoot.findMember(uri.toPlatformString(true));
					{
						Path path = new Path(uri.toPlatformString(true));
						file = workspaceRoot.getFile(path);
						Main.run(GraphBTUtil.getXMLFromBT(btIFile), file);
					}
					
					try {
						workspaceRoot.refreshLocal(IResource.DEPTH_ONE, null);
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "path: " + path+"\n"+ketemu);
				}
			}
		};
		generateSALCode.setText("BT to SAL Translator");
		generateSALCode.setToolTipText("Generate the SAL Code");
		generateSALCode.setImageDescriptor(getImageDescriptor("icons/generateSALCode.gif"));
		
		runCode = new Action() {
			public void run() {
				//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "Sample Action Executed");
				if(activeEditorPart instanceof DiagramEditor)
				{
					Diagram d = ((DiagramEditor)activeEditorPart).getDiagramTypeProvider().getDiagram();
					if(GraphBTUtil.isValid(d)>0)
					{
						MessageDialog.openError(null, "Run error", "The model is not valid, validate the model first to check error");
						return;
					}
				
					try {
						IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
						String homeDrivePath = root.getRawLocation().toOSString();
						String project =  d.eResource().getURI().toPlatformString(true);
						IResource res = root.findMember(project);
						URL url = codegenerator.helper.Helper.getURL("lib/absfrontend.jar");
						Runtime.getRuntime().exec(
							"cmd /c start cmd.exe /k \"c: && cd " 
							+ homeDrivePath+"&& java -cp "+res.getProject().getName()+"/src/;"+url.getPath()+" "+res.getProject().getName()+".Main");
					}catch (IOException e) {
						e.printStackTrace();
					}
					//MessageDialog.openInformation(null, "Graphiti Sample Sketch (Incubation)", "path: " + path+"\n"+ketemu);
				}
			}
		};
		runCode.setText("Run the code");
		runCode.setToolTipText("Run the generated Code");
		runCode.setImageDescriptor(getImageDescriptor("icons/run.gif"));
		
	}
	
	private String handleBrowse() {
		FileDialog dialog = new FileDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		dialog.setFilterExtensions(new String [] {"*.bt"});
		dialog.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toOSString());
		   
		String path = dialog.open();
		
		if(path!=null)
			return path;
		return "";
	}
	
	private ImageDescriptor getImageDescriptor(String imgPath)
	{
		Bundle bundle = Platform.getBundle("behaviortree.graphBT");
		IPath path = new Path(imgPath);

		URL fileURL = FileLocator.find(bundle, path, null);
		try {
			fileURL = FileLocator.resolve(fileURL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageData im = new ImageData(fileURL.getPath());
		ImageDescriptor imd = ImageDescriptor.createFromImageData(im);
		return imd;
	}
	public void contributeToMenu(IMenuManager manager) {
		IMenuManager menu = new MenuManager("Editor &Menu");
		manager.prependToGroup(IWorkbenchActionConstants.MB_ADDITIONS, menu);
		menu.add(clearDiagram);
		menu.add(generateBTCode);
		menu.add(addNewComponent);
		menu.add(manageComponents);
		menu.add(manageRequirements);
		menu.add(manageLibrary);
		menu.add(verifyModel);
		menu.add(validateBT);
		menu.add(debugBT);
		menu.add(generateJavaCode);
		menu.add(generateSALCode);
		menu.add(new Separator());
		menu.add(correctLayout);
		menu.add(extractFromBTFile);

		menu.add(runCode);
	}
	public void contributeToToolBar(IToolBarManager manager) {
		manager.add(new Separator());
		manager.add(clearDiagram);
		manager.add(extractFromBTFile);
		manager.add(addNewComponent);
		manager.add(manageComponents);
		manager.add(manageRequirements);
		manager.add(manageLibrary);
		manager.add(new Separator());
		manager.add(validateBT);
		manager.add(verifyModel);
		manager.add(debugBT);
		manager.add(generateBTCode);
		manager.add(generateJavaCode);
		manager.add(generateSALCode);
		manager.add(new Separator());
		manager.add(correctLayout);
		manager.add(runCode);
		manager.add(new Separator());
		//manager.add(getAction(GEFActionConstants.ZOOM_OUT));
		//manager.add(getAction(GEFActionConstants.ZOOM_IN));
		
		ZoomComboContributionItem zoomCombo = new ZoomComboContributionItem(getPage());
		manager.add(zoomCombo);
	}
	
	private void clearDiagram(final DiagramEditor d)
	{
		d.getEditingDomain().getCommandStack().execute(new RecordingCommand(d.getEditingDomain(),"clear diagram editor"){
			@Override
			protected void doExecute() {
				Diagram diag = d.getDiagramTypeProvider().getDiagram(); 
				diag.getChildren().clear();
				diag.getConnections().clear();
				diag.getAnchors().clear();
				diag.getPictogramLinks().clear();
				diag.getProperties().clear();
				URI uri = d.getDiagramTypeProvider().getDiagram().eResource().getURI();
				uri = uri.trimFragment();
				uri = uri.trimFileExtension();
				uri = uri.appendFileExtension("model");
				Resource res = GraphBTUtil.getResource(diag.eResource().getResourceSet(), uri);
				 
				List<EObject> rs = new ArrayList<EObject>(res.getContents());
				for(int i = 0; i < rs.size(); i++)
				{
					EObject ob = rs.get(i);
					if(ob instanceof StandardNode)
					{
						res.getContents().remove(ob);
					}
				}
				GraphBTUtil.errorReversionNode.clear();
				GraphBTUtil.reversionNode.clear();
			}
		});
	}
	
}