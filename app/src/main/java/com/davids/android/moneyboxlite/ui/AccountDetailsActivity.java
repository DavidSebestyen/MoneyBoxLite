package com.davids.android.moneyboxlite.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.davids.android.moneyboxlite.R;
import com.davids.android.moneyboxlite.api.RetrofitClient;
import com.davids.android.moneyboxlite.api.RetrofitInterface;
import com.davids.android.moneyboxlite.model.payment.Payment;
import com.davids.android.moneyboxlite.model.payment.PaymentResponse;
import com.davids.android.moneyboxlite.model.product.Product;
import com.davids.android.moneyboxlite.model.product.ProductDetails;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.davids.android.moneyboxlite.ui.UserPageActivity.SESSION_COUNTDOWN_TICK_TIME;

public class AccountDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = AccountDetailsActivity.class.getName();

    Product mProduct;
    ProductDetails mProductDetails;
    CountDownTimer sessionTimer;

    long mSessionTimerProgress;
    String mBearerToken;

    @BindView(R.id.account_name_text_view)
    TextView mAccountNameTV;

    @BindView(R.id.account_value_text_view)
    TextView mAccountValueTV;

    @BindView(R.id.moneybox_value_text_view)
    TextView mMoneyboxValueTV;

    @BindView(R.id.add_monery_btn)
    Button mAddMoneyBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        ButterKnife.bind(this);

        mProduct = getIntent().getParcelableExtra(UserPageActivity.INTENT_ACCOUNT_DETAILS_KEY);
        mProductDetails = getIntent().getParcelableExtra(UserPageActivity.INTENT_ACCOUNT_EXTRA_DETAILS_KEY);
        mSessionTimerProgress = getIntent().getLongExtra(UserPageActivity.INTENT_SESSION_COUNTDOWN_TIME, 300000);

        startSessionTimer();

        if(mProduct != null){
            mAccountNameTV.setText(mProductDetails.getFriendlyName());
            mAccountValueTV.setText(mProduct.getFormattedPlanValue());
            mMoneyboxValueTV.setText(mProduct.getFormattedMoneyBox());
        }

        mBearerToken = getIntent().getStringExtra(UserPageActivity.INTENT_BEARER_TOKEN_KEY);

        mAddMoneyBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage(getString(R.string.procees_money_txt));
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        Payment payment = new Payment(10.00, mProduct.getInvestorProductId());
        RetrofitInterface service = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);
        Call<PaymentResponse> call = service.postPayment(payment, mBearerToken);
        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                progressDoalog.dismiss();
                if(response.isSuccessful()){
                    if(response.body() != null) {
                        Log.w(TAG, "Value added: " + response.body().getMoneybox());
                        mProduct.setMoneybox(response.body().getMoneybox());
                        mMoneyboxValueTV.setText(getString(R.string.pound_sign_txt) + String.valueOf(response.body().getMoneybox()));
                    }
                } else {
                    Log.w(TAG, getString(R.string.generic_error_txt));
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                progressDoalog.dismiss();
            }
        });

    }

    private void startSessionTimer(){

        sessionTimer = new CountDownTimer(mSessionTimerProgress, SESSION_COUNTDOWN_TICK_TIME) {
            @Override
            public void onTick(long millisUntilFinished) {
                mSessionTimerProgress = millisUntilFinished;
                if(millisUntilFinished <= 60000){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AccountDetailsActivity.this);
                    dialog.setTitle(R.string.dialog_attention_txt);
                    dialog.setMessage(R.string.timer_warning_txt);
                    dialog.setPositiveButton(R.string.ok_txt, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            }

            @Override
            public void onFinish() {
                logOut(getString(R.string.logout_session_ended_text));
            }
        };
        sessionTimer.start();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void logOut(final String logOutMessage){

        RetrofitInterface service = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);

        Call<Void> call = service.sendLogout(mBearerToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AccountDetailsActivity.this, logOutMessage, Toast.LENGTH_SHORT).show();
                    Intent startSingInActivityIntent = new Intent(AccountDetailsActivity.this, SignInActivity.class);
                    startActivity(startSingInActivityIntent);
                    finish();
                } else {
                    Toast.makeText(AccountDetailsActivity.this, R.string.generic_error_txt, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });

    }
}
