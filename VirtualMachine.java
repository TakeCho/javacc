/**
 **     Mini-Compiler Virtual Machine
 **/

import java.io.*;

class VirtualMachine implements VMcodeConst {
    int nCode = 0;
    int opCode[];
    int subCode[];
    int nVar = 0;
    int varArea[];
    int nString = 0;
    String stringPool[];
    int sp = -1;
    final int STACKSIZE = 100;
    int IntStack[] = new int[STACKSIZE];
    int pc = 1;
    BufferedReader inStream = null;
    PrintStream outStream = null;

    void load(String file) {
        int i, op, sub;

        try {
            DataInputStream vmcod = 
                new DataInputStream(new FileInputStream(file));
            op = vmcod.readInt();
            nCode = vmcod.readInt() - 2;
            opCode = new int[nCode];
            subCode = new int[nCode];
            op = vmcod.readInt();
            sub = vmcod.readInt();
            for (i = 1; op != OP_NDCL; ++i) {
                opCode[i] = op;
                subCode[i] = sub;
                op = vmcod.readInt();
                sub = vmcod.readInt();
            }
            nVar = sub;
            varArea = new int[nVar];
            op = vmcod.readInt();
            nString = vmcod.readInt();
            stringPool = new String[nString];
            for (i = 0; i < nString; ++i) {
                stringPool[i] = vmcod.readUTF();
            }
        } catch (IOException e) {
            System.out.println("### VMcode input error.");
            e.printStackTrace();
            throw new Error();
        }
    }
    void execute(boolean debug, String infile, String outfile) {
        while (pc < nCode) {
            if (debug)
                trace(pc);
            switch (opCode[pc]) {
            case OP_POP:
                varArea[subCode[pc]] = IntStack[sp--];
                break;
//------------------------------------------------------------------------------
            case OP_POPX:
                varArea[subCode[pc]+IntStack[sp-1]] = IntStack[sp];
                sp-=2;
                break;

            case OP_PSHXI:
                //ここに処理を記述
                IntStack[sp] = varArea[subCode[pc]+IntStack[sp]];
                break;
//------------------------------------------------------------------------------
            case OP_PSHI:
                IntStack[++sp] = varArea[subCode[pc]];
                break;
            case OP_PSHN:
                IntStack[++sp] = subCode[pc];
                break;
            case OP_CAL:
                switch (subCode[pc]) {
                case ADD:
                    --sp;
                    IntStack[sp] =
                            IntStack[sp] + IntStack[sp + 1];
                    break;
                case SUB:
                    --sp;
                    IntStack[sp] =
                            IntStack[sp] - IntStack[sp + 1];
                    break;
                case MUL:
                    --sp;
                    IntStack[sp] =
                            IntStack[sp] * IntStack[sp + 1];
                    break;
                case DIV:
                    --sp;
                    IntStack[sp] =
                            IntStack[sp] / IntStack[sp + 1];
                    break;
                case MIN :
                    IntStack[sp] = - IntStack[sp];
                    break;
                case EQ:
                    --sp;
                    IntStack[sp] =
                            (IntStack[sp] == IntStack[sp + 1])
                            ? 1 : 0;
                    break;
                case GT:
                    --sp;
                    IntStack[sp] =
                            (IntStack[sp] > IntStack[sp + 1])
                            ? 1 : 0;
                    break;
                case GE:
                    --sp;
                    IntStack[sp] =
                            (IntStack[sp] >= IntStack[sp + 1])
                            ? 1 : 0;
                    break;
                case LS:
                    --sp;
                    IntStack[sp] =
                            (IntStack[sp] < IntStack[sp + 1])
                            ? 1 : 0;
                    break;
                case LE:
                    --sp;
                    IntStack[sp] =
                            (IntStack[sp] <= IntStack[sp + 1])
                            ? 1 : 0;
                    break;
                case NT:
                    --sp;
                    IntStack[sp] =
                            (IntStack[sp] != IntStack[sp + 1])
                            ? 1 : 0;
                    break;
                default:
                    break;
                }
            case OP_LBL:
                break;
            case OP_JMP:
                pc = subCode[pc];
                break;
            case OP_JPF:
                if (IntStack[sp--] == 0)
                    pc = subCode[pc];
                break;
            case OP_GET:
                try {
                    if (inStream == null) {
                        if (infile.equals("System.in"))
                            inStream = new BufferedReader(
                                    new InputStreamReader(
                                    System.in));
                        else
                            inStream = new BufferedReader(
                                    new FileReader(infile));
                    }
                    String line = inStream.readLine();
                    varArea[subCode[pc]] = 
                            (new Integer(line.trim()))
                            .intValue();
                } catch (IOException e) {
                    System.out.println(
                        "### I/O Error at Get statement.");
                    e.printStackTrace();
                    throw new Error();
                } catch (NumberFormatException e) {
                    System.out.println(
                        "### Number Error at Get statement.");
                    e.printStackTrace();
                    throw new Error();
                }
                break;
            case OP_PUTN:
            case OP_PUTS:
            case OP_PUTL:
                try {
                    if (outStream == null) {
                        if (outfile.equals("System.out"))
                            outStream = System.out;
                        else
                            outStream = new PrintStream(
                                new FileOutputStream(outfile));
                    }
                } catch (IOException e) {
                    System.out.println(
                            "### I/O Error at Put statement.");
                    e.printStackTrace();
                    throw new Error();
                }
                switch (opCode[pc]) {
                case OP_PUTN:
                    outStream.print(IntStack[sp--]);
                    break;
                case OP_PUTS:
                    outStream.print(stringPool[subCode[pc]]);
                    break;
                case OP_PUTL:
                    outStream.flush();
                    break;
                }
                break;
            default:
                break;
            }
            ++pc;
        }
    }

    void trace(int pc) {
        if (opCode[pc] == OP_CAL)
            System.out.println(pc + "\t\t" + opName[opCode[pc]]
                + "\t\t" + subName[subCode[pc]]);
        else
            System.out.println(pc + "\t\t" + opName[opCode[pc]]
                + "\t\t" + subCode[pc]);
    }

}
