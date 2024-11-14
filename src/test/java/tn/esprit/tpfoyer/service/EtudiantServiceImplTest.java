package tn.esprit.tpfoyer.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

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
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

@ContextConfiguration(classes = {EtudiantServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class EtudiantServiceImplTest {
    @MockBean
    private EtudiantRepository etudiantRepository;


    @Autowired
    private EtudiantServiceImpl etudiantServiceImpl;

    /**
     * Method under test: {@link EtudiantServiceImpl#retrieveAllEtudiants()}
     */
    @Test
    void testretrieveAllEtudiants() {
        // Arrange
        ArrayList<Etudiant> EtudiantList = new ArrayList<>();
        when(etudiantRepository.findAll()).thenReturn(EtudiantList);

        // Act
        List<Etudiant> actualRetrieveAllEtudiantsResult = etudiantServiceImpl.retrieveAllEtudiants();

        // Assert
        verify(etudiantRepository).findAll();
        assertTrue(actualRetrieveAllEtudiantsResult.isEmpty());
        assertSame(EtudiantList, actualRetrieveAllEtudiantsResult);
    }

    /**
     * Method under test: {@link EtudiantServiceImpl#retrieveEtudiant(Long)}
     */
    @Test
    void testRetrieveEtudiant() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("Nom Etudiant");
        etudiant.setPrenomEtudiant("prenomEtudiant");
        etudiant.setCinEtudiant((long)8);
        etudiant.setDateNaissance(new Date());
        etudiant.setIdEtudiant(1L);


        Optional<Etudiant> ofResult = Optional.of(etudiant);
        when(etudiantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Etudiant actualRetrieveEtudiantResult = etudiantServiceImpl.retrieveEtudiant(1L);

        // Assert
        verify(etudiantRepository).findById(eq(1L));
        assertSame(etudiant, actualRetrieveEtudiantResult);
    }

    /**
     * Method under test: {@link EtudiantServiceImpl#addEtudiant(Etudiant)}
     */
    @Test
    void testAddEtudiant() {
        // Arrange


        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("Nom Etudiant");
        etudiant.setPrenomEtudiant("prenomEtudiant");
        etudiant.setCinEtudiant((long)8);
        etudiant.setDateNaissance(new Date());
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("Nom Etudiant");


        when(etudiantRepository.save(Mockito.<Etudiant>any())).thenReturn(etudiant);



        // Act
        Etudiant actualAddEtudiantResult = etudiantServiceImpl.addEtudiant(etudiant);

        // Assert
        verify(etudiantRepository).save(isA(Etudiant.class));
        assertSame(etudiant, actualAddEtudiantResult);
    }


}