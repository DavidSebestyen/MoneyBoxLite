package com.davids.android.moneyboxlite.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.davids.android.moneyboxlite.R;
import com.davids.android.moneyboxlite.adapter.ProductAdapter;
import com.davids.android.moneyboxlite.api.RetrofitClient;
import com.davids.android.moneyboxlite.api.RetrofitInterface;
import com.davids.android.moneyboxlite.model.product.Product;
import com.davids.android.moneyboxlite.model.product.Products;
import com.davids.android.moneyboxlite.model.user.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPageActivity extends AppCompatActivity{

    private final static String TAG = UserPageActivity.class.getName();

    private static final int SESSION_COUNTDOWN_TIME = 300000;
    public static final int SESSION_COUNTDOWN_TICK_TIME = 1000;

    public static final String INTENT_USER_DETAILS_KEY = "USER_DETAILS";
    public static final String INTENT_ACCOUNT_DETAILS_KEY = "ACCOUNT_DETAILS";
    public static final String INTENT_ACCOUNT_EXTRA_DETAILS_KEY = "ACCOUNT_EXTRA_DETAILS";
    public static final String INTENT_BEARER_TOKEN_KEY = "BEARER_TOKEN";
    public static final String INTENT_SESSION_COUNTDOWN_TIME = "SESSION_COUNTDOWN_PROGRESS";

    User mUser;
    List<Product> mListOfProducts = new ArrayList<>();
    CountDownTimer sessionTimer;
    long mSessionTimerProgress;

    @BindView(R.id.product_recycler_view)
    RecyclerView mProductList;

    @BindView(R.id.no_products_text)
    TextView mNoProductsText;

    @BindView(R.id.log_out_btn)
    Button mLogOutButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        startSessionTimer();
        ButterKnife.bind(this);

        mUser = getIntent().getParcelableExtra(INTENT_USER_DETAILS_KEY);

        if(mUser != null) {
            Toast.makeText(this, getString(R.string.welcome_back_txt) + mUser.getFirstName(), Toast.LENGTH_SHORT).show();
        }

        getProductsFromApi();

        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut(getString(R.string.successful_log_out_txt));
            }
        });

    }

    private void getProductsFromApi(){
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage(getString(R.string.loading_details_txt));
        progressDoalog.setTitle(getString(R.string.generic_logging_in_txt));
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        RetrofitInterface service = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);
        Call<Products> call = service.getProducts(mUser.getBearerToken());
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                progressDoalog.dismiss();
                if(response.isSuccessful()) {
                    if (response.body() != null) {

                        for (Product prod : response.body().getProducts()) {
                            mListOfProducts.add(prod);
                            Log.w(TAG, "Got product: " + prod.getPlanValue() + ", " + prod.getMoneybox());
                        }
                        mProductList.setHasFixedSize(true);
                        mProductList.setLayoutManager(new LinearLayoutManager(UserPageActivity.this));
                        mProductList.setAdapter(new ProductAdapter(UserPageActivity.this, mListOfProducts, new ProductAdapter.OnProductClickListener() {
                            @Override
                            public void onProductClick(int pos) {
                                Intent startAccountDetailsActivityIntent = new Intent(UserPageActivity.this, AccountDetailsActivity.class);
                                startAccountDetailsActivityIntent.putExtra(INTENT_ACCOUNT_DETAILS_KEY, mListOfProducts.get(pos));
                                startAccountDetailsActivityIntent.putExtra(INTENT_ACCOUNT_EXTRA_DETAILS_KEY, mListOfProducts.get(pos).getProduct());
                                startAccountDetailsActivityIntent.putExtra(INTENT_BEARER_TOKEN_KEY, mUser.getBearerToken());
                                startAccountDetailsActivityIntent.putExtra(INTENT_SESSION_COUNTDOWN_TIME, mSessionTimerProgress);
                                startActivity(startAccountDetailsActivityIntent);
                            }
                        }));
                    }
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                progressDoalog.dismiss();
            }
        });
    }

    private void logOut(final String logOutMessage){

        RetrofitInterface service = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);

        Call<Void> call = service.sendLogout(mUser.getBearerToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UserPageActivity.this, logOutMessage, Toast.LENGTH_SHORT).show();
                    Intent startSingInActivityIntent = new Intent(UserPageActivity.this, SignInActivity.class);
                    startActivity(startSingInActivityIntent);
                    finish();
                } else {
                    Toast.makeText(UserPageActivity.this, R.string.generic_error_txt, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setTitle(R.string.dialog_attention_txt);
        alertDlg.setMessage(R.string.log_out_question_txt);
        alertDlg.setPositiveButton(R.string.yes_txt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logOut(getString(R.string.successful_log_out_txt));
            }
        });
        alertDlg.setNegativeButton(R.string.no_txt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDlg.show();
    }


    private void startSessionTimer(){

        sessionTimer = new CountDownTimer(SESSION_COUNTDOWN_TIME, SESSION_COUNTDOWN_TICK_TIME) {
            @Override
            public void onTick(long millisUntilFinished) {
                mSessionTimerProgress = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                logOut(getString(R.string.logout_session_ended_text));
            }
        };
        sessionTimer.start();
    }
}
