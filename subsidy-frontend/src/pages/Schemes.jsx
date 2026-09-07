import { useEffect, useState } from "react";
import api from "../services/api";

function Schemes() {
  const [schemes, setSchemes] = useState([]);

  const [searchTerm, setSearchTerm] = useState("");
  const [regionFilter, setRegionFilter] = useState("");

  const [formData, setFormData] = useState({
    name: "",
    description: "",
    eligibilityCriteria: "",
    grantAmount: "",
    region: "",
    allocationBudget: ""
  });

  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchSchemes = async () => {
    try {
      const response = await api.get("/api/schemes");
      setSchemes(response.data);
    } catch (error) {
      console.error("Error fetching schemes:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSchemes();
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
      name: "",
      description: "",
      eligibilityCriteria: "",
      grantAmount: "",
      region: "",
      allocationBudget: ""
    });

    setEditingId(null);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const schemeData = {
      name: formData.name,
      description: formData.description,
      eligibilityCriteria: formData.eligibilityCriteria,
      grantAmount: Number(formData.grantAmount),
      region: formData.region,
      allocationBudget: Number(formData.allocationBudget)
    };

    try {
      if (editingId) {
        await api.put(
          `/api/schemes/${editingId}`,
          schemeData
        );
      } else {
        await api.post(
          "/api/schemes",
          schemeData
        );
      }

      resetForm();
      await fetchSchemes();

    } catch (error) {
      console.error("Error saving scheme:", error);
    }
  };

  const handleEdit = (scheme) => {
    setEditingId(scheme.id);

    setFormData({
      name: scheme.name || "",
      description: scheme.description || "",
      eligibilityCriteria:
        scheme.eligibilityCriteria || "",
      grantAmount: scheme.grantAmount ?? "",
      region: scheme.region || "",
      allocationBudget:
        scheme.allocationBudget ?? ""
    });
  };

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this scheme?"
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await api.delete(`/api/schemes/${id}`);
      await fetchSchemes();

    } catch (error) {
      console.error("Error deleting scheme:", error);
    }
  };

  // Search + Region Filter
  const filteredSchemes = schemes.filter((scheme) => {

    const matchesSearch =
      scheme.name
        ?.toLowerCase()
        .includes(searchTerm.toLowerCase());

    const matchesRegion =
      regionFilter === "" ||
      scheme.region === regionFilter;

    return matchesSearch && matchesRegion;
  });

  // Get unique regions
  const regions = [
    ...new Set(
      schemes
        .map((scheme) => scheme.region)
        .filter(Boolean)
    )
  ];

  if (loading) {
    return (
      <div className="dashboard">
        <h1>Schemes</h1>
        <p>Loading schemes...</p>
      </div>
    );
  }

  return (
    <div className="dashboard">

      <h1>Scheme Management</h1>

      {/* Form */}

      <div className="form-container">

        <h2>
          {editingId
            ? "Update Scheme"
            : "Create Scheme"}
        </h2>

        <form onSubmit={handleSubmit}>

          <input
            type="text"
            name="name"
            placeholder="Scheme Name"
            value={formData.name}
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
            step="0.01"
            name="grantAmount"
            placeholder="Grant Amount"
            value={formData.grantAmount}
            onChange={handleChange}
            required
          />

          <input
            type="number"
            step="0.01"
            name="allocationBudget"
            placeholder="Allocation Budget"
            value={formData.allocationBudget}
            onChange={handleChange}
            required
          />

          <textarea
            name="description"
            placeholder="Description"
            value={formData.description}
            onChange={handleChange}
            rows="3"
            required
          />

          <textarea
            name="eligibilityCriteria"
            placeholder="Eligibility Criteria"
            value={formData.eligibilityCriteria}
            onChange={handleChange}
            rows="3"
            required
          />

          <button type="submit">
            {editingId
              ? "Update Scheme"
              : "Create Scheme"}
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
          placeholder="🔍 Search scheme..."
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
              <th>Region</th>
              <th>Grant Amount</th>
              <th>Allocation Budget</th>
              <th>Description</th>
              <th>Eligibility Criteria</th>
              <th>Actions</th>
            </tr>

          </thead>

          <tbody>

            {filteredSchemes.length > 0 ? (

              filteredSchemes.map((scheme) => (

                <tr key={scheme.id}>

                  <td>{scheme.id}</td>

                  <td>{scheme.name}</td>

                  <td>{scheme.region}</td>

                  <td>
                    ₹{scheme.grantAmount}
                  </td>

                  <td>
                    ₹{scheme.allocationBudget}
                  </td>

                  <td>
                    {scheme.description}
                  </td>

                  <td>
                    {scheme.eligibilityCriteria}
                  </td>

                  <td>

                    <button
                      onClick={() =>
                        handleEdit(scheme)
                      }
                    >
                      Edit
                    </button>

                    <button
                      onClick={() =>
                        handleDelete(scheme.id)
                      }
                    >
                      Delete
                    </button>

                  </td>

                </tr>

              ))

            ) : (

              <tr>

                <td colSpan="8">
                  No schemes found
                </td>

              </tr>

            )}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default Schemes;