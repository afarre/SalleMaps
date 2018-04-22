public class ConnectionsModel {
    private String from;
    private String to;
    private int distance;
    private int duration;

    public ConnectionsModel(String from, String to, int distance, int duration){
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.duration = duration;
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
}
