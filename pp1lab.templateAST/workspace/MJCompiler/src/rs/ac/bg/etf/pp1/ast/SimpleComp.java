// generated with ast extension for cup
// version 0.8
// 7/1/2021 8:21:21


package rs.ac.bg.etf.pp1.ast;

public class SimpleComp extends Relop {

    private SimpleRel SimpleRel;

    public SimpleComp (SimpleRel SimpleRel) {
        this.SimpleRel=SimpleRel;
        if(SimpleRel!=null) SimpleRel.setParent(this);
    }

    public SimpleRel getSimpleRel() {
        return SimpleRel;
    }

    public void setSimpleRel(SimpleRel SimpleRel) {
        this.SimpleRel=SimpleRel;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SimpleRel!=null) SimpleRel.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SimpleRel!=null) SimpleRel.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SimpleRel!=null) SimpleRel.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SimpleComp(\n");

        if(SimpleRel!=null)
            buffer.append(SimpleRel.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SimpleComp]");
        return buffer.toString();
    }
}
