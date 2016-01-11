/**
 **     VMcode Constants
 **/

public interface VMcodeConst {

    int OP_POP  =  1;
    int OP_PSHI =  2;
    int OP_PSHN =  3;
    int OP_CAL  =  4;
    int OP_LBL  =  5;
    int OP_JMP  =  6;
    int OP_JPF  =  7;
    int OP_GET  =  8;
    int OP_PUTN =  9;
    int OP_PUTS = 10;
    int OP_PUTL = 11;
    int OP_NCOD = 12;
    int OP_NDCL = 13;
    int OP_NSTR = 14;

    int ADD =  1;
    int SUB =  2;
    int MUL =  3;
    int DIV =  4;
    int MIN =  5;
    int EQ  =  6;
    int GT  =  7;
    int GE  =  8;
    int LS  =  9;
    int LE  = 10;
    int NT  = 11;

    int ID  = 100;
    int NUM = 101;
    int STR = 102;

    int CODESIZE = 1000;
    int POOLSIZE = 100;
    int LBLSIZE = 100;

    String opName[] = {
        "",
        "OP_POP",
        "OP_PSHI",
        "OP_PSHN",
        "OP_CAL",
        "OP_LBL",
        "OP_JMP",
        "OP_JPF",
        "OP_GET",
        "OP_PUTN",
        "OP_PUTS",
        "OP_PUTL",
        "OP_NCOD",
        "OP_NDCL",
        "OP_NSTR"
    };

    String subName[] = {
        "",
        "ADD",
        "SUB",
        "MUL",
        "DIV",
        "MIN",
        "EQ",
        "GT",
        "GE",
        "LS",
        "LE",
        "NT"
    };

}
