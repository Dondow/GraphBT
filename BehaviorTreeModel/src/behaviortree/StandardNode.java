/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package behaviortree;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Standard Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link behaviortree.StandardNode#getBehavior <em>Behavior</em>}</li>
 *   <li>{@link behaviortree.StandardNode#getBehaviorType <em>Behavior Type</em>}</li>
 *   <li>{@link behaviortree.StandardNode#getTraceabilityLink <em>Traceability Link</em>}</li>
 *   <li>{@link behaviortree.StandardNode#getTraceabilityStatus <em>Traceability Status</em>}</li>
 *   <li>{@link behaviortree.StandardNode#getComponent <em>Component</em>}</li>
 *   <li>{@link behaviortree.StandardNode#getOperator <em>Operator</em>}</li>
 *   <li>{@link behaviortree.StandardNode#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @see behaviortree.BehaviortreePackage#getStandardNode()
 * @model
 * @generated
 */
public interface StandardNode extends Node {
	/**
	 * Returns the value of the '<em><b>Behavior</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Behavior</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behavior</em>' attribute.
	 * @see #setBehavior(String)
	 * @see behaviortree.BehaviortreePackage#getStandardNode_Behavior()
	 * @model
	 * @generated
	 */
	String getBehavior();

	/**
	 * Sets the value of the '{@link behaviortree.StandardNode#getBehavior <em>Behavior</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Behavior</em>' attribute.
	 * @see #getBehavior()
	 * @generated
	 */
	void setBehavior(String value);

	/**
	 * Returns the value of the '<em><b>Behavior Type</b></em>' attribute.
	 * The literals are from the enumeration {@link behaviortree.BehaviorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Behavior Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behavior Type</em>' attribute.
	 * @see behaviortree.BehaviorType
	 * @see #setBehaviorType(BehaviorType)
	 * @see behaviortree.BehaviortreePackage#getStandardNode_BehaviorType()
	 * @model
	 * @generated
	 */
	BehaviorType getBehaviorType();

	/**
	 * Sets the value of the '{@link behaviortree.StandardNode#getBehaviorType <em>Behavior Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Behavior Type</em>' attribute.
	 * @see behaviortree.BehaviorType
	 * @see #getBehaviorType()
	 * @generated
	 */
	void setBehaviorType(BehaviorType value);

	/**
	 * Returns the value of the '<em><b>Traceability Link</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Traceability Link</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Traceability Link</em>' attribute.
	 * @see #setTraceabilityLink(String)
	 * @see behaviortree.BehaviortreePackage#getStandardNode_TraceabilityLink()
	 * @model
	 * @generated
	 */
	String getTraceabilityLink();

	/**
	 * Sets the value of the '{@link behaviortree.StandardNode#getTraceabilityLink <em>Traceability Link</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Traceability Link</em>' attribute.
	 * @see #getTraceabilityLink()
	 * @generated
	 */
	void setTraceabilityLink(String value);

	/**
	 * Returns the value of the '<em><b>Traceability Status</b></em>' attribute.
	 * The literals are from the enumeration {@link behaviortree.TraceabilityStatus}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Traceability Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Traceability Status</em>' attribute.
	 * @see behaviortree.TraceabilityStatus
	 * @see #setTraceabilityStatus(TraceabilityStatus)
	 * @see behaviortree.BehaviortreePackage#getStandardNode_TraceabilityStatus()
	 * @model
	 * @generated
	 */
	TraceabilityStatus getTraceabilityStatus();

	/**
	 * Sets the value of the '{@link behaviortree.StandardNode#getTraceabilityStatus <em>Traceability Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Traceability Status</em>' attribute.
	 * @see behaviortree.TraceabilityStatus
	 * @see #getTraceabilityStatus()
	 * @generated
	 */
	void setTraceabilityStatus(TraceabilityStatus value);

	/**
	 * Returns the value of the '<em><b>Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' reference.
	 * @see #setComponent(Component)
	 * @see behaviortree.BehaviortreePackage#getStandardNode_Component()
	 * @model
	 * @generated
	 */
	Component getComponent();

	/**
	 * Sets the value of the '{@link behaviortree.StandardNode#getComponent <em>Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' reference.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(Component value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link behaviortree.Operator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see behaviortree.Operator
	 * @see #setOperator(Operator)
	 * @see behaviortree.BehaviortreePackage#getStandardNode_Operator()
	 * @model
	 * @generated
	 */
	Operator getOperator();

	/**
	 * Sets the value of the '{@link behaviortree.StandardNode#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see behaviortree.Operator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(Operator value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see behaviortree.BehaviortreePackage#getStandardNode_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link behaviortree.StandardNode#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

} // StandardNode
