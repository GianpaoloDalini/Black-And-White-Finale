package com.dipendente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Dipendente addDipendente(Dipendente dipendente) {
        String encryptedPassword = passwordEncoder.encode(dipendente.getPassword());
        dipendente.setPassword(encryptedPassword);
        return repository.save(dipendente);
    }
    

}
