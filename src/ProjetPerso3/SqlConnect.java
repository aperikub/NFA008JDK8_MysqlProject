package ProjetPerso3;



import com.mysql.cj.jdbc.Driver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class SqlConnect implements ActionListener {

    //private Properties props = new Properties();
    private String url;

    //private FileInputStream fis = new FileInputStream(".\\src\\ProjetPerso3\\conf.properties");
    private Connection connection;
    private ConnectDialog dialog;
    private String socket;
    private String database;
    private String user;
    private String password;

    public SqlConnect() throws Exception {
        com.mysql.cj.jdbc.Driver driver = new Driver();
    }

    public Connection getConnection() {
        return connection;
    }

    public void openConnect(MyWindow owner){
        dialog = new ConnectDialog(this);
        dialog.pack();
        dialog.setVisible(true);
    }

    public void connect(String url,String user, String password) {
        try{
            connection = DriverManager.getConnection(url, user, password);
            if (connection.isValid(0)) {
                JOptionPane.showMessageDialog(null,"connexion réussie");
                dialog.dispose();
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null,"erreur de connexion","erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void EndConnection(){
        try {
            System.out.println("end connection");
            connection.close();
            if(connection.isClosed()){
                JOptionPane.showMessageDialog(null,"déconnexion réussie");
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"erreur de déconnexion","erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("dialog event");
        if (e.getSource() == dialog.getButtonOK()) {
            socket = dialog.getSocketField().getText();
            database = dialog.getDatabaseField().getText();
            user = dialog.getUserField().getText();
            password = dialog.getPasswordField().getText();
            url = "jdbc:mysql://localhost:" + dialog.getSocketField().getText() + "/" +
                    dialog.getDatabaseField().getText() + "?useSSL=false&serverTimezone=UTC";
            connect(url, dialog.getUserField().getText(), dialog.getPasswordField().getText());
        }
        else if(e.getSource() == dialog.getButtonDisconnect()){
            EndConnection();
        }
    }
}