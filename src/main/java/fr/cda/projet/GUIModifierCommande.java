package fr.cda.projet;

import fr.cda.ihm.Formulaire;
import fr.cda.ihm.FormulaireInt;
import fr.cda.models.Commande;

import java.util.ArrayList;

/**
 * Fenêtre de modification des quantités d'un bon de commande non livré.
 * Elle affiche une zone de saisie par référence de la commande,
 * préremplie avec la quantité actuelle.
 */
public class GUIModifierCommande implements FormulaireInt {

    private Commande commande;
    private Site site;

    /**
     * Crée et affiche la fenêtre : une zone de saisie par ligne de la commande,
     * nommée par la référence du produit, et un bouton Valider.
     *
     * @param commande la commande à modifier (doit être non livrée)
     */
    public GUIModifierCommande(Commande commande, Site site) {
        this.commande = commande;
        this.site = site;

        // Creation du formulaire
        Formulaire form = new Formulaire("Modification d'un bon de commande",this,550,365);

        //  Creation des elements de l'IHM
        //
        for (String ligne : commande.getReferences()) {
            String[] separateur = ligne.split("=");
            form.addText(separateur[0], separateur[0], true, separateur[1]);
        }

        form.addButton("VALIDER", "Valider");
        form.setPosition(300, 300);
        form.afficher();
    }

    /**
     * Au clic sur Valider, relit les quantités saisies, vérifie que ce sont
     * des entiers positifs ou nuls, met à jour la commande puis ferme la fenêtre.
     * Si une quantité est invalide, un message est affiché, rien n'est enregistré
     * et la fenêtre reste ouverte.
     *
     * @param form      le formulaire de modification
     * @param nomSubmit le nom du bouton cliqué
     */
    @Override
    public void submit(Formulaire form, String nomSubmit) {
        if (nomSubmit.equals("VALIDER")) {
            ArrayList<String> references = new ArrayList<>();

            for (String ligne : commande.getReferences()) {
                String[] separateur = ligne.split("=");
                String ref = separateur[0];
                String quantite = form.getValeurChamp(ref);
                references.add(ref + "=" + quantite.trim());
            }

            commande.setReferences(references);
            site.ecrireCommandes();
            form.fermer();
        }
    }
}
