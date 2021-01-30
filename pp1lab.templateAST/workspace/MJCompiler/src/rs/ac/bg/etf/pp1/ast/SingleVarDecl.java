// generated with ast extension for cup
// version 0.8
// 30/0/2021 12:32:52


package rs.ac.bg.etf.pp1.ast;

public class SingleVarDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String varName;
    private OptionalArraySpec OptionalArraySpec;

    public SingleVarDecl (String varName, OptionalArraySpec OptionalArraySpec) {
        this.varName=varName;
        this.OptionalArraySpec=OptionalArraySpec;
        if(OptionalArraySpec!=null) OptionalArraySpec.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public OptionalArraySpec getOptionalArraySpec() {
        return OptionalArraySpec;
    }

    public void setOptionalArraySpec(OptionalArraySpec OptionalArraySpec) {
        this.OptionalArraySpec=OptionalArraySpec;
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
        if(OptionalArraySpec!=null) OptionalArraySpec.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptionalArraySpec!=null) OptionalArraySpec.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptionalArraySpec!=null) OptionalArraySpec.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleVarDecl(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(OptionalArraySpec!=null)
            buffer.append(OptionalArraySpec.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleVarDecl]");
        return buffer.toString();
    }
}
