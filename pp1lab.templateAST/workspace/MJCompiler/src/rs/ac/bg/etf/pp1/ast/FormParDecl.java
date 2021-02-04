// generated with ast extension for cup
// version 0.8
// 3/1/2021 19:30:44


package rs.ac.bg.etf.pp1.ast;

public class FormParDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String paramName;
    private OptionalArraySpec OptionalArraySpec;

    public FormParDecl (String paramName, OptionalArraySpec OptionalArraySpec) {
        this.paramName=paramName;
        this.OptionalArraySpec=OptionalArraySpec;
        if(OptionalArraySpec!=null) OptionalArraySpec.setParent(this);
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName=paramName;
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
        buffer.append("FormParDecl(\n");

        buffer.append(" "+tab+paramName);
        buffer.append("\n");

        if(OptionalArraySpec!=null)
            buffer.append(OptionalArraySpec.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParDecl]");
        return buffer.toString();
    }
}
