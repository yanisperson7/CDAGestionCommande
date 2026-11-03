// Projet 1 CDA
// 
// NOM,Prenom
// NOM,Prenom
//
package fr.cda.projet;

import fr.cda.models.Commande;
import fr.cda.util.*;

import java.util.ArrayList;

/**
 * Classe principale de l'application : crée le site de vente
 * affiche la fenêtre principale.
 */
public class Projet
{
    public static void main(String a_args[])
    {
        Terminal.ecrireStringln("Execution du projet ");

        Site site = new Site();
        GUISite ihm = new GUISite(site);
    }
}
