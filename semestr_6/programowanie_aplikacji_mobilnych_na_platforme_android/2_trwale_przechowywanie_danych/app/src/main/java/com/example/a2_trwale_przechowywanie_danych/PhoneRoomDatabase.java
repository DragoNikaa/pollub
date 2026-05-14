package com.example.a2_trwale_przechowywanie_danych;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phone.class}, version = 1, exportSchema = false)
public abstract class PhoneRoomDatabase extends RoomDatabase {
    public abstract PhoneDao phoneDao();

    private static volatile PhoneRoomDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PhoneRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhoneRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PhoneRoomDatabase.class, "phone_database")
                            .addCallback(roomDatabaseCallback)
                            .fallbackToDestructiveMigration(true)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                PhoneDao dao = INSTANCE.phoneDao();
                Phone[] phones = {
                        new Phone("Google", "Pixel 9", "14", "https://store.google.com/pl/product/pixel_9"),
                        new Phone("Google", "Pixel 9 Pro", "14", "https://store.google.com/pl/product/pixel_9_pro"),
                        new Phone("Google", "Pixel 9a", "15", "https://store.google.com/pl/product/pixel_9a"),
                        new Phone("Google", "Pixel 10", "16", "https://store.google.com/pl/product/pixel_10"),
                };
                for (Phone phone : phones) dao.insert(phone);
            });
        }
    };
}