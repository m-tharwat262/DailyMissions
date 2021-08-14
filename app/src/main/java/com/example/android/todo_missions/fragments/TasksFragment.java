package com.example.android.todo_missions.fragments;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;

import com.example.android.todo_missions.R;
import com.example.android.todo_missions.RandomIcons;
import com.example.android.todo_missions.adapters.TasksCursorAdapter;
import com.example.android.todo_missions.data.TodoThingsContract.TasksEntry;


public class TasksFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String LOG_TAG = YearsFragment.class.getSimpleName();

    private Context mContext;
    private View mMainView;

    private int mTimePickerMinutes = 0;
    private int mTimePickerHours = 0;
    private String mDayOrNight;

    private ListView mTaskListView;

    private TasksCursorAdapter mTasksCursorAdapter; // adapter for the semester items.

    private static final int TASKS_LOADER = 0; // number for the semester loader.


    public TasksFragment(Context context) {
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

        mMainView = inflater.inflate(R.layout.fragment_tasks, container, false);


        setClickingOnFloatingButton();


        mTaskListView = mMainView.findViewById(R.id.fragment_tasks_list_view);
        mTasksCursorAdapter = new TasksCursorAdapter(mContext, null);

        setupTasksListView();

        LoaderManager.getInstance(this).initLoader(TASKS_LOADER, null, this);


        // Inflate the layout for this fragment
        return mMainView;
    }


    private void setClickingOnFloatingButton() {

        LinearLayout floatingActionButton = mMainView.findViewById(R.id.fragment_tasks_floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_task);
                dialog.getWindow().setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                TextView taskNameTextView = dialog.findViewById(R.id.dialog_add_task_name);
                TextView taskTimeValueTextView = dialog.findViewById(R.id.dialog_add_task_time_value);
                TextView taskPickerButton = dialog.findViewById(R.id.dialog_add_task_time_picker);
                TextView addTaskButton = dialog.findViewById(R.id.dialog_add_task_add_task_button);



                taskPickerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Dialog dialog = new Dialog(mContext);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_time_picker);
                        dialog.getWindow().setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                        TimePicker timePickerView = dialog.findViewById(R.id.dialog_time_picker_view);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            timePickerView.setHour(12);
                            timePickerView.setMinute(0);
                        }


                        TextView okButton = dialog.findViewById(R.id.dialog_time_picker_button);
                        okButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                timePickerView.clearFocus();

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    mTimePickerMinutes = timePickerView.getMinute();
                                    mTimePickerHours = timePickerView.getHour();
                                }

                                String formattedTime = getTimeFormatted(mTimePickerHours, mTimePickerMinutes);

                                taskTimeValueTextView.setText(formattedTime);

                                dialog.dismiss();

                            }
                        });

                        dialog.show();

                    }
                });


                addTaskButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String taskName = taskNameTextView.getText().toString().trim();
                        String taskTime = taskTimeValueTextView.getText().toString().trim();

                        if (taskName.isEmpty()) {
                            Toast.makeText(mContext, "ادخل اسم المهمة اولا", Toast.LENGTH_SHORT).show();
                        } else if (taskTime.isEmpty()) {
                            Toast.makeText(mContext, "ادخل وقت المهمة اولا", Toast.LENGTH_SHORT).show();
                        } else {
                            insertInTaskDatabase(taskName, taskTime);
                            dialog.dismiss();
                        }

                    }
                });

                dialog.show();


            }
        });


    }

    private String getTimeFormatted(int mTimePickerHours, int mTimePickerMinutes) {

        String finalHours;
        String finalMinutes;
        String formatTime;

        Log.i(LOG_TAG, "the hours and minutes from the timePicker is : " + mTimePickerHours + "   " + mTimePickerMinutes);

        if (mTimePickerHours == 0) {

            mTimePickerHours += 12;
            formatTime = "ص";

        } else if (mTimePickerHours == 12) {
            formatTime = "م";
        } else if (mTimePickerHours > 12) {
            mTimePickerHours -= 12;
            formatTime = "م";
        } else {
            formatTime = "ص";
        }


        if (mTimePickerHours < 10) {
            finalHours = "0" + mTimePickerHours;
        } else {
            finalHours = "" + mTimePickerHours;
        }

        if (mTimePickerMinutes < 10) {
            finalMinutes = "0" + mTimePickerMinutes;
        } else {
            finalMinutes = "" + mTimePickerMinutes;
        }

        String finalFormattedString = finalHours + ":" + finalMinutes + " " + formatTime +" " ;

        return finalFormattedString;

    }

    private void insertInTaskDatabase(String taskName, String taskTime) {

        Log.i(LOG_TAG, "the method that responsible to insert the year to data base triggered");

        if (taskName.isEmpty()) {
            taskName = getString(R.string.placeholder_for_year_name);
        }

        long time = System.currentTimeMillis();

        int iconResourceId = RandomIcons.getIconResourceId();
        int iconBackgroundResourceId = RandomIcons.getIconBackgroundResourceId();
        int smallBackgroundResourceId = RandomIcons.getSmallCircleColorResourceId();


        // initialize and setup the ContentValues to contain the data that will be insert inside the database.
        ContentValues values = new ContentValues();
        values.put(TasksEntry.COLUMN_TASK_NAME, taskName);
        values.put(TasksEntry.COLUMN_TASK_TIME, taskTime);
        values.put(TasksEntry.COLUMN_ICON_NUMBER, iconResourceId);
        values.put(TasksEntry.COLUMN_BACKGROUND_ICON_NUMBER, iconBackgroundResourceId);
        values.put(TasksEntry.COLUMN_BACKGROUND_SMALL_CIRCLE_NUMBER, smallBackgroundResourceId);
        values.put(TasksEntry.COLUMN_UNIX, time);




        insertTask(values);


    }

    private void insertTask(ContentValues values) {

        // insert the new semester to the database and get the uri for that semester.
        Uri uri = mContext.getContentResolver().insert(TasksEntry.CONTENT_URI, values);

        // check if the semester inserted successfully or failed.
        if (uri == null) {
            // show a toast message to the user says that "Error with saving semester".
            Toast.makeText(mContext, R.string.insert_task_inside_database_failed, Toast.LENGTH_SHORT).show();
        } else {
            // show a toast message to the user says that "Semester saved".
            Toast.makeText(mContext, R.string.insert_task_inside_database_successful, Toast.LENGTH_SHORT).show();
        }

    }





    private void setupTasksListView() {

        // put the adapter relate to display semester items to the semester ListView.
//        mTaskListView.enab
//        mTaskListView.setOnItemClickListener(null);
        mTaskListView.setAdapter(mTasksCursorAdapter);


      // TODO: set empty view



    }





    /**
     * Setup all loader functions in the activity.
     * Specify what exactly we want to get from the database.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {


        // handle what each loader do.
        switch (loaderID) {

            // for semester loader.
            case TASKS_LOADER:

                // to get data from the database by specific order :
                // first by student name ASCENDING => (a : z).
                // second by student ID ASCENDING => (1 : 10).
                // third by the semester ASCENDING => (1 : 10).
                String sortOrder = TasksEntry.COLUMN_UNIX + " ASC";


                return new CursorLoader(mContext,
                        TasksEntry.CONTENT_URI,
                        null, // null because all the table columns will be used.
                        null,
                        null,
                        sortOrder);

//            // for cumulative loader.
//            case CUMULATIVE_LOADER:
//
//                return new CursorLoader(this,
//                        CumulativeGpaEntry.CONTENT_URI,
//                        null,
//                        null,
//                        null,
//                        null);

            default:
                return null;
        }

    }


    /**
     * Add the Cursor that get from the database to the adapter to display the items with the
     * data stored inside that cursor.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        // handle each loader.
        switch (loader.getId()) {

            // for semester loader.
            case TASKS_LOADER:

                mTasksCursorAdapter.swapCursor(cursor);
                break;


//            // for cumulative loader.
//            case CUMULATIVE_LOADER:
//
//                mCumulativeCursorAdapter.swapCursor(cursor);
//
//                // get items in the adapter.
//                int itemNumbers = mCumulativeCursorAdapter.getCount();
//
//                // if the adapter has one or more item a vertical line will appear to separate
//                // between semester items and cumulative items.
//                View verticalLine = findViewById(R.id.activity_gpa_vertical_line_between_list_views);
//                if (itemNumbers == 0) {
//                    verticalLine.setVisibility(View.GONE);
//                } else {
//                    verticalLine.setVisibility(View.VISIBLE);
//                }
//
//                break;

        }


    }


    /**
     * Don't know exactly.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mTasksCursorAdapter.swapCursor(null);

//        mCumulativeCursorAdapter.swapCursor(null);

    }
}
