package com.kevinnt.bnccmobilepraetorian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText et_email_login, et_password_login;
    private Button btn_login;
    private TextView tv_sign_up;

    private FirebaseAuth auth;

    private View.OnClickListener goSignUp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            boolean isValid = true;

            if(et_password_login.getText().toString().isEmpty()){
                et_password_login.setError("Password must not be empty");
                et_password_login.requestFocus();
                isValid = false;
            }
            if(et_email_login.getText().toString().isEmpty()){
                et_email_login.setError("Email must not be empty");
                et_email_login.requestFocus();
                isValid = false;
            }

            if (isValid){
                loginUser();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        auth = FirebaseAuth.getInstance();

        initViews();

        tv_sign_up.setOnClickListener(goSignUp);
        btn_login.setOnClickListener(login);
    }

    private void initViews() {
        et_email_login = findViewById(R.id.et_email_signup);
        et_password_login = findViewById(R.id.et_password_signup);
        btn_login = findViewById(R.id.btn_sign_up);
        tv_sign_up = findViewById(R.id.tv_sign_up);
    }

    private void loginUser(){
        String email = et_email_login.getText().toString();
        String password = et_password_login.getText().toString();
        SharedPreferences sharedPreferences = this.getSharedPreferences("userData",MODE_PRIVATE);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = auth.getCurrentUser();

                            FirebaseDatabase db = FirebaseDatabase.getInstance("https://bnccmobilepraetorian-default-rtdb.asia-southeast1.firebasedatabase.app/");
                            DatabaseReference ref = db.getReference(user.getUid());

                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User loginUser = dataSnapshot.getValue(User.class);


                                    SharedPreferences.Editor spEditor = sharedPreferences.edit();

                                    spEditor.putString("nama", loginUser.getNama());
                                    spEditor.putString("email", loginUser.getEmail());
                                    spEditor.apply();

                                    Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                }
                            });
                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}