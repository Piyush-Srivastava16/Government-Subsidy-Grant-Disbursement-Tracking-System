import { useNavigate } from "react-router-dom";

function Header() {
  const navigate = useNavigate();

  const username = localStorage.getItem("username");
  const role = localStorage.getItem("role");

  const handleLogout = () => {
    localStorage.removeItem("username");
    localStorage.removeItem("password");
    localStorage.removeItem("role");

    navigate("/login");
  };

  return (
    <div className="header">

      <h2>Dashboard</h2>

      <div className="user-info">

        <span>
          Welcome, {username || "User"} 👋
          {role && ` (${role})`}
        </span>

        <button
          className="logout-button"
          onClick={handleLogout}
        >
          Logout
        </button>

      </div>

    </div>
  );
}

export default Header;