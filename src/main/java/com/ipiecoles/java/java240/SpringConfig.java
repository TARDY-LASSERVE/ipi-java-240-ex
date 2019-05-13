package com.ipiecoles.java.java240;

import org.springframework.context.annotation.*;

@Configuration
// L'annotation @ComponentScan permet de dire à Spring de chercher les beans dans
// toutes les classes de ce package.
@ComponentScan(basePackages = "com.ipiecoles.java.java240")
public class SpringConfig {

    @Bean(name = "bitcoinServiceWithoutCache")
    @Scope("singleton") // facultatif car valeur par défaut
    public BitcoinService bitcoinServiceWithoutCache(){
        BitcoinService bitcoinService = new BitcoinService();
        bitcoinService.setForceRefresh(true);
        return bitcoinService;
    }

    @Bean(name = "bitcoinServiceWithCache")
    public BitcoinService bitcoinServiceWithCache(){
        BitcoinService bitcoinService = new BitcoinService();
        bitcoinService.setForceRefresh(Boolean.getBoolean(System.getProperty("bitcoinService.cache")));
        return bitcoinService;
    }

}
