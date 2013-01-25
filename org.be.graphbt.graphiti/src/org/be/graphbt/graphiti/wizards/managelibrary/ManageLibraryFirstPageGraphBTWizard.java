package org.be.graphbt.graphiti.wizards.managelibrary;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TreeSet;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.validation.internal.util.Log;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import org.be.graphbt.model.graphbt.BEModel;
import org.be.graphbt.model.graphbt.Behavior;
import org.be.graphbt.model.graphbt.BehaviorType;
import org.be.graphbt.model.graphbt.Component;
import org.be.graphbt.graphiti.GraphBTUtil;
import org.be.graphbt.model.graphbt.Library;
import org.be.graphbt.model.graphbt.Requirement;
import org.be.graphbt.model.graphbt.StandardNode;
import org.be.graphbt.graphiti.wizards.createbehavior.CreateBehaviorGraphBTWizard;
import org.be.graphbt.graphiti.wizards.createcomponent.CreateComponentGraphBTWizard;
import org.be.graphbt.graphiti.editor.*;
/**
 * Class to define the contents of manage component wizard
 * @author GraphBT Team
 *
 */
public class ManageLibraryFirstPageGraphBTWizard extends WizardPage {

	private Composite container;
	private HashMap<Integer,String> map;
	private Diagram d;
	private Text filterText;
	private String selectedAdd;
	private BEModel model;
	private String selectedRemove;
	public ManageLibraryFirstPageGraphBTWizard(HashMap<Integer,String> map, Diagram d) {
		super("Manage Library Wizard");
		setTitle("Manage Library Wizard");
		setDescription("Manage library of the project.");
		this.map = map;
		this.d=d;
		model = GraphBTUtil.getBEModel(d);
	}

	@Override
	public void createControl(Composite parent) {
		IWorkbenchPage page=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        final DiagramEditor ds;
        if(page.getActiveEditor() instanceof DiagramEditor) {
        	 ds = (DiagramEditor)page.getActiveEditor();	
        }
        else
        {
        	ds = ((MultiPageEditor)page.getActiveEditor()).getDiagramEditor();
        }
        
		
		container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);
		GridData gridData;
		final ArrayList<Library> availableLib = new ArrayList<Library>();
		final ArrayList<String> usedLib = new ArrayList<String>();
		Bundle bundle = Platform.getBundle("org.be.graphbt.graphiti.common");
		Enumeration<String> listLib = bundle.getEntryPaths("/files/lib");
		
		
		/*
		 * Filter
		 */
		
		final Label filterLabel = new Label(container, SWT.NULL);
		filterLabel.setText("Filter:");
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		filterLabel.setLayoutData(gridData);
		
		filterText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gridData = new GridData();
		gridData.widthHint = 200;
		gridData.horizontalSpan = 3;
		filterText.setLayoutData(gridData);		
										
		
		/*
		 * Available Library
		 */
		final List listAvailable = new List(container, SWT.BORDER | SWT.V_SCROLL);
		gridData =
				new GridData(
						GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		gridData.horizontalSpan = 1;
		gridData.verticalSpan = 3;
		gridData.heightHint = 200;
		gridData.widthHint = 200;
		//gridData.grabExcessVerticalSpace = true;		
		listAvailable.setLayoutData(gridData);
		while(listLib.hasMoreElements()) {
			String po = listLib.nextElement();
			if(!po.endsWith(".zip")) {
				continue;
			}
			Library l = GraphBTUtil.getBEFactory().createLibrary();
			l.setName(po.substring(po.lastIndexOf("/")+1));
			l.setLocation(po);
			availableLib.add(l);
			listAvailable.add(l.getName());
		}

		listAvailable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if(listAvailable.getSelectionIndex()>=0&&listAvailable.getSelectionIndex()<listAvailable.getItemCount()) {
					selectedAdd = listAvailable.getItem(listAvailable.getSelectionIndex());
				}
			}
		});
		
		/*
		 * Right Button: ->
		 */
		Button rightButton = new Button(container, SWT.NULL);
		gridData = new GridData();
		gridData.widthHint = 35;
		
		rightButton.setLayoutData(gridData);
		rightButton.setText("->");
		
		
		/*
		 * Selected Library
		 */
		final List listSelected = new List(container, SWT.BORDER | SWT.V_SCROLL);
		gridData =
				new GridData(
						GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		gridData.horizontalSpan = 1;
		gridData.verticalSpan = 3;
		gridData.heightHint = 200;
		gridData.widthHint = 200;
		//gridData.grabExcessVerticalSpace = true;
		
		listSelected.setLayoutData(gridData);
		listSelected.removeAll();
		for(Library r:model.getLibraries().getImport()) {
			listSelected.add(r.getName());
		}
		listSelected.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				selectedRemove = listSelected.getItem(listSelected.getSelectionIndex());
			}
		});
		
		/*
		 * Left Button: <-			
		 */
		
		Button leftButton = new Button(container, SWT.NULL);
		gridData = new GridData();
		gridData.widthHint = 35;
		//gridData.grabExcessHorizontalSpace = true;	
		leftButton.setLayoutData(gridData);
		leftButton.setText("<-");
		
		
		/*
		 * Action List
		 */		

		filterText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String text = ((Text)e.widget).getText();
				listAvailable.removeAll();
				for(int i = 0; i < availableLib.size(); i++) {
					if(availableLib.get(i).getName().contains(text)) {
						listAvailable.add(availableLib.get(i).getName());
					}
				}
			}
	    });
		
		leftButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if(selectedRemove==null) {
					return;
				}
				
				for(int i = 0; i < model.getLibraries().getImport().size(); i++) {
					if(selectedRemove.equals(model.getLibraries().getImport().get(i).getName())) {
						listSelected.remove(selectedRemove);
						final Library sel = model.getLibraries().getImport().get(i);
						final Command cmd = new RecordingCommand(ds.getEditingDomain(), "Remove Import") {
							protected void doExecute() {
								model.getLibraries().getImport().remove(sel);
						    }
						};
						ds.getEditingDomain().getCommandStack().execute(cmd);
						break;
					}
				}
			}
		});
		rightButton.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("restriction")
			public void widgetSelected(SelectionEvent event) {
				if(selectedAdd==null) {
					return;
				}
				for(int i = 0; i < availableLib.size(); i++) {
					if(selectedAdd.equals(availableLib.get(i).getName())&&
							!model.getLibraries().getImport().contains(availableLib.get(i))) {
						Log.info(1, "ManageLibrary, new import is inserted");
						listSelected.add(selectedAdd);
						final Library sel = availableLib.get(i);
						final Command cmd = new RecordingCommand(ds.getEditingDomain(), "Add Import") {
							protected void doExecute() {
								model.getLibraries().getImport().add(sel);
						    }
						};
						ds.getEditingDomain().getCommandStack().execute(cmd);
						break;
						
					}
				}
			}
		});
				
		setControl(container);
	}
	
}
