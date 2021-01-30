package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class MyDumpSymbolTableVisitor extends SymbolTableVisitor {

    private StringBuilder output = new StringBuilder();
    private int tab_number = 0;

    private void nextIndentLevel() {
	tab_number++;
    }

    private void prevIndentLevel() {
	tab_number--;
    }

    @Override
    public void visitObjNode(Obj objToVisit) {
	for (int i = 0; i < tab_number; i++)
	    output.append("\t");
	output.append(ObjectFormatter.getObjectDescription(objToVisit));
	if (objToVisit.getKind() == Obj.Prog || objToVisit.getKind() == Obj.Meth) {
	    nextIndentLevel();
	    output.append("\n");
	}

	for (Obj o : objToVisit.getLocalSymbols()) {
	    if (o.getName().compareTo("this") != 0) {
		o.accept(this);
		output.append("\n");
	    }
	}

	if (objToVisit.getKind() == Obj.Prog || objToVisit.getKind() == Obj.Meth) {
	    prevIndentLevel();
	}
    }

    @Override
    public void visitScopeNode(Scope scopteToVisit) {
	output.append("\n");
	for (Obj o : scopteToVisit.values()) {
	    if (o.getName().compareTo("this") != 0) {
		o.accept(this);
		output.append("\n");
	    }
	}

    }

    @Override
    public void visitStructNode(Struct structToVisit) {
	// TODO Auto-generated method stub

    }

    @Override
    public String getOutput() {

	return output.toString();
    }

}
