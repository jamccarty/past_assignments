/**
 * Sorts all names from inputted year files in alphabetical order and tallies
 * the total number of children born with each name.
 * 
 * Assumes file names are named "namesXXXX.csv" with the XXXX signifying the year.
 * 
 * All words in each file assumed to be separated by commas and arranged thusly:
 *      rank,male name,number babies born w male name,female name,number babies born w female name
 * on each line
 * 
 * Created: May 2, 2021
 * Modified : May 7, 2021
 * @author jmccarty
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class NameSorter {
    private int totalBabiesM; //total number of male babies (w top 1000 names) for all years
    private int totalBabiesF; //total number of female babies (w top 1000 names) for all years

    private ArrayList<String> fileNames = new ArrayList<>(); //names of data files used

    //alphabetized lists of male and female Names
    private SortedNamesList maleNames = new SortedNamesList();
    private SortedNamesList femaleNames = new SortedNamesList();

    /**
     * @param files
     * @throws IOException
     * @throws FileNotFoundException
     * @throws NumberFormatException
     */
    public NameSorter(String[] files) throws IOException, FileNotFoundException, 
    NumberFormatException{
        //add all file names to filenames ArrayList
        for(int i = 0; i < files.length; i++){
            fileNames.add(files[i]);
        }
        //set initial total babies to 0
        totalBabiesM = 0;
        totalBabiesF = 0;

        //sort names alphabetically
        sortNames();
    }

    /**
     * @param files ArrayList<String> of all file names
     * @throws IOException
     * @throws FileNotFoundException
     * @throws NumberFormatException
     */
    public NameSorter(ArrayList<String> files) throws IOException, FileNotFoundException,
    NumberFormatException{
        //ad all file names to filenames ArrayList
        for(int i = 0; i < files.size(); i++){
            fileNames.add(files.get(i));
        }
        //set intitial total babies to 0
        totalBabiesM = 0;
        totalBabiesF = 0;

        //sort names alphabetically
        sortNames();
    }

    /**
     * Returns the percentage of all babies born with this name within range of inputted years
     * 
     * @param name name for name percentage you're looking for
     * @param isMale true if name is male name, false if female
     * @return
     */
    private double percentage(Name name, boolean isMale){
        if(isMale){
            return (double)name.totalBabies()/(double)totalBabiesM;
        } 
        return (double)name.totalBabies()/(double)totalBabiesF;
    }

    /**
     * @return SortedLinkedList of alphabetized male names
     */
    public SortedNamesList alphabetizedMale(){
        return maleNames;
    }

    /**
     * @return SortedLinkedList of alphabetized female names
     */
    public SortedNamesList alphabetizedFemale(){
        return femaleNames;
    }

    /**
     * Takes input from terminal, searches for name, then prints
     *      gender(s) associated with name
     *      Total babies named this name w/in inputted time frame
     *      All years name was in top 1000
     *      Percentage of all babies born with this name w/in inputted time frame
     *      Alphabetic position w/ regards to all other alphabetized names in list
     * If input is 'q' will quit program
     */
    public void searchNames(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Name     (q to quit): ");
        String name = scanner.next();
        searchNamesUtil(name);
    }

    /**
     * Determines if name exists w/in lists, and if it does, prints
     * associated data to terminal
     * @param name the String name you're searching for (if q, will quit program)
     */
    private void searchNamesUtil(String name){
        Scanner scanner = new Scanner(System.in);

        //if input String is "q", quit program
        if(name.equals("q")){
            scanner.close();
            return;
        }else{
            //if exists w/in maleNames, won't be null
            Name maleName = maleNames.getData(name);
            //if exists w/in femaleNames, won't be null
            Name femaleName = femaleNames.getData(name);

            //if femaleName isn't null, print all data to terminal
            //i.e. if it's a female name that exists within the data files
            if(femaleName != null){
                printInfo(femaleName, false, femaleNames);
            }
            //same as above but for maleName
            //i.e. if it's a male name that exists within the data files
            if(maleName != null){
                printInfo(maleName, true, maleNames);
            }
            //if both are null, then print 'not found' message to terminal
            //i.e. if name is not found in any of the files
            if(maleName == null && femaleName == null){
                System.out.println("This name was not found within the top 1000 names for the inputted years");
            }

            //prompts user for next input
            System.out.println();
            System.out.println("Name     (q to quit): ");
            System.out.println();

            //Takes next input
            String nextName = scanner.next();

            //recurse until input is "q"
            searchNamesUtil(nextName);
        }
    }

    /**
     * prints relevant data for associated Name object
     * @param name Name object
     * @param isMale true if in regards to status as a male name, false if otherwise
     * @param list list you're searching (should match isMale, i.e. maleNames and true)
     */
    private void printInfo(Name name, boolean isMale, SortedNamesList list){
        //if female name, make gender read "girl", vice versa if otherwise
        String gender = "girl";
        if(isMale){
            gender = "boy";
        }

        System.out.println("Used as a " + gender +"'s name:");
        System.out.println("Total babies:       " + name.totalBabies());
        System.out.println("Years in top 1000:  " + name.yearsInTop1000());
        System.out.println("Percentage:         " + percentage(name, isMale));
        System.out.println("Alphabetic postion  " + list.location(name));
    }

    /**
     * Sorts names alphabetically
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws NumberFormatException if file name not in recognizable format (see class documentation)
     */
    private void sortNames() throws IOException, FileNotFoundException, NumberFormatException{
        //for each file
        for(int i = 0; i < fileNames.size(); i++){
            //cut filename down to just the year (assumes format of name || location)
            String date = fileNames.get(i);

            date = date.replaceAll(".csv", "");
            date = date.replaceAll("names", "");

            int year = Integer.parseInt(date); //year file references
            readFile(fileNames.get(i), year);//read file
        }
    }

    /**
     * reads file and sorts names from it into lists alphabetically
     * @param filename name of file
     * @param year year file references
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void readFile(String filename, int year) throws IOException, FileNotFoundException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        while(br.ready()){
            String line = br.readLine();//reads first line of file
            String[] partsOfLine = line.split(",");//splits each line by comma placement

            //if line is empty, break out of loop
            if(partsOfLine.length == 0){
                break;
            }

            //number babies born with line's male name
            int maleNum = Integer.parseInt(partsOfLine[2]);

            //number babies born with line's female name
            int femaleNum = Integer.parseInt(partsOfLine[4]);

            //add names to respective lists
            this.addName(maleNames, partsOfLine[1], maleNum, year);
            this.addName(femaleNames, partsOfLine[3], femaleNum, year);

            //increases total number babies by additional babies born with these names
            totalBabiesF = totalBabiesF + femaleNum;
            totalBabiesM = totalBabiesM + maleNum;
        }
        br.close();//close buffered reader
    }

    private void addName(SortedNamesList list, String name, int numOccurrences, int year){
        if(list.contains(name)){
            //if name already exists in list, simply add year and increment number babies 
            //within internal Name data
            list.getData(name).newOccurrence(year, numOccurrences);
        }else{
            //if doesn't already exist, add new name to list
            //(sorts automatically w/in add() method)
            list.add(new Name(name, numOccurrences, year));
        }
    }

    public static void main(String[] args){
        try{
            String[] files = {"names1990.csv", "names1991.csv", "names1992.csv", "names1993.csv", 
            "names1994.csv", "names1995.csv", "names1996.csv", "names1997.csv", "names1998.csv", 
            "names1999.csv", "names2000.csv", "names2001.csv", "names2002.csv", "names2003.csv",
            "names2004.csv", "names2005.csv", "names2006.csv", "names2007.csv", "names2008.csv", 
            "names2009.csv", "names2010.csv", "names2011.csv", "names2012.csv", "names2013.csv", 
            "names2014.csv", "names2015.csv", "names2016.csv", "names2017.csv"};

            NameSorter ns;
            if(args.length == 0){
                //if no command line arguments, use files
                ns = new NameSorter(files);
            }else{
                //use file names from command line arguments
                ArrayList<String> filesArgs = new ArrayList<>();
                for(int i = 0; i < args.length; i++){
                    filesArgs.add(args[i]);
                }
                ns = new NameSorter(filesArgs);
            }
            ns.searchNames();
        }catch(FileNotFoundException fnf){
            System.err.println("File not found... " + fnf);
        }catch(IOException ioe){
            System.err.println("Reading error... " + ioe);
        }catch(NumberFormatException nfe){
            System.err.println("Something went wrong. Your file name may not be formmatted correctly.");
        }
    }
    
}
