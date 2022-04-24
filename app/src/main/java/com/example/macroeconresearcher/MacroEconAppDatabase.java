package com.example.macroeconresearcher;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MacroEconomicRecord.class}, version = 1, exportSchema = false)
abstract class MacroEconAppDatabase extends RoomDatabase {
    public abstract MacroEconomicRecordDao macroEconomicRecordDao();

    private static volatile MacroEconAppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static MacroEconAppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MacroEconAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MacroEconAppDatabase.class, "macro_econ_app_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
