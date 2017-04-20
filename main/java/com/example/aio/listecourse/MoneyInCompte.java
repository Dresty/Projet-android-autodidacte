package com.example.aio.listecourse;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Drest on 07/01/2016.
 */
public class MoneyInCompte {
    public static float moneyPerso = 0;
    public static float moneyNonPerso = 0;
    public static void init(View view){
        try {
            ReadSettings(view.getContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void WriteSettings(Context context, String data){
        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;

        try{
            context.getFileStreamPath("money.txt").delete();
            fOut = context.openFileOutput("money.txt", Context.MODE_APPEND);



            osw = new OutputStreamWriter(fOut);
            osw.write(data);
            osw.flush();
            //popup surgissant pour le résultat
            Toast.makeText(context, "Settings saved", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(context, "Settings not saved",Toast.LENGTH_SHORT).show();
        }
        finally {
            try {
                osw.close();
                fOut.close();
            } catch (IOException e) {
                Toast.makeText(context, "Settings not saved", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String ReadSettings(Context context) throws FileNotFoundException {
        InputStream inputStream = context.openFileInput("money.txt");

//Read text from file
        StringBuilder text = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            int cpt=0;
            while ((line = br.readLine()) != null) {
                text.append(line);
                switch(cpt){
                    case 0:
                        moneyPerso = Float.valueOf(line);
                        break;
                    case 1:
                        moneyNonPerso = Float.valueOf(line);
                        break;
                }
                cpt++;
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        return text.toString();
    }
}
