package com.example.android.todo_missions.fragments.tables;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.android.todo_missions.R;
import com.example.android.todo_missions.adapters.MonthsCursorAdapter;
import com.example.android.todo_missions.data.TodoThingsContract;
import com.example.android.todo_missions.fragments.DaysFragment;
import com.example.android.todo_missions.fragments.YearsFragment;

import java.util.ArrayList;


public class TablesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String LOG_TAG = YearsFragment.class.getSimpleName();
    private Context mContext;

    private long mYearIdInDatabase;
    private int mYearNumber;

    private View mMainView;

    private ListView mMonthListView;

    private MonthsCursorAdapter mMonthCursorAdapter; // adapter for the semester items.

    private static final int MONTH_LOADER = 0; // number for the semester loader.


    public TablesFragment(Context context) {
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

        mMainView = inflater.inflate(R.layout.fragment_tables, container, false);

        setLastYearDataFromDatabase();

        setSecondTitleTextView();


        mMonthListView = mMainView.findViewById(R.id.fragment_tables_list_view);
        mMonthCursorAdapter = new MonthsCursorAdapter(mContext, null, mYearNumber);


        setupYearListView();


        LoaderManager.getInstance(this).initLoader(MONTH_LOADER, null, this);



        // Inflate the layout for this fragment
        return mMainView;
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
                fragmentTransaction.remove(TablesFragment.this);

                fragmentTransaction.add(R.id.main_activity_frame_layout, new DaysFragment(mContext, mYearIdInDatabase, id));
                fragmentTransaction.commit();


            }

        });




    }



    private void setLastYearDataFromDatabase() {

        String[] projection = {TodoThingsContract.YearEntry._ID, TodoThingsContract.YearEntry.COLUMN_YEAR_NUMBER};

        String sortOrder = TodoThingsContract.YearEntry.COLUMN_YEAR_NUMBER + " ASC";


        Cursor cursor = mContext.getContentResolver().query(TodoThingsContract.YearEntry.CONTENT_URI, projection, null, null, sortOrder);


        int YearsIdsColumnIndex = cursor.getColumnIndexOrThrow(TodoThingsContract.YearEntry._ID);
        int yearsNumbersColumnIndex = cursor.getColumnIndexOrThrow(TodoThingsContract.YearEntry.COLUMN_YEAR_NUMBER);



        ArrayList<Integer> yearsIdsArrayList = new ArrayList<>();
        ArrayList<Integer> yearsNumbersArrayList = new ArrayList<>();

        while (cursor.moveToNext()) {

            int yearId = cursor.getInt(YearsIdsColumnIndex);
            int yearNumber = cursor.getInt(yearsNumbersColumnIndex);

            yearsIdsArrayList.add(yearId);
            yearsNumbersArrayList.add(yearNumber);

        }


        Log.i(LOG_TAG, "the years ids  = " + yearsIdsArrayList);

        Log.i(LOG_TAG, "the years numbers   = " + yearsNumbersArrayList);


        mYearNumber = yearsNumbersArrayList.get(yearsNumbersArrayList.size()-1);
        mYearIdInDatabase = yearsIdsArrayList.get(yearsNumbersArrayList.size()-1);

    }


    private void setSecondTitleTextView() {

        TextView textView = mMainView.findViewById(R.id.fragment_tables_second_title);
        textView.setText(getString(R.string.tables_second_title, mYearNumber));



    }





    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {


        // handle what each loader do.
        switch (loaderID) {

            // for semester loader.
            case MONTH_LOADER:


                String selection = TodoThingsContract.MonthsEntry.COLUMN_YEAR_ID + "=?";

                String[] selectionArgs = {String.valueOf(mYearIdInDatabase)};

                String sortOrder = TodoThingsContract.MonthsEntry.COLUMN_MONTH_NUMBER + " ASC";


                return new CursorLoader(mContext,
                        TodoThingsContract.MonthsEntry.CONTENT_URI,
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





}
