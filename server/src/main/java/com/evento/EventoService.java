package com.evento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public Evento addEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Evento aggiornaEvento(long id, Evento evento) {
        return null;
    }

    public boolean eliminaEvento(long id) {
        return false;
    }

    // Altri metodi del service, se necessari

}
