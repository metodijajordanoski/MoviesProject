package prvaaplikacija.moviesproject.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import prvaaplikacija.moviesproject.R;
import prvaaplikacija.moviesproject.api.RestApi;
import prvaaplikacija.moviesproject.models.Movie;
import prvaaplikacija.moviesproject.models.People;
import prvaaplikacija.moviesproject.models.PeopleModel;
import prvaaplikacija.moviesproject.models.Person;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mende on 12.2.2018.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder>{
    Context context;
    PeopleModel peopleModel = new PeopleModel();
    RestApi api = new RestApi();
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);



        View view = inflater.inflate(R.layout.people_recycler_row,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final People people = peopleModel.getResults().get(position);

        Call<Person> call = api.getPeople(people.getId());
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                final Person person = response.body();
                holder.name.setText(person.getName());
                holder.overview.setText(person.getBiography());
                holder.birthDate.setText(person.getBirthday()+" "+person.getPlace_of_birth());
                Log.w("MyTag", "requestSuccesfull");
            }


            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.w("MyTag", "NOTSUCCESFFUL",t);
            }
        });
        holder.number.setText(position+1+"");
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185/"+people.getProfile_path())
                .centerCrop()
                .fit()
                .into(holder.profile_path);
    }

    public void setItems(ArrayList<People> _peoples) {
        peopleModel.setResults(_peoples);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return peopleModel.getResults().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profile_path)
        ImageView profile_path;
        @BindView(R.id.overview)
        TextView overview;
        @BindView(R.id.profile_name)
        TextView name;
        @BindView(R.id.actorMalefemale)
        TextView actorMaleFemale;
        @BindView(R.id.birthDate)
        TextView birthDate;
        @BindView(R.id.number)
        TextView number;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
