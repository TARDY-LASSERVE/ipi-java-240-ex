package com.ipiecoles.java.java240;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ProduitManager {

    private List<Produit> produits = new ArrayList<>();

    //NB : Si c'est la valeur qui s'affiche entre côtes et pas seulement la référence,
    //il suffit de supprimer au moins un caractère en fin de ligne pour que la référence
    //s'écrive de nouveau. Etrange ???
    @Value("${bitcoinService.urlCatalogue")
    private String urlCatalogue;

    @Autowired
    private WebPageManager webPageManager;

    @Autowired // OU @Resource(name = "bitcoinServiceWithCache") si la classe BitcoinService n'est pas en @Component et @Primary
    @Qualifier("withCache")
    private BitcoinService bitcoinServiceWithCache;

    public ProduitManager(){
        System.out.println("Instanciation du ProduitManager");
    }
    /**
     * Méthode qui demande les caractéristiques d'un nouveau produit
     * à l'utilisateur et qui l'ajoute au catalogue
     */
    public void ajouterProduit(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez l'intitulé du produit");
        String intitule = scanner.nextLine();
        if(produits.stream().
                map(Produit::getIntitule).
                anyMatch(s -> s.equals(intitule))){
            System.out.println("Ce produit existe déjà dans le catalogue !");
            return;
        }
        System.out.println("Entrez le prix du produit");
        Double prixEuro = scanner.nextDouble();

        produits.add(new Produit(intitule, prixEuro));
    }

    /**
     * Méthode qui affiche tous les produits du catalogue
     */
    public void afficherTousLesProduits(){
        produits.forEach(System.out::println);
    }

    /**
     * Méthode qui affiche les détails du produit du numéro passé en paramètre
     * et notamment le prix en bitcoin
     * @param index
     * @throws IOException
     */
    public void afficherDetailProduit(Integer index) throws IOException {
        System.out.println(produits.get(index).toString() + ", " + bitcoinServiceWithCache.getBitcoinPrice(produits.get(index).getPrixEuro()) + " BTC");
    }

    /**
     * Méthode qui initialise le catalogue à partir d'un fichier distant.
     * @throws IOException
     */
    @PostConstruct
    public void initialiserCatalogue() throws IOException {
        String catalogue = webPageManager.getPageContentsFromCacheIfExists(urlCatalogue);
        int nbProduits = 0;
        for(String line : catalogue.split("\n")){
            String[] elements = line.split(";");
            produits.add(new Produit(elements[0], Double.parseDouble(elements[1])));
            nbProduits++;
        }
        System.out.println("Ajout de " + nbProduits + " produits !");
    }

}
