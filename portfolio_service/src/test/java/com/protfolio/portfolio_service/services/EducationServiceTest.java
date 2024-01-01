package com.protfolio.portfolio_service.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.protfolio.portfolio_service.entities.Education;
import com.protfolio.portfolio_service.repositories.EducationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class EducationServiceTest {

    @Mock
    private EducationRepository educationRepository;

    @InjectMocks
    private EducationService educationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindAll_thenRetrieveEducationEntries() {
        List<Education> educations = new ArrayList<>();
        educations.add(new Education());
        educations.add(new Education());

        when(educationRepository.findAll()).thenReturn(educations);

        List<Education> foundEducations = educationService.findAll();

        assertNotNull(foundEducations);
        assertEquals(2, foundEducations.size());
        verify(educationRepository, times(1)).findAll();
    }

    @Test
    void whenFindById_thenRetrieveEducation() {
        Education education = new Education();
        when(educationRepository.findById(anyLong())).thenReturn(Optional.of(education));

        Optional<Education> found = educationService.findById(1L);

        assertTrue(found.isPresent());
        verify(educationRepository, times(1)).findById(anyLong());
    }

    @Test
    void whenSaveEducation_thenSuccess() {
        Education education = new Education();
        when(educationRepository.save(any(Education.class))).thenReturn(education);

        Education savedEducation = educationService.save(new Education());

        assertNotNull(savedEducation);
        verify(educationRepository, times(1)).save(any(Education.class));
    }

    @Test
    void whenUpdateEducation_thenSuccess() {
        Education existingEducation = new Education();
        when(educationRepository.findById(anyLong())).thenReturn(Optional.of(existingEducation));
        when(educationRepository.save(any(Education.class))).thenReturn(existingEducation);

        Education savedEducation = educationService.update(1L, new Education());

        assertNotNull(savedEducation);
        verify(educationRepository, times(1)).findById(anyLong());
        verify(educationRepository, times(1)).save(any(Education.class));
    }

    @Test
    void whenDeleteEducation_thenSuccess() {
        doNothing().when(educationRepository).deleteById(anyLong());

        educationService.delete(1L);

        verify(educationRepository, times(1)).deleteById(anyLong());
    }
}
