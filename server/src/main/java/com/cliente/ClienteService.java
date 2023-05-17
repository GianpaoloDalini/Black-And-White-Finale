package com.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente addCliente(Cliente cliente) {
        // Effettua qui eventuali operazioni di validazione o di business logic
        // prima di salvare il cliente nel repository
        return repository.save(cliente);
    }

    public Cliente getClienteById(String id) {
        return repository.findById(id).orElse(null);
    }

    public boolean deleteCliente(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Cliente> getAllClienti() {
        return repository.findAll();
    }
    

    // Aggiungi altri metodi per le operazioni CRUD come la lettura, l'aggiornamento e l'eliminazione dei clienti
}
