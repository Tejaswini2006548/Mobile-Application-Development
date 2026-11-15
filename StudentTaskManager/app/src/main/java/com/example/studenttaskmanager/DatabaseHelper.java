
package com.example.studenttaskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentTasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tasks";
    private static final String COL_ID = "id";
    private static final String COL_STUDENT = "student_id";
    private static final String COL_TASK = "task";
    private static final String COL_STATUS = "status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_STUDENT + " TEXT NOT NULL, " +
                COL_TASK + " TEXT NOT NULL, " +
                COL_STATUS + " TEXT NOT NULL)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Add a new record
    public boolean insertRecord(String studentId,
                                String task,
                                String status) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_STUDENT, studentId);
        values.put(COL_TASK, task);
        values.put(COL_STATUS, status);

        long result = db.insert(TABLE_NAME, null, values);

        return result != -1;
    }

    // View all records
    public Cursor getAllRecords() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COL_ID + " DESC"
        );
    }

    // Search records by student ID or task
    public Cursor searchRecords(String keyword) {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(
                TABLE_NAME,
                null,
                COL_STUDENT + " LIKE ? OR " +
                        COL_TASK + " LIKE ?",
                new String[]{
                        "%" + keyword + "%",
                        "%" + keyword + "%"
                },
                null,
                null,
                COL_ID + " DESC"
        );
    }

    // Update an existing record
    public boolean updateRecord(int id,
                                String studentId,
                                String task,
                                String status) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_STUDENT, studentId);
        values.put(COL_TASK, task);
        values.put(COL_STATUS, status);

        int result = db.update(
                TABLE_NAME,
                values,
                COL_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }

    // Delete a record
    public boolean deleteRecord(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_NAME,
                COL_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }
}
