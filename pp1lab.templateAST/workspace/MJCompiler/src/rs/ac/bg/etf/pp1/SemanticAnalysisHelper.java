package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class SemanticAnalysisHelper {

    private static class Variable {
	public String varName;
	public boolean arraySpec;

	Variable(String varName, boolean arraySpec) {
	    this.varName = varName;
	    this.arraySpec = arraySpec;
	}
    }

    private static boolean DEFAULT_ARRAY_SPEC = false;

    private static Map<Struct, Struct> definedArrays = new HashMap<>();

    private static List<Variable> variablesToDeclare = new ArrayList<>();
    private static boolean array_specifier = false;
    private static List<List<Variable>> designatorStructureList = new ArrayList<>();
    private static boolean not_array_indexing_error = false;
    private static boolean array_not_indexed_error = false;
    private static boolean array_allocation = false;
    private static Obj current_class = null;

    private static List<Struct> actualParams = new ArrayList<>();
    private static boolean class_declaration_in_progress = false;
    private static String error_info = null;
    private static int nested_do_while = 0;
    private static int nested_switch = 0;
    private static boolean method_declaration_in_progress = false;
    private static Struct current_method_type;
    private static boolean leaveArray = false;

    private static class Constant {
	public Struct initializer;
	public String constantName;

	public Constant(Struct initializer, String constName) {
	    this.initializer = initializer;
	    this.constantName = constName;
	}
    }

    private static List<Constant> constantsToDeclare = new ArrayList<>();
    private static int form_parameters_num = 0;
    private static boolean class_comparison = false;

    public static void declareConstant(Struct initializer, String constName) {
	constantsToDeclare.add(new Constant(initializer, constName));
    }

    public static void declareAllConstants(Struct type) {
	StringBuilder error_message = new StringBuilder();
	for (int i = 0; i < constantsToDeclare.size(); i++) {
	    Constant constant = constantsToDeclare.get(i);
	    if (!constant.initializer.equals(type)) {
		error_message.append(
			"Inicijalizaciona konstanta za simbol: " + constant.constantName + " nije odgovarajuceg tipa");
	    } else if (SymbolTable.currentScope.getLocals() != null
		    && SymbolTable.currentScope.getLocals().searchKey(constant.constantName) != null)
		error_message.append(
			"Semanticka Greska! Simbol: " + constant.constantName + "je vec deklarisan u tekucem opsegu ");
	    else
		SymbolTable.insert(Obj.Con, constant.constantName, type);
	}
	constantsToDeclare.clear();
	error_info = error_message.toString();
    }

    public static void declareVariable(String varName) {
	variablesToDeclare.add(new Variable(varName, array_specifier));
	array_specifier = false;
    }

    public static void declareAllVariables(Struct type) {
	StringBuilder error_message = new StringBuilder();
	for (int i = 0; i < variablesToDeclare.size(); i++) {
	    Variable var = variablesToDeclare.get(i);
	    Struct declType = type;
	    if (var.arraySpec) {
		if (checkIfArrayTypeDefined(type))
		    declType = getArrayType(type);
		else {
		    defineArrayType(type);
		    declType = getArrayType(type);
		}
	    }
	    int varType = Obj.Var;
	    if (!method_declaration_in_progress && class_declaration_in_progress)
		varType = Obj.Fld;
	    if (SymbolTable.currentScope.getLocals() != null
		    && SymbolTable.currentScope.getLocals().searchKey(var.varName) != null)
		error_message
			.append("Semanticka Greska! Simbol: " + var.varName + " je vec deklarisan u tekucem opsegu ");
	    else
		SymbolTable.insert(varType, var.varName, declType);
	}
	variablesToDeclare.clear();
	error_info = error_message.toString();
    }

    public static int getFormParametersNum() {
	return form_parameters_num;
    }

    public static void addFormalParameter() {
	form_parameters_num++;
    }

    public static void setFormParametersNum(int num) {
	form_parameters_num = num;
    }

    public static void resetFormParametersNum() {
	form_parameters_num = 0;
    }

    public static boolean isArray_specifier() {
	return array_specifier;
    }

    public static void setArray_specifier() {
	SemanticAnalysisHelper.array_specifier = true;
    }

    private static boolean checkIfArrayTypeDefined(Struct type) {
	return definedArrays.containsKey(type);
    }

    public static Struct arrayType(Struct type) {
	if (type == SymbolTable.noType)
	    return SymbolTable.noType;
	if (checkIfArrayTypeDefined(type)) {
	    return getArrayType(type);
	}
	defineArrayType(type);
	return getArrayType(type);
    }

    private static Struct getArrayType(Struct type) {
	if (!definedArrays.containsKey(type))
	    return SymbolTable.noType;
	else
	    return definedArrays.get(type);
    }

    private static void defineArrayType(Struct type) {
	Struct newArrayType = new MyStruct(Struct.Array, type);
	definedArrays.put(type, newArrayType);
	StringBuilder buildDescription = new StringBuilder();
	buildDescription.append("Array of " + ObjectFormatter.getDescription(type));
	ObjectFormatter.setDescription(newArrayType, buildDescription.toString());
    }

    public static void setArraySpecToDesignator() {
	List<Variable> desStructure = designatorStructureList.get(designatorStructureList.size() - 1);
	desStructure.get(desStructure.size() - 1).arraySpec = true;
    }

    public static void startDesignatorBuilding(String varName) {
	List<Variable> desStructure = new ArrayList<>();
	designatorStructureList.add(desStructure);
	desStructure.add(new Variable(varName, DEFAULT_ARRAY_SPEC));
    }

    public static void buildDesigntor(String varName) {
	List<Variable> desStructure = designatorStructureList.get(designatorStructureList.size() - 1);
	desStructure.add(new Variable(varName, DEFAULT_ARRAY_SPEC));
    }

    public static Obj processDesignator() {
	StringBuilder sb = new StringBuilder();
	List<Variable> designatorStructure = designatorStructureList.get(designatorStructureList.size() - 1);
	Variable firstVar = designatorStructure.get(0);
	Obj designator = SymbolTable.find(firstVar.varName);
	SymbolDataStructure class_members;
	if (designator == SymbolTable.noObj && class_declaration_in_progress) {
	    Struct designator_type = current_class.getType();
	    while ((designator == SymbolTable.noObj || designator == null) && designator_type.getElemType() != null
		    && designator_type.getElemType() != SymbolTable.noType) {
		designator_type = designator_type.getElemType();
		class_members = designator_type.getMembers();
		designator = class_members != null ? class_members.searchKey(firstVar.varName) : null;
	    }
	}
	if (designator == SymbolTable.noObj || designator == null) {
	    sb.append("Semanticka Greska! Promenljiva " + firstVar.varName + " ne postoji u tabeli simbola");
	    error_info = sb.toString();
	    designatorStructureList.remove(designatorStructure);
	    return designator;
	} else if (designator.getType().getKind() != Struct.Array && firstVar.arraySpec) {
	    not_array_indexing_error = true;
	    sb.append(" Semanticka greska! Indeksiran je simbol " + firstVar.varName + " koji nije nizovnog tipa ");
	} else if (designator.getType().getKind() == Struct.Array && !firstVar.arraySpec
		&& designatorStructure.size() > 1) {
	    array_not_indexed_error = true;
	    sb.append(" Semanticka Greska! Simbol nizovnog tipa " + firstVar.varName + " nije indeksiran ");
	} else
	    sb.append("Prisput simbolu: " + ObjectFormatter.getObjectDescription(designator));

	for (int i = 1; i < designatorStructure.size(); i++) {
	    if (designator.getType().getKind() != MyStruct.Class
		    && designator.getType().getElemType().getKind() != MyStruct.Class) {
		sb.append(" Semanticka Greska! Pokusaj prisputa polju promenljive " + designator.getName()
			+ " koja nije klasnog tipa \n" + designator.getType().getKind());
		error_info = sb.toString();
		designatorStructureList.remove(designatorStructure);
		return designator;
	    }
	    Variable var = designatorStructure.get(i);
	    SymbolDataStructure members;
	    Struct type = (designator.getType().getKind() == Struct.Array) ? designator.getType().getElemType()
		    : designator.getType();
	    if (classDeclInProgress() && type.equals(getCurrentClassType()))
		members = SymbolTable.currentScope.getOuter().getLocals();
	    else
		members = type.getMembers();

	    designator = members != null ? members.searchKey(var.varName) : null;

	    while (designator == null && type.getElemType() != null && type.getElemType() != SymbolTable.noType) {
		type = type.getElemType();
		members = type.getMembers();
		designator = members != null ? members.searchKey(var.varName) : null;
	    }
	    if (designator == null || designator == SymbolTable.noObj) {
		sb.append(" Semanticka Greska! Prisput polju " + var.varName
			+ " koje ne postoji u specificiranoj klasi niti u roditeljskim klasama");
		error_info = sb.toString();
		designatorStructureList.remove(designatorStructure);
		return designator;
	    }
	    if (designator.getType().getKind() != Struct.Array && var.arraySpec) {
		not_array_indexing_error = true;
		sb.append(" Semanticka greska! Indeksiran je simbol " + var.varName + " koji nije nizovnog tipa ");
	    } else if (designator.getType().getKind() == Struct.Array && !var.arraySpec
		    && i < designatorStructure.size() - 1) {
		array_not_indexed_error = true;
		sb.append(" Semanticka Greska! Simbol nizovnog tipa " + var.varName + " nije indeksiran ");
	    } else
		sb.append(", pristup njegovom polju: " + ObjectFormatter.getObjectDescription(designator));

	}
	error_info = sb.toString();
	if (!designatorStructure.get(designatorStructure.size() - 1).arraySpec
		&& designator.getType().getKind() == MyStruct.Array)
	    leaveArray = true;
	designatorStructureList.remove(designatorStructure);
	return designator;
    }

    public static boolean getNotArrayIndexingError() {
	boolean error_occured = not_array_indexing_error;
	not_array_indexing_error = false;
	return error_occured;
    }

    public static boolean getArrayNotIndexedError() {
	boolean error_occured = array_not_indexed_error;
	array_not_indexed_error = false;
	return error_occured;
    }

    public static boolean isArray_allocation() {
	boolean isArrayAlloc = array_allocation;
	array_allocation = false;
	return isArrayAlloc;
    }

    public static void setArray_allocation() {
	SemanticAnalysisHelper.array_allocation = true;
    }

    public static void insertParam(Struct paramType) {
	actualParams.add(paramType);
    }

    public static int DIFFERENT_PARAM_NUMBER = -1;
    public static int DIFFERENT_TYPES = -2;
    private static int specified_num_of_params;

    public static int checkParams(Obj method) {
	if (method.getLevel() != actualParams.size()) {
	    specified_num_of_params = actualParams.size();
	    actualParams.clear();
	    return DIFFERENT_PARAM_NUMBER;
	}
	Collection<Obj> locals = method.getLocalSymbols();
	Iterator<Obj> iterator = locals.iterator();
	int paramNumber = 0;
	while (paramNumber < actualParams.size()) {
	    Struct param_type = iterator.next().getType();
	    if (!(actualParams.get(paramNumber).assignableTo(param_type))
		    && !(actualParams.get(paramNumber).getKind() == MyStruct.Array
			    && param_type.getKind() == MyStruct.Array
			    && actualParams.get(paramNumber).getElemType().assignableTo(param_type.getElemType()))) {
		actualParams.clear();
		return DIFFERENT_TYPES;
	    }
	    paramNumber++;
	}
	actualParams.clear();
	return 0;
    }

    public static int getSpecifiedNumOfParams() {
	int params = specified_num_of_params;
	specified_num_of_params = 0;
	return params;
    }

    public static void setClassDeclStart(Obj class_to_declare) {
	current_class = class_to_declare;
	class_declaration_in_progress = true;
    }

    public static void classDeclarationEnd() {
	current_class = null;
	class_declaration_in_progress = false;
    }

    public static boolean classDeclInProgress() {
	return class_declaration_in_progress;
    }

    public static String getErrorInfo() {
	String error = error_info;
	error_info = null;
	return error;
    }

    public static Struct getCurrentClassType() {
	if (current_class != null) {
	    return current_class.getType();
	}
	return SymbolTable.noType;
    }

    public static void setClassComparison() {
	class_comparison = true;
    }

    public static boolean canCompareClasses() {
	boolean can_compare = class_comparison;
	class_comparison = false;
	return can_compare;
    }

    public static boolean insideDoWhile() {
	return nested_do_while > 0;
    }

    public static void enterDoWhile() {
	nested_do_while++;
    }

    public static void exitDoWhile() {
	nested_do_while--;
    }

    public static boolean inisideSwitch() {
	return nested_switch > 0;
    }

    public static void enterSwitch() {
	nested_switch++;
    }

    public static void exitSwitch() {
	nested_switch--;
    }

    public static boolean isMethod_declaration_in_progress() {
	return method_declaration_in_progress;
    }

    public static void setMethod_declaration_in_progress(boolean method_declaration_in_progress) {
	SemanticAnalysisHelper.method_declaration_in_progress = method_declaration_in_progress;
    }

    public static Struct getCurrent_method_type() {
	return current_method_type;
    }

    public static void setCurrent_method_type(Struct current_method_type) {
	SemanticAnalysisHelper.current_method_type = current_method_type;
    }

    public static boolean shouldLeaveArray() {
	boolean leave = leaveArray;
	leaveArray = false;
	return leave;
    }
}
