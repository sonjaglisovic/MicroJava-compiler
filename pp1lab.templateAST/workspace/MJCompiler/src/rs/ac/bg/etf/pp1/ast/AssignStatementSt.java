// generated with ast extension for cup
// version 0.8
// 7/1/2021 8:21:21


package rs.ac.bg.etf.pp1.ast;

public class AssignStatementSt extends DesignatorStatement {

    private Designator Designator;
    private AssignStatement AssignStatement;

    public AssignStatementSt (Designator Designator, AssignStatement AssignStatement) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.AssignStatement=AssignStatement;
        if(AssignStatement!=null) AssignStatement.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public AssignStatement getAssignStatement() {
        return AssignStatement;
    }

    public void setAssignStatement(AssignStatement AssignStatement) {
        this.AssignStatement=AssignStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(AssignStatement!=null) AssignStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(AssignStatement!=null) AssignStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(AssignStatement!=null) AssignStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AssignStatementSt(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AssignStatement!=null)
            buffer.append(AssignStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AssignStatementSt]");
        return buffer.toString();
    }
}
