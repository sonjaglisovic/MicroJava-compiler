// generated with ast extension for cup
// version 0.8
// 8/1/2021 15:36:29


package rs.ac.bg.etf.pp1.ast;

public class SwitchStatement extends Statement {

    private Switch Switch;
    private CaseList CaseList;

    public SwitchStatement (Switch Switch, CaseList CaseList) {
        this.Switch=Switch;
        if(Switch!=null) Switch.setParent(this);
        this.CaseList=CaseList;
        if(CaseList!=null) CaseList.setParent(this);
    }

    public Switch getSwitch() {
        return Switch;
    }

    public void setSwitch(Switch Switch) {
        this.Switch=Switch;
    }

    public CaseList getCaseList() {
        return CaseList;
    }

    public void setCaseList(CaseList CaseList) {
        this.CaseList=CaseList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Switch!=null) Switch.accept(visitor);
        if(CaseList!=null) CaseList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Switch!=null) Switch.traverseTopDown(visitor);
        if(CaseList!=null) CaseList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Switch!=null) Switch.traverseBottomUp(visitor);
        if(CaseList!=null) CaseList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SwitchStatement(\n");

        if(Switch!=null)
            buffer.append(Switch.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CaseList!=null)
            buffer.append(CaseList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SwitchStatement]");
        return buffer.toString();
    }
}
