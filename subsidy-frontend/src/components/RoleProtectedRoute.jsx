import { Navigate } from "react-router-dom";

function RoleProtectedRoute({ allowedRoles, userRole, children }) {
  if (!userRole) {
    return <p>Loading...</p>;
  }

  if (!allowedRoles.includes(userRole)) {
    return <Navigate to="/dashboard" replace />;
  }

  return children;
}

export default RoleProtectedRoute;