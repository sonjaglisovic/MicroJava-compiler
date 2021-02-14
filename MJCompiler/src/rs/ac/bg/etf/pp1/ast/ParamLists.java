// generated with ast extension for cup
// version 0.8
// 8/1/2021 15:36:29


package rs.ac.bg.etf.pp1.ast;

public class ParamLists extends FormPars {

    private ParamList ParamList;

    public ParamLists (ParamList ParamList) {
        this.ParamList=ParamList;
        if(ParamList!=null) ParamList.setParent(this);
    }

    public ParamList getParamList() {
        return ParamList;
    }

    public void setParamList(ParamList ParamList) {
        this.ParamList=ParamList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ParamList!=null) ParamList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ParamList!=null) ParamList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ParamList!=null) ParamList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ParamLists(\n");

        if(ParamList!=null)
            buffer.append(ParamList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ParamLists]");
        return buffer.toString();
    }
}
