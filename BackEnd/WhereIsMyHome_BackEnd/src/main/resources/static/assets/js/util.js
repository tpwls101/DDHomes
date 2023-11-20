
// 초기 설정: 옵션 하나도 없게 한다.
function initOption(selectorId) {
	let options = document.querySelector("#" + selectorId);
	options.length = 1;
}

// db에서 json형식으로 얻어오기
// root: context path, selectorId: 설정할 셀렉터 id속성, selectedValue: 이전 셀렉터 선택값
function setSelector(root, selectorId, selectedValue) {
	let url = root + "/apt/getSelector?selectorId=" + selectorId + "&selectedValue=" + selectedValue;

	fetch(url)
		.then((response) => response.text())
		.then((data) => addOption(selectorId, data));
}

//셀렉터 옵션 설정
function addOption(selectorId, data) {
  let list = JSON.parse(data);
  let selector;
  let length = 0;
  let opt = ``;

  switch (selectorId) {
      case "sido":
          selector = document.querySelector("#sido");
          length = list.length;
          opt += `<option value="">시도선택</option>`;
          for(let i = 0; i < length; i++) {
              opt += `<option value="` + list[i].dongCode + `">` + list[i].sidoName + `</option>`;
          }
          break;
      case "gugun":
          selector = document.querySelector("#gugun");
          length = list.length;
          opt += `<option value="">구군선택</option>`;
          for(let i = 0; i < length; i++) {
              opt += `<option value="` + list[i].dongCode + `">` + list[i].gugunName + `</option>`;
          }
          break;
      case "dong":
          selector = document.querySelector("#dong");
          length = list.length;
          opt += `<option value="">동선택</option>`;
          for(let i = 0; i < length; i++) {
              opt += `<option value="`+list[i].dongCode +`">` + list[i].dongName + `</option>`;
          }
          break;
  }
  selector.innerHTML = opt;
}

//// 검색 함수
//function getDongCode () {
//    let selector = document.querySelector("#dong");
//    let val = selector[selector.selectedIndex].value;
//    let url = "${root}/AptController?action=dongCode&dongName=" + val;
//    fetch(url)
//        .then((response) => response.text())
//        .then((data) => JSON.parse(data).dongCode)
//        .then(function(res) {
//            console.log(res);
//        });
//}
