import { useEffect, useState } from "react";
import api from "../services/api";

function Disbursements() {
  const [disbursements, setDisbursements] = useState([]);
  const [applications, setApplications] = useState([]);
  const [milestones, setMilestones] = useState([]);

  const [searchTerm, setSearchTerm] = useState("");
  const [statusFilter, setStatusFilter] = useState("");

  const [formData, setFormData] = useState({
    amount: "",
    disbursementDate: "",
    status: "",
    applicationId: "",
    milestoneId: ""
  });

  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchDisbursements = async () => {
    try {
      const response = await api.get("/api/disbursements");
      setDisbursements(response.data);
    } catch (error) {
      console.error(
        "Error fetching disbursements:",
        error
      );
    }
  };

  const fetchApplications = async () => {
    try {
      const response = await api.get("/api/applications");
      setApplications(response.data);
    } catch (error) {
      console.error(
        "Error fetching applications:",
        error
      );
    }
  };

  const fetchMilestones = async () => {
    try {
      const response = await api.get("/api/milestones");
      setMilestones(response.data);
    } catch (error) {
      console.error(
        "Error fetching milestones:",
        error
      );
    }
  };

  useEffect(() => {
    const loadData = async () => {
      setLoading(true);

      await Promise.all([
        fetchDisbursements(),
        fetchApplications(),
        fetchMilestones()
      ]);

      setLoading(false);
    };

    loadData();
  }, []);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData({
      ...formData,
      [name]: value
    });
  };

  const resetForm = () => {
    setFormData({
      amount: "",
      disbursementDate: "",
      status: "",
      applicationId: "",
      milestoneId: ""
    });

    setEditingId(null);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const disbursementData = {
      amount: Number(formData.amount),
      disbursementDate: formData.disbursementDate,
      status: formData.status,
      application: {
        id: Number(formData.applicationId)
      },
      milestone: {
        id: Number(formData.milestoneId)
      }
    };

    try {
      if (editingId) {
        await api.put(
          `/api/disbursements/${editingId}`,
          disbursementData
        );
      } else {
        await api.post(
          "/api/disbursements",
          disbursementData
        );
      }

      resetForm();
      await fetchDisbursements();

    } catch (error) {
      console.error(
        "Error saving disbursement:",
        error
      );
    }
  };

  const handleEdit = (disbursement) => {
    setEditingId(disbursement.id);

    setFormData({
      amount: disbursement.amount ?? "",
      disbursementDate:
        disbursement.disbursementDate || "",
      status:
        disbursement.status || "",
      applicationId:
        disbursement.application?.id ?? "",
      milestoneId:
        disbursement.milestone?.id ?? ""
    });
  };

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this disbursement?"
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await api.delete(
        `/api/disbursements/${id}`
      );

      await fetchDisbursements();

    } catch (error) {
      console.error(
        "Error deleting disbursement:",
        error
      );
    }
  };

  // Search + Status Filter
  const filteredDisbursements =
    disbursements.filter((disbursement) => {

      const applicationId =
        String(
          disbursement.application?.id || ""
        );

      const matchesSearch =
        applicationId.includes(
          searchTerm.trim()
        );

      const matchesStatus =
        statusFilter === "" ||
        disbursement.status === statusFilter;

      return (
        matchesSearch &&
        matchesStatus
      );
    });

  if (loading) {
    return (
      <div className="dashboard">
        <h1>Disbursements</h1>
        <p>Loading disbursements...</p>
      </div>
    );
  }

  return (
    <div className="dashboard">

      <h1>Disbursement Management</h1>

      {/* Form */}

      <div className="form-container">

        <h2>
          {editingId
            ? "Update Disbursement"
            : "Create Disbursement"}
        </h2>

        <form onSubmit={handleSubmit}>

          <input
            type="number"
            step="0.01"
            min="0"
            name="amount"
            placeholder="Disbursement Amount"
            value={formData.amount}
            onChange={handleChange}
            required
          />

          <input
            type="date"
            name="disbursementDate"
            value={formData.disbursementDate}
            onChange={handleChange}
            required
          />

          <select
            name="status"
            value={formData.status}
            onChange={handleChange}
            required
          >
            <option value="">
              Select Status
            </option>

            <option value="PENDING">
              PENDING
            </option>

            <option value="RELEASED">
              RELEASED
            </option>

            <option value="CANCELLED">
              CANCELLED
            </option>
          </select>

          <select
            name="applicationId"
            value={formData.applicationId}
            onChange={handleChange}
            required
          >
            <option value="">
              Select Application
            </option>

            {applications.map((application) => (
              <option
                key={application.id}
                value={application.id}
              >
                Application ID: {application.id}
              </option>
            ))}
          </select>

          <select
            name="milestoneId"
            value={formData.milestoneId}
            onChange={handleChange}
            required
          >
            <option value="">
              Select Milestone
            </option>

            {milestones.map((milestone) => (
              <option
                key={milestone.id}
                value={milestone.id}
              >
                {milestone.milestoneName} (ID:{" "}
                {milestone.id})
              </option>
            ))}
          </select>

          <button type="submit">
            {editingId
              ? "Update Disbursement"
              : "Create Disbursement"}
          </button>

          {editingId && (
            <button
              type="button"
              onClick={resetForm}
            >
              Cancel
            </button>
          )}

        </form>
      </div>

      {/* Search + Filter */}

      <div className="table-toolbar">

        <input
          type="text"
          placeholder="🔍 Search by application ID..."
          value={searchTerm}
          onChange={(event) =>
            setSearchTerm(
              event.target.value
            )
          }
        />

        <select
          value={statusFilter}
          onChange={(event) =>
            setStatusFilter(
              event.target.value
            )
          }
        >
          <option value="">
            All Statuses
          </option>

          <option value="PENDING">
            PENDING
          </option>

          <option value="RELEASED">
            RELEASED
          </option>

          <option value="CANCELLED">
            CANCELLED
          </option>
        </select>

      </div>

      {/* Table */}

      <div className="table-container">

        <table>

          <thead>
            <tr>
              <th>ID</th>
              <th>Amount</th>
              <th>Date</th>
              <th>Status</th>
              <th>Application</th>
              <th>Milestone</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>

            {filteredDisbursements.length > 0 ? (

              filteredDisbursements.map(
                (disbursement) => (

                  <tr
                    key={disbursement.id}
                  >

                    <td>
                      {disbursement.id}
                    </td>

                    <td>
                      ₹
                      {disbursement.amount}
                    </td>

                    <td>
                      {disbursement.disbursementDate}
                    </td>

                    <td>

                      <span
                        className={`status-badge status-${disbursement.status?.toLowerCase()}`}
                      >
                        {disbursement.status}
                      </span>

                    </td>

                    <td>
                      {disbursement.application?.id}
                    </td>

                    <td>
                      {disbursement.milestone
                        ?.milestoneName ||
                        disbursement.milestone
                          ?.id}
                    </td>

                    <td>

                      <button
                        onClick={() =>
                          handleEdit(
                            disbursement
                          )
                        }
                      >
                        Edit
                      </button>

                      <button
                        onClick={() =>
                          handleDelete(
                            disbursement.id
                          )
                        }
                      >
                        Delete
                      </button>

                    </td>

                  </tr>

                )
              )

            ) : (

              <tr>
                <td colSpan="7">
                  No disbursements found
                </td>
              </tr>

            )}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default Disbursements;