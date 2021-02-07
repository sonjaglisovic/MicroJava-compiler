package rs.ac.bg.etf.pp1;

import java.util.*;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

import rs.etf.pp1.mj.runtime.*;

public class CodeGenerator extends VisitorAdaptor {

	private int main_pc;
	private Stack<Integer> operation_code_stack = new Stack<>();
	private Stack<Integer> break_jump_address = new Stack<>();
	private Stack<Integer> continue_jump_address = new Stack<>();
	private Stack<Integer> jump_true = new Stack<>();
	private Stack<Integer> jump_false = new Stack<>();
	private Stack<Integer> do_while_stack = new Stack<>();
	boolean class_decl_in_progress = false;
	boolean method_decl_in_progress = false;
	private int static_memory_free;
	private Obj this_object_for_current_method = null;
	private Obj currentClass;
	private Stack<Boolean> invokevirtual = new Stack<>(); 
	private int nested_if = 0;
	private boolean return_statement_occurred = false;
	private Stack<Obj> designtor_stack = new Stack<>();
	private Stack<Integer> case_stack = new Stack<>();
	
	
	public CodeGenerator(int numOfStatics) {
		static_memory_free = numOfStatics;
		len_code();
		ch_code();
		ord_code();
	}
	
	public int getStaticMemoryFree() {
		return SemanticAnalysisHelper.getGlobalOffset() + static_memory_free;
	}

	public void visit(MethodName methodName) {
		methodName.obj.setAdr(Code.pc);
		if(methodName.getMethName().compareTo("main") == 0) {
			main_pc = Code.pc;
			int last_address = 0;
			Map<Struct, Integer> classes_to_vmt_offsets = SemanticAnalysisHelper.getOffsets();
			Iterator<Map.Entry<Struct, Integer>> iterator = classes_to_vmt_offsets.entrySet().iterator();
			while(iterator.hasNext()) {
				
			Map.Entry<Struct, Integer> class_to_offset = iterator.next();
			Struct currentCls = class_to_offset.getKey();
			int offset = class_to_offset.getValue();
			Map<String, Integer> methods_mapped_to_addresses = new HashMap<>();
			Struct current_type = currentCls;
			while(current_type != SymbolTable.noType && current_type != null) {
				Collection<Obj>local_symbols = current_type.getMembers();
				for(Obj current_obj : local_symbols) {
					if(current_obj.getKind() == MyObject.Meth) {
						if(!methods_mapped_to_addresses.containsKey(current_obj.getName()))
							methods_mapped_to_addresses.put(current_obj.getName(), current_obj.getAdr());
					}
				}
				current_type = current_type.getElemType();
			}
			
			last_address = fillVMT(methods_mapped_to_addresses, static_memory_free + offset);
			}
			Code.loadConst(2);
			Code.put(Code.neg);
			Code.put(Code.putstatic);
			Code.put2(last_address);
		}
		method_decl_in_progress = true;
		Collection<Obj> locals = methodName.obj.getLocalSymbols();
		Code.put(Code.enter);
		Code.put(methodName.obj.getLevel());
		Code.put(methodName.obj.getLocalSymbols().size());
		for(Obj current_obj : locals) {
			if(current_obj.getName().compareTo("this") == 0) {
				Code.store(current_obj);
				this_object_for_current_method = current_obj;
				break;
			}
		}
	}
	
	public void visit(MethodDeclaration methodDecl) {
		this_object_for_current_method = null;
		method_decl_in_progress = false;
		if(!return_statement_occurred && methodDecl.getMethodSign().getMethodName().obj.getType() != SymbolTable.voidType) {
			Code.put(Code.trap);
			Code.put('g');
		}
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		return_statement_occurred = false;
		
	}
	
	public void visit(ClassName className) {
		Obj parent = className.getOptionalParent().obj;
		int start_address = 1;
		int parent_level = 0;
		if(parent != SymbolTable.noObj)
			parent_level = parent.getLevel();
		start_address += parent_level;
		Collection<Obj> locals = className.obj.getType().getMembers();
		int field_num = 0;
		for(Obj class_member: locals) {
			if(class_member.getKind() == MyObject.Fld) {
				field_num++;
				class_member.setAdr(class_member.getAdr() + start_address);
			}
		}
		currentClass = className.obj;
		class_decl_in_progress = true;
		className.obj.setLevel(parent_level + field_num);
	}
	
	private int fillVMT(Map<String, Integer> method_names_to_addresses, int startAddress) {
		Iterator<Map.Entry<String, Integer>> iterator = method_names_to_addresses.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Integer> method = iterator.next();
			String methodName = method.getKey();
			int address = method.getValue();
			for(int i = 0; i < methodName.length(); i++) {
				Code.loadConst(methodName.charAt(i));
				Code.put(Code.putstatic);
				Code.put2(startAddress++);
			}
			Code.loadConst(-1);
			Code.put(Code.putstatic);
			Code.put2(startAddress++);
			Code.loadConst(address);
			Code.put(Code.putstatic);
			Code.put2(startAddress++);			
		}
		return startAddress;
		
	}
	
	public void visit(ClassDecl classDecl) {
		class_decl_in_progress = false;	
		currentClass = null;
	}
	/**
	 * Za 3 tipa konstatni kada se detektuju load-uje se vrednost na expression stack
	 */
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
	
	//alocira se prostor za novi objekat i postavlja se pokazivac na tableu virtuelnih metoda na onu vrednost koja odgovara alociranom tipu
	public void visit(MemoryAllocation memAllocation) {
		if(memAllocation.getOptionalExpression().getClass() == NoOptExpr.class) {
			Obj type_obj = memAllocation.getAllocationType().obj;	
			Code.put(Code.new_);
			//+1 je za pokazivac na tabelu virtuelnih funkcija
			Code.put2(4*type_obj.getLevel() + 4);
			Code.put(Code.dup);
			Code.loadConst(SemanticAnalysisHelper.getOffset(type_obj) + static_memory_free);
			Code.put(Code.putfield);
			Code.put2(0);
		}
	}
	/**
	 * U slucaju da alociramo niz, ako je tipa char postavljamo vrednost parametra instrukcije newarray na 1
	 */
	public void visit(SingleExpressionDecl optExpr) {
		Code.put(Code.newarray);
		MemoryAllocation memAlloc = (MemoryAllocation) optExpr.getParent();
		int array_dimensions = memAlloc.getAllocationType().getType().struct == SymbolTable.charType ? 0:1;
		Code.put(array_dimensions);
	}
	
	public void visit(FunctionCallDesign funcCallDesign) {
		if(invokevirtual.pop() == false) {
			Code.put(Code.call);
			int jump_address = funcCallDesign.getDesignator().obj.getAdr();
			Code.put2(jump_address - Code.pc + 1);
		}else {
				Code.put(Code.getfield); 
				Code.put2(0);
				
				Code.put(Code.invokevirtual);
				String methodName = funcCallDesign.getDesignator().obj.getName();
				for(int i = 0; i < methodName.length(); i++) {
					Code.put4(methodName.charAt(i));
				}
				Code.put4(-1);
			}
		}
	
	/**
	 * Sto se tice aritmetickih operacija kada se prepozna neka od njih kod instrukcije se stavlja na operation_stack, da bi se 
	 * kada budu prepoznata oba operanda skinuo sa steka i primenila odredjena operacija, 
	 * Na primer najjednostavniji slucaj imamo a*b, najpre ce se detektovati a i staviti na expr stack, kada se detektuje * jos uvek nemamo
	 * oba operanda na expr stack-u pa ce se Code.add staviti na stack , kada se i b detektuje i stavi na expr stack, na vrhu
	 */
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
	
	public void visit(MinusExpr minusExpr) {
		Code.put(Code.neg);
	}
	
	public void visit(NOMinusExpr noMinusExpr) {
		Code.put(operation_code_stack.pop());
	}
	
	public void visit(OptionalOperator optionalOp) {
		Code.put(operation_code_stack.pop());
		int address_to_fill = Code.pc;
		Code.put2(0);
		Code.put(Code.const_n);
		Code.put(Code.jmp);
		int jump_address = Code.pc;
		Code.put2(0);
		Code.fixup(address_to_fill);
		Code.put(Code.const_1);
		Code.fixup(jump_address);
	}
	/**
	 * Najpre se prvi operand poredi sa 0 ako je jednako skace, skida drugi operand sa steka jer on u tom slucaju nije potreban i ucitava se 0
	 * Ako ne, poredi se drugi operand sa nulom i ako je jednako skace se s tim sto nema pop vec se samo ucitava vrednost 0, ako su obe vrednosti razlicite od 0 ucitava se 1
	 * 
	 */
	public void visit(ConditionTerm condTerm) {
		Code.put(Code.const_n);
		Code.put(Code.jcc + Code.eq);
		int address_to_fill = Code.pc;
		Code.put2(0);
		Code.put(Code.const_n);
		Code.put(Code.jcc + Code.eq);
		int address_to_fill_2 = Code.pc;
		Code.put2(0);
		Code.put(Code.const_1);
		Code.put(Code.jmp);
		int jump_address = Code.pc;
		Code.put2(0);
		Code.fixup(address_to_fill);
		Code.put(Code.pop);
		Code.fixup(address_to_fill_2);
		Code.put(Code.const_n);
		Code.fixup(jump_address);
	}
	
	
	public void visit(ConditionExpr condExpr) {
		Code.put(Code.const_n);
		Code.put(Code.jcc + Code.ne);
		int address_to_fill = Code.pc;
		Code.put2(0);
		Code.put(Code.const_n);
		Code.put(Code.jcc + Code.ne);
		int address_to_fill_2 = Code.pc;
		Code.put2(0);
		Code.put(Code.const_n);
		Code.put(Code.jmp);
		int jump_address = Code.pc;
		Code.put2(0);
		Code.fixup(address_to_fill);
		Code.put(Code.pop);
		Code.fixup(address_to_fill_2);
		Code.put(Code.const_1);
		Code.fixup(jump_address);
	}
	
	public void visit(TernaryCondition ternaryCond) {
		Code.loadConst(0);
		Code.put(Code.jcc + Code.eq);
		jump_false.push(Code.pc);
		Code.put2(0);
	}
	
	public void visit(Col col) {
		Code.put(Code.jmp);
		jump_true.push(Code.pc);
		Code.put2(0);
		Code.fixup(jump_false.pop());
	}	
	
	public void visit(CondExpr condExpr) {
		Code.fixup(jump_true.pop());
	}	
	
	public void visit(DesignatorName desName) {
		if(this_object_for_current_method != null && desName.obj.getKind() == MyObject.Fld) {
			Code.load(this_object_for_current_method);
		}
		if(this_object_for_current_method != null && desName.obj.getKind() == MyObject.Meth) {
			if(class_decl_in_progress) {
				invokevirtual.push(true);
				Code.load(this_object_for_current_method);
				Code.put(Code.dup);
			}else
				invokevirtual.push(false);
		}else if(desName.obj.getKind() == MyObject.Meth)
			invokevirtual.push(false);
		else {
			designtor_stack.push(desName.obj);
		}
	}
	
	public void visit(AccessField accessField) {
		Obj my_object = designtor_stack.pop();
		Code.load(my_object);
		Code.put(Code.dup);
		Code.put(Code.const_n);
		Code.put(Code.jcc + Code.ne);
		int jump_address = Code.pc;
		Code.put2(0);
		Code.put(Code.trap);
		Code.put('g');
		Code.fixup(jump_address);
		if(accessField.obj.getKind() == MyObject.Meth) {
			invokevirtual.push(true);
			Code.put(Code.dup);
		}else {
			designtor_stack.push(accessField.obj);
		}
	}
	
	public void visit(AccessElement accessElement) {
		Obj my_array = designtor_stack.pop();
		if(my_array.getKind() == MyObject.Fld) {
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		}
		Code.load(my_array);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		designtor_stack.push(accessElement.obj);
	}
	
	public void visit(SimpleDesignator simpleDes) {
		designtor_stack.pop();
		Code.load(simpleDes.getDesignator().obj);
	}
	
	public void visit(AssignStatementSt assignStmt) {
		Code.store(assignStmt.getDesignator().obj);
		designtor_stack.pop();
	}
	
	public void visit(FuncCallSt funcCall) {
		if(invokevirtual.pop() == false) {
			Code.put(Code.call);
			int jump_address = funcCall.getDesignator().obj.getAdr();
			Code.put2(jump_address - Code.pc + 1);
		}else {
			Code.put(Code.getfield); 
			Code.put2(0);
			Code.put(Code.invokevirtual);
			String methodName = funcCall.getDesignator().obj.getName();
			for(int i = 0; i < methodName.length(); i++) {
				Code.put4(methodName.charAt(i));
			}
			Code.put4(-1);
			
			if(funcCall.getDesignator().obj.getType() != SymbolTable.voidType)
				Code.put(Code.pop);
		}
	}
	
	/**
	 * Ukoliko je designator niz ili polje klase na stacku imamo sledeca stanja: 
	 * Za niz adr, index te da bi se element loadovao sa aload ili baload bila izvresena operacija nad njim i kasnije store ovao na isto mesto
	 * moramo duplirati sadrzaj na stacku adr, index, adr, index -> nakon load-a adr, index, elem -> adr, index, elem+1 -> kao sto vidimo stanje steka
	 * je upravo onakvo kakvo se ocekuje za instrukcije bastore i astore, za polje klase slicno objasnjenje
	 * Za dekreentiranje uradjena slicna stvar, sto se tice pop-a sa designator stacka u prvoj liniji, objasnjenje kod read-a
	 */
	public void visit(IncrementSt increment) {
		designtor_stack.pop();
		if(increment.getDesignator().obj.getKind() == MyObject.Elem || increment.getDesignator().obj.getKind() == MyObject.Fld) 
			Code.put(Code.dup2);
		Code.load(increment.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(increment.getDesignator().obj);
	}
	
	
	public void visit(DecrementSt decrement) {
		designtor_stack.pop();
		if(decrement.getDesignator().obj.getKind() == MyObject.Elem || decrement.getDesignator().obj.getKind() == MyObject.Fld) 
			Code.put(Code.dup2);
		Code.load(decrement.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(decrement.getDesignator().obj);
	}
	
   /**
    * Kad god se detektuje designator skida se jedna vrednost koja je stavljena na designator stack koja bi bila sledeca za load da se
    * pristupalo njenom polju, zato je prva instrukcija u svim smenama koje sadrze designator najpre radi pop sa ovom stack-a - pogledati
    * implementaciju designatorName, accessField i accessElement za dalja pojasnjenja
    */
	public void visit(ReadFunctionCall readFuncCall) {
		designtor_stack.pop();
		if(readFuncCall.getDesignator().obj.getType() == SymbolTable.charType) {
			Code.put(Code.bread);
		}else {
			Code.put(Code.read);
		}
		Code.store(readFuncCall.getDesignator().obj);
	}
	
	public void visit(PrintFunctionCall printFuncCall) {
		if(printFuncCall.getOptionalSecondParam().getClass() == NoSecondParam.class) {
			Code.loadConst(5);
		}
		if(printFuncCall.getExpr().struct == SymbolTable.charType) {
			Code.put(Code.bprint);
		} else 
			Code.put(Code.print);
		}
	
	public void visit(PrintSecondParam secondParam) {
		Code.loadConst(secondParam.getValue());
	}
	
	public void visit(RightCondition rightCond) {
		nested_if++;
		Code.put(Code.const_n);
		Code.put(Code.jcc + Code.eq);
		jump_false.push(Code.pc);
		Code.put2(0);
	}
	
	public void visit(Else else_) {
		Code.put(Code.jmp);
		jump_true.push(Code.pc);
		Code.put2(0);
		Code.fixup(jump_false.pop());
	}
	
	/**
	 * nested_if se dekrementira jer u slucaju da se return nadje u okviru if-a ne treba smatrati da je return detektovan jer se ne nalazi 
	 * u default grani 
	 */
	public void visit(UnmatchedIf unmatchedIf) {
		nested_if--;
		Code.fixup(jump_false.pop());
	}
	
	public void visit(MatchedStatement matchedIf) {
		nested_if--;
		Code.fixup(jump_true.pop());
	}
	
	public void visit(DoWhileStart doWhileStart) {
		do_while_stack.push(Code.pc);
		break_jump_address.push(-1);
		continue_jump_address.push(-1);
	}
	
	public void visit(DoWhileLoop doWhile) {
		Code.loadConst(0);
		Code.put(Code.jcc + Code.ne);
		Code.put2(do_while_stack.pop() - Code.pc + 1);		
		int break_address = break_jump_address.empty() ? -1 : break_jump_address.pop();
		while(break_address != -1) {
			Code.fixup(break_address);
			break_address = break_jump_address.pop();
		}
	}
	
	public void visit(DoWhileConditionLoad doWhileCondition) {
		int continue_address = continue_jump_address.empty() ? -1 : continue_jump_address.pop();
		while(continue_address != -1) {
			Code.fixup(continue_address);
			continue_address = continue_jump_address.pop();
		}
	}
	
	public void visit(BreakStatement breakStatement) {
		Code.put(Code.jmp);
		break_jump_address.push(Code.pc);
		Code.put2(0);
	}
	
	public void visit(ContinueStatement continueStatement) {
		Code.put(Code.jmp);
		continue_jump_address.push(Code.pc);
		Code.put2(0);
	}
	
	public void visit(ReturnStatement returnStmt) {
		if(nested_if == 0)
			return_statement_occurred = true;
	}
	
	
	//ord len i ch implementation
	
	private void ord_code() {
		Obj ord_obj = SymbolTable.find("ord");
		ord_obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	private void ch_code() {
		Obj chr_obj = SymbolTable.find("chr");
		chr_obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	private void len_code() {
		Obj len_obj = SymbolTable.find("len");
		len_obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(SwitchStart switchStart) {
		break_jump_address.push(-1);
		case_stack.push(-1);
		//stavljanje na stek lokalne promenljive koja je inicijalno 0 jer nije bio ispunjen za ulazak u case granu
		Code.loadConst(0);
	}
	/**
	 * Ukoliko postoji prethodni case postaviti njegovu adresu skoka na adresu tekceg pc-a, sledeci korak uvedena je (videti SwitchStart)
	 * lokalna promenljiva koja govori da li je vec bio ispunjen uslov za ulazak u case granu, ukoliko jeste izvrsava se kod u tekucoj case grani
	 * Ukoliko promenljiva ima vrednost 0 proverava se da je je expr jednak vrednosti za tekucu case granu ako jeste kao pomocna promenljiva se ucitava 1
	 * i prelazi se na izvrsavanje u suprtnom, se uzitava kao pomocna promenljiva i skace se na naredni case
	 */
	public void visit(CaseStart caseStart) {
		int previous_case = case_stack.pop();
		if(previous_case != -1)
			Code.fixup(previous_case);
		Code.loadConst(0);
		Code.put(Code.jcc + Code.ne);
		int address_to_fill_1 = Code.pc;
		Code.put2(0);
		Code.put(Code.dup);
		Code.loadConst(caseStart.getValue());
		Code.put(Code.jcc + Code.ne);
		int address_to_fill = Code.pc;
		Code.put2(0);
		Code.put(Code.jmp);
		int jump_address = Code.pc;
		Code.put2(0);
		Code.fixup(address_to_fill);
		Code.loadConst(0);
		Code.put(Code.jmp);
		case_stack.push(Code.pc);
		Code.put2(0);
		Code.fixup(address_to_fill_1);
		Code.fixup(jump_address);
		Code.loadConst(1);	
		
	}
	
	public void visit(SwitchStatement switchStatement) {
		int break_address = break_jump_address.empty() ? -1 : break_jump_address.pop();
		while(break_address != -1) {
			Code.fixup(break_address);
			break_address = break_jump_address.pop();
		}
		
		int previous_case = case_stack.pop();
		if(previous_case != -1)
			Code.fixup(previous_case);
		
		//Skidaju se sa expression stacka promenljiva i expr koji je su bili tu radi provere u case-ovima
		Code.put(Code.pop);
		Code.put(Code.pop);
		
		
	}
	
	public void visit(FirstParamExpr firstActPar) {
		if(invokevirtual.peek()) {
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
		}
	}
	
	public void visit(ParameterList paramList) {
		if(invokevirtual.peek()) {
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
		}
	}
	
	public int getMainPC() {
		return main_pc;
	}

}

