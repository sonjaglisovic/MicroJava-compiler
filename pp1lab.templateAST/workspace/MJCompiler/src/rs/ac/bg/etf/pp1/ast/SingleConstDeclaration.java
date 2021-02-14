// generated with ast extension for cup
// version 0.8
// 8/1/2021 15:36:29


package rs.ac.bg.etf.pp1.ast;

public class SingleConstDeclaration extends SingleConstDecl {

    private String constName;
    private Initializer Initializer;

    public SingleConstDeclaration (String constName, Initializer Initializer) {
        this.constName=constName;
        this.Initializer=Initializer;
        if(Initializer!=null) Initializer.setParent(this);
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Initializer getInitializer() {
        return Initializer;
    }

    public void setInitializer(Initializer Initializer) {
        this.Initializer=Initializer;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Initializer!=null) Initializer.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Initializer!=null) Initializer.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Initializer!=null) Initializer.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleConstDeclaration(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(Initializer!=null)
            buffer.append(Initializer.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleConstDeclaration]");
        return buffer.toString();
    }
}
