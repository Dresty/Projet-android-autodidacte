package com.example.aio.listecourse;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Drest on 04/03/2017.
 */
public class MoneyInCompteLayout {
    private View view;

    public MoneyInCompteLayout(View view){
        this.view = view;
        // déclarations
        final EditText editMoneyPersoPlus = (EditText) this.view.findViewById(R.id.editTextArgentPersoPlus);
        final EditText editMoneyNonPersoPlus = (EditText) this.view.findViewById(R.id.editTextArgentNonPersoPlus);
        final TextView moneyPersoText = (TextView) this.view.findViewById(R.id.textViewMoneyPerso);
        final TextView moneyNonPersoText = (TextView) this.view.findViewById(R.id.textViewMoneyNonPerso);
        moneyPersoText.setText(String.valueOf(MoneyInCompte.moneyPerso));
        moneyNonPersoText.setText(String.valueOf(MoneyInCompte.moneyNonPerso));
        Button validata = (Button) this.view.findViewById(R.id.buttonValidateMoney);

        validata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                if(!editMoneyPersoPlus.getText().toString().isEmpty()){
                    MoneyInCompte.moneyPerso += Float.valueOf(editMoneyPersoPlus.getText().toString());
                    moneyPersoText.setText(String.valueOf(MoneyInCompte.moneyPerso));
                }
                if(!editMoneyNonPersoPlus.getText().toString().isEmpty()){
                    MoneyInCompte.moneyNonPerso += Float.valueOf(editMoneyNonPersoPlus.getText().toString());
                    moneyNonPersoText.setText(String.valueOf(MoneyInCompte.moneyNonPerso));
                }
                String write = String.valueOf(MoneyInCompte.moneyPerso) + "\n" + String.valueOf(MoneyInCompte.moneyNonPerso);
                MoneyInCompte.WriteSettings(view.getContext(), write);
                builder.setMessage("Solde modifié");
                builder.show();
            }
        });
    }

}
