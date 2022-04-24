package com.example.macroeconresearcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends AppCompatActivity {
    private MacroEconomicRecordViewModel macroEconomicRecordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        macroEconomicRecordViewModel = new ViewModelProvider(
                GraphActivity.this).get(
                MacroEconomicRecordViewModel.class
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        String mode = getIntent().getStringExtra("mode");
        Button annotationButton = findViewById(R.id.annotationButton);
        if (mode.equals("gov")) {
            annotationButton.setVisibility(View.INVISIBLE);
        } else {
            annotationButton.setVisibility(View.VISIBLE);
        }
    }

    public void onApply(View view) {
        String country = ((Spinner)findViewById(R.id.countrySpinner)).getSelectedItem().toString().toLowerCase();
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
                    List<MacroEconomicRecord> gdp = macroEconomicRecordViewModel.filterGDPByCountryAndRange(country, start, stop);
//                    List<MacroEconomicRecord> all = macroEconomicRecordViewModel.getAll();
                    if (gdp == null) {
                        Toast.makeText(this, "Error! Query returned null", Toast.LENGTH_SHORT).show();
                    }
                    GraphView graphView = findViewById(R.id.idGraphView);

                    // on below line we are adding data to our graph view.
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                            // on below line we are adding
                            // each point on our x and y axis.
                            new DataPoint(1990, 1),
                            new DataPoint(1991, 2),
                            new DataPoint(1992, 3),
                            new DataPoint(1993, 4),
                            new DataPoint(1994, 5),
                            new DataPoint(1995, 6),
                            new DataPoint(1996, 5.5),
                            new DataPoint(1997, 5),
                            new DataPoint(1998, 4)
                    });
                    graphView.setTitle("My Graph View");
                    series.setColor(Color.RED);
                    graphView.setTitleColor(R.color.purple_200);
                    graphView.setTitleTextSize(18);
                    graphView.addSeries(series);;
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