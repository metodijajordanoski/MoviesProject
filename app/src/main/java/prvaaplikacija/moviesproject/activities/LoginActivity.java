package prvaaplikacija.moviesproject.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import prvaaplikacija.moviesproject.Explorer;
import prvaaplikacija.moviesproject.R;
import prvaaplikacija.moviesproject.api.RestApi;
import prvaaplikacija.moviesproject.models.RequestToken;
import prvaaplikacija.moviesproject.models.SessionModel;
import prvaaplikacija.moviesproject.preferences.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.editUsername)
    EditText username;
    @BindView(R.id.editPassword)
    EditText password;
    @BindView(R.id.buttonLogin)
    Button login;
    RestApi api = new RestApi();
    String token="";
    String sessionid="";
    Context context;
    RequestToken requestToken = new RequestToken();
    RequestToken requestToken1= new RequestToken();
    SessionModel sessionModel = new SessionModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        context = this;


    }

    @OnClick(R.id.buttonLogin)
    public void onClick(View view) {
        Call<RequestToken> call = api.getToken();
        call.enqueue(new Callback<RequestToken>() {
            @Override
            public void onResponse(Call<RequestToken> call, Response<RequestToken> response) {
                requestToken = response.body();
                token=requestToken.getRequest_token();
                Call<RequestToken> call2 = api.validateToken(username.getText().toString(), password.getText().toString(), token);
                call2.enqueue(new Callback<RequestToken>() {
                    @Override
                    public void onResponse(Call<RequestToken> call, Response<RequestToken> response) {
                        requestToken1 = response.body();
                        PreferenceManager.setToken(LoginActivity.this, requestToken1.getRequest_token());
                        Call<SessionModel> call3 = api.getSession(token);
                        call3.enqueue(new Callback<SessionModel>() {
                            @Override
                            public void onResponse(Call<SessionModel> call, Response<SessionModel> response) {
                                sessionModel = response.body();
                                sessionid = sessionModel.getSession_id();
                                PreferenceManager.setSessionId(LoginActivity.this, sessionModel.getSession_id());
                                if(requestToken1.isSuccess()) {

                                    Intent i = new Intent(LoginActivity.this, Explorer.class);
                                    startActivity(i);
                                }
                            }

                            @Override
                            public void onFailure(Call<SessionModel> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<RequestToken> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<RequestToken> call, Throwable t) {

            }
        });







    }
}
