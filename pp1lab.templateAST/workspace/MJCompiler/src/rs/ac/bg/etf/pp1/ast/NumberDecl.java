// generated with ast extension for cup
// version 0.8
// 30/0/2021 12:32:52


package rs.ac.bg.etf.pp1.ast;

public class NumberDecl extends Factor {

    private Integer constType;

    public NumberDecl (Integer constType) {
        this.constType=constType;
    }

    public Integer getConstType() {
        return constType;
    }

    public void setConstType(Integer constType) {
        this.constType=constType;
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
        buffer.append("NumberDecl(\n");

        buffer.append(" "+tab+constType);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumberDecl]");
        return buffer.toString();
    }
}
