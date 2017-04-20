package com.example.aio.listecourse;


import java.util.Date;

/**
 * Created by Drest on 12/12/2016.
 */
public class Achat {
    private float price;
    private String namePurchase;
    private Date date;
    private String group;
    private String day;
    private String month;
    private String year;

    /**
     * Constructor
     * @param price
     * @param namePurchase
     * @param date
     * @param group
     */
    public Achat(float price, String namePurchase, Date date, String group)
    {
        this.price = price;
        this.namePurchase = namePurchase;
        this.date = date;
        this.group = group;
        this.day = "0";
        this.month = "0";
        this.month = "0";

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Constructor
     * @param price
     * @param namePurchase
     * @param year
     * @param month
     * @param day
     * @param group
     */
    public Achat(float price, String namePurchase, int year, int month, int day, String group)
    {
        this.price = price;
        this.namePurchase = namePurchase;
        this.date = new Date(year, month, day);
        this.group = group;
    }

    public Achat()
    {
        this.price = -1;
        this.namePurchase = "";
        this.date = null;
        this.group = null;
    }

    public float getPrice() {
        return price;
    }

    public String getNamePurchase() {
        return namePurchase;
    }

    /**
     *
     * @return good format of date for this application
     */
    public String getDate() {
        return day + "/" + month + "/" + year;
    }

    public String getGroup() {
        return group;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setNamePurchase(String namePurchase) {
        this.namePurchase = namePurchase;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
