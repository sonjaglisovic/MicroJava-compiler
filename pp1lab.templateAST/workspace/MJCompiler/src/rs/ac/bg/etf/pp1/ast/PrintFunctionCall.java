// generated with ast extension for cup
// version 0.8
// 7/1/2021 8:21:20


package rs.ac.bg.etf.pp1.ast;

public class PrintFunctionCall extends Statement {

    private Expr Expr;
    private OptionalSecondParam OptionalSecondParam;

    public PrintFunctionCall (Expr Expr, OptionalSecondParam OptionalSecondParam) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.OptionalSecondParam=OptionalSecondParam;
        if(OptionalSecondParam!=null) OptionalSecondParam.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public OptionalSecondParam getOptionalSecondParam() {
        return OptionalSecondParam;
    }

    public void setOptionalSecondParam(OptionalSecondParam OptionalSecondParam) {
        this.OptionalSecondParam=OptionalSecondParam;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(OptionalSecondParam!=null) OptionalSecondParam.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(OptionalSecondParam!=null) OptionalSecondParam.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(OptionalSecondParam!=null) OptionalSecondParam.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintFunctionCall(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalSecondParam!=null)
            buffer.append(OptionalSecondParam.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintFunctionCall]");
        return buffer.toString();
    }
}
