package com.dipendente;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assenza.AssenzaService;

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

    public Dipendente getDipendenteById(String id) {
        return repository.findById(id).orElse(null);
    }

    public boolean deleteDipendente(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Dipendente> getAllDipendenti() {
        return repository.findAll();
    }

    public List<Dipendente> getDipendentiByDate(Date data) {
        List<Dipendente> dipendentiDisponibili = repository.findAll(); // Ottieni tutti i dipendenti
        List<String> dipendentiAssenti = AssenzaService.getDipendentiAssentiByData(data); // Ottieni i dipendenti assenti

        // Rimuovi i dipendenti assenti dalla lista dei dipendenti disponibili
        dipendentiDisponibili.removeAll(dipendentiAssenti);

        return dipendentiDisponibili;
    }
}
