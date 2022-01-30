/**
 * Creates a 9x9 unsolved sudoku puzzle from
 * and imported text file where all numbers are 
 * separated by commas and blank spaces are signified
 * by the number 0
 * 
 * Created: April 3, 2021
 * Modified: April 8, 2021
 * Modified: April 12, 2021
 * @author jmccarty
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Puzzle {
    private String filename; //name of the file
    private String[][] puz = new String[9][9]; //holds a 2d array of sudoku board, arranged puz[y][x]
    private BufferedReader br;

    public Puzzle(String filename) throws FileNotFoundException, IOException{
        this.filename = filename;
        readFile(); //reads the file and stores in puz
    }

    /**
     * Creates a Puzzle from an existing 2d array
     * @param p another 2d array sudoku board
     */
    public Puzzle(String[][] p){
        puz = p;
    }

    /**
     * Solves the puzzle and prints to terminal.
     */
    public void solve(){
        solveUtil(0, 0, this);
        System.out.println(this);
    }

    /**
     * 
     * @param xloc x coordinate
     * @param yloc y coordinate
     * @param puzzle current iteration of puzzle
     * @return solved puzzle
     */
    private Puzzle solveUtil(int xloc, int yloc, Puzzle puzzle){
        /**
         * If passes bottom right corner, puzzle is solved
         * returns solved puzzle
         */
        if(xloc > 8 && yloc == 8){
            return puzzle;
        }

        /**
         * if reaches end of row, goes to beginning
         * of next row
         */
        if(xloc > 8){
            yloc++;
            xloc = 0;
        }

        /**
         * if current square at coordinates (xloc, yloc) is blank, gather all legal moves
         * and plug them in one by one until it finds one that allows the
         * rest of the puzzle to be solved
         */
        if(puzzle.getNum(xloc, yloc) == 0){
            ArrayList<Integer> legalmoves = legalMovesAt(xloc, yloc); //all legal moves at these coordinates
            for(int i = 0; i < legalmoves.size(); i++){
                //put current legal move into current square
                puzzle.put(legalmoves.get(i), xloc, yloc);
                
                /**
                 * if the rest of puzzle works, return puzzle
                 * else undo move and go through loop again until 
                 * it finds a move that works
                 */
                if(solveUtil(xloc + 1, yloc, puzzle) != null){
                    return puzzle;
                }else{
                    puzzle.remove(xloc, yloc);
                }
            }
            return null; //return null if no workable solutions found at current coordinates
            //this will cause the recursion to backtrack to last workable move
        }
        //go to next square
        return solveUtil(xloc + 1, yloc, puzzle);
    }

    /**
     * @param x x coordinate [0-8]
     * @param y y coordinate [0-8]
     * @return ArrayList<Integer> of all legal moves for inputted coordinates
     */
    private ArrayList<Integer> legalMovesAt(int x, int y){
        ArrayList<Integer> moves = new ArrayList<>();
        return legalMovesAtUtil(moves, 1, x, y);
    }

    /**
     * Utility function for legalMovesAt() method
     * @param moves the ArrayList of all legal moves
     * @param number the current number the method is checking (start at 1)
     * @param x x coordinate [0-8]
     * @param y y coordinate [0-8]
     * @return ArrayList<Integer> of all legal moves for inputted coordinates
     */
    private ArrayList<Integer> legalMovesAtUtil(ArrayList<Integer> moves, int number, int x, int y){
        //if it's a legal move, add it to ArrayList
        if(isLegal(number, x, y)){
            moves.add(number);
        }

        //if reach 9, end recursion
        if(number == 9){
            return moves;
        }

        //check next number for checkign [1-9]
        return legalMovesAtUtil(moves, number + 1, x, y);
    }

    /**
     * Checks if a given number at inputted coordinates is a legal move
     * @param number the number you're considering inputting
     * @param x x coordinate [0-8]
     * @param y y coordinate [0-8]
     * @return true if number is legal at inputted coordinates
     */
    private boolean isLegal(int number, int x, int y){
        //return true if there are no duplicates in the vertical, horizontal, or box
        if(!(isDuplicateVert(number, x, 0) || isDuplicateHoriz(number, 0, y) || isDuplicateBox(number, x, y))){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Checks column to see if there's already a duplicate number
     * @param number the number you're checking for
     * @param x x coordinate you're checking
     * @param y y coordinate you're checking
     * @return true if there is a duplicate in the column
     */
    private boolean isDuplicateVert(int number, int x, int y){
        //if reaches end of column, returns false (no duplicates)
        if(y == 8){ return false; }

        //if current square is empty, check the next square down
        if(puz[y][x] == null){ 
            return isDuplicateVert(number, x, y + 1);
        }

        //if current square matches inputted number, return true
        //(there is a duplicate in column)
        if(number == Integer.parseInt(puz[y][x])){
            return true;
        }

        //check next square down
        return isDuplicateVert(number, x, y + 1);
    }

    /**
     * Checks row to see if there's already a duplicate number
     * @param number the number you're checking for
     * @param x x coordinate you're checking
     * @param y y coordinate you're checking
     * @return true if there is a duplicate in row
     */
    private boolean isDuplicateHoriz(int number, int x, int y){
        //if reaches end of row, return false (no duplicates)
        if(x == 8){ return false; }

        //if current square is empty, check next square over
        if(puz[y][x] == null){
            return isDuplicateHoriz(number, x + 1, y);
        }
        
        //if current square matches inputted number, return true
        //(there is a duplicate in row)
        if(number == Integer.parseInt(puz[y][x])){
            return true;
        }

        //check next square over
        return isDuplicateHoriz(number, x + 1, y);
    }

    /**
     * Checks box to see if there's already a duplicate number
     * throws ArrayIndexOutOfBoundsException if box number not recognized
     * 
     * Boxes are arranged [1-9]
     *      1 2 3
     *      4 5 6
     *      7 8 9
     * @param number the number you're checking for
     * @param x x coordinate
     * @param y y coordinate
     * @return true if there is a duplicate number in box
     */
    private boolean isDuplicateBox(int number, int x, int y) throws ArrayIndexOutOfBoundsException{
        int boxXStart = 9; //first x coordinate in box, set to 9 so out-of-bounds coordinates throw aioob exceptions
        int boxYStart = 9; //first y coordinate in box, ^^ same as above
        int box = boxNumber(x, y); //box that inputted coordinates are in

        //setting starting Y coordinates for box
        if(box <= 3){
            boxYStart = 0; 
        }else if(box <= 6 && box >= 4){
            boxYStart = 3;
        }else if(box >= 7){
            boxYStart = 6;
        }

        //setting starting X coordinates for box
        if(box % 3 == 1){
            boxXStart = 0;
        }else if(box % 3 == 2){
            boxXStart = 3;
        }else if(box % 3 == 0){
            boxXStart = 6;
        }

        //check if there are duplicates in box w/ isDupBoxUtil
        return isDupBoxUtil(number, boxXStart, boxYStart, boxXStart, boxYStart);
    }

    /**
     * Utility function for isDuplicateBox() method
     * @param number the number you're checking
     * @param x x coordinate
     * @param y y coordinate
     * @param boxXStart first x coordinate of box
     * @param boxYStart first y coordinate of box
     * @return true if there is a duplicate in box
     */
    private boolean isDupBoxUtil(int number, int x, int y, int boxXStart, int boxYStart){

        //if have reached end of row, increment y and reset x to 0 (next row down)
        if(x == boxXStart + 3){
            y++;
            x = boxXStart;
        }

        //if have gone thru all squares in box w/ no duplicates found, return false
        if(x == boxXStart && y == boxYStart + 3){ return false; }
        
        //if current box is empty, go on to next box
        if(puz[y][x] == null){
            return isDupBoxUtil(number, x + 1, y, boxXStart, boxYStart);
        }
        
        //if number in box matches parameter number, return true                
        if(number == Integer.parseInt(puz[y][x])){
            return true;
        }
        
        //else return results for next box over
        return isDupBoxUtil(number, x + 1, y, boxXStart, boxYStart);
    }

    /**
     * Returns box number of specified coordinates.
     * Boxes are ordered so:
     *    1 2 3
     *    4 5 6
     *    7 8 9
     * @param x x coordinate of number
     * @param y y coordinate of number
     * @return box number or -1 if coordinates are outside parameters of puzzle
     */
    private int boxNumber(int x, int y){
        int box = -1;

        if(x >= 0 && x <= 2 && y >= 0 && y <= 2){ box = 1; }

        if(x >= 3 && x <= 5 && y >= 0 && y <= 2){ box = 2; }

        if(x >= 6 && x <= 9 && y >= 0 && y <= 2){ box = 3; }

        if(x >= 0 && x <= 2 && y >= 3 && y <= 5){ box = 4; }

        if(x >= 3 && x <= 5 && y >= 3 && y <= 5){ box = 5; }

        if(x >= 6 && x <= 9 && y >= 3 && y <= 5){ box = 6; }

        if(x >= 0 && x <= 2 && y >= 6 && y <= 9){ box = 7; }

        if(x >= 3 && x <= 5 && y >= 6 && y <= 9){ box = 8; }

        if(x >= 6 && x <= 9 && y >= 6 && y <= 9){ box = 9; }

        return box;
    }

    /**
     * @param number the number you which to place in puzzle
     * @param x x coordinate at which you want to place number
     * @param y y coordinate at which you want to place number
     * @return true if placement is successful, false if there is a duplicate
     */
    private void put(int number, int x, int y){
        puz[y][x] = "" + number;
    }

    /**
     * Removes number at inputted coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    private void remove(int x, int y){
        puz[y][x] = null;
    }

    /**
     * returns number at specific coordinates in puzzle,
     * starting at (0, 0) in top left corner
     * @param x collumn number [0-8]
     * @param y row number [0-8]
     * @return number at those coordinates or 0 if empty
     */
    private int getNum(int x, int y){
        //if square at coordinates is empty, return 0
        if(puz[y][x] == null){
            return 0;
        }

        //return contents of square
        return Integer.parseInt(puz[y][x]);
    }

    @Override
    public String toString(){
        String string = "";

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(puz[i][j] == null){
                    string = string + " _";
                }else if(Integer.parseInt(puz[i][j]) <= 9 && Integer.parseInt(puz[i][j]) >= 0){
                    string = string + " " + puz[i][j];
                }
            }
            string = string + "\n";
        }
        //all numbers are separated by spaces, blanks squares are signified by _
        return string;
    }

    /**
     * Reads the file specified by filename and stores
     * all data in a 2d array
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void readFile() throws FileNotFoundException, IOException{
        br = new BufferedReader(new FileReader(filename));

        /**
         * 
         */
        for(int i = 0; i < 9; i++){
            //read each line (assumes 9 in file), separate
            //by commas (assumes 9 data points per line)
            String line = br.readLine();
            String[] parts = line.split(",");

            //parses each data point
            for(int j = 0; j < 9; j++){
                //if current word == 0, stores as empty space in puz
                //else if it is a number [1-9], stores in puz in (x, y)
                //with y signifying line number and x the location in parts array
                if(parts[j].equals("0")){
                    puz[i][j] = null;
                }else if(Integer.parseInt(parts[j]) >= 0 & Integer.parseInt(parts[j]) <= 9){
                    puz[i][j] = parts[j];
                }
            }
        }
    }

    public static void main(String[] args){
        String altArg = "samplePuzzle.txt";//alternate argument in case no runtime argument inputted

        //if runtime argument inputted, set altArg equal to args[0]
        if(args.length != 0){
            altArg = args[0];
        }

        try{
            Puzzle p = new Puzzle(altArg);
            System.out.println(p);
            p.solve();
        }catch(FileNotFoundException fnf){
            System.err.println("File " + args[0] + " not found: " + fnf);
        }catch(IOException ioe){
            System.err.println("File corrupted: " + ioe);
        }catch(ArrayIndexOutOfBoundsException ai){
            System.err.println("Array index out of bounds");
            ai.printStackTrace();
        }
    }
}
