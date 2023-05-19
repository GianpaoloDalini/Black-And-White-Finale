package com.dipendente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dipendenti")
@CrossOrigin(origins = "*")
public class DipendenteController {

    @Autowired
    private DipendenteService service;

@PostMapping("/adddipendente")
@PreAuthorize("hasAuthority('PROPRIETARIO') || hasAuthority('REFERENTE')")
public ResponseEntity<Dipendente> addDipendente(@RequestBody Dipendente dipendente) {
    Dipendente savedDipendente = service.addDipendente(dipendente);
    return new ResponseEntity<>(savedDipendente, HttpStatus.CREATED);
}


}
