package com.example.android.todo_missions.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.todo_missions.R;
import com.example.android.todo_missions.fragments.NotificationFragment;
import com.example.android.todo_missions.fragments.SettingsFragment;
import com.example.android.todo_missions.fragments.YearsFragment;
import com.example.android.todo_missions.models.UserObject;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private static Toolbar toolbar;

    private static TextView mToolBarTitle;
    private static ImageView mNotificationIcon;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabaseReference;


    private String mUserId = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mToolBarTitle = findViewById(R.id.activity_main_toolbar_title);
        mNotificationIcon = findViewById(R.id.activity_main_notification_icon);


        drawer = findViewById(R.id.activity_main_drawer_layout);
        navigationView = findViewById(R.id.activity_main_navigation_view);
        toolbar = findViewById(R.id.activity_main_toolbar);


        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.navigation_icon);


        addShadowForBottomLayout();

        if (savedInstanceState == null) {

            YearsFragment yearsFragment = new YearsFragment(this);

            openFragment(yearsFragment);

        }










        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");



        mUserId = mFirebaseUser.getUid();


        mDatabaseReference.child(mUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserObject userObject = snapshot.getValue(UserObject.class);

                if (userObject != null) {

                    String realName = userObject.getUserRealName();
                    String userName = userObject.getUserName();
                    String userEmail = userObject.getUserEmail();
                    String userPhone = userObject.getUserPhone();

                    Log.i(LOG_TAG, "the user info that stored in the firebase : " +
                            "   real name = " + realName +
                            "   user name = " + userName +
                            "   user email = " + userEmail +
                            "   user phone = " + userPhone);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this, "Can't get the data from the server", Toast.LENGTH_SHORT).show();

            }
        });


        setClickingOnHome();

        setClickingOnSettings();

        checkUserAlreadyLoggedIn();

        setClickingOnNotification();

        setClickingOnLogOut();



    }


    private void setClickingOnHome() {

        LinearLayout navigationButton = findViewById(R.id.navigation_drawer_items_home_item);
        LinearLayout bottomLayoutButton = findViewById(R.id.main_activity_navigation_bottom_navigation_home);


        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YearsFragment yearsFragment = new YearsFragment(MainActivity.this);
                openFragment(yearsFragment);
                drawer.closeDrawer(GravityCompat.START, false);

            }
        });


        bottomLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YearsFragment yearsFragment = new YearsFragment(MainActivity.this);
                openFragment(yearsFragment);

            }
        });


    }

    private void setClickingOnSettings() {

        LinearLayout navigationButton = findViewById(R.id.navigation_drawer_items_settings_item);
        LinearLayout bottomLayoutButton = findViewById(R.id.main_activity_navigation_bottom_navigation_settings);


        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SettingsFragment settingsFragment = new SettingsFragment(MainActivity.this);
                openFragment(settingsFragment);
                drawer.closeDrawer(GravityCompat.START, false);

            }
        });


        bottomLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SettingsFragment settingsFragment = new SettingsFragment(MainActivity.this);
                openFragment(settingsFragment);

            }
        });


    }





    private void setClickingOnNotification() {

        ImageView imageView = findViewById(R.id.activity_main_notification_icon);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationFragment notificationFragment = new NotificationFragment(MainActivity.this);

                openFragment(notificationFragment);

                displayNotificationToolbar();

            }
        });


    }

    private void setClickingOnLogOut() {

        LinearLayout linearLayout = findViewById(R.id.navigation_drawer_items_log_out);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFirebaseAuth.signOut();
                Toast.makeText(MainActivity.this, "تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show();
                checkUserAlreadyLoggedIn();


            }
        });


    }

    private void setClickingOnShare() {

        LinearLayout linearLayout = findViewById(R.id.navigation_drawer_items_share_item);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


    }







    private void displayNotificationToolbar() {

        mNotificationIcon.setVisibility(View.GONE);

        toolbar.setNavigationIcon(null);
        mToolBarTitle.setText(R.string.notification_fragment_title);
        mToolBarTitle.setTextColor(getResources().getColor(R.color.colorPrimary));

    }

    public static void displayMainToolbar() {

        mNotificationIcon.setVisibility(View.VISIBLE);

        toolbar.setNavigationIcon(R.drawable.navigation_icon);
        mToolBarTitle.setText(R.string.app_name);
        mToolBarTitle.setTextColor(Resources.getSystem().getColor(android.R.color.black));

    }

    private void checkUserAlreadyLoggedIn() {

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        if(firebaseUser == null) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }

    }

    private void addShadowForBottomLayout() {

        FrameLayout frameLayout = findViewById(R.id.main_activity_frame_layout);


        LinearLayout bottomLayout = findViewById(R.id.main_activity_bottom_layout);
        bottomLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                bottomLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = bottomLayout.getHeight();

                frameLayout.setPadding(0, 0, 0, height);

            }

        });

    }

    private void openFragment(Fragment fragment) {

        displayMainToolbar();

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.main_activity_frame_layout, fragment, null)
                .commit();

    }



































    /**
     * Convert the dp value to the pixel value.
     *
     * @param dp the number we want convert it to px.
     *
     * @return the px value from the dp value inserted to the method.
     */
    private int convertDpToPx(int dp) {

        float pxFloat = ( dp * getResources().getDisplayMetrics().density );

        return Math.round(pxFloat);

    }


    /**
     * Convert the dp value to the pixel value.
     *
     * @param dp the number we want convert it to px.
     *
     * @return the px value from the dp value inserted to the method.
     */
    private int convertToPxDp(int dp) {

        float pxFloat = ( dp * getResources().getDisplayMetrics().density );

        return Math.round(pxFloat);

    }



}