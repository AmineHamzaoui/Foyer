package tn.esprit.tpfoyer.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

@ContextConfiguration(classes = {UniversiteServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UniversiteServiceImplTest {
    @MockBean
    private UniversiteRepository universiteRepository;


    @Autowired
    private UniversiteServiceImpl universiteServiceImpl;

    /**
     * Method under test: {@link UniversiteServiceImpl#retrieveAllUniversites()}
     */
    @Test
    void testretrieveAllUniversites() {
        // Arrange
        ArrayList<Universite> UniversiteList = new ArrayList<>();
        when(universiteRepository.findAll()).thenReturn(UniversiteList);

        // Act
        List<Universite> actualRetrieveAllUniversitesResult = universiteServiceImpl.retrieveAllUniversites();

        // Assert
        verify(universiteRepository).findAll();
        assertTrue(actualRetrieveAllUniversitesResult.isEmpty());
        assertSame(UniversiteList, actualRetrieveAllUniversitesResult);
    }

    /**
     * Method under test: {@link UniversiteServiceImpl#retrieveUniversite(Long)}
     */
    @Test
    void testRetrieveUniversite() {
        // Arrange
        Universite universite = new Universite();
        universite.setAdresse("Adresse");
        universite.setFoyer(new Foyer());
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Nom Universite");

        Optional<Universite> ofResult = Optional.of(universite);
        when(universiteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Universite actualRetrieveUniversiteResult = universiteServiceImpl.retrieveUniversite(1L);

        // Assert
        verify(universiteRepository).findById(eq(1L));
        assertSame(universite, actualRetrieveUniversiteResult);
    }

    /**
     * Method under test: {@link UniversiteServiceImpl#addUniversite(Universite)}
     */
    @Test
    void testAddUniversite() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setUniversite(new Universite());

        Universite universite = new Universite();
        universite.setAdresse("Adresse");
        universite.setFoyer(foyer);
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Nom Universite");


        when(universiteRepository.save(Mockito.<Universite>any())).thenReturn(universite);



        // Act
        Universite actualAddUniversiteResult = universiteServiceImpl.addUniversite(universite);

        // Assert
        verify(universiteRepository).save(isA(Universite.class));
        assertSame(universite, actualAddUniversiteResult);
    }


}