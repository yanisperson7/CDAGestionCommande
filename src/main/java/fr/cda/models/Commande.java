package fr.cda.models;

import java.util.*;

/**
 * Un bon de commande du site de vente.
 * Une commande est identifiée par son numéro et contient une liste de lignes
 * au format "reference=quantite". Elle est livrée ou non, et si elle n'a pas pu être livrée, un texte précise les produits manquants.
 */
public class Commande
{

    private int     numero;         // numero de la commande
    private String  date;           // date de la commande. Au format JJ/MM/AAAA
    private String  client;         // nom du client
    private ArrayList<String> references; // les references des produits de la commande
    private boolean livre;          // bon de commande livré ou non
    private String raisonPasLivre;  // raisons pour lesquelles le bon de commande n'a pas pu être livré

    /**
     * Crée une commande avec une liste de lignes donnée.
     * La commande n'est pas livrée et son motif de non-livraison est vide.
     *
     * @param numero     le numéro unique du bon de commande
     * @param date       la date de création (JJ/MM/AAAA)
     * @param client     le nom du client
     * @param references les lignes "reference=quantite"
     */
    public Commande(int numero, String date, String client, ArrayList<String> references) {
        this.numero = numero;
        this.date = date;
        this.client = client;
        this.references = references;
        this.livre = false;
        this.raisonPasLivre = "";
    }

    /**
     * Crée une commande sans ligne : la liste de références est créée vide.
     * Les lignes sont ajoutées ensuite, au fil de la lecture du fichier.
     *
     * @param numero le numéro unique du bon de commande
     * @param date   la date de création (JJ/MM/AAAA)
     * @param client le nom du client
     */
    public Commande(int numero, String date, String client) {
        this.numero = numero;
        this.date = date;
        this.client = client;
        this.references = new ArrayList<String>();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public ArrayList<String> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<String> references) {
        this.references = references;
    }

    public boolean isLivre() {
        return livre;
    }

    public void setLivre(boolean livre) {
        this.livre = livre;
    }

    public String getRaisonPasLivre() {
        return raisonPasLivre;
    }

    public void setRaisonPasLivre(String raisonPasLivre) {
        this.raisonPasLivre = raisonPasLivre;
    }

     /**
     * Retourne la commande sous forme de texte : numéro, date, client et références,
     * suivis d'une ligne de tirets.
     *
     * @return le texte de la commande
     */
    @Override
    public String toString() {
        return "Commande    : " + numero + "\n" +
                "Date       : " + date + "\n" +
                "Client     : " + client + "\n" +
                "RefProduits : \n" + references + "\n" +
                "--------------------------------\n";
    }
}