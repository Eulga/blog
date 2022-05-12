// eslint-disable -> remove warning message

import logo from "./logo.svg";
import "./App.css";
import { useState } from "react";

function App() {
  const post = "강남 우동 맛집";
  let 글제목배열 = ["남자 코트 추천", "강남 우동 맛집", "파이썬독학"];
  let [글제목, 글제목변경] = useState(글제목배열);

  let [따봉, 따봉변경] = useState(0);

  //Destucturing 문법
  // let num = [1, 2];
  // let [a, c] = [1, 2];
  // let a = num[0];
  // let b = num[1];
  // state는 내부 값 변경시 html 자동 재랜더링됨

  return (
    <div className="App">
      <div className="black-nav">
        {/* <h4 style={{ color: "red", fontSize: "16px" }}>블로그임</h4> */}
        <h4>
          ReactBlog
          <span
            onClick={() => {
              //ley copy = 글제목
              //state 함수는 내부값이 동일하다 판단되면 재랜더링 해주지 않음
              //copy에 글제목이 가진 주소가 복제됐기 때문에 state함수가 값이 달라진걸 알 수 없음
              //배열 복제는 주소저장이 아닌 배열자체를 저장해야함
              let copy = [...글제목];
              // ... -> 배열해제
              // [] -> 배열적용

              copy[0] = "여자 코트 추천";

              글제목변경(copy);
            }}
          >
            🙍‍♀️
          </span>
        </h4>
      </div>
      <button
        onClick={() => {
          let copy = [...글제목];
          copy.sort();
          글제목변경(copy);
        }}
      >
        가나다순정렬
      </button>
      {/* <h4>{post}</h4> */}
      <div className="list">
        <h4>
          {글제목[0]}
          <span
            onClick={() => {
              따봉변경(따봉 + 1);
            }}
          >
            👍
          </span>
          {따봉}
        </h4>
        <p>2월 17일 발행</p>
      </div>

      <div className="list">
        <h4>{글제목[1]}</h4>
        <p>2월 17일 발행</p>
      </div>

      <div className="list">
        <h4>{글제목[2]}</h4>
        <p>2월 17일 발행</p>
      </div>
      <Modal></Modal>
      <ModalEx></ModalEx>
    </div>
  );
}

//이것도 Component
const ModalEx = () => {
  return (
    <div>
      <div>제대로 나오나염</div>
      <div>나옴ㅋ</div>
    </div>
  );
};

//Component
// 1.반복적인 html 축약할 때
// 2.큰 페이지들
// 3.자주변경되는 것들
function Modal() {
  return (
    <div className="modal">
      <h4>제목</h4>
      <p>날짜</p>
      <p>상세내용</p>
    </div>
  );
  // state를 가져다 사용할 때 문제가 발생

  //Fragment
  //병렬로 태그를 사용해야할 때
  <>
    <div></div>
    <div></div>
  </>;
}

export default App;
