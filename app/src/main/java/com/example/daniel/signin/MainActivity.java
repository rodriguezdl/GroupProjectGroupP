package com.example.daniel.signin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText Email;
    private EditText password;
    private TextView resetpw;
    private Button login;
    private Button register;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView welcome;
    private Typeface font;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        font= Typeface.createFromAsset(getAssets(),"myFont.ttf");

        mAuth= FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(getApplicationContext(),Account_Activity.class));
                }

                }
            };
        welcome= findViewById(R.id.WelcomeString);
        welcome.setTypeface(font);
        Email= findViewById(R.id.Email);
        password=findViewById(R.id.Password);
        resetpw=findViewById(R.id.pwreset);
        register=findViewById(R.id.Register);
        login=findViewById(R.id.Login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });



        String text="Forgot Password Click Here";//FORGOT PASSWORD LINK
        SpannableString ss= new SpannableString(text);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(getApplicationContext(),PasswordReset.class));
            }
        };
        ss.setSpan(clickableSpan,0,26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        resetpw.setText(ss);
        resetpw.setMovementMethod(LinkMovementMethod.getInstance());



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
                    startActivity(new Intent(getApplicationContext(), HomeNav.class));
                    InputMethodManager im=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(view.getWindowToken(),0);
                }
            }

        });


    }


}}
