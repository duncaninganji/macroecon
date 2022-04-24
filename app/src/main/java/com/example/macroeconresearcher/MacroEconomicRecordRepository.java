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

    List<MacroEconomicRecord> filterGDPByCountryAndRange(String country, int start, int stop) {
        return macroEconomicRecordDao.filterGDPByCountryAndRange(country, start, stop);
    }

    List<MacroEconomicRecord> filterFDIInflowByCountryAndRange(String country, int start, int stop) {
        return macroEconomicRecordDao.filterFDIInflowByCountryAndRange(country, start, stop);
    }

    List<MacroEconomicRecord> filterFDIOutflowByCountryAndRange(String country, int start, int stop) {
        return macroEconomicRecordDao.filterFDIOutflowByCountryAndRange(country, start, stop);
    }

    List<MacroEconomicRecord> filterImportExportFlowByCountryAndRange(String country, int start, int stop) {
        return macroEconomicRecordDao.filterImportExportFlowByCountryAndRange(country, start, stop);
    }

    List<MacroEconomicRecord> getAll() {
        return macroEconomicRecordDao.getAll();
    }

    void insert(MacroEconomicRecord macroEconomicRecord) {
        MacroEconAppDatabase.databaseWriteExecutor.execute(() -> {
            macroEconomicRecordDao.insert(macroEconomicRecord);
        });
    }
}
