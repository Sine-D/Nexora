import React, { Suspense, lazy } from "react";
import { Routes, Route, Navigate } from "react-router-dom";

// Lazy load the two dashboards to keep them separate
import AdminApp from "./admin/App.jsx";
import { AuthProvider } from "./admin/context/AuthContext.jsx";
const DeveloperApp = lazy(() => import("./developer/App.jsx"));

function App() {
    return (
        <AuthProvider>
            <Suspense fallback={<div className="loading">Loading Nexora...</div>}>
                <Routes>
                    {/* Route /admin to the Admin Manager */}
                    <Route path="/admin/*" element={<AdminApp />} />

                    {/* Route /developer to the Developer Dashboard */}
                    <Route path="/developer/*" element={<DeveloperApp />} />

                    {/* Default route */}
                    <Route path="/" element={<Navigate to="/admin" replace />} />
                </Routes>
            </Suspense>
        </AuthProvider>
    );
}

export default App;
