<template>
  <div class="container">
    <div class="left-column">
      <!-- Titolo -->
      <div class="title">
        <h2 style="font-weight: bold">DATI CLIENTE</h2>
      </div>

      <!-- Form di Aggiunta Cliente -->
      <div class="form-container">
        <div class="form-group">
          <label for="nome" class="label">Nome:</label>
          <div class="input-wrapper">
            <input type="text" id="nome" class="input" v-model="nome" />
          </div>
        </div>

        <div class="form-group">
          <label for="descrizione" class="label">Descrizione:</label>
          <div class="input-wrapper">
            <textarea id="descrizione" class="textarea" v-model="descrizione"></textarea>
          </div>
        </div>

        <button class="button" @click="inviaDati">Conferma</button>
        <span v-if="mostraConferma" class="conferma">Cliente aggiunto correttamente!</span>
      </div>

      <!-- Form di Eliminazione Cliente -->
      <div class="form-container">
        <div class="form-group">
          <label for="clienteId" class="label">ID Cliente:</label>
          <div class="input-wrapper">
            <input type="text" id="clienteId" class="input" v-model="clienteId" />
          </div>
        </div>

        <button class="button button-delete" style="background-color: red;" @click="eliminaCliente">Elimina Cliente</button>
        <span v-if="mostraEliminaConferma" class="conferma">Cliente eliminato correttamente!</span>
      </div>
    </div>

    <div class="right-column">
      <!-- Tabella dei clienti -->
      <div class="table-container">
        <table class="clienti-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nome</th>
              <th>Descrizione</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="cliente in clienti" :key="cliente.id">
              <td>{{ cliente.id }}</td>
              <td>{{ cliente.nome }}</td>
              <td>{{ cliente.descrizione }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!-- Spazio per scorrere -->
    <div style="height: 1000px;"></div>
  </div>
</template>

<style>
/* Stili CSS */
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
      mostraConferma: false,
      clienteId: "",
      mostraEliminaConferma: false,
      clienti: [],
    };
  },
  methods: {
    inviaDati() {
      const cliente = {
        nome: this.nome,
        descrizione: this.descrizione,
      };

      axiosInstance.post("/clienti/addcliente", cliente)
        .then(response => {
          // Resetta i campi del form dopo l'invio dei dati
          this.nome = "";
          this.descrizione = "";

          // Mostra il messaggio di conferma
          this.mostraConferma = true;

          // Carica i clienti dopo l'aggiunta di un nuovo cliente
          this.caricaClienti();
        })
        .catch(error => {
          console.error("Errore durante l'invio dei dati:", error);
        });
    },

    eliminaCliente() {
      axiosInstance.delete(`/clienti/deletecliente/${this.clienteId}`)
        .then(response => {
          if (response.status === 200) {
            // Cliente eliminato con successo
            this.mostraEliminaConferma = true;

            // Resetta il campo ID dopo l'eliminazione del cliente
            this.clienteId = "";

            // Carica i clienti dopo l'eliminazione di un cliente
            this.caricaClienti();
          } else {
            // Gestione dell'errore in caso di fallimento dell'eliminazione
            // Puoi mostrare un messaggio di errore o fare altre azioni
          }
        })
        .catch(error => {
          console.error("Errore durante l'eliminazione del cliente:", error);
        });
    },

    caricaClienti() {
      axiosInstance.get("/clienti/getallclienti")
        .then(response => {
          this.clienti = response.data;
        })
        .catch(error => {
          console.error("Errore durante il recupero dei clienti:", error);
        });
    },
  },

  mounted() {
    // Carica i clienti al caricamento della pagina
    this.caricaClienti();
  },
};
</script>
