/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.be.graphbt.model.graphbt;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Behavior</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link behaviortree.Behavior#getBehaviorType <em>Behavior Type</em>}</li>
 *   <li>{@link behaviortree.Behavior#getBehaviorName <em>Behavior Name</em>}</li>
 *   <li>{@link behaviortree.Behavior#getBehaviorRef <em>Behavior Ref</em>}</li>
 *   <li>{@link behaviortree.Behavior#getBehaviorDesc <em>Behavior Desc</em>}</li>
 *   <li>{@link behaviortree.Behavior#getTechnicalDetail <em>Technical Detail</em>}</li>
 * </ul>
 * </p>
 *
 * @see behaviortree.BehaviortreePackage#getBehavior()
 * @model
 * @generated
 */
public interface Behavior extends EObject {
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
	 * @see behaviortree.BehaviortreePackage#getBehavior_BehaviorType()
	 * @model
	 * @generated
	 */
	BehaviorType getBehaviorType();

	/**
	 * Sets the value of the '{@link behaviortree.Behavior#getBehaviorType <em>Behavior Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Behavior Type</em>' attribute.
	 * @see behaviortree.BehaviorType
	 * @see #getBehaviorType()
	 * @generated
	 */
	void setBehaviorType(BehaviorType value);

	/**
	 * Returns the value of the '<em><b>Behavior Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Behavior Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behavior Name</em>' attribute.
	 * @see #setBehaviorName(String)
	 * @see behaviortree.BehaviortreePackage#getBehavior_BehaviorName()
	 * @model
	 * @generated
	 */
	String getBehaviorName();

	/**
	 * Sets the value of the '{@link behaviortree.Behavior#getBehaviorName <em>Behavior Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Behavior Name</em>' attribute.
	 * @see #getBehaviorName()
	 * @generated
	 */
	void setBehaviorName(String value);

	/**
	 * Returns the value of the '<em><b>Behavior Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Behavior Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behavior Ref</em>' attribute.
	 * @see #setBehaviorRef(String)
	 * @see behaviortree.BehaviortreePackage#getBehavior_BehaviorRef()
	 * @model
	 * @generated
	 */
	String getBehaviorRef();

	/**
	 * Sets the value of the '{@link behaviortree.Behavior#getBehaviorRef <em>Behavior Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Behavior Ref</em>' attribute.
	 * @see #getBehaviorRef()
	 * @generated
	 */
	void setBehaviorRef(String value);
	/**
	 * Returns the value of the '<em><b>Behavior Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Behavior Desc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behavior Desc</em>' attribute.
	 * @see #setBehaviorDesc(String)
	 * @see behaviortree.BehaviortreePackage#getBehavior_BehaviorDesc()
	 * @model
	 * @generated
	 */
	String getBehaviorDesc();

	/**
	 * Sets the value of the '{@link behaviortree.Behavior#getBehaviorDesc <em>Behavior Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Behavior Desc</em>' attribute.
	 * @see #getBehaviorDesc()
	 * @generated
	 */
	void setBehaviorDesc(String value);
	/**
	 * Returns the value of the '<em><b>Technical Detail</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Technical Detail</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Technical Detail</em>' attribute.
	 * @see #setTechnicalDetail(String)
	 * @see behaviortree.BehaviortreePackage#getBehavior_TechnicalDetail()
	 * @model
	 * @generated
	 */
	String getTechnicalDetail();

	/**
	 * Sets the value of the '{@link behaviortree.Behavior#getTechnicalDetail <em>Technical Detail</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Technical Detail</em>' attribute.
	 * @see #getTechnicalDetail()
	 * @generated
	 */
	void setTechnicalDetail(String value);
	static final int NAME_VALUE = 0x11;
	static final int REF_VALUE = 0x10;
	static final int DESC_VALUE = 0x0F;
	static final int TYPE_VALUE = 0x0E;
	static final int DETAIL_VALUE = 0x0D;
} // Behavior
