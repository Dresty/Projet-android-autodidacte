package com.example.aio.listecourse;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Drest on 13/12/2016.
 */
public class Groupp {

    public ArrayList<String> allGroup;
    //Map<String, String> allGroupPersoOrNot;
    public static ArrayList<String> groupAndCategory;
    public static ArrayList<ArrayList<String>> allGroupPersoOrNot; // Première ligne :  ACHAT_PERSO; deuxième ligne : ACHAT_NON_PERSO
    public static View view;

    /**
     * Constructor
     */
    public Groupp()
    {

    }

    /**
     * Recovery groupe and category
     */
    private static void parseGroupAndCategory() {
        try {
            ReadSettings(view.getContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean findVigule = false;
        String stringTempGroup = "";
        String stringTempCategory = "";
        for(String s : groupAndCategory){
            stringTempCategory = "";
            stringTempGroup = "";
            Log.i("bulbiii", s);
            for(char c : s.toCharArray()){
                if(c == ';'){
                    findVigule = true;
                }
                else{
                    if(!findVigule){
                        stringTempGroup += c;
                    }
                    else{
                        stringTempCategory += c;
                    }
                }
            }
            findVigule = false;

            if (stringTempCategory.equals("ACHAT_PERSO")){
                allGroupPersoOrNot.get(0).add(stringTempGroup);
            }
            else if (stringTempCategory.equals("ACHAT_NON_PERSO")){
                allGroupPersoOrNot.get(1).add(stringTempGroup);
            }
        }
    }

    /**
     * Create first group
     */
    public static void createAllGroup(){
        allGroupPersoOrNot = new ArrayList<>();
        groupAndCategory = new ArrayList<>();
        // ACHAT_PERSO, add example group
        allGroupPersoOrNot.add(new ArrayList<String>());
        allGroupPersoOrNot.get(0).add("MANGA_PERSO");
        allGroupPersoOrNot.get(0).add("ACHAT_PERSO");
        allGroupPersoOrNot.get(0).add("SORTIE");
        allGroupPersoOrNot.get(0).add("MULTIMEDIA");
        allGroupPersoOrNot.get(0).add("SPORT_PERSO");
        // ACHAT_NON_PERSO, add example group
        allGroupPersoOrNot.add(new ArrayList<String>());
        allGroupPersoOrNot.get(1).add("MANGER");
        allGroupPersoOrNot.get(1).add("SPORT_NON_PERSO");
        allGroupPersoOrNot.get(1).add("ACHAT_NON_PERSO");

        parseGroupAndCategory();
    }

    /**
     *
     * @param groupName
     * @return give the category
     */
    public static String getTypeGroup(String groupName){
        for(String s: allGroupPersoOrNot.get(0)){
            if(s==groupName){
                return "PERSO";
            }
        }
        return "NON_PERSO";
    }

    public static ArrayList<String> getAchat(String g)
    {
        if(allGroupPersoOrNot == null)
        {
            createAllGroup();
        }
        if(g.equals("ACHAT_PERSO"))
        {
            return new ArrayList<String>(allGroupPersoOrNot.get(0));
        }
        else
            return new ArrayList<String>(allGroupPersoOrNot.get(1));
    }

    public static ArrayList<String> getAllNameGroupp()
    {
        if(allGroupPersoOrNot == null)
        {
            createAllGroup();
        }
        //return new ArrayList<String>(Arrays.asList("ACHAT_PERSO", "ACHAT_NON_PERSO", "MANGER", "MANGA", "SORTIE", "MULTIMEDIA"));
        ArrayList<String> s = new ArrayList<>();
        for(ArrayList<String> s2 : allGroupPersoOrNot){
            for(String s3 : s2){
                s.add(s3);
            }
        }

        return s;
    }

    public static String ReadSettings(Context context) throws FileNotFoundException {
        File sdcard = Environment.getRootDirectory();
        InputStream inputStream = context.openFileInput("grouppp.txt");


//Read text from file
        StringBuilder text = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
                Groupp.groupAndCategory.add(line);
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        return text.toString();
    }


}
