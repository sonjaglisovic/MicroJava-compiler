// generated with ast extension for cup
// version 0.8
// 8/1/2021 15:36:29


package rs.ac.bg.etf.pp1.ast;

public class AdditionalPart extends AdditionalList {

    private AdditionalList AdditionalList;
    private AdditionalElement AdditionalElement;

    public AdditionalPart (AdditionalList AdditionalList, AdditionalElement AdditionalElement) {
        this.AdditionalList=AdditionalList;
        if(AdditionalList!=null) AdditionalList.setParent(this);
        this.AdditionalElement=AdditionalElement;
        if(AdditionalElement!=null) AdditionalElement.setParent(this);
    }

    public AdditionalList getAdditionalList() {
        return AdditionalList;
    }

    public void setAdditionalList(AdditionalList AdditionalList) {
        this.AdditionalList=AdditionalList;
    }

    public AdditionalElement getAdditionalElement() {
        return AdditionalElement;
    }

    public void setAdditionalElement(AdditionalElement AdditionalElement) {
        this.AdditionalElement=AdditionalElement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AdditionalList!=null) AdditionalList.accept(visitor);
        if(AdditionalElement!=null) AdditionalElement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AdditionalList!=null) AdditionalList.traverseTopDown(visitor);
        if(AdditionalElement!=null) AdditionalElement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AdditionalList!=null) AdditionalList.traverseBottomUp(visitor);
        if(AdditionalElement!=null) AdditionalElement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AdditionalPart(\n");

        if(AdditionalList!=null)
            buffer.append(AdditionalList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AdditionalElement!=null)
            buffer.append(AdditionalElement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AdditionalPart]");
        return buffer.toString();
    }
}
