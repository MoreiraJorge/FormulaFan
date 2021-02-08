package pt.ipp.estg.formulafan.Databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import pt.ipp.estg.formulafan.Models.User;

@Dao
public interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserInfo(User... user);

    @Query("Select * From User Where email = :user_mail")
    LiveData<User> getUserInfo(String user_mail);

}
