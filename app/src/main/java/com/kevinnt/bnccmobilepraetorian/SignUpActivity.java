package com.kevinnt.bnccmobilepraetorian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText et_name_signup, et_email_signup, et_password_signup, et_confirm_password_signup;
    private Button btn_sign_up;
    private TextView tv_login;

    private FirebaseAuth auth;

    private View.OnClickListener goLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };

    private View.OnClickListener signUp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean isValid = true;
            String name = et_name_signup.getText().toString();
            String email = et_email_signup.getText().toString();
            String password = et_password_signup.getText().toString();
            String confirm = et_confirm_password_signup.getText().toString();

            if(name.isEmpty()){
                et_name_signup.setError("Name must not be empty");
                et_name_signup.requestFocus();
                isValid = false;
            }
            else if(name.length() < 5){
                et_name_signup.setError("Name minimum 5 characters");
                et_name_signup.requestFocus();
                isValid = false;
            }

            if(email.isEmpty()){
                et_email_signup.setError("Email must not be empty");
                et_email_signup.requestFocus();
                isValid = false;
            }
            else if(!email.contains("@") ||
                    !email.substring(email.length()-4, email.length())
                            .equals(".com")){
                et_email_signup.setError("Email must contain '@' and ended with '.com'");
                et_email_signup.requestFocus();
                isValid = false;
            }

            if(password.isEmpty()){
                et_password_signup.setError("Password must not be empty");
                et_password_signup.requestFocus();
                isValid = false;
            }
            else if(password.length() < 6){
                et_password_signup.setError("Password minimum 6 characters");
                et_password_signup.requestFocus();
                isValid = false;
            }

            if(confirm.isEmpty()){
                et_confirm_password_signup.setError("Password Confirmation must not be empty");
                et_confirm_password_signup.requestFocus();
                isValid = false;
            }
            else if(!confirm.equals(password)){
                et_confirm_password_signup.setError("Password Confirmation must same with Password");
                et_confirm_password_signup.requestFocus();
                isValid = false;
            }
            else if(confirm.length() < 6){
                et_confirm_password_signup.setError("Password confirmation minimum 6 characters");
                et_confirm_password_signup.requestFocus();
                isValid = false;
            }


            if (isValid) {
                createNewUser();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        initViews();
        tv_login.setOnClickListener(goLogin);
        btn_sign_up.setOnClickListener(signUp);
    }

    private void initViews() {
        et_name_signup = findViewById(R.id.et_name_signup);
        et_email_signup = findViewById(R.id.et_email_signup);
        et_password_signup = findViewById(R.id.et_password_signup);
        et_confirm_password_signup = findViewById(R.id.et_confirm_password_signup);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        tv_login = findViewById(R.id.tv_login);
    }

    private void createNewUser() {

        String email = et_email_signup.getText().toString();
        String password = et_password_signup.getText().toString();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String nama = et_name_signup.getText().toString();

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            User user = new User(firebaseUser.getUid().toString(), nama, email);

                            FirebaseDatabase db = FirebaseDatabase.getInstance("https://bnccmobilepraetorian-default-rtdb.asia-southeast1.firebasedatabase.app/");
                            DatabaseReference ref = db.getReference(user.getUid());
                            ref.setValue(user);

                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}