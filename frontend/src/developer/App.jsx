import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./index.css";

import DevDashboardHome from "./pages/dashboard/DevDashboardHome";
import DevWorkspace from "./pages/workspace/DevWorkspace";
import DevTaskView from "./pages/tasks/DevTaskView";
import DevTicketView from "./pages/tickets/DevTicketView";
import DevTicketCreate from "./pages/tickets/DevTicketCreate";
import DevProfile from "./pages/profile/DevProfile";

function App() {
  return (
    <Routes>
      <Route path="/" element={<DevDashboardHome />} />
      <Route path="project/:id" element={<DevWorkspace />} />

      {/* NEW: Task details */}
      <Route path="tasks/:id" element={<DevTaskView />} />

      <Route path="tickets/:id" element={<DevTicketView />} />
      <Route path="tickets/new" element={<DevTicketCreate />} />
      <Route path="profile" element={<DevProfile />} />
    </Routes>
  );
}

export default App;
