package prvaaplikacija.moviesproject.models;

/**
 * Created by Mende on 25.2.2018.
 */

public class ResponseFavourites {

    int status_code;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    String status_message;
}
