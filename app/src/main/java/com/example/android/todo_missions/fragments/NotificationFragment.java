package com.example.android.todo_missions.fragments;



import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.android.todo_missions.R;
import com.example.android.todo_missions.RandomIcons;
import com.example.android.todo_missions.activities.MainActivity;
import com.example.android.todo_missions.adapters.TodoAdapter;
import com.example.android.todo_missions.adapters.YearCursorAdapter;
import com.example.android.todo_missions.models.TodoObject;

import java.util.ArrayList;

import com.example.android.todo_missions.data.TodoThingsContract.YearEntry;



public class NotificationFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = YearsFragment.class.getSimpleName();

    private Context mContext;
    private View mMainView;

    private LinearLayout mLinearListView;

    private YearCursorAdapter mYearCursorAdapter; // adapter for the semester items.

    private static final int YEAR_LOADER = 0; // number for the semester loader.


    public NotificationFragment(Context context) {
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

        mMainView = inflater.inflate(R.layout.fragment_notification, container, false);


        controlBackPressedButton();

//        mLinearListView = mMainView.findViewById(R.id.fragment_notification_list_view);
//        mYearCursorAdapter = new YearCursorAdapter(mContext, null);
//
//        setupYearListView();
//
//
//        LoaderManager.getInstance(this).initLoader(YEAR_LOADER, null, this);

        // Inflate the layout for this fragment
        return mMainView;
    }



    private void controlBackPressedButton() {

        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {


                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.remove(NotificationFragment.this);

                fragmentTransaction.add(R.id.main_activity_frame_layout, new YearsFragment(mContext));
                fragmentTransaction.commit();

                MainActivity.displayMainToolbar();


            }
        });

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