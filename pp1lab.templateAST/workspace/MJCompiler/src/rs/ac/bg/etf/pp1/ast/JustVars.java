// generated with ast extension for cup
// version 0.8
// 30/0/2021 12:32:52


package rs.ac.bg.etf.pp1.ast;

public class JustVars extends JustVarDecl {

    private JustVarDecl JustVarDecl;
    private VarDecl VarDecl;

    public JustVars (JustVarDecl JustVarDecl, VarDecl VarDecl) {
        this.JustVarDecl=JustVarDecl;
        if(JustVarDecl!=null) JustVarDecl.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public JustVarDecl getJustVarDecl() {
        return JustVarDecl;
    }

    public void setJustVarDecl(JustVarDecl JustVarDecl) {
        this.JustVarDecl=JustVarDecl;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(JustVarDecl!=null) JustVarDecl.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(JustVarDecl!=null) JustVarDecl.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(JustVarDecl!=null) JustVarDecl.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("JustVars(\n");

        if(JustVarDecl!=null)
            buffer.append(JustVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [JustVars]");
        return buffer.toString();
    }
}
