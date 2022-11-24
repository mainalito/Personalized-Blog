import React from "react";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios"

const Register = () => {
  const [inputs, setInputs] = useState({
    username: "",
    email: "",
    password: "",
  });
  const [error, seterror] = useState(null)

  const navigate = useNavigate();

  const handleChange = (e) => {
    setInputs((prev) => ({ ...prev, [e.target.name]: e.target.value.trim()}));
  };
  const handleSubmit = async e =>{
    e.preventDefault()
    try {
      const response = await axios.post("auth/signup", inputs)
      navigate("/login")
    } catch (error) {
  
      seterror(error.response.data.message)

    }
  }
  return (
    <div className="auth">
      <h1>Register</h1>
      <form action="" onSubmit={handleSubmit}>
        <input
          onChange={handleChange}
          type="text"
          name="username"
          placeholder="username"
          required
          id="username"
        />
        <input
          onChange={handleChange}
          type="email"
          name="email"
          placeholder="email"
          required
          id="email"
        />
        <input
          onChange={handleChange}
          type="password"
          name="password"
          placeholder="password"
          required
          id="password"
        />
        <input className="button" type="submit" value="Submit" />
        {/* <button onClick={handleSubmit} >Register</button> */}
        {error && <span style={{color:"red"}}>{error}</span>}
        <span>
          Do you have an account? <Link to="/login">Login</Link>
        </span>
      </form>
    </div>
  );
};

export default Register;
