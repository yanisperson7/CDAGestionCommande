package fr.cda.projet;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Envoi par email de l'état des commandes livrées, en pièce jointe.
 * L'envoi passe par le serveur SMTP de Gmail. Le mot de passe d'application
 * est lu dans la variable d'environnement MAIL_PASSWORD, afin de ne jamais
 * figurer dans le code source ni sur GitHub.
 */
public class EnvoiMail {
    /**
     * Envoie un mail avec le fichier donné en pièce jointe.
     * Le destinataire, l'objet et le corps sont définis par des variables.
     * L'envoi échoue (retourne false) si la variable d'environnement
     * MAIL_PASSWORD est absente, si le fichier est introuvable, si le réseau
     * est indisponible ou si Gmail refuse l'authentification.
     *
     * @param fichier le chemin du fichier à joindre (ex : data/etat.txt)
     * @return true si l'envoi a réussi, false sinon
     */
    public static boolean envoyer(String fichier) {
        Properties constantes = new Properties();

        try (FileInputStream flux = new FileInputStream("constantes.properties")) {
            constantes.load(flux);
        } catch (IOException e) {
            System.out.println("Impossible de lire constantes.properties : " + e.getMessage());
            return false;
        }

        String expediteur = constantes.getProperty("mail.expediteur");
        String destinataire = constantes.getProperty("mail.destinataire");
        String sujet = constantes.getProperty("mail.sujet");
        String corps = constantes.getProperty("mail.corps");
        String motDePasse = constantes.getProperty("mail.motdepasse");

        // Clé Valeur de type String
        Properties prop = new Properties();
        // Se connecter au serveur d'envoi
        prop.put("mail.smtp.host", "smtp.gmail.com"); // Adresse du serveur qui va envoyer le mail
        prop.put("mail.smtp.port", "465"); // Port de connexion SMTP du serveur
        prop.put("mail.smtp.auth", "true"); // Exige une authentification
        prop.put("mail.smtp.ssl.enable", "true"); // Active le chiffrement SSL de la connexion

        // Session de messagerie
        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(expediteur, motDePasse);
            }
        });

        try {
            // le texte
            MimeBodyPart texte = new MimeBodyPart();
            texte.setText(corps);

            // le fichier joint
            MimeBodyPart piece = new MimeBodyPart();
            piece.attachFile(fichier);

            // Conteneur de parties (morceaux dans le mail)
            Multipart contenu = new MimeMultipart();
            contenu.addBodyPart(texte);
            contenu.addBodyPart(piece);

            // Crée le message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(expediteur)); // Format valide
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire)); // Destinaitaires
            message.setSubject(sujet); // Objet
            message.setContent(contenu); // Pose le contenu

            // Ouverture de connexion, authentification et transmission du message
            Transport.send(message);
            return true;

        } catch (Exception e) {
            System.out.println("Erreur d'envoi : " + e.getMessage());
            return false;
        }
    }
}