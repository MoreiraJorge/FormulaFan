package pt.ipp.estg.formulafan.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import pt.ipp.estg.formulafan.Databases.UserInfoDao;
import pt.ipp.estg.formulafan.Databases.UserInfoDatabase;
import pt.ipp.estg.formulafan.Models.User;
import pt.ipp.estg.formulafan.WebServices.UserInfoFirestoreService;

public class UserInfoRepository {
    private UserInfoDao userInfoDao;

    public UserInfoRepository(Application application) {
        UserInfoDatabase db = UserInfoDatabase.getDatabase(application);
        userInfoDao = db.getUserInfoDAO();
    }

    public void insertUser(User user){
        UserInfoDatabase.databaseWriteExecutor.execute(() -> {
            userInfoDao.insertUserInfo(user);
            UserInfoFirestoreService.addUserToFirestore(user);
        });
    }

    public LiveData<User> getUserInfo(String email){
        return userInfoDao.getUserInfo(email);
    }
}
