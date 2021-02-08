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

    public void registerUser(User user){
            UserInfoFirestoreService.addUserToFirestore(user);
    }

    public void insertUserToDb(String email){
        UserInfoFirestoreService.getUserFromFireStore(email, this);
    }

    public void setUserFromService(User user){
        UserInfoDatabase.databaseWriteExecutor.execute(() -> {
            userInfoDao.insertUserInfo(user);
        });
    }

    public LiveData<User> getUserInfo(String email){
        return userInfoDao.getUserInfo(email);
    }
}
