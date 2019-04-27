package TJasn;

import static TJlexer.LexicalAnalyzer.getCurrentToken;
import static TJlexer.LexicalAnalyzer.nextToken;
import static TJlexer.Symbols.*;
import static TJasn.TJ.staticTab;
import static TJasn.TJ.symTab;
import TJ1asn.SourceFileErrorException;
import TJlexer.Symbols;
import TJlexer.LexicalAnalyzer;
import TJasn.symbolTable.*;
import TJasn.virtualMachine.*;


// ******************* Recursive Descent ParserAndTranslator ***************************

public final class ParserAndTranslator {

  private static class IdentList {    // for parameter lists of methods
    String name;
    int dimensions;
    IdentList next;
  }

  private static class ParameterList {
    IdentList theParams;
    int paramCount;
  }

  private static int level = 1;

  public static int getLevel() {
    return level;
  }

  private static Boolean ScannerImported = false;

  private static void accept (Symbols expectedToken) throws SourceFileErrorException
  {
    if (getCurrentToken() == expectedToken)
      nextToken();
    else throw new SourceFileErrorException("Something's wrong--maybe the following token is missing: "
                                               + expectedToken.symbolRepresentationForOutputFile);
  }


  private static MethodRec lookUpCalledMethod(String methodName, int expectedType)
                                          throws SourceFileErrorException
  {
     MethodRec t = (MethodRec) BlockRec.searchForStatic(methodName, true);

     if (t == null) { /* the method has never been declared nor called */
        t = new MethodRec(methodName, expectedType, MethodRec.NOT_KNOWN);
        t.setStartAddr(Instruction.OPERAND_NOT_YET_KNOWN);
        MethodRec.incUndeclaredMethodCount();
     }

     else if (expectedType == MethodRec.NOT_KNOWN)
         ;
     else if (t.getType() == MethodRec.NOT_KNOWN)
         t.setType(expectedType);

     else if (expectedType != t.getType())
             throw new SourceFileErrorException("Method does not have appropriate return type");

     return t;
  }


  static void program () throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTprogram);
    TJ.output.incTreeDepth();

    if (getCurrentToken() == IMPORT) {
       ScannerImported = true;
       importStmt();
    }

    accept(CLASS);
    accept(IDENT);
    accept(LBRACE);

    while (getCurrentToken() == STATIC)
      dataFieldDecl();

    staticTab.setLastStaticVarAddr(staticTab.getNextOffset() - 1);

    LexicalAnalyzer.setEndOfString(staticTab.getLastStaticVarAddr());

    mainDecl();

    while (getCurrentToken() == STATIC)
      methodDecl();

    if (MethodRec.getUndeclaredMethodCount() != 0)
      throw new SourceFileErrorException("Not all called methods have been declared");

    accept(RBRACE);

    TJ.output.decTreeDepth();
  }


  private static void importStmt() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTimport);
    TJ.output.incTreeDepth();

    accept(IMPORT);
    accept(JAVA);
    accept(DOT);
    accept(UTIL);
    accept(DOT);
    accept(SCANNER);
    accept(SEMICOLON);

    TJ.output.decTreeDepth();
  }


  private static void dataFieldDecl() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTdataFieldDecl);
    TJ.output.incTreeDepth();

    accept(STATIC);
    varDecl();

    TJ.output.decTreeDepth();
  }


  private static void varDecl() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTvarDecl);
    TJ.output.incTreeDepth();

    if (getCurrentToken() == INT) {
      nextToken();
      singleVarDecl();
      while (getCurrentToken() == COMMA) {
        nextToken();
        singleVarDecl();
      }
      accept(SEMICOLON);
    }
    else if (getCurrentToken() == SCANNER) {

      if (!ScannerImported)
        throw new SourceFileErrorException("Scanner used without having been imported");

      nextToken();

      if (getCurrentToken() == IDENT) {
        if (level == 1) {
          if (BlockRec.searchForStatic(LexicalAnalyzer.getCurrentSpelling(), false) != null)
            throw new SourceFileErrorException("Illegal redeclaration of class variable "
                                          + LexicalAnalyzer.getCurrentSpelling());
          else new ClassVariableRec(LexicalAnalyzer.getCurrentSpelling());
        }
        else {
          if (symTab.searchForLocal(LexicalAnalyzer.getCurrentSpelling()) != null)
            throw new SourceFileErrorException("Illegal redeclaration of local variable "
                                          + LexicalAnalyzer.getCurrentSpelling());
          else new LocalVariableRec(LexicalAnalyzer.getCurrentSpelling());
        }
        nextToken();
      }
      else throw new SourceFileErrorException("Scanner name expected");

      accept(BECOMES);
      accept(NEW);
      accept(SCANNER);
      accept(LPAREN);
      accept(SYSTEM);
      accept(DOT);
      accept(IN);
      accept(RPAREN);
      accept(SEMICOLON);
    }
    else throw new SourceFileErrorException("\"int\" or \"Scanner\" expected");

    TJ.output.decTreeDepth();
  }


  private static void singleVarDecl() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTsingleVarDecl);
    TJ.output.incTreeDepth();

    if (getCurrentToken() == IDENT) {

      VariableRec v = null;
      String identName = LexicalAnalyzer.getCurrentSpelling();
      int dimensions = 0;

      nextToken();

      while (getCurrentToken() == LBRACKET) {
            dimensions++;
            nextToken();
            accept(RBRACKET);
      }

      if (level == 1) {
        if (BlockRec.searchForStatic(identName, false) != null)
          throw new SourceFileErrorException("Illegal redeclaration of class variable "
                              + LexicalAnalyzer.getCurrentSpelling());
        else {
          int addr = staticTab.getNextOffset();
          v = new ClassVariableRec(identName, VariableRec.INT, dimensions, addr);
          staticTab.setNextOffset(addr + 1);
        }
      }

      else {  // level > 1
        if (symTab.searchForLocal(identName) != null)
          throw new SourceFileErrorException("Illegal redeclaration of local variable "
                              + LexicalAnalyzer.getCurrentSpelling());
        else {
          int stackFrameOffset = symTab.getNextOffset();
          v = new LocalVariableRec(identName, VariableRec.INT, dimensions, stackFrameOffset);
          symTab.setNextOffset(stackFrameOffset + 1);
        }
      }

      if (getCurrentToken() == BECOMES) {
        nextToken();
        if (level == 1)
          new PUSHSTATADDRinstr(v.offset);
        else
          new PUSHLOCADDRinstr(v.offset);
        expr3();
        new SAVETOADDRinstr();
      }
    }
    else
      throw new SourceFileErrorException("Variable name expected");


    TJ.output.decTreeDepth();
  }


  private static void mainDecl() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTmainDecl);
    TJ.output.incTreeDepth();

    accept(PUBLIC);
    accept(STATIC);
    accept(VOID);
    accept(MAIN);
    accept(LPAREN);
    accept(STRING);
    accept(IDENT);
    accept(LBRACKET);
    accept(RBRACKET);
    accept(RPAREN);

    MethodRec t = new MethodRec("main", MethodRec.VOID, 0);
    t.setStartAddr(Instruction.getNextCodeAddress());

    INITSTKFRMinstr iInstr
      = new INITSTKFRMinstr(Instruction.OPERAND_NOT_YET_KNOWN);

    compoundStmt(t, null);

    iInstr.fixUpOperand(t.getTable().getMaxNextOffset() - TJ.FIRST_LOCALVAR_STACKFRAME_OFFSET);

    new STOPinstr();

    TJ.output.decTreeDepth();
  }


  private static void methodDecl() throws SourceFileErrorException
  {
    INITSTKFRMinstr iInstr = null;
    int dimensions = 0;
    int type;

    TJ.output.printSymbol(NTmethodDecl);
    TJ.output.incTreeDepth();

    accept(STATIC);

    if (getCurrentToken() == INT) {
      type = MethodRec.INT;
      nextToken();
      while (getCurrentToken() == LBRACKET) {
            dimensions++;
            nextToken();
            accept(RBRACKET);
      }
    }
    else {
      type = MethodRec.VOID;
      accept(VOID);
    }

    MethodRec t = null;

    if (getCurrentToken() == IDENT) {
      t = (MethodRec) BlockRec.searchForStatic(LexicalAnalyzer.getCurrentSpelling(), true);

      if (t == null)                             /* no call or declaration of this method has yet been seen */
        t = new MethodRec(LexicalAnalyzer.getCurrentSpelling(), type, dimensions);

      else if (t.getCallsToBeFixedUp() != null) {/* method was previously called (but not previously declared)*/

        t.getCallsToBeFixedUp().redirectThemToHere(type);
        t.setCallsToBeFixedUp(null);

        t.setDimensionCount(dimensions);

        if (t.getType() == MethodRec.NOT_KNOWN)
          t.setType(type);
        else if (t.getType() != type)
          throw new SourceFileErrorException
                      ("Declared return type inconsistent with earlier call of this method");

        MethodRec.decUndeclaredMethodCount();
      }

      else throw new SourceFileErrorException("Method " + LexicalAnalyzer.getCurrentSpelling()
                                                 + " has already been declared");

      t.setStartAddr(Instruction.getNextCodeAddress());

      iInstr = new INITSTKFRMinstr(Instruction.OPERAND_NOT_YET_KNOWN);

      nextToken();
    }
    else
      throw new SourceFileErrorException("Method name expected");

    accept(LPAREN);
    ParameterList parameters = parameterDeclList();
    accept(RPAREN);

    if (t.getArgCount() == MethodRec.NOT_KNOWN)  // Method has not yet been called
      t.setArgCount(parameters.paramCount);
    else if (t.getArgCount() != parameters.paramCount)
      throw new SourceFileErrorException("Method " + t.name + " was previously called with "
                                             + t.getArgCount() + " arguments");

    compoundStmt(t, parameters.theParams);

    iInstr.fixUpOperand(t.getTable().getMaxNextOffset() - TJ.FIRST_LOCALVAR_STACKFRAME_OFFSET);

    if (t.getType() == MethodRec.VOID)
      new RETURNinstr(t.getArgCount());

    TJ.output.decTreeDepth();
  }


  private static ParameterList parameterDeclList() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTparameterDeclList);
    TJ.output.incTreeDepth();

    ParameterList p = new ParameterList();

    if (getCurrentToken() == INT) {
        parameterDecl(p);
        while (getCurrentToken() == COMMA) {
          nextToken();
          parameterDecl(p);
        }
    }
    else
      TJ.output.printSymbol(EMPTY);

    TJ.output.decTreeDepth();
    return p;
  }


  private static void parameterDecl(ParameterList p) throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTparameterDecl);
    TJ.output.incTreeDepth();

    IdentList param = new IdentList();

    accept(INT);
    if (getCurrentToken() == IDENT) {
          param.name = LexicalAnalyzer.getCurrentSpelling();
          param.next = p.theParams;
          p.theParams = param;
          p.paramCount++;
    }
    accept(IDENT);
    while (getCurrentToken() == LBRACKET) {
          param.dimensions++;
          nextToken();
          accept(RBRACKET);
    }

    TJ.output.decTreeDepth();
  }


  private static void compoundStmt(MethodRec m, IdentList params) throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTcompoundStmt);
    TJ.output.incTreeDepth();

    level++;
    if (m == null)
      symTab = new CompoundStmtBlockRec();
    else {
      symTab = new MethodBlockRec(m);

      int offset = -1;

      for (IdentList p = params; p != null; p = p.next) {
        if (symTab.searchForLocal(p.name) != null)
          throw new SourceFileErrorException("Illegal redeclaration of parameter "
                              + p.name);
        else
          new LocalVariableRec(p.name, VariableRec.INT, p.dimensions, --offset);
      }
    }

    accept(LBRACE);
    while (getCurrentToken() != RBRACE)
      statement();
    nextToken();

    MethodBlockRec mb = ((CompoundStmtBlockRec) symTab).methodBlock;
    mb.setMaxNextOffset(Math.max(symTab.getNextOffset(),
                                         mb.getMaxNextOffset()));
    symTab = symTab.enclosingBlock;

    level--;

    TJ.output.decTreeDepth();
  }


  private static void statement() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTstatement);
    TJ.output.incTreeDepth();

    switch (getCurrentToken()) {
      case SEMICOLON: nextToken(); break;
      case RETURN: nextToken();
                           if (((CompoundStmtBlockRec) symTab)
                                    .methodBlock.methodRec.getType() == MethodRec.INT)
                                 expr3();
                           new RETURNinstr(((CompoundStmtBlockRec) symTab)
                                                 .methodBlock.methodRec.getArgCount());
                           accept(SEMICOLON);
                           break;
      case INT: case SCANNER: varDecl(); break;
      case IDENT: assignmentOrInvoc(); break;
      case LBRACE: compoundStmt(null, null); break;
      case IF: ifStmt(); break;
      case WHILE: whileStmt(); break;
      case SYSTEM: outputStmt(); break;
      default: throw new SourceFileErrorException("Expected first token of a <statement>, not "
                              + getCurrentToken().symbolRepresentationForOutputFile);
    }

    TJ.output.decTreeDepth();
  }


  private static void assignmentOrInvoc() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTassignmentOrInvoc);
    TJ.output.incTreeDepth();

    String identName = LexicalAnalyzer.getCurrentSpelling();

    accept(IDENT);
    if (getCurrentToken() != LPAREN) {
      VariableRec t = symTab.searchForVariable(identName);
      if (t == null)
        throw new SourceFileErrorException("Undeclared variable: " + identName);
      if (t.type != VariableRec.INT)
        throw new SourceFileErrorException("int variable expected");

      if (t instanceof LocalVariableRec) {
         //======================x
         new PUSHLOCADDRinstr(t.offset);

      }
      else {
        //=======================x
        new PUSHSTATADDRinstr(t.offset);
      }

      int dim = 0;

      while (getCurrentToken() == LBRACKET) {
           new LOADFROMADDRinstr();
           nextToken();
           expr3();
           accept(RBRACKET);
           new ADDTOPTRinstr();
           dim++;
      }

      if (dim > t.dimensionCount) throw new SourceFileErrorException("Unexpected index(es)");

      //======================x
      accept(BECOMES);
      expr3();
      new SAVETOADDRinstr();
      accept(SEMICOLON);

    }

    else {

      MethodRec t = lookUpCalledMethod(identName, MethodRec.NOT_KNOWN);

      argumentList(t);

      CALLSTATMETHODinstr cInstr = new CALLSTATMETHODinstr(t.getStartAddr());

      if (t.getStartAddr() == Instruction.OPERAND_NOT_YET_KNOWN)
                             /* the method has not yet been declared */
        if (t.getType() == MethodRec.NOT_KNOWN)
          t.setCallsToBeFixedUp(new FixUpRec(cInstr, t.getCallsToBeFixedUp(),
                                                new NOPorDISCARDVALUEinstr()));
        else {
          t.setCallsToBeFixedUp(new FixUpRec(cInstr, t.getCallsToBeFixedUp()));
          if (t.getType() != MethodRec.VOID)
            new NOPorDISCARDVALUEinstr(false);  // generate DISCARDVALUE instruction
        }

      else if (t.getType() != MethodRec.VOID)
        new NOPorDISCARDVALUEinstr(false);  // generate DISCARDVALUE instruction

      accept(SEMICOLON);
    }

    TJ.output.decTreeDepth();
  }


  private static void argumentList(MethodRec m) throws SourceFileErrorException
  {
     TJ.output.printSymbol(NTargumentList);
     TJ.output.incTreeDepth();

     //===============x
     accept(LPAREN);
     if(getCurrentToken() != RPAREN){
       expr3();
       new PASSPARAMinstr();
       while(getCurrentToken() == COMMA){
         nextToken();
         expr3();
         new PASSPARAMinstr();
       }
     }
     accept(RPAREN);

     TJ.output.decTreeDepth();
  }


  private static void ifStmt() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTifStmt);
    TJ.output.incTreeDepth();

    accept(IF);
    accept(LPAREN);
    expr7();
    accept(RPAREN);

    JUMPONFALSEinstr jInstr1 =
        new JUMPONFALSEinstr(Instruction.OPERAND_NOT_YET_KNOWN);

    statement();

    if (getCurrentToken() == ELSE) {
      nextToken();

      JUMPinstr jInstr2 = new JUMPinstr(Instruction.OPERAND_NOT_YET_KNOWN);

      jInstr1.fixUpOperand(Instruction.getNextCodeAddress());

      statement();

      jInstr2.fixUpOperand(Instruction.getNextCodeAddress());
    }
    else
      jInstr1.fixUpOperand(Instruction.getNextCodeAddress());

    TJ.output.decTreeDepth();
  }


  private static void whileStmt() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTwhileStmt);
    TJ.output.incTreeDepth();

    //=============================x
    accept(WHILE);
    accept(LPAREN);
    int addressOfA = Instruction.getNextCodeAddress();
    expr7();
    accept(RPAREN);
    JUMPONFALSEinstr jInstr = new JUMPONFALSEinstr(Instruction.OPERAND_NOT_YET_KNOWN);
    statement();
    new JUMPinstr(addressOfA);
    jInstr.fixUpOperand(Instruction.getNextCodeAddress());

    TJ.output.decTreeDepth();
  }

  private static void outputStmt() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NToutputStmt);
    TJ.output.incTreeDepth();

    accept(SYSTEM);
    accept(DOT);
    accept(OUT);
    accept(DOT);

    switch (getCurrentToken()) {
      //=======================================
      /* ????????

      default: throw new SourceFileErrorException("print() or println() expected, not "
                              + getCurrentToken().symbolRepresentationForOutputFile);
      */
      case PRINT:   nextToken();
                    accept(LPAREN);
                    printArgument();
                    accept(RPAREN);
                    accept(SEMICOLON);
                    break;

      case PRINTLN: nextToken();
                    accept(LPAREN);
                    if(getCurrentToken() != RPAREN)
                      printArgument();
                    accept(RPAREN);
                    accept(SEMICOLON);
                    new WRITELNOPinstr();
                    break;

      default: throw new SourceFileErrorException("print() or println() expected, not "
                              + getCurrentToken().symbolRepresentationForOutputFile);

    }

    TJ.output.decTreeDepth();
  }


  private static void printArgument() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTprintArgument);
    TJ.output.incTreeDepth();

    //=====================
    if(getCurrentToken() == CHARSTRING){
      new WRITESTRINGinstr(LexicalAnalyzer.getStartOfString(),LexicalAnalyzer.getEndOfString());
      nextToken();
    }else{
       expr3();
       new WRITEINTinstr();
     }

    TJ.output.decTreeDepth();
  }


  private static void expr7() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTexpr7);
    TJ.output.incTreeDepth();
    //=======================x
    expr6();
    while(getCurrentToken() == OR){
      nextToken();
      expr6();
      new ORinstr();
    }
    TJ.output.decTreeDepth();
  }


  private static void expr6() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTexpr6);
    TJ.output.incTreeDepth();
    //==========================x
    expr5();
    while(getCurrentToken() == AND){
      nextToken();
      expr5();
      new ANDinstr();
    }
    TJ.output.decTreeDepth();
  }


  private static void expr5() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTexpr5);
    TJ.output.incTreeDepth();
    //==============================x
    expr4();
    while(getCurrentToken() == EQ || getCurrentToken() == NE){
      if(getCurrentToken() == EQ){
        nextToken();
        expr4();
        new EQinstr();
      }else{
        nextToken();
        expr4();
        new NEinstr();
      }
    }

    TJ.output.decTreeDepth();
  }


  private static void expr4() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTexpr4);
    TJ.output.incTreeDepth();
    //===========================x
    expr3();
    if(getCurrentToken() == GT){
      nextToken();
      expr3();
      new GTinstr();
    }else if(getCurrentToken() == LT){
      nextToken();
      expr3();
      new LTinstr();
    }else if(getCurrentToken() == GE){
      nextToken();
      expr3();
      new GEinstr();
    }else if(getCurrentToken() == LE){
      nextToken();
      expr3();
      new LEinstr();
    }
    TJ.output.decTreeDepth();
  }


  private static void expr3() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTexpr3);
    TJ.output.incTreeDepth();
    //====================================x
    expr2();
    while(getCurrentToken() == PLUS || getCurrentToken() == MINUS){
      if(getCurrentToken() == PLUS){
        nextToken();
        expr2();
        new ADDinstr();
      }else{
        nextToken();
        expr2();
        new SUBinstr();
      }
    }

    TJ.output.decTreeDepth();
  }


  private static void expr2() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTexpr2);
    TJ.output.incTreeDepth();

    expr1();

    while (   getCurrentToken() == TIMES
           || getCurrentToken() == DIV
           || getCurrentToken() == MOD) {

      Symbols op = getCurrentToken();
      nextToken();
      expr1();

      if (op == TIMES)
        new MULinstr();
      else if (op == DIV)
        new DIVinstr();
      else
        new MODinstr();
    }

    TJ.output.decTreeDepth();
  }


  private static void expr1() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTexpr1);
    TJ.output.incTreeDepth();

    switch (getCurrentToken()) {
      //============================
      case LPAREN:
        nextToken();
        expr7();
        accept(RPAREN);
        break;
      case PLUS:
      case MINUS:
      case NOT:
       if(getCurrentToken() == PLUS){
         nextToken();
         expr1();
       }else if(getCurrentToken() == MINUS){
         nextToken();
         expr1();
         new CHANGESIGNinstr();
       }else{
         nextToken();
         expr1();
         new NOTinstr();
       }
       break;
      case UNSIGNEDINT:
        new PUSHNUMinstr(LexicalAnalyzer.getCurrentValue());
        nextToken();
        break;
      case NEW:
        nextToken();
        accept(INT);
        accept(LBRACKET);
        expr3();
        new HEAPALLOCinstr();
        accept(RBRACKET);
        while(getCurrentToken() == LBRACKET){
          nextToken();
          accept(RBRACKET);
        }
        break;
      case IDENT:
        String identName = LexicalAnalyzer.getCurrentSpelling();
        nextToken();
        if (getCurrentToken() == DOT) {
          VariableRec t = symTab.searchForVariable(identName);
          if (t == null)
            throw new SourceFileErrorException("Undeclared variable: " + identName);
          if (t.type == VariableRec.SCANNER) {
            nextToken();
            accept(NEXTINT);
            accept(LPAREN);
            accept(RPAREN);
            new READINTinstr();
          }
          else throw new SourceFileErrorException("Scanner variable expected");
        }
        else {
          int dimensionality;

          if (getCurrentToken() == LPAREN) {
            MethodRec t = lookUpCalledMethod(identName, MethodRec.INT);
            argumentList(t);
            CALLSTATMETHODinstr cInstr = new CALLSTATMETHODinstr(t.getStartAddr());
            if (t.getStartAddr() == Instruction.OPERAND_NOT_YET_KNOWN)
                             /* the method has not yet been declared */
              t.setCallsToBeFixedUp(new FixUpRec(cInstr, t.getCallsToBeFixedUp()));
            dimensionality = t.getDimensionCount();
          }
          else {
            VariableRec t = symTab.searchForVariable(identName);
            if (t == null)
              throw new SourceFileErrorException("Undeclared variable: " + identName);
            if (t.type != VariableRec.INT)
              throw new SourceFileErrorException("Integer variable expected");
            if (t instanceof LocalVariableRec)
              new PUSHLOCADDRinstr(t.offset);
            else
              new PUSHSTATADDRinstr(t.offset);
            new LOADFROMADDRinstr();
            dimensionality = t.dimensionCount;
          }

          while (getCurrentToken() == LBRACKET) {
            if (dimensionality-- == 0)
              throw new SourceFileErrorException("Unexpected index expression");
            nextToken();
            expr3();
            new ADDTOPTRinstr();
            new LOADFROMADDRinstr();
            accept(RBRACKET);
          }
        }

        break;

      case NULL:
        nextToken();
        new PUSHNUMinstr(0);
        break;


      default: throw new SourceFileErrorException("Malformed expression");
    }

    TJ.output.decTreeDepth();
  }

}

