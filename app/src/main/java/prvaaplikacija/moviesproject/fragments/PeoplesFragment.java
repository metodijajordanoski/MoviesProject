package prvaaplikacija.moviesproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import prvaaplikacija.moviesproject.R;
import prvaaplikacija.moviesproject.adapters.PeopleAdapter;
import prvaaplikacija.moviesproject.api.RestApi;
import prvaaplikacija.moviesproject.models.MoviesModel;
import prvaaplikacija.moviesproject.models.PeopleModel;
import prvaaplikacija.moviesproject.models.Person;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mende on 08.2.2018.
 */

public class PeoplesFragment extends Fragment {
    private Unbinder mUnbinder;
    Context context;

    @BindView(R.id.recyclerViewPeople)
    RecyclerView recyclerView;
    @BindView(R.id.peopleSearch)
    EditText peopleSearch;
    RestApi api = new RestApi();
    PeopleModel peopleModel = new PeopleModel();
    PeopleAdapter adapter = new PeopleAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_people,null);
        context=getContext();
        mUnbinder = ButterKnife.bind(this, view);
        refreshItems();
        peopleSearch.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(s.length()!=0) {
                   searchPeople(s);
               } else {
                   refreshItems();
               }
           }

           @Override
           public void afterTextChanged(Editable s) {

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

    void refreshItems() {
        Call<PeopleModel> call = api.getPopularPeople();
        call.enqueue(new Callback<PeopleModel>() {
            @Override
            public void onResponse(Call<PeopleModel> call, Response<PeopleModel> response) {
                peopleModel = response.body();
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter.setItems(peopleModel.getResults());
                recyclerView.setAdapter(adapter);
                Log.w("MyTag", "requestSuccesfull");
            }

            @Override
            public void onFailure(Call<PeopleModel> call, Throwable t) {

            }
        });
    }
    void searchPeople(CharSequence sequence) {
        Call<PeopleModel> call = api.searchPeoples(sequence);
        call.enqueue(new Callback<PeopleModel>() {
            @Override
            public void onResponse(Call<PeopleModel> call, Response<PeopleModel> response) {
                peopleModel = response.body();
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter.setItems(peopleModel.getResults());
                recyclerView.setAdapter(adapter);
                Log.w("MyTag", "requestSuccesfull");
            }

            @Override
            public void onFailure(Call<PeopleModel> call, Throwable t) {

            }
        });
    }



}
