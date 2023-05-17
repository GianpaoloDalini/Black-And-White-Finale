package com.dipendente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class DipendenteController {

    @Autowired
    private DipendenteService service;

    @PostMapping("/dipendente")
    public ResponseEntity<Dipendente> addDipendente(@RequestBody Dipendente dipendente) {
        Dipendente savedDipendente = service.addDipendente(dipendente);
        return new ResponseEntity<>(savedDipendente, HttpStatus.CREATED);
    }

}
