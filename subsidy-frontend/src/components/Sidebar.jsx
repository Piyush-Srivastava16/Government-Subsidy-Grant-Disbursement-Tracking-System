import { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import api from "../services/api";

function Sidebar() {
  const [role, setRole] = useState("");

  useEffect(() => {
    const loadUser = async () => {
      try {
        const response = await api.get("/api/auth/me");
        setRole(response.data.role);
      } catch (error) {
        console.error("Error fetching current user:", error);
      }
    };

    loadUser();
  }, []);

  const isAdmin = role === "ADMIN";
  const isFieldOfficer = role === "FIELD_OFFICER";
  const isDistrictOfficer = role === "DISTRICT_OFFICER";
  const isFinanceApprover = role === "FINANCE_APPROVER";

  return (
    <div className="sidebar">

      <h2>Subsidy Admin</h2>

      <ul>

        {/* ADMIN */}
        {isAdmin && (
          <>
            <li>
              <NavLink to="/dashboard">
                📊 Dashboard
              </NavLink>
            </li>

            <li>
              <NavLink to="/beneficiaries">
                👥 Beneficiaries
              </NavLink>
            </li>

            <li>
              <NavLink to="/applications">
                📄 Applications
              </NavLink>
            </li>

            <li>
              <NavLink to="/documents">
                📄 Documents
              </NavLink>
            </li>

            <li>
              <NavLink to="/schemes">
                🏛️ Schemes
              </NavLink>
            </li>

            <li>
              <NavLink to="/verifications">
                ✅ Verifications
              </NavLink>
            </li>

            <li>
              <NavLink to="/milestones">
                🎯 Milestones
              </NavLink>
            </li>

            <li>
              <NavLink to="/disbursements">
                💰 Disbursements
              </NavLink>
            </li>

            <li>
              <NavLink to="/fund-utilizations">
                📋 Fund Utilization
              </NavLink>
            </li>

            <li>
              <NavLink to="/analytics">
                📈 Analytics
              </NavLink>
            </li>

            <li>
              <NavLink to="/users">
                👤 Users
              </NavLink>
            </li>
          </>
        )}

        {/* FIELD OFFICER */}
        {isFieldOfficer && (
          <>
            <li>
              <NavLink to="/beneficiaries">
                👥 Beneficiaries
              </NavLink>
            </li>

            <li>
              <NavLink to="/applications">
                📄 Applications
              </NavLink>
            </li>

            <li>
              <NavLink to="/documents">
                📄 Documents
              </NavLink>
            </li>
          </>
        )}

        {/* DISTRICT OFFICER */}
        {isDistrictOfficer && (
          <>
            <li>
              <NavLink to="/verifications">
                ✅ Verifications
              </NavLink>
            </li>

            <li>
              <NavLink to="/milestones">
                🎯 Milestones
              </NavLink>
            </li>
          </>
        )}

        {/* FINANCE APPROVER */}
        {isFinanceApprover && (
          <>
            <li>
              <NavLink to="/disbursements">
                💰 Disbursements
              </NavLink>
            </li>

            <li>
              <NavLink to="/fund-utilizations">
                📋 Fund Utilization
              </NavLink>
            </li>
          </>
        )}

      </ul>

    </div>
  );
}

export default Sidebar;