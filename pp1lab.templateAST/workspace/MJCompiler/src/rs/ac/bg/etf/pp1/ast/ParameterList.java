// generated with ast extension for cup
// version 0.8
// 3/1/2021 19:30:44


package rs.ac.bg.etf.pp1.ast;

public class ParameterList extends ActualParamList {

    private ActualParamList ActualParamList;
    private SingleParameter SingleParameter;

    public ParameterList (ActualParamList ActualParamList, SingleParameter SingleParameter) {
        this.ActualParamList=ActualParamList;
        if(ActualParamList!=null) ActualParamList.setParent(this);
        this.SingleParameter=SingleParameter;
        if(SingleParameter!=null) SingleParameter.setParent(this);
    }

    public ActualParamList getActualParamList() {
        return ActualParamList;
    }

    public void setActualParamList(ActualParamList ActualParamList) {
        this.ActualParamList=ActualParamList;
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
        if(ActualParamList!=null) ActualParamList.accept(visitor);
        if(SingleParameter!=null) SingleParameter.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActualParamList!=null) ActualParamList.traverseTopDown(visitor);
        if(SingleParameter!=null) SingleParameter.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActualParamList!=null) ActualParamList.traverseBottomUp(visitor);
        if(SingleParameter!=null) SingleParameter.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ParameterList(\n");

        if(ActualParamList!=null)
            buffer.append(ActualParamList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SingleParameter!=null)
            buffer.append(SingleParameter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ParameterList]");
        return buffer.toString();
    }
}
