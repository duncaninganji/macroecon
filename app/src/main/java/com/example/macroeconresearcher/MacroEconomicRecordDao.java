package com.example.macroeconresearcher;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MacroEconomicRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MacroEconomicRecord macroEconomicRecord);

    @Query("SELECT * FROM MacroEconomicRecord WHERE country == :country AND year >= :start AND year <= :stop")
    List<MacroEconomicRecord> filterGDPByCountryAndRange(String country, int start, int stop);

    @Query("SELECT * FROM MacroEconomicRecord WHERE country == :country AND year >= :start AND year <= :stop")
    List<MacroEconomicRecord> filterFDIInflowByCountryAndRange(String country, int start, int stop);

    @Query("SELECT * FROM MacroEconomicRecord WHERE country == :country AND year >= :start AND year <= :stop")
    List<MacroEconomicRecord> filterFDIOutflowByCountryAndRange(String country, int start, int stop);

    @Query("SELECT * FROM MacroEconomicRecord WHERE country == :country AND year >= :start AND year <= :stop")
    List<MacroEconomicRecord> filterImportExportFlowByCountryAndRange(String country, int start, int stop);

    @Query("SELECT * FROM MacroEconomicRecord")
    List<MacroEconomicRecord> getAll();
}
