package com.mobdev.leitnerbox;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;


public class FileHelper extends Activity {

    static final int REQUEST_PERMISSION_WRITE = 1111;
    protected static final String FIRST_BOX_FILE_NAME = "firstBox.txt";
    protected static final String SECOND_BOX_FILE_NAME = "secondBox.txt";
    protected static final String THIRD_BOX_FILE_NAME = "thirdBox.txt";
    protected static final String FOURTH_BOX_FILE_NAME = "fourthBox.txt";
    protected static final String FIFTHBOX_FILE_NAME = "fifthBox.txt";
    protected static final String LEARNED_BOX_FILE_NAME = "learnedBox.txt";



    static File getBoxFile(int boxNumber) {
        switch (boxNumber) {
            case 1: return new File(Environment.getExternalStorageDirectory(), FIRST_BOX_FILE_NAME);
            case 2: return new File(Environment.getExternalStorageDirectory(), SECOND_BOX_FILE_NAME);
            case 3: return new File(Environment.getExternalStorageDirectory(), THIRD_BOX_FILE_NAME);
            case 4: return new File(Environment.getExternalStorageDirectory(), FOURTH_BOX_FILE_NAME);
            case 5: return new File(Environment.getExternalStorageDirectory(), FIFTHBOX_FILE_NAME);
            case 6: return new File(Environment.getExternalStorageDirectory(), LEARNED_BOX_FILE_NAME);
        }
        return null;
    }

    long getDifferencedTimeFromNow(int days) {
        long difference = days * 24 * 3600 * 1000;
        return ZonedDateTime.now().toInstant().toEpochMilli() + difference;
    }

    byte[] getWritablePatternOf(String word, String meaning, long releaseTime) {
        return ("{" + releaseTime + ":" + word + ":\n" + meaning + "}\n").getBytes();
    }

    private int getDays(int boxNumber) {
        return 0;
//        switch (boxNumber) {
//            case 1: return 0;
//            case 2: return 1;
//            case 3: return 2;
//            case 4: return 4;
//            case 5: return 8;
//            default: return 0;
//        }
    }

    void addNewWordToBoxFile(String newWord, String newMeaning, int boxNumber, boolean backward) {
        FileOutputStream fileOutputStream = null;
        File boxFile = FileHelper.getBoxFile(boxNumber);
        try {
            fileOutputStream = new FileOutputStream(boxFile, true);
            long differencedTimeFromNow;
            if(backward)
                differencedTimeFromNow = getDifferencedTimeFromNow(1);
            else
                differencedTimeFromNow = getDifferencedTimeFromNow(getDays(boxNumber));
            fileOutputStream.write(getWritablePatternOf(newWord, newMeaning, differencedTimeFromNow));
            Log.i("------------------------------", newWord + newMeaning);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fileOutputStream != null;
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






    /* Checks if external storage is available for read and write */
    static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }



}














