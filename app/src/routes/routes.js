import DashboardLayout from "@/pages/Layout/DashboardLayout.vue";
import Dashboard from "@/pages/Dashboard.vue";
import UserProfile from "@/pages/UserProfile.vue";
import AggiungiDipendente from "@/pages/AggiungiDipendente.vue";
import AggiungiEvento from "@/pages/AggiungiEvento.vue";
import AggiungiCliente from "@/pages/AggiungiCliente.vue";
import AggiungiDate from "@/pages/AggiungiDate.vue";
import Ore from "@/pages/Ore.vue";
import Calendario from "@/pages/Calendario.vue";
import Icons from "@/pages/Icons.vue";
import Maps from "@/pages/Maps.vue";
import Notifications from "@/pages/Notifications.vue";
import LoginPage from "@/pages/LoginPage.vue"

const routes = [
  {
    path: "/login",
    name: "Login",
    component: LoginPage,
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: "/",
    component: DashboardLayout,
    redirect: "dashboard",
    children: [
      {
        path: "dashboard",
        name: "Dashboard",
        component: Dashboard,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "user",
        name: "User Profile",
        component: UserProfile,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "aggiungidipendente",
        name: "Aggiungi Dipendente",
        component: AggiungiDipendente,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "aggiungidate",
        name: "Aggiungi Date",
        component: AggiungiDate,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "aggiungievento",
        name: "Aggiungi Evento",
        component: AggiungiEvento,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "ore",
        name: "Ore",
        component: Ore,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "calendario",
        name: "Calendario",
        component: Calendario,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "aggiungicliente",
        name: "Aggiungi Cliente",
        component: AggiungiCliente,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "icons",
        name: "Icons",
        component: Icons,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "maps",
        name: "Maps",
        meta: {
          requiresAuth: true,
          hideFooter: true,
        },
        component: Maps,
      },
      {
        path: "notifications",
        name: "Notifications",
        component: Notifications,
        meta: {
          requiresAuth: true,
        },
      },

    ],
  },
];

export default routes;
