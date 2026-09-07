import { useEffect, useState } from "react";
import api from "../services/api";

function Analytics() {
  const [regionBeneficiaries, setRegionBeneficiaries] = useState({});
  const [applicationStatus, setApplicationStatus] = useState({});

  const [totalApplications, setTotalApplications] = useState(0);
  const [approvedApplications, setApprovedApplications] = useState(0);
  const [pendingApplications, setPendingApplications] = useState(0);
  const [totalFundReleased, setTotalFundReleased] = useState(0);
  const [totalFundUtilized, setTotalFundUtilized] = useState(0);

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadAnalytics = async () => {
      try {
        const [
          beneficiariesResponse,
          statusResponse,
          totalApplicationsResponse,
          approvedApplicationsResponse,
          pendingApplicationsResponse,
          releasedFundResponse,
          utilizedFundResponse
        ] = await Promise.all([
          api.get("/api/analytics/beneficiaries-by-region"),
          api.get("/api/analytics/applications-by-status"),
          api.get("/api/analytics/total-applications"),
          api.get("/api/analytics/approved-applications"),
          api.get("/api/analytics/pending-applications"),
          api.get("/api/analytics/total-fund-released"),
          api.get("/api/analytics/total-fund-utilized")
        ]);

        setRegionBeneficiaries(beneficiariesResponse.data);
        setApplicationStatus(statusResponse.data);

        setTotalApplications(totalApplicationsResponse.data);
        setApprovedApplications(approvedApplicationsResponse.data);
        setPendingApplications(pendingApplicationsResponse.data);

        setTotalFundReleased(
          releasedFundResponse.data || 0
        );

        setTotalFundUtilized(
          utilizedFundResponse.data || 0
        );

      } catch (error) {
        console.error("Error fetching analytics:", error);

        if (error.response) {
          console.error(
            "Server response:",
            error.response.data
          );
        }
      } finally {
        setLoading(false);
      }
    };

    loadAnalytics();
  }, []);

  if (loading) {
    return (
      <div className="dashboard">
        <h1>Analytics</h1>
        <p>Loading analytics...</p>
      </div>
    );
  }

  return (
    <div className="dashboard">

      <h1>Analytics</h1>

      {/* Summary Cards */}

      <div className="dashboard-cards">

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
          <h3>Total Fund Released</h3>
          <p>
            ₹{Number(totalFundReleased).toLocaleString()}
          </p>
        </div>

        <div className="card">
          <h3>Total Fund Utilized</h3>
          <p>
            ₹{Number(totalFundUtilized).toLocaleString()}
          </p>
        </div>

      </div>


      {/* Beneficiaries by Region */}

      <div className="analytics-section">

        <h2>Beneficiaries by Region</h2>

        <div className="analytics-grid">

          {Object.entries(regionBeneficiaries).length > 0 ? (
            Object.entries(regionBeneficiaries).map(
              ([region, count]) => (

                <div className="card" key={region}>

                  <h3>{region}</h3>

                  <p>{count}</p>

                </div>

              )
            )
          ) : (
            <p>No regional beneficiary data available.</p>
          )}

        </div>

      </div>


      {/* Applications by Status */}

      <div className="analytics-section">

        <h2>Applications by Status</h2>

        <div className="analytics-grid">

          {Object.entries(applicationStatus).length > 0 ? (
            Object.entries(applicationStatus).map(
              ([status, count]) => (

                <div className="card" key={status}>

                  <h3>
                    {status.replaceAll("_", " ")}
                  </h3>

                  <p>{count}</p>

                </div>

              )
            )
          ) : (
            <p>No application status data available.</p>
          )}

        </div>

      </div>


      {/* Fund Summary */}

      <div className="analytics-section">

        <h2>Fund Summary</h2>

        <div className="table-container">

          <table>

            <thead>
              <tr>
                <th>Metric</th>
                <th>Amount</th>
              </tr>
            </thead>

            <tbody>

              <tr>
                <td>Total Fund Released</td>
                <td>
                  ₹{Number(
                    totalFundReleased
                  ).toLocaleString()}
                </td>
              </tr>

              <tr>
                <td>Total Fund Utilized</td>
                <td>
                  ₹{Number(
                    totalFundUtilized
                  ).toLocaleString()}
                </td>
              </tr>

              <tr>
                <td>Remaining Fund</td>
                <td>
                  ₹{Number(
                    totalFundReleased -
                    totalFundUtilized
                  ).toLocaleString()}
                </td>
              </tr>

            </tbody>

          </table>

        </div>

      </div>

    </div>
  );
}

export default Analytics;