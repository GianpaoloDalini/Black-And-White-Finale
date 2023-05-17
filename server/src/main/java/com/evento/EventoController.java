package com.evento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping("/evento")
    public ResponseEntity<Evento> creaEvento(@RequestBody Evento evento) {
        Evento nuovoEvento = eventoService.addEvento(evento);
        return new ResponseEntity<>(nuovoEvento, HttpStatus.CREATED);
    }

    @PutMapping("/evento/{id}")
    public ResponseEntity<Evento> aggiornaEvento(@PathVariable long id, @RequestBody Evento evento) {
        Evento eventoAggiornato = eventoService.aggiornaEvento(id, evento);
        if (eventoAggiornato != null) {
            return new ResponseEntity<>(eventoAggiornato, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/evento/{id}")
    public ResponseEntity<Void> eliminaEvento(@PathVariable long id) {
        boolean eliminato = eventoService.eliminaEvento(id);
        if (eliminato) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
