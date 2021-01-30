// generated with ast extension for cup
// version 0.8
// 30/0/2021 12:32:52


package rs.ac.bg.etf.pp1.ast;

public class FirstParam extends ParamList {

    private ParamDecl ParamDecl;

    public FirstParam (ParamDecl ParamDecl) {
        this.ParamDecl=ParamDecl;
        if(ParamDecl!=null) ParamDecl.setParent(this);
    }

    public ParamDecl getParamDecl() {
        return ParamDecl;
    }

    public void setParamDecl(ParamDecl ParamDecl) {
        this.ParamDecl=ParamDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ParamDecl!=null) ParamDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ParamDecl!=null) ParamDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ParamDecl!=null) ParamDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FirstParam(\n");

        if(ParamDecl!=null)
            buffer.append(ParamDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FirstParam]");
        return buffer.toString();
    }
}
