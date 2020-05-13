package parser;

public class parseArgs {

public static void validateArgs(String[]args)
{
    checkForHelp(args);
    try
    {
        if(!(args[0].matches("-d") && (!args[1].isEmpty())  && args[2].matches("-m") && (!args[3].isEmpty()) && args[4].matches("-o")  && (!args[5].isEmpty()) && (!args[6].isEmpty()) ))
            throw new IllegalArgumentException("illegal arguemtns");
    }
    catch (IllegalArgumentException | ArrayIndexOutOfBoundsException iae)

    {
        System.out.println("exiting...Please pass the arguements in the format -d [DATASETLOCATION] -m [MANIPULATIONMETHODS]-o [OUTPUTTYPE] [OUTPUTFILE]!");
        System.exit(0);
    }

}

    private static void checkForHelp(String[] args) {
        try{
            if(args[0].matches("-help") || args[0].matches("-h"))
                throw new IllegalArgumentException("help ");
        }
        catch (IllegalArgumentException |ArrayIndexOutOfBoundsException help)
        {
            System.out.println("Usage: java -jar solution.jar \n -d [dataset complete address] \n -m [1 : removeing item with blank email, \n\t  2: removing item with blank address, \n\t 3: <both options 1 and 2>"
                    + "\n\t 4: avg price of orders per year, \n\t 5 : total price of order per year, \n\t 6: top three customers with max no of orders, \n\t 7 : <option 4,5, and 6 combined>  ] \n -o text [output file address]");
            System.exit(0);
        }
    }


    public static String getInputLocation(String[] args) {
    return args[1];
    }

    public static String getManipulations(String[] args) {
    return args[3];
    }

    public static String getOutType(String[] args) {
    return args[5];
    }

    public static String getOutLoc(String[] args) {
    return args[6];
    }
}
