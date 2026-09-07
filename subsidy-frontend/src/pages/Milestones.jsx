import { useEffect, useState } from "react";
import api from "../services/api";

function Milestones() {
  const [milestones, setMilestones] = useState([]);
  const [applications, setApplications] = useState([]);

  const [searchTerm, setSearchTerm] = useState("");
  const [statusFilter, setStatusFilter] = useState("");

  const [formData, setFormData] = useState({
    milestoneName: "",
    dueDate: "",
    status: "",
    remarks: "",
    applicationId: ""
  });

  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchMilestones = async () => {
    try {
      const response = await api.get("/api/milestones");
      setMilestones(response.data);
    } catch (error) {
      console.error("Error fetching milestones:", error);
    }
  };

  const fetchApplications = async () => {
    try {
      const response = await api.get("/api/applications");
      setApplications(response.data);
    } catch (error) {
      console.error("Error fetching applications:", error);
    }
  };

  useEffect(() => {
    const loadData = async () => {
      setLoading(true);

      await Promise.all([
        fetchMilestones(),
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
      milestoneName: "",
      dueDate: "",
      status: "",
      remarks: "",
      applicationId: ""
    });

    setEditingId(null);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const milestoneData = {
      milestoneName: formData.milestoneName,
      dueDate: formData.dueDate,
      status: formData.status,
      remarks: formData.remarks,
      application: {
        id: Number(formData.applicationId)
      }
    };

    try {
      if (editingId) {
        await api.put(
          `/api/milestones/${editingId}`,
          milestoneData
        );
      } else {
        await api.post(
          "/api/milestones",
          milestoneData
        );
      }

      resetForm();
      await fetchMilestones();

    } catch (error) {
      console.error(
        "Error saving milestone:",
        error
      );
    }
  };

  const handleEdit = (milestone) => {
    setEditingId(milestone.id);

    setFormData({
      milestoneName:
        milestone.milestoneName || "",

      dueDate:
        milestone.dueDate || "",

      status:
        milestone.status || "",

      remarks:
        milestone.remarks || "",

      applicationId:
        milestone.application?.id ?? ""
    });
  };

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this milestone?"
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await api.delete(
        `/api/milestones/${id}`
      );

      await fetchMilestones();

    } catch (error) {
      console.error(
        "Error deleting milestone:",
        error
      );
    }
  };

  // Search + Status Filter
  const filteredMilestones =
    milestones.filter((milestone) => {

      const milestoneName =
        milestone.milestoneName || "";

      const matchesSearch =
        milestoneName
          .toLowerCase()
          .includes(
            searchTerm.toLowerCase()
          );

      const matchesStatus =
        statusFilter === "" ||
        milestone.status === statusFilter;

      return (
        matchesSearch &&
        matchesStatus
      );
    });

  if (loading) {
    return (
      <div className="dashboard">
        <h1>Milestones</h1>
        <p>Loading milestones...</p>
      </div>
    );
  }

  return (
    <div className="dashboard">

      <h1>Milestone Management</h1>

      {/* Form */}

      <div className="form-container">

        <h2>
          {editingId
            ? "Update Milestone"
            : "Create Milestone"}
        </h2>

        <form onSubmit={handleSubmit}>

          <input
            type="text"
            name="milestoneName"
            placeholder="Milestone Name"
            value={formData.milestoneName}
            onChange={handleChange}
            required
          />

          <input
            type="date"
            name="dueDate"
            value={formData.dueDate}
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

            <option value="IN_PROGRESS">
              IN PROGRESS
            </option>

            <option value="COMPLETED">
              COMPLETED
            </option>

            <option value="OVERDUE">
              OVERDUE
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
              ? "Update Milestone"
              : "Create Milestone"}
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
          placeholder="🔍 Search milestone..."
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

          <option value="IN_PROGRESS">
            IN PROGRESS
          </option>

          <option value="COMPLETED">
            COMPLETED
          </option>

          <option value="OVERDUE">
            OVERDUE
          </option>
        </select>

      </div>

      {/* Table */}

      <div className="table-container">

        <table>

          <thead>
            <tr>
              <th>ID</th>
              <th>Milestone</th>
              <th>Due Date</th>
              <th>Status</th>
              <th>Application</th>
              <th>Remarks</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>

            {filteredMilestones.length > 0 ? (

              filteredMilestones.map(
                (milestone) => (

                  <tr
                    key={milestone.id}
                  >

                    <td>
                      {milestone.id}
                    </td>

                    <td>
                      {milestone.milestoneName}
                    </td>

                    <td>
                      {milestone.dueDate}
                    </td>

                    <td>

                      <span
                        className={`status-badge status-${milestone.status?.toLowerCase()}`}
                      >
                        {milestone.status}
                      </span>

                    </td>

                    <td>
                      {milestone.application?.id}
                    </td>

                    <td>
                      {milestone.remarks}
                    </td>

                    <td>

                      <button
                        onClick={() =>
                          handleEdit(
                            milestone
                          )
                        }
                      >
                        Edit
                      </button>

                      <button
                        onClick={() =>
                          handleDelete(
                            milestone.id
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
                  No milestones found
                </td>

              </tr>

            )}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default Milestones;