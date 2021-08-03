package com.example.android.todo_missions.fragments;



import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.todo_missions.BuildConfig;
import com.example.android.todo_missions.R;
import com.example.android.todo_missions.activities.LoginActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SettingsFragment extends Fragment {

    private static final String LOG_TAG = YearsFragment.class.getSimpleName();

    private Context mContext;
    private View mMainView;

    private FirebaseAuth mFirebaseAuth;


    public SettingsFragment(Context context) {
        // Required empty public constructor
        mContext = context;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_settings, container, false);


        setClickingOnShare();

        setClickingOnLogOut();

        displayAppVersionName();

        // Inflate the layout for this fragment
        return mMainView;

    }



    private void setClickingOnLogOut() {

        mFirebaseAuth = FirebaseAuth.getInstance();

        LinearLayout linearLayout = mMainView.findViewById(R.id.fragment_settings_log_out);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFirebaseAuth.signOut();
                Toast.makeText(mContext, "تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show();
                checkUserAlreadyLoggedIn();

            }
        });

    }


    private void setClickingOnShare() {

        LinearLayout linearLayout = mMainView.findViewById(R.id.fragment_settings_share);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareApp();

            }
        });

    }





    private void displayAppVersionName() {

        String versionNumber = "unknown";
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            versionNumber = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        TextView textView = mMainView.findViewById(R.id.fragment_settings_app_version);
        textView.setText(getString(R.string.settings_app_version_details, versionNumber));

    }

    private void checkUserAlreadyLoggedIn() {

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        if(firebaseUser == null) {

            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            getActivity().finish();

        }

    }

    public void shareApp() {

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage = "\nJust found the best app to save my tasks every day! Check it out:\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share via..."));
        } catch (Exception e) {
            e.toString();
        }

    }



}