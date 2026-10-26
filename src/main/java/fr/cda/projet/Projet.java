// Projet 1 CDA
// 
// NOM,Prenom
// NOM,Prenom
//
package fr.cda.projet;

import fr.cda.util.*;

// Classe principale d'execution du projet
//
public class Projet
{
    public static void main(String a_args[])
    {
        Terminal.ecrireStringln("Execution du projet ");

        Site site = new Site();
        GUISite ihm = new GUISite(site);
    }
}
