package com.cliente;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clienti")
public class Cliente {
    
    @Id
    private String id;
    private String nome;
    private String descrizione;
    
    // Costruttori, getter e setter
    
    public Cliente() {
        this.id = generateRandomId();
    }
    
    public Cliente(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.id = generateRandomId();
    }
    
    // Metodi getter e setter
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescrizione() {
        return descrizione;
    }
    
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    private String generateRandomId() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid.substring(0, 5);
    }
}
