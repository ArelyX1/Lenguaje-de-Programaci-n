package Sesion09.Actividad1.View.components;

import javax.swing.*;

public class ComboBox extends JComboBox{
    public ComboBox(String users[])
    {
        super(users);
    }
    public void updateComboBox(String[] users)
    {
        setModel(new DefaultComboBoxModel<>(users));
    }
}
