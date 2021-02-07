// generated with ast extension for cup
// version 0.8
// 7/1/2021 8:21:20


package rs.ac.bg.etf.pp1.ast;

public class NoSuperclass extends OptionalParent {

    public NoSuperclass () {
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
        buffer.append("NoSuperclass(\n");

        buffer.append(tab);
        buffer.append(") [NoSuperclass]");
        return buffer.toString();
    }
}
