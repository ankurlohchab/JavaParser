package parser;

public class parseArgs {

public static void validateArgs(String[]args)
{
    checkForHelp(args);

    
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

    //TODO validate if the arguments in correct order
    // TODO flag error if unvalid arguments
    // TODO Display help for args format

}
