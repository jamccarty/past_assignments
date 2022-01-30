/**
 * Creates an object that stores the zip code, town name, and state code
 * of a particular location
 */
public class Place{
    private String locationZip; //zip code of location
    private String townName; //name of town
    private String stateAbbreviation; //state abbreviation

    public static void main(String[] args){
        Place p = new Place("12345", "Townsville", "NJ");
        System.out.println(p);
    }

    /**
     * Constructor, initializes instance variables
     * @param zip zip code of location
     * @param town name of town
     * @param state state abbreviation
     */
    public Place(String zip, String town, String state){
        locationZip = zip;
        townName = town;
        stateAbbreviation = state;
    }

    public String getZip(){
        return locationZip;
    }

    public String getTown(){
        return townName;
    }

    public String getState(){
        return stateAbbreviation;
    }

    @Override
    public String toString(){
        return townName + ", " + stateAbbreviation;
    }
}