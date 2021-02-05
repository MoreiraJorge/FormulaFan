package pt.ipp.estg.formulafan.Databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pt.ipp.estg.formulafan.Models.DriverPosition;

@Database(entities = {DriverPosition.class}, version = 1, exportSchema = false)
public abstract class DriverPositionDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    private static volatile DriverPositionDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DriverPositionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DriverPositionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DriverPositionDatabase.class, "drivers_positions_database") //.addCallback(sRoomDatabaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract DriverPositionDAO getDriverPositionDAO();

    private static RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                DriverPositionDAO dao = INSTANCE.getDriverPositionDAO();
            });
        }
    };
}
