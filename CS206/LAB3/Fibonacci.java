/*
* Desc: class calculates two fibonacci sequences
* to a specified number of units and uses them 
* to calculate a golden ratio.
* @author jmccarty
* created Feb 11, 2021
*/

public class Fibonacci {

    public static void main(String[] args){
        Fibonacci sequence = new Fibonacci();
        sequence.calculateFibonacci(20);
    }

    /*
    * Calculates a Fibonacci sequence to 20 units
    * and prints two versions of it to the terminal 
    * (the second sequence prints each preceding 
    * number of the first).
    * It also prints the golden mean for each row.
    * As the sequence lengthens, the golden mean will get
    * infinitely closer to the golden ratio
    * @param length indicates the desired number of units in each sequence
    */
    public void calculateFibonacci(int length){
        //holds the first collumn number of the sequence
        int n_2 = 1; 

        //holds the second collumn number of the sequence
        int n_1 = 1; 

        //holds the golden ratio (third collumn)
        double ratio = 1.0;

        //prints first row of each collumn to terminal
        System.out.println(n_2 + " " + n_1 + " " + ratio);

        for(int i = 1; i < length; i++){
            int nI = n_2 + n_1;

            n_1 = n_2;
            n_2 = nI;
            
            ratio = (1.0 * n_2) / (n_1 * 1.0);

            System.out.println(n_2 + " " + n_1 + " " + ratio);
        }

    }

    public Fibonacci(){
    }
}
