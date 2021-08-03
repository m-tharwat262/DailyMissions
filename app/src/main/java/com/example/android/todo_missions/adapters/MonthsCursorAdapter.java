package com.example.android.todo_missions.adapters;


import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.android.todo_missions.R;

import com.example.android.todo_missions.data.TodoThingsContract.MonthsEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MonthsCursorAdapter extends CursorAdapter {


    public static final String LOG_TAG = MonthsCursorAdapter.class.getSimpleName(); // class name.

    private Context mContext;
    private int mYearNumber;

    private ArrayList<Boolean> mItemChecked; // array list for store state of each checkbox.

    private static final int DISPLAY_ITEMS = 0; // the layout display items without checkboxes.
    private static final int CALCULATE_TOTAL_GPA = 1; // the layout display items with checkboxes..
    private int mMode = DISPLAY_ITEMS; // the mode that the adapter uses from above.


    public MonthsCursorAdapter(Context context, Cursor c, int yearNumber) {
        super(context, c, 0);

        mContext = context;
        mYearNumber = yearNumber;

    }



    /**
     * Inflate our custom item to use it inside the ListView.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        // use our custom list item inside the adapter.
        return LayoutInflater.from(context).inflate(R.layout.item_fragment, parent, false);

    }



    @Override
    public void bindView(View view, Context context, final Cursor cursor) {


        // determine the views from the inflated layout.
        TextView nameTextView = view.findViewById(R.id.item_fragment_name);
        TextView dateTextView = view.findViewById(R.id.item_fragment_date);
        TextView dateDetailsTextView = view.findViewById(R.id.item_fragment_months_number);
        ImageView circleIconImageView = view.findViewById(R.id.item_fragment_circle_icon);
        LinearLayout BigCircleBackgroundLayout = view.findViewById(R.id.item_fragment_circle_layout);
        TextView smallCircleView = view.findViewById(R.id.item_fragment_small_circle);


        // get the column position inside the table in semester database.
        int yearNumberColumnIndex = cursor.getColumnIndexOrThrow(MonthsEntry.COLUMN_YEAR_ID);
        int monthNameColumnIndex = cursor.getColumnIndexOrThrow(MonthsEntry.COLUMN_MONTH_NAME);
        int monthNumberColumnIndex = cursor.getColumnIndexOrThrow(MonthsEntry.COLUMN_MONTH_NUMBER);
        int monthDescriptionColumnIndex = cursor.getColumnIndexOrThrow(MonthsEntry.COLUMN_MONTH_DESCRIPTION);
        int daysNumberColumnIndex = cursor.getColumnIndexOrThrow(MonthsEntry.COLUMN_DAYS_NUMBER);
        int iconIdColumnIndex = cursor.getColumnIndexOrThrow(MonthsEntry.COLUMN_ICON_NUMBER);
        int iconBackgroundIdColumnIndex = cursor.getColumnIndexOrThrow(MonthsEntry.COLUMN_BACKGROUND_ICON_NUMBER);
        int smallCircleColumnIndex = cursor.getColumnIndexOrThrow(MonthsEntry.COLUMN_BACKGROUND_SMALL_CIRCLE_NUMBER);
        int unixNumberColumnIndex = cursor.getColumnIndexOrThrow(MonthsEntry.COLUMN_UNIX);


        // get the student name from the database and display it in the screen.
        String yearName = cursor.getString(monthNameColumnIndex);
        nameTextView.setText(yearName);

        // get the student name from the database and display it in the screen.
        String monthDate = cursor.getString(monthNumberColumnIndex) + "/" + mYearNumber;
        dateTextView.setText(monthDate);

        // get the student name from the database and display it in the screen.
        int daysNumber = cursor.getInt(daysNumberColumnIndex);
        dateDetailsTextView.setText(mContext.getString(R.string.number_of_days, daysNumber));





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