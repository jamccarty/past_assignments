/**
 * Scans a text file and calculates the number of unique words
 * and the top three most frequent words (and their associated word counts).
 * main() method also calculates time elapsed during runtime of program.
 * @author jmccarty
 * Created: March 13,2021
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class HashCounter{
    private String filename; //name of file

    private int uniqueWords = 0; //number of unique words in file
    
    private HashMap<String, Integer> hash = new HashMap<>();
    //String is key (word from text), Integer is count (number of words found)

    private String[] topThreeWords = new String[3]; //array containing top three most frequent words
    private int[] topThreeCounts = new int[3]; //array with associated word counts of topThreeWords

    public static void main(String[] args){
        long start = System.currentTimeMillis(); //recording start time of program

        HashCounter hc = new HashCounter(args[0]);
        hc.bestOfThree();

        long end = System.currentTimeMillis(); //recording end time of program

        //print time taken by program to terminal
        System.out.println("Time taken: " + (end - start) + " milliseconds");
    }

    /**
     * @param filename file name
     */
    public HashCounter(String filename){
        this.filename = filename;
    }

    /**
     * returns hashtable with all unique words and paired word counts
     * in a file.
     * @return hashtable with all words and paired word counts
     */
    public HashMap<String, Integer> getChain(){
        return hash;
    } 

    /**
     * counts the number of unique words in a textfile
     */
    private void countWords(){
        try{
            BookReader read = new BookReader(filename);
            
            while(read.isReady() == true){
                int wordCount = 1;//number of occurrences for this word
                String word = read.nextWord(); //read next word of file and store in word

                if(!hash.containsKey(word)){
                    //if no previous occurrences, creates new occurrence
                    //with wordCount of 1
                    hash.put(word, wordCount);
                }else if(hash.containsKey(word)){
                    //gets current number of word occurrences and adds 1
                    wordCount = hash.get(word) + 1;
                    //updates value in probe with updated wordCount
                    hash.put(word, wordCount);
                }
            }
        }catch(FileNotFoundException fnf){
            System.err.println("File " + filename + " not found: " + fnf);
        }catch(IOException ioe){
            System.err.println("Reading error: " + ioe);
        }catch(NullPointerException npe){
            System.err.println("Something in your program is returning null: " + npe);
        }
    }

    /**
     * Scans a text file and prints to the terminal the
     * - number of unique words
     * - top three most freqently occurring words and their frequency of occurrence
     * found within
     */
    public void bestOfThree(){
        HashCounter hc = new HashCounter(filename);
        hc.countWords();
        hc.sort();

        int[] counts = hc.getTopThreeCounts();
        String[] words = hc.getTopThreeWords();

        System.out.println("File: " + filename);
        System.out.println("Number of unique words: " + hc.getUniqueWordCount());
        System.out.println("Top three words");
        System.out.println("     " + words[0] + ": " + counts[0]);
        System.out.println("     " + words[1] + ": " + counts[1]);
        System.out.println("     " + words[2] + ": " + counts[2]);
        
    }

    /**
     * returns array of word counts of top three highest frequency words in a text file
     * with a[0] being the highest frequency and a[2] being the third highest.
     * @return array of top three word counts
     */
    public int[] getTopThreeCounts(){
        return topThreeCounts;
    }

    /**
     * returns array of top three highest frequency words in a text file,
     * with a[0] being the highest frequency and a[2] being the third highest.
     * @return array of top three words
     */
    public String[] getTopThreeWords(){
        return topThreeWords;
    }

    /**
     * returns array of the wordcounts of the top three highest frequency
     * words in the text file, with a[0] being the highest frequency and
     * a[2] being the third highest.
     * @return array of wordcounts of top three words
     */
    public int getUniqueWordCount(){
        return uniqueWords;
    }

    /**
     * Orders all unique words from lowest to greatest occurrences
     * and identifies the top three highest frequency words.
     */
    private void sort(){
        Collection<Integer> vCollect = hash.values();
        ArrayList<Integer> values = new ArrayList<>(vCollect);

        Set<String> kSet = hash.keySet();
        ArrayList<String> keys = new ArrayList<>(kSet);

        ArrayList<Integer> sortedValues = new ArrayList<>();
        uniqueWords = keys.size();//number of unique words

        //set sortedValues equal to values
        for(int i = 0; i < values.size(); i++){
            sortedValues.add(i, values.get(i));
        }

        //sorts from lowest to highest
        Collections.sort(sortedValues);
        
        //finds top three most frequent words
        //and stores them/their word counts in the topThreeWords/topThreeCounts 
        //arrays for access outside the method
        for(int i = 0; i < values.size(); i++){
            //identifying first most frequent word
            if(values.get(i) == sortedValues.get(sortedValues.size()-1)){
                topThreeCounts[0] = values.get(i);
                topThreeWords[0] = keys.get(i);
            }
            //identifying second most frequent word
            if(values.get(i) == sortedValues.get(sortedValues.size()-2)){
                topThreeCounts[1] = values.get(i);
                topThreeWords[1] = keys.get(i);
            }
            //identifying third most frequent word
            if(values.get(i) == sortedValues.get(sortedValues.size()-3)){
                topThreeCounts[2] = values.get(i);
                topThreeWords[2] = keys.get(i);
            }
        }
    }
}


