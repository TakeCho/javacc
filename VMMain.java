/**
 **     Virtual machine Main
 **/

class VMMain {

    public static void main(String args[]) {
        VirtualMachine vm;
        String infile, outfile;
        boolean debug;
        int startindex;

        if (args.length >= 1 && args[0].equals("-D")) {
            debug = true;
            startindex = 1;
        } else {
            debug = false;
            startindex = 0;
        }
        if (args.length >= startindex + 1) {
            vm = new VirtualMachine();
            vm.load(args[startindex]);
        } else {
            System.out.println("### VMCode not specified.");
            return;
        }
        if (args.length >= startindex + 3) {
            infile = args[startindex + 1];
            outfile = args[startindex + 2];
        } else {
            infile = "System.in";
            outfile = "System.out";
        }
        vm.execute(debug, infile, outfile);
        return;
    }

}
