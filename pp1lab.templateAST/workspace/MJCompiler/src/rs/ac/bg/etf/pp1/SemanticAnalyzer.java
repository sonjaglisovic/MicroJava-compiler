package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.AccessElement;
import rs.ac.bg.etf.pp1.ast.AccessField;
import rs.ac.bg.etf.pp1.ast.ArraySpecifier;
import rs.ac.bg.etf.pp1.ast.AssignStatementSt;
import rs.ac.bg.etf.pp1.ast.AssignStm;
import rs.ac.bg.etf.pp1.ast.BoolInit;
import rs.ac.bg.etf.pp1.ast.BoolValue;
import rs.ac.bg.etf.pp1.ast.BreakStatement;
import rs.ac.bg.etf.pp1.ast.CharInit;
import rs.ac.bg.etf.pp1.ast.ClassComp;
import rs.ac.bg.etf.pp1.ast.ClassDecl;
import rs.ac.bg.etf.pp1.ast.ClassName;
import rs.ac.bg.etf.pp1.ast.CompositeTerm;
import rs.ac.bg.etf.pp1.ast.CondExpr;
import rs.ac.bg.etf.pp1.ast.ConditionExpr;
import rs.ac.bg.etf.pp1.ast.ConditionTerm;
import rs.ac.bg.etf.pp1.ast.ConstDecl;
import rs.ac.bg.etf.pp1.ast.ContinueStatement;
import rs.ac.bg.etf.pp1.ast.DecrementSt;
import rs.ac.bg.etf.pp1.ast.DesignatorDecl;
import rs.ac.bg.etf.pp1.ast.DesignatorName;
import rs.ac.bg.etf.pp1.ast.DoWhileLoop;
import rs.ac.bg.etf.pp1.ast.DoWhileStart;
import rs.ac.bg.etf.pp1.ast.ExprAfterReturn;
import rs.ac.bg.etf.pp1.ast.ExpressionFactor;
import rs.ac.bg.etf.pp1.ast.FirstTerm;
import rs.ac.bg.etf.pp1.ast.FormParDecl;
import rs.ac.bg.etf.pp1.ast.FuncCallSt;
import rs.ac.bg.etf.pp1.ast.FunctionCallDesign;
import rs.ac.bg.etf.pp1.ast.IncrementSt;
import rs.ac.bg.etf.pp1.ast.IntInit;
import rs.ac.bg.etf.pp1.ast.Letter;
import rs.ac.bg.etf.pp1.ast.LogicalExpr;
import rs.ac.bg.etf.pp1.ast.MemoryAllocation;
import rs.ac.bg.etf.pp1.ast.MethodDeclaration;
import rs.ac.bg.etf.pp1.ast.MethodName;
import rs.ac.bg.etf.pp1.ast.MethodParamDecl;
import rs.ac.bg.etf.pp1.ast.MethodSign;
import rs.ac.bg.etf.pp1.ast.MinusExpr;
import rs.ac.bg.etf.pp1.ast.NOMinusExpr;
import rs.ac.bg.etf.pp1.ast.NoAfterRet;
import rs.ac.bg.etf.pp1.ast.NoSuperclass;
import rs.ac.bg.etf.pp1.ast.NumberDecl;
import rs.ac.bg.etf.pp1.ast.OptionalOperator;
import rs.ac.bg.etf.pp1.ast.ParentSpecification;
import rs.ac.bg.etf.pp1.ast.PrintFunctionCall;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.ProgramName;
import rs.ac.bg.etf.pp1.ast.ReadFunctionCall;
import rs.ac.bg.etf.pp1.ast.RegularType;
import rs.ac.bg.etf.pp1.ast.ReturnStatement;
import rs.ac.bg.etf.pp1.ast.RightCondition;
import rs.ac.bg.etf.pp1.ast.SimpleDesignator;
import rs.ac.bg.etf.pp1.ast.SimpleExpr;
import rs.ac.bg.etf.pp1.ast.SingleCondTerm;
import rs.ac.bg.etf.pp1.ast.SingleConstDeclaration;
import rs.ac.bg.etf.pp1.ast.SingleExpressionDecl;
import rs.ac.bg.etf.pp1.ast.SingleFact;
import rs.ac.bg.etf.pp1.ast.SingleParameter;
import rs.ac.bg.etf.pp1.ast.SingleTermDecl;
import rs.ac.bg.etf.pp1.ast.SingleVarDecl;
import rs.ac.bg.etf.pp1.ast.SwitchStart;
import rs.ac.bg.etf.pp1.ast.SwitchStatement;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.Terms;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.VarDecl1;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.VoidType;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {

    private final int MAX_NUM_VARS = 65536;
    private final int MAX_NUM_FIELDS = 65536;
    private final int MAX_NUM_LOCALS = 256;

    boolean errorDetected = false;

    Logger log = Logger.getLogger(getClass());

    public void report_error(String message, SyntaxNode info) {
	errorDetected = true;
	StringBuilder msg = new StringBuilder(message);
	int line = (info == null) ? 0 : info.getLine();
	if (line != 0)
	    msg.append(" na liniji ").append(line);
	log.error(msg.toString());
    }

    public void report_info(String message, SyntaxNode info) {
	StringBuilder msg = new StringBuilder(message);
	int line = (info == null) ? 0 : info.getLine();
	if (line != 0)
	    msg.append(" na liniji ").append(line);
	log.info(msg.toString());
    }

    public void visit(Program program) {
	int numOfGlobalVars = SymbolTable.currentScope.getnVars();
	if (numOfGlobalVars > MAX_NUM_VARS) {
	    report_error("Deklarisano je vise globalnih promenljivih nego sto je dozvoljeno", null);
	}
	System.out.println("Broj promenljivih u programu je" + SymbolTable.currentScope.getnVars());
	Obj mainMethod = SymbolTable.find("main");
	if (mainMethod == SymbolTable.noObj || mainMethod.getKind() != MyObject.Meth)
	    report_error("Semanticka Greska! Metoda main nije deklarisana u programu ", null);
	SymbolTable.chainLocalSymbols(program.getProgramName().obj);
	SymbolTable.closeScope();
    }

    public void visit(ProgramName programName) {
	programName.obj = SymbolTable.insert(MyObject.Prog, programName.getProgName(), SymbolTable.noType);
	SymbolTable.openScope();
    }

    public void visit(ClassName className) {
	if (SymbolTable.find(className.getClassName()) != SymbolTable.noObj) {
	    report_error("Vec postoji definisan simbol imena: " + className.getClassName(), className);
	}
	Struct newType = new MyStruct(MyStruct.Class, className.getOptionalParent().struct);
	ObjectFormatter.setDescription(newType, className.getClassName());
	className.obj = SymbolTable.insert(MyObject.Type, className.getClassName(), newType);
	SemanticAnalysisHelper.setClassDeclStart(className.obj);
	SymbolTable.openScope();
    }

    public void visit(NoSuperclass noSuperClass) {
	noSuperClass.struct = SymbolTable.noType;
    }

    public void visit(ClassDecl classDecl) {
	int numOfFields = SymbolTable.currentScope.getnVars();
	if (numOfFields > MAX_NUM_FIELDS) {
	    report_error("Semanticka Greska! Deklarisano je vise polja nego sto je dozvoljeno u klasi: "
		    + classDecl.getClassName().getClassName(), null);
	}
	SemanticAnalysisHelper.classDeclarationEnd();
	SymbolTable.chainLocalSymbols(classDecl.getClassName().obj.getType());
	SymbolTable.closeScope();
    }

    public void visit(ParentSpecification parentSpec) {
	if (parentSpec.getType().struct.getKind() != MyStruct.Class
		&& parentSpec.getType().struct != SymbolTable.noType) {
	    report_error("Semanticka Greska! Izvodi se iz tipa koji nije klasa", parentSpec);
	    parentSpec.struct = SymbolTable.noType;
	} else
	    parentSpec.struct = parentSpec.getType().struct;
    }

    public void visit(Type type) {
	Obj typeNode = SymbolTable.find(type.getTypeName());
	if (typeNode == SymbolTable.noObj) {
	    report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
	    type.struct = SymbolTable.noType;
	} else {
	    if (MyObject.Type == typeNode.getKind()) {
		type.struct = typeNode.getType();
	    } else {
		report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
		type.struct = SymbolTable.noType;
	    }
	}

    }

    public void visit(SingleVarDecl singleVarDecl) {
	SemanticAnalysisHelper.declareVariable(singleVarDecl.getVarName());
    }

    public void visit(VarDecl1 varDecl) {
	SemanticAnalysisHelper.declareAllVariables(varDecl.getType().struct);
	String message = SemanticAnalysisHelper.getErrorInfo();
	if (message != null && !message.equals(""))
	    report_error(message, varDecl);
    }

    public void visit(BoolInit boolInit) {
	boolInit.struct = SymbolTable.boolType;
    }

    public void visit(IntInit intInit) {
	intInit.struct = SymbolTable.intType;
    }

    public void visit(CharInit charInit) {
	charInit.struct = SymbolTable.charType;
    }

    public void visit(SingleConstDeclaration singleConstDecl) {
	SemanticAnalysisHelper.declareConstant(singleConstDecl.getInitializer().struct, singleConstDecl.getConstName());
    }

    public void visit(ConstDecl constDecl) {
	SemanticAnalysisHelper.declareAllConstants(constDecl.getType().struct);
	String message = SemanticAnalysisHelper.getErrorInfo();
	if (message != null && !message.equals("")) {
	    report_error(message, constDecl);
	}
    }

    public void visit(RegularType regularType) {
	regularType.struct = regularType.getType().struct;
    }

    public void visit(VoidType voidType) {
	voidType.struct = SymbolTable.voidType;
    }

    public void visit(ArraySpecifier arraySpec) {
	SemanticAnalysisHelper.setArray_specifier();
    }

    public void visit(MethodName methodName) {
	if (SymbolTable.currentScope.getLocals() != null
		&& SymbolTable.currentScope.getLocals().searchKey(methodName.getMethName()) != null) {
	    report_error(
		    "Semanticka Greska! Metod: " + methodName.getMethName() + " je vec deklarisan u tekucem opsegu ",
		    methodName);
	}
	methodName.obj = SymbolTable.insert(MyObject.Meth, methodName.getMethName(), methodName.getRetType().struct);
	SemanticAnalysisHelper.setMethod_declaration_in_progress(true);
	SemanticAnalysisHelper.setCurrent_method_type(methodName.getRetType().struct);
	SymbolTable.openScope();
    }

    public void visit(MethodParamDecl methParDecl) {
	SemanticAnalysisHelper.declareAllVariables(methParDecl.getType().struct);
	String message = SemanticAnalysisHelper.getErrorInfo();
	if (message != null && !message.equals(""))
	    report_error(message, methParDecl);
    }

    public void visit(FormParDecl formParDecl) {
	SemanticAnalysisHelper.declareVariable(formParDecl.getParamName());
	SemanticAnalysisHelper.addFormalParameter();
    }

    public void visit(MethodDeclaration methodDecl) {

	Obj method = SymbolTable.find(methodDecl.getMethodSign().getMethodName().getMethName());
	SemanticAnalysisHelper.setMethod_declaration_in_progress(false);
	int countThis = 0;
	if (SemanticAnalysisHelper.classDeclInProgress())
	    countThis = 1;
	if ((SymbolTable.currentScope.getnVars() - (method.getLevel() + countThis)) > MAX_NUM_LOCALS)
	    report_error("U metodi je definisano vise lokalnih promenljivih nego sto je dozvoljeno", null);
	System.out.println("Broj lokalnih promenljivih u metodi je: " + SymbolTable.currentScope.getnVars());
	SymbolTable.chainLocalSymbols(method);
	SymbolTable.closeScope();

    }

    public void visit(MethodSign methodSign) {
	Obj method = SymbolTable.find(methodSign.getMethodName().getMethName());
	method.setLevel(SemanticAnalysisHelper.getFormParametersNum());
	SemanticAnalysisHelper.resetFormParametersNum();
	if (SemanticAnalysisHelper.classDeclInProgress()) {
	    SymbolTable.insert(Obj.Var, "this", SemanticAnalysisHelper.getCurrentClassType());
	}
    }

    public void visit(AccessField accessField) {
	SemanticAnalysisHelper.buildDesigntor(accessField.getVarName());
    }

    public void visit(AccessElement accessElement) {
	SemanticAnalysisHelper.setArraySpecToDesignator();
	if (!accessElement.getExpr().struct.equals(SymbolTable.intType)) {
	    report_error("Semanticka Greska! Tip izraza za indeksiranje elementa nije int kako je ocekivano",
		    accessElement.getExpr());
	}
    }

    public void visit(DesignatorName designName) {
	SemanticAnalysisHelper.startDesignatorBuilding(designName.getVarName());
    }

    public void visit(DesignatorDecl designDecl) {
	designDecl.obj = SemanticAnalysisHelper.processDesignator();
	if (designDecl.obj == null || designDecl.obj == SymbolTable.noObj
		|| SemanticAnalysisHelper.getNotArrayIndexingError()
		|| SemanticAnalysisHelper.getArrayNotIndexedError()) {
	    report_error(SemanticAnalysisHelper.getErrorInfo(), designDecl);
	    designDecl.obj = SymbolTable.noObj;
	} else
	    report_info(SemanticAnalysisHelper.getErrorInfo(), designDecl);
    }

    public void visit(SimpleDesignator simpleDes) {
	int designatorKind = simpleDes.getDesignator().obj.getKind();
	if (designatorKind != Obj.Fld && designatorKind != Obj.Var && designatorKind != Obj.Con) {
	    report_error("Semanticka Greska! pogresna upotreba simbola", simpleDes);
	    simpleDes.struct = SymbolTable.noType;
	} else if (simpleDes.getDesignator().obj.getType().getKind() == MyStruct.Array
		&& !SemanticAnalysisHelper.shouldLeaveArray())
	    simpleDes.struct = simpleDes.getDesignator().obj.getType().getElemType();
	else
	    simpleDes.struct = simpleDes.getDesignator().obj.getType();
    }

    public void visit(MemoryAllocation memAllocation) {
	if (memAllocation.getType().struct == SymbolTable.noType) {
	    report_error("Semanticka Greska! Klasa: " + memAllocation.getType().getTypeName() + "nije definisana",
		    memAllocation);
	}

	if (SemanticAnalysisHelper.isArray_allocation()) {
	    memAllocation.struct = SemanticAnalysisHelper.arrayType(memAllocation.getType().struct);
	} else
	    memAllocation.struct = memAllocation.getType().struct;
    }

    public void visit(SingleExpressionDecl singleExprDecl) {
	SemanticAnalysisHelper.setArray_allocation();
	if (!singleExprDecl.getExpr().struct.equals(SymbolTable.intType)) {
	    report_error("Ocekivan izraz tipa int", singleExprDecl);
	}
    }

    public void visit(NumberDecl numberDecl) {
	numberDecl.struct = SymbolTable.intType;
    }

    public void visit(Letter letter) {
	letter.struct = SymbolTable.charType;
    }

    public void visit(BoolValue boolVal) {
	boolVal.struct = SymbolTable.boolType;
    }

    public void visit(ExpressionFactor exprFactor) {
	exprFactor.struct = exprFactor.getExpr().struct;
    }

    public void visit(SingleTermDecl singleTerm) {
	singleTerm.struct = singleTerm.getFactor().struct;
    }

    public void visit(CompositeTerm compositeTerm) {
	if (compositeTerm.getFactor().struct != SymbolTable.intType
		|| compositeTerm.getTerm().struct != SymbolTable.intType) {
	    report_error("Semanticka Greska! Operacije mnozenja i deljenja se mogu primeniti samo na tip int ",
		    compositeTerm);
	    compositeTerm.struct = SymbolTable.noType;
	} else
	    compositeTerm.struct = SymbolTable.intType;
    }

    public void visit(FirstTerm firstTerm) {
	firstTerm.struct = firstTerm.getTerm().struct;
    }

    public void visit(Terms terms) {
	if (terms.getTermList().struct != SymbolTable.intType || terms.getTerm().struct != SymbolTable.intType) {
	    report_error("Semanticka greska! Operacije sabiranja i oduzivanja se mogu primeniti samo na tipu int ",
		    terms);
	    terms.struct = SymbolTable.noType;
	} else
	    terms.struct = SymbolTable.intType;

    }

    public void visit(MinusExpr minusExpr) {
	if (minusExpr.getTermList().struct != SymbolTable.intType) {
	    report_error("Operator - se moze primeniti samo na tip int ", minusExpr);
	    minusExpr.struct = SymbolTable.noType;
	} else
	    minusExpr.struct = minusExpr.getTermList().struct;
    }

    public void visit(NOMinusExpr noMinusExpr) {
	noMinusExpr.struct = noMinusExpr.getTermList().struct;
    }

    public void visit(SimpleExpr simpleExpr) {
	simpleExpr.struct = simpleExpr.getExpr1().struct;
    }

    public void visit(ClassComp classComp) {
	SemanticAnalysisHelper.setClassComparison();
    }

    public void visit(OptionalOperator optComparisonOperator) {
	if (!optComparisonOperator.getExpr1().struct.compatibleWith(optComparisonOperator.getExpr11().struct)
		|| ((optComparisonOperator.getExpr1().struct.isRefType()
			|| optComparisonOperator.getExpr11().struct.isRefType())
			&& !SemanticAnalysisHelper.canCompareClasses())) {
	    report_error(
		    "Semanticka Greska! tipovi operanada relacionog operatora moraju biti kompatibilni i za nizove i klase se smeju koristiti samo != i ==",
		    optComparisonOperator);
	    optComparisonOperator.struct = SymbolTable.noType;
	} else
	    optComparisonOperator.struct = SymbolTable.boolType;
    }

    public void visit(LogicalExpr logicalExpr) {
	logicalExpr.struct = logicalExpr.getExpr1().struct;
    }

    public void visit(ConditionTerm condTerm) {
	condTerm.struct = SymbolTable.boolType;
    }

    public void visit(SingleFact singleFact) {
	singleFact.struct = singleFact.getCondFact().struct;
    }

    public void visit(ConditionExpr condExpr) {
	condExpr.struct = SymbolTable.boolType;
    }

    public void visit(SingleCondTerm singleCondTerm) {
	singleCondTerm.struct = singleCondTerm.getCondTerm().struct;
    }

    public void visit(FunctionCallDesign fcDesign) {
	if (fcDesign.getDesignator().obj.getKind() != Obj.Meth) {
	    report_error("Greska prilikom poziva funkcije specificirani simbol nije funkcija ", fcDesign);
	}
	int parameterCheck = SemanticAnalysisHelper.checkParams(fcDesign.getDesignator().obj);
	if (parameterCheck == SemanticAnalysisHelper.DIFFERENT_PARAM_NUMBER) {
	    report_error(
		    "Semanticka Greska! prilikom poziva funkcije, prosledjen broj argumenata se razlikuje od ocekivanog  ocekivano: "
			    + fcDesign.getDesignator().obj.getLevel() + " prosledjeno:"
			    + SemanticAnalysisHelper.getSpecifiedNumOfParams(),
		    fcDesign);
	    fcDesign.struct = SymbolTable.noType;
	} else if (parameterCheck == SemanticAnalysisHelper.DIFFERENT_TYPES) {
	    report_error(
		    "Semanticka Greska! prilikom poziva funkcije, tipovi prosledjenih parametara se razlikuju od ocekivanih",
		    fcDesign);
	    fcDesign.struct = SymbolTable.noType;
	} else
	    fcDesign.struct = fcDesign.getDesignator().obj.getType();
    }

    public void visit(SingleParameter singlePar) {
	SemanticAnalysisHelper.insertParam(singlePar.getExpr().struct);
    }

    public void visit(AssignStatementSt assignStmt) {
	Struct type = assignStmt.getDesignator().obj.getType();
	if (assignStmt.getDesignator().obj.getType().getKind() == Struct.Array
		&& !SemanticAnalysisHelper.shouldLeaveArray())
	    type = type.getElemType();
	if (assignStmt.getDesignator().obj.getKind() != MyObject.Fld
		&& assignStmt.getDesignator().obj.getKind() != MyObject.Var)
	    report_error(
		    "Semanticka Greska! Simbol sa leve strane operatora dodele mora biti promenljiva, element niza ili polje unutar objekta",
		    assignStmt);
	else if (!assignStmt.getAssignStatement().struct.assignableTo(type)
		&& !(assignStmt.getAssignStatement().struct.getKind() == MyStruct.Array
			&& type.getKind() == MyStruct.Array
			&& assignStmt.getAssignStatement().struct.getElemType().assignableTo(type.getElemType()))) {
	    report_error("Semanticka Greska! Tipovi nisu kompatibilni pri dodeli", assignStmt);
	}
    }

    public void visit(AssignStm assignStm) {
	assignStm.struct = assignStm.getExpr().struct;
    }

    public void visit(IncrementSt increment) {
	Struct type = increment.getDesignator().obj.getType();
	if (increment.getDesignator().obj.getType().getKind() == Struct.Array
		&& !SemanticAnalysisHelper.shouldLeaveArray())
	    type = type.getElemType();

	if (increment.getDesignator().obj.getKind() != MyObject.Fld
		&& increment.getDesignator().obj.getKind() != MyObject.Var)
	    report_error(
		    "Semanticka Greska! Operand kod inkrementa mora biti promenljiva, element niza ili polje objekta unutrasnje klase",
		    increment);
	else if (type != SymbolTable.intType) {
	    report_error("Semanticka Greska! Nedozvoljen tip, ocekivan je tip int pri operaciji inkrementiranja",
		    increment);

	}
    }

    public void visit(DecrementSt decrement) {
	Struct type = decrement.getDesignator().obj.getType();
	if (decrement.getDesignator().obj.getType().getKind() == Struct.Array
		&& !SemanticAnalysisHelper.shouldLeaveArray())
	    type = type.getElemType();
	if (decrement.getDesignator().obj.getKind() != MyObject.Fld
		&& decrement.getDesignator().obj.getKind() != MyObject.Var)
	    report_error(
		    "Semanticka Greska! Operand kod dekrementa mora biti promenljiva, element niza ili polje objekta unutrasnje klase",
		    decrement);
	else if (type != SymbolTable.intType) {
	    report_error("Semanticka Greska! Nedozvoljen tip, ocekivan je tip int pri operaciji dekrementiranja",
		    decrement);

	}
    }

    public void visit(CondExpr condExpr) {
	if (!condExpr.getExpr().struct.compatibleWith(condExpr.getExpr1().struct)) {
	    report_error("Semanticka Greska! Drugi i treci izraz kod ternarnog operatora moraju biti istog tipa ",
		    condExpr);
	    condExpr.struct = SymbolTable.noType;
	} else
	    condExpr.struct = condExpr.getExpr().struct == SymbolTable.nullType ? condExpr.getExpr1().struct
		    : condExpr.getExpr().struct;
    }

    public void visit(DoWhileStart doWhileStart) {
	SemanticAnalysisHelper.enterDoWhile();
    }

    public void visit(SwitchStart switchStart) {
	SemanticAnalysisHelper.enterSwitch();
    }

    public void visit(BreakStatement breakStmt) {
	if (!SemanticAnalysisHelper.insideDoWhile() && !SemanticAnalysisHelper.inisideSwitch())
	    report_error("Semanticka Greska! Break se mora nalaziti unutar do-while petlje ili naredbe switch",
		    breakStmt);
    }

    public void visit(ContinueStatement continueStmt) {
	if (!SemanticAnalysisHelper.insideDoWhile())
	    report_error("Semanticka Greska! continue se mora nalaziti unutar do-while petlje", continueStmt);
    }

    public void visit(ReadFunctionCall readFunc) {
	Struct desType = (readFunc.getDesignator().obj.getType().getKind() == MyStruct.Array
		&& !SemanticAnalysisHelper.shouldLeaveArray()) ? readFunc.getDesignator().obj.getType().getElemType()
			: readFunc.getDesignator().obj.getType();
	if (readFunc.getDesignator().obj.getKind() != MyObject.Fld
		&& readFunc.getDesignator().obj.getKind() != MyObject.Var)
	    report_error(
		    "Semanticka Greska! Argument funkcije read mora oznacavati promenljivu, polje unutar objekta ili element niza",
		    readFunc);
	else if (desType != SymbolTable.boolType && desType != SymbolTable.intType && desType != SymbolTable.charType)
	    report_error("Semanticka Greska! Argument funkcije read mora biti tipa int, bool ili char ", readFunc);
    }

    public void visit(PrintFunctionCall printFuncCall) {
	if (printFuncCall.getExpr().struct != SymbolTable.intType
		&& printFuncCall.getExpr().struct != SymbolTable.boolType
		&& printFuncCall.getExpr().struct != SymbolTable.charType) {
	    report_error("Semanticka Greska! Izraz argument print funkcije mora biti tipa int bool ili char",
		    printFuncCall);
	}
    }

    public void visit(ExprAfterReturn exprAfterRet) {
	exprAfterRet.struct = exprAfterRet.getExpr().struct;
    }

    public void visit(NoAfterRet noAfterRet) {
	noAfterRet.struct = SymbolTable.voidType;
    }

    public void visit(ReturnStatement retStmt) {
	if (!SemanticAnalysisHelper.isMethod_declaration_in_progress())
	    report_error("Semanticka Greska! Return naredba se moze naci samo u okviru funkcije ", retStmt);
	else if (SemanticAnalysisHelper.getCurrent_method_type() != retStmt.getOptionalAfterReturn().struct)
	    report_error(
		    "Semanticka Greska! Tip izraza iza naredbe return mora odgovarati tipu povratne vrednosti funkcije",
		    retStmt);
    }

    public void visit(RightCondition condition) {
	if (condition.getCondition().struct != SymbolTable.boolType)
	    report_error("Semanticka Greska! Uslov kod if naredbe mora biti tipa bool", condition);
    }

    public void visit(DoWhileLoop doWhile) {
	if (doWhile.getCondition().struct != SymbolTable.boolType)
	    report_error("Semanticka Greska! Tip uslova kod do-while petlje mora biti bool", doWhile);
	SemanticAnalysisHelper.exitDoWhile();
    }

    public void visit(SwitchStatement switchStmt) {
	if (switchStmt.getExpr().struct != SymbolTable.intType)
	    report_error("Semanticka Greska! Izraz u okviru switch naredbe mora biti tipa int ", switchStmt);
	SemanticAnalysisHelper.exitSwitch();
    }

    public void visit(FuncCallSt funcCallStmt) {
	if (funcCallStmt.getDesignator().obj.getKind() != Obj.Meth) {
	    report_error("Greska prilikom poziva funkcije specificirani simbol nije funkcija ", funcCallStmt);
	}
	int parameterCheck = SemanticAnalysisHelper.checkParams(funcCallStmt.getDesignator().obj);
	if (parameterCheck == SemanticAnalysisHelper.DIFFERENT_PARAM_NUMBER) {
	    report_error(
		    "Semanticka Greska! prilikom poziva funkcije, prosledjen broj argumenata se razlikuje od ocekivanog ocekivano: "
			    + funcCallStmt.getDesignator().obj.getLevel() + " prosledjeno: "
			    + SemanticAnalysisHelper.getSpecifiedNumOfParams(),
		    funcCallStmt);
	} else if (parameterCheck == SemanticAnalysisHelper.DIFFERENT_TYPES) {
	    report_error(
		    "Semanticka Greska! prilikom poziva funkcije, tipovi prosledjenih parametara se razlikuju od ocekivanih",
		    funcCallStmt);
	}
    }

    public boolean passed() {
	return !errorDetected;
    }

}
