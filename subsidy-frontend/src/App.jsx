import { useEffect, useState } from "react";
import { Routes, Route, Navigate } from "react-router-dom";

import api from "./services/api";

import Sidebar from "./components/Sidebar";
import Header from "./components/Header";
import ProtectedRoute from "./components/ProtectedRoute";
import RoleProtectedRoute from "./components/RoleProtectedRoute";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Beneficiaries from "./pages/Beneficiaries";
import Applications from "./pages/Applications";
import Documents from "./pages/Documents";
import Verifications from "./pages/Verifications";
import Milestones from "./pages/Milestones";
import Disbursements from "./pages/Disbursements";
import FundUtilizations from "./pages/FundUtilizations";
import Analytics from "./pages/Analytics";
import Schemes from "./pages/Schemes";
import Users from "./pages/Users";

function App() {

  const [userRole, setUserRole] = useState("");
  const [loadingRole, setLoadingRole] = useState(true);

  useEffect(() => {

    const loadCurrentUser = async () => {

      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");

      if (!username || !password) {
        setLoadingRole(false);
        return;
      }

      try {

        const response =
          await api.get("/api/auth/me");

        setUserRole(response.data.role);

      } catch (error) {

        console.error(
          "Error fetching current user:",
          error
        );

        setUserRole("");

      } finally {

        setLoadingRole(false);
      }
    };

    loadCurrentUser();

  }, []);

  return (
    <Routes>

      {/* Public Route */}
      <Route
        path="/login"
        element={<Login />}
      />

      {/* Protected Application */}
      <Route
        path="/*"
        element={
          <ProtectedRoute>

            {loadingRole ? (

              <div className="dashboard">
                <h1>Loading...</h1>
              </div>

            ) : (

              <div className="app-container">

                <Sidebar />

                <div className="main-content">

                  <Header />

                  <Routes>

                    {/* Dashboard */}

                    <Route
                      path="/"
                      element={
                        <RoleProtectedRoute
                          allowedRoles={["ADMIN"]}
                          userRole={userRole}
                        >
                          <Dashboard />
                        </RoleProtectedRoute>
                      }
                    />

                    <Route
                      path="/dashboard"
                      element={
                        <RoleProtectedRoute
                          allowedRoles={["ADMIN"]}
                          userRole={userRole}
                        >
                          <Dashboard />
                        </RoleProtectedRoute>
                      }
                    />

                    {/* Beneficiaries */}

                    <Route
                      path="/beneficiaries"
                      element={
                        <RoleProtectedRoute
                          allowedRoles={[
                            "ADMIN",
                            "FIELD_OFFICER"
                          ]}
                          userRole={userRole}
                        >
                          <Beneficiaries />
                        </RoleProtectedRoute>
                      }
                    />

                    {/* Applications */}

                    <Route
                      path="/applications"
                      element={
                        <RoleProtectedRoute
                          allowedRoles={[
                            "ADMIN",
                            "FIELD_OFFICER"
                          ]}
                          userRole={userRole}
                        >
                          <Applications />
                        </RoleProtectedRoute>
                      }
                    />

                    {/* Documents */}

                    <Route
                      path="/documents"
                      element={
                        <RoleProtectedRoute
                          allowedRoles={[
                            "ADMIN",
                            "FIELD_OFFICER"
                          ]}
                          userRole={userRole}
                        >
                          <Documents />
                        </RoleProtectedRoute>
                      }
                    />

                    {/* Verifications */}

                    <Route
                      path="/verifications"
                      element={
                        <RoleProtectedRoute
                          allowedRoles={[
                            "ADMIN",
                            "DISTRICT_OFFICER"
                          ]}
                          userRole={userRole}
                        >
                          <Verifications />
                        </RoleProtectedRoute>
                      }
                    />

                    {/* Milestones */}

                    <Route
                      path="/milestones"
                      element={
                        <RoleProtectedRoute
                          allowedRoles={[
                            "ADMIN",
                            "DISTRICT_OFFICER"
                          ]}
                          userRole={userRole}
                        >
                          <Milestones />
                        </RoleProtectedRoute>
                      }
                    />

                    {/* Disbursements */}

                    <Route
                      path="/disbursements"
                      element={
                        <RoleProtectedRoute
                          allowedRoles={[
                            "ADMIN",
                            "FINANCE_APPROVER"
                          ]}
                          userRole={userRole}
                        >
                          <Disbursements />
                        </RoleProtectedRoute>
                      }
                    />

                    {/* Fund Utilization */}

                    <Route
                      path="/fund-utilizations"
                      element={
                        <RoleProtectedRoute
                          allowedRoles={[
                            "ADMIN",
                            "FINANCE_APPROVER"
                          ]}
                          userRole={userRole}
                        >
                          <FundUtilizations />
                        </RoleProtectedRoute>
                      }
                    />

                    {/* Analytics */}

                    <Route
                      path="/analytics"
                      element={
                        <RoleProtectedRoute
                          allowedRoles={["ADMIN"]}
                          userRole={userRole}
                        >
                          <Analytics />
                        </RoleProtectedRoute>
                      }
                    />

                    {/* Schemes */}

                    <Route
                      path="/schemes"
                      element={
                        <RoleProtectedRoute
                          allowedRoles={["ADMIN"]}
                          userRole={userRole}
                        >
                          <Schemes />
                        </RoleProtectedRoute>
                      }
                    />

                    {/* Users */}

                    <Route
                      path="/users"
                      element={
                        <RoleProtectedRoute
                          allowedRoles={["ADMIN"]}
                          userRole={userRole}
                        >
                          <Users />
                        </RoleProtectedRoute>
                      }
                    />

                    {/* Unknown Route */}

                    <Route
                      path="*"
                      element={
                        <Navigate
                          to="/dashboard"
                          replace
                        />
                      }
                    />

                  </Routes>

                </div>

              </div>
            )}

          </ProtectedRoute>
        }
      />

    </Routes>
  );
}

export default App;