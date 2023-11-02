package com.example.moviesflix.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.moviesflix.R;

public class SecondDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_details);

        Intent intent = getIntent();
        String movieId = intent.getStringExtra("movie_id");

    }
}