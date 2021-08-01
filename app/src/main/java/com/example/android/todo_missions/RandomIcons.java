package com.example.android.todo_missions;


import android.util.Log;

import java.util.Random;


public class RandomIcons {


    private static final String LOG_TAG = RandomIcons.class.getSimpleName();




    public static int getIconBackgroundResourceId() {

        int randomNumber = generateRandomNumber();

        int background = getIconBackgroundId(randomNumber);

        return background;

    }


    public static int getIconResourceId() {

        int randomNumber = generateRandomNumber();

        int iconResourceId = getIconId(randomNumber);

        return iconResourceId;

    }


    public static int getSmallCircleColorResourceId() {

        int randomNumber = generateRandomNumber();

        int colorResourceId = getSmallCircleColorId(randomNumber);

        return colorResourceId;

    }


    private static int generateRandomNumber() {

        Random random = new Random();
        int upperbound = 5;
        int randomNumber = random.nextInt(upperbound);

        Log.i(LOG_TAG, "the random number that comes from the RandomIcons is  :  " + (randomNumber += 1) );
        return (randomNumber += 1);


    }

    private static int getIconId(int randomNumber) {

        switch (randomNumber) {
            case 1:
                return R.drawable.random_icon_1;
            case 2:
                return R.drawable.random_icon_2;
            case 3:
                return R.drawable.random_icon_3;
            case 4:
                return R.drawable.random_icon_4;
            case 5:
                return R.drawable.random_icon_5;
            default:
                return R.drawable.random_icon_1;


        }


    }



    private static int getSmallCircleColorId(int randomNumber) {

        switch (randomNumber) {
            case 1:
                return R.color.random_1;
            case 2:
                return R.color.random_2;
            case 3:
                return R.color.random_3;
            case 4:
                return R.color.random_4;
            case 5:
                return R.color.random_5;
            case 6:
                return R.color.random_6;
            case 7:
                return R.color.random_7;
            case 8:
                return R.color.random_8;
            case 9:
                return R.color.random_9;
            default:
                return R.color.random_10;
        }


    }


    private static int getIconBackgroundId(int randomNumber) {

        switch (randomNumber) {
            case 1:
                return R.drawable.random_circle_icon_1;
            case 2:
                return R.drawable.random_circle_icon_2;
            case 3:
                return R.drawable.random_circle_icon_3;
            case 4:
                return R.drawable.random_circle_icon_4;
            default:
                return R.drawable.random_circle_icon_5;
        }


    }


}
