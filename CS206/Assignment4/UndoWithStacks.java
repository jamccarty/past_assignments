/**
 * Scans and stores letters and underscores from typed input in the terminal. 
 * If program hits a 1, it deletes the previous stored character from storage.
 * If program hits a 9, it prints all stored characters in reverse order to that 
 * in which they were inputted.
 * 
 * Created March 28 2021
 * @author jmccarty
 */
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UndoWithStacks {
    private Scanner scan = new Scanner(System.in);
    public static void main(String[] args){
        try{
            UndoWithStacks undo = new UndoWithStacks();
            undo.readAndPrint();
        }catch(NoSuchElementException nsee){
            nsee.printStackTrace();
        }catch(IllegalStateException ise){
            ise.printStackTrace();
        }
    }

    public UndoWithStacks(){
    }

    /**
     * Reads input from terminal
     * Prints all inputted letters and underscores in reverse order
     * to that in which the user originally typed them. All numbers and
     * special characters are ignored -- the program stores only those characters
     * mentioned above.
     * 
     * If program hits a 1 in the inputted lines,
     * it will delete the character immediately preceeding that 1.
     * 
     * Likewise, if program hits a 9 in the inputted lines,
     * it will stop reading, and the next time the user hits the enter key,
     * the program will print the output as described above.
     * 
     * @throws NoSuchElementException
     * @throws IllegalStateException
     */
    public void readAndPrint() throws NoSuchElementException, IllegalStateException{
        String input = ""; //holds entirety of input in terminal
        System.out.print("Input: ");

        boolean isDoneReading = false; //stops reading from terminal when true
        ArrayStackHW5<String> stack = new ArrayStackHW5<>(); //holds all appropriate characters

        while(!isDoneReading){
            String line = scan.nextLine(); //Scans line from terminal, stores in string form

            for(int i = 0; i < line.length(); i++){
                if(line.charAt(i) == '9'){
                    //if hits a 9, stops reading
                    isDoneReading = true;
                    break;
                }else{
                    //otherwise, stores each line in input String
                    input = input + line.charAt(i);
                }
            }
        }

        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '1'){
                //if hits a 1, deletes previous character stored in stack
                if(!stack.empty()){
                    stack.pop();
                }
            }else if(input.charAt(i) >= 'a' && input.charAt(i) <= 'z'){
                //if character is lowercase letter, adds it to stack
                stack.push(String.valueOf(input.charAt(i)));
            }else if(input.charAt(i) >= 'A' && input.charAt(i) <= 'Z'){
                //if character is uppercase letter, adds it to stack
                stack.push(String.valueOf(input.charAt(i)));
            }else if(input.charAt(i) == '_'){
                //if character is underscore, adds it to stack
                stack.push("_");
            }
        }

        String output = "";
        while(!stack.empty()){
            //stores parsed input in reverse order
            //to how it was originally inputted to terminal
            output = output + stack.pop();
        }

        //prints output
        System.out.println("Output: " + output);
    }
}