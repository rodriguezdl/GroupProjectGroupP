package com.example.daniel.signin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText Email;
    private EditText password;
    private Button login;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth= FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(getApplicationContext(),Account_Activity.class));
                }

                }
            };

        Email= findViewById(R.id.Email);
        password=findViewById(R.id.Password);

        login=findViewById(R.id.Login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
    }

    public void onStart (){
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
    }
                    
    private void SignIn(){
        String em= Email.getText().toString();
        String pass= password.getText().toString();
        final View view=this.getCurrentFocus();
        if(TextUtils.isEmpty(em)||TextUtils.isEmpty(pass)){

            Toast.makeText(getApplicationContext(),"The fields are empty", Toast.LENGTH_LONG).show();
        }else{

        mAuth.signInWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Sign in Unseccessful", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getApplicationContext(), Account_Activity.class));
                   // InputMethodManager im=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                   // im.hideSoftInputFromWindow(view.getWindowToken(),0);
                }
            }

        });


    }


}}
