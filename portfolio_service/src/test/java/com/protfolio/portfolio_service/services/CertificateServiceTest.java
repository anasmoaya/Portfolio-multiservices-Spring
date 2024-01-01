package com.protfolio.portfolio_service.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.protfolio.portfolio_service.entities.Certificate;
import com.protfolio.portfolio_service.repositories.CertificateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class CertificateServiceTest {

    @Mock
    private CertificateRepository certificateRepository;

    @InjectMocks
    private CertificateService certificateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindAll_thenRetrieveCertificates() {
        List<Certificate> certificates = new ArrayList<>();
        certificates.add(new Certificate());
        certificates.add(new Certificate());

        when(certificateRepository.findAll()).thenReturn(certificates);

        List<Certificate> foundCertificates = certificateService.findAll();

        assertNotNull(foundCertificates);
        assertEquals(2, foundCertificates.size());
        verify(certificateRepository, times(1)).findAll();
    }


    @Test
    void whenFindById_thenRetrieveCertificate() {
        Certificate certificate = new Certificate();
        certificate.setId(1L);

        when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));

        Optional<Certificate> found = certificateService.findById(1L);

        assertTrue(found.isPresent());
        assertEquals(certificate.getId(), found.get().getId());
        verify(certificateRepository, times(1)).findById(1L);
    }

    @Test
    void whenSaveCertificate_thenSuccess() {
        Certificate certificate = new Certificate();

        when(certificateRepository.save(any(Certificate.class))).thenReturn(certificate);

        Certificate savedCertificate = certificateService.save(certificate);

        assertNotNull(savedCertificate);
        verify(certificateRepository, times(1)).save(any(Certificate.class));
    }
    @Test
    void whenUpdateCertificate_thenSuccess() {
        Certificate existingCertificate = new Certificate();
        existingCertificate.setId(1L);

        Certificate updatedCertificate = new Certificate();
        updatedCertificate.setName("Updated Certificate");

        when(certificateRepository.findById(1L)).thenReturn(Optional.of(existingCertificate));
        when(certificateRepository.save(any(Certificate.class))).thenReturn(updatedCertificate);

        Certificate savedCertificate = certificateService.update(1L, updatedCertificate);

        assertNotNull(savedCertificate);
        assertEquals("Updated Certificate", savedCertificate.getName());
        verify(certificateRepository, times(1)).findById(1L);
        verify(certificateRepository, times(1)).save(any(Certificate.class));
    }
    @Test
    void whenDeleteCertificate_thenSuccess() {
        Long id = 1L;

        doNothing().when(certificateRepository).deleteById(id);

        certificateService.delete(id);

        verify(certificateRepository, times(1)).deleteById(id);
    }


}
