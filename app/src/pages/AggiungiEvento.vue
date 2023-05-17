<template>
  <div class="container">
    <!-- Titolo -->
    <div class="title">
      <h2 style="font-weight: bold">DATI EVENTO</h2>
    </div>

    <div class="form-group">
  <label for="cliente" class="label">Cliente:</label>
  <div>
    <select id="cliente" class="input" v-model="cliente">
      <option value="" disabled selected>&lt;seleziona il cliente&gt;</option>
      <option v-for="cliente in clientiDisponibili" :key="cliente.id" :value="cliente.id">{{ cliente.nome }}</option>
    </select>
  </div>
</div>


    <div class="form-group">
      <label for="data" class="label">Data:</label>
      <div class="input-wrapper">
        <input type="date" id="data" class="input" v-model="data" />
      </div>
    </div>

    <div class="form-group">
      <label for="luogo" class="label">Luogo:</label>
      <div class="input-wrapper">
        <input type="text" id="luogo" class="input" v-model="luogo" />
      </div>
    </div>

    <div class="form-group">
      <label for="descrizione" class="label">Descrizione:</label>
      <div class="input-wrapper">
        <textarea id="descrizione" class="textarea" v-model="descrizione"></textarea>
      </div>
    </div>

    <div class="form-group">
      <label for="isFesta" class="label">È una festa:</label>
      <div class="input-wrapper">
        <input type="checkbox" id="isFesta" class="input" v-model="isFesta" />
      </div>
    </div>

    <div class="form-group">
      <label for="dipendenti" class="label">Dipendenti:</label>
      <div class="input-wrapper">
        <select id="dipendenti" class="input" multiple v-model="dipendenti">
          <option v-for="dipendente in dipendentiDisponibili" :key="dipendente.id" :value="dipendente.id">{{ dipendente.nome }}</option>
        </select>
      </div>
    </div>

    <!-- Pulsante di conferma -->
    <button class="button" @click="inviaDati">Conferma</button>
    <span v-if="mostraConferma" class="conferma">Evento aggiunto correttamente!</span>

      <!-- Spazio per scorrere -->
  <div style="height: 1000px;"></div>
  </div>

  
</template>

<style>
/* Stili per il container principale, form e pulsante */
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 5px;
  background-color: #f9f9f9;
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: baseline;
  margin-bottom: 20px;
  overflow-y: auto; /* Abilita lo scrolling verticale */
}

.title {
  margin-bottom: 20px;
}

.title h2 {
  font-weight: bold;
}

.form-group {
  padding: 10px;
  margin-bottom: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}

.label {
  display: block;
  font-size: 14px;
  font-weight: bold;
  margin-bottom: 5px;
}

.input-wrapper {
  display: flex;
  align-items: center;
}

.button-delete {
  background-color: red;
}

.input {
  width: 100%;
  height: 40px;
  padding: 5px 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.textarea {
  width: 100%;
  min-height: 100px;
  padding: 5px 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.button {
  padding: 10px 50px;
  background-color: #4caf50;
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.conferma {
  display: inline-block;
  margin-left: 10px;
  font-size: 14px;
  color: green;
  margin-top: 10px;
}
</style>
<script>
import Evento from "@/models/Evento.js";

export default {
  data() {
    return {
      cliente: "",
      data: "",
      luogo: "",
      descrizione: "",
      isFesta: false,
      dipendenti: [],
      mostraConferma: false,
      eventi: [], // Array degli eventi
      clienti: [], // Array dei clienti disponibili
      dipendentiDisponibili: [], // Array dei dipendenti disponibili
      clientiDisponibili: [], // Array di clienti disponibili
   
    };
  },
  created() {

    // Recupera la lista dei dipendenti clienti
    this.fetchClienti();
  
    // Recupera la lista dei dipendenti disponibili
    this.fetchDipendenti();

    // Recupera la lista degli eventi
    this.fetchEventi();
  },
  methods: {
    fetchClienti() {
      // Effettua la richiesta al server per ottenere la lista dei clienti
      fetch("http://localhost:8080/clienti")
        .then((response) => response.json())
        .then((data) => {
          this.clientiDisponibili = data;
        })
        .catch((error) => {
          console.error("Errore durante il recupero dei clienti:", error);
        });
    },
    fetchDipendenti() {
      // Effettua la richiesta al server per ottenere la lista dei dipendenti
      fetch("http://localhost:8080/dipendenti")
        .then((response) => response.json())
        .then((data) => {
          this.dipendentiDisponibili = data;
        })
        .catch((error) => {
          console.error("Errore durante il recupero dei dipendenti:", error);
        });
    },
    fetchEventi() {
      // Effettua la richiesta al server per ottenere la lista degli eventi
      fetch("http://localhost:8080/eventi")
        .then((response) => response.json())
        .then((data) => {
          this.eventi = data;
        })
        .catch((error) => {
          console.error("Errore durante il recupero degli eventi:", error);
        });
    },
    inviaDati() {
      const evento = new Evento(
        this.cliente,
        this.data,
        this.luogo,
        this.descrizione,
        this.isFesta,
        this.dipendenti
      );

      // Invio dei dati al server
      fetch("http://localhost:8080/evento", {
        method: "POST",
        body: JSON.stringify(evento),
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((response) => {
          if (response.ok) {
            // Evento aggiunto con successo
            this.mostraConferma = true;
            // Aggiorna la lista degli eventi
            this.fetchEventi();
          } else {
            // Gestione dell'errore in caso di fallimento dell'aggiunta
            // Puoi mostrare un messaggio di errore o fare altre azioni
          }
        })
        .catch((error) => {
          console.error("Errore durante l'invio dei dati evento:", error);
        });

      // Resetta i campi del form dopo l'invio dei dati
      this.cliente = "";
      this.data = "";
      this.luogo = "";
      this.descrizione = "";
      this.isFesta = false;
      this.dipendenti = [];
    },
    eliminaEvento() {
      // Invio della richiesta di eliminazione al server
      fetch(`http://localhost:8080/evento/${this.eventoId}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((response) => {
          if (response.ok) {
            // Evento eliminato con successo
            this.mostraEliminaConferma = true;
            // Aggiorna la lista degli eventi
            this.fetchEventi();
          } else {
            // Gestione dell'errore in caso di fallimento dell'eliminazione
            // Puoi mostrare un messaggio di errore o fare altre azioni
          }
        })
        .catch((error) => {
          console.error("Errore durante l'eliminazione dell'evento:", error);
        });

      // Resetta il campo ID dopo l'eliminazione dell'evento
      this.eventoId = "";
    },
  },
};
</script>

