package com.example.billcalc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;



import com.example.billcalc.databinding.ActivityUnevenlySplitBillBinding;

import java.util.ArrayList;

public class Unevenly_Split_Bill extends AppCompatActivity {
    ArrayList<LinearLayout> linearLayoutArrayList = new ArrayList<>();
    double tipPercent = .15;
    private ActivityUnevenlySplitBillBinding binding;
    int amountOfPeople = 0;
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUnevenlySplitBillBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.CollapsableNumberOfPeople.setAlpha(0f);


        binding.zero.setOnClickListener(view1 -> {
            tipPercent = 0;
            Unevenly_Split_Bill.this.changeBack();
            Unevenly_Split_Bill.this.highlightButton(binding.zero);
        });
        binding.ten.setOnClickListener(view2 -> {
            tipPercent = .1;
            Unevenly_Split_Bill.this.changeBack();
            Unevenly_Split_Bill.this.highlightButton(binding.ten);
        });
        binding.fifteen.setOnClickListener(view3 -> {
            tipPercent = .15;
            Unevenly_Split_Bill.this.changeBack();
            Unevenly_Split_Bill.this.highlightButton(binding.fifteen);
        });
        binding.eighteen.setOnClickListener(view4 -> {
            tipPercent = .18;
            Unevenly_Split_Bill.this.changeBack();
            Unevenly_Split_Bill.this.highlightButton(binding.eighteen);
        });




        binding.ChangeNumberOfPeople.setOnClickListener(view1 -> {
            binding.ChangeableAmountOfPeople.setEnabled(true);
            //add animation to fly in from bottom
            if(binding.CollapsableNumberOfPeople.getAlpha() < 0f)
            {
                binding.CollapsableNumberOfPeople.setAlpha(0f);
            }
            binding.ChangeableAmountOfPeople.setText(String.format("%d", amountOfPeople));
            for (int i = 0; i < 10000; i++) {
                binding.CollapsableNumberOfPeople.animate().alphaBy((float) (.0001 * i));
            }
        });
        binding.SubtractFromNumberOfPeople.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                if(amountOfPeople>1)
                {
                    amountOfPeople--;
                    deleteLinearLayout();
                    binding.ChangeableAmountOfPeople.setText(String.format("%d",amountOfPeople));
                }

            }
        });
        binding.AddFromAmountOfPeople.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                if(amountOfPeople < 16)
                {
                    amountOfPeople++;
                    createLinearLayout();
                    binding.ChangeableAmountOfPeople.setText(String.format("%d",amountOfPeople));
                }
            }
        });
        view.setOnClickListener(view6 -> {
            
            if(binding.CollapsableNumberOfPeople.getAlpha() > 1f)
            {
                binding.CollapsableNumberOfPeople.setAlpha(1f);
            }
            for (int i = 0; i < 10000; i++) {
                binding.CollapsableNumberOfPeople.animate().alphaBy((float) (-.0001 * i));
            }
            binding.ChangeableAmountOfPeople.setEnabled(false);
            binding.SubtractFromNumberOfPeople.setEnabled(false);
            binding.AddFromAmountOfPeople.setEnabled(false);
        });

        binding.billAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                double tipAmount = valueCalculator();
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
    }

    public void createLinearLayout()
    {
        int numberOfPeople = Integer.parseInt(binding.ChangeableAmountOfPeople.getText().toString());

        LinearLayout individualContribution = new LinearLayout(getApplicationContext());
        individualContribution.setOrientation(LinearLayout.HORIZONTAL);
        individualContribution.setId(numberOfPeople);
        individualContribution.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayoutArrayList.add(individualContribution);

        fillLinearLayout(individualContribution);

        if(numberOfPeople < 8)
        {
            binding.leftSideEditTextHolder.addView(linearLayoutArrayList.get(numberOfPeople));
        }
        else {
            binding.rightSideEditTextHolder.addView(linearLayoutArrayList.get(numberOfPeople));
            System.out.println("Add to right side");
        }
    }

    public void deleteLinearLayout()
    {
        int numberOfPeople = Integer.parseInt(binding.ChangeableAmountOfPeople.getText().toString());
        if(numberOfPeople <= 8)
        {
            binding.leftSideEditTextHolder.removeView(linearLayoutArrayList.remove(numberOfPeople-1));
        }
        else
        {
            binding.rightSideEditTextHolder.removeView(linearLayoutArrayList.remove(numberOfPeople-1));
        }
    }

    public void fillLinearLayout(LinearLayout layout)
    {
        int numberOfPeople = Integer.parseInt(binding.ChangeableAmountOfPeople.getText().toString());
        EditText individualBillInput = new EditText(getApplicationContext());
        individualBillInput.setHint("Bill Amount");
        individualBillInput.setId(numberOfPeople);


        TextView individualBill = new TextView(getApplicationContext());
        individualBill.setId(numberOfPeople);

        individualBillInput.addTextChangedListener(new TextWatcher() {
            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(valueCalculator());
                double tempAmount = Double.parseDouble(individualBillInput.getText().toString()) + valueCalculator();
                individualBill.setText("$" + tempAmount);
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });



        layout.addView(individualBillInput, 0);
        layout.addView(individualBill, 1);
    }



    public double valueCalculator()
    {
        // make more efficient by adding if statement instead of try catch, that way it won't run unneeded code
        try {
            int numberOfPeople = Integer.parseInt(binding.ChangeableAmountOfPeople.getText().toString());
            double billAmount1 = Double.parseDouble(binding.billAmount.getText().toString());
            double tempTipAmount = (billAmount1 * tipPercent);
            tempTipAmount /= numberOfPeople;
            tempTipAmount = Math.round(tempTipAmount * 100.0);
            tempTipAmount = tempTipAmount/100;
            return tempTipAmount;
        } catch (NumberFormatException e){
            System.out.println("EditText be empty");
            return 0;
        }
    }
    private void changeBack()
    {
        Button[] buttons = {binding.zero,binding.ten,binding.fifteen,binding.eighteen};
        for (Button button : buttons) {
            button.animate().alpha(1f);
        }
    }
    public void highlightButton(Button button)
    {
        button.animate().alpha(.6f);
    }
}