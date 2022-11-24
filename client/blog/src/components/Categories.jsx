import React from "react";

const categories = ({onChange,checked}) => {
   
  return (
    <div className="item">
      <h1>Category</h1>
      <div className="cat">
        <input type="radio" checked={checked === "art"} onChange={onChange} name="cat" id="art" value="art" />
        <label htmlFor="art">Art</label>
      </div>
      <div className="cat">
        <input type="radio"  checked={checked === "science"} onChange={onChange} name="cat" id="science" value="science" />
        <label htmlFor="science">Science</label>
      </div>
      <div className="cat">
        <input type="radio" checked={checked === "technology"} onChange={onChange} name="cat" id="technology" value="technology" />
        <label htmlFor="technology">Technology</label>
      </div>
      <div className="cat">
        <input type="radio" checked={checked === "cinema"} onChange={onChange} name="cat" id="cinema" value="cinema" />
        <label htmlFor="cinema">Cinema</label>
      </div>
      <div className="cat">
        <input type="radio" checked={checked === "design"} onChange={onChange} name="cat" id="design" value="design" />
        <label htmlFor="design">Design</label>
      </div>
      <div className="cat">
        <input type="radio" checked={checked === "food"} onChange={onChange} name="cat" id="food" value="food" />
        <label htmlFor="food">Food</label>
      </div>
    </div>
  );
};

export default categories;
