package com.example.android.todo_missions.fragments;



import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

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
import com.example.android.todo_missions.adapters.TodoAdapter;
import com.example.android.todo_missions.adapters.YearCursorAdapter;
import com.example.android.todo_missions.models.TodoObject;

import java.util.ArrayList;

import com.example.android.todo_missions.data.TodoThingsContract.YearEntry;



public class YearsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = YearsFragment.class.getSimpleName();

    private Context mContext;
    private View mMainView;

    private ListView mYearListView;

    private YearCursorAdapter mYearCursorAdapter; // adapter for the semester items.

    private static final int YEAR_LOADER = 0; // number for the semester loader.


    public YearsFragment(Context context) {
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

        mMainView = inflater.inflate(R.layout.fragment_year, container, false);

        mYearListView = mMainView.findViewById(R.id.fragment_main_list_view);
        mYearCursorAdapter = new YearCursorAdapter(mContext, null);

        setupYearListView();

        setClickingOnFloatingButton();


        LoaderManager.getInstance(this).initLoader(YEAR_LOADER, null, this);

        // Inflate the layout for this fragment
        return mMainView;
    }



    private void setClickingOnFloatingButton() {

        LinearLayout floatingActionButton = mMainView.findViewById(R.id.fragment_year_floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_year);
                dialog.getWindow().setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                TextView yearNameTextView = dialog.findViewById(R.id.dialog_add_year_name);
                TextView yearNumberTextView = dialog.findViewById(R.id.dialog_add_year_number);
                TextView yearDescriptionTextView = dialog.findViewById(R.id.dialog_add_year_description);
                TextView addYearButton = dialog.findViewById(R.id.dialog_add_year_add_year_button);


                addYearButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String yearName = yearNameTextView.getText().toString().trim();
                        String yearNumberString = yearNumberTextView.getText().toString().trim();
                        String yearDescription = yearDescriptionTextView.getText().toString().trim();

                        if (yearNumberString.isEmpty()) {
                            Toast.makeText(mContext, "ادخل رقم هذه السنة اولا", Toast.LENGTH_SHORT).show();
                        } else {
                            int yearNumber = Integer.parseInt(yearNumberString);
                            insertInYearDatabase(yearName, yearNumber, yearDescription);
                            dialog.dismiss();
                        }

                    }
                });

                dialog.show();


            }
        });


    }



    private void insertInYearDatabase(String yearName, int yearNumber, String yearDescription) {

        Log.i(LOG_TAG, "the method that responsable to insert the year to data base triggered");

        if (yearName.isEmpty()) {
            yearName = getString(R.string.placeholder_for_year_name);
        }

        long time = System.currentTimeMillis();
        int numberOfMonths = 12;
        int iconResourceId = RandomIcons.getIconResourceId();
        int iconBackgroundResourceId = RandomIcons.getIconBackgroundResourceId();
        int smallBackgroundResourceId = RandomIcons.getSmallCircleColorResourceId();


        // initialize and setup the ContentValues to contain the data that will be insert inside the database.
        ContentValues values = new ContentValues();
        values.put(YearEntry.COLUMN_YEAR_NAME, yearName);
        values.put(YearEntry.COLUMN_YEAR_NUMBER, yearNumber);
        values.put(YearEntry.COLUMN_YEAR_DESCRIPTION, yearDescription);
        values.put(YearEntry.COLUMN_MONTHS_NUMBER, numberOfMonths);
        values.put(YearEntry.COLUMN_ICON_NUMBER, iconResourceId);
        values.put(YearEntry.COLUMN_BACKGROUND_ICON_NUMBER, iconBackgroundResourceId);
        values.put(YearEntry.COLUMN_BACKGROUND_SMALL_CIRCLE_NUMBER, smallBackgroundResourceId);
        values.put(YearEntry.COLUMN_UNIX, time);




        insertSemester(values);


    }

    private void insertSemester(ContentValues values) {

        // insert the new semester to the database and get the uri for that semester.
        Uri uri = mContext.getContentResolver().insert(YearEntry.CONTENT_URI, values);

        // check if the semester inserted successfully or failed.
        if (uri == null) {
            // show a toast message to the user says that "Error with saving semester".
            Toast.makeText(mContext, R.string.insert_year_inside_database_failed, Toast.LENGTH_SHORT).show();
        } else {
            // show a toast message to the user says that "Semester saved".
            Toast.makeText(mContext, R.string.insert_year_inside_database_successful, Toast.LENGTH_SHORT).show();
        }

    }




    private void setupYearListView() {

        // put the adapter relate to display semester items to the semester ListView.
        mYearListView.setAdapter(mYearCursorAdapter);


        // TODO: set empty view
//        // to hide the empty views from the layout when there is a semester item in the listView.
//        RelativeLayout relativeLayout =  (RelativeLayout) findViewById(R.id.activity_gpa_layout_for_empty_view);
//        mSemesterListView.setEmptyView(relativeLayout);

        // handle clicks on the items in the semester ListView.
        mYearListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                fragmentTransaction.remove(YearsFragment.this);

                fragmentTransaction.add(R.id.main_activity_frame_layout, new MonthsFragment(mContext, id));
                fragmentTransaction.commit();


            }

        });


    }




    private void setListView() {

        ListView listView = mMainView.findViewById(R.id.fragment_main_list_view);

        ArrayList<TodoObject> todoObjects = getFakeTodoObjects();

        TodoAdapter todoAdapter = new TodoAdapter(mContext, todoObjects);

        listView.setAdapter(todoAdapter);



    }

    private ArrayList<TodoObject> getFakeTodoObjects() {

        ArrayList<TodoObject> todoObjectsArrayList = new ArrayList<>();



        for (int i = 0 ; i < 25 ; i++) {

            String name = "سنه التخرج من الجامعه";
            String date = "8-11-2021";
            String dateContent = "عدد الشهور 11";
            int icon = RandomIcons.getIconResourceId();
            int iconBackground = RandomIcons.getIconBackgroundResourceId();
            int smallCircle = RandomIcons.getSmallCircleColorResourceId();

            TodoObject todoObject = new TodoObject(
                    name,
                    date,
                    dateContent,
                    icon,
                    iconBackground,
                    smallCircle);

            todoObjectsArrayList.add(todoObject);
        }

        return todoObjectsArrayList;
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
            case YEAR_LOADER:

                // to get data from the database by specific order :
                // first by student name ASCENDING => (a : z).
                // second by student ID ASCENDING => (1 : 10).
                // third by the semester ASCENDING => (1 : 10).
                String sortOrder = YearEntry.COLUMN_YEAR_NUMBER + " ASC";


                return new CursorLoader(mContext,
                        YearEntry.CONTENT_URI,
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
            case YEAR_LOADER:

                mYearCursorAdapter.swapCursor(cursor);
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

        mYearCursorAdapter.swapCursor(null);

//        mCumulativeCursorAdapter.swapCursor(null);

    }

}