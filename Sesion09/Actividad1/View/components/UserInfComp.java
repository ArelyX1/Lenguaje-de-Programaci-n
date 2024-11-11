package Sesion09.Actividad1.View.components;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.*;

public class UserInfComp extends JPanel {
    private JTextArea areaInf;

    public UserInfComp()
    {
        setLayout(new BorderLayout());
        areaInf = new JTextArea();
        areaInf.setEditable(false);
        areaInf.setLineWrap(true);
        areaInf.setWrapStyleWord(true);

        JScrollPane scrollBar = new JScrollPane(areaInf);
        add(scrollBar, BorderLayout.CENTER);
    }
    public void viewInf(String inf)
    {
        areaInf.setText(inf);
    }
}
