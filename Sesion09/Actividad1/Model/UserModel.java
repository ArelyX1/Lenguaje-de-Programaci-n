package Sesion09.Actividad1.Model;

import Sesion09.Actividad1.Model.Interfaces.UsersRepo;
import java.util.*;

public class UserModel implements UsersRepo {
    private Map<String, UserInf> users;
    
    public UserModel()
    {
        users = new HashMap<>();
        inicializar();
    }
    private void inicializar()
    {
        users.put("ArelyXl", new UserInf("ArelyXl", "hp: 10\nDamage:40", "Developer", 97));
    }
    @Override
    public boolean addUser(UserInf user)
    {
        if(user == null || user.getUsername() == null || user.getUsername().isEmpty())
        {
            return false;
        }
        if(users.containsKey(user.getUsername()))
        {
            return false;
        }
        users.put(user.getUsername(), user);
        return true;
    }
    @Override
    public boolean removeUser(String username)
    {
        if(username == null || !users.containsKey(username))
        {
            return false;
        }
        users.remove(username);
        return true;
    }
    @Override
    public List<String> getUsers()
    {
        return new ArrayList<>(users.keySet());
    }
    @Override
    public UserInf getUserInfo(String username)
    {
        return users.get(username);
    }
}
