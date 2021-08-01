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



public class SettingsFragment extends Fragment {

    private static final String LOG_TAG = YearsFragment.class.getSimpleName();

    private Context mContext;
    private View mMainView;


    public SettingsFragment(Context context) {
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

        mMainView = inflater.inflate(R.layout.fragment_settings, container, false);



        // Inflate the layout for this fragment
        return mMainView;
    }







}