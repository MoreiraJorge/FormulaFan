package pt.ipp.estg.formulafan.WebServices;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import pt.ipp.estg.formulafan.Models.QuizDone;

public class QuizDoneFirestoreService {

    private static final String TAG = "Test firestore";

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void addQuizToFirestore(QuizDone quiz) {
        db.collection("quizzesDone")
                .add(quiz)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
