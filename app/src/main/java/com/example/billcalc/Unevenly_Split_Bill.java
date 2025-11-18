package com.example.billcalc;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.billcalc.databinding.ActivityUnevenlySplitBillBinding;

public class Unevenly_Split_Bill extends AppCompatActivity {
    private ActivityUnevenlySplitBillBinding binding2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_unevenly_split_bill);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding2 = ActivityUnevenlySplitBillBinding.inflate(getLayoutInflater());
        binding2.CollapsableNumberOfPeople.setAlpha(0);





    }
}