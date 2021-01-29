package pt.ipp.estg.formulafan.Interfaces;

public interface ISessionListener {
    public void signIn(String email, String password);
    public void register(String email, String password);
    public void logout();
}
