// generated with ast extension for cup
// version 0.8
// 8/1/2021 15:36:29


package rs.ac.bg.etf.pp1.ast;

public class FirstParamExpr extends ActualParamList {

    private SingleParameter SingleParameter;

    public FirstParamExpr (SingleParameter SingleParameter) {
        this.SingleParameter=SingleParameter;
        if(SingleParameter!=null) SingleParameter.setParent(this);
    }

    public SingleParameter getSingleParameter() {
        return SingleParameter;
    }

    public void setSingleParameter(SingleParameter SingleParameter) {
        this.SingleParameter=SingleParameter;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SingleParameter!=null) SingleParameter.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleParameter!=null) SingleParameter.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleParameter!=null) SingleParameter.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FirstParamExpr(\n");

        if(SingleParameter!=null)
            buffer.append(SingleParameter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FirstParamExpr]");
        return buffer.toString();
    }
}
