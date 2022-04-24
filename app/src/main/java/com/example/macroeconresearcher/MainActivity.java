package com.example.macroeconresearcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private DownloadAsyncTask downloadAsyncTask;
    private String FILENAME = "gdp.csv";
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading App Data");
        downloadAsyncTask = new DownloadAsyncTask();
        String [] urls = {
                "https://drive.google.com/uc?export=download&id=1M5sZrUHK-Q1iufT30CxioQfBuYcmAyfp"
        };
//        downloadAsyncTask.execute(urls);
//        createTable();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.govtAvatar:
                mode = "gov";
                Toast.makeText(this, "Logged in as Govt Official", Toast.LENGTH_SHORT).show();
                break;
            case R.id.researcherAvatar:
                mode = "gov";
                Toast.makeText(this, "Logged in as Researcher", Toast.LENGTH_SHORT).show();
                break;
            case R.id.gdpIcon:
                Intent filterActivityIntent = new Intent(MainActivity.this, FilterActivity.class);
                filterActivityIntent.putExtra("mode", mode);
                startActivity(filterActivityIntent);
        }
    }

    private void createTable() {
        MacroEconomicRecordViewModel macroEconomicRecordViewModel = new ViewModelProvider(
                this).get(
                MacroEconomicRecordViewModel.class
        );
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
            while ((line = bufferedReader.readLine()) != null) {
                MacroEconomicRecord macroEconomicRecord = createRecord(line);
                if (macroEconomicRecord != null) macroEconomicRecordViewModel.insert(macroEconomicRecord);
            }
            Toast.makeText(
                    this,
                    "DB Synced",
                    Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException fileNotFoundException) {
            Toast.makeText(
                    this,
                    "Error! Source File Not Found",
                    Toast.LENGTH_SHORT).show();
        } catch (IOException ioException) {
            Toast.makeText(
                    this,
                    "Error! Cannot read file",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private MacroEconomicRecord createRecord(String row) {
        String [] columns = row.split(",");
        if (columns.length != 6) {
            Toast.makeText(this, "Error! Invalid Record", Toast.LENGTH_SHORT).show();
            return null;
        }
        MacroEconomicRecord macroEconomicRecord = new MacroEconomicRecord();
        macroEconomicRecord.year = Integer.parseInt(columns[0]);
        macroEconomicRecord.country = columns[1];
        macroEconomicRecord.gdpUSD = Double.parseDouble(columns[2]);
        macroEconomicRecord.fdiNetInflow = Double.parseDouble(columns[3]);
        macroEconomicRecord.fdiNetOutflow = Double.parseDouble(columns[4]);
        macroEconomicRecord.currentAccountBalance = Double.parseDouble(columns[5]);
        return macroEconomicRecord;
    }

    private class DownloadAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            for ( int i = 0; i < urls.length; i++) {
                InputStream inputStream = null;
                OutputStream outputStream = null;
                HttpsURLConnection httpsURLConnection = null;

                try {
                    URL url = new URL(urls[i]);
                    httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    httpsURLConnection.connect();

                    if (httpsURLConnection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                        return "Server returned HTTP " + httpsURLConnection.getResponseCode()
                                + " " + httpsURLConnection.getResponseMessage();
                    }

                    int contentLength = httpsURLConnection.getContentLength();
                    inputStream = httpsURLConnection.getInputStream();
                    outputStream = openFileOutput(FILENAME, MODE_PRIVATE);
                    byte[] data = new byte[4096];
                    long total = 0;
                    int count;
                    while((count = inputStream.read(data)) != -1) {
                        if (isCancelled())
                            return null;
                        total += count;
                        if (contentLength > 0)
                            publishProgress(100 * (int)(total/contentLength));
                            outputStream.write(data, 0, count);
                    }
                } catch (Exception e) {
                    return e.toString();
                } finally {
                    try {
                        if (outputStream != null)
                            outputStream.close();
                        if (inputStream !=  null)
                            inputStream.close();
                    } catch (IOException ignored) { }

                    if (httpsURLConnection != null)
                        httpsURLConnection.disconnect();
                }
            }
        return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result !=  null)
                Toast.makeText(MainActivity.this, "Download Error! " + result, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "Data Downloaded Successfully!", Toast.LENGTH_SHORT).show();
        }

    }
}