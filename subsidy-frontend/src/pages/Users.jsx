import { useEffect, useState } from "react";
import api from "../services/api";

function Users() {
  const [users, setUsers] = useState([]);

  const [searchTerm, setSearchTerm] = useState("");
  const [roleFilter, setRoleFilter] = useState("");

  const [formData, setFormData] = useState({
    username: "",
    password: "",
    role: ""
  });

  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchUsers = async () => {
    try {
      const response = await api.get("/api/users");
      setUsers(response.data);
    } catch (error) {
      console.error(
        "Error fetching users:",
        error
      );
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers();
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
      username: "",
      password: "",
      role: ""
    });

    setEditingId(null);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      if (editingId) {
        const updateData = {
          username: formData.username,
          role: formData.role
        };

        if (formData.password.trim() !== "") {
          updateData.password = formData.password;
        }

        await api.put(
          `/api/users/${editingId}`,
          updateData
        );
      } else {
        await api.post("/api/users", {
          username: formData.username,
          password: formData.password,
          role: formData.role
        });
      }

      resetForm();
      await fetchUsers();

    } catch (error) {
      console.error(
        "Error saving user:",
        error
      );
    }
  };

  const handleEdit = (user) => {
    setEditingId(user.id);

    setFormData({
      username: user.username || "",
      password: "",
      role: user.role || ""
    });
  };

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this user?"
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await api.delete(`/api/users/${id}`);
      await fetchUsers();

    } catch (error) {
      console.error(
        "Error deleting user:",
        error
      );
    }
  };

  // Search + Role Filter
  const filteredUsers = users.filter((user) => {

    const matchesSearch =
      user.username
        ?.toLowerCase()
        .includes(searchTerm.toLowerCase());

    const matchesRole =
      roleFilter === "" ||
      user.role === roleFilter;

    return matchesSearch && matchesRole;
  });

  if (loading) {
    return (
      <div className="dashboard">
        <h1>Users</h1>
        <p>Loading users...</p>
      </div>
    );
  }

  return (
    <div className="dashboard">

      <h1>User Management</h1>

      {/* Form */}

      <div className="form-container">

        <h2>
          {editingId
            ? "Update User"
            : "Create User"}
        </h2>

        <form onSubmit={handleSubmit}>

          <input
            type="text"
            name="username"
            placeholder="Username"
            value={formData.username}
            onChange={handleChange}
            required
          />

          <input
            type="password"
            name="password"
            placeholder={
              editingId
                ? "New Password (optional)"
                : "Password"
            }
            value={formData.password}
            onChange={handleChange}
            required={!editingId}
          />

          <select
            name="role"
            value={formData.role}
            onChange={handleChange}
            required
          >
            <option value="">
              Select Role
            </option>

            <option value="ADMIN">
              ADMIN
            </option>

            <option value="FIELD_OFFICER">
              FIELD OFFICER
            </option>

            <option value="DISTRICT_OFFICER">
              DISTRICT OFFICER
            </option>

            <option value="FINANCE_APPROVER">
              FINANCE APPROVER
            </option>
          </select>

          <button type="submit">
            {editingId
              ? "Update User"
              : "Create User"}
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
          placeholder="🔍 Search username..."
          value={searchTerm}
          onChange={(event) =>
            setSearchTerm(
              event.target.value
            )
          }
        />

        <select
          value={roleFilter}
          onChange={(event) =>
            setRoleFilter(
              event.target.value
            )
          }
        >
          <option value="">
            All Roles
          </option>

          <option value="ADMIN">
            ADMIN
          </option>

          <option value="FIELD_OFFICER">
            FIELD OFFICER
          </option>

          <option value="DISTRICT_OFFICER">
            DISTRICT OFFICER
          </option>

          <option value="FINANCE_APPROVER">
            FINANCE APPROVER
          </option>
        </select>

      </div>

      {/* Table */}

      <div className="table-container">

        <table>

          <thead>
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Role</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>

            {filteredUsers.length > 0 ? (

              filteredUsers.map((user) => (

                <tr key={user.id}>

                  <td>
                    {user.id}
                  </td>

                  <td>
                    {user.username}
                  </td>

                  <td>

                    <span
                      className={`status-badge role-${user.role?.toLowerCase()}`}
                    >
                      {user.role}
                    </span>

                  </td>

                  <td>

                    <button
                      onClick={() =>
                        handleEdit(user)
                      }
                    >
                      Edit
                    </button>

                    <button
                      onClick={() =>
                        handleDelete(user.id)
                      }
                    >
                      Delete
                    </button>

                  </td>

                </tr>

              ))

            ) : (

              <tr>

                <td colSpan="4">
                  No users found
                </td>

              </tr>

            )}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default Users;