package prvaaplikacija.moviesproject.listeners;

import android.view.View;

/**
 * Created by Mende on 14.2.2018.
 */

public interface OnMovieClickListener {

        public void onMovieClick(View view, int position);

        public void onFavourite(View view);
}
