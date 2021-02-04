// generated with ast extension for cup
// version 0.8
// 3/1/2021 19:30:44


package rs.ac.bg.etf.pp1.ast;

public class MemoryAllocation extends Factor {

    private AllocationType AllocationType;
    private OptionalExpression OptionalExpression;

    public MemoryAllocation (AllocationType AllocationType, OptionalExpression OptionalExpression) {
        this.AllocationType=AllocationType;
        if(AllocationType!=null) AllocationType.setParent(this);
        this.OptionalExpression=OptionalExpression;
        if(OptionalExpression!=null) OptionalExpression.setParent(this);
    }

    public AllocationType getAllocationType() {
        return AllocationType;
    }

    public void setAllocationType(AllocationType AllocationType) {
        this.AllocationType=AllocationType;
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
        if(AllocationType!=null) AllocationType.accept(visitor);
        if(OptionalExpression!=null) OptionalExpression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AllocationType!=null) AllocationType.traverseTopDown(visitor);
        if(OptionalExpression!=null) OptionalExpression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AllocationType!=null) AllocationType.traverseBottomUp(visitor);
        if(OptionalExpression!=null) OptionalExpression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MemoryAllocation(\n");

        if(AllocationType!=null)
            buffer.append(AllocationType.toString("  "+tab));
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
