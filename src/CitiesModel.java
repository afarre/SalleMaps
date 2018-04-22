public class CitiesModel {
    private String name;
    private String address;
    private String country;
    private double latitude;
    private double longitude;

    public CitiesModel(String name, String address, String country, long latitude, long longitude){
        this.name = name;
        this.address = address;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
