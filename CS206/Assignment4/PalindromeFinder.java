/**
 * Finds all palindromes in a txt file and stores them in an ArrayList.
 * This program will ignore all spaces and capital letters in the txt
 * file. So with a txt file containing "wow racecar wow", it will find palindromes
 * "wowracecarwow" "cec" "racecar" "aceca" "wracecarw" etc.
 * Created March 27, 2021
 * @author jmccarty
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class PalindromeFinder {
    private String fileString; //string holding contents of txt file

    private ArrayList<Integer> locations = new ArrayList<>();//all locations of palindromes
    private ArrayList<String> allPalindromes = new ArrayList<>();//all palindromes

    public static void main(String[] args){
        String[] arguments = new String[1];

        if(args.length == 0){
            System.out.println();
            System.out.println("You have not submitted a command line argument");
            System.out.println("Therefore, PalindromeFinder will be running on testpals.txt");
            System.out.println();
            arguments[0] = "testpals.txt";
        }else if (args.length > 0){
            arguments[0] = args[0];
        }

        try{
            PalindromeFinder pf = new PalindromeFinder(arguments[0]);
            System.out.println(pf);
        }catch(FileNotFoundException fnf){
            fnf.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * @param filename the name of the file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public PalindromeFinder(String filename) throws FileNotFoundException, IOException{
        File2String f2s = new File2String(filename);
        fileString = f2s.getString(); //converts file to string form
        findPalindromes(); //finds all palindromes and stores data in appropriate ArrayLists
    }

    /**
     * @return an ArrayList of all palindromes in txt file
     */
    public ArrayList<String> getPalindromes(){
        return allPalindromes;
    }

    /**
     * @return an ArrayList of all palindrome locations in txt file
     * such that getPalindromeLocations(i) holds the location of getPalindromes(i)
     */
    public ArrayList<Integer> getPalindromeLocations(){
        return locations;
    }

    @Override
    public String toString(){
        //if there are no palindromes in allPalindromes ArrayList return
        //string saying so
        if(allPalindromes.size() == 0){
            return "There are no palindromes in this file.";
        }

        //Designates which collumn is which
        String palindromes = "length / location / palindrome";

        //adds each size, location, and palindrome on a new line of the string
        for(int i = 0; i < locations.size(); i++){
            String nextpal = allPalindromes.get(i).length() + "   " + locations.get(i) + "   " + allPalindromes.get(i);
            palindromes = palindromes + "\n" + nextpal;
        }

        return palindromes;
    }

    /**
     * finds all palindromes in txt file and stores them
     * in the privae allPalindromes ArrayList, and their 
     * individual locations in the locations ArrayList
     */
    private void findPalindromes(){
        int maxLength; //longest palindrome length program will test for

        if(fileString.length() < 50){
            //if fileString is shorter than 50 characters, maximum
            //palindrome length is set equal to fileString length
            maxLength = fileString.length();
        }else{
            //if fileString is 50+ characters, program will only test 
            //for palindromes that are 50 characters in length
            maxLength = 50;
        }

        /**
         * i-for-loop: finds all 2-character palindromes,
         * then all 3-character palindromes, then all 4-character, etc
         * until it hits maxLength
         */
        for(int i = 2; i <= maxLength; i++){

            /**
             * j-for-loop moves through each overlapping word in file
             * so in "abcd" at i = 2, it will look at "ab" then "bc"
             * then "cd"
             */
            for(int j = 0; j < fileString.length() - (i - 1); j++){

                String word = "";

                /**
                 * setting current word. The length is
                 * i characters long, and the first letter is
                 * at a location of j.
                 */
                for(int k = j; k < j + i; k++){
                    word = word + fileString.charAt(k);
                }

                /**
                 * check to see if it's a palindrome.
                 * if it is, add it to allPalindromes, and
                 * add location to locations
                 */
                if(isPalindrome(word)){
                    allPalindromes.add(word);
                    locations.add(j);
                }
            }
        }
    }

    /**
     * @param string
     * @return true if string is a palindrome
     */
    private boolean isPalindrome(String string){
        if(string.equals(getBackwardString(string))){
            return true;
        }else{
            return false;
        }
    }

    /**
     * reverses the order of the letters in a string
     * i.e. if paramter is "abc" then it will return "cba"
     * @param string
     * @return backwards string
     */
    private String getBackwardString(String string){
        ArrayStackHW5<String> arrayStack = new ArrayStackHW5<>(string.length());
        String backwardstring = ""; //will hold the backwards version of string

        /**
         * puts each character of string into arrayStack in order
         */
        for(int i = 0; i < string.length(); i++){
            String nextChar = String.valueOf(string.charAt(i));
            arrayStack.push(nextChar);
        }

        /**
         * puts each character from string into backwardstring
         * in reverse order (last into arrayStack, first out)
         */
        for(int i = 0; i < string.length(); i++){
            backwardstring = backwardstring + arrayStack.pop();
        }

        return backwardstring;
    }

}
