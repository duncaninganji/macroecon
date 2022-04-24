package com.example.macroeconresearcher;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;


import java.util.List;

public class MacroEconomicRecordViewModel extends AndroidViewModel {
    private final MacroEconomicRecordRepository macroEconomicRecordRepository;

    public MacroEconomicRecordViewModel(Application application) {
        super(application);
        macroEconomicRecordRepository = new MacroEconomicRecordRepository(application);
    }

    public void insert(MacroEconomicRecord macroEconomicRecord) {
        macroEconomicRecordRepository.insert(macroEconomicRecord);
    }

    public List<MacroEconomicRecord> filterGDPByCountryAndRange(String country, int start, int stop) {
        return macroEconomicRecordRepository.filterGDPByCountryAndRange(country, start, stop);
    }

    public List<MacroEconomicRecord> filterFDIInflowByCountryAndRange(String country, int start, int stop) {
        return macroEconomicRecordRepository.filterFDIInflowByCountryAndRange(country, start, stop);
    }

    public List<MacroEconomicRecord> filterFDIOutflowByCountryAndRange(String country, int start, int stop) {
        return macroEconomicRecordRepository.filterFDIOutflowByCountryAndRange(country, start, stop);
    }

    public List<MacroEconomicRecord> filterImportExportFlowByCountryAndRange(String country, int start, int stop) {
        return macroEconomicRecordRepository.filterImportExportFlowByCountryAndRange(country, start, stop);
    }

    public List<MacroEconomicRecord> getAll() {
        return macroEconomicRecordRepository.getAll();
    }
}
