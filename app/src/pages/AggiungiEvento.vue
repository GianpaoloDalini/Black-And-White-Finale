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
import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "http://localhost:8080/api/v1",
  headers: {
    "Content-Type": "application/json",
  },
});

export default {
  data() {
    return {
      nome: "",
      descrizione: "",
      clienteId: "",
      dipendenteId: "",
      mostraConferma: false,
      eventi: [],
      clientiDisponibili: [],
      dipendentiDisponibili: [],
      cliente: "", // Aggiungi questa linea
    };
  },
  methods: {
    aggiungiEvento() {
      const evento = {
        nome: this.nome,
        descrizione: this.descrizione,
        clienteId: this.clienteId,
        dipendenteId: this.dipendenteId,
      };

      axiosInstance.post("/eventi", evento)
        .then(response => {
          // Resetta i campi del form dopo l'invio dei dati
          this.nome = "";
          this.descrizione = "";
          this.clienteId = "";
          this.dipendenteId = "";

          // Mostra il messaggio di conferma
          this.mostraConferma = true;

          // Carica gli eventi dopo l'aggiunta di un nuovo evento
          this.caricaEventi();
        })
        .catch(error => {
          console.error("Errore durante l'invio dei dati:", error);
        });
    },

    caricaEventi() {
      axiosInstance.get("/eventi")
        .then(response => {
          this.eventi = response.data;
        })
        .catch(error => {
          console.error("Errore durante il recupero degli eventi:", error);
        });
    },

    caricaClientiDisponibili() {
      axiosInstance.get("/clienti/getallclienti")
        .then(response => {
          this.clientiDisponibili = response.data;
        })
        .catch(error => {
          console.error("Errore durante il recupero dei clienti:", error);
        });
    },

    caricaDipendentiDisponibili() {
      axiosInstance.get("/dipendenti")
        .then(response => {
          this.dipendentiDisponibili = response.data;
        })
        .catch(error => {
          console.error("Errore durante il recupero dei dipendenti:", error);
        });
    },
  },

  mounted() {
    // Carica gli eventi al caricamento della pagina
    this.caricaEventi();

    // Carica i clienti disponibili
    this.caricaClientiDisponibili();

    // Carica i dipendenti disponibili
    this.caricaDipendentiDisponibili();
  },
};
</script>
