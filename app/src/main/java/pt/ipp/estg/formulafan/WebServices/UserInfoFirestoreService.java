package pt.ipp.estg.formulafan.WebServices;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import pt.ipp.estg.formulafan.Models.User;
import pt.ipp.estg.formulafan.Repositories.UserInfoRepository;

public class UserInfoFirestoreService {
    private static final String TAG = "Test firestore";

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void addUserToFirestore(User user) {

        Map<String, Object> fireUser = new HashMap<>();
        fireUser.put("email", user.email);
        fireUser.put("userName", user.userName);
        fireUser.put("qi", user.qi);
        fireUser.put("correctAnswers", user.correctAnswers);
        fireUser.put("wrongAnsers", user.wrongAnsers);
        fireUser.put("quizesDone", user.quizesDone);
        fireUser.put("quizesMissed", user.quizesMissed);

        db.collection("users")
                .document(user.email)
                .set(fireUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Saved!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Failed!");
                    }
                });
    }

    public static void getUserFromFireStore(String email, UserInfoRepository repo) {
        DocumentReference docRef = db.collection("users").document(email);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String email = documentSnapshot.getString("email");
                            String userName = documentSnapshot.getString("userName");
                            int qi = documentSnapshot.getLong("qi").intValue();
                            int correctAnswers = documentSnapshot.getLong("correctAnswers").intValue();
                            int wrongAnsers = documentSnapshot.getLong("wrongAnsers").intValue();
                            int quizesDone = documentSnapshot.getLong("quizesDone").intValue();
                            int quizesMissed = documentSnapshot.getLong("quizesMissed").intValue();

                            User user;
                            user = new User(email, userName);
                            user.setCorrectAnswers(correctAnswers);
                            user.setWrongAnsers(wrongAnsers);
                            user.setQuizesDone(quizesDone);
                            user.setQuizesMissed(quizesMissed);
                            user.setQi(qi);

                            repo.setUserFromService(user);

                            Log.d(TAG, "FETCH USER SUCCESS!");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "FETCH USER FAILED!");
                    }
                });
    }

    public static void updateUseInfo(String email, User user) {
        db.collection("users").document(email).set(user);
    }

}
