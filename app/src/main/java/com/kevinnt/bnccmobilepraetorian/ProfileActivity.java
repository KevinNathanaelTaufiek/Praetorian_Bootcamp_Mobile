package com.kevinnt.bnccmobilepraetorian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView tv_nama, tv_email;
    private Button btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tv_nama = findViewById(R.id.tv_nama);
        tv_email = findViewById(R.id.tv_email);
        btn_home = findViewById(R.id.btn_home);

        SharedPreferences sharedPreferences = this.getSharedPreferences("userData",MODE_PRIVATE);
        tv_nama.setText(sharedPreferences.getString("nama", "asd"));
        tv_email.setText(sharedPreferences.getString("email", "asd"));

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });

    }
}