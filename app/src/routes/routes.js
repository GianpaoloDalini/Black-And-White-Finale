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

const routes = [
  {
    path: "/",
    component: DashboardLayout,
    redirect: "/dashboard",
    children: [
      {
        path: "dashboard",
        name: "Dashboard",
        component: Dashboard,
      },
      {
        path: "user",
        name: "User Profile",
        component: UserProfile,
      },
      {
        path: "aggiungidipendente",
        name: "Aggiungi Dipendente",
        component: AggiungiDipendente,
      },
      {
        path: "aggiungidate",
        name: "Aggiungi Date",
        component: AggiungiDate,
      },
      {
        path: "aggiungievento",
        name: "Aggiungi Evento",
        component: AggiungiEvento,
      },
      {
        path: "ore",
        name: "Ore",
        component: Ore,
      },
      {
        path: "calendario",
        name: "Calendario",
        component: Calendario,
      },
      {
        path: "aggiungicliente",
        name: "Aggiungi Cliente",
        component: AggiungiCliente,
      },
      {
        path: "icons",
        name: "Icons",
        component: Icons,
      },
      {
        path: "maps",
        name: "Maps",
        meta: {
          hideFooter: true,
        },
        component: Maps,
      },
      {
        path: "notifications",
        name: "Notifications",
        component: Notifications,
      },
    ],
  },
];

export default routes;
