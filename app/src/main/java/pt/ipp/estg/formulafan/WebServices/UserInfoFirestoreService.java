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

public class UserInfoFirestoreService {

    private static final String TAG = "Test firestore";

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static User user;

    public static void addUserToFirestore(User user) {

        Map<String, Object> fireUser = new HashMap<>();
        fireUser.put("email", user.email);
        fireUser.put("userName", user.userName);
        fireUser.put("qi", user.qi);
        fireUser.put("correctAnswers", user.correctAnswers);
        fireUser.put("wrongAnsers", user.wrongAnsers);
        fireUser.put("quizesDone", user.quizesDone);
        fireUser.put("quizesMissed", user.quizesMissed);

        db.collection("userInfo")
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

    public static User getUserFromFireStore(String email) {
        DocumentReference docRef = db.document("userInfo" + "/" + email);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){

                            String email = documentSnapshot.getString("email");
                            String userName = documentSnapshot.getString("userName");
                            int qi = Integer.parseInt(documentSnapshot.getString("qi"));
                            int correctAnswers = Integer.parseInt(documentSnapshot.getString("correctAnswers"));
                            int wrongAnsers = Integer.parseInt(documentSnapshot.getString("wrongAnsers"));
                            int quizesDone = Integer.parseInt(documentSnapshot.getString("quizesDone"));
                            int quizesMissed = Integer.parseInt(documentSnapshot.getString("quizesMissed"));

                            user = new User(email,userName);
                            user.setCorrectAnswers(correctAnswers);
                            user.setWrongAnsers(wrongAnsers);
                            user.setQuizesDone(quizesDone);
                            user.setQuizesMissed(quizesMissed);
                            user.setQi(qi);

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
        return user;
    }
}
