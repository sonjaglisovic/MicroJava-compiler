// generated with ast extension for cup
// version 0.8
// 7/1/2021 8:21:20


package rs.ac.bg.etf.pp1.ast;

public class UnmatchedIf extends Statement {

    private ConditionPart ConditionPart;
    private Statement Statement;

    public UnmatchedIf (ConditionPart ConditionPart, Statement Statement) {
        this.ConditionPart=ConditionPart;
        if(ConditionPart!=null) ConditionPart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ConditionPart getConditionPart() {
        return ConditionPart;
    }

    public void setConditionPart(ConditionPart ConditionPart) {
        this.ConditionPart=ConditionPart;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConditionPart!=null) ConditionPart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConditionPart!=null) ConditionPart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConditionPart!=null) ConditionPart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("UnmatchedIf(\n");

        if(ConditionPart!=null)
            buffer.append(ConditionPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [UnmatchedIf]");
        return buffer.toString();
    }
}
