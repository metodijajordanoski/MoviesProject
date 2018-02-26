package prvaaplikacija.moviesproject.models;

import java.util.ArrayList;

/**
 * Created by Mende on 12.2.2018.
 */

public class People {
    double popularity;
    int id;
    String profile_path;
    String name;

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Movie> getKnown_for() {
        return known_for;
    }

    public void setKnown_for(ArrayList<Movie> known_for) {
        this.known_for = known_for;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    ArrayList<Movie> known_for = new ArrayList<>();
    boolean adult;
}
