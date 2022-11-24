import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const Menu = ({ cat }) => {
  const [posts, setposts] = useState([]);

  useEffect(() => {
    const fetchdata = async () => {
      try {
        const res = await axios.get(`/posts/?cat=${cat}`);
        setposts(res.data.data);
      } catch (err) {
        console.log(err);
      }
    };
    fetchdata();
  }, [cat]);
  return (
    <div className="menu">
      <h1>Other posts your may like</h1>
      {posts.map((post) => (
        <div className="post" key={post.id}>
          <img
            src={post.postImageUrl}
          />

          <h2>{post.title}</h2>

          <Link className="link" to={`/post/${post.id}`}>
            <button>Read More</button>
          </Link>
        </div>
      ))}
    </div>
  );
};

export default Menu;
