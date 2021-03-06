package org.be.graphbt.graphiti.wizards.createbehavior;

import java.util.HashMap;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import org.be.graphbt.model.graphbt.Behavior;
import org.be.graphbt.model.graphbt.BehaviorType;
import org.be.graphbt.model.graphbt.Component;
import org.be.graphbt.graphiti.GraphBTUtil;
import org.be.graphbt.graphiti.editor.*;
/**
 * Class for mananing the create behavior wizard
 * @author GraphBT Team
 *
 */
public class CreateBehaviorGraphBTWizard extends Wizard {

	protected CreateBehaviorFirstPageGraphBTWizard one;
	protected HashMap<Integer,String> map;
	protected Component c;
	private Behavior b = null;
	public CreateBehaviorGraphBTWizard(Component c) {
		super();
		setNeedsProgressMonitor(true);
		this.map = new HashMap<Integer,String>();
		this.c = c;
	}
	public CreateBehaviorGraphBTWizard(Component c, Behavior b) {
		super();
		setNeedsProgressMonitor(true);
		this.map = new HashMap<Integer,String>();
		this.c = c;
		this.b = b;
	}

	@Override
	public void addPages() {
		one = new CreateBehaviorFirstPageGraphBTWizard(map,c,b);
		addPage(one);
	}

	@Override
	public boolean performFinish() {
		if(map.get(Behavior.NAME_VALUE).equals("") ||
				map.get(Behavior.REF_VALUE).equals("") ||
				map.get(Behavior.NAME_VALUE) == null ||
				map.get(Behavior.REF_VALUE) == null) {
			return false;
		}
		IWorkbenchPage page=PlatformUI.getWorkbench().
				getActiveWorkbenchWindow().getActivePage();
        DiagramEditor ds;
        if(page.getActiveEditor() instanceof DiagramEditor) {
        	 ds = (DiagramEditor)page.getActiveEditor();	
        }
        else {
        	ds = ((MultiPageEditor)page.
        			getActiveEditor()).getDiagramEditor();
        }
		Command cmd;
		if(this.b==null) {
			final Behavior b = GraphBTUtil.getBEFactory().createBehavior();
			b.setBehaviorName(map.get(Behavior.NAME_VALUE));
			b.setBehaviorDesc(map.get(Behavior.DESC_VALUE));
			b.setBehaviorRef(map.get(Behavior.REF_VALUE));
			b.setBehaviorType(BehaviorType.getByName(map.get(Behavior.TYPE_VALUE)));
			b.setTechnicalDetail(map.get(Behavior.DETAIL_VALUE));
			
	        
	        cmd = new RecordingCommand(ds.getEditingDomain(), "Create behavior") {
				protected void doExecute() {
					c.getBehaviors().add(b);
			    }
			};
		}
		else{
			cmd = new RecordingCommand(ds.getEditingDomain(), "Update behavior") {
				protected void doExecute() {
					b.setBehaviorName(map.get(Behavior.NAME_VALUE));
					b.setBehaviorDesc(map.get(Behavior.DESC_VALUE));
					b.setTechnicalDetail(map.get(Behavior.DETAIL_VALUE));
			    }
			};
		}
		TransactionalEditingDomain f = ds.getEditingDomain();
		f.getCommandStack().execute(cmd);
		return true;
	}
}
