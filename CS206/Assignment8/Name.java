/**
 * A name found in the top 1000 names of a certain year.
 * 
 * Created: May 2, 2021
 * @author jmccarty
 */

import java.util.ArrayList;

public class Name implements Comparable<Name>{
    private String name; //name
    private int totalBabies; //total babies with this name
    private ArrayList<Integer> yearsInTop1000 = new ArrayList<>();//years name found in top 1000 names

    /**
     * @param name name
     * @param totalBabies total babies born with this name in inputted year
     * @param year year you're referencing 
     */
    public Name(String name, int totalBabies, int year){
        this.name = name;
        this.totalBabies = totalBabies;
        yearsInTop1000.add(year);
    }

    /**
     * @return name string
     */
    public String name(){
        return name;
    }

    /**
     * @return total babies born with this name in all inputted years
     */
    public int totalBabies(){
        return totalBabies;
    }

    /**
     * @return String form of all years name has been in top 1000
     */
    public String yearsInTop1000(){
        String years = "[";
        years = years + yearsInTop1000.get(0);

        //sort through every item in yearsInTop1000
        for(int i = 1; i < yearsInTop1000.size(); i++){
            years = years + ", " + yearsInTop1000.get(i);
        }

        years = years + "]";

        return years;
    }

    /**
     * Adds new year's data to Name data
     * @param year year of new occurrence
     * @param numberNames number of new babies born with this name in year of new occurrence
     */
    public void newOccurrence(int year, int numberNames){
        yearsInTop1000.add(year);
        totalBabies = totalBabies + numberNames;
    }

    @Override
    public int compareTo(Name o) {
        return this.name().compareTo(o.name());
    }

    /**
     * returns positive integer if this.name is further along in the alphabet
     * than otherName, negative if otherwise
     * @param otherName the String form of the other name i.e. "Kate"
     * @return integer of difference
     */
    public int compareTo(String otherName){
        return this.name().compareTo(otherName);
    }
    public static void main(String[] args){
        Name janice = new Name("Janice", 1000, 2001);
        janice.newOccurrence(2002, 999);
        System.out.println(janice.name());
        System.out.println(janice.totalBabies());
        System.out.println(janice.yearsInTop1000());

        Name j2 = new Name("Janice", 878, 2003);
        System.out.println("Janice == Janice? " + j2.compareTo(janice));
        Name j3 = new Name("Jani", 1000, 2008);
        System.out.println("Janice == Jani? " + j2.compareTo(j3));
        Name dave = new Name("Dave", 888, 2001);
        System.out.println("Dave == Janice? " + dave.compareTo(janice));
    }

}

