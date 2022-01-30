/**
 * Creates an object that stores the zip code, town name, and state code
 * of a particular location
 */
public class Place{
    protected String locationZip; //zip code of location
    protected String townName; //name of town
    protected String stateAbbreviation; //state abbreviation

    //main() method for testing purposes only
    public static void main(String[] args){
        Place p = new Place("12345", "Townsville", "NJ");
        System.out.println(p);
    }

    /**
     * Constructor, initializes instance variables
     * @param zip zip code
     * @param town town name
     * @param state state abbreviation
     */
    public Place(String zip, String town, String state){
        locationZip = zip.replaceAll("\"", ""); //removes "" from inputted String before assigning as value
        townName = town.replaceAll("\"", ""); //same as above
        stateAbbreviation = state.replaceAll("\"", "");//same as above
    }

    public Place(){
        //implicit constructor for inheritance purposes
    }

    //for if someone calls implicit constructor
    public void assignZip(String zip){
        locationZip = zip;
    }

    //for if someone calls implicit constructor
    public void assignTown(String town){
        townName = town;
    }

    //for if someone calls implicit constructor
    public void assignState(String state){
        stateAbbreviation = state;
    }

    /**
     * Returns zip code of location
     * @return locationZip
     */
    public String getZip(){
        return locationZip;
    }

    /**
     * returns town name of location
     * @return townName
     */
    public String getTown(){
        return townName;
    }

    /**
     * returns state abbreviation of location
     * @return stateAbbreviation
     */
    public String getState(){
        return stateAbbreviation;
    }

    @Override
    public String toString(){
        return townName + ", " + stateAbbreviation;
    }
}
