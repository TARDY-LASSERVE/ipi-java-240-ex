package com.ipiecoles.java.java240;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ProduitManager {

    private List<Produit> produits = new ArrayList<>();

    //NB : Si c'est la valeur qui s'affiche entre côtes et pas seulement la référence, il suffit de
    // supprimer au moins un caractère en fin de ligne pour que la référence s'écrive de nouveau.
    // Bien penser à remettre le }
    // En fait, L'IDE précise la valeur afin que l'utilisateur càd moi puisse avoir la vraie valeur
    // devant les yeux
    @Value("${bitcoinService.urlCatalogue}")
    private String urlCatalogue;

    @Autowired
    private WebPageManager webPageManager;

    @Autowired
    private ProduitRepository produitRepository; //Ajout du rapport entre la classe Produit et la bdd en mémoire vive (h2)

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
        produitRepository.saveAll(produits);
        System.out.println("Ajout de " + nbProduits + " produits !");
        System.out.println("Ajout en BDD mémoire de " + produitRepository.count() + " produits !");
    }

}
