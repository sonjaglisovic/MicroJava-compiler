// generated with ast extension for cup
// version 0.8
// 30/0/2021 12:32:52


package rs.ac.bg.etf.pp1.ast;

public class MemoryAllocation extends Factor {

    private Type Type;
    private OptionalExpression OptionalExpression;

    public MemoryAllocation (Type Type, OptionalExpression OptionalExpression) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.OptionalExpression=OptionalExpression;
        if(OptionalExpression!=null) OptionalExpression.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public OptionalExpression getOptionalExpression() {
        return OptionalExpression;
    }

    public void setOptionalExpression(OptionalExpression OptionalExpression) {
        this.OptionalExpression=OptionalExpression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(OptionalExpression!=null) OptionalExpression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(OptionalExpression!=null) OptionalExpression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(OptionalExpression!=null) OptionalExpression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MemoryAllocation(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalExpression!=null)
            buffer.append(OptionalExpression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MemoryAllocation]");
        return buffer.toString();
    }
}
