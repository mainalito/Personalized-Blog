import React, { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import Menu from "../components/Menu";
import moment from "moment";
import { useEffect } from "react";
import axios from "axios";
import { useContext } from "react";
import { AuthContext } from "../context/authContext";

const Single = () => {
  const [post, setpost] = useState({});

  const location = useLocation();

  const postId = location.pathname.split("/")[2];

  const { currentUser } = useContext(AuthContext);
  const navigate = useNavigate();
  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await axios.get(`/posts/${postId}`);
        setpost(res.data);
      } catch (err) {
        console.log(err);
      }
    };
    fetchData();
  }, [postId]);

  const handleDelete = async () => {
    try {
      await axios.delete(`/posts/${postId}/userId/${currentUser.data.id}`);
      navigate("/");
    } catch (err) {
      console.log(err);
    }
  };
  const getText = (html) => {
    const doc = new DOMParser().parseFromString(html, "text/html");
    return doc.body.textContent;
  };

  return (
    <div className="single">
      <div className="content">
        <img
          src={post.postImageUrl} alt=""
        />
        <div className="user">
          {post.registerUser?.imageUrl && (
            <img src={post.registerUser?.imageUrl} alt="" srcset="" />
          )}
          <div className="info">
            <span>{post.registerUserUsername}</span>
            <p>Posted {moment(post.date).fromNow()}</p>
          </div>
          {currentUser.data.username === post.registerUserUsername && (
            <div className="edit">
              <Link to={`/write`} state={post}>
                <button className="editBtn">edit</button>
              </Link>
              <button onClick={handleDelete} className="deleteBtn">
                delete
              </button>
            </div>
          )}
        </div>
        <h1>{post.title}</h1>
        {getText(post.description)}
      </div>
      <Menu cat={post.category} />
    </div>
  );
};

export default Single;
