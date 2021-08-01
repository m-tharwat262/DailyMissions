package com.example.android.todo_missions.data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class TodoThingsContract {


    public static final String CONTENT_AUTHORITY = "com.example.android.todo_missions"; // the content authority.

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY); // the uri for the database.

    public static final String PATH_YEAR = "year";
    public static final String PATH_MONTHS = "months";
    public static final String PATH_DAYS = "days";
    public static final String PATH_MISSIONS = "missions";





    public static final class YearEntry implements BaseColumns {


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_YEAR);

        public final static String TABLE_NAME = "year"; // table name.


        // head names for the table rows.
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_YEAR_NAME = "name";
        public final static String COLUMN_YEAR_NUMBER = "year_number";
        public final static String COLUMN_YEAR_DESCRIPTION = "description";
        public final static String COLUMN_MONTHS_NUMBER = "months";
        public final static String COLUMN_ICON_NUMBER= "icon";
        public final static String COLUMN_BACKGROUND_ICON_NUMBER = "icon_background";
        public final static String COLUMN_BACKGROUND_SMALL_CIRCLE_NUMBER = "small_circle";
        public final static String COLUMN_UNIX = "unix";







        // for outside apps that may be allow to access to the database in the app.
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_YEAR;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_YEAR;


    }



    public static final class MonthsEntry implements BaseColumns {


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MONTHS);

        public final static String TABLE_NAME = "months"; // table name.


        // head names for the table rows.
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_YEAR_ID = "year_id";
        public final static String COLUMN_MONTH_NAME = "month_name";
        public final static String COLUMN_MONTH_NUMBER = "month_number";
        public final static String COLUMN_MONTH_DESCRIPTION = "month_description";
        public final static String COLUMN_DAYS_NUMBER = "days";
        public final static String COLUMN_ICON_NUMBER= "icon";
        public final static String COLUMN_BACKGROUND_ICON_NUMBER = "icon_background";
        public final static String COLUMN_BACKGROUND_SMALL_CIRCLE_NUMBER = "small_circle";
        public final static String COLUMN_UNIX = "unix";


        public static final String MONTH_ONE = "يناير";
        public static final String MONTH_TWO = "فبراير";
        public static final String MONTH_THREE = "مارس";
        public static final String MONTH_FOUR = "أبريل";
        public static final String MONTH_FIVE = "مايو";
        public static final String MONTH_SIX = "يونيو";
        public static final String MONTH_SEVEN = "يوليو";
        public static final String MONTH_EIGHT = "أغسطس";
        public static final String MONTH_NINE = "سبتمبر";
        public static final String MONTH_TEN = "أكتوبر";
        public static final String MONTH_ELEVEN = "نوفمبر";
        public static final String MONTH_TWELVE = "ديسمبر";



        public static String getMonthName(int monthNumber) {

            switch (monthNumber) {

                case 1:
                    return MONTH_ONE;
                case 2:
                    return MONTH_TWO;
                case 3:
                    return MONTH_THREE;
                case 4:
                    return MONTH_FOUR;
                case 5:
                    return MONTH_FIVE;
                case 6:
                    return MONTH_SIX;
                case 7:
                    return MONTH_SEVEN;
                case 8:
                    return MONTH_EIGHT;
                case 9:
                    return MONTH_NINE;
                case 10:
                    return MONTH_TEN;
                case 11:
                    return MONTH_ELEVEN;
                case 12:
                    return MONTH_TWELVE;
                default:
                    return "unknown";

            }

        }




    }







    public static final class DaysEntry implements BaseColumns {


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DAYS);

        public final static String TABLE_NAME = "days"; // table name.


        // head names for the table rows.
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_YEAR_ID = "year_id";
        public final static String COLUMN_MONTH_ID = "month_id";
        public final static String COLUMN_DAY_NAME = "day_name";
        public final static String COLUMN_DAY_NUMBER = "day_number";
        public final static String COLUMN_DAY_DESCRIPTION = "day_description";
        public final static String COLUMN_MISSIONS_NUMBER = "missions";
        public final static String COLUMN_ICON_NUMBER= "icon";
        public final static String COLUMN_BACKGROUND_ICON_NUMBER = "icon_background";
        public final static String COLUMN_BACKGROUND_SMALL_CIRCLE_NUMBER = "small_circle";
        public final static String COLUMN_UNIX = "unix";



    }



}
