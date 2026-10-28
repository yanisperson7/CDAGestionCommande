// Projet 1 CDA
// 
// NOM,Prenom
// NOM,Prenom
//
package fr.cda.projet;

import fr.cda.models.Commande;
import fr.cda.util.*;

import java.util.ArrayList;

// Classe principale d'execution du projet
//
public class Projet
{
    public static void main(String a_args[])
    {
        Terminal.ecrireStringln("Execution du projet ");

        Site site = new Site();
        GUISite ihm = new GUISite(site);
        /*
        // Avant la livraison
        System.out.println(site.listerTousProduits());

        // Livraison
        ArrayList<Commande> nonLivrees = site.livraison();

        // Après la livraison
        for (Commande c : nonLivrees) {
            System.out.println(c);
            System.out.println(c.getRaisonPasLivre());
        }
        System.out.println(site.listerTousProduits());
        */
    }
}
