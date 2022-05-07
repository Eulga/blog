import logo from "./logo.svg";
import "./App.css";
import { useState } from "react";

function App() {
  const post = "강남 우동 맛집";
  const [글제목배열, b1] = useState([
    "남자 코트 추천",
    "강남 우동 맛집",
    "파이썬독학",
  ]);

  //Destucturing 문법
  let num = [1, 2];
  let [a, c] = [1, 2];
  // let a = num[0];
  // let b = num[1];
  // state는 내부 값 변경시 html 자동 재랜더링됨

  return (
    <div className="App">
      <div className="black-nav">
        {/* <h4 style={{ color: "red", fontSize: "16px" }}>블로그임</h4> */}
        <h4>ReactBlog</h4>
      </div>
      {/* <h4>{post}</h4> */}
      <div className="list">
        <h4>{글제목배열[0]}</h4>
        <p>2월 17일 발행</p>
      </div>
      <div className="list">
        <h4>{글제목배열[1]}</h4>
        <p>2월 17일 발행</p>
      </div>
      <div className="list">
        <h4>{글제목배열[2]}</h4>
        <p>2월 17일 발행</p>
      </div>
    </div>
  );
}

export default App;
