package com.example.macroeconresearcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

public class GraphActivity extends AppCompatActivity {
    private MacroEconomicRecordViewModel macroEconomicRecordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        String mode = getIntent().getStringExtra("mode");
        if (mode.equals("gov")) {
            Button annotationButton = findViewById(R.id.annotationButton);
            annotationButton.setVisibility(View.INVISIBLE);
        }
        macroEconomicRecordViewModel = new ViewModelProvider(
                GraphActivity.this).get(
                MacroEconomicRecordViewModel.class
        );
    }

    public void onApply(View view) {
        String country = (String)((Spinner)findViewById(R.id.countrySpinner)).getSelectedItem();
        TextInputLayout endYearText = findViewById(R.id.endYearLayout);
        TextInputLayout startYearText = findViewById(R.id.startYearLayout);
        try {
            int stop = Integer.parseInt(Objects.requireNonNull(endYearText.getEditText()).getText().toString());
            int start = Integer.parseInt(Objects.requireNonNull(startYearText.getEditText()).getText().toString());
            if (stop <= start) {
                Toast.makeText(this, "Error! Input must be a valid range", Toast.LENGTH_SHORT).show();
            } else if (stop > 2020 || start < 1960) {
                Toast.makeText(this, "Error! Range must be between 1960 and 2020", Toast.LENGTH_LONG).show();
            } else {
                if (getIntent().getStringExtra("gdp") != null) {
                    LiveData<List<MacroEconomicRecord>> gdp = macroEconomicRecordViewModel.filterFDIInflowByCountryAndRange(country, start, stop);
                }
//                if (getIntent().getStringExtra("fdi_inf") != null) {
//                    LiveData<List<MacroEconomicRecord>> fdi_inf = macroEconomicRecordViewModel.filterFDIInflowByCountryAndRange(country, start, stop);
//                }
//                if (getIntent().getStringExtra("fdi_outf") != null) {
//                    LiveData<List<MacroEconomicRecord>> fdi_outf = macroEconomicRecordViewModel.filterFDIOutflowByCountryAndRange(country, start, stop);
//                }
//                if (getIntent().getStringExtra("ie_flow") != null) {
//                    LiveData<List<MacroEconomicRecord>> ie_flow = macroEconomicRecordViewModel.filterImportExportFlowByCountryAndRange(country, start, stop);
//                }
                Toast.makeText(this, "Queries ran successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException nullPointerException) {
            Toast.makeText(this, "Error! Input cannot be null", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException numberFormatException) {
            Toast.makeText(this, "Error! Input must be a number", Toast.LENGTH_SHORT).show();
        }
    }
}