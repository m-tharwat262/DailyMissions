package com.example.android.todo_missions.fragments;


import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;

import com.example.android.todo_missions.R;
import com.example.android.todo_missions.RandomIcons;
import com.example.android.todo_missions.adapters.DaysCursorAdapter;
import com.example.android.todo_missions.adapters.MonthsCursorAdapter;

import com.example.android.todo_missions.data.TodoThingsContract;
import com.example.android.todo_missions.data.TodoThingsContract.YearEntry;
import com.example.android.todo_missions.data.TodoThingsContract.MonthsEntry;
import com.example.android.todo_missions.data.TodoThingsContract.DaysEntry;


public class DaysFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = YearsFragment.class.getSimpleName();

    private Context mContext;
    private long mYearIdInDatabase;
    private long mMonthIdInDatabase;


    private int mYearNumber;
    private int mMonthNumber;

    private View mMainView;

    private ListView mDaysListView;

    private DaysCursorAdapter mDaysCursorAdapter; // adapter for the semester items.

    private static final int MONTH_LOADER = 0; // number for the semester loader.


    public DaysFragment(Context context, long yearIdInDatabase, long monthIdInDatabase) {
        // Required empty public constructor
        mContext = context;
        mYearIdInDatabase = yearIdInDatabase;
        mMonthIdInDatabase = monthIdInDatabase;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_months, container, false);

        displayMonthData();

        controlBackPressedButton();

        setClickingOnFloatingButton();


        mDaysListView = mMainView.findViewById(R.id.fragment_month_list_view);
        mDaysCursorAdapter = new DaysCursorAdapter(mContext, null, mYearNumber, mMonthNumber);


        setupYearListView();


        LoaderManager.getInstance(this).initLoader(MONTH_LOADER, null, this);


        // Inflate the layout for this fragment
        return mMainView;

    }


    private void displayMonthData() {

        Uri yearUri = new ContentUris().withAppendedId(YearEntry.CONTENT_URI, mYearIdInDatabase);

        Cursor yearCursor = mContext.getContentResolver().query(yearUri, null, null, null, null);
        yearCursor.moveToNext();

        int yearNumberColumnIndexId = yearCursor.getColumnIndexOrThrow(YearEntry.COLUMN_YEAR_NUMBER);
        mYearNumber = yearCursor.getInt(yearNumberColumnIndexId);

        yearCursor.close();

        Uri monthUri = new ContentUris().withAppendedId(MonthsEntry.CONTENT_URI, mMonthIdInDatabase);
        Cursor monthCursor = mContext.getContentResolver().query(monthUri, null, null, null, null);
        monthCursor.moveToNext();

        int monthNumberColumnIndexId = monthCursor.getColumnIndexOrThrow(MonthsEntry.COLUMN_MONTH_NUMBER);
        mMonthNumber = monthCursor.getInt(monthNumberColumnIndexId);

        monthCursor.close();



        TextView yearNameTextView = mMainView.findViewById(R.id.fragment_month_title);
        String title = getString(R.string.month_title, mMonthNumber, MonthsEntry.getMonthName(mMonthNumber));
        yearNameTextView.setText(title);

        TextView yearDetailsTextView = mMainView.findViewById(R.id.fragment_month_details);
        yearDetailsTextView.setText(R.string.month_details);


    }


    private void setClickingOnFloatingButton() {

        LinearLayout floatingActionButton = mMainView.findViewById(R.id.fragment_month_floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_day);
                dialog.getWindow().setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                TextView dayNameTextView = dialog.findViewById(R.id.dialog_add_day_name);
                TextView dayNumberTextView = dialog.findViewById(R.id.dialog_add_day_number);
                TextView addDayButton = dialog.findViewById(R.id.dialog_add_day_add_day_button);


                addDayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String dayName = dayNameTextView.getText().toString().trim();
                        String dayNumber = dayNumberTextView.getText().toString().trim();


                        if (dayNumber.isEmpty()) {
                            Toast.makeText(mContext, "ادخل رقم هذه اليوم اولا", Toast.LENGTH_SHORT).show();
                        } else {
                            insertInDayDatabase(dayName, dayNumber);
                            dialog.dismiss();
                        }


                    }
                });

                dialog.show();


            }
        });


    }


    private void insertInDayDatabase(String dayName, String dayNumber) {

        Log.i(LOG_TAG, "the method that responsible to insert the year to data base triggered");

        if (dayName.isEmpty()) {
            dayName = getString(R.string.placeholder_for_day_name);
        }

        long time = System.currentTimeMillis();


        int iconResourceId = RandomIcons.getIconResourceId();
        int iconBackgroundResourceId = RandomIcons.getIconBackgroundResourceId();
        int smallBackgroundResourceId = RandomIcons.getSmallCircleColorResourceId();


        // initialize and setup the ContentValues to contain the data that will be insert inside the database.
        ContentValues values = new ContentValues();
        values.put(TodoThingsContract.DaysEntry.COLUMN_YEAR_ID, mYearIdInDatabase);
        values.put(TodoThingsContract.DaysEntry.COLUMN_MONTH_ID, mMonthIdInDatabase);
        values.put(DaysEntry.COLUMN_DAY_NAME, dayName);
        values.put(DaysEntry.COLUMN_DAY_NUMBER, dayNumber);
//        values.put(DaysEntry.COLUMN_DAY_DESCRIPTION, dayDescription);
//        values.put(DaysEntry.COLUMN_DAYS_NUMBER, dayMissionsInteger);
        values.put(TodoThingsContract.DaysEntry.COLUMN_ICON_NUMBER, iconResourceId);
        values.put(TodoThingsContract.DaysEntry.COLUMN_BACKGROUND_ICON_NUMBER, iconBackgroundResourceId);
        values.put(DaysEntry.COLUMN_BACKGROUND_SMALL_CIRCLE_NUMBER, smallBackgroundResourceId);
        values.put(TodoThingsContract.DaysEntry.COLUMN_UNIX, time);


        insertDay(values);


    }

    private void insertDay(ContentValues values) {

        // insert the new semester to the database and get the uri for that semester.
        Uri uri = mContext.getContentResolver().insert(TodoThingsContract.DaysEntry.CONTENT_URI, values);

        // check if the semester inserted successfully or failed.
        if (uri == null) {
            // show a toast message to the user says that "Error with saving semester".
            Toast.makeText(mContext, R.string.insert_day_inside_database_failed, Toast.LENGTH_SHORT).show();
        } else {
            // show a toast message to the user says that "Semester saved".
            Toast.makeText(mContext, R.string.insert_day_inside_database_successful, Toast.LENGTH_SHORT).show();
        }

    }




    private void setupYearListView() {


        mDaysListView.setAdapter(mDaysCursorAdapter);


        // TODO: set empty view
//        // to hide the empty views from the layout when there is a semester item in the listView.
//        RelativeLayout relativeLayout =  (RelativeLayout) findViewById(R.id.activity_gpa_layout_for_empty_view);
//        mSemesterListView.setEmptyView(relativeLayout);

        // handle clicks on the items in the semester ListView.
        mDaysListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                // open AddSemesterActivity by intent contain uri refer to the location for that
//                // semester inside the database.
//                // id parameter refer to the unique id for the semester inside database.
//                Intent intent = new Intent(GpaActivity.this, AddSemesterActivity.class);
//                Uri currentSemesterUri = new ContentUris().withAppendedId(SemesterGpaEntry.CONTENT_URI, id);
//                intent.setData(currentSemesterUri);
//                startActivity(intent);


//
//                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
//                fragmentTransaction.hide(MonthsFragment.this);
//
//                fragmentTransaction.add(R.id.main_activity_frame_layout, new MonthsFragment(mContext, YearsFragment.this, id));
//                fragmentTransaction.commit();


            }

        });


    }






    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {


        // handle what each loader do.
        switch (loaderID) {

            // for semester loader.
            case MONTH_LOADER:


                String selection = DaysEntry.COLUMN_YEAR_ID + "=?" + " AND " +  DaysEntry.COLUMN_MONTH_ID + "=?";

                String[] selectionArgs = {String.valueOf(mYearIdInDatabase) , String.valueOf(mMonthIdInDatabase)};

                String sortOrder = DaysEntry.COLUMN_DAY_NUMBER + " ASC";


                return new CursorLoader(mContext,
                        DaysEntry.CONTENT_URI,
                        null, // null because all the table columns will be used.
                        selection,
                        selectionArgs,
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
            case MONTH_LOADER:

                mDaysCursorAdapter.swapCursor(cursor);
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

        mDaysCursorAdapter.swapCursor(null);

//        mCumulativeCursorAdapter.swapCursor(null);

    }






    private void controlBackPressedButton() {

        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {


                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.remove(DaysFragment.this);

                fragmentTransaction.add(R.id.main_activity_frame_layout, new MonthsFragment(mContext, mYearIdInDatabase));
                fragmentTransaction.commit();




            }
        });

    }



}