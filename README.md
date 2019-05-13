# TP Java 240, Spring et Spring Boot

L'objectif de ce TP est de comprendre l'intérêt d'un framework comme Spring en voyant les limitations courantes que l'on peut rencontrer, et de voir comment Spring adresse ces limitations.

## Introduction

- Exécutez cette application pour vous familiariser avec les différentes classes et lire la JavaDoc de chaque classe pour comprendre le rôle de chacune d'entre elles.
- Analysez cette application et définissez le graph de dépendances entre les classes (uniquement celles de votre projet, pas les dépendances externes). Il y a une dépendance entre deux classes lorsqu'une classe fait référence à une autre (par un appel de méthode par exemple).
Main.java dépend de ProduitManager.java et BitCoinService.java
ProduitManager.java dépend de BitCoinService.java, Produit.java et WebPageManager.java
BitCoinService.java dépend de WebPageManager.java

- Concernant la classe `BitcoinService`, comment la dépendance avec `WebPageManager` est-elle gérée ? 
Essayer d'optimiser (sans utiliser Spring). Faire de même pour la classe `ProduitManager` avec `BitcoinService` et `WebPageManager`. 

classe `BitcoinService`: A chaque appel de la méthode getBitCoinRate(...), il va créer l'objet WebPageManager. Du coup, pour optimiser, il est possible de déclarer l'objet new WebPageManager dans la classe et plus dans la méthode.

classe `ProduitManager` : A chaque appel de la méthode initialiserCatalogue(...), il va créer l'objet WebPageManager. A chaque appel de la méthode afficherDetailProduit(...), il va créer l'objet BitCoinService. Du coup, pour optimiser, il est possible de déclarer les objets new WebPageManager et new BitCoinService dans la classe et plus dans les méthodes. 

classe `Main`: A chaque fois que l'utilisateur fait le choix du '1 - Connaître le cours du bitcoin', le constructeur(...) va créer l'objet BitCoinService. Du coup, pour optimiser, il faut déclarer l'objet new BitCoinService en haut du constructeur.

- Qu'avez-vous gagner avec ces optimisations ? Quelle est la particularité de `BitcoinService`, `WebPageManager` et `ProduitManager` ? De combien d'instances de ces classes aurait-on en théorie besoin ? Est-ce le cas actuellement ?

Nous avons gagné du temps de retour de données et centraliser l'appel des objets dans le Main. Les objets WebPageManager, BitCoinService et ProduitManager seront créés et initialisés une seule fois.

- Committez/pushez !

## Ajout de Spring

- Créez une branche `spring` à partir de `master`
- Créez à l'aide de votre IDE un fichier `pom.xml` et ajoutez les dépendances nécessaires pour le framework Spring (version 5.1.6.RELEASE)
- Créez une classe de configuration pour Spring appelée `SpringConfig` et la placer dans le package `java240`
- Ajoutez la configuration nécessaire pour définir un bean en singleton pour la classe `BitcoinService`
- Modifiez la classe `Main` pour pouvoir utiliser ce bean dans la fonctionnalité de récupération du cours du bitcoin.
- Faites de même avec `ProduitManager`. Avez-vous résolu les problèmes soulevés dans l'introduction ?
- Faites en sorte que le `BitcoinService` utilisé dans `ProduitManager` soit le même que celui de la classe `Main`
- Faites gérer la classe `WebPageManager` par Spring et remplacez les instanciations manuelles dans `ProduitManager` et `BitcoinService`
- Créez un deuxième bean pour `BitcoinService` qui met l'attribut `forceRefresh` à `true` et utilisez ce dernier dans la fonctionnalité de récupération du cours du bitcoin. Vérifier que le site est systématiquement consulté pour la fonctionnalité de consultation du cours tandis que pour l'affichage des prix en bitcoin des produits, on utilise le cache à partir du deuxième appel.
- Faire en sorte que le catalogue soit initialisé systématiquement à l'instanciation du bean `ProduitManager`

## Automatisation !

- Créez une autre branche `spring-auto` à partir de `spring`
- Remplacez la déclaration manuelle des beans dans `SpringApplication` par un scan automatique de Spring en ajoutant les annotations nécessaires sur les différentes classes.
- Remplacez les injections effectuée manuellement par de l'autowiring.
- Vérifier que l'application fonctionne toujours.
- Créez un fichier de `properties` et définissez une propriété `bitcoinService.cache` valorisée à `false` et utilisez cette propriété pour construire le bean `BitcoinService` utilisé dans la classe `Main`. Constatez que cela fonctionne toujours et qu'en redémarrant votre application après avoir passé cette propriété à `true`, le cache est bien utilisé.

## Spring Boot

- Créez une autre branche `spring-auto` à partir de `spring-auto`
- Remplacez votre `pom.xml` par un `pom.xml` utilisant Spring Boot et convertissez votre application Spring normale en application Spring Boot console. Constatez la simplification (relative) du code et les possibilités maintenant à votre disposition !
