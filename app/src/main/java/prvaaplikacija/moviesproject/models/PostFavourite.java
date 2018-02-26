package prvaaplikacija.moviesproject.models;

/**
 * Created by Mende on 22.2.2018.
 */

public class PostFavourite {

    String media_type;

    int media_id;

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    boolean favorite;
}
