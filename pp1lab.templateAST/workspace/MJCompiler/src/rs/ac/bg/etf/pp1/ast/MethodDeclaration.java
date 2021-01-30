// generated with ast extension for cup
// version 0.8
// 30/0/2021 12:32:52


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclaration extends SingleMethodDecl {

    private MethodSign MethodSign;
    private JustVarDecl JustVarDecl;
    private StatementList StatementList;

    public MethodDeclaration (MethodSign MethodSign, JustVarDecl JustVarDecl, StatementList StatementList) {
        this.MethodSign=MethodSign;
        if(MethodSign!=null) MethodSign.setParent(this);
        this.JustVarDecl=JustVarDecl;
        if(JustVarDecl!=null) JustVarDecl.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethodSign getMethodSign() {
        return MethodSign;
    }

    public void setMethodSign(MethodSign MethodSign) {
        this.MethodSign=MethodSign;
    }

    public JustVarDecl getJustVarDecl() {
        return JustVarDecl;
    }

    public void setJustVarDecl(JustVarDecl JustVarDecl) {
        this.JustVarDecl=JustVarDecl;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodSign!=null) MethodSign.accept(visitor);
        if(JustVarDecl!=null) JustVarDecl.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodSign!=null) MethodSign.traverseTopDown(visitor);
        if(JustVarDecl!=null) JustVarDecl.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodSign!=null) MethodSign.traverseBottomUp(visitor);
        if(JustVarDecl!=null) JustVarDecl.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclaration(\n");

        if(MethodSign!=null)
            buffer.append(MethodSign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(JustVarDecl!=null)
            buffer.append(JustVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclaration]");
        return buffer.toString();
    }
}
