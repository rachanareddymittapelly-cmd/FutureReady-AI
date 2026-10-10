import { useEffect, useState } from "react";
import api from "./api";
import SkillProgressChart from "./components/SkillProgressChart";
import AICareerAdvice from "./components/AICareerAdvice";
import "./App.css";

function App() {
  const studentId = 1;

  const [careers, setCareers] = useState([]);
  const [careerMatches, setCareerMatches] = useState([]);
  const [skills, setSkills] = useState([]);
  const [roadmap, setRoadmap] = useState([]);

  const [selectedCareer, setSelectedCareer] = useState("");
  const [loading, setLoading] = useState(true);
  const [updating, setUpdating] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  // Load all dashboard data
  const loadDashboard = async () => {
    setError("");

    try {
      const [
        careersResponse,
        matchesResponse,
        skillsResponse,
      ] = await Promise.all([
        api.get("/careers"),
        api.get(`/career-matches?studentId=${studentId}`),
        api.get("/student-skills"),
      ]);

      setCareers(careersResponse.data);
      setCareerMatches(matchesResponse.data);

      // Keep only this student's skills.
      const studentSkills = skillsResponse.data.filter(
        (item) => Number(item.student?.id) === studentId
      );

      // If duplicate records exist, display the highest skill level.
      const skillMap = new Map();

      studentSkills.forEach((item) => {
        const skillId = item.skill?.id;

        if (skillId == null) return;

        const existing = skillMap.get(skillId);

        if (
          !existing ||
          Number(item.currentLevel || 0) >
            Number(existing.currentLevel || 0)
        ) {
          skillMap.set(skillId, item);
        }
      });

      setSkills(Array.from(skillMap.values()));
    } catch (err) {
      console.error("Dashboard loading error:", err);

      setError(
        "Unable to load dashboard data. Please check that the backend is running."
      );
    } finally {
      setLoading(false);
    }
  };

  // Load learning roadmap
  const loadRoadmap = async (careerId) => {
    if (!careerId) {
      setRoadmap([]);
      return;
    }

    try {
      const response = await api.get(
        `/learning-roadmap?studentId=${studentId}&careerId=${careerId}`
      );

      setRoadmap(response.data);
    } catch (err) {
      console.error("Roadmap loading error:", err);
      setRoadmap([]);
    }
  };

  useEffect(() => {
    loadDashboard();
  }, []);

  // Automatically select the first available career
  useEffect(() => {
    if (careers.length > 0 && !selectedCareer) {
      const firstCareer = careers[0];
      const careerId = firstCareer.id;

      setSelectedCareer(String(careerId));
    }
  }, [careers, selectedCareer]);

  useEffect(() => {
    if (selectedCareer) {
      loadRoadmap(selectedCareer);
    }
  }, [selectedCareer]);

  // Update student's skill level
  const updateSkill = async (skillRecordId, currentLevel) => {
    const input = window.prompt(
      "Enter your new skill level (0-100):",
      String(currentLevel ?? 0)
    );

    if (input === null) return;

    const newLevel = Number(input);

    if (
      input.trim() === "" ||
      !Number.isFinite(newLevel) ||
      newLevel < 0 ||
      newLevel > 100
    ) {
      window.alert("Please enter a number between 0 and 100.");
      return;
    }

    setUpdating(true);
    setMessage("");
    setError("");

    try {
      const skillRecord = skills.find(
        (item) => item.id === skillRecordId
      );

      if (!skillRecord) {
        throw new Error("Skill record not found.");
      }

      await api.put(`/student-skills/${skillRecordId}`, {
        ...skillRecord,
        currentLevel: newLevel,
      });

      setMessage("Skill level updated successfully!");

      // Refresh skill levels, career matches and progress chart data.
      await loadDashboard();

      if (selectedCareer) {
        await loadRoadmap(selectedCareer);
      }
    } catch (err) {
      console.error("Skill update error:", err);

      setError(
        err.response?.data?.message ||
          err.message ||
          "Unable to update skill level."
      );
    } finally {
      setUpdating(false);
    }
  };

  if (loading) {
    return (
      <div className="dashboard-container">
        <h2>Loading FutureReady AI...</h2>
      </div>
    );
  }

  return (
    <div className="dashboard-container">
      {/* Header */}
      <header className="dashboard-header">
        <div>
          <h1>FutureReady AI</h1>

          <p>
            Student Skill Gap Analysis & Personalized Career Development
          </p>
        </div>

        <div className="student-badge">
          Student Dashboard
        </div>
      </header>

      {error && (
        <div className="error-message">
          {error}
        </div>
      )}

      {message && (
        <div className="success-message">
          {message}
        </div>
      )}

      {/* Career Matches */}
      <section className="dashboard-section">
        <h2>🎯 Career Match Analysis</h2>

        <p className="section-description">
          Explore careers that match your current skill levels.
        </p>

        {careerMatches.length === 0 ? (
          <p>No career matches available yet.</p>
        ) : (
          <div className="career-grid">
            {careerMatches.map((career, index) => {
              const percentage = Math.max(
                0,
                Math.min(
                  100,
                  Number(career.matchPercentage || 0)
                )
              );

              return (
                <div
                  className="career-card"
                  key={career.careerId ?? index}
                >
                  <h3>{career.careerName}</h3>

                  <div className="match-percentage">
                    {percentage}%
                  </div>

                  <div className="progress-track">
                    <div
                      className="progress-fill"
                      style={{ width: `${percentage}%` }}
                    />
                  </div>

                  <p>Career Match</p>
                </div>
              );
            })}
          </div>
        )}
      </section>

      {/* Skills */}
      <section className="dashboard-section">
        <h2>📚 My Skills</h2>

        <p className="section-description">
          Track and update your current skill levels.
        </p>

        {skills.length === 0 ? (
          <p>
            No skills found for this student. Add student skills to the
            database first.
          </p>
        ) : (
          <div className="skills-grid">
            {skills.map((item) => {
              const level = Math.max(
                0,
                Math.min(100, Number(item.currentLevel || 0))
              );

              return (
                <div
                  className="skill-card"
                  key={item.id}
                >
                  <div className="skill-card-heading">
                    <h3>{item.skill?.name || "Skill"}</h3>

                    <span>{level}%</span>
                  </div>

                  <div className="progress-track">
                    <div
                      className="progress-fill"
                      style={{ width: `${level}%` }}
                    />
                  </div>

                  <button
                    type="button"
                    disabled={updating}
                    onClick={() =>
                      updateSkill(item.id, item.currentLevel)
                    }
                  >
                    Update Skill
                  </button>
                </div>
              );
            })}
          </div>
        )}
      </section>

      {/* Career Roadmap */}
      <section className="dashboard-section">
        <h2>🗺️ Personalized Learning Roadmap</h2>

        <label
          htmlFor="career-select"
          className="career-select-label"
        >
          Select Career Goal
        </label>

        <select
          id="career-select"
          value={selectedCareer}
          onChange={(event) =>
            setSelectedCareer(event.target.value)
          }
          className="career-select"
        >
          {careers.map((career) => (
            <option
              key={career.id}
              value={String(career.id)}
            >
              {career.name}
            </option>
          ))}
        </select>

        {roadmap.length === 0 ? (
          <p className="section-description">
            No roadmap items available for this career yet.
          </p>
        ) : (
          <div className="roadmap-list">
            {roadmap.map((item, index) => (
              <div
                className="roadmap-card"
                key={`${item.skill}-${index}`}
              >
                <h3>{item.skill}</h3>

                <p>
                  <strong>Current Level:</strong>{" "}
                  {item.currentLevel}%
                </p>

                <p>
                  <strong>Required Level:</strong>{" "}
                  {item.requiredLevel}%
                </p>

                <p>
                  <strong>Skill Gap:</strong>{" "}
                  {item.skillGap}
                </p>

                <p>
                  <strong>Recommendation:</strong>{" "}
                  {item.recommendation}
                </p>
              </div>
            ))}
          </div>
        )}
      </section>

      {/* Skill Progress Chart */}
      <section className="dashboard-section">
        <h2>📈 Skill Progress History</h2>

        <p className="section-description">
          Track how your skills improve over time.
        </p>

        <SkillProgressChart />
      </section>

      {/* Career Advisor */}
      <section className="dashboard-section">
        <AICareerAdvice />
      </section>

      {/* Footer */}
      <footer className="dashboard-footer">
        <p>
          FutureReady AI | Learn Skills. Close Gaps. Build Your Future.
        </p>
      </footer>
    </div>
  );
}

export default App;