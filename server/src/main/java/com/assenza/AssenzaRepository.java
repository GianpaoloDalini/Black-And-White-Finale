package com.assenza;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;


@Repository
public interface AssenzaRepository extends MongoRepository<Assenza, String> {
    
    Assenza findByData(Date data);
    
    List<Assenza> findByDipendentiContaining(String dipendenteId);

    List<Assenza> findByDipendentiContainingAndData(String dipendenteId, Date data);
    
}
