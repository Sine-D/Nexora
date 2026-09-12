// src/pages/auth/Login.jsx
import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../../context/AuthContext.jsx";
import {
  Box,
  Typography,
  TextField,
  Button,
  Paper,
  Container,
  InputAdornment,
  CircularProgress,
  Alert,
  Fade,
  IconButton
} from "@mui/material";
import { FiMail, FiLock, FiChevronRight, FiEye, FiEyeOff } from "react-icons/fi";

export default function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    if (e) e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const user = await login({ email, password });

      if (!user) throw new Error("Login failed");

      // Redirect based on role in the unified app
      const role = user.role.toUpperCase();
      if (role === "ADMIN") {
        navigate("/admin/dashboard");
      } else if (role === "MANAGER") {
        navigate("/admin/manager");
      } else if (role === "DEVELOPER") {
        // Force a full reload to ensure the developer module is loaded cleanly
        window.location.href = "/developer/";
      } else {
        navigate("/admin/login");
      }
    } catch (err) {
      setError(err.message || "Login failed");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box
      sx={{
        minHeight: "100vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        background: "radial-gradient(1000px 600px at 20% 10%, rgba(124,92,255,0.18), transparent 60%), radial-gradient(900px 700px at 80% 20%, rgba(34,197,94,0.1), transparent 55%)",
        backgroundColor: "#0b1020",
        position: "fixed",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        zIndex: 1000,
        overflowY: "auto",
        padding: 3
      }}
    >
      <Fade in timeout={800}>
        <Container maxWidth="sm">
          <Paper
            elevation={0}
            sx={{
              p: { xs: 4, md: 6 },
              borderRadius: 5,
              background: "rgba(255, 255, 255, 0.03)",
              backdropFilter: "blur(20px)",
              border: "1px solid rgba(255, 255, 255, 0.08)",
              boxShadow: "0 25px 50px -12px rgba(0, 0, 0, 0.5)",
              textAlign: "center"
            }}
          >
            {/* Logo / Title Section */}
            <Box sx={{ mb: 5 }}>
              <Typography variant="h4" fontWeight="900" gutterBottom sx={{ color: "#fff", letterSpacing: "-1px" }}>
                Login
              </Typography>
            </Box>

            {error && (
              <Alert
                severity="error"
                variant="filled"
                sx={{
                  mb: 3,
                  borderRadius: 3,
                  backgroundColor: "rgba(239, 68, 68, 0.15)",
                  color: "#fca5a5",
                  border: "1px solid rgba(239, 68, 68, 0.2)"
                }}
              >
                {error}
              </Alert>
            )}

            <form onSubmit={handleSubmit}>
              <Box sx={{ display: "flex", flexDirection: "column", gap: 2.5 }}>
                <TextField
                  fullWidth
                  placeholder="Email address"
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  autoFocus
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <FiMail style={{ color: "rgba(255,255,255,0.4)" }} />
                      </InputAdornment>
                    ),
                  }}
                  sx={{
                    "& .MuiOutlinedInput-root": {
                      color: "#fff",
                      backgroundColor: "rgba(255,255,255,0.03)",
                      borderRadius: 3,
                      "& fieldset": { borderColor: "rgba(255,255,255,0.12)" },
                      "&:hover fieldset": { borderColor: "rgba(255,255,255,0.22)" },
                      "&.Mui-focused fieldset": { borderColor: "#7c5cff" },
                    },
                  }}
                />

                <TextField
                  fullWidth
                  placeholder="Password"
                  type={showPassword ? "text" : "password"}
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <FiLock style={{ color: "rgba(255,255,255,0.4)" }} />
                      </InputAdornment>
                    ),
                    endAdornment: (
                      <InputAdornment position="end">
                        <IconButton
                          onClick={() => setShowPassword(!showPassword)}
                          edge="end"
                          sx={{ color: "rgba(255,255,255,0.4)" }}
                        >
                          {showPassword ? <FiEyeOff /> : <FiEye />}
                        </IconButton>
                      </InputAdornment>
                    ),
                  }}
                  sx={{
                    "& .MuiOutlinedInput-root": {
                      color: "#fff",
                      backgroundColor: "rgba(255,255,255,0.03)",
                      borderRadius: 3,
                      "& fieldset": { borderColor: "rgba(255,255,255,0.12)" },
                      "&:hover fieldset": { borderColor: "rgba(255,255,255,0.22)" },
                      "&.Mui-focused fieldset": { borderColor: "#7c5cff" },
                    },
                  }}
                />

                <Button
                  fullWidth
                  type="submit"
                  disabled={loading}
                  variant="contained"
                  endIcon={!loading && <FiChevronRight />}
                  sx={{
                    mt: 2,
                    py: 1.8,
                    borderRadius: 3,
                    fontSize: "1rem",
                    fontWeight: "bold",
                    background: "linear-gradient(90deg, #7c5cff 0%, #6366f1 100%)",
                    boxShadow: "0 10px 25px -5px rgba(124,92,255,0.4)",
                    textTransform: "none",
                    "&:hover": {
                      background: "linear-gradient(90deg, #6d4aff 0%, #4f46e5 100%)",
                    },
                    "&:disabled": {
                      background: "rgba(255,255,255,0.1)",
                      color: "rgba(255,255,255,0.3)"
                    }
                  }}
                >
                  {loading ? <CircularProgress size={24} color="inherit" /> : "Sign In"}
                </Button>
              </Box>
            </form>
          </Paper>


        </Container>
      </Fade>
    </Box>
  );
}
