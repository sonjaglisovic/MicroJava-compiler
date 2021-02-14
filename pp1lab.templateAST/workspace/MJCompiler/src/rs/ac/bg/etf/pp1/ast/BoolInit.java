// generated with ast extension for cup
// version 0.8
// 8/1/2021 15:36:29


package rs.ac.bg.etf.pp1.ast;

public class BoolInit extends Initializer {

    private BoolInitializer BoolInitializer;

    public BoolInit (BoolInitializer BoolInitializer) {
        this.BoolInitializer=BoolInitializer;
        if(BoolInitializer!=null) BoolInitializer.setParent(this);
    }

    public BoolInitializer getBoolInitializer() {
        return BoolInitializer;
    }

    public void setBoolInitializer(BoolInitializer BoolInitializer) {
        this.BoolInitializer=BoolInitializer;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(BoolInitializer!=null) BoolInitializer.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(BoolInitializer!=null) BoolInitializer.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(BoolInitializer!=null) BoolInitializer.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BoolInit(\n");

        if(BoolInitializer!=null)
            buffer.append(BoolInitializer.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BoolInit]");
        return buffer.toString();
    }
}
