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

    public Commande(int numero, String date, String client, ArrayList<String> references) {
        this.numero = numero;
        this.date = date;
        this.client = client;
        this.references = references;
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

    @Override
    public String toString() {
        return "Commande{" +
                "numero=" + numero +
                ", date='" + date + '\'' +
                ", client='" + client + '\'' +
                ", references=" + references +
                '}';
    }
    //TODO vous devez coder le reste (constructeur, methodes ...)
}