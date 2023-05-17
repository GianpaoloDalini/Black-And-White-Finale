<!-- TO DO per sfizio, aggiungere lateralmente una tabella con i clienti nel sistema -->

<template>
  <div class="container">
    <!-- Titolo -->
    <!-- Titolo -->
    <div class="title">
      <h2 style="font-weight: bold">DATI CLIENTE</h2>
    </div>

    <!-- Form -->
    <div class="form-group">
      <label for="nome" class="label">Nome:</label>
      <div class="input-wrapper">
        <input type="text" id="nome" class="input" v-model="nome" />
      </div>
    </div>

    <div class="form-group">
      <label for="descrizione" class="label">Descrizione:</label>
      <div class="input-wrapper">
        <textarea
          id="descrizione"
          class="textarea"
          v-model="descrizione"
        ></textarea>
      </div>
    </div>

    <!-- Pulsante di conferma -->
    <button class="button" @click="inviaDati">Conferma</button>
    <span v-if="mostraConferma" class="conferma">Cliente aggiunto correttamente!</span>


<!-- Sezione Elimina Cliente -->
    <div class="form-group">
      <label for="clienteId" class="label">ID Cliente:</label>
      <div class="input-wrapper">
        <input type="text" id="clienteId" class="input" v-model="clienteId" />
      </div>
    </div>
    <button class="button button-delete" style="background-color: red;" @click="eliminaCliente">Elimina Cliente</button>
    <span v-if="mostraEliminaConferma" class="conferma">Cliente eliminato correttamente!</span>
  



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
import Cliente from "@/models/Cliente.js";

export default {
  data() {
    return {
      nome: "",
      descrizione: "",
      mostraConferma: false,
    
    
      // parte del delte 
      clienteId: "",
      mostraEliminaConferma: false,
    };
  },
  methods: {
    inviaDati() {
      const cliente = new Cliente(this.nome, this.descrizione);

      // Invio dei dati al server
      fetch("http://localhost:8080/cliente", {
        method: "POST",
        body: JSON.stringify(cliente),
        headers: {
          "Content-Type": "application/json",
        },
      });
      // Resetta i campi del form dopo l'invio dei dati
      this.nome = "";
      this.descrizione = "";
      
      // Mostrare il messaggio di conferma
      this.mostraConferma = true;
    },

    eliminaCliente() {
      // Invio della richiesta di eliminazione al server
      fetch(`http://localhost:8080/cliente/${this.clienteId}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      }).then(response => {
        if (response.ok) {
          // Cliente eliminato con successo
          this.mostraEliminaConferma = true;
        } else {
          // Gestione dell'errore in caso di fallimento dell'eliminazione
          // Puoi mostrare un messaggio di errore o fare altre azioni
        }
      });

      // Resetta il campo ID dopo l'eliminazione del cliente
      this.clienteId = "";
    },



  },
};
</script>
