import { useEffect, useState } from "react";
import api from "../services/api";

function Applications() {
  const [applications, setApplications] = useState([]);
  const [beneficiaries, setBeneficiaries] = useState([]);
  const [schemes, setSchemes] = useState([]);

  const [searchTerm, setSearchTerm] = useState("");
  const [statusFilter, setStatusFilter] = useState("");

  const [formData, setFormData] = useState({
    applicationDate: "",
    status: "",
    eligibilityScore: "",
    beneficiaryId: "",
    schemeId: ""
  });

  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(true);

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

  const fetchBeneficiaries = async () => {
    try {
      const response = await api.get("/api/beneficiaries");
      setBeneficiaries(response.data);
    } catch (error) {
      console.error(
        "Error fetching beneficiaries:",
        error
      );
    }
  };

  const fetchSchemes = async () => {
    try {
      const response = await api.get("/api/schemes");
      setSchemes(response.data);
    } catch (error) {
      console.error(
        "Error fetching schemes:",
        error
      );
    }
  };

  useEffect(() => {
    const loadData = async () => {
      setLoading(true);

      await Promise.all([
        fetchApplications(),
        fetchBeneficiaries(),
        fetchSchemes()
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
      applicationDate: "",
      status: "",
      eligibilityScore: "",
      beneficiaryId: "",
      schemeId: ""
    });

    setEditingId(null);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const applicationData = {
      applicationDate: formData.applicationDate,
      status: formData.status,
      eligibilityScore: Number(
        formData.eligibilityScore
      ),
      beneficiary: {
        id: Number(formData.beneficiaryId)
      },
      scheme: {
        id: Number(formData.schemeId)
      }
    };

    try {
      if (editingId) {
        await api.put(
          `/api/applications/${editingId}`,
          applicationData
        );
      } else {
        await api.post(
          "/api/applications",
          applicationData
        );
      }

      resetForm();
      await fetchApplications();

    } catch (error) {
      console.error(
        "Error saving application:",
        error
      );
    }
  };

  const handleEdit = (application) => {
    setEditingId(application.id);

    setFormData({
      applicationDate:
        application.applicationDate || "",

      status:
        application.status || "",

      eligibilityScore:
        application.eligibilityScore ?? "",

      beneficiaryId:
        application.beneficiary?.id ?? "",

      schemeId:
        application.scheme?.id ?? ""
    });
  };

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this application?"
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await api.delete(
        `/api/applications/${id}`
      );

      await fetchApplications();

    } catch (error) {
      console.error(
        "Error deleting application:",
        error
      );
    }
  };

  // Search + status filter
  const filteredApplications =
    applications.filter((application) => {

      const beneficiaryName =
        application.beneficiary?.name || "";

      const matchesSearch =
        beneficiaryName
          .toLowerCase()
          .includes(
            searchTerm.toLowerCase()
          );

      const matchesStatus =
        statusFilter === "" ||
        application.status === statusFilter;

      return (
        matchesSearch &&
        matchesStatus
      );
    });

  if (loading) {
    return (
      <div className="dashboard">
        <h1>Applications</h1>
        <p>
          Loading applications...
        </p>
      </div>
    );
  }

  return (
    <div className="dashboard">

      <h1>Application Management</h1>

      {/* Form */}

      <div className="form-container">

        <h2>
          {editingId
            ? "Update Application"
            : "Create Application"}
        </h2>

        <form onSubmit={handleSubmit}>

          <input
            type="date"
            name="applicationDate"
            value={formData.applicationDate}
            onChange={handleChange}
            required
          />

          <select
            name="beneficiaryId"
            value={formData.beneficiaryId}
            onChange={handleChange}
            required
          >
            <option value="">
              Select Beneficiary
            </option>

            {beneficiaries.map(
              (beneficiary) => (
                <option
                  key={beneficiary.id}
                  value={beneficiary.id}
                >
                  {beneficiary.name}
                  {" "}
                  (ID: {beneficiary.id})
                </option>
              )
            )}
          </select>

          <select
            name="schemeId"
            value={formData.schemeId}
            onChange={handleChange}
            required
          >
            <option value="">
              Select Scheme
            </option>

            {schemes.map((scheme) => (
              <option
                key={scheme.id}
                value={scheme.id}
              >
                {scheme.name}
                {" "}
                (ID: {scheme.id})
              </option>
            ))}
          </select>

          <input
            type="number"
            step="0.01"
            name="eligibilityScore"
            placeholder="Eligibility Score"
            value={formData.eligibilityScore}
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

            <option value="SUBMITTED">
              SUBMITTED
            </option>

            <option value="APPROVED">
              APPROVED
            </option>

            <option value="REJECTED">
              REJECTED
            </option>
          </select>

          <button type="submit">
            {editingId
              ? "Update Application"
              : "Create Application"}
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
          placeholder="🔍 Search by beneficiary..."
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

          <option value="SUBMITTED">
            SUBMITTED
          </option>

          <option value="APPROVED">
            APPROVED
          </option>

          <option value="REJECTED">
            REJECTED
          </option>
        </select>

      </div>

      {/* Table */}

      <div className="table-container">

        <table>

          <thead>
            <tr>
              <th>ID</th>
              <th>Date</th>
              <th>Beneficiary</th>
              <th>Scheme</th>
              <th>Eligibility Score</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>

            {filteredApplications.length > 0 ? (

              filteredApplications.map(
                (application) => (
                  <tr
                    key={application.id}
                  >

                    <td>
                      {application.id}
                    </td>

                    <td>
                      {application.applicationDate}
                    </td>

                    <td>
                      {application.beneficiary?.name ||
                        `ID ${application.beneficiary?.id}`}
                    </td>

                    <td>
                      {application.scheme?.name ||
                        `ID ${application.scheme?.id}`}
                    </td>

                    <td>
                      {application.eligibilityScore}
                    </td>

                    <td>

                      <span
                        className={`status-badge status-${application.status?.toLowerCase()}`}
                      >
                        {application.status}
                      </span>

                    </td>

                    <td>

                      <button
                        onClick={() =>
                          handleEdit(
                            application
                          )
                        }
                      >
                        Edit
                      </button>

                      <button
                        onClick={() =>
                          handleDelete(
                            application.id
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
                  No applications found
                </td>
              </tr>

            )}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default Applications;