package pt.ipp.estg.formulafan.Databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pt.ipp.estg.formulafan.Models.Race;

@Database(entities = {Race.class}, version = 1, exportSchema = false)
public abstract class CurrentRaceDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    private static volatile CurrentRaceDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CurrentRaceDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CurrentRaceDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CurrentRaceDatabase.class, "race_database") //.addCallback(sRoomDatabaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract RaceDAO getRaceDAO();

    private static RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                RaceDAO dao = INSTANCE.getRaceDAO();
            });
        }
    };
}
