package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;

public class Compiler {

    static {
	DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
	Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }

    public static void tsdump() {
	MyDumpSymbolTableVisitor dstv = new MyDumpSymbolTableVisitor();
	SymbolTable.dump(dstv);
    }

    public static void main(String[] args) throws IOException {
	Logger log = Logger.getLogger(Compiler.class);

	Reader br = null;
	try {
	    File sourceCode = new File(args[0]);
	    log.info("Compiling source file: " + sourceCode.getAbsolutePath());

	    br = new BufferedReader(new FileReader(sourceCode));
	    Yylex lexer = new Yylex(br);

	    MJParser p = new MJParser(lexer);
	    try {
		Symbol s = p.parse(); // pocetak parsiranja
		Program prog = (Program) (s.value);
		// ispis sintaksnog stabla
		// log.info(prog.toString(""));
		log.info("===================================");

		
		// ispis prepoznatih programskih konstrukcija
		SyntaxAnalysis syntax_analyzer = new SyntaxAnalysis();
		
		if(SyntaxAnalysis.passedSyntaxCheck()) {
		prog.traverseBottomUp(syntax_analyzer);
		SemanticAnalyzer analyzer = new SemanticAnalyzer();
		SymbolTable.initialize();
		prog.traverseBottomUp(analyzer);
		if (analyzer.passed()) {
		    tsdump();
		    File objFile = new File(args[1]);
			if(objFile.exists()) objFile.delete();

		    CodeGenerator codeGenerator = new CodeGenerator(analyzer.getNVars());
		    prog.traverseBottomUp(codeGenerator);
		    Code.dataSize = analyzer.getNVars();
		    Code.mainPc = codeGenerator.getMainPC();
			Code.write(new FileOutputStream(objFile));

		}
		}
	    } catch (ClassCastException e) {
		System.out.println("Fatalna greska");
		return;
	    }

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    if (br != null)
		try {
		    br.close();
		} catch (IOException e1) {
		    log.error(e1.getMessage(), e1);
		}
	}

    }

}
