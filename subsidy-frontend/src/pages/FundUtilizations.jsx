import { useEffect, useState } from "react";
import api from "../services/api";

function FundUtilizations() {
  const [fundUtilizations, setFundUtilizations] = useState([]);
  const [disbursements, setDisbursements] = useState([]);

  const [searchTerm, setSearchTerm] = useState("");
  const [statusFilter, setStatusFilter] = useState("");

  const [formData, setFormData] = useState({
    utilizedAmount: "",
    utilizationDate: "",
    description: "",
    status: "",
    disbursementId: ""
  });

  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchFundUtilizations = async () => {
    try {
      const response = await api.get("/api/fund-utilizations");
      setFundUtilizations(response.data);
    } catch (error) {
      console.error(
        "Error fetching fund utilizations:",
        error
      );
    }
  };

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

  useEffect(() => {
    const loadData = async () => {
      setLoading(true);

      await Promise.all([
        fetchFundUtilizations(),
        fetchDisbursements()
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
      utilizedAmount: "",
      utilizationDate: "",
      description: "",
      status: "",
      disbursementId: ""
    });

    setEditingId(null);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const fundUtilizationData = {
      utilizedAmount: Number(
        formData.utilizedAmount
      ),
      utilizationDate:
        formData.utilizationDate,
      description: formData.description,
      status: formData.status,
      disbursement: {
        id: Number(formData.disbursementId)
      }
    };

    try {
      if (editingId) {
        await api.put(
          `/api/fund-utilizations/${editingId}`,
          fundUtilizationData
        );
      } else {
        await api.post(
          "/api/fund-utilizations",
          fundUtilizationData
        );
      }

      resetForm();
      await fetchFundUtilizations();

    } catch (error) {
      console.error(
        "Error saving fund utilization:",
        error
      );
    }
  };

  const handleEdit = (fundUtilization) => {
    setEditingId(fundUtilization.id);

    setFormData({
      utilizedAmount:
        fundUtilization.utilizedAmount ?? "",

      utilizationDate:
        fundUtilization.utilizationDate || "",

      description:
        fundUtilization.description || "",

      status:
        fundUtilization.status || "",

      disbursementId:
        fundUtilization.disbursement?.id ?? ""
    });
  };

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this fund utilization?"
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await api.delete(
        `/api/fund-utilizations/${id}`
      );

      await fetchFundUtilizations();

    } catch (error) {
      console.error(
        "Error deleting fund utilization:",
        error
      );
    }
  };

  const filteredFundUtilizations =
    fundUtilizations.filter(
      (fundUtilization) => {

        const disbursementId =
          String(
            fundUtilization
              .disbursement?.id || ""
          );

        const matchesSearch =
          disbursementId.includes(
            searchTerm.trim()
          );

        const matchesStatus =
          statusFilter === "" ||
          fundUtilization.status ===
            statusFilter;

        return (
          matchesSearch &&
          matchesStatus
        );
      }
    );

  const totalUtilizedAmount =
    filteredFundUtilizations.reduce(
      (total, record) =>
        total +
        Number(record.utilizedAmount || 0),
      0
    );

  if (loading) {
    return (
      <div className="dashboard">
        <h1>Fund Utilization</h1>
        <p>
          Loading fund utilizations...
        </p>
      </div>
    );
  }

  return (
    <div className="dashboard">

      <h1>Fund Utilization Management</h1>

      {/* Summary */}

      <div className="dashboard-cards">

        <div className="card">

          <h3>
            Total Records
          </h3>

          <p>
            {filteredFundUtilizations.length}
          </p>

        </div>

        <div className="card">

          <h3>
            Total Utilized
          </h3>

          <p>
            ₹{totalUtilizedAmount}
          </p>

        </div>

      </div>

      {/* Form */}

      <div className="form-container">

        <h2>
          {editingId
            ? "Update Fund Utilization"
            : "Create Fund Utilization"}
        </h2>

        <form onSubmit={handleSubmit}>

          <input
            type="number"
            step="0.01"
            min="0"
            name="utilizedAmount"
            placeholder="Utilized Amount"
            value={formData.utilizedAmount}
            onChange={handleChange}
            required
          />

          <input
            type="date"
            name="utilizationDate"
            value={formData.utilizationDate}
            onChange={handleChange}
            required
          />

          <textarea
            name="description"
            placeholder="Description"
            value={formData.description}
            onChange={handleChange}
            rows="4"
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

            <option value="VERIFIED">
              VERIFIED
            </option>

            <option value="REJECTED">
              REJECTED
            </option>
          </select>

          <select
            name="disbursementId"
            value={formData.disbursementId}
            onChange={handleChange}
            required
          >
            <option value="">
              Select Disbursement
            </option>

            {disbursements.map(
              (disbursement) => (
                <option
                  key={disbursement.id}
                  value={disbursement.id}
                >
                  ID {disbursement.id} - ₹
                  {disbursement.amount}
                </option>
              )
            )}
          </select>

          <button type="submit">
            {editingId
              ? "Update Fund Utilization"
              : "Create Fund Utilization"}
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
          placeholder="🔍 Search by disbursement ID..."
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

          <option value="VERIFIED">
            VERIFIED
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
              <th>Utilized Amount</th>
              <th>Date</th>
              <th>Description</th>
              <th>Status</th>
              <th>Disbursement</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>

            {filteredFundUtilizations.length >
            0 ? (

              filteredFundUtilizations.map(
                (fundUtilization) => (

                  <tr
                    key={
                      fundUtilization.id
                    }
                  >

                    <td>
                      {fundUtilization.id}
                    </td>

                    <td>
                      ₹
                      {
                        fundUtilization
                          .utilizedAmount
                      }
                    </td>

                    <td>
                      {
                        fundUtilization
                          .utilizationDate
                      }
                    </td>

                    <td>
                      {
                        fundUtilization
                          .description
                      }
                    </td>

                    <td>
                      <span
                        className={`status-badge status-${fundUtilization.status?.toLowerCase()}`}
                      >
                        {
                          fundUtilization.status
                        }
                      </span>
                    </td>

                    <td>
                      {
                        fundUtilization
                          .disbursement?.id
                      }
                    </td>

                    <td>

                      <button
                        onClick={() =>
                          handleEdit(
                            fundUtilization
                          )
                        }
                      >
                        Edit
                      </button>

                      <button
                        onClick={() =>
                          handleDelete(
                            fundUtilization.id
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
                  No fund utilization
                  records found
                </td>
              </tr>

            )}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default FundUtilizations;