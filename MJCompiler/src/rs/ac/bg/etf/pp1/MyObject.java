package rs.ac.bg.etf.pp1;

import java.util.HashMap;
import java.util.Map;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class MyObject extends Obj {

    public MyObject(int kind, String name, Struct type) {
	super(kind, name, type);
	// TODO Auto-generated constructor stub
    }

    public MyObject(int kind, String name, Struct type, int adr, int level) {
	super(kind, name, type, adr, level);
    }

    private static Map<Integer, String> hashMap = new HashMap<>();

    public static void initialize() {
	hashMap.put(Obj.Con, "Con");
	hashMap.put(Obj.Elem, "Elem");
	hashMap.put(Obj.Fld, "Field");
	hashMap.put(Obj.Meth, "Meth");
	hashMap.put(Obj.Prog, "Prog");
	hashMap.put(Obj.Type, "Type");
	hashMap.put(Obj.Var, "Var");
    }

    public static String getTypeDesc(int type) {
	return hashMap.get(type);
    }
}
