package fr.cda.projet;

import java.awt.List;
import java.util.*;

import fr.cda.models.Commande;
import fr.cda.models.Produit;
import fr.cda.util.*;

/**
 * Le site de vente : il gère le stock de produits et les bons de commande.
 * Les données sont lues dans les fichiers data/Produits.txt et data/Commandes.txt.
 */
public class Site
{
    private ArrayList<Produit> stock;       // Les produits du stock
    private ArrayList<Commande> commandes;  // Les bons de commande

    /**
     * Crée le site et charge les produits et les commandes depuis les fichiers.
     */
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
    }
    
    // Methode qui retourne sous la forme d'une chaine de caractere
    // tous les produits du stock
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

        return res;
    }

    /**
     * Cherche une commande par son numéro.
     *
     * @param numero le numéro recherché
     * @return la commande trouvée, ou null si elle n'existe pas
     */
    public Commande trouverCommande(int numero) {
        for (Commande c : commandes) {
            if (c.getNumero() == numero) {
                return c;
            }
        }
        return null;
    }

    /**
     * Cherche un produit du stock par sa référence.
     *
     * @param reference la référence recherchée
     * @return le produit trouvé, ou null si la référence est inconnue
     */
    public Produit trouverProduit(String reference) {
        for (Produit p : stock) {
            if (p.getReference().equals(reference)) {
                return p;
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

    /**
     * Lit le fichier des produits et remplit le stock.
     * Les lignes vides sont ignorées ; les lignes mal formées sont
     * signalées dans la console et ne sont pas ajoutées.
     *
     * @param nomFichier le chemin du fichier (ex : data/Produits.txt)
     */
    public void initialiserStock(String nomFichier)
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

    /**
     * Lit le fichier des commandes. Une ligne correspond à un produit d'une
     * commande : la commande est créée si elle n'existe pas encore, puis la
     * ligne "reference=quantite" lui est ajoutée.
     *
     * @param nomFichier le chemin du fichier (ex : data/Commandes.txt)
     */
    public void initialiserCommande(String nomFichier)
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

    /**
     * Livre les commandes non encore livrées, dans l'ordre du fichier.
     * Une commande n'est livrée que si tous ses produits sont disponibles :
     * le stock est alors diminué. Sinon, les produits manquants sont
     * enregistrés dans la commande et le stock n'est pas modifié.
     * Les commandes traitées en premier réduisent le stock disponible
     * pour les suivantes.
     *
     * @return la liste des commandes qui n'ont pas pu être livrées
     */
    public ArrayList<Commande> livraison() {
        ArrayList<Commande> nonLivrees = new ArrayList<>();

        for (Commande c : commandes) {
            if(c.isLivre()) {
                continue;
            }

            String manque = "";

            // Partie 1 : vérification, sans toucher au stock
            for (String ligne : c.getReferences()) {
                String[] separateur = ligne.split("=");
                String reference = separateur[0];
                int quantiteDemande = Integer.parseInt(separateur[1]);
                Produit p = trouverProduit(reference);

                if (quantiteDemande > p.getQuantite()) {
                    manque = manque + "Il manque " + (quantiteDemande - p.getQuantite()) + " " + reference + "\n";
                }
            }

            // Partie 2 : si rien ne manque, on diminue le stock et on livre
            if (manque.equals("")) {
                for(String ligne : c.getReferences()) {
                    String[] separateur = ligne.split("=");
                    Produit p = trouverProduit(separateur[0]);
                    int quantiteDemande = Integer.parseInt(separateur[1]);
                    p.setQuantite(p.getQuantite() - quantiteDemande);
                }
                c.setLivre(true);
            } else {
                c.setRaisonPasLivre(manque);
                nonLivrees.add(c);
            }
        }
        return nonLivrees;
    }

    /**
     * Construit le détail des commandes livrées (nom, quantité et prix de
     * chaque produit) et calcule la somme des ventes en euros.
     *
     * @return le texte du détail, suivi de la ligne "SOMME : ... euros"
     */
    public String calculerVentes() {
        double total = 0;
        String res = "";

        for (Commande c : commandes) {
            if (c.isLivre()) {
                res = res + "COMMANDE : " + c.getNumero() + "\n";

                for (String ligne : c.getReferences()) {
                    String[] separateur = ligne.split("=");
                    int quantite = Integer.parseInt(separateur[1]);
                    Produit p = trouverProduit(separateur[0]);

                    res = res + p.getNom() + "   " + quantite + "   " + p.getPrix() + "\n";
                    total = total + p.getPrix() * quantite;
                }
                res = res + "\n";
            }
        }

        res = res + "====================\n";
        res = res + "SOMME : " + total + " euros";
        return res;
    }

    /**
     * Écrit dans le fichier data/etat.txt l'état des commandes livrées.
     * Ce fichier est ensuite joint à l'email.
     */
    public void ecrireEtat() {
        String texte = calculerVentes();
        Terminal.ecrireFichier("data/etat.txt", new StringBuffer(texte));
    }

    public void ecrireCommandes() {
        String texte = "";

        for (Commande c : commandes) {
            for (String reference : c.getReferences()) {
                texte = texte + c.getNumero() + ";" + c.getDate() + ";" + c.getClient() + ";" + reference + "\n";
            }
        }

        Terminal.ecrireFichier("data/Commandes.txt", new StringBuffer(texte));
    }
}