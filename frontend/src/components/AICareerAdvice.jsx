import { useState } from "react";
import api from "../api";

function AICareerAdvice() {
  const [studentSkills, setStudentSkills] = useState(
    "Java: 50%, Python: 70%, SQL: 40%, React: 60%"
  );

  const [careerGoal, setCareerGoal] = useState("Full Stack Developer");
  const [advice, setAdvice] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const generateAdvice = async (e) => {
    e.preventDefault();

    setLoading(true);
    setError("");
    setAdvice("");

    try {
      const response = await api.post("/ai/career-advice", {
        studentSkills,
        careerGoal,
      });

      setAdvice(response.data.advice);
    } catch (err) {
      setError(
        err.response?.data?.message ||
          "Unable to generate career advice. Check whether the backend is running."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="ai-advice-card">
      <h2>AI Career Advisor</h2>
      <p>
        Analyse your skills and get a personalized career development plan.
      </p>

      <form onSubmit={generateAdvice}>
        <label>Enter Your Skills</label>

        <textarea
          value={studentSkills}
          onChange={(e) => setStudentSkills(e.target.value)}
          placeholder="Example: Java: 50%, Python: 70%, SQL: 40%"
          required
          rows="4"
        />

        <label>Select Your Career Goal</label>

        <select
          value={careerGoal}
          onChange={(e) => setCareerGoal(e.target.value)}
        >
          <option>Full Stack Developer</option>
          <option>Java Developer</option>
          <option>Data Analyst</option>
          <option>Python Developer</option>
        </select>

        <button type="submit" disabled={loading}>
          {loading ? "Generating Report..." : "Generate Career Report"}
        </button>
      </form>

      {error && <p className="error-message">{error}</p>}

      {advice && (
        <div className="career-report">
          <h3>Your Personalized Career Report</h3>
          <pre>{advice}</pre>
        </div>
      )}
    </div>
  );
}

export default AICareerAdvice;