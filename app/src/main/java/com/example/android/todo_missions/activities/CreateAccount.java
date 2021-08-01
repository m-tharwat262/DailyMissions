package com.example.android.todo_missions.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.todo_missions.R;
import com.example.android.todo_missions.databinding.ActivityCreateAccountBinding;
import com.example.android.todo_missions.models.UserObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CreateAccount extends AppCompatActivity {


    private static final String LOG_TAG = CreateAccount.class.getSimpleName();

    private ActivityCreateAccountBinding binding;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    private ProgressDialog mProgressDialog;

    private String mRealName = "";
    private String mUserName = "";
    private String mEmail = "";
    private String mPassword = "";
    private String mPhoneNumber = "";
    private String mConfirmPassword = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("انتظر من فضلك");
        mProgressDialog.setMessage("جاري إنشاء حساب جديد ...");
        mProgressDialog.setCanceledOnTouchOutside(false);



        setClickingOnCreateAccount();


        setClickingOnLogin();


    }

    private void checkValidateData() {

        // get data
        mRealName = binding.activityCreateAccountRealName.getText().toString().trim();
        mEmail = binding.activityCreateAccountEmail.getText().toString().trim();
        mUserName = binding.activityCreateAccountUserName.getText().toString().trim();
        mPassword = binding.activityCreateAccountPassword.getText().toString();
        mConfirmPassword = binding.activityCreateAccountConfirmPassword.getText().toString();
        mPhoneNumber = binding.activityCreateAccountPhoneNumber.getText().toString().trim();



        if (TextUtils.isEmpty(mRealName)) {

            binding.activityCreateAccountRealName.setError("ادخل الاسم");
            binding.activityCreateAccountRealName.requestFocus();


        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {

            binding.activityCreateAccountEmail.setError("بريد إلكتروني غير صحيح");
            binding.activityCreateAccountEmail.requestFocus();

        } else if (TextUtils.isEmpty(mUserName)) {

            binding.activityCreateAccountUserName.setError("ادخل اسم المستخدم");
            binding.activityCreateAccountUserName.requestFocus();

        } else if (TextUtils.isEmpty(mPassword)) {

            binding.activityCreateAccountPassword.setError("ادخل كلمه مرور");
            binding.activityCreateAccountPassword.requestFocus();

        } else if (mPassword.length() < 6) {

            binding.activityCreateAccountPassword.setError("كلمة المرور لايمكن ان تقل عن 6 احرف");
            binding.activityCreateAccountPassword.requestFocus();

        } else if (!mConfirmPassword.equals(mPassword)) {

            binding.activityCreateAccountConfirmPassword.setError("كلمة المرور غير متطابقة");
            binding.activityCreateAccountConfirmPassword.requestFocus();

        } else if (!Patterns.PHONE.matcher(mPhoneNumber).matches()) {

            binding.activityCreateAccountPhoneNumber.setError("رقم الهاتف المدخل غير صالح");
            binding.activityCreateAccountPhoneNumber.requestFocus();

        } else  {

            hasValidateUserName();

        }



    }


    private void hasValidateUserName() {

        mProgressDialog.show();

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                boolean isValidUserId = true;

                for(DataSnapshot ds : snapshot.getChildren()) {

                    String userName = ds.child("userName").getValue(String.class);

                    if (mUserName.equals(userName)) {

                        isValidUserId = false;

                    }

                }


                if (!isValidUserId) {

                    mProgressDialog.dismiss();
                    binding.activityCreateAccountUserName.setError("اسم المستخدم موجود من قبل");
                    binding.activityCreateAccountUserName.requestFocus();

                } else {

                    firebaseSingUp();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }



    private void firebaseSingUp() {

//        mProgressDialog.show();

        mFirebaseAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        mProgressDialog.dismiss();


                        UserObject userObject = new UserObject(mRealName, mUserName, mEmail, mPhoneNumber);

                        FirebaseDatabase.getInstance().getReference("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(userObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(CreateAccount.this, "تم إنشاء الحساب بنجاح", Toast.LENGTH_SHORT).show();
                                    mProgressDialog.dismiss();
                                    Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {

                                    Toast.makeText(CreateAccount.this, "فشل إنشاء الحساب", Toast.LENGTH_SHORT).show();
                                    mProgressDialog.dismiss();
                                }

                            }
                        });



//                        // get user info
//                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
//                        String email = firebaseUser.getEmail();
//                        Toast.makeText(CreateAccount.this, "Account created \n" + email , Toast.LENGTH_SHORT).show();



                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        mProgressDialog.dismiss();
                        binding.activityCreateAccountEmail.setError("البريد الإلكتروني موجود من قبل");
                        binding.activityCreateAccountEmail.requestFocus();

                    }
                });

    }


    private void setClickingOnLogin() {

        TextView textView = findViewById(R.id.activity_create_account_log_in_button);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }



    private void setClickingOnCreateAccount() {

        TextView textView = findViewById(R.id.activity_create_account_create_account);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidateData();

            }
        });

    }


}