import axios from 'axios';

// Crea un'istanza di Axios con le configurazioni di base
const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080', // Sostituisci con l'URL dell'API
  timeout: 5000, // Imposta il timeout desiderato
});


// Funzione per impostare l'header di autorizzazione
function setAuthorizationHeader(token) {
  if (token) {
    axiosInstance.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    console.log('Token di accesso:', token);
  } else {
    delete axiosInstance.defaults.headers.common['Authorization'];
    console.log('Token di accesso non presente');
  }
}

// Interceptor per le richieste
axiosInstance.interceptors.request.use(
  (config) => {
    const token = sessionStorage.getItem('token');
    setAuthorizationHeader(token);
    return config;
  },
  (error) => {
    console.log('errore');
    return Promise.reject(error);
  }
);

export default axiosInstance;