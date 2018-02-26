package prvaaplikacija.moviesproject.models;

import java.util.ArrayList;

/**
 * Created by Mende on 14.2.2018.
 */

public class VideoModel {
    int id;
    ArrayList<Video> results = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Video> getResults() {
        return results;
    }

    public void setResults(ArrayList<Video> results) {
        this.results = results;
    }
}
