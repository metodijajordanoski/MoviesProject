package prvaaplikacija.moviesproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import prvaaplikacija.moviesproject.R;
import prvaaplikacija.moviesproject.activities.MovieDetails;
import prvaaplikacija.moviesproject.adapters.MoviesAdapter;
import prvaaplikacija.moviesproject.api.RestApi;
import prvaaplikacija.moviesproject.listeners.OnMovieClickListener;
import prvaaplikacija.moviesproject.models.MoviesModel;
import prvaaplikacija.moviesproject.preferences.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mende on 08.2.2018.
 */

public class FavouritesFragment extends Fragment {
    private Unbinder mUnbinder;
    RestApi api = new RestApi();
    Context context;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.editSearch)
    EditText search;
    String session_id;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    MoviesModel moviesModel = new MoviesModel();
    MoviesAdapter adapter = new MoviesAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_favourites,null);
        context=getContext();
        mUnbinder = ButterKnife.bind(this, view);
        session_id= PreferenceManager.getSessionId(context);
        refreshItems(session_id);


        final OnMovieClickListener _onMovieClickListener = new OnMovieClickListener() {
            @Override
            public void onMovieClick(View view, int position) {
                int id = moviesModel.getResults().get(position).getId();
                Intent i = new Intent(view.getContext(),MovieDetails.class);
                i.putExtra("EXTRA_ID",id);
                startActivity(i);
            }

            @Override
            public void onFavourite(View view) {
                Toast.makeText(context,"Was added succesfully",Toast.LENGTH_SHORT).show();
            }
        };
        adapter = new MoviesAdapter(view.getContext(),_onMovieClickListener);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()!= 0) {
                    searchMovies(session_id);
                } else {
                    refreshItems(session_id);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems(session_id);
            }
        });


//
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    void onItemsLoadComplete() {
        swipe.setRefreshing(false);
    }


    void searchMovies(String sequence) {
            Call<MoviesModel> call = api.getFavourites(sequence);
            call.enqueue(new Callback<MoviesModel>() {
                @Override
                public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                    moviesModel = response.body();
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    adapter.setItems(moviesModel.getResults());
                    recyclerView.setAdapter(adapter);
                    Log.w("MyTag", "requestSuccesfull");
                }

                @Override
                public void onFailure(Call<MoviesModel> call, Throwable t) {
                    Toast.makeText(context,"Favourite list is empthy! ", Toast.LENGTH_LONG).show();
                }
            });
    }


    void refreshItems(String id) {
        Call<MoviesModel> calls = api.getFavourites(id);
        calls.enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                moviesModel = response.body();
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                adapter.setItems(moviesModel.getResults());
                recyclerView.setAdapter(adapter);
                Log.w("MyTag", "requestSuccesfull");
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.w("MyTag", "requestFailed",t);
            }
        });
        onItemsLoadComplete();
    }


}
