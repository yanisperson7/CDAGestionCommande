package fr.cda.models;

import java.util.*;

/**
 * Un produit du stock du site de vente.
 * Un produit est identifié par sa référence unique et utilisée dans les bons de commande.
 */
public class Produit
{
    private String  reference;      // reference du produit
    private String  nom;            // nom du produit
    private double  prix;           // prix du produit
    private int     quantite;       // quantit� du produit

    /**
     * Crée un produit.
     *
     * @param reference la référence unique du produit
     * @param nom       le nom du produit
     * @param prix      le prix en euros
     * @param quantite  la quantité en stock
     */
    public Produit(String reference,
                   String nom,
                   double prix,
                   int quantite)
    {
        this.reference = reference;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Retourne le produit sous forme de texte aligné en colonnes.
     *
     * @return la référence, le nom, le prix et la quantité du produit
     */
    public String toString()
    {
        return String.format("%-15s %-50s %3.2f   %3d",reference,nom,prix,quantite);
    }

}