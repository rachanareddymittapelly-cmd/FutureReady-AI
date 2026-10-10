import { useEffect, useState } from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";

import api from "../api";

function SkillProgressChart() {
  const [progress, setProgress] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    api
      .get("/skill-progress/student/1")
      .then((response) => {
        setProgress(response.data);
      })
      .catch((err) => {
        console.error("Progress loading error:", err);
        setError("Unable to load skill progress.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <p>Loading skill progress...</p>;
  }

  if (error) {
    return <p>{error}</p>;
  }

  if (progress.length === 0) {
    return (
      <div>
        <h2>Skill Progress Tracking</h2>
        <p>No progress history available yet.</p>
        <p>Update a skill level to start tracking your progress.</p>
      </div>
    );
  }

  const chartData = [...progress]
    .sort(
      (a, b) =>
        new Date(a.updatedAt) - new Date(b.updatedAt)
    )
    .map((item) => ({
      date: new Date(item.updatedAt).toLocaleString(),
      skill: item.skill?.name || item.skill?.skillName || "Skill",
      previousLevel: Number(item.previousLevel),
      newLevel: Number(item.newLevel),
    }));

  return (
    <div className="progress-chart">
      <h2>Skill Progress Tracking</h2>
      <p>Track your skill-level changes over time.</p>

      <ResponsiveContainer width="100%" height={320}>
        <LineChart data={chartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="date" />
          <YAxis domain={[0, 100]} />
          <Tooltip />
          <Legend />

          <Line
            type="monotone"
            dataKey="previousLevel"
            name="Previous Level"
            stroke="#8884d8"
            activeDot={{ r: 6 }}
          />

          <Line
            type="monotone"
            dataKey="newLevel"
            name="New Level"
            stroke="#82ca9d"
            activeDot={{ r: 6 }}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}

export default SkillProgressChart;