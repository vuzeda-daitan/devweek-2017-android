package com.vruzeda.wanikani.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vruzeda.wanikani.R;
import com.vruzeda.wanikani.api.model.UserInformation;
import com.vruzeda.wanikani.fragment.KanaListFragment;
import com.vruzeda.wanikani.fragment.KanjiListFragment;
import com.vruzeda.wanikani.fragment.RadicalListFragment;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_ACTIVITY_API_TOKEN = "MAIN_ACTIVITY_API_TOKEN";

    private static final int LOGIN_REQUEST_CODE = 1;

    private static final String WANIKANI_SHARED_PREFERENCES = "WANIKANI_SHARED_PREFERENCES";
    private static final String WANIKANI_SHARED_PREFERENCES_API_TOKEN = "WANIKANI_SHARED_PREFERENCES_API_TOKEN";
    private static final String WANIKANI_SHARED_PREFERENCES_USER_INFORMATION = "WANIKANI_SHARED_PREFERENCES_USER_INFORMATION";

    private String apiToken;
    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiToken = retrieveApiToken();
        userInformation = retrieveUserInformation();

        if (apiToken == null || userInformation == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);
        } else {
            configureDashboard(apiToken, userInformation);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.activity_main_menu_logout) {
            storeApiToken(null);
            storeUserInformation(null);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOGIN_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    apiToken = data.getStringExtra(LoginActivity.LOGIN_DATA_API_TOKEN);
                    userInformation = (UserInformation) data.getSerializableExtra(LoginActivity.LOGIN_DATA_USER_INFORMATION);

                    boolean rememberMe = data.getBooleanExtra(LoginActivity.LOGIN_DATA_REMEMBER_ME, false);
                    if (rememberMe) {
                        storeApiToken(apiToken);
                        storeUserInformation(userInformation);
                    }

                    configureDashboard(apiToken, userInformation);
                } else {
                    finish();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String retrieveApiToken() {
        SharedPreferences sharedPreferences = getSharedPreferences(WANIKANI_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(WANIKANI_SHARED_PREFERENCES_API_TOKEN, null);
    }

    private void storeApiToken(String apiToken) {
        SharedPreferences sharedPreferences = getSharedPreferences(WANIKANI_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WANIKANI_SHARED_PREFERENCES_API_TOKEN, apiToken);
        editor.apply();
    }

    private UserInformation retrieveUserInformation() {
        UserInformation userInformation = null;

        SharedPreferences sharedPreferences = getSharedPreferences(WANIKANI_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String userInformationJson = sharedPreferences.getString(WANIKANI_SHARED_PREFERENCES_USER_INFORMATION, null);
        if (userInformationJson != null) {
            Gson gson = new Gson();
            userInformation = gson.fromJson(userInformationJson, UserInformation.class);
        }

        return userInformation;
    }

    private void storeUserInformation(UserInformation userInformation) {
        SharedPreferences sharedPreferences = getSharedPreferences(WANIKANI_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (userInformation != null) {
            Gson gson = new Gson();
            String userInformationJson = gson.toJson(userInformation);
            editor.putString(WANIKANI_SHARED_PREFERENCES_USER_INFORMATION, userInformationJson);
        } else {
            editor.remove(WANIKANI_SHARED_PREFERENCES_USER_INFORMATION);
        }
        editor.apply();
    }

    private void configureDashboard(String apiToken, UserInformation userInformation) {
        ImageView avatarImageView = (ImageView) findViewById(R.id.activity_main_avatar);
        Picasso.with(this)
                .load("https://www.gravatar.com/avatar/" + userInformation.getGravatar() + "?d=mm&s=200")
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(avatarImageView);

        TextView usernameTextView = (TextView) findViewById(R.id.activity_main_username);
        usernameTextView.setText(userInformation.getUsername());

        TextView titleTextView = (TextView) findViewById(R.id.activity_main_title);
        titleTextView.setText(userInformation.getTitle());

        KanaPagerAdapter kanaPagerAdapter = new KanaPagerAdapter(getSupportFragmentManager(), apiToken);

        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_main_pager);
        viewPager.setAdapter(kanaPagerAdapter);
    }

    private class KanaPagerAdapter extends FragmentStatePagerAdapter {

        List<KanaListFragment> fragmentList;

        KanaPagerAdapter(FragmentManager fragmentManager, String apiToken) {
            super(fragmentManager);

            fragmentList = Arrays.asList(new RadicalListFragment(), new KanjiListFragment());

            Bundle arguments = new Bundle();
            arguments.putString(MAIN_ACTIVITY_API_TOKEN, apiToken);

            for (Fragment fragment : fragmentList) {
                fragment.setArguments(arguments);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentList.get(position).getTitle(MainActivity.this);
        }
    }
}
