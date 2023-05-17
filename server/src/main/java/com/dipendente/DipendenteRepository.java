package com.dipendente;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DipendenteRepository extends MongoRepository<Dipendente, String> {

    Optional<Dipendente> findByUsername(String username);}
