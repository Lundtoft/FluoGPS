package dk.fluo.gps;

/**
 * Created by hagbarth on 9/25/13.
 */
public class GPSFixer {

    /**
     * Properties
     */
    private int timePeriod;
    private int dist;

    /**
     * Constructors
     */
    public GPSFixer(int timePeriod, int dist){
        this.timePeriod = timePeriod;
        this.dist = dist;
    }

    /**
     * Setters and getters
     */
    public int getTimePeriod(){
        return timePeriod;
    }

    public int getDist(){
        return dist;
    }

    public void setTimePeriod(int timePeriod){
        this.timePeriod = timePeriod;
    }

    public void setDist(int dist){
        this.dist = dist;
    }

    /**
     * GPS related method
     */
    public void getPosition(){
        //TODO Method stub - Write implementation and correct void to the right return object.
    }
}
