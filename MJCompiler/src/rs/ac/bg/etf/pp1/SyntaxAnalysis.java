package rs.ac.bg.etf.pp1;


import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class SyntaxAnalysis extends VisitorAdaptor{

	private static boolean error_detected = false; 
	
	public static void errorDetected() {
		error_detected = true;
	}
	
	public static boolean passedSyntaxCheck() {
		return !error_detected;
	}
	
}
