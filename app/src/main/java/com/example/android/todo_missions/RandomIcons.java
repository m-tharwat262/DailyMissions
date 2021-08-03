package com.example.android.todo_missions;


import android.util.Log;

import java.util.Random;


public class RandomIcons {


    private static final String LOG_TAG = RandomIcons.class.getSimpleName();




    public static int getIconBackgroundResourceId() {

        int randomNumber = generateRandomNumber(10);

        int background = getIconBackgroundId(randomNumber);

        return background;

    }


    public static int getIconResourceId() {

        int randomNumber = generateRandomNumber(10);

        int iconResourceId = getIconId(randomNumber);

        return iconResourceId;

    }


    public static int getSmallCircleColorResourceId() {

        int randomNumber = generateRandomNumber(27);

        int colorResourceId = getSmallCircleColorId(randomNumber);

        return colorResourceId;

    }


    private static int generateRandomNumber(int maximumNumber) {

        Random random = new Random();
        int upperbound = maximumNumber;
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
            case 6:
                return R.drawable.random_icon_6;
            case 7:
                return R.drawable.random_icon_7;
            case 8:
                return R.drawable.random_icon_8;
            case 9:
                return R.drawable.random_icon_9;
            case 10:
                return R.drawable.random_icon_10;
            case 11:
                return R.drawable.random_icon_11;
            case 12:
                return R.drawable.random_icon_12;
            case 13:
                return R.drawable.random_icon_13;
            case 14:
                return R.drawable.random_icon_14;
            case 15:
                return R.drawable.random_icon_15;
            case 16:
                return R.drawable.random_icon_16;
            case 17:
                return R.drawable.random_icon_17;
            case 18:
                return R.drawable.random_icon_18;
            case 19:
                return R.drawable.random_icon_19;
            case 20:
                return R.drawable.random_icon_20;
            case 21:
                return R.drawable.random_icon_21;
            case 22:
                return R.drawable.random_icon_22;
            case 23:
                return R.drawable.random_icon_23;
            case 24:
                return R.drawable.random_icon_24;
            case 25:
                return R.drawable.random_icon_25;
            case 26:
                return R.drawable.random_icon_26;
            default:
                return R.drawable.random_icon_27;


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
            case 5:
                return R.drawable.random_circle_icon_5;
            case 6:
                return R.drawable.random_circle_icon_6;
            case 7:
                return R.drawable.random_circle_icon_7;
            case 8:
                return R.drawable.random_circle_icon_8;
            case 9:
                return R.drawable.random_circle_icon_9;
            default:
                return R.drawable.random_circle_icon_10;
        }


    }


}
