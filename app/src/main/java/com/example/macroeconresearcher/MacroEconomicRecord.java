package com.example.macroeconresearcher;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"year", "country"})
public class MacroEconomicRecord {
    @NonNull
    @ColumnInfo(name = "year")
    public int year;

    @NonNull
    @ColumnInfo(name = "country")
    public String country;

    @ColumnInfo(name = "gdp_usd")
    public Double gdpUSD;

    @ColumnInfo(name = "fdi_net_inflow")
    public Double fdiNetInflow;

    @ColumnInfo(name = "fdi_net_outflow")
    public Double fdiNetOutflow;

    @ColumnInfo(name = "current_account_balance")
    public Double currentAccountBalance;
}
