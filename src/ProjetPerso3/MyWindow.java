package ProjetPerso3;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

public class MyWindow extends JFrame {


    public MyWindow() throws Exception{
        super("gestionnaire de bricolage");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(600,600));
        this.setPreferredSize(new Dimension(600,600));

        this.setLocationRelativeTo(null);

    }
}
