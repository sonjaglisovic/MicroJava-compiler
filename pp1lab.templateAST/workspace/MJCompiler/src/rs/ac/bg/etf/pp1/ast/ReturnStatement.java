// generated with ast extension for cup
// version 0.8
// 30/0/2021 12:32:52


package rs.ac.bg.etf.pp1.ast;

public class ReturnStatement extends Matched {

    private OptionalAfterReturn OptionalAfterReturn;

    public ReturnStatement (OptionalAfterReturn OptionalAfterReturn) {
        this.OptionalAfterReturn=OptionalAfterReturn;
        if(OptionalAfterReturn!=null) OptionalAfterReturn.setParent(this);
    }

    public OptionalAfterReturn getOptionalAfterReturn() {
        return OptionalAfterReturn;
    }

    public void setOptionalAfterReturn(OptionalAfterReturn OptionalAfterReturn) {
        this.OptionalAfterReturn=OptionalAfterReturn;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptionalAfterReturn!=null) OptionalAfterReturn.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptionalAfterReturn!=null) OptionalAfterReturn.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptionalAfterReturn!=null) OptionalAfterReturn.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ReturnStatement(\n");

        if(OptionalAfterReturn!=null)
            buffer.append(OptionalAfterReturn.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ReturnStatement]");
        return buffer.toString();
    }
}
