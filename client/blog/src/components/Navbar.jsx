import React from "react";
import { useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../context/authContext";
const Navbar = () => {
  const { currentUser, logout } = useContext(AuthContext);
 const navigate= useNavigate()
 const handleLogout = () =>{
  try{
    logout()
    navigate("/login")
  }
  catch(error){
    console.log(error);
  }
 }
  return (
    <div className="navbar">
      <div className="container">
        <Link className="links" to="/">
          <div className="logo">Chalito Tech </div>
        </Link>

        <div className="links">
          <Link className="link" to="/?cat=art">
            <h6>ART</h6>
          </Link>
          <Link className="link" to="/?cat=science">
            <h6>SCIENCE</h6>
          </Link>
          <Link className="link" to="/?cat=technology">
            <h6>TECHNOLOGY</h6>
          </Link>
          <Link className="link" to="/?cat=cinema">
            <h6>CINEMA</h6>
          </Link>
          <Link className="link" to="/?cat=design">
            <h6>DESIGN</h6>
          </Link>
          <Link className="link" to="/?cat=food">
            <h6>FOOD</h6>
          </Link>
          <span>{currentUser?.data?.username}</span>
          {currentUser?.data?.username ? (
            <span onClick={handleLogout}>Logout</span>
          ) : (
            <Link className="link" to="login">
              Login
            </Link>
          )}
          <span className="write">
            <Link className="link" to="/write">
              Write
            </Link>
          </span>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
