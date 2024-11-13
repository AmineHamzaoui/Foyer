package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlocServiceImplTest {
    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService; // Assuming retrieveAllBlocs() is inside a class named BlocService

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


        @Test
        void testRetrieveAllBlocs() {
            // Step 1: Prepare Mock Data
            Foyer foyer = new Foyer();
            foyer.setIdFoyer(1L);
            foyer.setNomFoyer("Foyer 1");

            Bloc bloc1 = new Bloc();
            bloc1.setIdBloc(1L);
            bloc1.setNomBloc("Bloc A");
            bloc1.setCapaciteBloc(50);
            bloc1.setFoyer(foyer);
            bloc1.setChambres(new HashSet<>()); // Empty set of chambres

            Bloc bloc2 = new Bloc();
            bloc2.setIdBloc(2L);
            bloc2.setNomBloc("Bloc B");
            bloc2.setCapaciteBloc(100);
            bloc2.setFoyer(foyer);
            bloc2.setChambres(new HashSet<>()); // Empty set of chambres

            List<Bloc> mockBlocs = Arrays.asList(bloc1, bloc2);

            // Step 2: Mock the behavior of the blocRepository
            when(blocRepository.findAll()).thenReturn(mockBlocs);

            // Step 3: Call the method under test
            List<Bloc> result = blocService.retrieveAllBlocs();

            // Step 4: Assertions
            assertNotNull(result);  // Verify the result is not null
            assertEquals(2, result.size());  // Verify the size of the result list
            assertEquals(bloc1, result.get(0));  // Verify the first Bloc is as expected
            assertEquals(bloc2, result.get(1));  // Verify the second Bloc is as expected

            // Step 5: Verify repository interactions
            verify(blocRepository, times(1)).findAll(); // Ensure findAll() was called exactly once


        }


@Test
void testRetrieveBlocsSelonCapacite() {
            Bloc bloc1 = new Bloc();
            bloc1.setIdBloc(1L);
            bloc1.setNomBloc("Bloc A");
            bloc1.setCapaciteBloc(50);

            Bloc bloc2 = new Bloc();
            bloc2.setIdBloc(2L);
            bloc2.setNomBloc("Bloc B");
            bloc2.setCapaciteBloc(100);

            Bloc bloc3 = new Bloc();
            bloc3.setIdBloc(3L);
            bloc3.setNomBloc("Bloc C");
            bloc3.setCapaciteBloc(150);

            // Mock list of all blocs
            List<Bloc> mockBlocs = Arrays.asList(bloc1, bloc2, bloc3);

            // Step 2: Mock the behavior of the blocRepository
            when(blocRepository.findAll()).thenReturn(mockBlocs);

            // Step 3: Define the capacity threshold
            long capacityThreshold = 100;

            // Step 4: Call the method under test
            List<Bloc> result = blocService.retrieveBlocsSelonCapacite(capacityThreshold);

            // Step 5: Assertions
            assertNotNull(result);  // Verify that the result is not null
            assertEquals(2, result.size());  // Verify that two blocs match the capacity filter
            assertEquals(bloc2, result.get(0));  // Verify the first bloc in the result matches Bloc B
            assertEquals(bloc3, result.get(1));  // Verify the second bloc in the result matches Bloc C

            // Step 6: Verify repository interactions
            verify(blocRepository, times(1)).findAll(); // Ensure findAll() was called exactly once
        }

    @Test
    void trouverBlocsParNomEtCap() {
        Bloc bloc1 = new Bloc();
        bloc1.setIdBloc(1L);
        bloc1.setNomBloc("Bloc A");
        bloc1.setCapaciteBloc(100);

        Bloc bloc2 = new Bloc();
        bloc2.setIdBloc(2L);
        bloc2.setNomBloc("Bloc A");
        bloc2.setCapaciteBloc(100);

        // Mock list of blocs matching the search criteria
        List<Bloc> mockBlocs = Arrays.asList(bloc1, bloc2);

        // Step 2: Mock the behavior of the blocRepository
        when(blocRepository.findAllByNomBlocAndCapaciteBloc("Bloc A", 100)).thenReturn(mockBlocs);

        // Step 3: Call the method under test
        String blocName = "Bloc A";
        long blocCapacity = 100;
        List<Bloc> result = blocService.trouverBlocsParNomEtCap(blocName, blocCapacity);

        // Step 4: Assertions
        assertNotNull(result);  // Verify that the result is not null
        assertEquals(2, result.size());  // Verify that two blocs match the criteria
        assertEquals(bloc1, result.get(0));  // Verify the first bloc matches bloc1
        assertEquals(bloc2, result.get(1));  // Verify the second bloc matches bloc2

        // Step 5: Verify repository interactions
        verify(blocRepository, times(1)).findAllByNomBlocAndCapaciteBloc("Bloc A", 100); // Ensure the repository method is called exactly once
    }
}