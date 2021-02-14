// generated with ast extension for cup
// version 0.8
// 8/1/2021 15:36:29


package rs.ac.bg.etf.pp1.ast;

public class FormParams extends ParamList {

    private ParamList ParamList;
    private ParamDecl ParamDecl;

    public FormParams (ParamList ParamList, ParamDecl ParamDecl) {
        this.ParamList=ParamList;
        if(ParamList!=null) ParamList.setParent(this);
        this.ParamDecl=ParamDecl;
        if(ParamDecl!=null) ParamDecl.setParent(this);
    }

    public ParamList getParamList() {
        return ParamList;
    }

    public void setParamList(ParamList ParamList) {
        this.ParamList=ParamList;
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
        if(ParamList!=null) ParamList.accept(visitor);
        if(ParamDecl!=null) ParamDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ParamList!=null) ParamList.traverseTopDown(visitor);
        if(ParamDecl!=null) ParamDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ParamList!=null) ParamList.traverseBottomUp(visitor);
        if(ParamDecl!=null) ParamDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParams(\n");

        if(ParamList!=null)
            buffer.append(ParamList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ParamDecl!=null)
            buffer.append(ParamDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParams]");
        return buffer.toString();
    }
}
