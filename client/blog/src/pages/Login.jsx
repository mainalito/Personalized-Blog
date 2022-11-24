import axios from "axios";
import React, { useState } from "react";
import { useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../context/authContext";

const Login = () => {
  const navigate = useNavigate();
  const [username, setusername] = useState(null);
  const [password, setpassword] = useState(null);
  const [error, seterror] = useState("");

  const { login } = useContext(AuthContext);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const inputs = {
        username: username,
        password: password,
      };
      await login(inputs);

      navigate("/");
    } catch (err) {
      seterror(err.response.data.message);
    }
  };
  return (
    <div className="auth" onSubmit={handleSubmit}>
      <h1>Login</h1>
      <form action="">
        <input
          type="text"
          required
          name=""
          placeholder="username"
          onChange={(e) => setusername(e.target.value.trim())}
          id="username"
        />
        <input
          type="password"
          required
          name=""
          placeholder="password"
          onChange={(e) => {
            setpassword(e.target.value.trim());
          }}
          id="password"
        />
        <button>Login</button>
        {error && <p>{error}</p>}
        <span>
          Don't you have an account? <Link to="/register">Register</Link>
        </span>
      </form>
    </div>
  );
};

export default Login;
