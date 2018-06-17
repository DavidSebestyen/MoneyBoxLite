package com.davids.android.moneyboxlite.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.davids.android.moneyboxlite.api.RetrofitClient;
import com.davids.android.moneyboxlite.api.RetrofitInterface;
import com.davids.android.moneyboxlite.R;
import com.davids.android.moneyboxlite.model.user.Session;
import com.davids.android.moneyboxlite.model.user.User;
import com.davids.android.moneyboxlite.model.user.UserLogIn;
import com.davids.android.moneyboxlite.model.user.UserLogInResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = SignInActivity.class.getName();

    @BindView(R.id.log_in_button)
    Button mLogInButton;

    @BindView(R.id.user_name_edit_text)
    EditText mUserNameEditText;

    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;

    User mUser;
    Session mSession;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        mLogInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(SignInActivity.this);
        progressDoalog.setMessage(getString(R.string.loading_details_txt));
        progressDoalog.setTitle(getString(R.string.log_in_txt));
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

//        UserLogIn userLogIn = new UserLogIn(mUserNameEditText.getText().toString(), mPasswordEditText.getText().toString());
        UserLogIn userLogIn = new UserLogIn("test+env12@moneyboxapp.com", "Money$$box@107");
        RetrofitInterface service = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);
        Call<UserLogInResponse> call = service.sendLogIn(userLogIn);
        call.enqueue(new Callback<UserLogInResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserLogInResponse> call, @NonNull Response<UserLogInResponse> response) {
                progressDoalog.dismiss();
                if(response.isSuccessful()) {
                    if(response.body() != null) {
                        mUser = response.body().getUserDetails();
                        mSession = response.body().getSession();
                        mUser.setBearerToken(mSession.getBearerToken());
                    }
                    Intent startUserPageIntent = new Intent(SignInActivity.this, UserPageActivity.class);
                    startUserPageIntent.putExtra(UserPageActivity.INTENT_USER_DETAILS_KEY, mUser);

                    startActivity(startUserPageIntent);
                } else {
                    Toast.makeText(SignInActivity.this, R.string.wrong_log_in_txt, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserLogInResponse> call, @NonNull Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(SignInActivity.this, getString(R.string.generic_error_txt), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
