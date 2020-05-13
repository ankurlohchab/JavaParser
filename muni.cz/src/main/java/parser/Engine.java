package parser;

public class Engine {
public String input_loc;
public String manipulations;
public String out_type;
public String output_loc;


    public void run(String[] args) {
    parseArgs.validateArgs(args);
    getArgsData(args);


    }

    private void getArgsData(String[] args) {
        input_loc = parseArgs.getInputLocation(args);
        manipulations=parseArgs.getManipulations(args);
        out_type=parseArgs.getOutType(args);
        output_loc=parseArgs.getOutLoc(args);
    }


}
