package rs.ac.bg.etf.pp1;

import java.util.Collection;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class CodeGenerator extends VisitorAdaptor {

	private int main_pc;
	private Stack<Integer> operation_code_stack = new Stack<>();
	private Stack<Integer> break_jump_address = new Stack<>();
	private Stack<Integer> continue_jump_address = new Stack<>();
	private Stack<Integer> else_jump_address = new Stack<>();
	boolean class_decl_in_progress = false;
	boolean method_decl_in_progress = false;
	private int current_method_start_adr = 0;
	private Stack<Obj> designator_stack = new Stack<>();
	private Stack<Integer> jump_to_trap_addresses = new Stack<>();
	
	public void visit(MethodName methodName) {
		current_method_start_adr = Code.pc;
		methodName.obj.setAdr(Code.pc);
		method_decl_in_progress = true;
		int var_count = 0;
		Collection<Obj> locals = methodName.obj.getLocalSymbols();
		var_count = locals.size();
		Code.put(Code.enter);
		Code.put(methodName.obj.getLevel());
		int var_num = var_count - methodName.obj.getLevel();
		Code.put(var_num);
	}
	
	public void visit(ClassName className) {
		Obj parent = className.getOptionalParent().obj;
		int start_address = parent.getLevel();
		SymbolDataStructure members = parent.getType().getMembers();
		Collection<Obj> locals = members.symbols();
		int field_num = 0;
		for(Obj class_member: locals) {
			if(class_member.getKind() == MyObject.Fld) {
				field_num++;
				class_member.setAdr(class_member.getAdr() + start_address);
			}
		}
		className.obj.setLevel(start_address + field_num);
	}
	
	public void visit(NumberDecl numberDecl) {
		Obj numeric_constant = new Obj(MyObject.Con, "numeric_const", SymbolTable.intType);
		numeric_constant.setAdr(numberDecl.getConstValue());
		Code.load(numeric_constant);
	}
	
	public void visit(Letter letter) {
		Obj char_constant = new Obj(MyObject.Con, "numeric_const", SymbolTable.charType);
		char_constant.setAdr(letter.getConstValue().charAt(1));
		Code.load(char_constant);
	}
	
	public void visit(BoolValue boolValue) {
	   int value = boolValue.getBoolInitializer().getClass() == FalseValue.class ? 0 : 1;
	   Obj bool_constant = new MyObject(MyObject.Con, "bool_const", SymbolTable.boolType);
	   bool_constant.setAdr(value);
	   Code.load(bool_constant);
	}
	
	public void visit(MemoryAllocation memAllocation) {
		if(memAllocation.struct.getKind() != MyStruct.Array) {
			Code.put(Code.new_);
			//jedna rec vise zbog pokazivaca na tabelu virtuelnih funkcija
			Code.put2(memAllocation.getAllocationType().obj.getLevel() + 1);
		}
	}
	
	public void visit(OptionalExpression optExpr) {
		Code.put(Code.newarray);
		MemoryAllocation memAlloc = (MemoryAllocation) optExpr.getParent();
		int array_dimensions = memAlloc.getAllocationType().getType().struct == SymbolTable.charType ? 0:1;
		Code.put(array_dimensions);
	}
	
	//potrebno jos doraditi
	public void visit(FunctionCallDesign funcCallDesign) {
		Code.put(Code.call);
		//ako se ne poziva za objekat klase
		int jump_address = funcCallDesign.getDesignator().obj.getAdr();
		Code.put2(jump_address - Code.pc);
	}
	
	public void visit(Addition addition) {
		operation_code_stack.push(Code.add);
	}
	
	public void visit(Subtraction subtr) {
		operation_code_stack.push(Code.sub);
	}
	
	public void visit(Multiplication mul) {
		operation_code_stack.push(Code.mul);
	}
	
	public void visit(Division division) {
		operation_code_stack.push(Code.div);
	}
	
	public void visit(Remaining rem) {
		operation_code_stack.push(Code.rem);
	}
	
	public void visit(ComparisonEqual compEqu) {
		operation_code_stack.push(Code.jcc + Code.eq);
	}
	
	public void visit(ComparionDiffernet comparisonDiff) {
		operation_code_stack.push(Code.jcc + Code.ne);
	}
	
	public void visit(ComparisonGrater grater) {
		operation_code_stack.push(Code.jcc + Code.gt);
	}
	
	public void visit(CompGraterEqual compGrEq) {
		operation_code_stack.push(Code.jcc + Code.ge);
	}
	
	public void visit(ComparisonLess less) {
		operation_code_stack.push(Code.jcc + Code.lt);
	}
	
	public void visit(ComparisonLessEqual comparisonLess) {
		operation_code_stack.push(Code.jcc + Code.le);
	}
	
	public void visit(CompositeTerm compositeTerm) {
		Code.put(operation_code_stack.pop());
	}
	
	public void visit(Terms terms) {
		Code.put(operation_code_stack.pop());
	}
	
	public void visit(MinusExpr minusExpr) {
		Code.put(Code.neg);
	}
	
	public void visit(OptionalOperator optionalOp) {
		Code.put(operation_code_stack.pop());
		int address_to_fill = Code.pc;
		Code.put2(0);
		Code.put(Code.const_);
		Code.put(Code.jmp);
		int jump_address = Code.pc;
		Code.put2(0);
		Code.put2(address_to_fill, address_to_fill - Code.pc);
		Code.put(Code.const_1);
		Code.put2(jump_address, jump_address - Code.pc);
	}
	
	public void visit(ConditionTerm condTerm) {
		Code.put(Code.const_);
		Code.put(Code.jcc + Code.eq);
		int address_to_fill = Code.pc;
		Code.put2(0);
		Code.put(Code.const_);
		Code.put(Code.jcc + Code.eq);
		int address_to_fill_2 = Code.pc;
		Code.put2(0);
		Code.put(Code.const_1);
		Code.put(Code.jmp);
		int jump_address = Code.pc;
		Code.put2(0);
		Code.put2(address_to_fill, address_to_fill - Code.pc);
		Code.put2(address_to_fill_2, address_to_fill_2 - Code.pc);
		Code.put(Code.const_);
		Code.put2(jump_address, jump_address - Code.pc);
	}
	
	public void visit(ConditionExpr condExpr) {
		Code.put(Code.const_);
		Code.put(Code.jcc + Code.ne);
		int address_to_fill = Code.pc;
		Code.put2(0);
		Code.put(Code.const_);
		Code.put(Code.jcc + Code.ne);
		int address_to_fill_2 = Code.pc;
		Code.put2(0);
		Code.put(Code.const_);
		Code.put(Code.jmp);
		int jump_address = Code.pc;
		Code.put2(0);
		Code.put2(address_to_fill, address_to_fill - Code.pc);
		Code.put2(address_to_fill_2, address_to_fill_2 - Code.pc);
		Code.put(Code.const_1);
		Code.put2(jump_address, jump_address - Code.pc);
	}
	
	
	public void condExpr(CondExpr condExpr) {
		Code.put(Code.enter);
		//prave se 2 lokalne promenljive gde se cuvaju vrednosti izraza u slucaju true i u slucaju false
		Code.put(0);
		Code.put(2);
		Code.put(Code.store);
		Code.put(0);
		Code.put(Code.store);
		Code.put(1);
		Code.put(Code.const_);
		Code.put(Code.jcc + Code.ne);
		int address_to_fill = Code.pc;
		Code.put2(0);
		Code.put(Code.load);
		Code.put(0);
		Code.put(Code.jmp);
		int jump_address = Code.pc;
		Code.put2(0);
		Code.put2(address_to_fill, address_to_fill - Code.pc);
		Code.put(Code.load);
		Code.put(1);
		Code.put2(jump_address, jump_address - Code.pc);
		Code.put(Code.exit);
	}
	
	public void visit(DesignatorName desName) {
		Code.load(desName.obj);
		designator_stack.push(desName.obj);
	}
	
	public void visit(AccessField accessField) {
		designator_stack.pop();
		Code.put(Code.const_);
		Code.put(Code.jmp);
		jump_to_trap_addresses.push(Code.pc);
		Code.put2(0);
		if(accessField.obj.getKind() == MyObject.Meth) {
			Code.put(Code.dup);
		}
		Code.load(accessField.obj);
		designator_stack.push(accessField.obj);
	}
	
	public void visit(AccessElement accessElement) {
		Obj my_array = designator_stack.peek();
		if(my_array.getType().getElemType() == SymbolTable.charType)
			Code.put(Code.baload);
		else
			Code.put(Code.aload);
	}
	
	public int getMainPC() {
		return main_pc;
	}
	
	
}
