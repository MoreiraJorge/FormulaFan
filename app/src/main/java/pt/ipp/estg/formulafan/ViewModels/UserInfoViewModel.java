package pt.ipp.estg.formulafan.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import pt.ipp.estg.formulafan.Models.User;
import pt.ipp.estg.formulafan.Repositories.UserInfoRepository;

public class UserInfoViewModel extends AndroidViewModel {
    private final UserInfoRepository userInfoRepository;

    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        userInfoRepository = new UserInfoRepository(application);
    }

    public void insertUser(User user) {
        userInfoRepository.insertUser(user);
    }

    public LiveData<User> getUserInfo(String email) {
        return userInfoRepository.getUserInfo(email);
    }
}
