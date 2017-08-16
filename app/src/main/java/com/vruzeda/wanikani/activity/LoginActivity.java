package com.vruzeda.wanikani.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vruzeda.wanikani.R;
import com.vruzeda.wanikani.api.WanikaniApiProvider;
import com.vruzeda.wanikani.api.model.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String LOGIN_DATA_API_TOKEN = "LOGIN_DATA_API_TOKEN";
    public static final String LOGIN_DATA_USER_INFORMATION = "LOGIN_DATA_USER_INFORMATION";
    public static final String LOGIN_DATA_REMEMBER_ME = "LOGIN_DATA_REMEMBER_ME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSupportActionBar(null);

        View loginButton = findViewById(R.id.activity_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        final TextView apiTokenTextView = (TextView) findViewById(R.id.activity_login_api_token);

        final String apiToken = apiTokenTextView.getText().toString();
        if (apiToken.isEmpty()) {
            apiTokenTextView.setError(getString(R.string.activity_login_error_api_token_is_required));
            return;
        }

        WanikaniApiProvider.getInstance().userInfomartion(apiToken).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if (response.isSuccessful()) {
                    boolean rememberMe = ((CheckBox) findViewById(R.id.activity_login_remember_me)).isChecked();

                    Intent loginData = new Intent();
                    loginData.putExtra(LOGIN_DATA_API_TOKEN, apiToken);
                    loginData.putExtra(LOGIN_DATA_USER_INFORMATION, response.body().getUserInformation());
                    loginData.putExtra(LOGIN_DATA_REMEMBER_ME, rememberMe);

                    setResult(RESULT_OK, loginData);
                    finish();
                } else {
                    apiTokenTextView.setError(getString(R.string.activity_login_invalid_api_token));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                apiTokenTextView.setError(getString(R.string.activity_login_error_internet_connection));
            }
        });
    }
}
