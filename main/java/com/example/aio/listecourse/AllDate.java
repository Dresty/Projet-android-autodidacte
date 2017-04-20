package com.example.aio.listecourse;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Drest on 07/01/2016.
 */
public class AllDate {

    /**
     *
     * @return ArrayList with all date checked in
     */
    public static ArrayList<String> allDates()
    {
        ArrayList<String> s = new ArrayList<>();
        s.add("Novembre 2016");
        s.add("Decembre 2016");
        s.add("Janvier 2017");
        s.add("Fevrier 2017");
        s.add("Mars 2017");
        s.add("Avril 2017");
        s.add("Mai 2017");
        s.add("Juin 2017");
        s.add("Juillet 2017");
        s.add("Aout 2017");
        s.add("Septembre 2017");
        s.add("Octobre 2017");
        s.add("Novembre 2017");
        s.add("Decembre 2017");
        s.add("Janvier 2018");
        s.add("Fevrier 2018");
        s.add("Mars 2018");
        s.add("Avril 2018");
        s.add("Mai 2018");
        s.add("Juin 2018");
        s.add("Juillet 2018");

        return s;
    }

    /**
     *
     * @param s
     * @return Return the good format with the date
     */
    public static String dateNormalToDateFormat(String s)
    {
        switch(s){
            case "Novembre 2016":
                return "10/2016";
            case "Decembre 2016":
                return "11/2016";
            case "Janvier 2017":
                return "0/2017";
            case "Fevrier 2017":
                return "1/2017";
            case "Mars 2017":
                return "2/2017";
            case "Avril 2017":
                return "3/2017";
            case "Mai 2017":
                return "4/2017";
            case "Juin 2017":
                return "5/2017";
            case "Juillet 2017":
                return "6/2017";
            case "Aout 2017":
                return "7/2017";
            case "Septembre 2017":
                return "8/2017";
            case "Octobre 2017":
                return "9/2017";
            case "Novembre 2017":
                return "10/2017";
            case "Decembre 2017":
                return "11/2017";
            case "Janvier 2018":
                return "0/2018";
            case "Fevrier 2018":
                return "1/2018";
            case "Mars 2018":
                return "2/2018";
            case "Avril 2018":
                return "3/2018";
            case "Mai 2018":
                return "4/2018";
            case "Juin 2018":
                return "5/2018";
            case "Juillet 2018":
                return "6/2018";
            default:
                return "0/0";
        }
    }

    /**
     *
     * @param date
     * @return position of date in the array
     */
    public static int position(Date date){
        return (((date.getYear()%116)*12) + date.getMonth()%11 - 10);
    }
}
