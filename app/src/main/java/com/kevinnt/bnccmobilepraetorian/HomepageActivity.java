package com.kevinnt.bnccmobilepraetorian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomepageActivity extends AppCompatActivity {

    private Button btn_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragment_container, new HomepageFragment())
                .commit();

        btn_profile = findViewById(R.id.btn_profile);

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}