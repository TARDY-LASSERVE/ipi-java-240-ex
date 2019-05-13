package com.ipiecoles.java.java240;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;

@Configuration
public class SpringConfig {
    @Bean(name = "bitcoinServiceWithoutCache")
    @Scope("singleton") // facultatif car valeur par défaut
    public BitcoinService bitcoinServiceWithoutCache(){
        //NB : Etant donné que le BitcoinService a forcément besoin d'un WebPageManager pour fonctionner
        // Il serait pertinent de mettre le set directement dans le constructeur du BitcoinManager
        BitcoinService bitcoinService = new BitcoinService();
        bitcoinService.setForceRefresh(true);
        bitcoinService.setWebPageManager(webPageManager());
        return bitcoinService;
    }


    @Bean(name = "bitcoinServiceWithCache")
    public BitcoinService bitcoinServiceWithCache(){
        //NB : Etant donné que le BitcoinService a forcément besoin d'un WebPageManager pour fonctionner
        // Il serait pertinent de mettre le set directement dans le constructeur du BitcoinManager
        BitcoinService bitcoinService = new BitcoinService();
        bitcoinService.setForceRefresh(false);
        bitcoinService.setWebPageManager(webPageManager());
        return bitcoinService;
    }

    //Ce Bean est un singleton. Du coup, les 2 beans de BitcoinService utiliserons le même webPageManager.
    @Bean
    public WebPageManager webPageManager(){
        return new WebPageManager();
    }

    @Bean(name = "produitManager")
    public ProduitManager produitManager() throws IOException {
        ProduitManager produitManager = new ProduitManager();
        produitManager.setWebPageManager(webPageManager());
        produitManager.setBitcoinService(bitcoinServiceWithCache());
        return produitManager;
    }


    @Bean(name = "initCatalogue")
    public ProduitManager produitManagerCatalogue() throws IOException {
        ProduitManager produitManager = new ProduitManager();
        produitManager.setWebPageManager(webPageManager());
        produitManager.setBitcoinService(bitcoinServiceWithCache());
        produitManager.initialiserCatalogue();
        return produitManager;
    }
}
