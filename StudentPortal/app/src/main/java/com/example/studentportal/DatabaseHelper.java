
package com.example.studentportal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentPortal.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "email TEXT UNIQUE NOT NULL, " +
                "mobile TEXT NOT NULL, " +
                "designation TEXT NOT NULL, " +
                "salt TEXT NOT NULL, " +
                "password_hash TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    private String toHex(byte[] data) {
        StringBuilder result = new StringBuilder();

        for (byte b : data) {
            result.append(String.format("%02x", b & 0xff));
        }

        return result.toString();
    }

    private byte[] fromHex(String value) {
        byte[] result = new byte[value.length() / 2];

        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) Integer.parseInt(
                    value.substring(i * 2, i * 2 + 2), 16);
        }

        return result;
    }

    private String makeHash(String password, String salt) {
        PBEKeySpec spec = null;

        try {
            spec = new PBEKeySpec(
                    password.toCharArray(),
                    fromHex(salt),
                    120000,
                    256);

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256");

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return toHex(hash);

        } catch (Exception e) {
            return null;

        } finally {
            if (spec != null) {
                spec.clearPassword();
            }
        }
    }

    public boolean registerUser(String name, String email,
                                String mobile, String designation,
                                String password) {

        byte[] saltBytes = new byte[16];
        new SecureRandom().nextBytes(saltBytes);

        String salt = toHex(saltBytes);
        String hash = makeHash(password, salt);

        if (hash == null) {
            return false;
        }

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("email", email);
        values.put("mobile", mobile);
        values.put("designation", designation);
        values.put("salt", salt);
        values.put("password_hash", hash);

        SQLiteDatabase db = getWritableDatabase();

        long result = db.insert("users", null, values);

        return result != -1;
    }

    public boolean checkLogin(String email, String password) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT salt, password_hash FROM users WHERE email = ?",
                new String[]{email});

        boolean valid = false;

        try {
            if (cursor.moveToFirst()) {

                String salt = cursor.getString(0);
                String savedHash = cursor.getString(1);

                String enteredHash = makeHash(password, salt);

                valid = enteredHash != null &&
                        savedHash.equals(enteredHash);
            }
        } finally {
            cursor.close();
        }

        return valid;
    }

    public Cursor getUser(String email) {

        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(
                "SELECT name, email, mobile, designation " +
                        "FROM users WHERE email = ?",
                new String[]{email});
    }
}
