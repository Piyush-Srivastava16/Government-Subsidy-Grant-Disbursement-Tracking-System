import { Navigate } from "react-router-dom";

function ProtectedRoute({ children }) {
  const username = localStorage.getItem("username");
  const password = localStorage.getItem("password");

  if (!username || !password) {
    return <Navigate to="/login" replace />;
  }

  return children;
}

export default ProtectedRoute;