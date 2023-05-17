package com.evento;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "eventi")
public class Evento {

    @Id
    private String id;
    private String cliente;
    private LocalDate data;
    private String luogo;
    private String descrizione;
    private boolean isFesta;
    private List<String> dipendenti;

    public Evento(String cliente, LocalDate data, String luogo, String descrizione, boolean isFesta, List<String> dipendenti) {
        this.id = generateRandomId();
        this.cliente = cliente;
        this.data = data;
        this.luogo = luogo;
        this.descrizione = descrizione;
        this.isFesta = isFesta;
        this.dipendenti = dipendenti;
    }

    // Genera un ID alfanumerico UUID di 6 cifre
    private String generateRandomId() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        return uuid;
    }

    // getters and setters omitted for brevity

}
