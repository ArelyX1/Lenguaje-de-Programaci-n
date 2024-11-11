package Sesion09.Actividad1.Model.Interfaces;

import Sesion09.Actividad1.Model.UserInf;
import java.util.List;

public interface UsersRepo {
    boolean addUser(UserInf user);
    boolean removeUser(String username);
    List<String> getUsers();
    UserInf getUserInfo(String username);
}
