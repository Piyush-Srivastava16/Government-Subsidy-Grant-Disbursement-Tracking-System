import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleLogin = async (event) => {
    event.preventDefault();
    setError("");

    try {
      // Verify login using common authenticated endpoint
      const response = await api.get("/api/auth/me", {
        auth: {
          username,
          password,
        },
      });

      const userRole = response.data.role;

      // Store credentials and role
      localStorage.setItem("username", username);
      localStorage.setItem("password", password);
      localStorage.setItem("role", userRole);

      // Redirect according to role
      if (userRole === "ADMIN") {
        navigate("/dashboard");
      } else if (userRole === "FIELD_OFFICER") {
        navigate("/beneficiaries");
      } else if (userRole === "DISTRICT_OFFICER") {
        navigate("/verifications");
      } else if (userRole === "FINANCE_APPROVER") {
        navigate("/disbursements");
      } else {
        navigate("/login");
      }

    } catch (error) {
      console.error("Login failed:", error);

      if (error.response?.status === 401) {
        setError("Invalid username or password.");
      } else if (error.response?.status === 403) {
        setError("You do not have permission to access this application.");
      } else {
        setError("Unable to connect to server.");
      }
    }
  };

  return (
    <div className="login-page">

      <div className="login-card">

        <h1>Subsidy Grant System</h1>

        <h2>Login</h2>

        <form onSubmit={handleLogin}>

          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(event) =>
              setUsername(event.target.value)
            }
            required
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(event) =>
              setPassword(event.target.value)
            }
            required
          />

          <button type="submit">
            Login
          </button>

        </form>

        {error && (
          <p className="login-error">
            {error}
          </p>
        )}

      </div>

    </div>
  );
}

export default Login;