// generated with ast extension for cup
// version 0.8
// 8/1/2021 15:36:29


package rs.ac.bg.etf.pp1.ast;

public class NoMethodInClass extends OptionalMethodDecl {

    public NoMethodInClass () {
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
        buffer.append("NoMethodInClass(\n");

        buffer.append(tab);
        buffer.append(") [NoMethodInClass]");
        return buffer.toString();
    }
}
