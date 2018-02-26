package prvaaplikacija.moviesproject.models;

/**
 * Created by Mende on 16.2.2018.
 */

public class SessionModel {

    boolean success;
    String session_id;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
