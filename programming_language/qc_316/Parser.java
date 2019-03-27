//EN LIN
package TJ1asn;

import static TJlexer.LexicalAnalyzer.getCurrentToken;
import static TJlexer.LexicalAnalyzer.nextToken;
import static TJlexer.Symbols.*;
import TJlexer.Symbols;


// ************************************ Recursive Descent Parser **************************************

public final class Parser {

  private static void accept (Symbols expectedToken) throws SourceFileErrorException
  {
    if (getCurrentToken() == expectedToken)
      nextToken();
    else throw new SourceFileErrorException("Something's wrong--maybe the following token is missing: "
                          + expectedToken.symbolRepresentationForOutputFile);
  }


  static void program () throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTprogram);
    TJ.output.incTreeDepth();

    if (getCurrentToken() == IMPORT) importStmt();

    accept(CLASS);
    accept(IDENT);
    accept(LBRACE);

    while (getCurrentToken() == STATIC)
      dataFieldDecl();

    mainDecl();

    while (getCurrentToken() == STATIC)
      methodDecl();

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

    if(getCurrentToken() == INT){
      nextToken();
      singleVarDecl();
      while (getCurrentToken() == COMMA) {
        nextToken();
        singleVarDecl();
      }
      accept(SEMICOLON);
    }else if (getCurrentToken() == SCANNER) {
      nextToken();
      if (getCurrentToken() == IDENT) {
        nextToken();
      }
      else
        throw new SourceFileErrorException("Scanner name expected");

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
    //==============================X
    if(getCurrentToken() == IDENT){
      nextToken();
      while(getCurrentToken() == LBRACKET){
        nextToken();
        accept(RBRACKET);
      }
      if(getCurrentToken() == BECOMES){
        nextToken();
        expr3();
      }
    }else throw new SourceFileErrorException("Variable name expected");
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

    compoundStmt();

    TJ.output.decTreeDepth();
  }


  private static void methodDecl() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTmethodDecl);
    TJ.output.incTreeDepth();
    //=====================================x
    if(getCurrentToken() == STATIC){
      nextToken();
      if(getCurrentToken() == VOID){
        nextToken();
      }else if(getCurrentToken() == INT){
        nextToken();
        while(getCurrentToken() == LBRACKET){
          nextToken();
          accept(RBRACKET);
        }
      }else throw new SourceFileErrorException("\"int\" or \"void\" expected");

      if(getCurrentToken() == IDENT){
        nextToken();
      }else throw new SourceFileErrorException("Method name expected");
      accept(LPAREN);
      parameterDeclList();
      accept(RPAREN);
      compoundStmt();
    }else throw new SourceFileErrorException("Keyword \"static\" expected");
    TJ.output.decTreeDepth();
  }


  private static void parameterDeclList() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTparameterDeclList);
    TJ.output.incTreeDepth();

    if (getCurrentToken() == INT) {
        parameterDecl();
        while (getCurrentToken() == COMMA) {
          nextToken();
          parameterDecl();
        }
    }
    else TJ.output.printSymbol(EMPTY);

    TJ.output.decTreeDepth();
  }


  private static void parameterDecl() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTparameterDecl);
    TJ.output.incTreeDepth();

    accept(INT);
    accept(IDENT);
    while (getCurrentToken() == LBRACKET) {
          nextToken();
          accept(RBRACKET);
    }

    TJ.output.decTreeDepth();
  }


  private static void compoundStmt() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTcompoundStmt);
    TJ.output.incTreeDepth();
    //=====================================X
    accept(LBRACE);
    while(getCurrentToken()!= RBRACE){statement();}
    accept(RBRACE);
    TJ.output.decTreeDepth();
  }


  private static void statement() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTstatement);
    TJ.output.incTreeDepth();

    switch (getCurrentToken()) {
      case SEMICOLON: nextToken(); break;
      case RETURN: nextToken();
                           if (getCurrentToken() != SEMICOLON)
                             expr3();
                           accept(SEMICOLON);
                           break;
      case INT: case SCANNER: varDecl(); break;
      case IDENT: assignmentOrInvoc(); break;
      case LBRACE: compoundStmt(); break;
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
    //==============================
    if(getCurrentToken() == IDENT){
      nextToken();
      if(getCurrentToken() == LPAREN){
        argumentList();
        accept(SEMICOLON);
      }else{
        while(getCurrentToken() == LBRACKET){
          nextToken();
          expr3();
          accept(RBRACKET);
        }
        accept(BECOMES);
        expr3();
        accept(SEMICOLON);
      }
    }else throw new SourceFileErrorException("Variable name or method name expected");
    TJ.output.decTreeDepth();
  }


  private static void argumentList() throws SourceFileErrorException
  {
     TJ.output.printSymbol(NTargumentList);
     TJ.output.incTreeDepth();
     //======================================x
     accept(LPAREN);
     if(getCurrentToken() != RPAREN){
       expr3();
       while(getCurrentToken() == COMMA){
         nextToken();
         expr3();
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
    statement();
    if (getCurrentToken() == ELSE) {
      nextToken();
      statement();
    }

    TJ.output.decTreeDepth();
  }


  private static void whileStmt() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTwhileStmt);
    TJ.output.incTreeDepth();
    //=================================x
    accept(WHILE);
    accept(LPAREN);
    expr7();
    accept(RPAREN);
    statement();
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

      /*==========================x

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
    //====================================
    if(getCurrentToken() == CHARSTRING){
      nextToken();
    }else expr3();

    TJ.output.decTreeDepth();
  }


  private static void expr7() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTexpr7);
    TJ.output.incTreeDepth();
    //==================================x
    expr6();
    while(getCurrentToken() == OR){
      nextToken();
      expr6();
    }
    TJ.output.decTreeDepth();
  }


  private static void expr6() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTexpr6);
    TJ.output.incTreeDepth();
    //========================x
    expr5();
    while(getCurrentToken() == AND){
      nextToken();
      expr5();
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
      nextToken();
      expr4();
    }
    TJ.output.decTreeDepth();
  }


  private static void expr4() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTexpr4);
    TJ.output.incTreeDepth();
    //==============================x
    expr3();
    if(getCurrentToken() == GT || getCurrentToken() == LT ||
       getCurrentToken() == GE || getCurrentToken() == LE){
      nextToken();
      expr3();
    }
    TJ.output.decTreeDepth();
  }


  private static void expr3() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTexpr3);
    TJ.output.incTreeDepth();
    //==============================x
    expr2();
    while(getCurrentToken() == PLUS || getCurrentToken() == MINUS){
      nextToken();
      expr2();
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

      nextToken();

      expr1();
    }

    TJ.output.decTreeDepth();
  }


  private static void expr1() throws SourceFileErrorException
  {
    TJ.output.printSymbol(NTexpr1);
    TJ.output.incTreeDepth();

    switch (getCurrentToken()) {

      /*=====================================

      default: throw new SourceFileErrorException("Malformed expression");

      */
      case LPAREN: nextToken();
                   expr7();
                   accept(RPAREN);
                   break;
      case PLUS:
      case MINUS:
      case NOT:    nextToken();
                   expr1();
                   break;
      case UNSIGNEDINT:
      case NULL:   nextToken();
                   break;
      case NEW:    nextToken();
                   accept(INT);
                   accept(LBRACKET);
                   expr3();
                   accept(RBRACKET);
                   while(getCurrentToken() == LBRACKET){
                     nextToken();
                     accept(RBRACKET);
                   }
                   break;
      case IDENT:  nextToken();
                   if(getCurrentToken() == DOT){
                     nextToken();
                     accept(NEXTINT);
                     accept(LPAREN);
                     accept(RPAREN);
                   }else{
                     if(getCurrentToken() == LPAREN){
                       argumentList();
                     }
                     while(getCurrentToken() == LBRACKET){
                       nextToken();
                       expr3();
                       accept(RBRACKET);
                     }
                   }
                   break;
      default: throw new SourceFileErrorException("Malformed expression");


    }

    TJ.output.decTreeDepth();
  }
}

