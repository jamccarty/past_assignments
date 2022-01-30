import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads a file containing data in the order of 
 * [zip code, type, city name, state code, type 2, latitude, longitude
 * true or false, population 1, population 2, another number].
 * Program expects file data to be separated by commas.
 * 
 * Takes a user-inputted zip code, then
 * searches file for the place with the matching zip code 
 * and prints the city name and state abbreviation to terminal.
 * If location data includes latitude, longitude, or population,
 * program will print this data to terminal also.
 * 
 * Typing "exit" instead of a zip code will exit the program.
 * 
 * @author jmccarty
 * created Mar 3 2021
 * Parts of code copied from jmccarty/CS206/Assignment2a/Places.java
 */

public class Places {
    private boolean lookUpAgain = true; //determines whether program will ask for another zip code

    //where all Place objects created from file will be stored
    private ArrayList<Place> usZipCodes = new ArrayList<>(); 

    public static void main(String[] args){
        //opening banner
        System.out.println("=============================================");
        System.out.println("Welcome to Zip Code Search!");
        System.out.println("=============================================");

        //creates new instance of Places and begins search loop
        Places unitedStates = new Places();
        unitedStates.lookUpLoop("ziplocs.csv"); 
    }

    /**
     * uses enterZip() and lookupZip() to collect user-inputted zip code
     * and look it up from the usZipCodes array.
     * It then prints the corresponding town name and state code
     * to the terminal. Loops until user types "exit" into the terminal.
     */
    public void lookUpLoop(String filename){

        //catches the IOException thrown by readZipCodes()
        try{
            this.readZipCodes(filename); //inputs data from file of your choice 

            //prompts user for zip code [this.enterZip()] ands searches for zip's location,
            //then prints location's stored data to terminal [this.lookupZip()]
            while(lookUpAgain == true){
                String myZip = this.enterZip();
                this.lookupZip(myZip);
            }

        //catches exceptions thrown from lookUpLoop() and readZipCodes()
        }catch(FileNotFoundException fnf){
            System.err.println("File " + filename + " not found. " + fnf);
            System.err.println("Exiting program...");
        }catch(IOException io){
            System.err.println("Reading " + io);
            System.err.println("Exiting program...");
        }
    }

    /**
     * Reads zip code file
     * @param filename
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void readZipCodes(String filename) throws IOException, FileNotFoundException {
        //reads filename line by line
        //splits each line by comma placement
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            while(br.ready()){//loop continues until there is no more file to read
                String line = br.readLine(); //reads single line
                String[] parts = line.split(","); //stores parts of line, separated by commas
                
                //scans each line for length, then divides into Place, LocatedPlace, or PopulatedPlace
                //depending on if it location has necessary parameters
                if(parts.length == 9 && parts[5].equals("") && parts[6].equals("")){
                    //classifies Place-type locations as having length of 9, rules out those
                    //whose latitude/longitude spaces contain ""
                    usZipCodes.add(new Place(parts[0], parts[2], parts[3]));
                }else if(parts.length == 9){
                    //classifies LocatedPlace-type locations as having length of 9
                    usZipCodes.add(new LocatedPlace(parts[0], parts[2], parts[3],
                    parts[5], parts[6]));
                }else{
                    //classifies all other location types as PopulatedPlaces
                    usZipCodes.add(new PopulatedPlace(parts[0], parts[2], parts[3],
                    parts[5], parts[6], parts[10]));
                }
            }
        }
    }

    /**
     * returns ArrayList housing all data from file
     * @return usZipCodes
     */
    public ArrayList<Place> getUSZipCodes(){
        return usZipCodes;
    }


    /**
     * Finds a place record given a zip code
     * @param zipCode zip code of location
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void lookupZip(String zipCode)throws IOException, FileNotFoundException{
        boolean isFound = false; //denotates whether inputted zip code exists in file

        //if you enter "exit" will exit program
        if(zipCode.equals("exit")){
            System.out.println("Exiting program...");
            lookUpAgain = false; //ends lookUpLoop()'s while loop
        }else{
            for(int i = 0; i < usZipCodes.size(); i++){
                //scans each item in usZipCodes for one equal to inputted zip code
                if(zipCode.equals(usZipCodes.get(i).getZip())){
                    System.out.println(usZipCodes.get(i)); //prints town and state of match
                    isFound = true;

                    break; //ends loop if match is found
                }
            }

            //if no match found, prints result to terminal
            if(isFound == false){
                System.out.println("No such zip code");
            }

            System.out.println("");//inserts paragraph of white space for readability
        }
    }

    /**
     * Prompts user to enter a zip code from the terminal
     * @return user-inputted zip code
     */
    public String enterZip(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("zip code (type 'exit' to exit program): "); //prompts user for zip code
        String zip = scanner.next(); //user-inputted zip code
        return zip;
    }
}
