/**
 * An O(nlogn) sorter for int[] arrays. Arranges data from least to greatest.
 * Created: April 28, 2021
 * Modified: April 29, 2021
 * @author jmccarty
 */
public class QuickSort extends SortBase{

    public QuickSort(){
    }

    /**
     * Sorts array in place from least to greatest
     * @param array the int array you wish to sort
     */
    public void sortInPlace(int[] array){
        sortListUtil(array, 0, array.length - 1);
        return;
    }

    /**
     * private recursive method; sorts array from least to greatest
     * @param array the array
     * @param begin starting location of array segment you wish to sort
     * @param end ending location of array segment you wish to sort
     */
    private void sortListUtil(int[] array, int begin, int end){
        /**
         * base case. if end coordinate and begin coordinate cross or are the same, end sort
         */
        if(end - begin < 1){
            return;
        }

        //middle location dividing "less-than segment" from "greater-than segment"
        //segments are either greater than or less than array[part]
        int part = partition(array, begin, end);

        //sort "less-than segment"
        sortListUtil(array, begin, part - 1);

        //sort "greater-than segment"
        sortListUtil(array, part + 1, end);
    }

    /**
     * Rearranges section of array into "greater-than" or "less-than" segments
     * with the array[mid] they're greater-than or less-than between them
     * @param array the array
     * @param begin starting coordinate of array segment you want to partition
     * @param end ending coordinate of array segment you want to partition
     * @return "midpoint" location between "greater-than" and "less-than" segments
     */
    private int partition(int[] array, int begin, int end){
        //the item sections will be greater than or less than
        int elem = array[end];

        //marker of last slot in "less-than" side
        int loc = begin;

        /**
         * for every location between begin and end locations in array,
         * compare contents of location to elem. If contents are less than elem,
         * put into "smaller side" by swapping it with contents of loc (last slot
         * in smaller side and incrementing loc by one)
         */
        for(int i = begin; i < end - 1; i++){
            if(array[i] < elem){
                int hold = array[i];
                array[i] = array[loc];
                array[loc] = hold;
                loc++;
            }
        }

        //if the current contents of last location in "smaller side" is smaller than elem
        //increment loc to be one ahead of that last location
        //loc will now be the location of the "mid" value elem
        if(array[loc] < array[end]){
            loc++;
        }

        //then swap contents with elem (which is at the end of the section)
        array[end] = array[loc];
        array[loc] = elem;

        //if there's still leftover space between loc and end
        if(end - loc > 1){
            //for every location between them
            for(int i = loc; i < end; i++){
                if(array[i] < elem){
                    //if array[i] < elem, make array[loc] = array[i]
                    //(contents of array[loc] held in elem)
                    array[loc] = array[i];
                    if(i != loc + 1){
                        //BUT if i and loc aren't right next to each other in array
                        //move contents of array[loc + 1] to location i, then put
                        //elem in the slot after loc (so it's still separating smaller
                        //and larger sides)
                        int holdNextAfterElem = array[loc + 1];
                        array[loc + 1] = elem;
                        array[i] = holdNextAfterElem;
                    }else{
                        //if loc and i are right next to each other, then just swap contents
                        array[i] = elem;
                    }
                    //after swapping, increment loc to keep track of new elem position
                    loc++;
                }
            }
        }
        //return position of elem
        return loc;
    }

    /**
     * @param array the array
     * @return String form of int array, with each data point separated by a space
     */
    protected String arrayToString(int[] array){
        String string = "";
        for(int i = 0; i < array.length; i++){
            string = string + " " + array[i];
        }
        return string;
    }

    public static void main(String[] args){
        try{
            long startTime = System.currentTimeMillis(); //start time of program
            int sizeOfArray = 1000; //default size of random array is 1000
            //boolean determining whether program will print array in sorted and unsorted form
            boolean printArrays = true; 

            if(args.length == 0){
                System.out.println("No runtime arguments inputted. Default array length is 1000");
                System.out.println("and program will print by default");
            }

            if(args.length == 1){
                System.out.println("No second runtime argument detected.");
                System.out.println("Program will print by default.");
            }

            if(args.length >= 1){
                //set sizeOfArray equal to first runtime argument
                sizeOfArray = Integer.parseInt(args[0]);

                if(sizeOfArray < 0){
                    //if first runtime argument negative, set sizeOfArray to default 1000
                    System.out.println("First runtime argument must be positive int. Default array size is 1000");
                    sizeOfArray = 1000;
                }

                if(args.length > 1){
                    //if second runtime argument exists but isn't valid, tell user and leave
                    //printArrays at default true
                    if(!args[1].equals("true") && !args[1].equals("false")){
                        System.out.println("Second runtime argument is invalid - must be 'true' or 'false'");
                        System.out.println("Program will print by default.");
                    }else if(args[1].equals("false")){
                        //if second runtime argument is false, set printArrays to false
                        System.out.println("Program will not print resulting list");
                        printArrays = false;
                    }
                }
            }
            System.out.println();

            /**
             * create array of random ints ranging from (1, sizeOfArray - 1)
             * array.length == sizeOfArray
             */
            QuickSort sort = new QuickSort();
            int[] array = sort.fyShuffle(sizeOfArray);

            //print unsorted array if printArrays is true
            if(printArrays){
                System.out.println("unsorted: ");
                System.out.println(sort.arrayToString(array));
            }

            //sort array in place
            sort.sortInPlace(array);

            //print sorted array if printArrays is true
            if(printArrays){
                System.out.println("sorted: ");
                System.out.println(sort.arrayToString(array));
            }

            //print time taken by program
            System.out.println("Time taken: " + (System.currentTimeMillis() - startTime));
        }catch(NumberFormatException nfe){
            System.err.println(nfe + " - first runtime argument must be an int >= 0");
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("Oops, something went wrong: " + e);
        }
    }
}
