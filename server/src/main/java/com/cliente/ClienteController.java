package com.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clienti")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping("/addcliente")
    public ResponseEntity<Cliente> addCliente(@RequestBody Cliente cliente) {
        Cliente savedCliente = service.addCliente(cliente);
        return new ResponseEntity<>(savedCliente, HttpStatus.CREATED);
    }

    @GetMapping("/getallclienti")
    public ResponseEntity<List<Cliente>> getAllClienti() {
        final List<Cliente> clienti = service.getAllClienti();
        return new ResponseEntity<>(clienti, HttpStatus.OK);
    }

    @DeleteMapping("/deletecliente/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable String id) {
        boolean deleted = service.deleteCliente(id);
        if (deleted) {
            return new ResponseEntity<>("Cliente eliminato correttamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Impossibile trovare il cliente con l'ID specificato", HttpStatus.NOT_FOUND);
        }
    }

    // Altri metodi per la gestione delle operazioni CRUD per il modello Cliente
}
