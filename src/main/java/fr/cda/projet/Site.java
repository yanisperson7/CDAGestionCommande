package fr.cda.projet;

import java.awt.List;
import java.util.*;

import fr.cda.models.Commande;
import fr.cda.models.Produit;
import fr.cda.util.*;

// Classe de definition du site de vente
//
public class Site
{
    private ArrayList<Produit> stock;       // Les produits du stock
    private ArrayList<Commande> commandes;  // Les bons de commande

    // Constructeur
    //
    public Site()
    {
        stock = new ArrayList<Produit>();
        commandes = new ArrayList<Commande>();

        // acces à la bdd
        //  pour chaque ligne on cree un Produit que l'on ajoute a stock
        initialiserStock("data/Produits.txt");

        // acces à la bdd
        //  pour chaque ligne on cree une commande ou on ajoute une reference
        initialiserCommande("data/Commandes.txt");
        // AC AC 
        
    }
    
    // Methode qui retourne sous la forme d'une chaine de caractere
    //  tous les produits du stock
    //
    public String listerTousProduits()
    {
        String res="";
        for(Produit prod : stock)
            res=res+prod.toString()+"\n";

        return res;
    }

    // Methode qui retourne sous la forme d'une chaine de caractere
    //  toutes les commandes
    //
    public String listerToutesCommandes()
    {
        String res="";
        for (Commande commande : commandes) {
            res=res+commande.toString();
        }
        //res=res+"Les commandes sont concatenes dans une chaine";

        return res;
    }

    /** Retourne la commande de ce numéro, ou null si elle n'existe pas. */
    public Commande trouverCommande(int numero) {
        for (Commande c : commandes) {
            if (c.getNumero() == numero) {
                return c;
            }
        }
        return null;
    }

    // Methode qui retourne sous la forme d'une chaine de caractere
    //  une commande
    //
    public String listerCommande(int numero) {

        Commande commande = trouverCommande(numero);

        if (commande == null) {
            return "Commande " + numero + " inexistante";
        }

        return commande.toString();
    }

    // Chargement des données du stock
    //
    private void initialiserStock(String nomFichier)
    {
        String[] lignes = Terminal.lireFichierTexte(nomFichier);
        if (lignes == null) {
            System.out.println("Le fichier n'a pas pu être lu");
            return;
        }

        for (String ligne : lignes) {
            // ignore les lignes vides et les espaces en trop
            if (ligne.trim().isEmpty()) {
                continue;
            }

            String[] champs = ligne.split(";");

            try {
                String reference = champs[0].trim();
                String nom = champs[1].trim();
                double prix = Double.parseDouble(champs[2].trim());
                int quantite = Integer.parseInt(champs[3].trim());

                Produit p = new Produit(reference, nom, prix, quantite);
                stock.add(p);
            } catch (NumberFormatException e) {
                System.out.println("Ligne mal écrite (nombre invalide) : " + ligne);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Ligne mal écrite (champs manquants) : " + ligne);
            }
        }
    }

    private void initialiserCommande(String nomFichier)
    {
        String[] lignes = Terminal.lireFichierTexte(nomFichier);
        if (lignes == null) {
            System.out.println("Le fichier n'a pas pu être lu");
            return;
        }

        for (String ligne : lignes) {
            // ignore les lignes vides et les espaces en trop
            if (ligne.trim().isEmpty()) {
                continue;
            }

            String[] champs = ligne.split(";");

            try {
                int numero = Integer.parseInt(champs[0].trim());
                String date = champs[1].trim();
                String client = champs[2].trim();

                Commande c = trouverCommande(numero);
                if (c == null) {
                    c = new Commande(numero, date, client);
                    commandes.add(c);
                }

                c.getReferences().add(champs[3].trim());

            } catch (NumberFormatException e) {
                System.out.println("Ligne mal écrite (nombre invalide) : " + ligne);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Ligne mal écrite (champs manquants) : " + ligne);
            }
        }
    }
}