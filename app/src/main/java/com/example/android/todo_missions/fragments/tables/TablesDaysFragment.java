package com.example.android.todo_missions.fragments.tables;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.android.todo_missions.R;
import com.example.android.todo_missions.adapters.MonthsCursorAdapter;
import com.example.android.todo_missions.data.TodoThingsContract;

public class TablesDaysFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = TablesDaysFragment.class.getSimpleName();
    private Context mContext;

    private long mYearIdInDatabase;
    private int mYearNumber;

    private View mMainView;

    private ListView mMonthListView;

    private MonthsCursorAdapter mMonthCursorAdapter; // adapter for the semester items.

    private static final int MONTH_LOADER = 0; // number for the semester loader.


    public TablesDaysFragment(Context context) {
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

        mMainView = inflater.inflate(R.layout.fragment_tables_days, container, false);

        setLastYearDataFromDatabase();

        setSecondTitleTextView();


        mMonthListView = mMainView.findViewById(R.id.fragment_tables_list_view);
        mMonthCursorAdapter = new MonthsCursorAdapter(mContext, null, mYearNumber);


        setupYearListView();


        LoaderManager.getInstance(this).initLoader(MONTH_LOADER, null, this);



        // Inflate the layout for this fragment
        return mMainView;
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
