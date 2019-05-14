package com.ipiecoles.java.java240;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;

//Pas @Component car ce n'est pas un singleton
// SINON
//Si @Component et @Primary alors l'appel dans ProduitManager.java devient un @Autowired et pas un @resource
@Component
@Primary
public class BitcoinService {

    private Double rate = null;

    private Boolean forceRefresh = false;

    //NB : Si c'est la valeur qui s'affiche entre côtes et pas seulement la référence, c'est l'IDE qui
    // précise la valeur afin que l'utilisateur càd moi puisse avoir la vraie valeur devant les yeux.
    // Pour voir la référence, il suffit de supprimer au moins un caractère en fin de ligne pour que la
    // référence s'écrive de nouveau. Bien penser à remettre le }
    @Value("${bitcoinService.urlApi}")
    private String urlApi;

    @Autowired
    private WebPageManager webPageManager;

    public void setForceRefresh(Boolean forceRefresh) {
        this.forceRefresh = forceRefresh;
    }


    public BitcoinService(){
        System.out.println("Instanciation du BitcoinService");
    }

    /**
     * Méthode qui renvoie le cours du Bitcoin
     * @return le cours du bitcoin
     * @throws IOException si impossible d'accéder à la bourse
     */
    public Double getBitcoinRate() throws IOException {
        if(rate != null && !forceRefresh){
            System.out.println("Récupération du cours du bitcoin en cache...");
            return rate;
        }

        System.out.println("Récupération du cours du bitcoin sur site distant");

        String apiResponse = webPageManager.getPageContents(urlApi);
        apiResponse = apiResponse.replace("{\"EUR\":","");
        apiResponse = apiResponse.replace("}","");
        rate = Double.parseDouble(apiResponse);
        return rate;
    }

    /**
     * Méthode qui renvoie l'équivalent en bitcoin du prix en euro passé en paramètre
     * @param prixEnEuro le prix à convertir
     * @return le prix en bitcoin au taux actuel
     * @throws IOException si impossible d'accéder à la bourse
     */
    public Double getBitcoinPrice(Double prixEnEuro) throws IOException {
        if(rate == null){
            getBitcoinRate();
        }
        return prixEnEuro / rate;
    }

}
