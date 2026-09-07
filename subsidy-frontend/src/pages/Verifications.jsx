import { useEffect, useState } from "react";
import api from "../services/api";

function Verifications() {
  const [verifications, setVerifications] = useState([]);
  const [applications, setApplications] = useState([]);

  const [searchTerm, setSearchTerm] = useState("");
  const [levelFilter, setLevelFilter] = useState("");
  const [statusFilter, setStatusFilter] = useState("");

  const [formData, setFormData] = useState({
    verificationLevel: "",
    officerName: "",
    status: "",
    remarks: "",
    applicationId: ""
  });

  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchVerifications = async () => {
    try {
      const response = await api.get("/api/verifications");
      setVerifications(response.data);
    } catch (error) {
      console.error(
        "Error fetching verifications:",
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

  useEffect(() => {
    const loadData = async () => {
      setLoading(true);

      await Promise.all([
        fetchVerifications(),
        fetchApplications()
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
      verificationLevel: "",
      officerName: "",
      status: "",
      remarks: "",
      applicationId: ""
    });

    setEditingId(null);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const verificationData = {
      verificationLevel: formData.verificationLevel,
      officerName: formData.officerName,
      status: formData.status,
      remarks: formData.remarks,
      application: {
        id: Number(formData.applicationId)
      }
    };

    try {
      if (editingId) {
        await api.put(
          `/api/verifications/${editingId}`,
          verificationData
        );
      } else {
        await api.post(
          "/api/verifications",
          verificationData
        );
      }

      resetForm();
      await fetchVerifications();

    } catch (error) {
      console.error(
        "Error saving verification:",
        error
      );
    }
  };

  const handleEdit = (verification) => {
    setEditingId(verification.id);

    setFormData({
      verificationLevel:
        verification.verificationLevel || "",

      officerName:
        verification.officerName || "",

      status:
        verification.status || "",

      remarks:
        verification.remarks || "",

      applicationId:
        verification.application?.id ?? ""
    });
  };

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this verification?"
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await api.delete(
        `/api/verifications/${id}`
      );

      await fetchVerifications();

    } catch (error) {
      console.error(
        "Error deleting verification:",
        error
      );
    }
  };

  const filteredVerifications =
    verifications.filter((verification) => {

      const officerName =
        verification.officerName || "";

      const matchesSearch =
        officerName
          .toLowerCase()
          .includes(
            searchTerm.toLowerCase()
          );

      const matchesLevel =
        levelFilter === "" ||
        verification.verificationLevel ===
          levelFilter;

      const matchesStatus =
        statusFilter === "" ||
        verification.status === statusFilter;

      return (
        matchesSearch &&
        matchesLevel &&
        matchesStatus
      );
    });

  if (loading) {
    return (
      <div className="dashboard">
        <h1>Verifications</h1>
        <p>Loading verifications...</p>
      </div>
    );
  }

  return (
    <div className="dashboard">

      <h1>Verification Management</h1>

      {/* Form */}

      <div className="form-container">

        <h2>
          {editingId
            ? "Update Verification"
            : "Create Verification"}
        </h2>

        <form onSubmit={handleSubmit}>

          <select
            name="verificationLevel"
            value={formData.verificationLevel}
            onChange={handleChange}
            required
          >
            <option value="">
              Select Verification Level
            </option>

            <option value="FIELD_OFFICER">
              Field Officer
            </option>

            <option value="DISTRICT_OFFICER">
              District Officer
            </option>

            <option value="FINANCE_APPROVER">
              Finance Approver
            </option>
          </select>

          <input
            type="text"
            name="officerName"
            placeholder="Officer Name"
            value={formData.officerName}
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

            <option value="APPROVED">
              APPROVED
            </option>

            <option value="REJECTED">
              REJECTED
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

          <textarea
            name="remarks"
            placeholder="Remarks"
            value={formData.remarks}
            onChange={handleChange}
            rows="4"
          />

          <button type="submit">
            {editingId
              ? "Update Verification"
              : "Create Verification"}
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

      {/* Search + Filters */}

      <div className="table-toolbar">

        <input
          type="text"
          placeholder="🔍 Search by officer..."
          value={searchTerm}
          onChange={(event) =>
            setSearchTerm(event.target.value)
          }
        />

        <select
          value={levelFilter}
          onChange={(event) =>
            setLevelFilter(event.target.value)
          }
        >
          <option value="">
            All Levels
          </option>

          <option value="FIELD_OFFICER">
            Field Officer
          </option>

          <option value="DISTRICT_OFFICER">
            District Officer
          </option>

          <option value="FINANCE_APPROVER">
            Finance Approver
          </option>
        </select>

        <select
          value={statusFilter}
          onChange={(event) =>
            setStatusFilter(event.target.value)
          }
        >
          <option value="">
            All Statuses
          </option>

          <option value="PENDING">
            PENDING
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
              <th>Level</th>
              <th>Officer</th>
              <th>Status</th>
              <th>Application</th>
              <th>Remarks</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>

            {filteredVerifications.length > 0 ? (

              filteredVerifications.map(
                (verification) => (

                  <tr
                    key={verification.id}
                  >

                    <td>
                      {verification.id}
                    </td>

                    <td>
                      {verification.verificationLevel}
                    </td>

                    <td>
                      {verification.officerName}
                    </td>

                    <td>
                      <span
                        className={`status-badge status-${verification.status?.toLowerCase()}`}
                      >
                        {verification.status}
                      </span>
                    </td>

                    <td>
                      {verification.application?.id}
                    </td>

                    <td>
                      {verification.remarks}
                    </td>

                    <td>

                      <button
                        onClick={() =>
                          handleEdit(
                            verification
                          )
                        }
                      >
                        Edit
                      </button>

                      <button
                        onClick={() =>
                          handleDelete(
                            verification.id
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
                  No verifications found
                </td>
              </tr>

            )}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default Verifications;