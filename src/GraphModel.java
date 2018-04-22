import java.util.ArrayList;

public class GraphModel {
    private ArrayList<CitiesModel> cities;
    private ArrayList<ConnectionsModel> connections;

    public GraphModel(){

    }

    public ArrayList<CitiesModel> getCities() {
        return cities;
    }

    public ArrayList<ConnectionsModel> getConnections() {
        return connections;
    }

}
