package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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
    
    private static Map<Struct, Struct> definedArrays = new HashMap<>();
    private static List<Variable> variablesToDeclare = new ArrayList<>();
    private static boolean array_specifier = false;
    private static boolean array_allocation = false;
    private static Obj current_class = null;
    private static Stack<List<Struct>> actualParamList = new Stack<>();
    private static boolean class_declaration_in_progress = false;
    private static String error_info = null;
    private static int nested_do_while = 0;
    private static int nested_switch = 0;
    private static boolean method_declaration_in_progress = false;
    private static Struct current_method_type;
    private static class Constant {
	public Struct initializer;
	public String constantName;
    public int constValue;
	
    
    
	public Constant(int value) {
	    this.constValue = value;
	 }
    }
    private static List<Constant> constantsToDeclare = new ArrayList<>();
    private static int form_parameters_num = 0;
    private static boolean class_comparison = false;

    
    public static void makeNewConstant(int value) {
    	constantsToDeclare.add(new Constant(value));
    }
    public static void declareConstant(Struct initializer, String constName) {
	Constant constantToDeclare = constantsToDeclare.get(constantsToDeclare.size() - 1);
	constantToDeclare.constantName = constName;
	constantToDeclare.initializer = initializer;
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
	    else {
		Obj new_constant = SymbolTable.insert(Obj.Con, constant.constantName, type);
		new_constant.setAdr(constant.constValue);
	    }
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

    public static boolean isArray_allocation() {
	boolean isArrayAlloc = array_allocation;
	array_allocation = false;
	return isArrayAlloc;
    }

    public static void setArray_allocation() {
	SemanticAnalysisHelper.array_allocation = true;
    }

    public static void pushNewParamList() {
    	actualParamList.push(new ArrayList<>());
    }
    
    public static void popParamList() {
    	actualParamList.pop();
    }
    
    public static void insertParam(Struct paramType) {
    	List<Struct> actualParam = actualParamList.peek();
    	actualParam.add(paramType);
    }

    public static int DIFFERENT_PARAM_NUMBER = -1;
    public static int DIFFERENT_TYPES = -2;
    private static int specified_num_of_params;

    public static int checkParams(Obj method) {
    if(actualParamList.empty()) {
    	return DIFFERENT_PARAM_NUMBER;
    }
    List<Struct> actualParams = actualParamList.pop();
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
    private static int global_offset = 0;
	private static Map<Struct, Integer> classes_to_offsets = new HashMap<>();
    
    public static Map<Struct, Integer> getOffsets(){ 	
		return classes_to_offsets;
    }
    
    public static int getOffset(Obj my_class) {
    	return classes_to_offsets.get(my_class.getType());
    }
    
 
    
    public static void insertClassOffset(Obj class_to_insert) {
    	Map<String, Boolean> method_overriden = new HashMap<>();
    	
        classes_to_offsets.put(class_to_insert.getType(), global_offset);
        Struct current_type = class_to_insert.getType();
        while(current_type != SymbolTable.noType && current_type != null) {
			Collection<Obj>local_symbols = current_type.getMembers();
			for(Obj current_obj : local_symbols) {
				if(current_obj.getKind() == MyObject.Meth) {
					if(!method_overriden.containsKey(current_obj.getName())) {
					global_offset+=current_obj.getName().length() + 2;
					method_overriden.put(current_obj.getName(), true);
					}
				}
			}
			current_type = current_type.getElemType();
		}
        global_offset++;
    }
    
    public static int getGlobalOffset() {
    	return global_offset;
    }
}
