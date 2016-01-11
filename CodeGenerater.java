/**
 **     VMcode Generation
 **/

import java.io.*;
import java.util.*;

public class CodeGenerater implements VMcodeConst {

    int nLabel = 0, nSymbol = 0;
    Hashtable symTable = new Hashtable();
    int nCode = 0;
    int opCode[] = new int[CODESIZE];
    int subCode[] = new int[CODESIZE];
    int lblTable[] = new int[LBLSIZE];
    int nString = 0;
    String stringPool[] = new String[POOLSIZE];
    DataOutputStream dataStream = null;

    public CodeGenerater(DataOutputStream ds) {
        dataStream = ds;
        genVMcode(OP_NCOD, 0);
   }

    int newLabel() {
        return nLabel++;
    }

    int newSymbol(String str) {
        if (symTable.containsKey(str))
            return -1;
        symTable.put(str, new Integer(nSymbol));
        return nSymbol++;
    }

    int getSymbol(String str) {
        Object o = symTable.get(str);

        if (o == null)
            return -1;
        else
            return ((Integer)o).intValue();
    }

    int pool(String str) {
        stringPool[nString] = str;
        return nString++;
    }

    void genVMcode(int op, int sub) {
        opCode[nCode] = op;
        subCode[nCode++] = sub;
    }

    void genExpr(ExprNode node) {               // --> <1>
        if (node == null)
            return;
        if (node.left != null)
            genExpr(node.left);
        if (node.right != null)
            genExpr(node.right);
        switch (node.type) {
        case ID:
            genVMcode(OP_PSHI, node.value);
            break;
        case NUM:
            genVMcode(OP_PSHN, node.value);
            break;
        default:
            genVMcode(OP_CAL, node.type);
            break;
        }
    }

    void defref() {
    int i;
    
    for (i = 0; i < nCode; ++i)
        if (opCode[i] == OP_LBL)
            lblTable[subCode[i]] = i;
    for (i = 0; i < nCode; ++i)
        if (opCode[i] == OP_JMP || opCode[i] == OP_JPF)
            subCode[i] = lblTable[subCode[i]];
    }

    void closeCodes() {
        defref();
        genVMcode(OP_NDCL, nSymbol);
        genVMcode(OP_NSTR, nString);
        subCode[0] = nCode;
    }

    void writeCodes() throws Exception {
        int i;

        for (i = 0; i < nCode; ++i) {
            dataStream.writeInt(opCode[i]);
            dataStream.writeInt(subCode[i]);
        }
        for (i = 0; i < nString; ++i) {
            dataStream.writeUTF(stringPool[i]);
        }
    }

    void dumpCodes() {
    int i;

        System.out.println("Addr\tOPcode\t\tSUBcode");
        for (i = 0; i < nCode; ++i) {
            if (opCode[i] == OP_CAL)
                System.out.println(i + "\t" + opName[opCode[i]]
                    + "\t\t" + subName[subCode[i]]);
            else
                System.out.println(i + "\t" + opName[opCode[i]]
                    + "\t\t" + subCode[i]);
        }
        for (i = 0; i < nString; ++i)
            System.out.println(i + "\t" + stringPool[i]);
    }
}

