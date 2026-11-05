package fr.cda.projet;

import fr.cda.ihm.*;
import fr.cda.models.Commande;
import fr.cda.util.Terminal;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Fenêtre principale de l'application : affichage du stock et des commandes,
 * livraison, modification d'une commande, calcul des ventes et envoi par email.
 */
public class GUISite implements FormulaireInt
{
    private Site site;  // Le site

     /**
     * Crée et affiche la fenêtre principale avec ses boutons et la zone de résultats.
     *
     * @param site le site de vente affiché par la fenêtre
     */
    public GUISite(Site site)
    {
        this.site = site;

        // Creation du formulaire
        Formulaire form = new Formulaire("Site de vente",this,1100,730);
        
        //  Creation des elements de l'IHM
        //
        form.addLabel("Afficher tous les produits du stock");
        form.addButton("AFF_STOCK","Tous le stock");
        form.addLabel("");
        form.addLabel("Afficher tous les bons de commande");
        form.addButton("AFF_COMMANDES","Toutes les commandes");
        form.addLabel("");
        form.addText("NUM_COMMANDE","Numero de commande",true,"1");
        form.addButton("AFF_COMMANDE","Afficher");
        form.addLabel("");
        form.addButton("LIVRER", "Livrer");
        form.addButton("MODIFIER", "Modifier");
        form.addButton("CALCULER", "Calculer ventes");
        form.addButton("EMAIL", "Email");
        form.addButton("SAUVEGARDE", "Sauvegarde");

        form.setPosition(400,0);
        form.addZoneText("RESULTATS","Resultats",
                            true,
                            "",
                            600,700);

        // Affichage du formulaire
        form.afficher();
    }

    /**
     * Appelée à chaque clic sur un bouton : exécute l'action correspondante
     * et affiche le résultat dans la zone RESULTATS (ou dans une fenêtre modale
     * pour l'envoi d'email).
     * Le bouton Modifier n'ouvre la fenêtre de modification que si la
     * commande existe et n'est pas déjà livrée.
     *
     * @param form      le formulaire contenant le bouton
     * @param nomSubmit le nom du bouton cliqué (AFF_STOCK, LIVRER, MODIFIER...)
     */
    public void submit(Formulaire form,String nomSubmit)
    {

        // Affichage de tous les produits du stock
        //
        if (nomSubmit.equals("AFF_STOCK")) {
                String res = site.listerTousProduits();
                form.setValeurChamp("RESULTATS",res);
        }

        // Affichage de toutes les commandes
        //
        if (nomSubmit.equals("AFF_COMMANDES")) {
                String res = site.listerToutesCommandes();
                form.setValeurChamp("RESULTATS",res);
        }

        // Affichage d'une commande
        //
        if (nomSubmit.equals("AFF_COMMANDE")) {
            try {
                String numStr = form.getValeurChamp("NUM_COMMANDE");
                int num = Integer.parseInt(numStr.trim());
                String res = site.listerCommande(num);
                form.setValeurChamp("RESULTATS", res);
            } catch (NumberFormatException e) {
                form.setValeurChamp("RESULTATS", "Numéro de commande invalide : saisissez un nombre entier");
            }
        }

        //Affichage des commandes non livrées
        //
        if (nomSubmit.equals("LIVRER")) {
            ArrayList<Commande> nonLivrees = site.livraison();

            String res = "Les commandes non livrées :\n============================ \n";
            for (Commande c : nonLivrees) {
                res = res + c.toString() + c.getRaisonPasLivre() + "\n";
            }

            form.setValeurChamp("RESULTATS", res);
        }

        // Modification d'une commande non livrée
        //
        if (nomSubmit.equals("MODIFIER")) {
            try {
                int num = Integer.parseInt(form.getValeurChamp("NUM_COMMANDE").trim());
                Commande c = site.trouverCommande(num);

                if (c == null) {
                    form.setValeurChamp("RESULTATS", "Commande inexistante");
                } else if (c.isLivre()) {
                    form.setValeurChamp("RESULTATS", "Commande " + c.getNumero() + " déjà livrée : modification impossible");
                } else {
                    new GUIModifierCommande(c, site);
                }
            } catch (NumberFormatException e) {
                form.setValeurChamp("RESULTATS", "Numéro de commande invalide : saisissez un nombre entier");
            }
        }

        // Détail et somme des ventes
        //
        if (nomSubmit.equals("CALCULER")) {
            String res = site.calculerVentes();
            form.setValeurChamp("RESULTATS", "SOMME : " + res + " euros");
        }

        // Envoi de l'état par email
        //
        if (nomSubmit.equals("EMAIL")) {
            site.ecrireEtat();
            Thread t = new Thread(new EnvoiEmailThread("data/etat.txt"));
            t.start();
        }

        if (nomSubmit.equals("SAUVEGARDE")) {
            try {
                Path fichierCle = Path.of("data/cle.aes");

                SecretKey cle;
                if (Files.exists(fichierCle)) {
                    cle = ChiffrementAES.chargerCle(fichierCle);
                } else {
                    cle = ChiffrementAES.genererCle();
                    ChiffrementAES.sauvegarderCle(cle, fichierCle);
                }

                String[] lignes = Terminal.lireFichierTexte("data/Commandes.txt");
                String texte = String.join("\n", lignes);

                ChiffrementAES.chiffrer(texte, cle, Path.of("data/Commandes.aes"));
                JOptionPane.showMessageDialog(null, "Sauvegarde chiffrée créée");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Échec de la sauvegarde : " + e.getMessage());
            }
        }
    }
}