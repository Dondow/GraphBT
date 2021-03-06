/**
 * 
 */
package org.be.graphbt.graphiti.diagram;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICopyFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveBendpointFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IPasteFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICopyContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IMoveBendpointContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IPasteContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

import org.be.graphbt.model.graphbt.AlternativeClass;
import org.be.graphbt.model.graphbt.BEModel;
import org.be.graphbt.model.graphbt.Behavior;
import org.be.graphbt.model.graphbt.Component;
import org.be.graphbt.model.graphbt.Composition;
import org.be.graphbt.model.graphbt.Edge;
import org.be.graphbt.model.graphbt.Layout;
import org.be.graphbt.model.graphbt.LayoutList;
import org.be.graphbt.model.graphbt.Link;
import org.be.graphbt.model.graphbt.OperatorClass;
import org.be.graphbt.model.graphbt.Requirement;
import org.be.graphbt.model.graphbt.StandardNode;
import org.be.graphbt.model.graphbt.TraceabilityStatusClass;
import org.be.graphbt.graphiti.GraphBTUtil;
import org.be.graphbt.graphiti.features.AddAtomicConnectionGraphBtFeature;
import org.be.graphbt.graphiti.features.AddComponentLayoutFeature;
import org.be.graphbt.graphiti.features.AddGeneralBtNodeFeature;
import org.be.graphbt.graphiti.features.AddLayoutFeature;
import org.be.graphbt.graphiti.features.AddSequentialConnectionGraphBtFeature;
import org.be.graphbt.graphiti.features.ConnectionReconnectGraphBTFeature;
import org.be.graphbt.graphiti.features.CopyNodeGraphBtFeature;
import org.be.graphbt.graphiti.features.CreateAtomicConnectionGraphBtFeature;
import org.be.graphbt.graphiti.features.CreateComponentLayoutFeature;
import org.be.graphbt.graphiti.features.CreateGeneralBtNodeFeature;
import org.be.graphbt.graphiti.features.CreateLayoutFeature;
import org.be.graphbt.graphiti.features.CreateSequentialConnectionGraphBtFeature;
import org.be.graphbt.graphiti.features.DeleteConnectionGraphBTFeature;
import org.be.graphbt.graphiti.features.DeleteNodeGraphBTFeature;
import org.be.graphbt.graphiti.features.LayoutGraphBtFeature;
import org.be.graphbt.graphiti.features.MoveGraphBtFeature;
import org.be.graphbt.graphiti.features.PasteNodeGraphBtFeature;
import org.be.graphbt.graphiti.features.ResizeGraphBtFeature;
import org.be.graphbt.graphiti.features.UpdateGraphBtFeature;

public class GraphBTFeatureProvider extends DefaultFeatureProvider {

	public GraphBTFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);

	}

	@Override
	public ICreateFeature[] getCreateFeatures() {
		return new ICreateFeature[] {new CreateGeneralBtNodeFeature(this), new CreateLayoutFeature(this),new CreateComponentLayoutFeature(this)};
	}

	@Override
	public ICreateConnectionFeature[] getCreateConnectionFeatures() {
		return new ICreateConnectionFeature[] {
				new CreateSequentialConnectionGraphBtFeature(this), 
				new CreateAtomicConnectionGraphBtFeature(this)};
	}

	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		if (context instanceof IAddConnectionContext && 
				context.getNewObject() instanceof Link) {
			Link l = (Link) context.getNewObject();

			if(l.getSource().getEdge().getComposition().getLiteral().
					equals(Composition.ATOMIC.getLiteral())) {
				return new AddAtomicConnectionGraphBtFeature(this);
			}
			else {
				return new AddSequentialConnectionGraphBtFeature(this);
			}
		} else if (context.getNewObject() instanceof StandardNode) {
			return new AddGeneralBtNodeFeature(this);
		} else if (context.getNewObject() instanceof Layout) {
			return new AddComponentLayoutFeature(this);
		} else if (context.getNewObject() instanceof LayoutList) {
			return new AddLayoutFeature(this);
		}
		return super.getAddFeature(context);
	}

	@Override
	public IUpdateFeature getUpdateFeature(IUpdateContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pictogramElement);
		if (bo instanceof StandardNode) {
			return new UpdateGraphBtFeature(this);
		}
		if (bo instanceof Component) {
			return new UpdateGraphBtFeature(this);
		}
		if (bo instanceof Behavior) {
			return new UpdateGraphBtFeature(this);
		}
		if (bo instanceof Requirement) {
			return new UpdateGraphBtFeature(this);
		}
		if (bo instanceof OperatorClass) {
			return new UpdateGraphBtFeature(this);
		}
		if (bo instanceof TraceabilityStatusClass) {
			return new UpdateGraphBtFeature(this);
		}
		if (bo instanceof AlternativeClass) {
			return new UpdateGraphBtFeature(this);
		}

		return super.getUpdateFeature(context);
	} 

	@Override
	public IMoveShapeFeature getMoveShapeFeature(IMoveShapeContext context) {
		return new MoveGraphBtFeature(this);
	} 

	@Override
	public ILayoutFeature getLayoutFeature(ILayoutContext context) {
		if (context.getPictogramElement() instanceof ContainerShape) {
			return  new LayoutGraphBtFeature(this);
		}
		return super.getLayoutFeature(context);
	}

	@Override
	public ICopyFeature getCopyFeature(ICopyContext context) {
		return  new CopyNodeGraphBtFeature(this);
	}

	@Override
	public IPasteFeature getPasteFeature(IPasteContext context) {
		return  new PasteNodeGraphBtFeature(this);
	}

	@Override
	public IResizeShapeFeature getResizeShapeFeature(
			IResizeShapeContext context) {
		return new ResizeGraphBtFeature(this);
	}

	@Override
	public IFeature[] getDragAndDropFeatures(IPictogramElementContext context) {
		return getCreateConnectionFeatures();
	}

	public IReconnectionFeature getReconnectionFeature(IReconnectionContext context) {
		return null;
		//return new ConnectionReconnectGraphBTFeature(this);
	}

	@Override
	public IRemoveFeature getRemoveFeature(IRemoveContext context) {
		return this.getRemoveFeatureEnabled(context); // remove disabled for the UI  
	}

	protected IRemoveFeature getRemoveFeatureEnabled(IRemoveContext context) {
		return super.getRemoveFeature(context); // used where we enable remove (deleting...) 
	}

	@Override
	public IDeleteFeature getDeleteFeature(IDeleteContext context) {
		PictogramElement pe = context.getPictogramElement();
		Object ob = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
		if(ob instanceof StandardNode) {
			return new DeleteNodeGraphBTFeature(this);
		}
		if(ob instanceof Link) {
			return new DeleteConnectionGraphBTFeature(this);
		}
		return super.getDeleteFeature(context);
	} 
	@Override
	public IMoveBendpointFeature getMoveBendpointFeature(IMoveBendpointContext context) {
		return null;
	}

	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		ArrayList<ICustomFeature> customFeatures = new ArrayList<ICustomFeature>();
		final PictogramElement pe = context.getPictogramElements()[0];
		final BEModel model = GraphBTUtil.getBEModel(GraphBTFeatureProvider.this.getDiagramTypeProvider().getDiagram());

		ICustomFeature[] ret = super.getCustomFeatures(context);
		for(int i=0; i < ret.length; i++) {
			customFeatures.add(ret[i]);
		}
		final Object ob = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
		if(ob instanceof Layout) {
			final LayoutList list = model.getLayoutList();
			final Layout l = (Layout) pe.getLink().getBusinessObjects().get(0);
			customFeatures.add(new ICustomFeature() {

				@Override
				public IFeatureProvider getFeatureProvider() {
					// TODO Auto-generated method stub
					return GraphBTFeatureProvider.this;
				}

				@Override
				public String getDescription() {
					return "Bring the layout to the top";
				}

				@Override
				public String getName() {
					return "Bring to top";
				}

				@Override
				public boolean isAvailable(IContext context) {
					return true;
				}

				@Override
				public boolean hasDoneChanges() {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public void execute(IContext context) {
					ContainerShape c = (ContainerShape)pe.eContainer();
					list.getLayouts().remove(l);
					list.getLayouts().add(l);
					c.getChildren().remove(pe);
					c.getChildren().add((Shape) pe);
					// TODO Auto-generated method stub
				}

				@Override
				public boolean canUndo(IContext context) {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public boolean canExecute(IContext context) {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public String getImageId() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public void execute(ICustomContext context) {
					// TODO Auto-generated method stub
				}

				@Override
				public boolean canExecute(ICustomContext context) {
					// TODO Auto-generated method stub
					return true;
				}
			});
			customFeatures.add(new ICustomFeature() {

				@Override
				public IFeatureProvider getFeatureProvider() {
					// TODO Auto-generated method stub
					return GraphBTFeatureProvider.this;
				}

				@Override
				public String getDescription() {
					return "Bring the layout to the bottom";
				}

				@Override
				public String getName() {
					return "Bring to bottom";
				}

				@Override
				public boolean isAvailable(IContext context) {
					return true;
				}

				@Override
				public boolean hasDoneChanges() {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public void execute(IContext context) {
					ContainerShape c = (ContainerShape)pe.eContainer();
					list.getLayouts().remove(l);
					list.getLayouts().add(0,l);
					c.getChildren().remove(pe);
					c.getChildren().add(0,(Shape)pe);
				}

				@Override
				public boolean canUndo(IContext context) {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public boolean canExecute(IContext context) {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public String getImageId() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public void execute(ICustomContext context) {
					// TODO Auto-generated method stub
				}

				@Override
				public boolean canExecute(ICustomContext context) {
					// TODO Auto-generated method stub
					return true;
				}
			});
			customFeatures.add(new ICustomFeature() {

				@Override
				public IFeatureProvider getFeatureProvider() {
					// TODO Auto-generated method stub
					return GraphBTFeatureProvider.this;
				}

				@Override
				public String getDescription() {
					return "Send the layout to the back";
				}

				@Override
				public String getName() {
					return "Send to back";
				}

				@Override
				public boolean isAvailable(IContext context) {
					return true;
				}

				@Override
				public boolean hasDoneChanges() {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public void execute(IContext context) {
					ContainerShape c = (ContainerShape)pe.eContainer();
					int i = c.getChildren().indexOf((Shape)pe);
					c.getChildren().remove(pe);
					c.getChildren().add(i-1,(Shape)pe);
					list.getLayouts().remove(l);
					list.getLayouts().add(i-1,l);
				}

				@Override
				public boolean canUndo(IContext context) {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public boolean canExecute(IContext context) {
					// TODO Auto-generated method stub
					ContainerShape c = (ContainerShape)pe.eContainer();
					int i = c.getChildren().indexOf((Shape)pe);
					if(i==0)
						return false;
					return true;
				}

				@Override
				public String getImageId() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public void execute(ICustomContext context) {
					// TODO Auto-generated method stub
				}

				@Override
				public boolean canExecute(ICustomContext context) {
					// TODO Auto-generated method stub
					return true;
				}
			});
			customFeatures.add(new ICustomFeature() {

				@Override
				public IFeatureProvider getFeatureProvider() {
					// TODO Auto-generated method stub
					return GraphBTFeatureProvider.this;
				}

				@Override
				public String getDescription() {
					return "Send the layout to the front";
				}

				@Override
				public String getName() {
					return "Send to front";
				}

				@Override
				public boolean isAvailable(IContext context) {
					return true;
				}

				@Override
				public boolean hasDoneChanges() {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public void execute(IContext context) {
					ContainerShape c = (ContainerShape)pe.eContainer();
					int i = c.getChildren().indexOf((Shape)pe);
					c.getChildren().remove(pe);
					c.getChildren().add(i+1,(Shape)pe);
					list.getLayouts().remove(l);
					list.getLayouts().add(i+1,l);
				}

				@Override
				public boolean canUndo(IContext context) {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public boolean canExecute(IContext context) {
					// TODO Auto-generated method stub
					ContainerShape c = (ContainerShape)pe.eContainer();
					int i = c.getChildren().indexOf((Shape)pe);
					if(i==c.getChildren().size()-1)
						return false;
					return true;
				}

				@Override
				public String getImageId() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public void execute(ICustomContext context) {
					// TODO Auto-generated method stub
				}

				@Override
				public boolean canExecute(ICustomContext context) {
					// TODO Auto-generated method stub
					return true;
				}
			});

			ret = new ICustomFeature[customFeatures.size()];
			for(int i = 0; i < ret.length; i++) {
				ret[i] = customFeatures.get(i);
			}
			return ret;
		}

		if(ob instanceof Link) {
			//return new DeleteConnectionGraphBTFeature(this);
		}

		return super.getCustomFeatures(context);
	}
}
