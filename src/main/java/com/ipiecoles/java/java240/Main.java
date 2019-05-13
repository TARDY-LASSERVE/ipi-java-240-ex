package com.ipiecoles.java.java240;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        ProduitManager pm = new ProduitManager();
        BitcoinService bitcoinServiceInCache = new BitcoinService();
        bitcoinServiceInCache.setForceRefresh(false);
        BitcoinService bitcoinServiceOutCache = new BitcoinService();
        bitcoinServiceOutCache.setForceRefresh(true);
        WebPageManager webPageManager = new WebPageManager();

        pm.setBitcoinService(bitcoinServiceInCache);
        pm.setWebPageManager(webPageManager);

        bitcoinServiceInCache.setWebPageManager(webPageManager);
        bitcoinServiceOutCache.setWebPageManager(webPageManager);

        System.out.println("Bienvenue !");
        while(true){
            System.out.println("Vous souhaitez : ");
            System.out.println("1 - Connaître le cours du bitcoin");
            System.out.println("2 - Ajouter un produit au catalogue");
            System.out.println("3 - Voir tous les produits du catalogue");
            System.out.println("4 - Voir les détails d'un produit");
            System.out.println("5 - Initialiser le catalogue");
            System.out.println("0 - Quitter");

            Scanner scanner = new Scanner(System.in);
            int saisie = scanner.nextInt();
            switch (saisie){
                case 1:
                    System.out.println("1 BTC = " + bitcoinServiceOutCache.getBitcoinRate() + " €");
                    break;
                case 2:
                    pm.ajouterProduit();
                    break;
                case 3:
                    pm.afficherTousLesProduits();
                    break;
                case 4:
                    System.out.println("Quel numéro de produit ?");
                    pm.afficherDetailProduit(scanner.nextInt());
                    break;
                case 5:
                    pm.initialiserCatalogue();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    return;
            }
        }
    }
}
