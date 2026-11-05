package fr.cda.projet;

import javax.swing.*;

public class EnvoiEmailThread implements Runnable {
    private final String fichier;

    public EnvoiEmailThread(String fichier) {
        this.fichier = fichier;
    }

    @Override
    public void run() {
        boolean ok = EnvoiMail.envoyer(fichier);

        String message;
        if (ok) {
            message = "Email envoyé avec succès";
        } else {
            message = "Échec de l'envoi de l'email";
        }
        JOptionPane.showMessageDialog(null, message);
    }
}
