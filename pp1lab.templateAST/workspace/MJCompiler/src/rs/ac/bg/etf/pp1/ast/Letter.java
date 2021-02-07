// generated with ast extension for cup
// version 0.8
// 7/1/2021 8:21:21


package rs.ac.bg.etf.pp1.ast;

public class Letter extends Factor {

    private String constValue;

    public Letter (String constValue) {
        this.constValue=constValue;
    }

    public String getConstValue() {
        return constValue;
    }

    public void setConstValue(String constValue) {
        this.constValue=constValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Letter(\n");

        buffer.append(" "+tab+constValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Letter]");
        return buffer.toString();
    }
}
