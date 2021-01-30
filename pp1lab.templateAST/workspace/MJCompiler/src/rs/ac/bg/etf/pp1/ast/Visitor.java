// generated with ast extension for cup
// version 0.8
// 30/0/2021 12:32:53


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(FormPars FormPars);
    public void visit(ParamDecl ParamDecl);
    public void visit(Factor Factor);
    public void visit(Statement Statement);
    public void visit(OptionalAfterReturn OptionalAfterReturn);
    public void visit(MatchedList MatchedList);
    public void visit(MethodDecl MethodDecl);
    public void visit(AdditionalList AdditionalList);
    public void visit(ConstDeclList ConstDeclList);
    public void visit(BoolInitializer BoolInitializer);
    public void visit(OptionalSecondParam OptionalSecondParam);
    public void visit(Relop Relop);
    public void visit(DeclList DeclList);
    public void visit(CaseList CaseList);
    public void visit(Expr Expr);
    public void visit(Initializer Initializer);
    public void visit(VarDecl VarDecl);
    public void visit(AdditionalElement AdditionalElement);
    public void visit(Unmatched Unmatched);
    public void visit(Do Do);
    public void visit(AssignStatement AssignStatement);
    public void visit(OptionalParent OptionalParent);
    public void visit(JustVarDecl JustVarDecl);
    public void visit(OptionalExpression OptionalExpression);
    public void visit(ActualParamList ActualParamList);
    public void visit(ParamList ParamList);
    public void visit(DesignParam DesignParam);
    public void visit(DesignatorList DesignatorList);
    public void visit(Condition Condition);
    public void visit(Mulop Mulop);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(AdditionForCall AdditionForCall);
    public void visit(ClassFieldDecList ClassFieldDecList);
    public void visit(Addop Addop);
    public void visit(StatementList StatementList);
    public void visit(SingleConstDecl SingleConstDecl);
    public void visit(ConditionPart ConditionPart);
    public void visit(OptionalArraySpec OptionalArraySpec);
    public void visit(DeclPart DeclPart);
    public void visit(OptionalMethodDecl OptionalMethodDecl);
    public void visit(SimpleRel SimpleRel);
    public void visit(SingleMethodDecl SingleMethodDecl);
    public void visit(CondTerm CondTerm);
    public void visit(CaseDecl CaseDecl);
    public void visit(ExpressionType ExpressionType);
    public void visit(TermList TermList);
    public void visit(Expr1 Expr1);
    public void visit(ActPars ActPars);
    public void visit(OptionalMinus OptionalMinus);
    public void visit(Designator Designator);
    public void visit(Matched Matched);
    public void visit(VarDeclList VarDeclList);
    public void visit(CondFact CondFact);
    public void visit(ClassRel ClassRel);
    public void visit(RetType RetType);
    public void visit(Term Term);
    public void visit(Switch Switch);
    public void visit(OptionalOp OptionalOp);
    public void visit(ComparisonLessEqual ComparisonLessEqual);
    public void visit(ComparisonLess ComparisonLess);
    public void visit(CompGraterEqual CompGraterEqual);
    public void visit(ComparisonGrater ComparisonGrater);
    public void visit(ComparionDiffernet ComparionDiffernet);
    public void visit(ComparisonEqual ComparisonEqual);
    public void visit(SimpleComp SimpleComp);
    public void visit(ClassComp ClassComp);
    public void visit(Assignop Assignop);
    public void visit(Remaining Remaining);
    public void visit(Division Division);
    public void visit(Multiplication Multiplication);
    public void visit(Subtraction Subtraction);
    public void visit(Addition Addition);
    public void visit(NoAfterRet NoAfterRet);
    public void visit(ExprAfterReturn ExprAfterReturn);
    public void visit(NoOptExpr NoOptExpr);
    public void visit(SingleExpressionDecl SingleExpressionDecl);
    public void visit(MemoryAllocation MemoryAllocation);
    public void visit(ExpressionFactor ExpressionFactor);
    public void visit(BoolValue BoolValue);
    public void visit(Letter Letter);
    public void visit(NumberDecl NumberDecl);
    public void visit(FunctionCallDesign FunctionCallDesign);
    public void visit(SimpleDesignator SimpleDesignator);
    public void visit(SingleTermDecl SingleTermDecl);
    public void visit(CompositeTerm CompositeTerm);
    public void visit(FirstTerm FirstTerm);
    public void visit(Terms Terms);
    public void visit(NOMinusExpr NOMinusExpr);
    public void visit(MinusExpr MinusExpr);
    public void visit(SimpleExpr SimpleExpr);
    public void visit(CondExpr CondExpr);
    public void visit(OptionalOperator OptionalOperator);
    public void visit(LogicalExpr LogicalExpr);
    public void visit(SingleFact SingleFact);
    public void visit(ConditionTerm ConditionTerm);
    public void visit(SingleCondTerm SingleCondTerm);
    public void visit(ConditionExpr ConditionExpr);
    public void visit(AccessElement AccessElement);
    public void visit(AccessField AccessField);
    public void visit(NoAdditional NoAdditional);
    public void visit(AdditionalPart AdditionalPart);
    public void visit(DesignatorName DesignatorName);
    public void visit(DesignatorDecl DesignatorDecl);
    public void visit(SingleParameter SingleParameter);
    public void visit(FirstParamExpr FirstParamExpr);
    public void visit(ParameterList ParameterList);
    public void visit(NoActPars NoActPars);
    public void visit(AcParamList AcParamList);
    public void visit(AssignError AssignError);
    public void visit(AssignStm AssignStm);
    public void visit(DecrementSt DecrementSt);
    public void visit(IncrementSt IncrementSt);
    public void visit(FuncCallSt FuncCallSt);
    public void visit(AssignStatementSt AssignStatementSt);
    public void visit(NoSecondParam NoSecondParam);
    public void visit(PrintSecondParam PrintSecondParam);
    public void visit(CaseStatement CaseStatement);
    public void visit(NoCaseList NoCaseList);
    public void visit(CaseListDecl CaseListDecl);
    public void visit(SwitchStart SwitchStart);
    public void visit(DoWhileStart DoWhileStart);
    public void visit(MatchedListSt MatchedListSt);
    public void visit(SwitchStatement SwitchStatement);
    public void visit(PrintFunctionCall PrintFunctionCall);
    public void visit(ReadFunctionCall ReadFunctionCall);
    public void visit(ReturnStatement ReturnStatement);
    public void visit(BreakStatement BreakStatement);
    public void visit(DoWhileLoop DoWhileLoop);
    public void visit(ContinueStatement ContinueStatement);
    public void visit(MatchedStatement MatchedStatement);
    public void visit(DesStatement DesStatement);
    public void visit(ErrorCondition ErrorCondition);
    public void visit(RightCondition RightCondition);
    public void visit(UnmatchedIfElse UnmatchedIfElse);
    public void visit(UnmatchedIf UnmatchedIf);
    public void visit(UnmatchedStmt UnmatchedStmt);
    public void visit(MatchedStmt MatchedStmt);
    public void visit(NoStmt NoStmt);
    public void visit(Statements Statements);
    public void visit(FormParDecl FormParDecl);
    public void visit(MethodParamDecl MethodParamDecl);
    public void visit(ParamError ParamError);
    public void visit(FirstParam FirstParam);
    public void visit(FormParams FormParams);
    public void visit(NoParams NoParams);
    public void visit(ParamLists ParamLists);
    public void visit(NoMethodInClass NoMethodInClass);
    public void visit(ClassMethodList ClassMethodList);
    public void visit(NoVars NoVars);
    public void visit(JustVars JustVars);
    public void visit(VoidType VoidType);
    public void visit(RegularType RegularType);
    public void visit(MethodName MethodName);
    public void visit(MethodSign MethodSign);
    public void visit(MethodDeclaration MethodDeclaration);
    public void visit(NoMethodDecl NoMethodDecl);
    public void visit(MethodDeclarationList MethodDeclarationList);
    public void visit(NoSuperclass NoSuperclass);
    public void visit(ExtendError ExtendError);
    public void visit(ParentSpecification ParentSpecification);
    public void visit(ClassName ClassName);
    public void visit(ClassDecl ClassDecl);
    public void visit(Type Type);
    public void visit(TrueValue TrueValue);
    public void visit(FalseValue FalseValue);
    public void visit(CharInit CharInit);
    public void visit(IntInit IntInit);
    public void visit(BoolInit BoolInit);
    public void visit(SingleConstDeclaration SingleConstDeclaration);
    public void visit(FisrtConstInList FisrtConstInList);
    public void visit(ConstList ConstList);
    public void visit(ConstDecl ConstDecl);
    public void visit(NotArray NotArray);
    public void visit(ArraySpecifier ArraySpecifier);
    public void visit(SingleVarDecl SingleVarDecl);
    public void visit(ErrorVarDcl ErrorVarDcl);
    public void visit(FirstDeclaration FirstDeclaration);
    public void visit(VariableList VariableList);
    public void visit(ErrorDecl ErrorDecl);
    public void visit(VarDecl1 VarDecl1);
    public void visit(ClassDeclaration ClassDeclaration);
    public void visit(ConstantDeclaration ConstantDeclaration);
    public void visit(VariableDeclaration VariableDeclaration);
    public void visit(NoVarDecl NoVarDecl);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(ProgramName ProgramName);
    public void visit(Program Program);

}
