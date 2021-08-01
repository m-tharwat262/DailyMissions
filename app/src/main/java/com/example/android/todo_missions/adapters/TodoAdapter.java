package com.example.android.todo_missions.adapters;

import android.content.Context;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import com.example.android.todo_missions.R;
import com.example.android.todo_missions.models.TodoObject;

import java.util.ArrayList;

public class TodoAdapter extends ArrayAdapter<TodoObject> {


    public static final String LOG_TAG = TodoAdapter.class.getSimpleName(); // class name.
    private final Context mContext; // for the activity context that the Adapter work at.


    public TodoAdapter(Context context, ArrayList<TodoObject> todoObjects) {
        super(context, 0, todoObjects);

        mContext = context; // to determine the specific place that the adapter works in.
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;

        if (listItemView == null) {


//            LayoutInflater.from(mContext).inflate(R.layout.downloads_item, null);

            listItemView =  LayoutInflater.from(mContext).inflate(R.layout.item_fragment, parent, false);


        }



        TodoObject todoObject = getItem(position);

        // determine the views from the inflated layout.
        TextView dateTextView = listItemView.findViewById(R.id.item_fragment_date);
        TextView dateDetailsTextView = listItemView.findViewById(R.id.item_fragment_months_number);

        TextView nameTextView = listItemView.findViewById(R.id.item_fragment_name);

        LinearLayout circleBackgroundLayout = listItemView.findViewById(R.id.item_fragment_circle_layout);
        ImageView circleIconImageView = listItemView.findViewById(R.id.item_fragment_circle_icon);

        TextView smallCircleView = listItemView.findViewById(R.id.item_fragment_test_small_circle);



        dateTextView.setText(todoObject.getDate());

        dateDetailsTextView.setText(todoObject.getDateContent());



        nameTextView.setText(todoObject.getName());


        circleBackgroundLayout.setBackgroundResource(todoObject.getBackgroundId());

        circleIconImageView.setBackgroundResource(todoObject.getIconId());


        GradientDrawable circleBackground = (GradientDrawable) smallCircleView.getBackground();

        // get the letter color.
        int colorResourceId = todoObject.getSmallCircleColorId();
        int color = ContextCompat.getColor(mContext, colorResourceId);

        // set the background color for the gpaAsLetterTextView to be the letter color above.
        circleBackground.setColor(color);


//        smallCircleView.setBackgroundResource(todoObject.getSmallCircleColorId());



        return listItemView;
    }




}

