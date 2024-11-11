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
import tn.esprit.tpfoyer.repository.ChambreRepository;

@ContextConfiguration(classes = {ChambreServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ChambreServiceImplTest {
    @MockBean
    private ChambreRepository chambreRepository;

    @Autowired
    private ChambreServiceImpl chambreServiceImpl;

    /**
     * Method under test: {@link ChambreServiceImpl#retrieveAllChambres()}
     */
    @Test
    void testRetrieveAllChambres() {
        // Arrange
        ArrayList<Chambre> chambreList = new ArrayList<>();
        when(chambreRepository.findAll()).thenReturn(chambreList);

        // Act
        List<Chambre> actualRetrieveAllChambresResult = chambreServiceImpl.retrieveAllChambres();

        // Assert
        verify(chambreRepository).findAll();
        assertTrue(actualRetrieveAllChambresResult.isEmpty());
        assertSame(chambreList, actualRetrieveAllChambresResult);
    }

    /**
     * Method under test: {@link ChambreServiceImpl#retrieveChambre(Long)}
     */
    @Test
    void testRetrieveChambre() {
        // Arrange
        Universite universite = new Universite();
        universite.setAdresse("Adresse");
        universite.setFoyer(new Foyer());
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Nom Universite");

        Foyer foyer = new Foyer();
        foyer.setBlocs(new HashSet<>());
        foyer.setCapaciteFoyer(1L);
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Nom Foyer");
        foyer.setUniversite(universite);

        Bloc bloc = new Bloc();
        bloc.setCapaciteBloc(1L);
        bloc.setChambres(new HashSet<>());
        bloc.setFoyer(foyer);
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Nom Bloc");

        Chambre chambre = new Chambre();
        chambre.setBloc(bloc);
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(1L);
        chambre.setReservations(new HashSet<>());
        chambre.setTypeC(TypeChambre.SIMPLE);
        Optional<Chambre> ofResult = Optional.of(chambre);
        when(chambreRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Chambre actualRetrieveChambreResult = chambreServiceImpl.retrieveChambre(1L);

        // Assert
        verify(chambreRepository).findById(eq(1L));
        assertSame(chambre, actualRetrieveChambreResult);
    }

    /**
     * Method under test: {@link ChambreServiceImpl#addChambre(Chambre)}
     */
    @Test
    void testAddChambre() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setBlocs(new HashSet<>());
        foyer.setCapaciteFoyer(1L);
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Nom Foyer");
        foyer.setUniversite(new Universite());

        Universite universite = new Universite();
        universite.setAdresse("Adresse");
        universite.setFoyer(foyer);
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Nom Universite");

        Foyer foyer2 = new Foyer();
        foyer2.setBlocs(new HashSet<>());
        foyer2.setCapaciteFoyer(1L);
        foyer2.setIdFoyer(1L);
        foyer2.setNomFoyer("Nom Foyer");
        foyer2.setUniversite(universite);

        Bloc bloc = new Bloc();
        bloc.setCapaciteBloc(1L);
        bloc.setChambres(new HashSet<>());
        bloc.setFoyer(foyer2);
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Nom Bloc");

        Chambre chambre = new Chambre();
        chambre.setBloc(bloc);
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(1L);
        chambre.setReservations(new HashSet<>());
        chambre.setTypeC(TypeChambre.SIMPLE);
        when(chambreRepository.save(Mockito.<Chambre>any())).thenReturn(chambre);

        Universite universite2 = new Universite();
        universite2.setAdresse("Adresse");
        universite2.setFoyer(new Foyer());
        universite2.setIdUniversite(1L);
        universite2.setNomUniversite("Nom Universite");

        Foyer foyer3 = new Foyer();
        foyer3.setBlocs(new HashSet<>());
        foyer3.setCapaciteFoyer(1L);
        foyer3.setIdFoyer(1L);
        foyer3.setNomFoyer("Nom Foyer");
        foyer3.setUniversite(universite2);

        Bloc bloc2 = new Bloc();
        bloc2.setCapaciteBloc(1L);
        bloc2.setChambres(new HashSet<>());
        bloc2.setFoyer(foyer3);
        bloc2.setIdBloc(1L);
        bloc2.setNomBloc("Nom Bloc");

        Chambre c = new Chambre();
        c.setBloc(bloc2);
        c.setIdChambre(1L);
        c.setNumeroChambre(1L);
        c.setReservations(new HashSet<>());
        c.setTypeC(TypeChambre.SIMPLE);

        // Act
        Chambre actualAddChambreResult = chambreServiceImpl.addChambre(c);

        // Assert
        verify(chambreRepository).save(isA(Chambre.class));
        assertSame(chambre, actualAddChambreResult);
    }

    /**
     * Method under test: {@link ChambreServiceImpl#modifyChambre(Chambre)}
     */
    @Test
    void testModifyChambre() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setBlocs(new HashSet<>());
        foyer.setCapaciteFoyer(1L);
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Nom Foyer");
        foyer.setUniversite(new Universite());

        Universite universite = new Universite();
        universite.setAdresse("Adresse");
        universite.setFoyer(foyer);
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Nom Universite");

        Foyer foyer2 = new Foyer();
        foyer2.setBlocs(new HashSet<>());
        foyer2.setCapaciteFoyer(1L);
        foyer2.setIdFoyer(1L);
        foyer2.setNomFoyer("Nom Foyer");
        foyer2.setUniversite(universite);

        Bloc bloc = new Bloc();
        bloc.setCapaciteBloc(1L);
        bloc.setChambres(new HashSet<>());
        bloc.setFoyer(foyer2);
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Nom Bloc");

        Chambre chambre = new Chambre();
        chambre.setBloc(bloc);
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(1L);
        chambre.setReservations(new HashSet<>());
        chambre.setTypeC(TypeChambre.SIMPLE);
        when(chambreRepository.save(Mockito.<Chambre>any())).thenReturn(chambre);

        Universite universite2 = new Universite();
        universite2.setAdresse("Adresse");
        universite2.setFoyer(new Foyer());
        universite2.setIdUniversite(1L);
        universite2.setNomUniversite("Nom Universite");

        Foyer foyer3 = new Foyer();
        foyer3.setBlocs(new HashSet<>());
        foyer3.setCapaciteFoyer(1L);
        foyer3.setIdFoyer(1L);
        foyer3.setNomFoyer("Nom Foyer");
        foyer3.setUniversite(universite2);

        Bloc bloc2 = new Bloc();
        bloc2.setCapaciteBloc(1L);
        bloc2.setChambres(new HashSet<>());
        bloc2.setFoyer(foyer3);
        bloc2.setIdBloc(1L);
        bloc2.setNomBloc("Nom Bloc");

        Chambre c = new Chambre();
        c.setBloc(bloc2);
        c.setIdChambre(1L);
        c.setNumeroChambre(1L);
        c.setReservations(new HashSet<>());
        c.setTypeC(TypeChambre.SIMPLE);

        // Act
        Chambre actualModifyChambreResult = chambreServiceImpl.modifyChambre(c);

        // Assert
        verify(chambreRepository).save(isA(Chambre.class));
        assertSame(c, actualModifyChambreResult);
    }

    /**
     * Method under test: {@link ChambreServiceImpl#removeChambre(Long)}
     */
    @Test
    void testRemoveChambre() {
        // Arrange
        doNothing().when(chambreRepository).deleteById(Mockito.<Long>any());

        // Act
        chambreServiceImpl.removeChambre(1L);

        // Assert that nothing has changed
        verify(chambreRepository).deleteById(eq(1L));
    }

    /**
     * Method under test:
     * {@link ChambreServiceImpl#recupererChambresSelonTyp(TypeChambre)}
     */
    @Test
    void testRecupererChambresSelonTyp() {
        // Arrange
        ArrayList<Chambre> chambreList = new ArrayList<>();
        when(chambreRepository.findAllByTypeC(Mockito.<TypeChambre>any())).thenReturn(chambreList);

        // Act
        List<Chambre> actualRecupererChambresSelonTypResult = chambreServiceImpl
                .recupererChambresSelonTyp(TypeChambre.SIMPLE);

        // Assert
        verify(chambreRepository).findAllByTypeC(eq(TypeChambre.SIMPLE));
        assertTrue(actualRecupererChambresSelonTypResult.isEmpty());
        assertSame(chambreList, actualRecupererChambresSelonTypResult);
    }

    /**
     * Method under test:
     * {@link ChambreServiceImpl#trouverchambreSelonEtudiant(long)}
     */
    @Test
    void testTrouverchambreSelonEtudiant() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setBlocs(new HashSet<>());
        foyer.setCapaciteFoyer(1L);
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Nom Foyer");
        foyer.setUniversite(new Universite());

        Universite universite = new Universite();
        universite.setAdresse("Adresse");
        universite.setFoyer(foyer);
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Nom Universite");

        Foyer foyer2 = new Foyer();
        foyer2.setBlocs(new HashSet<>());
        foyer2.setCapaciteFoyer(1L);
        foyer2.setIdFoyer(1L);
        foyer2.setNomFoyer("Nom Foyer");
        foyer2.setUniversite(universite);

        Bloc bloc = new Bloc();
        bloc.setCapaciteBloc(1L);
        bloc.setChambres(new HashSet<>());
        bloc.setFoyer(foyer2);
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Nom Bloc");

        Chambre chambre = new Chambre();
        chambre.setBloc(bloc);
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(1L);
        chambre.setReservations(new HashSet<>());
        chambre.setTypeC(TypeChambre.SIMPLE);
        when(chambreRepository.trouverChselonEt(anyLong())).thenReturn(chambre);

        // Act
        Chambre actualTrouverchambreSelonEtudiantResult = chambreServiceImpl.trouverchambreSelonEtudiant(1L);

        // Assert
        verify(chambreRepository).trouverChselonEt(eq(1L));
        assertSame(chambre, actualTrouverchambreSelonEtudiantResult);
    }
}
