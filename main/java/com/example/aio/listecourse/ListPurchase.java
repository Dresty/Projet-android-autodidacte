package com.example.aio.listecourse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Drest on 12/12/2016.
 */
public class ListPurchase {

    private ArrayList<String> group;
    private Spinner groupChoice;
    private ArrayList<String> allPurchase;
    private ListView listView;
    private ArrayList<String> purchasesToDisplay;
    private TextView textViewTotal;
    private String period;
    private Spinner monthChoice;

    private View view = null;

    /**
     * Constructor
     * @param rootView
     * @param period
     */
    public ListPurchase(View rootView, String period) {
        this.view = rootView;
        allPurchase = new ArrayList<>();
        purchasesToDisplay = new ArrayList<>();
        listView = (ListView) this.view.findViewById(R.id.listView);
        textViewTotal = (TextView) this.view.findViewById(R.id.TotalTextNumber);
        this.period = period;
        try {
            ReadSettings(this.view.getContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        createSpiner();

    }

    /**
     * Create a spinner of groupe and mouth (ici month is selected)
     */
    private void createSpiner() {
        this.groupChoice = (Spinner)this.view.findViewById(R.id.GroupeChoice);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.view.getContext(), android.R.layout.simple_spinner_item, Groupp.getAllNameGroupp());
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if(this.groupChoice != null)
        {
            this.groupChoice.setAdapter(dataAdapter);
        }

        this.monthChoice = (Spinner)this.view.findViewById(R.id.monthChoice);
        ArrayAdapter<String> dataAdapterMonthChoice = new ArrayAdapter<String>(this.view.getContext(), android.R.layout.simple_spinner_item, AllDate.allDates());
        dataAdapterMonthChoice.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if(this.monthChoice != null)
        {
            this.monthChoice.setAdapter(dataAdapterMonthChoice);
        }

        this.groupChoice.setOnItemSelectedListener(choiceFilter());

        if(this.monthChoice != null){
            this.monthChoice.setOnItemSelectedListener(choiceFilter());
            this.monthChoice.setSelection(AllDate.position(new Date()));
        }

    }

    public AdapterView.OnItemSelectedListener choiceFilter()
    {
        return (new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                purchasesToDisplay = null;
                purchasesToDisplay = new ArrayList<String>();
                purchasesToDisplay = goodGroup(allPurchase, groupChoice.getSelectedItem().toString());
                if (period == "mois") {
                    purchasesToDisplay = selectedPurchaseForSelectedMonth((String) monthChoice.getSelectedItem(), purchasesToDisplay);
                }
                textViewTotal.setText(String.valueOf(sumList(parcePrice(purchasesToDisplay))));
                if (view != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_list_item_1, purchasesToDisplay);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        // Delete a purcharse
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                            final int idInAllPurchase = searchEqualsString(purchasesToDisplay.get(i), allPurchase);
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Etes-vous sûr de vouloir supprimer cette ligne : "+ purchasesToDisplay.get(i) +  "?")
                                    .setTitle("Confirmation");
                            builder.setPositiveButton(R.string.Accpeter, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button
                                    if(idInAllPurchase > -1){
                                        allPurchase.remove(idInAllPurchase);
                                        AddPurchaseClass.WriteSettings(view.getContext(), allPurchase, true);
                                    }

                                }
                            });
                            builder.setNegativeButton(R.string.Refuser, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private int searchEqualsString(String stringToFind, ArrayList<String> arrayLS){
        int cpt = 0;
        for(String s : arrayLS){
            if(s.equals(stringToFind)){
                return cpt;
            }
            cpt++;
        }
        return -1;
    }

    /**
     *
     * @param selectedItem
     * @param purchasesToDisplay
     * @return all purchase for a selected month
     */
    private ArrayList<String> selectedPurchaseForSelectedMonth(String selectedItem, ArrayList<String> purchasesToDisplay) {
        String choiceDate = AllDate.dateNormalToDateFormat(selectedItem);
        String charTemp = "";
        boolean dayFind = false;
        int count = 0;
        ArrayList<String> datas = new ArrayList<>();

        for(String s : purchasesToDisplay){
            for(char c : s.toCharArray())
            {
                // Si je regarde la date
                if (count == 3) {
                    if (!dayFind) {
                        if (c == '/')
                            dayFind = true;
                    } else {
                        charTemp += c;
                    }
                }
                else{
                    if( c == ';'){
                        count++;
                    }
                }

            }
            if (charTemp.equals(AllDate.dateNormalToDateFormat(selectedItem))){
                datas.add(s);
            }
            charTemp = "";
            count = 0;
            dayFind = false;
        }
        return datas;
    }


    /*
    Récupère l'ensemble des données qui font part du bon groupe
     */
    public ArrayList<String> goodGroup(ArrayList<String> allPurchases, String groupp)
    {
        ArrayList<String> aS = new ArrayList<>();
        int cont = 0;
        String charTemp = "";
        // Pour tous les achats
        for(String s : allPurchases)
        {
            // Je parse afin de récupérer les bonnes données
            for(char c : s.toCharArray())
            {

                if(cont == 2)
                {
                    if(groupp.equals("ACHAT_PERSO") || groupp.equals("ACHAT_NON_PERSO"))
                    {
                        if(charTemp.equals(groupp)  || Groupp.getAchat(groupp).contains(charTemp))
                        {
                            aS.add(s);
                            break;
                        }
                        else
                        {
                            charTemp += c;

                        }
                    }
                    else
                    {
                        if(charTemp.equals(groupp))
                        {
                            aS.add(s);
                            break;
                        }
                        else
                        {
                            charTemp += c;

                        }
                    }

                }
                else if(cont == 0 || cont==1)
                {
                    if(c == ';')
                    {
                        cont += 1;
                        charTemp = "";
                    }
                }
                else
                    break;
            }
            charTemp = "";
            cont = 0;
        }
        return aS;
    }

    /*
    Recupère l'ensemble des prix
     */
    public ArrayList<Float> parcePrice(ArrayList<String> aS)
    {
        ArrayList<Float> aF = new ArrayList<>();
        int cont = 0;
        String charTemp = "";
        for(String s : aS)
        {
            cont = 0;
            charTemp = "";
            for(char c : s.toCharArray())
            {
                if(cont == 1)
                {

                    if(c == ';')
                    {
                        aF.add(Float.valueOf(charTemp));
                        cont = 2;
                    }
                    else
                    {
                        charTemp += c;
                    }
                }
                else if(cont == 0)
                {
                    if(c == ';')
                    {
                        cont += 1;
                    }
                }
                else
                    break;
            }
        }
        return aF;
    }

    /**
     *
     * @param aF
     * @return L'addition de tous les achats de la liste
     */
    public float sumList(ArrayList<Float> aF)
    {
        float f = 0;
        for(Float af : aF)
        {
            f += af;
        }
        return f;
    }

    public String ReadSettings(Context context) throws FileNotFoundException {
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
                allPurchase.add(line);
            }
            br.close();
        }
        catch (IOException e) {

        }

        return text.toString();
    }
}

