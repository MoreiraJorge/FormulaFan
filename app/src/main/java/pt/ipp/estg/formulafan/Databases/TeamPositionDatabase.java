package pt.ipp.estg.formulafan.Databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pt.ipp.estg.formulafan.Models.TeamPosition;

@Database(entities = {TeamPosition.class}, version = 1, exportSchema = false)
public abstract class TeamPositionDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    private static volatile TeamPositionDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TeamPositionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TeamPositionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TeamPositionDatabase.class, "teams_positions_database") //.addCallback(sRoomDatabaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TeamPositionDAO getTeamPositionDAO();

    private static RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                TeamPositionDAO dao = INSTANCE.getTeamPositionDAO();
            });
        }
    };

}
