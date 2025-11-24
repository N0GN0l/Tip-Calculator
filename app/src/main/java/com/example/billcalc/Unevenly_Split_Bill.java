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
        highlightButton(binding.fifteen);

        binding.zero.setOnClickListener(view1 -> {
            tipPercent = 0;
            changeAllTextViews();
            Unevenly_Split_Bill.this.changeBack();
            Unevenly_Split_Bill.this.highlightButton(binding.zero);
        });
        binding.ten.setOnClickListener(view2 -> {
            tipPercent = .1;
            changeAllTextViews();
            Unevenly_Split_Bill.this.changeBack();
            Unevenly_Split_Bill.this.highlightButton(binding.ten);
        });
        binding.fifteen.setOnClickListener(view3 -> {
            tipPercent = .15;
            changeAllTextViews();
            Unevenly_Split_Bill.this.changeBack();
            Unevenly_Split_Bill.this.highlightButton(binding.fifteen);
        });
        binding.eighteen.setOnClickListener(view4 -> {
            tipPercent = .18;
            changeAllTextViews();
            Unevenly_Split_Bill.this.changeBack();
            Unevenly_Split_Bill.this.highlightButton(binding.eighteen);
        });

        //displays the collapsable view
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
            binding.SubtractFromNumberOfPeople.setEnabled(true);
            binding.AddFromAmountOfPeople.setEnabled(true);
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
                    changeAllTextViews();
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
                    changeAllTextViews();
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
                changeAllTextViews();
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });

        binding.ChangeableAmountOfPeople.setOnClickListener(view5 -> generateMultipleLinearLayouts());
    }


    public void generateMultipleLinearLayouts()
    {
        int valueFromEditText = Integer.parseInt(binding.ChangeableAmountOfPeople.getText().toString());
        if(linearLayoutArrayList.size() != valueFromEditText)
        {
            if(valueFromEditText > linearLayoutArrayList.size())
            {
                for (int i = linearLayoutArrayList.size(); i < valueFromEditText; i++) {
                    createLinearLayout(i);
                }
            }
        }
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
        }
    }

    public void createLinearLayout(int numberOfPeople)
    {
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
        individualBillInput.setId(100 + numberOfPeople);


        TextView individualBill = new TextView(getApplicationContext());
        individualBill.setId(1000 + numberOfPeople);
        individualBill.setText("$0");

        individualBillInput.addTextChangedListener(new TextWatcher() {
            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    System.out.println(individualBill.getId());
                    double tempAmount = Double.parseDouble(individualBillInput.getText().toString()) + valueCalculator();
                    individualBill.setText("$" + tempAmount);
                } catch (NumberFormatException e) {
                    individualBill.setText("$0");
                    System.out.println("EditText be empty");
                }
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

    @SuppressLint("SetTextI18n")
    public void changeAllTextViews()
    {
        try {
            int numberOfPeople = Integer.parseInt(binding.ChangeableAmountOfPeople.getText().toString());
            System.out.println(numberOfPeople);
            for (int i = 1000; i < (1000 + numberOfPeople); i++) {
                System.out.println(i);
                TextView tempTextViewName = findViewById(i);
                EditText tempEditTextName = findViewById(i - 900);

                //prevents it from getting stuck on one empty edittext and not continuing on to the other edittexts that exist
                if(tempEditTextName.getText().toString().isEmpty())
                {
                    continue;
                }

                double tempAmount = Double.parseDouble(tempEditTextName.getText().toString()) + valueCalculator();
                tempTextViewName.setText("$" + tempAmount);
            }
        }catch (NullPointerException e){
            System.out.println("EditText be nonexisty");
        }
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