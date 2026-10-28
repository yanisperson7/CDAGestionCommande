package fr.cda.projet;

import fr.cda.ihm.Formulaire;
import fr.cda.ihm.FormulaireInt;
import fr.cda.models.Commande;

import java.util.ArrayList;

public class GUIModifierCommande implements FormulaireInt {

    private Commande commande;

    public GUIModifierCommande(Commande commande) {
        this.commande = commande;

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
            form.fermer();
        }
    }
}
