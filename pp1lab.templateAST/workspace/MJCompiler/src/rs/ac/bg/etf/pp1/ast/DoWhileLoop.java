// generated with ast extension for cup
// version 0.8
// 3/1/2021 19:30:44


package rs.ac.bg.etf.pp1.ast;

public class DoWhileLoop extends Statement {

    private Do Do;
    private Statement Statement;
    private DoWhileConditionLoad DoWhileConditionLoad;
    private Condition Condition;

    public DoWhileLoop (Do Do, Statement Statement, DoWhileConditionLoad DoWhileConditionLoad, Condition Condition) {
        this.Do=Do;
        if(Do!=null) Do.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.DoWhileConditionLoad=DoWhileConditionLoad;
        if(DoWhileConditionLoad!=null) DoWhileConditionLoad.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
    }

    public Do getDo() {
        return Do;
    }

    public void setDo(Do Do) {
        this.Do=Do;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public DoWhileConditionLoad getDoWhileConditionLoad() {
        return DoWhileConditionLoad;
    }

    public void setDoWhileConditionLoad(DoWhileConditionLoad DoWhileConditionLoad) {
        this.DoWhileConditionLoad=DoWhileConditionLoad;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Do!=null) Do.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(DoWhileConditionLoad!=null) DoWhileConditionLoad.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Do!=null) Do.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(DoWhileConditionLoad!=null) DoWhileConditionLoad.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Do!=null) Do.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(DoWhileConditionLoad!=null) DoWhileConditionLoad.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoWhileLoop(\n");

        if(Do!=null)
            buffer.append(Do.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DoWhileConditionLoad!=null)
            buffer.append(DoWhileConditionLoad.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoWhileLoop]");
        return buffer.toString();
    }
}
