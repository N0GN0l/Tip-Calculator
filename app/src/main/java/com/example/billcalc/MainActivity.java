package com.example.billcalc;

import android.annotation.SuppressLint;
import android.os.Bundle;

//importing widget types
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.billcalc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    double tipPercent = 1.15;
    int amountOfPeople = 4;
    private ActivityMainBinding binding;


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //linking XML and JAVA



        binding.CollapsableNumberOfPeople.setAlpha(0f);
        //onClick Listeners

        binding.BillAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                binding.Amount.setText(valueCalculator());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }


        });

        binding.Zero.setOnClickListener(view1 -> {
            tipPercent = 1;
            binding.Amount.setText(MainActivity.this.valueCalculator());
            MainActivity.this.changeBack();
            MainActivity.this.highlightButton(binding.Zero);
        });

        binding.Ten.setOnClickListener(view2 -> {
            tipPercent = 1.1;
            binding.Amount.setText(MainActivity.this.valueCalculator());
            MainActivity.this.changeBack();
            MainActivity.this.highlightButton(binding.Ten);
        });

        binding.Fifteen.setOnClickListener(view3 -> {
            tipPercent = 1.15;
            binding.Amount.setText(MainActivity.this.valueCalculator());
            MainActivity.this.changeBack();
            MainActivity.this.highlightButton(binding.Fifteen);
        });

        binding.Eighteen.setOnClickListener(view4 -> {
            tipPercent = 1.18;
            binding.Amount.setText(MainActivity.this.valueCalculator());
            MainActivity.this.changeBack();
            MainActivity.this.highlightButton(binding.Eighteen);
        });
        binding.SubtractFromNumberOfPeople.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                if(amountOfPeople!=0)
                {
                    amountOfPeople--;
                }
                binding.ChangeableAmountOfPeople.setText(String.format("%d",amountOfPeople));
            }
        });
        binding.AddFromAmountOfPeople.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                amountOfPeople++;
                binding.ChangeableAmountOfPeople.setText(String.format("%d",amountOfPeople));
            }
        });

        //displays the collapsable view
        binding.ChangeNumberOfPeople.setOnClickListener(view5 -> {
            //add animation to fly in from bottom
            binding.ChangeableAmountOfPeople.setText(String.format("%d", amountOfPeople));
            for (int i = 0; i < 10000; i++) {
                binding.CollapsableNumberOfPeople.animate().alphaBy((float) (.0001 * i));
            }
        });


    }//end of onCreate

    public String valueCalculator()
    {
        try {
            double billAmount1 = Double.parseDouble(binding.BillAmount.getText().toString());
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
        Button[] buttons = {binding.Zero,binding.Ten,binding.Fifteen,binding.Eighteen};
        for (Button button : buttons) {
            button.animate().alpha(1f);
        }
    }

    public void highlightButton(Button button)
    {
        button.animate().alpha(.6f);
    }
}