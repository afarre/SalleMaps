import com.google.gson.JsonObject;

public class ConnectionModel {
    private String from;
    private String to;
    private int distance;
    private int duration;

    public ConnectionModel (){}

    public ConnectionModel(String from, String to, int distance, int duration){
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.duration = duration;
    }

    public ConnectionModel(JsonObject cities) {
        this.from = cities.get("from").getAsString();
        this.to = cities.get("to").getAsString();
        this.distance = cities.get("distance").getAsInt();
        this.duration = cities.get("duration").getAsInt();
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString () {
        return "{" +
                "from: " + from + "," +
                "to: " + to + "," +
                "distance: " + distance + " meters" +
                "duration: " + duration + " seconds" +
                "}";
    }

}
