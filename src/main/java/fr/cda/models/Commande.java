package fr.cda.models;

import java.util.*;

// Classe de definition d'une commande
//
public class Commande
{
    // Les caracteristiques d'une commande
    //
    private int     numero;         // numero de la commande
    private String  date;           // date de la commande. Au format JJ/MM/AAAA
    private String  client;         // nom du client
    private ArrayList<String> references; // les references des produits de la commande
    private boolean livre;          // bon de commande livré ou non
    private String raisonPasLivre;  // raisons pour lesquelles le bon de commande n'a pas pu être livré

    public Commande(int numero, String date, String client, ArrayList<String> references) {
        this.numero = numero;
        this.date = date;
        this.client = client;
        this.references = references;
        this.livre = false;
        this.raisonPasLivre = "";
    }

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

    @Override
    public String toString() {
        return "Commande    : " + numero + "\n" +
                "Date       : " + date + "\n" +
                "Client     : " + client + "\n" +
                "RefProduits : \n" + references + "\n" +
                "--------------------------------\n";
    }
    //TODO vous devez coder le reste (constructeur, methodes ...)
}