import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Reads a file containing data in the order of 
 * [zip code, town name, state code, population, male population, female population]
 * then stores its contents. Takes a user-inputted zip code, then
 * searches the file for the place with the matching zip code, 
 * and prints the town name and state code to the terminal.
 * Typing "exit" instead of a zip code will exit the program.
 * 
 * @author jmccarty
 * created Feb 24 2021
 */

public class Places {
    private boolean lookUpAgain = true; //determines whether program will ask for another zip code
    private Place[] usZipCodes; //stores Place data from designated file

    public static void main(String[] args){
        Places unitedStates = new Places();
        unitedStates.lookUpLoop();
    }

    /**
     * uses enterZip() and lookupZip() to collect user-inputted zip code
     * and look it up from the usZipCodes array.
     * It then prints the corresponding town name and state code
     * to the terminal. Loops until user types "exit" into the terminal.
     */
    public void lookUpLoop(){
        System.out.println("========================================================");
        System.out.println("If at any time you wish to exit the program, type 'exit'");
        System.out.println("========================================================");

        //catches the IOException thrown by readZipCodes()
        try{
            this.readZipCodes("uszipcodes.csv"); //inputs data from "uszipcodes.csv"
        }catch(IOException io){
            System.err.println("Reading " + io);
        }

        //prompts user for zip code [this.enterZip()] ands stores response
        //then prints town and state code of zip to terminal [this.lookupZip()]
        while(lookUpAgain == true){
            String myZip = this.enterZip();
            this.lookupZip(myZip);
        }
    }

    /**
     * Reads a zip code file, then stores each line in usZipCodes
     * Catches FileNotFoundException
     * @param filename name of data file
     * @throws IOException
     */
    public void readZipCodes(String filename) throws IOException {
        //reads filename line by line
        //splits each line by comma placement
        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();//reads first line of file
            String[] partsOfLine = line.split(",");//splits each line by comma placement

            int numZips = Integer.parseInt(partsOfLine[0]); //number of zip codes in file
            Place[] allZips = new Place[numZips]; //array with all places from zip file

            //stores each line's data as a Place
            for(int i = 0; i < numZips; i++){
                String nextLine = br.readLine();//reads first line of file
                String[] partsOfNextLine = nextLine.split(",");//splits each line by comma placement
                allZips[i] = new Place(partsOfNextLine[0], partsOfNextLine[1], partsOfNextLine[2]);
                // ^^ initializes each Place in the array
            }
        usZipCodes = allZips; //initializes instance variable for other methods

        //catches error if can't find filename
        }catch(FileNotFoundException fnf){
            System.err.println(fnf + ": file " + filename + " not found");
        }
        
    }

    /**
     * Returns usZipCodes so they can be accessed from static main method
     * @return usZipCodes array filled with all data from file
     */
    public Place[] getUSZipCodes(){
        return usZipCodes;
    }

    /**
     * Finds a place record given a zip code
     * @param zipCode
     * @throws IOException
     */
    public void lookupZip(String zipCode){
        boolean isFound = false; //denotates whether inputted zip code exists in file

        //if you enter "exit" will exit program
        if(zipCode.equals("exit")){
            System.out.println("Exiting program...");
            lookUpAgain = false; //ends lookUpLoop()'s while loop
        }else{
            for(int i = 0; i < usZipCodes.length; i++){
                //scans each item in usZipCodes for one equal to inputted zip code
                if(zipCode.equals(usZipCodes[i].getZip())){
                    System.out.println(usZipCodes[i]); //prints town and state of match
                    isFound = true;
                    break; //ends loop if match is found
                }
            }

            //if no match found, prints result to terminal
            if(isFound == false){
                System.out.println("No such zip code");
            }
        }
    }

    /**
     * Prompts user to enter a zip code from the terminal
     * @return zip user-inputted zip code
     */
    public String enterZip(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("zip code: "); //prompts user for zip code
        String zip = scanner.next(); //user-inputted zip code
        return zip;
    }
}
