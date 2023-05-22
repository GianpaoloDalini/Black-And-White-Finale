<template>
  <div>
    <div class="container">
      <!-- Titolo -->
      <div class="title">
        <h2 style="font-weight: bold">INSERISCI DATI DEL DIPENDENTE</h2>
      </div>

      <!-- Form -->
      <!-- Form -->
      <div class="form-group">
        <div class="form form-inline">
          <!-- Username -->
          <label for="username" class="label">Username:</label>
          <div class="input-wrapper">
            <input type="text" id="username" class="input" v-model="username" />
          </div>

          <!-- Password -->
          <label for="password" class="label">Password:</label>
          <div class="input-wrapper">
            <input type="password" id="password" class="input" v-model="password" />
          </div>

          <!-- Nome -->
          <label for="name" class="label">Nome:</label>
          <div class="input-wrapper">
            <input type="text" id="name" class="input" v-model="nome" />
          </div>

          <!-- Cognome -->
          <label for="surname" class="label">Cognome:</label>
          <div class="input-wrapper">
            <input type="text" id="surname" class="input" v-model="cognome" />
          </div>
        </div>
      </div>

      <!-- Checkbox qualifiche e lingue -->
      <div class="row">
        <div class="column">
          <!-- Qualifiche -->
          <div class="checkbox-group">
            <label class="label">Qualifiche:</label>
            <div class="checkbox">
              <input type="checkbox" id="BAR" v-model="qualifiche.BAR" />
              <label for="BAR">Bar</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="SALA" v-model="qualifiche.SALA" />
              <label for="SALA">Sala</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="CUCINA" v-model="qualifiche.CUCINA" />
              <label for="CUCINA">Cucina</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="AUTOMUNITO" v-model="qualifiche.AUTOMUNITO" />
              <label for="AUTOMUNITO">Automunito</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="REFERENTE" v-model="referente" />
              <label for="REFERENTE">Referente</label>
            </div>
          </div>
        </div>
        <div class="column">
          <!-- Lingue -->
          <div class="checkbox-group">
            <label class="label">Lingue:</label>
            <div class="checkbox">
              <input type="checkbox" id="inglese" v-model="lingue.INGLESE" />
              <label for="inglese">Inglese</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="francese" v-model="lingue.FRANCESE" />
              <label for="francese">Francese</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="spagnolo" v-model="lingue.SPAGNOLO" />
              <label for="spagnolo">Spagnolo</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="tedesco" v-model="lingue.TEDESCO" />
              <label for="tedesco">Tedesco</label>
            </div>
          </div>
        </div>
      </div>

      <!-- Pulsante di conferma -->
      <button class="button" @click="inviaDati">Conferma</button>
      <span v-if="mostraConferma" class="conferma">Dati inviati correttamente!</span>
    </div>

    <!-- Clearfix per pulire il float -->
    <div class="clearfix"></div>
     <!-- Spazio per scorrere -->
     <div style="height: 1000px;"></div>
  </div>
</template>

<style>
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
  /* Aggiunge uno spazio di 20px sotto il contenitore principale */
}

.row {
  display: flex;
}

.column {
  flex: 1;
  margin-right: 100px;
  /* Aggiunge uno spazio di 100px tra le colonne */
}

.column:last-child {
  margin-right: 0;
}

.form-group {
  padding: 10px;
  margin-bottom: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
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
  margin-bottom: 5px;
  /* Aggiunge uno spazio di 5px tra l'etichetta e l'input */
}

.input {
  width: 2000px;
  height: 40px;
  padding: 5px 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.checkbox-group {
  width: 150px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-top: 20px;
}

.checkbox {
  margin-bottom: 5px;
}

.button {
  position: relative;
  margin-bottom: 20px;
}

.conferma {
  display: inline-block;
  margin-left: 100px;
  font-size: 14px;
  color: green;
  margin-top: 10px;
  position: absolute;
  top: 90%;
}

/* Clearfix */
.clearfix::after {
  content: "";
  display: table;
  clear: both;
}
</style>

<script>
import axiosInstance from "@/assets/api.js";
import Dipendente from "@/models/Dipendente.js";

export default {
  data() {
    return {
      username: "", // Variabile per lo username del dipendente
      password: "", // Variabile per la password del dipendente
      nome: "", // Variabile per il nome del dipendente
      cognome: "", // Variabile per il cognome del dipendente
      referente: false, // Variabile per il referente del dipendente
      mostraConferma: false, // Variabile per mostrare/nascondere il messaggio di conferma
      qualifiche: {
        AUTOMUNITO: false, // Valore booleano per la qualifica 'automunito'
        BAR: false, // Valore booleano per la qualifica 'bar'
        SALA: false, // Valore booleano per la qualifica 'sala'
        CUCINA: false, // Valore booleano per la qualifica 'cucina'
      },
      lingue: {
        INGLESE: false, // Valore booleano per la lingua 'inglese'
        FRANCESE: false, // Valore booleano per la lingua 'francese'
        SPAGNOLO: false, // Valore booleano per la lingua 'spagnolo'
        TEDESCO: false, // Valore booleano per la lingua 'tedesco'
        // Aggiungi altre lingue qui
      },
    };
  },
  methods: {
    inviaDati() {
      const role = this.referente ? "REFERENTE" : "DIPENDENTE";

      // Creazione di un nuovo oggetto Dipendente con i valori correnti
      const lingueSelezionate = Object.keys(this.lingue).filter(
        (l) => this.lingue[l]
      );
      const qualificheSelezionate = Object.keys(this.qualifiche).filter(
        (l) => this.qualifiche[l]
      );

      const dipendente = {
        username: this.username,
        password: this.password,
        nome: this.nome,
        cognome: this.cognome,
        role: role,
        qualifiche: qualificheSelezionate,
        lingue: lingueSelezionate,
      };

      // Effettua la richiesta POST al server utilizzando Axios
     // Effettua la richiesta POST al server utilizzando l'istanza di Axios
axiosInstance
  .post("/api/v1/dipendenti/adddipendente", dipendente)
  .then((response) => {
    console.log("Dipendente aggiunto:", response.data);
    // Gestisci la risposta del server se necessario
  })
  .catch((error) => {
    console.error("Errore durante la richiesta:", error);
    // Gestisci l'errore se necessario
  });


      // Resettare i valori del form
      this.username = "";
      this.password = "";
      this.nome = "";
      this.cognome = "";

      // Mostrare il messaggio di conferma
      this.mostraConferma = true;
    },
  },
};

</script>
