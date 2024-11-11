package Sesion09.Actividad1.View.components;

import javax.swing.*;
import java.awt.*;

public class UserEtiq extends JLabel {
    public UserEtiq()
    {
        super("Selecciona un usuario", SwingConstants.CENTER);
        setFont(new Font("Arial", Font.BOLD, 24));
    }
    public void updateText(String user){
        setText(user);
    }
}
