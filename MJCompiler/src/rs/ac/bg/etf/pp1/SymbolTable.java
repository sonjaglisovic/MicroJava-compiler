package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SymbolTable extends Tab {

    public static final Struct boolType = new MyStruct(MyStruct.Bool);
    public static final Struct voidType = new MyStruct(MyStruct.Void);

    public static void initialize() {
	SymbolTable.init();
	insert(MyObject.Type, "bool", boolType);
	insert(MyObject.Type, "void", voidType);
	ObjectFormatter.setDescription(intType, "int");
	ObjectFormatter.setDescription(charType, "char");
	ObjectFormatter.setDescription(boolType, "bool");
	ObjectFormatter.setDescription(voidType, "void");
	MyObject.initialize();
    }

}
