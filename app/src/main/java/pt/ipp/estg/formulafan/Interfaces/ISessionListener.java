package pt.ipp.estg.formulafan.Interfaces;

import android.widget.EditText;

public interface ISessionListener {
    void signIn(String email, String password);

    void register(EditText userName, EditText email, EditText password);
}
