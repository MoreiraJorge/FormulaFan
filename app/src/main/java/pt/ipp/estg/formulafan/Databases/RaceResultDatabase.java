package pt.ipp.estg.formulafan.Databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pt.ipp.estg.formulafan.Models.RaceResult;

@Database(entities = {RaceResult.class}, version = 1, exportSchema = false)
public abstract class RaceResultDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    private static volatile RaceResultDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RaceResultDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RaceResultDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RaceResultDatabase.class, "race_results_database") //.addCallback(sRoomDatabaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract RaceResultDAO getRaceResultDAO();

    private static RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                RaceResultDAO dao = INSTANCE.getRaceResultDAO();
            });
        }
    };

}
