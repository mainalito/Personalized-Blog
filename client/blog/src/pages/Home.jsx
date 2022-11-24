import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import axios from "axios";

const Home = () => {
  const [posts, setposts] = useState([]);

  const cat = useLocation().search;

  useEffect(() => {
    const fetchdata = async () => {
      try {
        const res = await axios.get(`/posts/${cat}`, {
          responseType: "json",
        });
        setposts(res.data.data);
      } catch (err) {
        console.log(err);
      }
    };
    fetchdata();
  }, [cat]);

  const getText = (html) => {
    const doc = new DOMParser().parseFromString(html, "text/html");
    return doc.body.textContent;
  };

  return (
    <div className="home">
      <div className="posts">
        {posts.map((post) => (
          <div className="post" key={post.id}>
            <div className="img">
              <img
                src={post.postImageUrl} alt=""
              />
            </div>
            <div className="content">
              <h1>{post.title}</h1>

              <p>{getText(post.description)}</p>
              <Link className="link" to={`/post/${post.id}`}>
                <button>Read More</button>
              </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Home;
