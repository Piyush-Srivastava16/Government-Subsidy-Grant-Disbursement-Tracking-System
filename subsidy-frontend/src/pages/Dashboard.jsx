
import { useEffect, useState } from "react";
import api from "../services/api";

function Dashboard() {
  const [dashboard, setDashboard] = useState(null);

  useEffect(() => {
    const fetchDashboard = async () => {
      try {
        const response = await api.get(
          "/api/dashboard/summary"
        );

        setDashboard(response.data);
      } catch (error) {
        console.error(
          "Error fetching dashboard data:",
          error
        );
      }
    };

    fetchDashboard();
  }, []);

  if (!dashboard) {
    return (
      <div className="dashboard">
        <h1>Dashboard Overview</h1>
        <p>Loading dashboard data...</p>
      </div>
    );
  }

  const totalBeneficiaries =
    dashboard.totalBeneficiaries ?? 0;

  const totalApplications =
    dashboard.totalApplications ?? 0;

  const approvedApplications =
    dashboard.approvedApplications ?? 0;

  const pendingApplications =
    dashboard.pendingApplications ?? 0;

  const totalDisbursements =
    dashboard.totalDisbursements ?? 0;

  const totalFundReleased =
    dashboard.totalDisbursementAmount ?? 0;

  const totalFundUtilized =
    dashboard.totalFundUtilized ?? 0;

  const utilizationPercentage =
    totalFundReleased > 0
      ? Math.min(
          Math.round(
            (totalFundUtilized / totalFundReleased) * 100
          ),
          100
        )
      : 0;

  const approvedPercentage =
    totalApplications > 0
      ? Math.round(
          (approvedApplications / totalApplications) * 100
        )
      : 0;

  const pendingPercentage =
    totalApplications > 0
      ? Math.round(
          (pendingApplications / totalApplications) * 100
        )
      : 0;

  const formatCurrency = (amount) =>
    `₹${Number(amount).toLocaleString("en-IN")}`;

  return (
    <div className="dashboard">

      <h1>Dashboard Overview</h1>

      {/* Summary Cards */}

      <div className="dashboard-cards">

        <div className="card">
          <h3>Total Beneficiaries</h3>
          <p>{totalBeneficiaries}</p>
        </div>

        <div className="card">
          <h3>Total Applications</h3>
          <p>{totalApplications}</p>
        </div>

        <div className="card">
          <h3>Approved Applications</h3>
          <p>{approvedApplications}</p>
        </div>

        <div className="card">
          <h3>Pending Applications</h3>
          <p>{pendingApplications}</p>
        </div>

        <div className="card">
          <h3>Total Disbursements</h3>
          <p>{totalDisbursements}</p>
        </div>

        <div className="card">
          <h3>Total Fund Released</h3>
          <p>{formatCurrency(totalFundReleased)}</p>
        </div>

        <div className="card">
          <h3>Total Fund Utilized</h3>
          <p>{formatCurrency(totalFundUtilized)}</p>
        </div>

        <div className="card">
          <h3>Fund Utilization</h3>
          <p>{utilizationPercentage}%</p>
        </div>

      </div>


      {/* Application Status */}

      <div className="dashboard-chart-container">

        <h2>Application Status</h2>

        <div className="status-row">

          <div className="status-item">

            <div className="status-header">
              <span>Approved</span>
              <strong>
                {approvedApplications}
              </strong>
            </div>

            <div className="progress-bar">
              <div
                className="progress-fill"
                style={{
                  width: `${approvedPercentage}%`
                }}
              />
            </div>

            <span>{approvedPercentage}%</span>

          </div>


          <div className="status-item">

            <div className="status-header">
              <span>Pending</span>
              <strong>
                {pendingApplications}
              </strong>
            </div>

            <div className="progress-bar">
              <div
                className="progress-fill"
                style={{
                  width: `${pendingPercentage}%`
                }}
              />
            </div>

            <span>{pendingPercentage}%</span>

          </div>

        </div>

      </div>


      {/* Fund Utilization */}

      <div className="fund-utilization-box">

        <div className="status-header">
          <span>Fund Utilization</span>

          <strong>
            {utilizationPercentage}%
          </strong>
        </div>

        <div className="progress-bar">

          <div
            className="progress-fill"
            style={{
              width: `${utilizationPercentage}%`
            }}
          />

        </div>

        <p>
          {formatCurrency(totalFundUtilized)} utilized out of{" "}
          {formatCurrency(totalFundReleased)}
        </p>

      </div>

    </div>
  );
}

export default Dashboard;

