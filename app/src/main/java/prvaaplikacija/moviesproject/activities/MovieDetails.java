package prvaaplikacija.moviesproject.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toolbar;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import prvaaplikacija.moviesproject.R;
import prvaaplikacija.moviesproject.api.RestApi;
import prvaaplikacija.moviesproject.models.CreditsModel;
import prvaaplikacija.moviesproject.models.Movie;
import prvaaplikacija.moviesproject.models.VideoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetails extends AppCompatActivity {

    @BindView(R.id.imageMovie)
    ImageView image;
    Context context;
    @BindView(R.id.textName)
    TextView textViewName;
    @BindView(R.id.textDirector)
    TextView textDirector;
    @BindView(R.id.textWriters)
    TextView textWriters;
    @BindView(R.id.textStars)
    TextView textStars;
    @BindView(R.id.textDescription)
    TextView textDescription;
    RestApi api = new RestApi();
    Movie movie = new Movie();
    CreditsModel creditsModel = new CreditsModel();
    int id;
    VideoModel videoModel = new VideoModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        Intent i = getIntent();
        if(i.hasExtra("EXTRA_ID")) {
            id = i.getIntExtra("EXTRA_ID",0);
        }
        setDetails();

        Call<VideoModel> callVideos = api.getVideos(id);
        callVideos.enqueue(new Callback<VideoModel>() {
            @Override
            public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {
                videoModel = response.body();
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String m = "https://www.youtube.com/watch?v="+videoModel.getResults().get(0).getKey();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(m)));
                    }
                });
            }

            @Override
            public void onFailure(Call<VideoModel> call, Throwable t) {

            }
        });


    }

    void setDetails() {
        Call<Movie> call = api.getMovie(id);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movie = response.body();
                textViewName.setText(movie.getTitle());
                textDescription.setText(movie.getOverview());
                Picasso.with(MovieDetails.this)
                        .load("http://image.tmdb.org/t/p/w185/"+movie.getPoster_path())
                        .centerCrop()
                        .fit()
                        .into(image);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

        Call<CreditsModel> call2 = api.getCredits(id);
        call2.enqueue(new Callback<CreditsModel>() {
            @Override
            public void onResponse(Call<CreditsModel> call, Response<CreditsModel> response) {
                creditsModel = response.body();
                for(int i = 0; i<creditsModel.getCrew().size(); i ++) {
                    if(creditsModel.getCrew().get(i).getDepartment().equals("Writing"))
                        textWriters.setText(creditsModel.getCrew().get(i).getName()+"  ");

                }
                textDirector.setText(creditsModel.getCrew().get(0).getName()+", "+creditsModel.getCrew().get(1).getName());

                textStars.setText(creditsModel.getCast().get(0).getName()+", "+creditsModel.getCast().get(1).getName()+", "+creditsModel.getCast().get(2).getName());
            }

            @Override
            public void onFailure(Call<CreditsModel> call, Throwable t) {

            }
        });


    }
}
