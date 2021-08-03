package com.example.android.todo_missions.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.todo_missions.BuildConfig;
import com.example.android.todo_missions.R;
import com.example.android.todo_missions.fragments.DoneFragment;
import com.example.android.todo_missions.fragments.NotificationFragment;
import com.example.android.todo_missions.fragments.SettingsFragment;
import com.example.android.todo_missions.fragments.TablesFragment;
import com.example.android.todo_missions.fragments.TasksFragment;
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

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private static Toolbar toolbar;

    private static TextView mToolBarTitle;
    private static ImageView mNotificationIcon;

    private LinearLayout mSettingBottomBar;
    private LinearLayout mDoneBottomBar;
    private LinearLayout mHomeBottomBar;
    private LinearLayout mTasksBottomBar;
    private LinearLayout mTablesBottomBar;

    private ImageView mSettingsImage;
    private ImageView mDoneImage;
    private ImageView mHomeImage;
    private ImageView mTasksImage;
    private ImageView mTablesImage;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabaseReference;

    private String mRealName;
    private String mUserName;
    private String mUserEmail;
    private String mUserPhone;


    private String mUserId = "";

    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initializeBottomBarIcons();

        addColorToBottomBarIcons();


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



        if (savedInstanceState == null) {

            YearsFragment yearsFragment = new YearsFragment(this);

            openFragment(yearsFragment);

        }



        displayUserName();

        addShadowForBottomLayout();




        setClickingOnSettings();

        setClickingOnDone();

        setClickingOnHome();

        setClickingOnTasks();

        setClickingOnTables();



        checkUserAlreadyLoggedIn();

        setClickingOnNotification();

        setClickingOnLogOut();

        setClickingOnShare();



    }




    private void setClickingOnSettings() {

        LinearLayout navigationButton = findViewById(R.id.navigation_drawer_items_settings_item);

        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SettingsFragment settingsFragment = new SettingsFragment(MainActivity.this);
                openFragment(settingsFragment);
                drawer.closeDrawer(GravityCompat.START, false);


                addColorToBottomBarIcons();
                mSettingBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_white_background));
                mSettingsImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorSecondary), PorterDuff.Mode.MULTIPLY);

                mHomeBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_navigation_settings));
                mHomeImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), PorterDuff.Mode.MULTIPLY);

            }
        });


        mSettingBottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SettingsFragment settingsFragment = new SettingsFragment(MainActivity.this);
                openFragment(settingsFragment);

                addColorToBottomBarIcons();
                mSettingBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_white_background));
                mSettingsImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorSecondary), PorterDuff.Mode.MULTIPLY);

                mHomeBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_navigation_settings));
                mHomeImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), PorterDuff.Mode.MULTIPLY);

            }
        });


    }

    private void setClickingOnDone() {

        LinearLayout navigationButton = findViewById(R.id.navigation_drawer_items_done_item);

        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DoneFragment doneFragment = new DoneFragment(MainActivity.this);
                openFragment(doneFragment);
                drawer.closeDrawer(GravityCompat.START, false);


                addColorToBottomBarIcons();
                mDoneBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_white_background));
                mDoneImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorSecondary), PorterDuff.Mode.MULTIPLY);

                mHomeBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_navigation_done));
                mHomeImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), PorterDuff.Mode.MULTIPLY);


            }
        });


        mDoneBottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DoneFragment doneFragment = new DoneFragment(MainActivity.this);
                openFragment(doneFragment);

                addColorToBottomBarIcons();
                mDoneBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_white_background));
                mDoneImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorSecondary), PorterDuff.Mode.MULTIPLY);

                mHomeBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_navigation_done));
                mHomeImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), PorterDuff.Mode.MULTIPLY);

            }
        });


    }

    private void setClickingOnHome() {

        LinearLayout navigationButton = findViewById(R.id.navigation_drawer_items_home_item);

        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YearsFragment yearsFragment = new YearsFragment(MainActivity.this);
                openFragment(yearsFragment);
                drawer.closeDrawer(GravityCompat.START, false);


                addColorToBottomBarIcons();
                mHomeBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_white_background));
                mHomeImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorSecondary), PorterDuff.Mode.MULTIPLY);


            }
        });


        mHomeBottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YearsFragment yearsFragment = new YearsFragment(MainActivity.this);
                openFragment(yearsFragment);


                addColorToBottomBarIcons();
                mHomeBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_white_background));
                mHomeImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorSecondary), PorterDuff.Mode.MULTIPLY);


            }
        });


    }

    private void setClickingOnTasks() {

        LinearLayout navigationButton = findViewById(R.id.navigation_drawer_items_tasks_item);

        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TasksFragment tasksFragment = new TasksFragment(MainActivity.this);
                openFragment(tasksFragment);
                drawer.closeDrawer(GravityCompat.START, false);


                addColorToBottomBarIcons();
                mTasksBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_white_background));
                mTasksImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorSecondary), PorterDuff.Mode.MULTIPLY);

                mHomeBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_navigation_tasks));
                mHomeImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), PorterDuff.Mode.MULTIPLY);

            }
        });


        mTasksBottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TasksFragment tasksFragment = new TasksFragment(MainActivity.this);
                openFragment(tasksFragment);

                addColorToBottomBarIcons();
                mTasksBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_white_background));
                mTasksImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorSecondary), PorterDuff.Mode.MULTIPLY);

                mHomeBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_navigation_tasks));
                mHomeImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), PorterDuff.Mode.MULTIPLY);

            }
        });


    }

    private void setClickingOnTables() {

        LinearLayout navigationButton = findViewById(R.id.navigation_drawer_items_table_item);

        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TablesFragment tablesFragment = new TablesFragment(MainActivity.this);
                openFragment(tablesFragment);
                drawer.closeDrawer(GravityCompat.START, false);


                addColorToBottomBarIcons();
                mTablesBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_white_background));
                mTablesImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorSecondary), PorterDuff.Mode.MULTIPLY);

                mHomeBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_navigation_tables));
                mHomeImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), PorterDuff.Mode.MULTIPLY);

            }
        });


        mTablesBottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TablesFragment tablesFragment = new TablesFragment(MainActivity.this);
                openFragment(tablesFragment);

                addColorToBottomBarIcons();
                mTablesBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_white_background));
                mTablesImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorSecondary), PorterDuff.Mode.MULTIPLY);

                mHomeBottomBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle_navigation_tables));
                mHomeImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), PorterDuff.Mode.MULTIPLY);

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

                shareApp();

            }
        });

    }





    private void displayUserName() {

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");


        mUserId = mFirebaseUser.getUid();


        mDatabaseReference.child(mUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserObject userObject = snapshot.getValue(UserObject.class);

                if (userObject != null) {

                    mRealName = userObject.getUserRealName();
                    mUserName = userObject.getUserName();
                    mUserEmail = userObject.getUserEmail();
                    mUserPhone = userObject.getUserPhone();


                    TextView realNameTextView = findViewById(R.id.navigation_drawer_items_user_real_name);
                    realNameTextView.setText(mRealName);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this, "Can't get the data from the server", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void initializeBottomBarIcons() {

        mSettingBottomBar = findViewById(R.id.main_activity_navigation_bottom_navigation_settings);
        mDoneBottomBar = findViewById(R.id.main_activity_navigation_bottom_navigation_done);
        mHomeBottomBar = findViewById(R.id.main_activity_navigation_bottom_navigation_home);
        mTasksBottomBar = findViewById(R.id.main_activity_navigation_bottom_navigation_tasks);
        mTablesBottomBar = findViewById(R.id.main_activity_navigation_bottom_navigation_tables);

        mSettingsImage = findViewById(R.id.activity_main_setting_image);
        mDoneImage = findViewById(R.id.activity_main_done_image);
        mHomeImage = findViewById(R.id.activity_main_home_image);
        mTasksImage = findViewById(R.id.activity_main_tasks_image);
        mTablesImage = findViewById(R.id.activity_main_tables_image);


    }

    private void addColorToBottomBarIcons() {

        mSettingBottomBar.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_navigation_settings));
        mDoneBottomBar.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_navigation_done));
        mHomeBottomBar.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_white_background));
        mTasksBottomBar.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_navigation_tasks));
        mTablesBottomBar.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_navigation_tables));

        mSettingsImage.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.MULTIPLY);
        mDoneImage.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.MULTIPLY);
        mHomeImage.setColorFilter(ContextCompat.getColor(this, R.color.colorSecondary), PorterDuff.Mode.MULTIPLY);
        mTasksImage.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.MULTIPLY);
        mTablesImage.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.MULTIPLY);


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

//    @Override
//    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();
//                return;
//            }
//            this.doubleBackToExitPressedOnce = true;
//            Toast.makeText(this, getResources().getString(R.string.click_back_again_to_exit), Toast.LENGTH_SHORT).show();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    doubleBackToExitPressedOnce = false;
//                }
//            }, 2000);
//        }
//    }



























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