package com.example.billcalc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText billAmount;
    TextView amountPerPerson;
    Button zeroPercent;
    Button tenPercent;
    Button fifteenPercent;
    Button eighteenPercent;
    Button changeNumberOfPeople;
    LinearLayout collapsableNumberOfPeople;
    double tipPercent = 1.15;
    int amountOfPeople = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        //linking XML and JAVA
        billAmount = findViewById(R.id.BillAmount);
        amountPerPerson = findViewById(R.id.Amount);

        zeroPercent = findViewById(R.id.Zero);
        tenPercent = findViewById(R.id.Ten);
        fifteenPercent = findViewById(R.id.Fifteen);
        eighteenPercent = findViewById(R.id.Eighteen);
        changeNumberOfPeople = findViewById(R.id.ChangeNumberOfPeople);

        collapsableNumberOfPeople = findViewById(R.id.CollapsableNumberOfPeople);
        collapsableNumberOfPeople.setAlpha(0f);
        //onClick Listeners
        billAmount.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view)
            {
                amountPerPerson.setText(valueCalculator());
            }
        });

        zeroPercent.setOnClickListener(view -> {
            tipPercent = 1;
            amountPerPerson.setText(valueCalculator());
            changeBack();
            highlightButton(zeroPercent);
        });

        tenPercent.setOnClickListener(view -> {
            tipPercent = 1.1;
            amountPerPerson.setText(valueCalculator());
            changeBack();
            highlightButton(tenPercent);
        });

        fifteenPercent.setOnClickListener(view -> {
            tipPercent = 1.15;
            amountPerPerson.setText(valueCalculator());
            changeBack();
            highlightButton(fifteenPercent);
        });

        eighteenPercent.setOnClickListener(view -> {
            tipPercent = 1.18;
            amountPerPerson.setText(valueCalculator());
            changeBack();
            highlightButton(eighteenPercent);
        });

        changeNumberOfPeople.setOnClickListener(view -> {
            //add animation to fly in from bottom
            collapsableNumberOfPeople.setAlpha(1f);
        });


    }//end of onCreate

    public String valueCalculator()
    {
        try {
            double billAmount1 = Double.parseDouble(billAmount.getText().toString());
            double tempTipAmount =  (billAmount1*tipPercent)/amountOfPeople;
            @SuppressLint("DefaultLocale") String tempTipAmountString = String.format("%.2f",tempTipAmount);

            return("$" + tempTipAmountString + " per person");
        } catch (NumberFormatException e){
            System.out.println("EditText be empty");
            return null;
        }
    }

    public void changeBack()
    {
        Button[] buttons = {zeroPercent,tenPercent,fifteenPercent,eighteenPercent};
        for (Button button : buttons) {
            button.animate().alpha(1f);
        }
    }

    public void highlightButton(Button button)
    {
        button.animate().alpha(.6f);
    }
}