package com.subbu.moviemasti.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.subbu.moviemasti.R;
import com.subbu.moviemasti.fragment.DiscoveryFragment;

public class DiscoveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new DiscoveryFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_discovery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        String[] filterValues = getResources().getStringArray(R.array.pref_sort_by_values);
        switch (item.getItemId()) {
            case R.id.action_popularity:
                sharedPrefs.edit().putString(getString(R.string.pref),
                        filterValues[0]).commit();
                break;
            case R.id.action_top_rated:
                sharedPrefs.edit().putString(getString(R.string.pref),
                        filterValues[1]).commit();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new DiscoveryFragment()).commit();
        return super.onOptionsItemSelected(item);
    }
}
