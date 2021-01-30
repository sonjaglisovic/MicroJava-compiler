// generated with ast extension for cup
// version 0.8
// 30/0/2021 12:32:52


package rs.ac.bg.etf.pp1.ast;

public class DecrementSt extends DesignatorStatement {

    private Designator Designator;
    private String M1;

    public DecrementSt (Designator Designator, String M1) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.M1=M1;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public String getM1() {
        return M1;
    }

    public void setM1(String M1) {
        this.M1=M1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DecrementSt(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+M1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DecrementSt]");
        return buffer.toString();
    }
}
