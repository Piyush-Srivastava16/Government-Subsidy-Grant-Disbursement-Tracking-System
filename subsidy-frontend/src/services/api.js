import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

// Automatically attach logged-in user's Basic Auth
api.interceptors.request.use(
  (config) => {
    const username = localStorage.getItem("username");
    const password = localStorage.getItem("password");

    if (username && password) {
      config.auth = {
        username,
        password,
      };
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
