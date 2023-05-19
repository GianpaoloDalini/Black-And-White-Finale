<template>
  <div class="wrapper" :class="{ 'nav-open': $sidebar.showSidebar }">
    <!-- Componente per le notifiche -->
    <notifications></notifications>

    <!-- Barra laterale -->
    <side-bar
      :sidebar-item-color="sidebarBackground"
      :sidebar-background-image="sidebarBackgroundImage"
    >
      
      <!-- Link alla dashboard -->
      <sidebar-link to="/dashboard">
        <md-icon>home</md-icon>
        <p>Dashboard</p>
      </sidebar-link>


      <!-- Link per aggiungere un dipendente -->
      <sidebar-link to="/aggiungidipendente">
        <md-icon>person_add</md-icon>
        <p>Aggiungi Dipendente</p>
      </sidebar-link>

      <!-- Link per aggiungere un cliente -->
      <sidebar-link to="/aggiungicliente">
        <md-icon>food_bank</md-icon>
        <p>Aggiungi Cliente</p>
      </sidebar-link>

       <!-- Link per aggiungere un cliente -->
       <sidebar-link to="/aggiungievento">
        <md-icon>food_bank</md-icon>
        <p>Aggiungi Evento</p>
      </sidebar-link>

      <!-- Link alla sezione delle ore -->
      <sidebar-link to="/ore">
        <md-icon>access_time</md-icon>
        <p>Ore</p>
      </sidebar-link>

      <!-- Link al calendario -->
      <sidebar-link to="/calendario">
        <md-icon>calendar_today</md-icon>
        <p>Calendario</p>
      </sidebar-link>
    </side-bar>

    <!-- Pannello principale -->
    <div class="main-panel">
      <!-- Contenuto della dashboard -->
      <router-view></router-view>

      <!-- Footer del contenuto -->
      <content-footer v-if="!$route.meta.hideFooter"></content-footer>
    </div>
  </div>
</template>

<script>
import ContentFooter from "./ContentFooter.vue";

export default {
  components: {
    ContentFooter,
  },
  data() {
    return {
      sidebarBackground: "green",
      sidebarBackgroundImage: require("@/assets/img/sidebar-2.jpg"),
    };
  },
};
</script>
