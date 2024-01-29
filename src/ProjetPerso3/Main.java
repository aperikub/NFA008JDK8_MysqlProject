package ProjetPerso3;

 /*COPYRIRIGHT:

  *tableau d'affichage des resultats de recherche (classe resultsTableModel.java): Dominique Liard de Koor.fr
   2020 © SARL Infini Software - Tous droits réservés

  */

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

public class Main  {


    public static void main(String[] args) throws Exception {
//        String in ="12";
//        String str = "test: %d";
//        System.out.println(String.format(str,Integer.parseInt(in)));
//        System.out.println(new File(".").getAbsolutePath());
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        MyWindow myWindow = new MyWindow();

        //JPanel contentPane = (JPanel) new Menu1PP3().getMenu1Proj3();
        TopMenu topMenu = new TopMenu();
        topMenu.getReturnToMain().setVisible(false);
        MenuPrincipal menu1 = new MenuPrincipal();
        myWindow.setJMenuBar(topMenu);

        myWindow.setVisible(true);
        myWindow.pack();
        /*ouverture du menu principal*/


        myWindow.setContentPane(menu1.getMenuPrincipal());
        myWindow.setMinimumSize(new Dimension(600,600));


        //ouverture de la connexion à la bd mysql
        SqlConnect sqlConnect = new SqlConnect();




        //bouton connexion
        menu1.getConnecterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlConnect.openConnect(myWindow);
                //menu1.getConnecterButton().setText("déconnexion");


            }

        });

        /* boutons du menu principal */

        menu1.getModifierButton().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    if (sqlConnect.getConnection() != null && sqlConnect.getConnection().isValid(0)) {

                        //topMenu.quitMain();
                        topMenu.getReturnToMain().setVisible(true);
                        topMenu.getReturnToMain().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                myWindow.setContentPane(menu1.getMenuPrincipal());
                                topMenu.getReturnToMain().setVisible(false);
                            }
                        });
                        MenuModifier menuModifier = new MenuModifier(sqlConnect.getConnection());
                        myWindow.setContentPane(menuModifier.getModifierMainPane());
                    } else {
                        JOptionPane.showMessageDialog(null, "vous n'êtes pas connecté à une base de données");
                    }
                }catch (SQLException err){
                    JOptionPane.showMessageDialog(null,err.toString());
                }
            }
        });

        menu1.getConsulterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (sqlConnect.getConnection() != null && sqlConnect.getConnection().isValid(0)) {

                        topMenu.getReturnToMain().setVisible(true);
                        topMenu.getReturnToMain().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                myWindow.setContentPane(menu1.getMenuPrincipal());
                                topMenu.getReturnToMain().setVisible(false);
                            }
                        });
                        System.out.println("test 1");
                        MenuConsulter menuConsulter = new MenuConsulter(sqlConnect.getConnection());
                        System.out.println("test 2");
                        myWindow.setContentPane(menuConsulter.getConsultPane());
                    } else {
                        JOptionPane.showMessageDialog(null, "vous n'êtes pas connecté à une base de données");
                    }
                }catch (SQLException err){
                    JOptionPane.showMessageDialog(null,err.toString());
                }
            }
        });

        menu1.getAjouterButton().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    if (sqlConnect.getConnection() != null && sqlConnect.getConnection().isValid(0)) {

                        topMenu.getReturnToMain().setVisible(true);
                        topMenu.getReturnToMain().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                myWindow.setContentPane(menu1.getMenuPrincipal());
                                topMenu.getReturnToMain().setVisible(false);
                            }
                        });
                        /*menu ajouter une tache ou un materiel*/
                        MenuAjouter menuAjouter = new MenuAjouter();
                        myWindow.setContentPane(menuAjouter.getPanelAjouter());

                        menuAjouter.getAjouterTacheButton().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                /*ouverture du menu ajouter une tache*/
                                AjouterTache ajouterTache = new AjouterTache(sqlConnect.getConnection());
                                myWindow.setContentPane(ajouterTache.getPanelAjouterTache());
                                ajouterTache.getValiderButton().addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        try{
                                            int affectedRows = ajouterTache.ajouterTacheQuery();

                                            if(affectedRows ==0){
                                                JOptionPane.showMessageDialog(null,"aucun changement effectué");
                                            }
                                            else{
                                                JOptionPane.showMessageDialog(null,String.format("%d colonne(s) affectée(s)", affectedRows));

                                            }
                                        } catch (SQLException err){
                                            JOptionPane.showMessageDialog(null, err.toString());
                                        }
                                        ajouterTache.getAjouterMaterielAssoButton().setVisible(true);
                                        ajouterTache.getAjouterMaterielAssoButton().addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                int id = 0;
                                                String nomTache = ajouterTache.getNomTacheField().getText();
                                                AjoutMaterielAssocie ajoutMaterielAssocie;
                                                try{
                                                    id = ajouterTache.setId();
                                                    ajoutMaterielAssocie = new AjoutMaterielAssocie(sqlConnect.getConnection(), id, nomTache);
                                                }catch(SQLException err){
                                                    JOptionPane.showMessageDialog(null,err.toString());
                                                    ajoutMaterielAssocie = new AjoutMaterielAssocie(sqlConnect.getConnection());
                                                }

                                                myWindow.setContentPane(ajoutMaterielAssocie.getMaterielAssocPane());
                                            }
                                        });
                                    }
                                });
                            }
                        });
                        menuAjouter.getAjouterMaterielButton().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                /*menu ajouter materiel*/
                                AjouterMateriel ajouterMateriel = new AjouterMateriel();
                                myWindow.setContentPane(ajouterMateriel.getAjouterMaterielPanel());
                                ajouterMateriel.getValider().addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        try{
                                            int[] affectedRows = ajouterMateriel.ajouterMaterielQuery(sqlConnect.getConnection());
                                            int materielResult = affectedRows[0];
                                            int stockResult = affectedRows[1];
                                            if(materielResult ==0 && stockResult == 0){
                                                JOptionPane.showMessageDialog(null,"aucun changement effectué");
                                            }
                                            else if (stockResult > 0 && materielResult != 0){
                                                JOptionPane.showMessageDialog(null,String.format("%d colonne(s) materiel affectée(s) \n%d colonne(s) stock affectées.",
                                                        materielResult, stockResult));
                                            }
                                            else if(materielResult !=0 && stockResult == -1){
                                                JOptionPane.showMessageDialog(null,String.format("%d colonne(s) materiel affectée(s).", materielResult));
                                            }
                                        } catch (SQLException err){
                                            JOptionPane.showMessageDialog(null, err.toString());
                                        }
                                    }
                                });
                            }
                        });//fin du listener menu ajouter materiel

                        menuAjouter.getAssocierMaterielButton().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                AjoutMaterielAssocie ajoutMaterielAssocie = new AjoutMaterielAssocie(sqlConnect.getConnection());
                                myWindow.setContentPane(ajoutMaterielAssocie.getMaterielAssocPane());
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "vous n'êtes pas connecté à une base de données");
                    }
                }catch (SQLException err){
                    JOptionPane.showMessageDialog(null,err.toString());
                }
            }
        });//fin du listener menu ajouter

        menu1.getRequestButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    if (sqlConnect.getConnection() != null && sqlConnect.getConnection().isValid(0)) {

                        topMenu.getReturnToMain().setVisible(true);
                        topMenu.getReturnToMain().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                myWindow.setContentPane(menu1.getMenuPrincipal());
                                topMenu.getReturnToMain().setVisible(false);
                            }
                        });
                        RequeteCustom requeteCustom = new RequeteCustom(sqlConnect.getConnection());
                        myWindow.setContentPane(requeteCustom.getRequetePane());
                        requeteCustom.getRequetePane().updateUI();
                    } else {
                        JOptionPane.showMessageDialog(null, "vous n'êtes pas connecté à une base de données");
                    }
                }catch (SQLException err){
                    JOptionPane.showMessageDialog(null,err.toString());
                }

            }
        });

        menu1.getQuitterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sqlConnect.getConnection() == null){
                    System.exit(0);
                }
                else {
                    sqlConnect.EndConnection();
                    System.exit(0);
                }
            }
        });
        topMenu.getQuit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sqlConnect.getConnection() == null){
                    System.exit(0);
                }
                else {
                    sqlConnect.EndConnection();
                    System.exit(0);
                }
            }
        });
    }


}
