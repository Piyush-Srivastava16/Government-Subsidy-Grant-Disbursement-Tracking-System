import { useEffect, useState } from "react";
import api from "../services/api";

function Documents() {
  const [documents, setDocuments] = useState([]);
  const [beneficiaries, setBeneficiaries] = useState([]);

  const [file, setFile] = useState(null);
const [searchTerm, setSearchTerm] = useState("");
const [statusFilter, setStatusFilter] = useState("");

  const [formData, setFormData] = useState({
    documentType: "",
    documentName: "",
    documentNumber: "",
    beneficiaryId: ""
  });

  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(true);
  const [uploading, setUploading] = useState(false);

  const fetchDocuments = async () => {
    try {
      const response = await api.get("/api/documents");
      setDocuments(response.data);
    } catch (error) {
      console.error("Error fetching documents:", error);
    }
  };

  const fetchBeneficiaries = async () => {
    try {
      const response = await api.get("/api/beneficiaries");
      setBeneficiaries(response.data);
    } catch (error) {
      console.error("Error fetching beneficiaries:", error);
    }
  };

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);

        await Promise.all([
          fetchDocuments(),
          fetchBeneficiaries()
        ]);
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value
    }));
  };

  const handleFileChange = (event) => {
    setFile(event.target.files[0] || null);
  };

  const resetForm = () => {
    setFormData({
      documentType: "",
      documentName: "",
      documentNumber: "",
      beneficiaryId: ""
    });

    setFile(null);
    setEditingId(null);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setUploading(true);

      // EDIT EXISTING DOCUMENT
      if (editingId) {
        const documentData = {
          documentType: formData.documentType,
          documentName: formData.documentName,
          documentNumber: formData.documentNumber,
          beneficiary: {
            id: Number(formData.beneficiaryId)
          }
        };

        await api.put(
          `/api/documents/${editingId}`,
          documentData
        );

        alert("Document updated successfully.");

        resetForm();
        await fetchDocuments();

        return;
      }

      // NEW DOCUMENT
      if (!file) {
        alert("Please select a file.");
        return;
      }

      const multipartData = new FormData();

      multipartData.append("file", file);
      multipartData.append(
        "beneficiaryId",
        formData.beneficiaryId
      );
      multipartData.append(
        "documentName",
        formData.documentName
      );
      multipartData.append(
        "documentType",
        formData.documentType
      );
      multipartData.append(
        "documentNumber",
        formData.documentNumber
      );

      await api.post(
        "/api/documents/upload",
        multipartData
      );

      alert("Document uploaded successfully.");

      resetForm();
      await fetchDocuments();

    } catch (error) {
      console.error(
        "Error saving/uploading document:",
        error
      );

      if (error.response) {
        console.error(
          "Server response:",
          error.response.data
        );
      }

      alert(
        "Document upload failed. Check browser console."
      );

    } finally {
      setUploading(false);
    }
  };

  const handleEdit = (document) => {
    setEditingId(document.id);

    setFormData({
      documentType: document.documentType || "",
      documentName: document.documentName || "",
      documentNumber: document.documentNumber || "",
      beneficiaryId: document.beneficiary?.id ?? ""
    });

    setFile(null);
  };

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this document?"
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await api.delete(`/api/documents/${id}`);

      await fetchDocuments();

      alert("Document deleted successfully.");

    } catch (error) {
      console.error(
        "Error deleting document:",
        error
      );

      alert("Failed to delete document.");
    }
  };

  const handleVerify = async (id, status) => {
    try {
      await api.put(
        `/api/documents/${id}/verify`,
        null,
        {
          params: {
            status: status
          }
        }
      );

      await fetchDocuments();

      alert(
        `Document ${status.toLowerCase()} successfully.`
      );

    } catch (error) {
      console.error(
        "Error verifying document:",
        error
      );

      alert(
        "Failed to update verification status."
      );
    }
  };

const filteredDocuments = documents.filter((document) => {
  const documentName = document.documentName || "";
  const documentType = document.documentType || "";
  const fileName = document.fileName || "";

  const search = searchTerm.toLowerCase();

  const matchesSearch =
    documentName.toLowerCase().includes(search) ||
    documentType.toLowerCase().includes(search) ||
    fileName.toLowerCase().includes(search);

  const matchesStatus =
    statusFilter === "" ||
    document.verificationStatus === statusFilter;

  return matchesSearch && matchesStatus;
});


  if (loading) {
    return (
      <div className="dashboard">
        <h1>Documents</h1>
        <p>Loading documents...</p>
      </div>
    );
  }

  return (
    <div className="dashboard">

      <h1>Document Management</h1>

      <div className="form-container">

        <h2>
          {editingId
            ? "Update Document"
            : "Upload Document"}
        </h2>

        <form onSubmit={handleSubmit}>

          <input
            type="text"
            name="documentName"
            placeholder="Document Name"
            value={formData.documentName}
            onChange={handleChange}
            required
          />

          <select
            name="documentType"
            value={formData.documentType}
            onChange={handleChange}
            required
          >
            <option value="">
              Select Document Type
            </option>

            <option value="ID_PROOF">
              ID Proof
            </option>

            <option value="INCOME_PROOF">
              Income Proof
            </option>

            <option value="ADDRESS_PROOF">
              Address Proof
            </option>

            <option value="BANK_PROOF">
              Bank Proof
            </option>

            <option value="OTHER">
              Other
            </option>
          </select>

          <input
            type="text"
            name="documentNumber"
            placeholder="Document Number"
            value={formData.documentNumber}
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

            {beneficiaries.map((beneficiary) => (
              <option
                key={beneficiary.id}
                value={beneficiary.id}
              >
                {beneficiary.name}
              </option>
            ))}
          </select>

          {/* File upload only for new document */}
          {!editingId && (
            <input
              type="file"
              onChange={handleFileChange}
              required
            />
          )}

          <button
            type="submit"
            disabled={uploading}
          >
            {uploading
              ? "Processing..."
              : editingId
                ? "Update Document"
                : "Upload Document"}
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
<div className="table-toolbar">

  <input
    type="text"
    placeholder="🔍 Search document..."
    value={searchTerm}
    onChange={(event) =>
      setSearchTerm(event.target.value)
    }
  />

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

    <option value="VERIFIED">
      VERIFIED
    </option>

    <option value="REJECTED">
      REJECTED
    </option>
  </select>

</div>

      <div className="table-container">

        <table>

          <thead>
            <tr>
              <th>ID</th>
              <th>Document Name</th>
              <th>Type</th>
              <th>Document Number</th>
              <th>Beneficiary</th>
              <th>File Name</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>

            {
           filteredDocuments.length > 0 ? (
  filteredDocuments.map((document) => (

                <tr key={document.id}>

                  <td>{document.id}</td>

                  <td>{document.documentName}</td>

                  <td>{document.documentType}</td>

                  <td>{document.documentNumber}</td>

                  <td>
                    {document.beneficiary?.name ||
                      `ID ${document.beneficiary?.id}`}
                  </td>

                  <td>
                    {document.fileName || "N/A"}
                  </td>
<td>
  <span
    className={`status-badge status-${document.verificationStatus?.toLowerCase()}`}
  >
    {document.verificationStatus}
  </span>
</td>

                  <td>

                    <button
                      onClick={() =>
                        handleEdit(document)
                      }
                    >
                      Edit
                    </button>

                    <button
                      onClick={() =>
                        handleDelete(document.id)
                      }
                    >
                      Delete
                    </button>

                    <button
                      onClick={() =>
                        handleVerify(
                          document.id,
                          "VERIFIED"
                        )
                      }
                    >
                      Verify
                    </button>

                    <button
                      onClick={() =>
                        handleVerify(
                          document.id,
                          "REJECTED"
                        )
                      }
                    >
                      Reject
                    </button>

                  </td>

                </tr>

              ))

            ) : (

              <tr>
                <td colSpan="8">
                  No documents found
                </td>
              </tr>

            )}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default Documents;