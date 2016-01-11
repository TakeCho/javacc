/**
 **     Mini-Compiler Main
 **/

import java.io.*;

class ComplMain {

    public static void main(String args[]) {
        ComplParser parser;
        boolean debug = false;
        int startindex = 0;
        CodeGenerater codegen;

        if (args.length >= 1) {
            if (args[0].equals("-D")) {         // --> <5>
                debug = true;
                startindex = 1;
            }
        }
        if (args.length >= startindex + 2) {
            try {
                codegen = new CodeGenerater(new DataOutputStream(
                    new FileOutputStream(args[startindex + 1])));
            } catch (FileNotFoundException e) {
                System.out.println(
                        "### VMcode file not found : \""
                        + args[startindex + 1] + "\".");
                System.out.println("### " + e.getMessage());
                return;
            }
            try {
                parser = new ComplParser(
                    new FileReader(args[startindex]), codegen);
                                                // --> <3>
            } catch (FileNotFoundException e) {
                System.out.println("### Program not found : \""
                    + args[startindex] + "\".");
                System.out.println("### " + e.getMessage());
                return;
            }
        } else {
            System.out.println("### File not specified.");
            return;
        }
        try {
            if (debug)                          // --> <4>
                parser.enable_tracing();
            else
                parser.disable_tracing();
            parser.ComplUnit();
            codegen.closeCodes();
            if (debug)
                codegen.dumpCodes();
            if (parser.nParseErrors !=0         // --> <2>
                    || parser.token_source.nLexicalErrors != 0) {
                System.out.println("... VMcode not created.");
                return;
            }
            codegen.writeCodes();
        } catch (ParseException e) {            // --> <1>
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }
}
