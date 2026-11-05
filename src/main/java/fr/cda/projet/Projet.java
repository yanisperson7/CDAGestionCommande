// Projet 1 CDA
// 
// NOM,Prenom
// NOM,Prenom
//
package fr.cda.projet;

import fr.cda.util.*;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Classe principale de l'application : crée le site de vente
 * affiche la fenêtre principale.
 */
public class Projet
{
    public static void main(String a_args[]) throws IOException, GeneralSecurityException {
        Terminal.ecrireStringln("Execution du projet ");

        Site site = new Site();
        GUISite ihm = new GUISite(site);
    }
}
