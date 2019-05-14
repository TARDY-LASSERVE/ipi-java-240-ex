package com.ipiecoles.java.java240;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/produits")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;


    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Iterable<Produit> findAll() {
        return produitRepository.findAll();
    }
}
