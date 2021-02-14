package rs.ac.bg.etf.pp1;

import java.util.HashMap;
import java.util.Map;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class ObjectFormatter {

    private static Map<Struct, String> typeDescriptions = new HashMap<>();

    public static void setDescription(Struct newType, String description) {
	typeDescriptions.put(newType, description);
    }

    public static String getDescription(Struct type) {
	if (typeDescriptions.containsKey(type))
	    return typeDescriptions.get(type);
	return "";
    }

    public static String getObjectDescription(Obj objectToDescribe) {
	StringBuilder sb = new StringBuilder(" ");
	sb.append(MyObject.getTypeDesc(objectToDescribe.getKind()) + " ");
	sb.append(objectToDescribe.getName() + ": ");
	sb.append(typeDescriptions.get(objectToDescribe.getType()) + " ");
	sb.append(objectToDescribe.getAdr() + " ");
	sb.append(objectToDescribe.getLevel() + " ");
	return sb.toString();
    }
}
