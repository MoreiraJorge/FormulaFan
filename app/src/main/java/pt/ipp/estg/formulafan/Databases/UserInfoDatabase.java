package pt.ipp.estg.formulafan.Databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pt.ipp.estg.formulafan.Models.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserInfoDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile UserInfoDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                UserInfoDao dao = INSTANCE.getUserInfoDAO();
            });
        }
    };

    public static UserInfoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserInfoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserInfoDatabase.class, "userInfo_database") //.addCallback(sRoomDatabaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserInfoDao getUserInfoDAO();

}
