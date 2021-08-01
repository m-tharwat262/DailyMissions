package com.example.android.todo_missions.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.todo_missions.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {


    private ProgressDialog mProgressDialog;

    private FirebaseAuth mFirebaseAuth;

    private String mEmail = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        mFirebaseAuth = FirebaseAuth.getInstance();


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("من فضلك انتظر");
        mProgressDialog.setMessage("جاري إعادة تعيين كلمة المرور ...");
        mProgressDialog.setCanceledOnTouchOutside(false);


        setClickingOnCreateAccount();


        setClickingOnChangePassword();
    }




    private void setClickingOnCreateAccount() {

        TextView textView = findViewById(R.id.activity_forget_password_create_account);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ForgetPassword.this, CreateAccount.class);
                startActivity(intent);
                finish();


            }
        });

    }


    private void setClickingOnChangePassword() {

        TextView textView = findViewById(R.id.activity_forget_password_change_password_button);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetPassword();

            }
        });

    }

    private void resetPassword() {

        EditText emailEditText = findViewById(R.id.activity_forget_password_edit_text);

        mEmail = emailEditText.getText().toString().trim();

        if (mEmail.isEmpty()) {

            emailEditText.setError("ادخل البريد الإلكنروني");
            emailEditText.requestFocus();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {

            emailEditText.setError("بريد إلكتروني غير صالح");
            emailEditText.requestFocus();

        } else {

            mProgressDialog.show();
            mFirebaseAuth.sendPasswordResetEmail(mEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        mProgressDialog.dismiss();
                        showResetDialog();

                    } else {
                        mProgressDialog.dismiss();
                        Toast.makeText(ForgetPassword.this, "فشل إعادة تعيين كلمة المرور", Toast.LENGTH_SHORT).show();
                        // TODO: set a dialog instead of the toast.

                    }


                }
            });

        }


    }

    private void showResetDialog() {


        Dialog dialog = new Dialog(ForgetPassword.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_send_change_password);
        dialog.getWindow().setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView okButton = dialog.findViewById(R.id.dialog_send_change_password_oK_button);


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                finish();

            }
        });

        dialog.show();

    }


}