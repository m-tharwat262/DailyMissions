package com.example.android.todo_missions.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.todo_missions.R;
import com.example.android.todo_missions.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {


    private ActivityLoginBinding mBinding;

    private ProgressDialog mProgressDialog;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    private String mEmailOrUserName = "";
    private String mPassword = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("انتظر من فضلك");
        mProgressDialog.setMessage("جار تسجيل الدخول ...");
        mProgressDialog.setCanceledOnTouchOutside(false);

        mFirebaseAuth = FirebaseAuth.getInstance();

        checkUserAlreadyLoggedIn();




        setClickingOnLogin();

        setClickingOnForgetPassword();

        setClickingOnCreateAccount();

    }

    private void checkUserAlreadyLoggedIn() {

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        if(firebaseUser != null) {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

    }

    private void validateData() {

        // get data
        mEmailOrUserName = mBinding.activityLoginUserName.getText().toString().trim();
        mPassword = mBinding.activityLoginUserPassword.getText().toString();


        if (TextUtils.isEmpty(mEmailOrUserName)) {

            mBinding.activityLoginUserName.setError("ادخل اسم المسخدم او ابريد الإلكتروني اولا");

        } else if (TextUtils.isEmpty(mPassword)) {

            mBinding.activityLoginUserPassword.setError("ادخل كلمة المرور");

        } else {

            LoginWithEmailOrUserName();

        }

    }


    private void LoginWithEmailOrUserName() {

        if(Patterns.EMAIL_ADDRESS.matcher(mEmailOrUserName).matches()) {

            firebaseLogin(mEmailOrUserName, mPassword);

        } else {

            tryLoginWithUserName();

        }
    }

    private void tryLoginWithUserName() {

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String email = "";

                for(DataSnapshot ds : snapshot.getChildren()) {

                    String userName = ds.child("userName").getValue(String.class);

                    if (mEmailOrUserName.equals(userName)) {

                        email = ds.child("userEmail").getValue(String.class);

                    }

                }


                if (email.isEmpty()) {
                    mBinding.activityLoginUserName.setError("اسم المستخدم غير صحيح");
                    mBinding.activityLoginUserName.requestFocus();

                } else {

                    firebaseLogin(email, mPassword);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }


    private void firebaseLogin(String email, String password) {

        mProgressDialog.show();

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

//                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

//                        if(firebaseUser.isEmailVerified()) {

//                            String email = firebaseUser.getEmail();
//
//                            Toast.makeText(LoginActivity.this, "Logged In\n" + email , Toast.LENGTH_SHORT).show();

                        mProgressDialog.dismiss();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

//                        }
//                        else {
//
//                            firebaseUser.sendEmailVerification();
//
//                            Toast.makeText(LoginActivity.this, "افحص بريدك الإلكتروني لتأكيد حسابك", Toast.LENGTH_SHORT).show();
//
//                        }



                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        mProgressDialog.dismiss();

                        Toast.makeText(LoginActivity.this, "فشل تسجيل الدخول", Toast.LENGTH_SHORT).show();

                    }
                });

    }











    private void setClickingOnLogin() {

        TextView textView = findViewById(R.id.activity_login_login_button);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateData();

            }
        });

    }


    private void setClickingOnForgetPassword() {

        TextView textView = findViewById(R.id.activity_login_forget_password);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);


            }
        });

    }



    private void setClickingOnCreateAccount() {

        TextView textView = findViewById(R.id.activity_login_create_account);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, CreateAccount.class);
                startActivity(intent);


            }
        });

    }



}