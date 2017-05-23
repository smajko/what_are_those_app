package org.smajko.watapp;

import android.os.Environment;
import java.io.File;
import java.io.FileWriter;


public class Log {

    private static StringBuffer sb = new StringBuffer();

    public static void i(String tag, String msg){

        android.util.Log.i(tag, msg);

        writeToLogFile("INFO:" + tag + ":" + msg+"\n");

    }

    public static void e(String tag, String msg){

        android.util.Log.e(tag, msg);

        writeToLogFile("ERROR:" + tag + ":" + msg+"\n");

    }

    public static void e(String tag, String msg, Throwable tr){

        android.util.Log.e(tag, msg,tr);

        writeToLogFile("ERROR:" + tag + ":" + msg+"\n" +  android.util.Log.getStackTraceString(tr) + "\n" );

    }


    private static void writeToLogFile(String text){

        try{
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File file = new File(extStorageDirectory + File.separator + "Log.txt");
            if(!file.exists())file.createNewFile();
            FileWriter fw = new FileWriter(file, true);
            fw.write(text);
            fw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


}

