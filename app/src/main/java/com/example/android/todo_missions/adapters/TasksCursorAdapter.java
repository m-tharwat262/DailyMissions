package com.example.android.todo_missions.adapters;


import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.android.todo_missions.R;
import com.example.android.todo_missions.activities.MainActivity;
import com.example.android.todo_missions.data.TodoThingsContract.TasksEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TasksCursorAdapter extends CursorAdapter {


    public static final String LOG_TAG = TasksCursorAdapter.class.getSimpleName(); // class name.

    private Context mContext;

    private ArrayList<Boolean> mItemChecked; // array list for store state of each checkbox.

    private static final int DISPLAY_ITEMS = 0; // the layout display items without checkboxes.
    private static final int CALCULATE_TOTAL_GPA = 1; // the layout display items with checkboxes..
    private int mMode = DISPLAY_ITEMS; // the mode that the adapter uses from above.


    public TasksCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);

        mContext = context;

    }


    protected static class RowViewHolder {

        public TextView editButton;

    }


    /**
     * Inflate our custom item to use it inside the ListView.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {



        // use our custom list item inside the adapter.
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_tasks, parent, false);


//        RowViewHolder rowView = new RowViewHolder();
//        rowView.editButton = itemView.findViewById(R.id.item_tasks_edit_button);
//
//        itemView.setTag(rowView);
//        rowView.editButton.setTag(rowView);
        itemView.setOnClickListener(null);

        return itemView;

    }



    @Override
    public void bindView(View view, Context context, final Cursor cursor) {


        // determine the views from the inflated layout.
        TextView nameTextView = view.findViewById(R.id.item_tasks_name);
        TextView timeTextView = view.findViewById(R.id.item_tasks_time);
        TextView editButton = view.findViewById(R.id.item_tasks_edit_button);
        ImageView circleIconImageView = view.findViewById(R.id.item_tasks_circle_icon);
        LinearLayout BigCircleBackgroundLayout = view.findViewById(R.id.item_tasks_circle_layout);
        TextView smallCircleView = view.findViewById(R.id.item_tasks_small_circle);


        // get the column position inside the table in semester database.
        int taskUniqueIdColumnIndex = cursor.getColumnIndexOrThrow(TasksEntry._ID);
        int taskNameColumnIndex = cursor.getColumnIndexOrThrow(TasksEntry.COLUMN_TASK_NAME);
        int taskTimeColumnIndex = cursor.getColumnIndexOrThrow(TasksEntry.COLUMN_TASK_TIME);
        int iconIdColumnIndex = cursor.getColumnIndexOrThrow(TasksEntry.COLUMN_ICON_NUMBER);
        int iconBackgroundIdColumnIndex = cursor.getColumnIndexOrThrow(TasksEntry.COLUMN_BACKGROUND_ICON_NUMBER);
        int smallCircleColumnIndex = cursor.getColumnIndexOrThrow(TasksEntry.COLUMN_BACKGROUND_SMALL_CIRCLE_NUMBER);
        int unixNumberColumnIndex = cursor.getColumnIndexOrThrow(TasksEntry.COLUMN_UNIX);


        // get the student name from the database and display it in the screen.
        String taskName = cursor.getString(taskNameColumnIndex);
        nameTextView.setText(taskName);

        // get the student name from the database and display it in the screen.
        String taskTime = cursor.getString(taskTimeColumnIndex);
        timeTextView.setText(taskTime);


        editButton.setTag(cursor.getPosition());

        // get the student name from the database and display it in the screen.
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = cursor.getPosition();


                Integer pos = (Integer) v.getTag();

                cursor.moveToPosition(pos);

                String taskName = cursor.getString(taskNameColumnIndex);
                String taskTime = cursor.getString(taskTimeColumnIndex);
                long taskUniqueId = cursor.getLong(taskUniqueIdColumnIndex);


                Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_task);
                dialog.getWindow().setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                TextView taskTitleTextView = dialog.findViewById(R.id.dialog_add_task_title);
                TextView taskNameTextView = dialog.findViewById(R.id.dialog_add_task_name);
                TextView taskTimeValueTextView = dialog.findViewById(R.id.dialog_add_task_time_value);
                TextView timePickerButton = dialog.findViewById(R.id.dialog_add_task_time_picker);
                TextView addTaskButton = dialog.findViewById(R.id.dialog_add_task_add_task_button);


                taskTitleTextView.setText(R.string.edit_task);
                taskNameTextView.setText(taskName);
                taskTimeValueTextView.setText(taskTime);
                addTaskButton.setText(R.string.edit_button);


                timePickerButton.setOnClickListener(new View.OnClickListener() {
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
                                    int mTimePickerMinutes = timePickerView.getMinute();
                                    int mTimePickerHours = timePickerView.getHour();
                                    taskTimeValueTextView.setText(mTimePickerHours + ":" + mTimePickerMinutes);
                                }

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
                            updateInTasksDatabase(taskName, taskTime, taskUniqueId);
                            dialog.dismiss();
                        }

                    }
                });

                dialog.show();

            }
        });





        int bigCircleIconId = cursor.getInt(iconIdColumnIndex);
        circleIconImageView.setBackgroundResource(bigCircleIconId);

        int bigCircleBackgroundId = cursor.getInt(iconBackgroundIdColumnIndex);
        BigCircleBackgroundLayout.setBackgroundResource(bigCircleBackgroundId);



        GradientDrawable smallCircleBackground = (GradientDrawable) smallCircleView.getBackground();

        // get the letter color.
        int colorResourceId = cursor.getInt(smallCircleColumnIndex);
        int color = ContextCompat.getColor(mContext, colorResourceId);

        // set the background color for the gpaAsLetterTextView to be the letter color above.
        smallCircleBackground.setColor(color);



//        // setup the checkBox.
//        if (mMode == CALCULATE_TOTAL_GPA) {
//
//            // show the checkBox in the item.
//            checkBox.setVisibility(View.VISIBLE);
//
//            // get position by cursor.
//            final int position = cursor.getPosition();
//
//            // to know when the user click on the checkBox.
//            checkBox.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//
//                    // handle clicking on the checkBox and link it to the array (itemChecked).
//                    if (mItemChecked.get(position)) {
//                        // if current checkbox is checked (true) before, make it not checked (false).
//                        mItemChecked.set(position, false);
//                    } else {
//                        // if current checkbox is not checked (false), make it checked (true).
//                        mItemChecked.set(position, true);
//                    }
//
//                }
//            });
//
//            // (important) set the checkbox state for all item base on the arrayList (itemChecked).
//            // without it scrolling in the ListView will change the selected items.
//            checkBox.setChecked(mItemChecked.get(position));
//
//        } else {
//            // hide the checkBox from the item (in Displaying mode).
//            checkBox.setVisibility(View.GONE);
//        }


    }



    private void updateInTasksDatabase(String taskName, String taskTime, long taskUniqueId) {

        Uri taskUri = new ContentUris().withAppendedId(TasksEntry.CONTENT_URI, taskUniqueId);

        // initialize and setup the ContentValues to contain the data that will be insert inside the database.
        ContentValues values = new ContentValues();
        values.put(TasksEntry.COLUMN_TASK_NAME, taskName);
        values.put(TasksEntry.COLUMN_TASK_TIME, taskTime);


        updateTask(values, taskUri);

    }

    private void updateTask(ContentValues values, Uri taskUri) {

        // update the semester and get number of the rows that updated.
        int rows = mContext.getContentResolver().update(taskUri, values, null, null);

        // check if the semester updated successfully or failed.
        if (rows == 0) {
            // show a toast message to the user says that "Error with updating semester".
            Toast.makeText(mContext, R.string.update_task_inside_database_failed, Toast.LENGTH_SHORT).show();
        } else {
            // show a toast message to the user says that "Semester updated".
            Toast.makeText(mContext, R.string.update_task_inside_database_successful, Toast.LENGTH_SHORT).show();
        }

    }












    /**
     * Transfer the unix number to a date look like this format (Apr 21, 2015).
     *
     * @param dateObject contain the unix number.
     *
     * @return the date.
     */
    private String formatDate(Date dateObject) {

        // setup the format shape of the date that should display in the item.
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");

        // transfer the unix to the format above.
        return dateFormat.format(dateObject);
    }


    /**
     * Transfer the unix number to a time look like this format (9:48 PM).
     *
     * @param dateObject contain the unix number.
     *
     * @return the time.
     */
    private String formatTime(Date dateObject) {

        // setup the format shape of the time that should display in the item.
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        // transfer the unix to the format above.
        return timeFormat.format(dateObject);

    }



}