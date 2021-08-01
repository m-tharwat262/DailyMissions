package com.example.android.todo_missions.fragments;


import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;

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

import com.example.android.todo_missions.R;
import com.example.android.todo_missions.RandomIcons;
import com.example.android.todo_missions.adapters.MonthsCursorAdapter;

import com.example.android.todo_missions.data.TodoThingsContract.YearEntry;
import com.example.android.todo_missions.data.TodoThingsContract.MonthsEntry;


public class MonthsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = YearsFragment.class.getSimpleName();

    private Context mContext;
    private long mYearIdInDatabase;

    private String mYearName;
    private int mYearNumber;

    private View mMainView;

    private ListView mMonthListView;

    private MonthsCursorAdapter mMonthCursorAdapter; // adapter for the semester items.

    private static final int MONTH_LOADER = 0; // number for the semester loader.


    public MonthsFragment(Context context, long yearIdInDatabase) {
        // Required empty public constructor
        mContext = context;
        mYearIdInDatabase = yearIdInDatabase;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_months, container, false);

        displayYearData();

        controlBackPressedButton();

        setClickingOnFloatingButton();


        mMonthListView = mMainView.findViewById(R.id.fragment_month_list_view);
        mMonthCursorAdapter = new MonthsCursorAdapter(mContext, null, mYearNumber);


        setupYearListView();


        LoaderManager.getInstance(this).initLoader(MONTH_LOADER, null, this);


        // Inflate the layout for this fragment
        return mMainView;

    }


    private void displayYearData() {

        Uri yearUri = new ContentUris().withAppendedId(YearEntry.CONTENT_URI, mYearIdInDatabase);

        Cursor cursor = mContext.getContentResolver().query(yearUri, null, null, null, null);
        cursor.moveToNext();

        int yearNameColumnIndexId = cursor.getColumnIndexOrThrow(YearEntry.COLUMN_YEAR_NAME);
        int yearNumberColumnIndexId = cursor.getColumnIndexOrThrow(YearEntry.COLUMN_YEAR_NUMBER);

        mYearName = cursor.getString(yearNameColumnIndexId);
        mYearNumber = cursor.getInt(yearNumberColumnIndexId);

        cursor.close();



        TextView titleTextView = mMainView.findViewById(R.id.fragment_month_title);
        titleTextView.setText(mYearName);

        TextView yearDetailsTextView = mMainView.findViewById(R.id.fragment_month_details);
        yearDetailsTextView.setText(R.string.year_details);



    }


    private void setClickingOnFloatingButton() {

        LinearLayout floatingActionButton = mMainView.findViewById(R.id.fragment_month_floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_month);
                dialog.getWindow().setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                TextView monthNameTextView = dialog.findViewById(R.id.dialog_add_month_name);
                TextView monthDaysTextView = dialog.findViewById(R.id.dialog_add_month_days);
                TextView monthNumberTextView = dialog.findViewById(R.id.dialog_add_month_number);
                TextView monthDescriptionTextView = dialog.findViewById(R.id.dialog_add_month_description);
                TextView addMonthButton = dialog.findViewById(R.id.dialog_add_month_add_month_button);


                addMonthButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String monthName = monthNameTextView.getText().toString().trim();
                        String monthDays = monthDaysTextView.getText().toString().trim();
                        String monthNumber = monthNumberTextView.getText().toString().trim();
                        String monthDescription = monthDescriptionTextView.getText().toString().trim();

                        if (monthNumber.isEmpty()) {
                            Toast.makeText(mContext, "ادخل رقم هذه الشهر اولا", Toast.LENGTH_SHORT).show();
                        } else {
                            insertInMonthDatabase(monthName, monthNumber, monthDescription, monthDays);
                            dialog.dismiss();
                        }

                    }
                });

                dialog.show();


            }
        });


    }


    private void insertInMonthDatabase(String monthName, String monthNumber, String monthDescription, String monthDays) {

        Log.i(LOG_TAG, "the method that responsible to insert the year to data base triggered");

        if (monthName.isEmpty()) {
            monthName = getString(R.string.placeholder_for_month_name);
        }

        long time = System.currentTimeMillis();
        int monthNumberInteger = Integer.parseInt(monthNumber);
        int monthDaysInteger;
        if (!monthDays.isEmpty()) {
             monthDaysInteger = Integer.parseInt(monthDays);
        } else {
            monthDaysInteger = 30;
            // TODO: get correct days in the month used variable monthNumberInteger
        }

        int iconResourceId = RandomIcons.getIconResourceId();
        int iconBackgroundResourceId = RandomIcons.getIconBackgroundResourceId();
        int smallBackgroundResourceId = RandomIcons.getSmallCircleColorResourceId();


        // initialize and setup the ContentValues to contain the data that will be insert inside the database.
        ContentValues values = new ContentValues();
        values.put(MonthsEntry.COLUMN_YEAR_ID, mYearIdInDatabase);
        values.put(MonthsEntry.COLUMN_MONTH_NAME, monthName);
        values.put(MonthsEntry.COLUMN_MONTH_NUMBER, monthNumberInteger);
        values.put(MonthsEntry.COLUMN_MONTH_DESCRIPTION, monthDescription);
        values.put(MonthsEntry.COLUMN_DAYS_NUMBER, monthDaysInteger);
        values.put(MonthsEntry.COLUMN_ICON_NUMBER, iconResourceId);
        values.put(MonthsEntry.COLUMN_BACKGROUND_ICON_NUMBER, iconBackgroundResourceId);
        values.put(MonthsEntry.COLUMN_BACKGROUND_SMALL_CIRCLE_NUMBER, smallBackgroundResourceId);
        values.put(MonthsEntry.COLUMN_UNIX, time);


        insertMonth(values);


    }

    private void insertMonth(ContentValues values) {

        // insert the new semester to the database and get the uri for that semester.
        Uri uri = mContext.getContentResolver().insert(MonthsEntry.CONTENT_URI, values);

        // check if the semester inserted successfully or failed.
        if (uri == null) {
            // show a toast message to the user says that "Error with saving semester".
            Toast.makeText(mContext, R.string.insert_month_inside_database_failed, Toast.LENGTH_SHORT).show();
        } else {
            // show a toast message to the user says that "Semester saved".
            Toast.makeText(mContext, R.string.insert_month_inside_database_successful, Toast.LENGTH_SHORT).show();
        }

    }




    private void setupYearListView() {


        mMonthListView.setAdapter(mMonthCursorAdapter);


        // TODO: set empty view
//        // to hide the empty views from the layout when there is a semester item in the listView.
//        RelativeLayout relativeLayout =  (RelativeLayout) findViewById(R.id.activity_gpa_layout_for_empty_view);
//        mSemesterListView.setEmptyView(relativeLayout);

        // handle clicks on the items in the semester ListView.
        mMonthListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                // open AddSemesterActivity by intent contain uri refer to the location for that
//                // semester inside the database.
//                // id parameter refer to the unique id for the semester inside database.
//                Intent intent = new Intent(GpaActivity.this, AddSemesterActivity.class);
//                Uri currentSemesterUri = new ContentUris().withAppendedId(SemesterGpaEntry.CONTENT_URI, id);
//                intent.setData(currentSemesterUri);
//                startActivity(intent);



                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.remove(MonthsFragment.this);

                fragmentTransaction.add(R.id.main_activity_frame_layout, new DaysFragment(mContext, mYearIdInDatabase, id));
                fragmentTransaction.commit();


            }

        });


    }






    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {


        // handle what each loader do.
        switch (loaderID) {

            // for semester loader.
            case MONTH_LOADER:


                String selection = MonthsEntry.COLUMN_YEAR_ID + "=?";

                String[] selectionArgs = {String.valueOf(mYearIdInDatabase)};

                String sortOrder = MonthsEntry.COLUMN_MONTH_NUMBER + " ASC";


                return new CursorLoader(mContext,
                        MonthsEntry.CONTENT_URI,
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

                mMonthCursorAdapter.swapCursor(cursor);
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

        mMonthCursorAdapter.swapCursor(null);

//        mCumulativeCursorAdapter.swapCursor(null);

    }






    private void controlBackPressedButton() {

        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {


                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.remove(MonthsFragment.this);

                fragmentTransaction.add(R.id.main_activity_frame_layout, new YearsFragment(mContext));
                fragmentTransaction.commit();


            }
        });

    }



}