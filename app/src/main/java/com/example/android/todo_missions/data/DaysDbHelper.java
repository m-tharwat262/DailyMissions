package com.example.android.todo_missions.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.todo_missions.data.TodoThingsContract.DaysEntry;


public class DaysDbHelper extends SQLiteOpenHelper {


    public static final String LOG_TAG = DaysDbHelper.class.getSimpleName(); // class name.

    private static final String DATABASE_NAME = "days.db"; // database name.

    private static final int DATABASE_VERSION = 1; // version of the database.


    /**
     * Constructor method for the semester total gpa database to make instance of it and call
     * methods on that instance.
     */
    public DaysDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Create the table in the database for the first time that the app open.
     * That will contain the total gpa fot the semester.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create the statement that will type (like in the cmd) to create the table.
        // determine the data that store in each of the rows.
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + DaysEntry.TABLE_NAME + " ("

                + DaysEntry._ID                                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DaysEntry.COLUMN_YEAR_ID                         + " INTEGER NOT NULL, "
                + DaysEntry.COLUMN_MONTH_ID                        + " INTEGER NOT NULL, "
                + DaysEntry.COLUMN_DAY_NAME                        + " TEXT NOT NULL, "
                + DaysEntry.COLUMN_DAY_NUMBER                      + " INTEGER NOT NULL, "
                + DaysEntry.COLUMN_DAY_DESCRIPTION                 + " TEXT, "
                + DaysEntry.COLUMN_MISSIONS_NUMBER                 + " INTEGER, "
                + DaysEntry.COLUMN_ICON_NUMBER                     + " INTEGER NOT NULL, "
                + DaysEntry.COLUMN_BACKGROUND_ICON_NUMBER          + " INTEGER NOT NULL, "
                + DaysEntry.COLUMN_BACKGROUND_SMALL_CIRCLE_NUMBER  + " INTEGER NOT NULL, "
                + DaysEntry.COLUMN_UNIX                            + " INTEGER NOT NULL DEFAULT 0);";


        // Result of the string above look like this :
        // String SQL_CREATE_PETS_TABLE = "CREATE TABLE semester_gpa (
        // _id INTEGER PRIMARY KEY AUTOINCREMENT ,
        // name TEXT NOT NULL ,
        // id INTEGER NOT NULL ,
        // semester INTEGER NOT NULL ,
        // degrees BLOB NOT NULL,
        // unix INTEGER NOT NULL DEFAULT 0);"


        // create the database if it is not created.
        // create the table inside the database.
        db.execSQL(SQL_CREATE_PETS_TABLE);

    }


    /**
     * (Not Necessary) For upgrade or update the database in the future like add a new column.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to be done here.
    }


}