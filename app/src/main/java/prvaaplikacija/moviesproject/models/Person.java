package prvaaplikacija.moviesproject.models;

import java.util.ArrayList;

/**
 * Created by Mende on 12.2.2018.
 */

public class Person {
    String birthday;
    String deathday;
    int id;
    String name;
    ArrayList<String> also_known_as= new ArrayList<>();
    int gender;
    String biography;
    double popularity;
    String place_of_birth;
    String profile_path;
    boolean adult;

    public ArrayList<String> getAlso_known_as() {
        return also_known_as;
    }

    public void setAlso_known_as(ArrayList<String> also_known_as) {
        this.also_known_as = also_known_as;
    }

    String imdb_id;

    public Person() {
    }

    public String getBirthday() {

        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }
}
