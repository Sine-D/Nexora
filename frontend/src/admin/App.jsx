import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import { CssBaseline, ThemeProvider, createTheme } from "@mui/material";
import "./index.css";

// Providers
import { AuthProvider, useAuth } from "./context/AuthContext.jsx";
import { LayoutProvider } from "./context/LayoutContext.jsx";

// Pages
import Login from "./pages/auth/Login.jsx";
import Register from "./pages/auth/Register.jsx";
import AdminDashboard from "./pages/dashboard/AdminDashboard.jsx";
import ManagerDashboard from "./pages/dashboard/ManagerDashboard.jsx";
import ProjectList from "./pages/projects/ProjectList.jsx";
import ProjectDetails from "./pages/projects/ProjectDetails.jsx";
import ProjectManagement from "./pages/projects/ProjectManagement.jsx";
import ProjectManagementDetails from "./pages/projects/ProjectManagementDetails.jsx";
import AddProject from "./pages/projects/AddProject.jsx";
import AIAssignment from "./pages/ai/AIAssignment.jsx";
import UserList from "./pages/users/UserList.jsx";
import AdminProfile from "./pages/profile/AdminProfile.jsx";
import AdminSettingsPage from "./pages/settings/AdminSettingsPage.jsx";
import AccessControl from "./pages/access/AccessControl.jsx";

// Layout Components
import ProtectedRoute from "./components/layout/ProtectedRoute.jsx";
import Sidebar from "./components/layout/Sidebar.jsx";
import ManagerSidebar from "./components/layout/ManagerSidebar.jsx";
import ManagerTopbar from "./components/layout/ManagerTopbar.jsx";
import Topbar from "./components/layout/Topbar.jsx";
import Surface from "./components/ui/Surface.jsx";

// Admin Theme Definition
const theme = createTheme({
  palette: {
    mode: "dark",
    primary: { main: "#7c5cff" },
    secondary: { main: "#22c55e" },
    background: {
      default: "#0b1020",
      paper: "rgba(255,255,255,0.06)"
    },
    text: {
      primary: "#e7e9ee",
      secondary: "rgba(231,233,238,0.72)"
    },
    divider: "rgba(255,255,255,0.10)"
  },
  shape: { borderRadius: 16 },
  typography: {
    fontFamily: `Inter, ui-sans-serif, system-ui, -apple-system, Segoe UI, Roboto, Arial`,
    h5: { fontWeight: 900, letterSpacing: -0.4 },
    h6: { fontWeight: 900, letterSpacing: -0.2 },
    button: { textTransform: "none", fontWeight: 800 }
  },
  components: {
    MuiCssBaseline: {
      styleOverrides: {
        body: {
          backgroundImage:
            "radial-gradient(1000px 600px at 20% 10%, rgba(124,92,255,0.26), transparent 60%), radial-gradient(900px 700px at 80% 20%, rgba(34,197,94,0.16), transparent 55%)",
          backgroundAttachment: "fixed"
        }
      }
    },
    MuiPaper: { styleOverrides: { root: { backgroundImage: "none" } } },
    MuiAppBar: {
      styleOverrides: {
        root: {
          backdropFilter: "blur(14px)",
          background: "linear-gradient(180deg, rgba(15,18,35,0.92), rgba(15,18,35,0.55))",
          borderBottom: "1px solid rgba(255,255,255,0.10)"
        }
      }
    },
    MuiButton: { styleOverrides: { root: { borderRadius: 14, paddingInline: 16, height: 44 } } },
    MuiTextField: { defaultProps: { fullWidth: true, size: "small", variant: "outlined" } },
    MuiOutlinedInput: {
      styleOverrides: {
        root: {
          borderRadius: 14,
          backgroundColor: "rgba(255,255,255,0.04)",
          "&:hover .MuiOutlinedInput-notchedOutline": { borderColor: "rgba(255,255,255,0.22)" },
          "&.Mui-focused .MuiOutlinedInput-notchedOutline": { borderColor: "rgba(124,92,255,0.65)" }
        },
        notchedOutline: { borderColor: "rgba(255,255,255,0.12)" }
      }
    },
    MuiContainer: { defaultProps: { maxWidth: "xl" } },
    MuiTableHead: { styleOverrides: { root: { backgroundColor: "rgba(255,255,255,0.04)" } } },
    MuiTableCell: {
      styleOverrides: {
        root: { borderBottom: "1px solid rgba(255,255,255,0.08)" },
        head: { fontWeight: 900, opacity: 0.85 }
      }
    },
    MuiTableRow: { styleOverrides: { root: { "&:hover": { backgroundColor: "rgba(255,255,255,0.04)" } } } },
    MuiDivider: { styleOverrides: { root: { borderColor: "rgba(255,255,255,0.10)" } } }
  }
});

// ================= ADMIN SHELL =================
function AdminShell({ children }) {
  return (
    <div style={{ display: "flex", minHeight: "100vh" }}>
      <Sidebar />
      <div style={{ flex: 1 }}>
        <Topbar />
        <div style={{ minHeight: 72 }} />
        <Surface>{children}</Surface>
      </div>
    </div>
  );
}

// ================= MANAGER SHELL =================
function ManagerShell({ children }) {
  const [open, setOpen] = React.useState(false);
  const handleToggle = () => setOpen(prev => !prev);
  return (
    <div style={{ display: "flex", minHeight: "100vh" }}>
      <ManagerSidebar open={open} onClose={() => setOpen(false)} />
      <div style={{ flex: 1 }}>
        <ManagerTopbar onMenuClick={handleToggle} />
        <div style={{ minHeight: 72 }} />
        <Surface>{children}</Surface>
      </div>
    </div>
  );
}

// ================= ROLE SHELL =================
function RoleShell({ children }) {
  const { user, loading } = useAuth();
  if (loading) return <div style={{ textAlign: "center", marginTop: 100 }}>Loading...</div>;
  if (!user) return <Navigate to="/admin/login" replace />;
  const role = user.role?.toUpperCase();
  return role === "ADMIN" ? <AdminShell>{children}</AdminShell> : <ManagerShell>{children}</ManagerShell>;
}

// ================= ADMIN APP WRAPPER =================
// This wraps the admin routes with the theme and auth providers
function AdminApp() {
  return (
    <LayoutProvider>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <Routes>
          <Route path="/" element={<Navigate to="dashboard" replace />} />
          <Route path="login" element={<Login />} />
          <Route path="register" element={<Register />} />

          <Route element={<ProtectedRoute allowedRoles={["ADMIN"]} />}>
            <Route path="dashboard" element={<AdminShell><AdminDashboard /></AdminShell>} />
            <Route path="access" element={<AdminShell><AccessControl /></AdminShell>} />
            <Route path="settings" element={<AdminShell><AdminSettingsPage /></AdminShell>} />
          </Route>

          <Route element={<ProtectedRoute allowedRoles={["MANAGER"]} />}>
            <Route path="manager" element={<ManagerShell><ManagerDashboard /></ManagerShell>} />
            <Route path="projects" element={<ManagerShell><ProjectList /></ManagerShell>} />
            <Route path="projects/:projectId" element={<ManagerShell><ProjectDetails /></ManagerShell>} />
            <Route path="project-management" element={<ManagerShell><ProjectManagement /></ManagerShell>} />
            <Route path="project-management/:projectId" element={<ManagerShell><ProjectManagementDetails /></ManagerShell>} />
            <Route path="add-project" element={<ManagerShell><AddProject /></ManagerShell>} />
            <Route path="ai-assignment" element={<ManagerShell><AIAssignment /></ManagerShell>} />
          </Route>

          <Route element={<ProtectedRoute allowedRoles={["ADMIN", "MANAGER"]} />}>
            <Route path="users" element={<RoleShell><UserList /></RoleShell>} />
            <Route path="profile" element={<RoleShell><AdminProfile /></RoleShell>} />
          </Route>

          <Route path="*" element={<Navigate to="/admin/login" replace />} />
        </Routes>
      </ThemeProvider>
    </LayoutProvider>
  );
}

export default AdminApp;
