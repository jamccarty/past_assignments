public class PopulatedPlace extends LocatedPlace{
    private String population; //population of location

    //main() method for testing purposes only
    public static void main(String[] args){
        PopulatedPlace p = new PopulatedPlace("12345", "Townsville", "PU", "12.4", "88.3", "12");
        System.out.println(p);
    }

    /**
     * Constructor
     * @param zip zip code
     * @param town town name
     * @param state state abbreviation
     * @param latitude latitude
     * @param longitude longitude
     * @param pop population
     */
    public PopulatedPlace(String zip, String town, String state, String latitude, String longitude, String pop){
        locationZip = zip.replaceAll("\"", "");//removes "" from inputted String before setting as value
        townName = town.replaceAll("\"", "");//same as above
        stateAbbreviation = state.replaceAll("\"", "");//same as above
        this.latitude = latitude;
        this.longitude = longitude;
        population = pop;
    }

    /**
     * returns population of location
     * @return population
     */
    public String getPopulation(){
        return population;
    }

    @Override
    public String toString(){
        return townName + ", " + stateAbbreviation + "  " + locationZip + 
        "        Pop: " + population + " Lat: " + latitude + " Long: " + longitude;
    }
}
