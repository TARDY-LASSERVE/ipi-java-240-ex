package com.ipiecoles.java.java240;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

@SpringBootApplication
/*
Inutile car SpringBoot gère automatiquement les annotations ci-dessous :
@Configuration
@ComponentScan(basePackages = "com.ipiecoles.java.java240")
@PropertySource("classpath:application.properties") devient inutile mais il faut que le fichier
se nomme bien application.properties et que ce dernier soit dans le dossier 'resources'
*/
public class SpringConfig {

    @Value("${bitcoinService.forceRefresh}")
    private Boolean strForceRefresh;

    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class, args);
    }

    @Bean(name = "bitcoinServiceWithoutCache")
    @Qualifier("WithoutCache")
    @Scope("singleton") // facultatif car valeur par défaut
    public BitcoinService bitcoinServiceWithoutCache(){
        BitcoinService bitcoinService = new BitcoinService();
        bitcoinService.setForceRefresh(true);
        return bitcoinService;
    }

    @Bean(name = "bitcoinServiceWithCache")
    @Qualifier("withCache")
    public BitcoinService bitcoinServiceWithCache(){
        BitcoinService bitcoinService = new BitcoinService();
        bitcoinService.setForceRefresh(strForceRefresh);
        //au lieu de
        //bitcoinService.setForceRefresh(Boolean.getBoolean(System.getProperty("bitcoinService.cache")));
        //OU au lieu de
        //bitcoinService.setForceRefresh(false);
        //comme ça l'initialisation de cette valeur est à un seul endroit dans toute l'appli !!
        return bitcoinService;
    }
//Dans ce cas-là, si j'y vais je reviens à lase départ puisque j'ai tourné en rond.
    //Emotion stressante et dévalorisante
}
