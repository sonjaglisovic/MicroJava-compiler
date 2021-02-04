// generated with ast extension for cup
// version 0.8
// 3/1/2021 19:30:44


package rs.ac.bg.etf.pp1.ast;

public class ClassComp extends Relop {

    private ClassRel ClassRel;

    public ClassComp (ClassRel ClassRel) {
        this.ClassRel=ClassRel;
        if(ClassRel!=null) ClassRel.setParent(this);
    }

    public ClassRel getClassRel() {
        return ClassRel;
    }

    public void setClassRel(ClassRel ClassRel) {
        this.ClassRel=ClassRel;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassRel!=null) ClassRel.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassRel!=null) ClassRel.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassRel!=null) ClassRel.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassComp(\n");

        if(ClassRel!=null)
            buffer.append(ClassRel.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassComp]");
        return buffer.toString();
    }
}
