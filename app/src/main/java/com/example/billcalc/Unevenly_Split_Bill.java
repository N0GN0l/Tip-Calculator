package com.example.billcalc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;



import com.example.billcalc.databinding.ActivityUnevenlySplitBillBinding;

public class Unevenly_Split_Bill extends AppCompatActivity {
    private ActivityUnevenlySplitBillBinding binding;
    int amountOfPeople = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUnevenlySplitBillBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.CollapsableNumberOfPeople.setAlpha(0f);

        binding.ChangeNumberOfPeople.setOnClickListener(view5 -> {
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
        binding.main.setOnClickListener(view6 -> {
            if(binding.CollapsableNumberOfPeople.getAlpha() > 1f)
            {
                binding.CollapsableNumberOfPeople.setAlpha(1f);
            }
            for (int i = 0; i < 10000; i++) {
                binding.CollapsableNumberOfPeople.animate().alphaBy((float) (-.0001 * i));
            }

            binding.ChangeableAmountOfPeople.setEnabled(false);
        });
    }
}