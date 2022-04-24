package com.example.macroeconresearcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

    public void onShow(View view) {
        ArrayList<String> columns = getColumns();
        String country = (String)((Spinner)findViewById(R.id.countrySpinner)).getSelectedItem();
        Intent graphActivityIntent = new Intent(FilterActivity.this, GraphActivity.class);
        graphActivityIntent.putExtra("mode", getIntent().getStringExtra("mode"));
        for (String col: columns) {
            graphActivityIntent.putExtra(col, col);
        }
        startActivity(graphActivityIntent);
    }

    private ArrayList<String> getColumns() {
        ArrayList<String> columns = new ArrayList<>();
        if (((CheckBox)findViewById(R.id.gdpCheckBox)).isChecked()) {
            columns.add("gdp");
        }
        if (((CheckBox)findViewById(R.id.fdiInfCheckbox)).isChecked()) {
            columns.add("fdi_inf");
        }
        if (((CheckBox)findViewById(R.id.fdiOutfCheckbox)).isChecked()) {
            columns.add("fdi_outf");
        }
        if (((CheckBox)findViewById(R.id.ieFlowCheckbox)).isChecked()) {
            columns.add("ie_flow");
        }
      return columns;
    }

}