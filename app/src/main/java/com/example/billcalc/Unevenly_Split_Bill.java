package com.example.billcalc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.example.billcalc.databinding.ActivityUnevenlySplitBillBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class Unevenly_Split_Bill extends AppCompatActivity {
    HashMap<Integer, TextView> integerTextViewHashMap = new HashMap<>();
    HashMap<Integer, EditText> integerEditTextHashMap = new HashMap<>();
    ArrayList<LinearLayout> linearLayoutArrayList = new ArrayList<>();
    double tipPercent = .15;
    private ActivityUnevenlySplitBillBinding binding;
    int amountOfPeople = 4;
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUnevenlySplitBillBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.CollapsableNumberOfPeople.setAlpha(1f);
        highlightButton(binding.fifteen);

        manipulateLinearLayouts();

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

        binding.changeView1.setOnClickListener(view7 -> {
            Intent intent = new Intent(Unevenly_Split_Bill.this,MainActivity.class);
            startActivity(intent);
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

        binding.ChangeableAmountOfPeople.setOnClickListener(view5 -> {
                if(Integer.parseInt(binding.ChangeableAmountOfPeople.getText().toString()) > 16)
                {
                    binding.ChangeableAmountOfPeople.setText("16");
                    Toast too_many_friends = Toast.makeText(this /* MyActivity */, "You are not allowed to have more than 16 friends", Toast.LENGTH_SHORT);
                    too_many_friends.show();
                }
            if(Integer.parseInt(binding.ChangeableAmountOfPeople.getText().toString()) == 0)
            {
                binding.ChangeableAmountOfPeople.setText("1");
                Toast no_friends = Toast.makeText(this /* MyActivity */, "You are not allowed to have no friends", Toast.LENGTH_SHORT);
                no_friends.show();
            }
            manipulateLinearLayouts();
            changeAllTextViews();
        });
    }


    @SuppressLint("DefaultLocale")
    public void manipulateLinearLayouts()
    {
        int numberOfPeople = Integer.parseInt(binding.ChangeableAmountOfPeople.getText().toString());
        if(linearLayoutArrayList.size() != numberOfPeople)
        {
            if(numberOfPeople > linearLayoutArrayList.size())
            {
                for (int i = linearLayoutArrayList.size(); i < numberOfPeople; i++) {
                    createLinearLayout(i);
                    amountOfPeople = linearLayoutArrayList.size();
                    binding.ChangeableAmountOfPeople.setText(String.format("%d",amountOfPeople));
                }
            }
            else {
                deleteLinearLayout(numberOfPeople);
                amountOfPeople = linearLayoutArrayList.size();
                binding.ChangeableAmountOfPeople.setText(String.format("%d",amountOfPeople));
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
    public void deleteLinearLayout(int numberOfPeople)
    {
        if(linearLayoutArrayList.size() > 8)
        {
            binding.rightSideEditTextHolder.removeView(linearLayoutArrayList.remove(linearLayoutArrayList.size()-1));
        }
        else
        {
            binding.leftSideEditTextHolder.removeView(linearLayoutArrayList.remove(linearLayoutArrayList.size()-1));
        }
        if(!(linearLayoutArrayList.size() == numberOfPeople))
        {
            deleteLinearLayout(numberOfPeople);
        }
    }
    public void fillLinearLayout(LinearLayout layout)
    {
        int numberOfPeople = Integer.parseInt(binding.ChangeableAmountOfPeople.getText().toString());
        EditText individualBillInput = new EditText(getApplicationContext());
        individualBillInput.setHint("Bill Amount");
        integerEditTextHashMap.putIfAbsent(numberOfPeople, individualBillInput);
        individualBillInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        individualBillInput.setTextColor(ContextCompat.getColor(this,R.color.white));

        TextView individualBill = new TextView(getApplicationContext());
        integerTextViewHashMap.putIfAbsent(numberOfPeople, individualBill);
        individualBill.setText("$0");
        individualBill.setTextColor(ContextCompat.getColor(this,R.color.white));

        individualBillInput.addTextChangedListener(new TextWatcher() {
            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {
                try {
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
            for (int i = 1; i <= integerTextViewHashMap.size(); i++) {
                TextView tempTextViewName = integerTextViewHashMap.get(i);
                EditText tempEditTextName = integerEditTextHashMap.get(i);

                //prevents it from getting stuck on one empty edittext and not continuing on to the other edittexts that exist
                assert tempEditTextName != null;
                if(tempEditTextName.getText().toString().isEmpty())
                {
                    continue;
                }

                double tempAmount = Double.parseDouble(tempEditTextName.getText().toString()) + valueCalculator();
                assert tempTextViewName != null;
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