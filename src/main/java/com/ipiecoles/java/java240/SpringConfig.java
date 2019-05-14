package com.ipiecoles.java.java240;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
// L'annotation @ComponentScan permet de dire à Spring de chercher les beans dans
// toutes les classes de ce package.
@ComponentScan(basePackages = "com.ipiecoles.java.java240")
@PropertySource("classpath:application.properties")
public class SpringConfig {

    @Value("${bitcoinService.forceRefresh}")
    private Boolean strForceRefresh;

    @Bean(name = "bitcoinServiceWithoutCache")
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
