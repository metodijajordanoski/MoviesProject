package prvaaplikacija.moviesproject.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import prvaaplikacija.moviesproject.BuildConfig;
import prvaaplikacija.moviesproject.LoggingInterceptor;
import prvaaplikacija.moviesproject.models.CreditsModel;
import prvaaplikacija.moviesproject.models.Movie;
import prvaaplikacija.moviesproject.models.MoviesModel;
import prvaaplikacija.moviesproject.models.PeopleModel;
import prvaaplikacija.moviesproject.models.Person;
import prvaaplikacija.moviesproject.models.PostFavourite;
import prvaaplikacija.moviesproject.models.RequestToken;
import prvaaplikacija.moviesproject.models.ResponseFavourites;
import prvaaplikacija.moviesproject.models.SessionModel;
import prvaaplikacija.moviesproject.models.VideoModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mende on 08.2.2018.
 */

public class RestApi {
    public static final int request_max_time_in_seconds = 20;

    public Retrofit getRetrofitInstance() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(request_max_time_in_seconds, TimeUnit.SECONDS)
                .connectTimeout(request_max_time_in_seconds, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder().setEncodedQueryParameter("Content-type","application/json").addQueryParameter("api_key","adc8cf4fe60c9e74d4260be93bdda240").build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                })
                .build();
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public ApiService request(){
        return getRetrofitInstance().create(ApiService.class);
    }


    public Call<MoviesModel> getPopularMovies(){
        return request().getPopularMovies();
    }


    public Call<MoviesModel> getTopRatedMovies(){
        return request().getTopRatedMovies();
    }

    public Call<MoviesModel> getUpComingMovies(){
        return request().getUpComingMovies();
    }

    public Call<MoviesModel> getNowPlayingMovies(){
        return request().getNowPlayingMovies();
    }

    public Call<MoviesModel> searchMovies(CharSequence keyword){
        return request().searchMovies(keyword);
    }

    public Call<PeopleModel> getPopularPeople() {
        return request().getPopularPeople();
    }

    public Call<Person> getPeople(int id) {
        return request().getPeople(id);
    }

    public Call<PeopleModel> searchPeoples(CharSequence keyword){
        return request().searchPeoples(keyword);
    }
    public Call<Movie> getMovie(int id) {
        return request().getMovie(id);
    }

    public Call<CreditsModel> getCredits(int id) {
        return request().getCredits(id);
    }

    public Call<VideoModel> getVideos(int id) {
        return request().getVideos(id);
    }

    public Call<RequestToken> getToken() {
        return request().getToken();
    }

    public Call<RequestToken> validateToken(String username, String password, String token) {
        return request().validateToken(username,password,token);
    }
    public Call<SessionModel> getSession(String token) {
        return request().getSession(token);
    }

    public Call<MoviesModel> getFavourites(String session_id) {
        return request().getFavourites(session_id);
    }

    public Call<ResponseFavourites> postFavourites(String session_id ,int id, String type, boolean isFav) {
        return request().postFavourites(session_id,id,type,isFav);
    }

}
