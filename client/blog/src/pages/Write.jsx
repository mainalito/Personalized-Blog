import React, { useContext } from "react";
import { useState } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import Categories from "../components/Categories";
import axios from "axios";
import { AuthContext } from "../context/authContext";
import { useLocation, useNavigate } from "react-router-dom";

const Write = () => {
  const state = useLocation().state;
  

  const [description, setDescription] = useState(state?.description || "");
  const [title, settitle] = useState(state?.title || "");
  const [image, setimage] = useState(null);
  const [category, setcategory] = useState(state?.category || "");

  const { currentUser } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      
      if(!state){
        const inputObject = {
          title: title,
          description: description,
          category: category,
          registerUserId: currentUser.data.id,
        };
        const inputJson = JSON.stringify(inputObject);
  
        const blob = new Blob([inputJson], {
          type: "application/json",
        });
        // FORM DATA TO SEND OBJECT AND IMAGE
        const formdata = new FormData();
        formdata.append("imagefile", image);
        formdata.append("post", blob);

        await axios.post("/posts/save", formdata, {
          headers: { "Content-Type": "multipart/form-data" },
        });
        navigate("/");
      }
      else{
        const updateObject = {
          title: title,
          description: description,
          category: category,
          registerUserId: currentUser.data.id,
        };
        const updateJson = JSON.stringify(updateObject);
  
        const blob = new Blob([updateJson], {
          type: "application/json",
        });
        // FORM DATA TO SEND OBJECT AND IMAGE
        const formdata = new FormData();
        formdata.append("imagefile", image);
        formdata.append("post", blob);
        await axios.put(`/posts/${state.id}`,formdata, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        navigate("/")
      }
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <div className="add">
      <div className="content">
        <input
          required
          type="text"
          name="title"
          onChange={(e) => settitle(e.target.value)}
          placeholder="Title"
          id="title"
          value={title}
        />
        <div className="editorContainer">
          <ReactQuill
            className="editor"
            theme="snow"
            value={description}
            onChange={setDescription}
          />
        </div>
      </div>
      <div className="menu">
        <div className="item">
          <h1>Publish</h1>
          <span>
            <b>Status:</b> Draft
          </span>
          <span>
            <b>Visibility: </b> Public
          </span>
          <input
            style={{ display: "none" }}
            type="file"
            name="file"
            onChange={(e) => setimage(e.target.files[0])}
            id="file"
          />
          <label className="file" htmlFor="file">
            Upload Image
          </label>
          <div className="buttons">
            <button>Save as a draft</button>
            <button onClick={handleSubmit}>Publish</button>
          </div>
        </div>
        <Categories
          onChange={(e) => setcategory(e.target.value)}
          checked={category}
        />
      </div>
    </div>
  );
};

export default Write;
