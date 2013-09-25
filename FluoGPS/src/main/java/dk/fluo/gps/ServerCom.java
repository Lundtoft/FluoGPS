package dk.fluo.gps;

/**
 * Created by hagabrth on 9/25/13.
 */
public class ServerCom {

    /**
     * Properties
     */
    private String ipAddress;
    private int port;

    /**
     * Constructors
     */
    public ServerCom(String ipAddress, int port){
        this.ipAddress = ipAddress;
        this.port = port;
    }

    /**
     * Com Methods
     */
    public void sendFixToServer(String position){
        //TODO Method stub - Write implementation and correct paraemter type if neccesary
    }
}
