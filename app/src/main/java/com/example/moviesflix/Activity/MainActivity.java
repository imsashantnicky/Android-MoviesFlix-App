package com.example.moviesflix.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.moviesflix.Fragments.DownloadsFragment;
import com.example.moviesflix.Fragments.HomeFragment;
import com.example.moviesflix.Fragments.NewmoviesFragment;
import com.example.moviesflix.Fragments.SettingsFragment;
import com.example.moviesflix.R;
import com.example.moviesflix.Utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnView;
    String api_key ="6a215d3a3b85b4319eaa45038a6f11c0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.setData(getApplicationContext(), "apiKey", api_key);

        bnView = (BottomNavigationView) findViewById(R.id.bnView);

        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id==R.id.nv_new_movies)
                {
                    loadFragment(new NewmoviesFragment(), false);
                }

                else if (id==R.id.nv_downloads)
                {
                    loadFragment(new DownloadsFragment(), false);
                }
                else
                { //for default navigation Home
                    loadFragment(new HomeFragment(), true);
                }

                return true;
            }

        });

        bnView.setSelectedItemId(R.id.nv_home);
    }


    //Function for loading Fragments
    public void loadFragment(Fragment fragment, boolean flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag)
            ft.add(R.id.fragments_container, fragment);
        else
            ft.replace(R.id.fragments_container, fragment);

        ft.commit();
    }

}

