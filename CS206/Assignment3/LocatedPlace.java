public class LocatedPlace extends Place{

    protected String latitude; //latitude of location
    protected String longitude; //longitude of location

    //main() method for testing purposes only
    public static void main(String[] args){
        LocatedPlace p = new LocatedPlace("12345", "Townsville", "PU", "12.4", "88.3");
        System.out.println(p);
    }

    /**
     * Constructor
     * @param zip zip code
     * @param town town name
     * @param state state abbreviation
     * @param latitude latitude
     * @param longitude longitude
     */
    public LocatedPlace(String zip, String town, String state, String latitude, String longitude){
        locationZip = zip.replaceAll("\"", "");//removes "" from inputted String before setting as value
        townName = town.replaceAll("\"", "");//same as above
        stateAbbreviation = state.replaceAll("\"", "");//same as above
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocatedPlace(){
        //implicit constructor for inheritance
    }


    public String getLatitude(){
        return latitude;
    }

    public String getLongitude(){
        return longitude;
    }

    /**
     * for use if implicit constructor called
     * assigns value to latitude
     * @param latitude
     */
    public void assignLatitude(String latitude){
        this.latitude = latitude;
    }

    /**
     * for use if implicit constructor called
     * assigns value to longitude
     * @param longitude
     */
    public void assignLongitude(String longitude){
        this.longitude = longitude;
    }

    @Override
    public String toString(){
        return townName + ", " + stateAbbreviation + "  " + locationZip + 
        "        Lat: " + latitude + " Long: " + longitude;
    }
}
