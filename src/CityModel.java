import com.google.gson.JsonObject;

public class CityModel {
    private String name;
    private String address;
    private String country;
    private double latitude;
    private double longitude;

    public CityModel(String name, String address, String country, double latitude, double longitude){
        this.name = name;
        this.address = address;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CityModel(JsonObject object){
        this.name = object.get("name").getAsString();
        this.address = object.get("address").getAsString();
        this.country = object.get("country").getAsString();
        this.latitude = object.get("latitude").getAsDouble();
        this.longitude = object.get("longitude").getAsDouble();
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

    @Override
    public String toString() {
        return "{" +
                "name: " + name +
                ", address: " + address +
                ", country: " + country +
                ", latitude: " + latitude +
                ", longitude: " + longitude +
                '}';
    }
}
