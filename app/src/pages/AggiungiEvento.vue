<template>
  <div class="container">
    <!-- Titolo -->
    <div class="title">
      <h2 style="font-weight: bold">DATI EVENTO</h2>
    </div>

    <!-- Lista clienti -->
    <div class="form-group">
      <label for="cliente" class="label">Cliente:</label>
      <div>
        <select id="cliente" class="input" v-model="cliente">
          <option value="" disabled selected>&lt;seleziona il cliente&gt;</option>
          <option v-for="cliente in clienti" :key="cliente.id" :value="cliente.id">{{ cliente.nome }}</option>
        </select>
      </div>
    </div>

    <!-- Data -->
    <div class="form-group">
      <label for="date" class="label">Data:</label>
      <div class="input-wrapper">
        <input type="date" id="date" class="input" v-model="date"  @change="fetchDipendenti(); fetchDipendentiDisponibili();" />
      </div>
    </div>

    <!-- Luogo -->
    <div class="form-group">
      <label for="luogo" class="label">Luogo:</label>
      <div class="input-wrapper">
        <input type="text" id="luogo" class="input" v-model="luogo" />
      </div>
    </div>

    <!-- Descrizione -->
    <div class="form-group">
      <label for="descrizione" class="label">Descrizione:</label>
      <div class="input-wrapper">
        <textarea id="descrizione" class="textarea" v-model="descrizione"></textarea>
      </div>
    </div>

    <!-- Numero di dipendenti -->
    <div class="form-group">
      <label for="numeroDipendenti" class="label">Numero di Dipendenti:</label>
      <div class="input-wrapper">
        <input type="number" id="numeroDipendenti" class="input" v-model="numeroDipendenti" />
      </div>
    </div>

    <!-- Tabella delle qualifiche necessarie -->
    <div class="form-group">
      <label class="label">Qualifiche necessarie:</label>
      <div class="qualifiche-table">
        <table>
          <thead>
            <tr>
              <th>Qualifica</th>
              <th>Numero Persone</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(quantity, qualifica) in qualificheNecessarie" :key="qualifica">
              <td>{{ qualifica }}</td>
              <td>
                <div class="numero-persone">
                  <button class="decrease-button" @click="decreaseQuantity(qualifica)">-</button>
                  {{ quantity }}
                  <button class="increase-button" @click="increaseQuantity(qualifica)">+</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Selezione dipendenti -->
    <div class="form-group">
      <label for="dipendentiSelezionati" class="label">Dipendenti:</label>
      <div class="input-wrapper" style="height: 400px; width: 300px; overflow-y: auto;">
        <select id="dipendentiSelezionati" class="input" multiple v-model="dipendenti" style="height: 100%; width: 100%;">
          <option v-for="dipendente in allDipendenti" :key="dipendente.id" :value="dipendente.id">{{ dipendente.nome }}</option>
        </select>
      </div>
    </div>

    <!-- Selezione dipendenti disponibili in una data -->
    <div class="form-group">
      <label for="dipendentiDisponibili" class="label">Dipendenti Disponibili:</label>
      <div class="input-wrapper" style="height: 400px; width: 300px; overflow-y: auto;">
        <select id="dipendentiDisponibili" class="input" multiple v-model="dipendentiDisponibili" style="height: 100%; width: 100%;">
          <option v-for="dipendente in dipendentiDisponibili" :key="dipendente.id" :value="dipendente.id">{{ dipendente.nome }}</option>
        </select>
      </div>
    </div>

    <!-- Pulsante di conferma -->
    <button class="button" @click="inviaDatiEvento">Conferma</button>
    <span v-if="mostraConferma" class="conferma">Evento aggiunto correttamente!</span>

    <!-- Spazio per scorrere -->
    <div style="height: 1000px;"></div>
  </div>
</template>

<script>
import Evento from "@/models/Evento.js";

export default {
  data() {
    return {
      cliente: "",
      date: "",
      luogo: "",
      descrizione: "",
      isFesta: false,
      dipendenti: [],
      mostraConferma: false,
      eventi: [],
      clienti: [],
      dipendentiDisponibili: [],
      allDipendenti: [],
      dipendentiAssenti: [],
      clientiDisponibili: [],
      numeroDipendenti: 0,
      qualificheNecessarie: {
        BAR: 0,
        SALA: 0,
        CUCINA: 0,
      },
    };
  },
  created() {
    this.fetchClienti();
    this.fetchDipendenti();
    // this.fetchEventi(); // Manca
  },
  methods: {
    fetchClienti() {
      const token = sessionStorage.getItem("token");
      fetch("http://localhost:8080/api/v1/clienti/getallclienti", {
        headers: {
          Authorization: `Bearer ${token}`,
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
    fetchDipendenti() {
      const token = sessionStorage.getItem("token");
      fetch("http://localhost:8080/api/v1/dipendenti/getalldipendenti", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => response.json())
      .then((data) => {
        this.allDipendenti = data;
      })
      .catch((error) => {
        console.error("Errore durante il recupero dei dipendenti:", error);
      });
    },
    increaseQuantity(qualifica) {
      this.qualificheNecessarie = {
        ...this.qualificheNecessarie,
        [qualifica]: this.qualificheNecessarie[qualifica] + 1,
      };
    },
    decreaseQuantity(qualifica) {
      if (this.qualificheNecessarie[qualifica] > 0) {
        this.qualificheNecessarie = {
          ...this.qualificheNecessarie,
          [qualifica]: this.qualificheNecessarie[qualifica] - 1,
        };
      }
    },
    inviaDatiEvento() {
      const evento = new Evento(
        this.cliente,
        this.date,
        this.luogo,
        this.descrizione,
        this.isFesta,
        this.dipendenti
      );

      evento.numeroDipendenti = this.numeroDipendenti;
      const token = sessionStorage.getItem("token");

      fetch("http://localhost:8080/api/v1/eventi/addevento", {
        method: "POST",
        body: JSON.stringify(evento),
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        if (response.ok) {
          this.mostraConferma = true;
        } else {
          // TODO Gestione dell'errore
        }
      })
      .catch((error) => {
        console.error("Errore durante l'invio dei dati evento:", error);
      });

      this.cliente = "";
      this.date = "";
      this.luogo = "";
      this.descrizione = "";
      this.isFesta = false;
      this.dipendenti = [];
      this.numeroDipendenti = 0;
      this.qualificheNecessarie = {
        BAR: 0,
        SALA: 0,
        CUCINA: 0,
      };
    },
    formatDateForApi(date) {
      const formattedDate = new Date(date);
      const year = formattedDate.getFullYear();
      let month = (formattedDate.getMonth() + 1).toString();
      let day = formattedDate.getDate().toString();

      if (month.length === 1) {
        month = `0${month}`;
      }
      if (day.length === 1) {
        day = `0${day}`;
      }

      return `${year}-${month}-${day}`;
    },
    fetchDipendentiDisponibili() {
  const token = sessionStorage.getItem("token");
  const formattedDate = this.formatDateForApi(this.date);

  fetch(`http://localhost:8080/api/v1/assenze/getassenzabydata/${formattedDate}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
  .then((response) => {
    if (!response.ok) {
      throw new Error('Assenza non trovata');
    }
    return response.json();
  })
  .then((assenza) => {
    return fetch(`http://localhost:8080/api/v1/assenze/getassenzedipendente/${assenza.id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  })
  .then((response) => {
    if (!response.ok) {
      throw new Error('Errore nel recupero dei dipendenti assenti');
    }
    return response.json();
  })
  .then((dipendentiAssenti) => {
    this.dipendentiAssenti = dipendentiAssenti;
    this.dipendentiDisponibili = this.allDipendenti.filter((dipendente) => !dipendentiAssenti.includes(dipendente.id));
  })
  .catch((error) => {
    console.error("Errore durante il recupero dei dipendenti assenti:", error);
  });
},

    // ... altri metodi se presenti ...
  },
};
</script>

<style>
/* Stili precedenti */

.qualifiche-table table {
  margin-top: 20px;
  width: 100%;
  border-collapse: collapse;
}

.qualifiche-table th,
.qualifiche-table td {
  padding: 10px;
  border: 1px solid #ccc;
  text-align: center;
}

.numero-persone {
  display: flex;
  align-items: center;
}

.decrease-button,
.increase-button {
  padding: 5px 10px;
  font-size: 14px;
  background-color: #f2f2f2;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}


.increase-button {
  margin-left: 5px;
}
</style>
