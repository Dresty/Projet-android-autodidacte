package com.example.aio.listecourse;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.Arrays;

/**
 * Created by Drest on 07/01/2016.
 */
public class AddGroupp {

    private View view;
    private Spinner category;
    private TextView groupp;
    private Button validate;

    /**
     * Constructor
     * @param rootView
     */
    AddGroupp(View rootView){
        this.view = rootView;
        this.groupp = (TextView) this.view.findViewById(R.id.editTextGroupp);
        this.category = (Spinner) this.view.findViewById(R.id.spinnerCreateCategory);
        this.validate = (Button) this.view.findViewById(R.id.ValidateButtonGroupp);

        createSpinner();

        this.validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WriteSettings(view.getContext(), groupp.getText() + ";" + category.getSelectedItem() + "\n");
                if(category.getSelectedItem().equals("ACHAT_PERSO")){
                    Groupp.allGroupPersoOrNot.get(0).add(String.valueOf(groupp.getText()));
                }
                else
                    Groupp.allGroupPersoOrNot.get(1).add(String.valueOf(groupp.getText()));
            }
        });
    }

    /**
     * Create a spinner
     */
    private void createSpinner(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.view.getContext(), android.R.layout.simple_spinner_item, new ArrayList<String>(Arrays.asList("ACHAT_PERSO", "ACHAT_NON_PERSO")));
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if(this.category != null)
        {
            this.category.setAdapter(dataAdapter);
        }
    }

    public void WriteSettings(Context context, String data){
        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;

        try{

            fOut = context.openFileOutput("grouppp.txt", Context.MODE_APPEND);
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

    public String ReadSettings(Context context) throws FileNotFoundException {
        File sdcard = Environment.getRootDirectory();
        InputStream inputStream = context.openFileInput("group.txt");

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
