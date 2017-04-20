package com.example.aio.listecourse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Drest on 13/12/2016.
 */
public class AddPurchaseClass {

    private View rootView;
    private Achat achat;
    private Spinner allGroupp;
    private Activity mainActivity;
    private String textJsonFinal;

    /**
     * Constructor
     * @param rootView
     * @param mainActivity
     */
    public AddPurchaseClass(final View rootView, Activity mainActivity) {
        this.mainActivity = mainActivity;
        this.rootView = rootView;
        achat = new Achat();
        Button validateButton = (Button) rootView.findViewById(R.id.ValidatePurchase);
        createSpiner();
        createCalender();
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                if (String.valueOf(((EditText) rootView.findViewById(R.id.PurchaseEdit)).getText()) != null && String.valueOf(((EditText) rootView.findViewById(R.id.PricePurchaseEdit)).getText()) != null)
                {
                    achat.setNamePurchase(String.valueOf(((EditText) rootView.findViewById(R.id.PurchaseEdit)).getText()));
                    achat.setGroup(allGroupp.getSelectedItem().toString());
                    achat.setPrice(Float.valueOf(((EditText) rootView.findViewById(R.id.PricePurchaseEdit)).getText().toString()));
                    textJsonFinal = achat.getNamePurchase() + ";" + String.valueOf(achat.getPrice()) +
                    ";" + achat.getGroup() + ";" + achat.getDate() + "\n";
                    WriteSettings(rootView.getContext(), textJsonFinal);
                    lessMoney();

                    builder.setMessage("dracau");
                    builder.show();
                }
            }
        });
    }

    /**
     * Decreases the balance
     */
    private void lessMoney() {
        if(Groupp.getTypeGroup(achat.getGroup()) == "PERSO"){
            MoneyInCompte.moneyPerso -= achat.getPrice();
        }else{
            MoneyInCompte.moneyNonPerso -= achat.getPrice();
        }
        String write = String.valueOf(MoneyInCompte.moneyPerso) + "\n" + String.valueOf(MoneyInCompte.moneyNonPerso);
        MoneyInCompte.WriteSettings(this.rootView.getContext(), write);
    }

    /**
     * Create calendar
     */
    private void createCalender() {
        Button calender = (Button) this.rootView.findViewById(R.id.CalendarPurchase);
        final TextView textDate = (TextView) this.rootView.findViewById(R.id.DatePurchseText);
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // i GET SELECTED DATE !!!
                        achat.setDate(new Date(year, month, day));
                        textDate.setText("Date de l'achat \n" + String.valueOf(day)
                                + "/" + String.valueOf(month) + "/" + String.valueOf(year));
                        achat.setDay(String.valueOf(day));
                        achat.setMonth(String.valueOf(month));
                        achat.setYear(String.valueOf(year));
                    }
                };
                newFragment.show(mainActivity.getFragmentManager(), "datePicker");
            }

        });
    }

    /**
     * Create spinner of all group
     */
    private void createSpiner() {
        this.allGroupp = (Spinner)this.rootView.findViewById(R.id.spinnerGroup);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.rootView.getContext(), android.R.layout.simple_spinner_item, Groupp.getAllNameGroupp());
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        this.allGroupp.setAdapter(dataAdapter);
    }

    public void WriteSettings(Context context, String data){
        this.WriteSettings(context, data, false);
    }

    public static void WriteSettings(Context context, ArrayList<String> data, boolean remove){
        if(remove && data.size() > 0){
            WriteSettings(context, data.get(0) + "\n", true);
        }
        for(int i=1; i<data.size();i++){
            WriteSettings(context, data.get(i) + "\n", false);
        }
    }

    public static void WriteSettings(Context context, String data, boolean remove){
        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;

        try{
            if(remove){
                context.getFileStreamPath("purchases.txt").delete();
            }
            fOut = context.openFileOutput("purchases.txt", Context.MODE_APPEND);

            osw = new OutputStreamWriter(fOut);
            osw.write(data);
            osw.flush();
            //popup surgissant pour le résultat
            Toast.makeText(context, "Settings saved",Toast.LENGTH_SHORT).show();
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

    public String ReadSettings(Context context) throws FileNotFoundException {
        File sdcard = Environment.getRootDirectory();
        InputStream inputStream = context.openFileInput("purchases.txt");

//Read text from file
        StringBuilder text = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        return text.toString();
    }
}
