package gitlet;

import java.util.Date;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author ZhouHanzhen
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if (args.length == 0) {
            Utils.message("Please enter a command.;");
            System.exit(0);
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                validateNumArgs(args, 1);
                Repository.init();
                 break;
            case "add":
                // TODO: handle the `add [filename]` command
                break;
            // TODO: FILL THE REST IN


            default:
                Utils.message("No command with that name exists.;");
                System.exit(0);
                break;
        }
    }

    public static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            Utils.message("Incorrect operands.;");
            System.exit(0);
        }
    }
}
