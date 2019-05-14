package com.ipiecoles.java.java240;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class BitcoinServiceTest {

    @InjectMocks //Mock injecté en entrée du test
    private BitcoinService bitcoinService;

    @Mock //Mock sur les dépendances
    private WebPageManager webPageManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this.getClass());
    }

    @Test
    public void getBitcoinRate() throws IOException {
        //Given
        Mockito.when(webPageManager.getPageContents(null)).thenReturn("{\"EUR\":7307.91}");

        //When = Exécution de la méthode à tester
        Double rate = bitcoinService.getBitcoinRate();

        //Then = Vérifications
        Assertions.assertThat(rate).isNotNull();
        Assertions.assertThat(rate).isEqualTo(7307.91d);

    }

}
