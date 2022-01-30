/**
 * Reads a file word by word. 
 * - nextWord() returns the next word in the file.
 * - isReady() returns true if there is more text in the file to read.
 * 
 * @param filename name of the file
 * @throws FileNotFoundException
 * @throws IOException
 * @throws NullPointerException
 * 
 * @author jmccarty
 * Created: March 11, 2021
 * Modified: March 12, 2021
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BookReader {
    /**
     * array of individual words in single line
     * reinitialized to next line in file
     * every time program calls last word in line
     */
    private String[] words;

    private int currentWord; //location of CURRENT word in words array
    private BufferedReader br; //new bufferedreader; initialized in constructor

    /**
     * main() method for testing purposes only
     * @param args file name
     */
    public static void main(String[] args){
        try{
            //new bookreader reads "ham.txt"
            BookReader book = new BookReader("ham.txt");
            while(book.isReady() == true){ //while there is still more text in file
                //stores next word in line and then prints line to terminal
                String line = book.nextWord();
                System.out.println(line);
            }
        }catch(FileNotFoundException fnf){
            System.out.println("File Not found: " + fnf);
        }catch(IOException ioe){
            System.out.println("Reading error " + ioe);
        }catch(NullPointerException npe){
            System.out.println(npe);
        }
    }

    /**
     * Constructor
     * @param filename name of file
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NullPointerException
     */
    public BookReader(String filename) throws FileNotFoundException, IOException, NullPointerException{
        br = new BufferedReader(new FileReader(filename));
        this.nextLine(); //reads first line to store in first words array to kick things off
    }
    
    /**
     * private method, reads the next line in the file
     * converts each word to lowercase and removes all punctuation
     * then stores each word in instance variable words array 
     * @throws IOException
     */
    private void nextLine() throws IOException{
        //stores individual words in words array
        String line = br.readLine();

        //if line is empty, read next line
        while(line.equals("")){
            line = br.readLine();
        }
        //replaces various punctuation marks with ""
        String betterline = line.trim().toLowerCase().replaceAll("[\\.\\'?!,\\\"]", "");
        betterline = betterline.replaceAll("-", " ");
        words = betterline.split("\\s");

        //resets currentword for new line
        currentWord = 0;
    }

    /**
     * @return word the next word in the file
     * @throws IOException
     * @throws NullPointerException
     */
    public String nextWord() throws IOException, NullPointerException{
        //should only have to run through once,
        //but if there's an empty line, it will get the next full line
        if(currentWord == words.length){
            nextLine();
        }
        //sets returned word equal to current word the "cursor" is on
        String word = words[currentWord];
        currentWord++; //increments for next word
        return word;
    }

    /**
     * Determines whether there is more text in the file
     * @return true if there is more text in file
     * @throws IOException
     */
    public boolean isReady() throws IOException{
        if(br.ready()){
            return true;
        }else{
            //allows to read until end of line if bufferedreader
            //has reached last line of text
            return !(currentWord == words.length);
        }
    }
}
