package com.example.android.todo_missions.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.android.todo_missions.data.TodoThingsContract.YearEntry;
import com.example.android.todo_missions.data.TodoThingsContract.MonthsEntry;
import com.example.android.todo_missions.data.TodoThingsContract.DaysEntry;
import com.example.android.todo_missions.data.TodoThingsContract.TasksEntry;



public class TodoProvider extends ContentProvider {


    public static final String LOG_TAG = TodoProvider.class.getSimpleName(); // class name.


    private YearsDbHelper mYearsDbHelper; // get instance of the semester database.
    private MonthsDbHelper mMonthsDbHelper; // // get instance of the cumulative database.
    private DaysDbHelper mDaysDbHelper; // // get instance of the cumulative database.
    private TasksDbHelper mTasksDbHelper; // // get instance of the cumulative database.


    private static final int YEAR = 100; // URI pattern to all the semester_gpa table.
    private static final int YEAR_ID = 101; // URI pattern to single column in the semester_gpa table.

    private static final int MONTHS = 200; // URI pattern to all the cumulative_gpa table.
    private static final int MONTHS_ID = 201; // URI pattern to single column in the cumulative_gpa table.

    private static final int DAYS = 300; // URI pattern to all the cumulative_gpa table.
    private static final int DAYS_ID = 301; // URI pattern to single column in the cumulative_gpa table.

    private static final int TASKS = 400; // URI pattern to all the cumulative_gpa table.
    private static final int TASKS_ID = 401; // URI pattern to single column in the cumulative_gpa table.


    // initialize the UriMatcher.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // add the table paths and his pattern number in the uri matcher.
    static {
        sUriMatcher.addURI(TodoThingsContract.CONTENT_AUTHORITY, TodoThingsContract.PATH_YEAR, YEAR);
        sUriMatcher.addURI(TodoThingsContract.CONTENT_AUTHORITY, TodoThingsContract.PATH_YEAR + "/#", YEAR_ID);

        sUriMatcher.addURI(TodoThingsContract.CONTENT_AUTHORITY, TodoThingsContract.PATH_MONTHS, MONTHS);
        sUriMatcher.addURI(TodoThingsContract.CONTENT_AUTHORITY, TodoThingsContract.PATH_MONTHS + "/#", MONTHS_ID);

        sUriMatcher.addURI(TodoThingsContract.CONTENT_AUTHORITY, TodoThingsContract.PATH_DAYS, DAYS);
        sUriMatcher.addURI(TodoThingsContract.CONTENT_AUTHORITY, TodoThingsContract.PATH_DAYS + "/#", DAYS_ID);

        sUriMatcher.addURI(TodoThingsContract.CONTENT_AUTHORITY, TodoThingsContract.PATH_TASKS, TASKS);
        sUriMatcher.addURI(TodoThingsContract.CONTENT_AUTHORITY, TodoThingsContract.PATH_TASKS + "/#", TASKS_ID);

    }


    /**
     * initialize databases here.
     */
    @Override
    public boolean onCreate() {

        // initialize databases (semester - cumulative).
        mYearsDbHelper = new YearsDbHelper(getContext());
        mMonthsDbHelper = new MonthsDbHelper(getContext());
        mDaysDbHelper = new DaysDbHelper(getContext());
        mTasksDbHelper = new TasksDbHelper(getContext());

        return false;

    }



    /**
     * Read from the database (all the database or a single row).
     *
     * @param uri uri for the path in the database.
     * @param projection specific column in the database.
     * @param selection specific row in the database.
     * @param selectionArgs the value for the selection parameter above.
     * @param sortOrder the order that the results must return with.
     *
     * @return Cursor contain the results after the reading from database finish.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // access to databases (semester - cumulative) to read the data from it.
        SQLiteDatabase yearDatabase = mYearsDbHelper.getReadableDatabase();
        SQLiteDatabase monthsDatabase = mMonthsDbHelper.getReadableDatabase();
        SQLiteDatabase daysDatabase = mDaysDbHelper.getReadableDatabase();
        SQLiteDatabase tasksDatabase = mTasksDbHelper.getReadableDatabase();

        // for the return result from the database.
        Cursor cursor;

        // get the pattern that the uri equal.
        int match = sUriMatcher.match(uri);

        // setup functions for every uri pattern.
        switch (match) {

            // access to semester database with no id.
            case YEAR:

                // setup the input inside the query function.
                cursor = yearDatabase.query(YearEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);

                break;

            // access to semester database with id.
            case YEAR_ID:

                // setup the input inside the query function (after the WHERE word).
                selection = YearEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = yearDatabase.query(YearEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;


           //access to cumulative database with no id.
            case MONTHS:

                // setup the input inside the query function.
                cursor = monthsDatabase.query(MonthsEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);

                break;

            // access to cumulative database with id.
            case MONTHS_ID:

                // setup the input inside the query function (after the WHERE word).
                selection = MonthsEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = monthsDatabase.query(MonthsEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;


            //access to cumulative database with no id.
            case DAYS:

                // setup the input inside the query function.
                cursor = daysDatabase.query(DaysEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);

                break;

            // access to cumulative database with id.
            case DAYS_ID:

                // setup the input inside the query function (after the WHERE word).
                selection = DaysEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = daysDatabase.query(DaysEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;



            //access to cumulative database with no id.
            case TASKS:

                // setup the input inside the query function.
                cursor = tasksDatabase.query(TasksEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);

                break;

            // access to cumulative database with id.
            case TASKS_ID:

                // setup the input inside the query function (after the WHERE word).
                selection = TasksEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = tasksDatabase.query(TasksEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;

            // to handle if the is no match for the uri inserted with the uri patterns.
            default:

                throw new IllegalArgumentException("Cannot query unknown URI " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // return Cursor contain the data from the database.
        return cursor;
    }



    /**
     * Write inside the database to add a new data.
     *
     * @param uri uri for the database path.
     * @param values contain the columns keys and its values.
     *
     * @return uri refer to the row place inside the database.
     */
    @Override
    public Uri insert( Uri uri, ContentValues values) {

        // get the pattern that the uri equal.
        final int match = sUriMatcher.match(uri);

        // setup functions for every uri pattern (no single uris by id).
        switch (match) {

            // semester_gpa table inside semester database.
            case YEAR:

                // execute helper method to insert the data inside the semester database.
                // return the uri that refer to the row place inside the semester database.
                return insertYear(uri, values);


            // cumulative_gpa table inside cumulative database.
            case MONTHS:

                // execute helper method to insert the data inside the cumulative database.
                // return the uri that refer to the row place inside the cumulative database.
                return insertMonth(uri, values);


            // cumulative_gpa table inside cumulative database.
            case DAYS:

                // execute helper method to insert the data inside the cumulative database.
                // return the uri that refer to the row place inside the cumulative database.
                return insertDay(uri, values);


            // cumulative_gpa table inside cumulative database.
            case TASKS:

                // execute helper method to insert the data inside the cumulative database.
                // return the uri that refer to the row place inside the cumulative database.
                return insertTask(uri, values);


            // to handle if the is no match for the uri inserted with the uri patterns.
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }


    /**
     * (Helper Method to Insert Data Inside semester Database).
     * Insert data inside the semester_gpa table in semester database.
     * Check the validation for the data inserted from the user.
     *
     * @param uri uri for the path inside the database.
     * @param values contain the columns keys and its values.
     *
     * @return uri refer to the row place inside the database.
     */
    private Uri insertYear(Uri uri, ContentValues values) {


        // access to the database to write inside it.
        SQLiteDatabase semesterDatabase = mYearsDbHelper.getWritableDatabase();

        // insert the data inside the database and get the row number that the data inserted at.
        long id = semesterDatabase.insert(YearEntry.TABLE_NAME, null, values);

        // if the insertion failed show a Log(e) for that.
        // make the uri return value equal null in case the insertion failed.
        if (id == -1) {

            Log.e(LOG_TAG, "Failed to insert row in year database for " + uri);

            return null;
        }

        // Notify that there is changing happened in the database to sync changes to the network or activities.
        getContext().getContentResolver().notifyChange(uri, null);

        // return the uri for the place that the data inserted inside database.
        return ContentUris.withAppendedId(uri, id);

    }


    /**
     * (Helper Method to Insert Data Inside cumulative Database).
     * Insert data inside the cumulative_gpa in cumulative database.
     * Check the validation for the data inserted from the user.
     *
     * @param uri uri for the path inside the database.
     * @param values contain the columns keys and its values.
     *
     * @return uri refer to the row place inside the database.
     */
    private Uri insertMonth(Uri uri, ContentValues values) {


        // access to the database to write inside it.
        SQLiteDatabase monthDatabase = mMonthsDbHelper.getWritableDatabase();

        // insert the data inside the database and get the row number that the data inserted at.
        long id = monthDatabase.insert(MonthsEntry.TABLE_NAME, null, values);

        // if the insertion failed show a Log(e) for that.
        // make the uri return value equal null in case the insertion failed.
        if (id == -1) {

            Log.e(LOG_TAG, "Failed to insert row in cumulative database for " + uri);

            return null;
        }

        // Notify that there is changing happened in the database to sync changes to the network or activities.
        getContext().getContentResolver().notifyChange(uri, null);

        // return the uri for the place that the data inserted inside database.
        return ContentUris.withAppendedId(uri, id);

    }


    /**
     * (Helper Method to Insert Data Inside cumulative Database).
     * Insert data inside the cumulative_gpa in cumulative database.
     * Check the validation for the data inserted from the user.
     *
     * @param uri uri for the path inside the database.
     * @param values contain the columns keys and its values.
     *
     * @return uri refer to the row place inside the database.
     */
    private Uri insertDay(Uri uri, ContentValues values) {


        // access to the database to write inside it.
        SQLiteDatabase dayDatabase = mDaysDbHelper.getWritableDatabase();

        // insert the data inside the database and get the row number that the data inserted at.
        long id = dayDatabase.insert(DaysEntry.TABLE_NAME, null, values);

        // if the insertion failed show a Log(e) for that.
        // make the uri return value equal null in case the insertion failed.
        if (id == -1) {

            Log.e(LOG_TAG, "Failed to insert row in cumulative database for " + uri);

            return null;
        }

        // Notify that there is changing happened in the database to sync changes to the network or activities.
        getContext().getContentResolver().notifyChange(uri, null);

        // return the uri for the place that the data inserted inside database.
        return ContentUris.withAppendedId(uri, id);

    }


    /**
     * (Helper Method to Insert Data Inside cumulative Database).
     * Insert data inside the cumulative_gpa in cumulative database.
     * Check the validation for the data inserted from the user.
     *
     * @param uri uri for the path inside the database.
     * @param values contain the columns keys and its values.
     *
     * @return uri refer to the row place inside the database.
     */
    private Uri insertTask(Uri uri, ContentValues values) {


        // access to the database to write inside it.
        SQLiteDatabase tasksDatabase = mTasksDbHelper.getWritableDatabase();

        // insert the data inside the database and get the row number that the data inserted at.
        long id = tasksDatabase.insert(TasksEntry.TABLE_NAME, null, values);

        // if the insertion failed show a Log(e) for that.
        // make the uri return value equal null in case the insertion failed.
        if (id == -1) {

            Log.e(LOG_TAG, "Failed to insert row in cumulative database for " + uri);

            return null;
        }

        // Notify that there is changing happened in the database to sync changes to the network or activities.
        getContext().getContentResolver().notifyChange(uri, null);

        // return the uri for the place that the data inserted inside database.
        return ContentUris.withAppendedId(uri, id);

    }


    /**
     * Write inside the database to update data inserted before.
     *
     * @param uri uri for the database path.
     * @param contentValues contain the columns keys and its values.
     * @param selection specific row in the database.
     * @param selectionArgs the value for the selection parameter above.
     *
     * @return uri refer to the row place inside the database.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        // get the pattern that the uri equal.
        final int match = sUriMatcher.match(uri);

        // setup functions for every uri pattern.
        switch (match) {

            // semester_gpa database with no id.
            case YEAR:

                // execute helper method to update the data inside the database.
                // return number of the rows that updated.
                return updateYear(uri, contentValues, selection, selectionArgs);

            // semester_gpa database with id.
            case YEAR_ID:

                // setup the input inside the update function (after the WHERE word).
                selection = YearEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // execute helper method to update the data inside the database.
                // return number of the rows that updated.
                return updateYear(uri, contentValues, selection, selectionArgs);

            // semester_gpa database with no id.
            case MONTHS:

                // execute helper method to update the data inside the database.
                // return number of the rows that updated.
                return updateMonth(uri, contentValues, selection, selectionArgs);

            // semester_gpa database with id.
            case MONTHS_ID:

                // setup the input inside the update function (after the WHERE word).
                selection = YearEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // execute helper method to update the data inside the database.
                // return number of the rows that updated.
                return updateMonth(uri, contentValues, selection, selectionArgs);

            // semester_gpa database with no id.
            case DAYS:

                // execute helper method to update the data inside the database.
                // return number of the rows that updated.
                return updateDay(uri, contentValues, selection, selectionArgs);

            // semester_gpa database with id.
            case DAYS_ID:

                // setup the input inside the update function (after the WHERE word).
                selection = YearEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // execute helper method to update the data inside the database.
                // return number of the rows that updated.
                return updateDay(uri, contentValues, selection, selectionArgs);



            // semester_gpa database with no id.
            case TASKS:

                // execute helper method to update the data inside the database.
                // return number of the rows that updated.
                return updateTask(uri, contentValues, selection, selectionArgs);

            // semester_gpa database with id.
            case TASKS_ID:

                // setup the input inside the update function (after the WHERE word).
                selection = TasksEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // execute helper method to update the data inside the database.
                // return number of the rows that updated.
                return updateTask(uri, contentValues, selection, selectionArgs);



            // to handle if the is no match for the uri inserted with the uri patterns.
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }


    /**
     * (Helper Method to Update Data Inside semester Database).
     * Insert data inside the semester gpa database.
     * Check the validation for the data inserted from the user.
     *
     * @param uri uri for the database path.
     * @param contentValues contain the columns keys and its values.
     * @param selection specific row in the database.
     * @param selectionArgs the value for the selection parameter above.
     *
     * @return uri refer to the row place inside the database.
     */
    private int updateYear(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {


        // if there is not keys inside the ContentValues return from the functions early.
        // the number of the rows that updated in this case will equal zero.
        if (contentValues.size() == 0) {
            return 0;
        }

        // access to the database to write inside it.
        SQLiteDatabase semesterDatabase = mYearsDbHelper.getWritableDatabase();

        // update the data inside the database and get the number of the rows that updated.
        int rowsUpdated = semesterDatabase.update(YearEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        // Notify that there is changing happened in the database to sync changes to the network or activities.
        getContext().getContentResolver().notifyChange(uri, null);

        // return the number of the rows that updated.
        return rowsUpdated;
    }


    /**
     * (Helper Method to Update Data Inside semester Database).
     * Insert data inside the semester gpa database.
     * Check the validation for the data inserted from the user.
     *
     * @param uri uri for the database path.
     * @param contentValues contain the columns keys and its values.
     * @param selection specific row in the database.
     * @param selectionArgs the value for the selection parameter above.
     *
     * @return uri refer to the row place inside the database.
     */
    private int updateMonth(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        // if there is not keys inside the ContentValues return from the functions early.
        // the number of the rows that updated in this case will equal zero.
        if (contentValues.size() == 0) {
            return 0;
        }

        // access to the database to write inside it.
        SQLiteDatabase semesterDatabase = mMonthsDbHelper.getWritableDatabase();

        // update the data inside the database and get the number of the rows that updated.
        int rowsUpdated = semesterDatabase.update(MonthsEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        // Notify that there is changing happened in the database to sync changes to the network or activities.
        getContext().getContentResolver().notifyChange(uri, null);

        // return the number of the rows that updated.
        return rowsUpdated;
    }


    /**
     * (Helper Method to Update Data Inside semester Database).
     * Insert data inside the semester gpa database.
     * Check the validation for the data inserted from the user.
     *
     * @param uri uri for the database path.
     * @param contentValues contain the columns keys and its values.
     * @param selection specific row in the database.
     * @param selectionArgs the value for the selection parameter above.
     *
     * @return uri refer to the row place inside the database.
     */
    private int updateDay(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        // if there is not keys inside the ContentValues return from the functions early.
        // the number of the rows that updated in this case will equal zero.
        if (contentValues.size() == 0) {
            return 0;
        }

        // access to the database to write inside it.
        SQLiteDatabase semesterDatabase = mDaysDbHelper.getWritableDatabase();

        // update the data inside the database and get the number of the rows that updated.
        int rowsUpdated = semesterDatabase.update(DaysEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        // Notify that there is changing happened in the database to sync changes to the network or activities.
        getContext().getContentResolver().notifyChange(uri, null);

        // return the number of the rows that updated.
        return rowsUpdated;
    }


    /**
     * (Helper Method to Update Data Inside semester Database).
     * Insert data inside the semester gpa database.
     * Check the validation for the data inserted from the user.
     *
     * @param uri uri for the database path.
     * @param contentValues contain the columns keys and its values.
     * @param selection specific row in the database.
     * @param selectionArgs the value for the selection parameter above.
     *
     * @return uri refer to the row place inside the database.
     */
    private int updateTask(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        // if there is not keys inside the ContentValues return from the functions early.
        // the number of the rows that updated in this case will equal zero.
        if (contentValues.size() == 0) {
            return 0;
        }

        // access to the database to write inside it.
        SQLiteDatabase tasksDatabase = mTasksDbHelper.getWritableDatabase();

        // update the data inside the database and get the number of the rows that updated.
        int rowsUpdated = tasksDatabase.update(TasksEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        // Notify that there is changing happened in the database to sync changes to the network or activities.
        getContext().getContentResolver().notifyChange(uri, null);

        // return the number of the rows that updated.
        return rowsUpdated;
    }



    /**
     * delete data from the database (all the database or a single row).
     *
     * @param uri uri for the database path.
     * @param selection specific row in the database.
     * @param selectionArgs the value for the selection parameter above.
     *
     * @return number of the rows that deleted from the database.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // access to databases (semester - cumulative) to write inside it.
        SQLiteDatabase yearsDatabase = mYearsDbHelper.getWritableDatabase();
        SQLiteDatabase monthsDatabase = mMonthsDbHelper.getWritableDatabase();
        SQLiteDatabase daysDatabase = mDaysDbHelper.getWritableDatabase();
        SQLiteDatabase tasksDatabase = mTasksDbHelper.getWritableDatabase();


        // get the pattern that the uri equal.
        final int match = sUriMatcher.match(uri);

        // number of the rows that will be deleted
        int rowsDeleted;

        // setup functions for every uri pattern.
        switch (match) {

            // semester_gpa database with no id.
            case YEAR:

                // delete the data from the database.
                // return number of the rows that deleted from the database.
                rowsDeleted = yearsDatabase.delete(YearEntry.TABLE_NAME, selection, selectionArgs);
                break;

            // semester_gpa database with id.
            case YEAR_ID:

                // setup the input inside the update function (after the WHERE word).
                selection = YearEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // delete the data from the database.
                // return number of the rows that deleted from the database.
                rowsDeleted = yearsDatabase.delete(YearEntry.TABLE_NAME, selection, selectionArgs);
                break;



            // semester_gpa database with no id.
            case MONTHS:

                // delete the data from the database.
                // return number of the rows that deleted from the database.
                rowsDeleted = monthsDatabase.delete(MonthsEntry.TABLE_NAME, selection, selectionArgs);
                break;

            // semester_gpa database with id.
            case MONTHS_ID:

                // setup the input inside the update function (after the WHERE word).
                selection = MonthsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // delete the data from the database.
                // return number of the rows that deleted from the database.
                rowsDeleted = monthsDatabase.delete(MonthsEntry.TABLE_NAME, selection, selectionArgs);
                break;


            // semester_gpa database with no id.
            case DAYS:

                // delete the data from the database.
                // return number of the rows that deleted from the database.
                rowsDeleted = daysDatabase.delete(DaysEntry.TABLE_NAME, selection, selectionArgs);
                break;

            // semester_gpa database with id.
            case DAYS_ID:

                // setup the input inside the update function (after the WHERE word).
                selection = DaysEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // delete the data from the database.
                // return number of the rows that deleted from the database.
                rowsDeleted = daysDatabase.delete(DaysEntry.TABLE_NAME, selection, selectionArgs);
                break;


            // semester_gpa database with no id.
            case TASKS:

                // delete the data from the database.
                // return number of the rows that deleted from the database.
                rowsDeleted = tasksDatabase.delete(TasksEntry.TABLE_NAME, selection, selectionArgs);
                break;

            // semester_gpa database with id.
            case TASKS_ID:

                // setup the input inside the update function (after the WHERE word).
                selection = TasksEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // delete the data from the database.
                // return number of the rows that deleted from the database.
                rowsDeleted = tasksDatabase.delete(TasksEntry.TABLE_NAME, selection, selectionArgs);
                break;

            // to handle if the is no match for the uri inserted with the uri patterns.
            default:

                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // notify the network or the activity when there is changing happened inside the database.
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // return number of the rows that deleted from the database.
        return rowsDeleted;
    }



    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {

            case YEAR:
                return YearEntry.CONTENT_LIST_TYPE;

            case YEAR_ID:
                return YearEntry.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);

        }

    }


}
