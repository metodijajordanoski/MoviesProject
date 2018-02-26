package prvaaplikacija.moviesproject.models;

import java.util.ArrayList;

/**
 * Created by Mende on 12.2.2018.
 */

public class PeopleModel {

    int page;
    int total_results;
    int total_pages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<People> getResults() {
        return results;
    }

    public void setResults(ArrayList<People> results) {
        this.results = results;
    }

    ArrayList<People> results = new ArrayList<>();
}
