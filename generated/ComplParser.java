/* Generated By:JavaCC: Do not edit this line. ComplParser.java */
import java.io.*;

public class ComplParser implements VMcodeConst, ComplParserConstants {

    CodeGenerater codeGen = null;               // --> <4>
    public int nParseErrors = 0;                // --> <13>

    public ComplParser(FileReader fr, CodeGenerater cg) {
        this(fr);
        codeGen = cg;
    }

  final public void ComplUnit() throws ParseException {
    trace_call("ComplUnit");
    try {
    Token t;
      label_1:
      while (true) {
        ;
        try {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case VAR:
            VarDecl();
            break;
          case LC:
          case SM:
          case IF:
          case WHILE:
          case PUT:
          case GET:
          case IDENT:
            Stat();
            break;
          case 0:
            jj_consume_token(0);
                          {if (true) return;}
            break;
          default:
            jj_la1[0] = jj_gen;
            ErrorOccur();
          }
        } catch (ParseException e) {
                ++nParseErrors;
                System.out.println("### " + e.getMessage());
                do {
                    t = getNextToken();
                } while (t.kind != SM && t.kind != RC
                        && t.kind != EOF);
        }
      }
    } finally {
      trace_return("ComplUnit");
    }
  }

                                                // --> <8>
  void ErrorOccur() throws ParseException {
    trace_call("ErrorOccur");
    try {
        ParseException e = generateParseException();
        throw e;
    } finally {
      trace_return("ErrorOccur");
    }
  }

  final public void VarDecl() throws ParseException {
    trace_call("VarDecl");
    try {
    Token t;
      jj_consume_token(VAR);
      t = jj_consume_token(IDENT);
      jj_consume_token(SM);
                if (codeGen.newSymbol(t.image) == -1)
                    {if (true) throw new ParseException(
                            "Variable multi-defined : \u005c""
                            + t.image + "\u005c" at line "
                            + t.beginLine + ", column "
                            + t.beginColumn + "." );}
                                                // --> <11>

    } finally {
      trace_return("VarDecl");
    }
  }

  final public void Stat() throws ParseException {
    trace_call("Stat");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENT:
        AssignStat();
        break;
      case IF:
        IfStat();
        break;
      case WHILE:
        WhileStat();
        break;
      case GET:
        GetStat();
        break;
      case PUT:
        PutStat();
        break;
      case LC:
        Block();
        break;
      case SM:
        jj_consume_token(SM);
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("Stat");
    }
  }

  final public void AssignStat() throws ParseException {
    trace_call("AssignStat");
    try {
    ExprNode e;
    int i;
      i = Ident();
      jj_consume_token(ASGNOP);
      e = AddExpr();
      jj_consume_token(SM);
                codeGen.genExpr(e);
                codeGen.genVMcode(OP_POP, i);
    } finally {
      trace_return("AssignStat");
    }
  }

  final public void IfStat() throws ParseException {
    trace_call("IfStat");
    try {
    int l1 = -1, l2 = -1;
    ExprNode c;
      jj_consume_token(IF);
      jj_consume_token(LP);
      c = Cond();
      jj_consume_token(RP);
                codeGen.genExpr(c);
                codeGen.genVMcode(
                        OP_JPF, l1 = codeGen.newLabel());
      Stat();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ELSE:
        jj_consume_token(ELSE);
                    codeGen.genVMcode(
                            OP_JMP, l2 = codeGen.newLabel());
                    codeGen.genVMcode(OP_LBL, l1);
        Stat();
                    codeGen.genVMcode(OP_LBL, l2);
        break;
      default:
        jj_la1[2] = jj_gen;
                    codeGen.genVMcode(OP_LBL, l1);
      }
    } finally {
      trace_return("IfStat");
    }
  }

  final public void WhileStat() throws ParseException {
    trace_call("WhileStat");
    try {
    int l1 = -1, l2 = -1;
    ExprNode c;
      jj_consume_token(WHILE);
      jj_consume_token(LP);
      c = Cond();
      jj_consume_token(RP);
                codeGen.genVMcode(
                        OP_LBL, l1 = codeGen.newLabel());
                codeGen.genExpr(c);
                codeGen.genVMcode(
                        OP_JPF, l2 = codeGen.newLabel());
      Stat();
                codeGen.genVMcode(OP_JMP, l1);
                codeGen.genVMcode(OP_LBL, l2);
    } finally {
      trace_return("WhileStat");
    }
  }

  final public void GetStat() throws ParseException {
    trace_call("GetStat");
    try {
    int i;
      jj_consume_token(GET);
      jj_consume_token(LP);
      i = Ident();
      jj_consume_token(RP);
      jj_consume_token(SM);
                codeGen.genVMcode(OP_GET, i);
    } finally {
      trace_return("GetStat");
    }
  }

  final public void PutStat() throws ParseException {
    trace_call("PutStat");
    try {
    int i = 0;
    ExprNode e;
      jj_consume_token(PUT);
      jj_consume_token(LP);
      PutParam();
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case CM:
          ;
          break;
        default:
          jj_la1[3] = jj_gen;
          break label_2;
        }
        jj_consume_token(CM);
        PutParam();
      }
      jj_consume_token(RP);
      jj_consume_token(SM);
                codeGen.genVMcode(OP_PUTL, 0);
    } finally {
      trace_return("PutStat");
    }
  }

  final public void PutParam() throws ParseException {
    trace_call("PutParam");
    try {
    int i = 0;
    ExprNode e = null;
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SUBOP:
      case LP:
      case LITERAL:
      case IDENT:
        //              try {                           // --> <23>
        
                e = AddExpr();
                codeGen.genExpr(e);
                codeGen.genVMcode(OP_PUTN, 0);
        break;
      case STRLIT:
        i = StrLit();
                codeGen.genVMcode(OP_PUTS, i);
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("PutParam");
    }
  }

  final public void Block() throws ParseException {
    trace_call("Block");
    try {
      jj_consume_token(LC);
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LC:
        case SM:
        case IF:
        case WHILE:
        case PUT:
        case GET:
        case IDENT:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_3;
        }
        Stat();
      }
      jj_consume_token(RC);
    } finally {
      trace_return("Block");
    }
  }

  final public ExprNode Cond() throws ParseException {
    trace_call("Cond");
    try {
    ExprNode l = null, r = null, tr = null;
      l = AddExpr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LSOP:
        jj_consume_token(LSOP);
        r = AddExpr();
                    tr = new ExprNode(LS, l, r);
        break;
      case GTOP:
        jj_consume_token(GTOP);
        r = AddExpr();
                    tr = new ExprNode(GT, l, r);
        break;
      case LEOP:
        jj_consume_token(LEOP);
        r = AddExpr();
                    tr = new ExprNode(LE, l, r);
        break;
      case GEOP:
        jj_consume_token(GEOP);
        r = AddExpr();
                    tr = new ExprNode(GE, l, r);
        break;
      case EQOP:
        jj_consume_token(EQOP);
        r = AddExpr();
                    tr = new ExprNode(EQ, l, r);
        break;
      case NTOP:
        jj_consume_token(NTOP);
        r = AddExpr();
                    tr = new ExprNode(NT, l, r);
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                {if (true) return tr;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("Cond");
    }
  }

  final public ExprNode AddExpr() throws ParseException {
    trace_call("AddExpr");
    try {
    ExprNode l = null, r = null;
      l = MulExpr();
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ADDOP:
        case SUBOP:
          ;
          break;
        default:
          jj_la1[7] = jj_gen;
          break label_4;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ADDOP:
          jj_consume_token(ADDOP);
          r = MulExpr();
                    l = new ExprNode(ADD, l, r);
          break;
        case SUBOP:
          jj_consume_token(SUBOP);
          r = MulExpr();
                    l = new ExprNode(SUB, l, r);
          break;
        default:
          jj_la1[8] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
                {if (true) return l;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("AddExpr");
    }
  }

  final public ExprNode MulExpr() throws ParseException {
    trace_call("MulExpr");
    try {
    ExprNode l = null, r = null;
      l = UnExpr();
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case MULOP:
        case DIVOP:
          ;
          break;
        default:
          jj_la1[9] = jj_gen;
          break label_5;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case MULOP:
          jj_consume_token(MULOP);
          r = UnExpr();
                    l = new ExprNode(MUL, l, r);
          break;
        case DIVOP:
          jj_consume_token(DIVOP);
          r = UnExpr();
                    l = new ExprNode(DIV, l, r);
          break;
        default:
          jj_la1[10] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
                {if (true) return l;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("MulExpr");
    }
  }

  final public ExprNode UnExpr() throws ParseException {
    trace_call("UnExpr");
    try {
    ExprNode l = null;
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SUBOP:
        jj_consume_token(SUBOP);
        l = UnExpr();
                {if (true) return new ExprNode(MIN, l, null);}
        break;
      case LP:
      case LITERAL:
      case IDENT:
        l = PrimExpr();
                {if (true) return l;}
        break;
      default:
        jj_la1[11] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("UnExpr");
    }
  }

  final public ExprNode PrimExpr() throws ParseException {
    trace_call("PrimExpr");
    try {
    ExprNode l = null;
    int n = 0, i = 0;
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LITERAL:
        n = Literal();
                {if (true) return new ExprNode(NUM, n);}
        break;
      case IDENT:
        i = Ident();
                {if (true) return new ExprNode(ID, i);}
        break;
      case LP:
        jj_consume_token(LP);
        l = AddExpr();
        jj_consume_token(RP);
                {if (true) return l;}
        break;
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("PrimExpr");
    }
  }

  final public int Ident() throws ParseException {
    trace_call("Ident");
    try {
    Token t;
    int n;
      t = jj_consume_token(IDENT);
                if ((n = codeGen.getSymbol(t.image)) == -1) {
                    {if (true) throw new ParseException(
                            "Variable not defined : \u005c""
                        + t.image + "\u005c" at line " + t.beginLine
                        + ", column " + t.beginColumn + "." );}
                                                // --> <12>
                } else
                    {if (true) return n;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("Ident");
    }
  }

  final public int Literal() throws ParseException {
    trace_call("Literal");
    try {
    Token t;
      t = jj_consume_token(LITERAL);
                try {
                    {if (true) return Integer.parseInt(t.image);}
                } catch (Exception e) {
                    {if (true) throw new ParseException(
                            "Invalid literal : \u005c""
                            + t.image + "\u005c" at line "
                            + t.beginLine + ", column "
                            + t.beginColumn + ".");}
                }
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("Literal");
    }
  }

  final public int StrLit() throws ParseException {
    trace_call("StrLit");
    try {
    Token t;
      t = jj_consume_token(STRLIT);
                {if (true) return codeGen.pool(t.image);}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("StrLit");
    }
  }

  /** Generated Token Manager. */
  public ComplParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[13];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x1da20001,0x1d220000,0x2000000,0x400000,0x80080080,0x1d220000,0x1f800,0xc0,0xc0,0x300,0x300,0x80080080,0x80080000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x1,0x1,0x0,0x0,0x5,0x1,0x0,0x0,0x0,0x0,0x0,0x1,0x1,};
   }

  /** Constructor with InputStream. */
  public ComplParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public ComplParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ComplParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public ComplParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ComplParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public ComplParser(ComplParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ComplParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      trace_token(token, "");
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
      trace_token(token, " (in getNextToken)");
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[42];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 13; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 42; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  private int trace_indent = 0;
  private boolean trace_enabled = true;

/** Enable tracing. */
  final public void enable_tracing() {
    trace_enabled = true;
  }

/** Disable tracing. */
  final public void disable_tracing() {
    trace_enabled = false;
  }

  private void trace_call(String s) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.println("Call:   " + s);
    }
    trace_indent = trace_indent + 2;
  }

  private void trace_return(String s) {
    trace_indent = trace_indent - 2;
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.println("Return: " + s);
    }
  }

  private void trace_token(Token t, String where) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.print("Consumed token: <" + tokenImage[t.kind]);
      if (t.kind != 0 && !tokenImage[t.kind].equals("\"" + t.image + "\"")) {
        System.out.print(": \"" + t.image + "\"");
      }
      System.out.println(" at line " + t.beginLine + " column " + t.beginColumn + ">" + where);
    }
  }

  private void trace_scan(Token t1, int t2) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.print("Visited token: <" + tokenImage[t1.kind]);
      if (t1.kind != 0 && !tokenImage[t1.kind].equals("\"" + t1.image + "\"")) {
        System.out.print(": \"" + t1.image + "\"");
      }
      System.out.println(" at line " + t1.beginLine + " column " + t1.beginColumn + ">; Expected token: <" + tokenImage[t2] + ">");
    }
  }

}
