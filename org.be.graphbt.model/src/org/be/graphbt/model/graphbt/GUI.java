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
 * A representation of the model object '<em><b>GUI</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.be.graphbt.model.graphbt.GUI#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link org.be.graphbt.model.graphbt.GUI#getCodeImplementation <em>Code Implementation</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.be.graphbt.model.graphbt.GraphBTPackage#getGUI()
 * @model
 * @generated
 */
public interface GUI extends EObject {
	/**
	 * Returns the value of the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Identifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identifier</em>' attribute.
	 * @see #setIdentifier(String)
	 * @see org.be.graphbt.model.graphbt.GraphBTPackage#getGUI_Identifier()
	 * @model
	 * @generated
	 */
	String getIdentifier();

	/**
	 * Sets the value of the '{@link org.be.graphbt.model.graphbt.GUI#getIdentifier <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identifier</em>' attribute.
	 * @see #getIdentifier()
	 * @generated
	 */
	void setIdentifier(String value);

	/**
	 * Returns the value of the '<em><b>Code Implementation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Code Implementation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Code Implementation</em>' attribute.
	 * @see #setCodeImplementation(String)
	 * @see org.be.graphbt.model.graphbt.GraphBTPackage#getGUI_CodeImplementation()
	 * @model
	 * @generated
	 */
	String getCodeImplementation();

	/**
	 * Sets the value of the '{@link org.be.graphbt.model.graphbt.GUI#getCodeImplementation <em>Code Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code Implementation</em>' attribute.
	 * @see #getCodeImplementation()
	 * @generated
	 */
	void setCodeImplementation(String value);

} // GUI
