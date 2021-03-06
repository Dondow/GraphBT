/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.be.graphbt.codegenerator.absmodel;
import java.util.ArrayList;
/**
 *
 * @author Emerson
 */
public class ABSMethodImplementation implements ABSBlock{
    private ABSMethod am;
    private ArrayList<ABSStatement> arr;
    
    public ABSMethodImplementation(ABSMethod am) {
        this(am,new ArrayList<ABSStatement>());
    }
    public ABSMethodImplementation(ABSMethod am, ArrayList<ABSStatement> arr) {
        this.am = am;
        this.arr = arr;
    }
    
    public void addStatement(int line, ABSStatement as) {
        arr.add(line,as);
    }
    public void addStatement(ABSStatement as) {
        arr.add(as);
    }
    
    public ABSMethod getMethodDeclaration() {
        return am;
    }
    
    public String toString() {
        String temp = am.toString();
        temp+=" {\n";
        for(int i=0; i < arr.size(); i++) {
            temp+=arr.get(i).toString();
        }
        temp+="}";
        return temp;
    }

    @Override
    public boolean isDeclared(ABSDeclarable a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
