package com.example.daniel.signin;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class emailVerifyScreen extends AppCompatActivity {
private Button emailBtn;
private Button home;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseUser user= mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify_screen);

        emailBtn= findViewById(R.id.EmailButton);
        home = findViewById(R.id.Home);

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verification();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),homeActivity.class));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });





    }

    public void verification(){
        final View view = getCurrentFocus();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            public static final String TAG = "" ;

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent= new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                    startActivity(intent);
                }else{
                    Log.d(TAG, "Verification not working");
                    Snackbar.make(view, "Oops, something is wrong with verification system. Please try again", Snackbar.LENGTH_LONG).show();
                }
                
            }
        });

    }
}
