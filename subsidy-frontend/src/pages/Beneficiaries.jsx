import { useEffect, useState } from "react";
import api from "../services/api";

function Beneficiaries() {

  const [beneficiaries, setBeneficiaries] = useState([]);

  const [searchTerm, setSearchTerm] = useState("");
  const [regionFilter, setRegionFilter] = useState("");

  const [formData, setFormData] = useState({
    name: "",
    category: "",
    region: "",
    income: ""
  });

  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(true);

  // Fetch all beneficiaries
  const fetchBeneficiaries = async () => {
    try {
      const response = await api.get("/api/beneficiaries");
      setBeneficiaries(response.data);
    } catch (error) {
      console.error(
        "Error fetching beneficiaries:",
        error
      );
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBeneficiaries();
  }, []);

  // Handle form input
  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData({
      ...formData,
      [name]: value
    });
  };

  // Add / Update beneficiary
  const handleSubmit = async (event) => {

    event.preventDefault();

    const beneficiaryData = {
      name: formData.name,
      category: formData.category,
      region: formData.region,
      income: Number(formData.income)
    };

    try {

      if (editingId) {

        await api.put(
          `/api/beneficiaries/${editingId}`,
          beneficiaryData
        );

      } else {

        await api.post(
          "/api/beneficiaries",
          beneficiaryData
        );
      }

      setFormData({
        name: "",
        category: "",
        region: "",
        income: ""
      });

      setEditingId(null);

      await fetchBeneficiaries();

    } catch (error) {
      console.error(
        "Error saving beneficiary:",
        error
      );
    }
  };

  // Edit
  const handleEdit = (beneficiary) => {

    setEditingId(beneficiary.id);

    setFormData({
      name: beneficiary.name || "",
      category: beneficiary.category || "",
      region: beneficiary.region || "",
      income: beneficiary.income ?? ""
    });
  };

  // Delete
  const handleDelete = async (id) => {

    const confirmDelete = window.confirm(
      "Are you sure you want to delete this beneficiary?"
    );

    if (!confirmDelete) {
      return;
    }

    try {

      await api.delete(
        `/api/beneficiaries/${id}`
      );

      await fetchBeneficiaries();

    } catch (error) {
      console.error(
        "Error deleting beneficiary:",
        error
      );
    }
  };

  // Reset form
  const handleCancel = () => {

    setEditingId(null);

    setFormData({
      name: "",
      category: "",
      region: "",
      income: ""
    });
  };

  // Search + Region Filter
  const filteredBeneficiaries =
    beneficiaries.filter((beneficiary) => {

      const matchesSearch =
        beneficiary.name
          ?.toLowerCase()
          .includes(
            searchTerm.toLowerCase()
          );

      const matchesRegion =
        regionFilter === "" ||
        beneficiary.region === regionFilter;

      return matchesSearch && matchesRegion;
    });

  // Unique regions
  const regions = [
    ...new Set(
      beneficiaries
        .map(
          (beneficiary) =>
            beneficiary.region
        )
        .filter(Boolean)
    )
  ];

  if (loading) {
    return (
      <div className="dashboard">
        <h1>Beneficiaries</h1>
        <p>Loading beneficiaries...</p>
      </div>
    );
  }

  return (
    <div className="dashboard">

      <h1>Beneficiary Management</h1>

      {/* Form */}

      <div className="form-container">

        <h2>
          {editingId
            ? "Update Beneficiary"
            : "Add Beneficiary"}
        </h2>

        <form onSubmit={handleSubmit}>

          <input
            type="text"
            name="name"
            placeholder="Beneficiary Name"
            value={formData.name}
            onChange={handleChange}
            required
          />

          <input
            type="text"
            name="category"
            placeholder="Category"
            value={formData.category}
            onChange={handleChange}
            required
          />

          <input
            type="text"
            name="region"
            placeholder="Region"
            value={formData.region}
            onChange={handleChange}
            required
          />

          <input
            type="number"
            name="income"
            placeholder="Income"
            value={formData.income}
            onChange={handleChange}
            required
          />

          <button type="submit">
            {editingId
              ? "Update Beneficiary"
              : "Add Beneficiary"}
          </button>

          {editingId && (
            <button
              type="button"
              onClick={handleCancel}
            >
              Cancel
            </button>
          )}

        </form>

      </div>

      {/* Search & Filter */}

      <div className="table-toolbar">

        <input
          type="text"
          placeholder="🔍 Search beneficiary by name..."
          value={searchTerm}
          onChange={(event) =>
            setSearchTerm(event.target.value)
          }
        />

        <select
          value={regionFilter}
          onChange={(event) =>
            setRegionFilter(event.target.value)
          }
        >
          <option value="">
            All Regions
          </option>

          {regions.map((region) => (
            <option
              key={region}
              value={region}
            >
              {region}
            </option>
          ))}
        </select>

      </div>

      {/* Table */}

      <div className="table-container">

        <table>

          <thead>

            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Category</th>
              <th>Region</th>
              <th>Income</th>
              <th>Actions</th>
            </tr>

          </thead>

          <tbody>

            {filteredBeneficiaries.length > 0 ? (

              filteredBeneficiaries.map(
                (beneficiary) => (

                  <tr
                    key={beneficiary.id}
                  >

                    <td>
                      {beneficiary.id}
                    </td>

                    <td>
                      {beneficiary.name}
                    </td>

                    <td>
                      {beneficiary.category}
                    </td>

                    <td>
                      {beneficiary.region}
                    </td>

                    <td>
                      ₹{beneficiary.income}
                    </td>

                    <td>

                      <button
                        onClick={() =>
                          handleEdit(
                            beneficiary
                          )
                        }
                      >
                        Edit
                      </button>

                      <button
                        onClick={() =>
                          handleDelete(
                            beneficiary.id
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

                <td colSpan="6">
                  No beneficiaries found
                </td>

              </tr>

            )}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default Beneficiaries;