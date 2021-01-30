package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Struct;

public class MyStruct extends Struct {

    public static final int Bool = 5;
    public static final int Void = 6;

    public MyStruct(int kind) {
	super(kind);
	// TODO Auto-generated constructor stub
    }

    private Struct myElemType;

    public Struct getElemType() {
	return myElemType;
    }

    public boolean assignableTo(Struct dest) {
	if (super.assignableTo(dest))
	    return true;
	if (getKind() == Class && dest.getKind() == Class)
	    return checkIfSubClass(dest);
	if (getKind() == Array && dest.getKind() == Array)
	    return dest.getElemType() == myElemType;
	return false;
    }

    private boolean checkIfSubClass(Struct dest) {
	Struct type = getElemType();
	while (type != null && type != SymbolTable.noType && !type.equals(dest))
	    type = type.getElemType();
	if (type.equals(dest))
	    return true;
	return false;
    }

    public MyStruct(int kind, Struct elemType) {
	super(kind, elemType);
	if (kind == Array || kind == Class)
	    myElemType = elemType;
    }

}
