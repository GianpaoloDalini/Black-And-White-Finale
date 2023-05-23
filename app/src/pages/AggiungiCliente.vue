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
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 5px;
  background-color: #f9f9f9;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.left-column,
.right-column {
  flex: 1;
  padding: 10px;
}

.title {
  margin-bottom: 10px;
}

.form-container {
  padding: 10px;
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

.table-container {
  width: 100%;
  padding: 10px;
  overflow: auto;
}

.clienti-table {
  width: 100%;
  border-collapse: collapse;
}

.clienti-table th,
.clienti-table td {
  padding: 10px;
  border: 1px solid #ccc;
  text-align: left;
}

.clienti-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.clienti-table td {
  font-size: 14px;
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
      clienteId: "",
      mostraEliminaConferma: false,
      clienti: [], // Aggiunta della lista dei clienti
    };
  },
  methods: {
    inviaDati() {
      const cliente = new Cliente(this.nome, this.descrizione);

      // Prendi il token dalla sessionStorage
      const token = localStorage.getItem("token");

      // Invio dei dati al server
      fetch("http://localhost:8080/api/v1/clienti/addcliente", {
        method: "POST",
        body: JSON.stringify(cliente),
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
      });

      // Resetta i campi del form dopo l'invio dei dati
      this.nome = "";
      this.descrizione = "";
      this.mostraConferma = true;

      // Caricamento dei clienti dopo l'aggiunta di un nuovo cliente
      this.caricaClienti();
    },

    eliminaCliente() {
      // Prendi il token dalla sessionStorage
      const token = localStorage.getItem("token");

      // Invio della richiesta di eliminazione al server
      fetch(`http://localhost:8080/api/v1/clienti/deletecliente/${this.clienteId}}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
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

      // Caricamento dei clienti dopo l'aggiunta di un nuovo cliente
      this.caricaClienti();
    },

    caricaClienti() {
      // Prendi il token dalla sessionStorage
      const token = localStorage.getItem("token");

      fetch("http://localhost:8080/api/v1/clienti/getallclienti", {
        headers: {
          "Authorization": `Bearer ${token}`,
        },
      })
        .then(response => response.json())
        .then(data => {
          this.clienti = data;
        })
        .catch(error => {
          console.error("Errore durante il recupero dei clienti:", error);
        });
    },
  },

  mounted() {
    // Caricamento dei clienti al caricamento della pagina
    this.caricaClienti();
  },
};
</script>
