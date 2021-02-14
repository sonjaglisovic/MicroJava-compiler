// generated with ast extension for cup
// version 0.8
// 8/1/2021 15:36:29


package rs.ac.bg.etf.pp1.ast;

public class ClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ClassName ClassName;
    private JustVarDecl JustVarDecl;
    private OptionalMethodDecl OptionalMethodDecl;

    public ClassDecl (ClassName ClassName, JustVarDecl JustVarDecl, OptionalMethodDecl OptionalMethodDecl) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.JustVarDecl=JustVarDecl;
        if(JustVarDecl!=null) JustVarDecl.setParent(this);
        this.OptionalMethodDecl=OptionalMethodDecl;
        if(OptionalMethodDecl!=null) OptionalMethodDecl.setParent(this);
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
    }

    public JustVarDecl getJustVarDecl() {
        return JustVarDecl;
    }

    public void setJustVarDecl(JustVarDecl JustVarDecl) {
        this.JustVarDecl=JustVarDecl;
    }

    public OptionalMethodDecl getOptionalMethodDecl() {
        return OptionalMethodDecl;
    }

    public void setOptionalMethodDecl(OptionalMethodDecl OptionalMethodDecl) {
        this.OptionalMethodDecl=OptionalMethodDecl;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassName!=null) ClassName.accept(visitor);
        if(JustVarDecl!=null) JustVarDecl.accept(visitor);
        if(OptionalMethodDecl!=null) OptionalMethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(JustVarDecl!=null) JustVarDecl.traverseTopDown(visitor);
        if(OptionalMethodDecl!=null) OptionalMethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(JustVarDecl!=null) JustVarDecl.traverseBottomUp(visitor);
        if(OptionalMethodDecl!=null) OptionalMethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(JustVarDecl!=null)
            buffer.append(JustVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalMethodDecl!=null)
            buffer.append(OptionalMethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl]");
        return buffer.toString();
    }
}
