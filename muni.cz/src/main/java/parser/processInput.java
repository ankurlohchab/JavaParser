package parser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class processInput {
int count=0;

    public String line;
    public Path pathToFile;
    public File fout;
    public FileOutputStream fos = null;
    public BufferedWriter bw;
    public BufferedReader br;

    public String inputLoc;
    public String manipulations;
    public String outType;
    public String outputLoc;

    static List<String> email_list = new ArrayList<>();
    static String[] attributes=null;

    long paid_total_18=0, unpaid_total_18=0;
    long paid_total_19=0, unpaid_total_19=0;
    int paid_count_18=0, unpaid_count_18=0;
    int paid_count_19=0, unpaid_count_19=0;
    float paid_avg_18=0,unpaid_avg_18=0;
    float paid_avg_19=0,unpaid_avg_19=0;

    processInput(String inputLoc, String manipulation, String outType, String outputLoc)
    {
        this.inputLoc=inputLoc;
        this.manipulations=manipulation;
        this.outType=outType;
        this.outputLoc=outputLoc;
    }

    void validateInputFileExistance(){

        try {
            pathToFile = Paths.get(inputLoc);
            br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII);
        }
        catch(IOException ioe){
            // ioe.printStackTrace();
            System.out.println("Incorrect input file path... exiting...please see -help for format...");
            System.exit(0);
        }
    }

    public void validateOutputPath() {
        fout = new File(outputLoc);
        try {
            fos = new FileOutputStream(fout);
        } catch (FileNotFoundException ex) {
            System.out.println("The output file path is incorrect...please provide correct path...");
            System.exit(0);
        }
        bw = new BufferedWriter(new OutputStreamWriter(fos));

    }

    public void processFileData() {
        validateInputFileExistance();
        validateOutputPath();
        processDataLineByLine();

    }

    void processDataLineByLine()  {

        try {

            line = br.readLine();

            while (line != null) {
                attributes = line.split(",");
                if (manipulations.matches("3") || manipulations.matches("2") || manipulations.matches("1"))
                    removeItems();
                updateLedger();
                line = br.readLine();
            }

                if (manipulations.matches("4") || manipulations.matches("7"))
                    getYearwisePaidUnpaidAvg();

                if (manipulations.matches("5") || manipulations.matches("7"))
                    getTotalYearWiseOrders();

                if (manipulations.matches("6") || manipulations.matches("7"))
                    getTopThreeCustomers();
            bw.close();
        }

        catch(IOException ioe){
            System.out.println("Incorrect input file path... exiting...please see -help for format...");
            System.exit(0);
        }
    }

    void getTopThreeCustomers() throws IOException {
        // if user has selected option 6 or 7 as input option
            Map<String,Long> map = email_list.stream().collect(Collectors.groupingBy(w -> w,Collectors.counting()));

            List<Map.Entry<String, Long>> result = map.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(3)
                    .collect(Collectors.toList());

           dispatchToResults("Top three orders are : " + result);

    }


    void getTotalYearWiseOrders() throws IOException {

        dispatchToResults("Total paid orders for year 2018 are: "+paid_total_18);
        dispatchToResults("Total paid orders for year 2018 are: "+paid_total_19);
    }

    void getYearwisePaidUnpaidAvg() throws IOException {

        calculateAvgResults();
        produceAvgResults();
    }

    private void produceAvgResults() throws IOException {
        dispatchToResults("Unpaid Avg yr 2019= "+ paid_avg_18);
        dispatchToResults("Unpaid Avg yr 2019= "+ unpaid_avg_18);
        dispatchToResults("Unpaid Avg yr 2019= "+ paid_avg_19);
        dispatchToResults("Unpaid Avg yr 2019= "+ unpaid_avg_19);
    }

    private void calculateAvgResults() {
        paid_avg_18=(float)paid_total_18/(float)paid_count_18;
        unpaid_avg_18=(float)unpaid_total_18/(float)unpaid_count_18;
        paid_avg_19=(float)paid_total_19/(float)paid_count_19;
        unpaid_avg_19=(float)unpaid_total_19/(float)unpaid_count_19;
    }

    void removeItems() throws IOException {

        // condition one

        if (manipulations.matches("3")) {

            if (!((attributes[2].matches("")) && (attributes[3].matches("")))) {
                dispatchToResults(line);
            }

        } else if (manipulations.matches("1")) {
            if (!(attributes[2].matches(""))) {
                dispatchToResults(line);
            }
        } else if (manipulations.matches("2")) {
            if (!(attributes[3].matches(""))) {
                dispatchToResults(line);
            }
        }
    }

    private void dispatchToResults(String line) throws IOException {

        bw.write(line);
        bw.newLine();
        System.out.println(line);
    }

    void updateLedger()
    {

        //avg price of order per year paid and unpaid
        if(attributes[5].matches("PAID"))
        {
            updatePaidLedger();
        }
        if(attributes[5].matches("UNPAID"))
        {
            updateUnpaidLedger();
        }

        if(!attributes[2].matches(""))
            email_list.add(attributes[2]);
    }

    private void updateUnpaidLedger() {
        if(attributes[1].contains("2018"))
        {
            unpaid_total_18 += Long.parseLong(attributes[4]);
            unpaid_count_18++;
        }
        else if (attributes[1].contains("2019"))
        {
            unpaid_total_19 += Long.parseLong(attributes[4]);
            unpaid_count_19++;
        }
    }

    private void updatePaidLedger() {
        if(attributes[1].contains("2018"))
        {
            paid_total_18 += Long.parseLong(attributes[4]);
            paid_count_18++;
        }
        else if (attributes[1].contains("2019"))
        {
            paid_total_19 += Long.parseLong(attributes[4]);
            paid_count_19++;
        }
    }


}
