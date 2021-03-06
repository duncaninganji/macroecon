package com.example.macroeconresearcher;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MacroEconomicRecordRepository {
    private MacroEconomicRecordDao macroEconomicRecordDao;

    MacroEconomicRecordRepository(Application application) {
        MacroEconAppDatabase db =  MacroEconAppDatabase.getDatabase(application);
        macroEconomicRecordDao = db.macroEconomicRecordDao();
    }

    LiveData<List<MacroEconomicRecord>> filterGDPByCountryAndRange(String country, int start, int stop) {
        return macroEconomicRecordDao.filterGDPByCountryAndRange(country, start, stop);
    }

    LiveData<List<MacroEconomicRecord>> filterFDIInflowByCountryAndRange(String country, int start, int stop) {
        return macroEconomicRecordDao.filterFDIInflowByCountryAndRange(country, start, stop);
    }

    LiveData<List<MacroEconomicRecord>> filterFDIOutflowByCountryAndRange(String country, int start, int stop) {
        return macroEconomicRecordDao.filterFDIOutflowByCountryAndRange(country, start, stop);
    }

    LiveData<List<MacroEconomicRecord>> filterImportExportFlowByCountryAndRange(String country, int start, int stop) {
        return macroEconomicRecordDao.filterImportExportFlowByCountryAndRange(country, start, stop);
    }

    void insert(MacroEconomicRecord macroEconomicRecord) {
        MacroEconAppDatabase.databaseWriteExecutor.execute(() -> {
            macroEconomicRecordDao.insert(macroEconomicRecord);
        });
    }
}
