package pt.ipp.estg.formulafan.Interfaces;

public interface ISessionListener {
    void signIn(String email, String password);
    void register(String email, String password);
    void logout();
}
