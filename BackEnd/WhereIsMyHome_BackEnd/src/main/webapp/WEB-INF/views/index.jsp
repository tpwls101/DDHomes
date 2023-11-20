<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- header -->
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<!-- main menu start -->
<main>
  <div
    class="main-box container-fluid card"
    style="
      background: url(${root}/assets/img/apt3.jpg);
      background-size: cover;
      background-position: 50% 20%;
      width: 100%;
      height: 900px;
    "
  >
    <div
      class="card-img-overlay text-light md-5"
      style="background-color: rgba(0, 0, 0, 0.6); height: auto; margin-top: auto"
    >
      <div class="card-img-overlay text-light" style="height: 350px; margin-top: 100px">
        <!-- main search start -->
        <div class="container mt-5">
        
          <!-- filter start -->
          <div class="row col-md-12 justify-content-center mb-2">
            <div class="form-group col-md-2">
              <select class="form-select bg-secondary text-light" id="sido">
                <option value="" selected="selected">시도선택</option>
              </select>
            </div>
            <div class="form-group col-md-2">
              <select class="form-select bg-secondary text-light" id="gugun">
                <option value="" selected="selected">구군선택</option>
              </select>
            </div>
            <div class="form-group col-md-2">
              <select class="form-select bg-secondary text-light" id="dong">
                <option value="" selected="selected">동선택</option>
              </select>
            </div>
            <!-- <div class="form-group col-md-2">
                          <select class="form-select bg-dark text-light" id="year">
                            <option value="" selected="selected">매매년도선택</option>
                          </select>
                        </div>
                        <div class="form-group col-md-2">
                          <select class="form-select bg-dark text-light" id="month">
                            <option value="" selected="selected">매매월선택</option>
                          </select>
                        </div> -->
            <div class="form-group col-md-2">
              <button type="button" id="list-btn" class="btn btn-outline-primary">
                	아파트매매정보
              </button>
            </div>
          </div>
          <!-- filter end -->

          <!-- search start -->
          <div class="row mt-5 mb-5">
            <form action="" class="text-center">
              <input
                type="text"
                class="form-control form-control-lg mx-auto"
                placeholder="검색어를 입력하세요!(예. 수완동 정동)"
                style="max-width: 400px; display: inline"
              />
              <input type="submit" value="검색" />
            </form>
          </div>
          <!-- search end -->
          
        </div>
        <!-- main search end -->
        <div class="my-5"><br /><br /><br /></div>
        <!-- introduce start -->
        <div class="container text-center introduce-box">
          <p style="font-size: 60px">구해줘 HOMEs!!</p>
          <p style="font-size: 20px">간편하게 지역 필터 혹은 검색어로 원하는 매물을 찾아보세요!</p>
        </div>
        <!-- introduce end -->
      </div>
    </div>
  </div>
</main>
<!-- main menu end -->

<!-- footer -->
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

<script src="${root}/assets/js/util.js"></script>

<script>
    let root = "${root}";
    
    // 로드시 시도 셀렉터 설정
    setSelector(root, "sido", null);

    // 시도가 바뀌면 구군정보 얻기(util.js)
    document.querySelector("#sido").addEventListener("change", function () {
        let selectedValue = this[this.selectedIndex].value;
        setSelector(root, "gugun", selectedValue);
        
        initOption("gugun");
        initOption("dong");
    });

    // 구군이 바뀌면 동정보 얻기(util.js)
    document.querySelector("#gugun").addEventListener("change", function () {
        let selectedValue = this[this.selectedIndex].value;
        setSelector(root, "dong", selectedValue);
        
        initOption("dong");
    });

    // 사용자가 선택한 셀렉터에 따라 아파트 매매 정보 확인 페이지로 이동
    document.querySelector("#list-btn").addEventListener("click", function () {
        let selector = document.querySelector("#dong");
        let selectedValue = selector[selector.selectedIndex].value;
        
        location.href = "${root}/apt/info?selectedValue=" + selectedValue;
        
        /* location.href = "${root}/AptController?action=mapView&dongName=" + selectedValue; */
    });
</script>

</body>

</html>