package prvaaplikacija.moviesproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import prvaaplikacija.moviesproject.Explorer;
import prvaaplikacija.moviesproject.R;
import prvaaplikacija.moviesproject.api.RestApi;
import prvaaplikacija.moviesproject.listeners.OnMovieClickListener;
import prvaaplikacija.moviesproject.models.Movie;
import prvaaplikacija.moviesproject.models.MoviesModel;
import prvaaplikacija.moviesproject.models.PostFavourite;
import prvaaplikacija.moviesproject.models.ResponseFavourites;
import prvaaplikacija.moviesproject.preferences.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mende on 09.2.2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    Context context;
    MoviesModel movies = new MoviesModel();
    MoviesModel moviesFav = new MoviesModel();
    RestApi api = new RestApi();
    OnMovieClickListener _onMovieClickListener;
    String session_id;

    public MoviesAdapter(Context context, OnMovieClickListener _onMovieClickListener) {
        this.context = context;
        this._onMovieClickListener = _onMovieClickListener;
    }

    public MoviesAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);



        View view = inflater.inflate(R.layout.recycler_movie,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Movie movie = movies.getResults().get(position);


        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185/"+movie.getPoster_path())
                .centerCrop()
                .fit()
                .into(holder.poster);

        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _onMovieClickListener.onMovieClick(holder.poster,position);
            }
        });



        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _onMovieClickListener.onFavourite(v);
                Picasso.with(context)
                        .load(R.drawable.favourites_full)
                        .centerCrop()
                        .fit()
                        .into(holder.favourite);
                session_id = PreferenceManager.getSessionId(context);


                Call<ResponseFavourites> call = api.postFavourites(session_id,movie.getId(),"movie",true);
                call.enqueue(new Callback<ResponseFavourites>() {
                    @Override
                    public void onResponse(Call<ResponseFavourites> call, Response<ResponseFavourites> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseFavourites> call, Throwable t) {
                        Log.d("TAG","failed",t);
                    }
                });
            }
        });

        holder.movieTitle.setText(movie.getOriginal_title());
        holder.textRating.setText(movie.getVote_average()+"");
        holder.textWatch.setText(movie.getPopularity()+"");

    }

    public void setItems(ArrayList<Movie> _movies) {
        movies.setResults(_movies);
        notifyDataSetChanged();
    }




    @Override
    public int getItemCount() {
        return movies.getResults().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.poster)
        ImageView poster;
        @BindView(R.id.textMovieTitle)
        TextView movieTitle;
        @BindView(R.id.textRating)
        TextView textRating;
        @BindView(R.id.textWatching)
        TextView textWatch;
        @BindView(R.id.favoriteImage)
        ImageView favourite;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
