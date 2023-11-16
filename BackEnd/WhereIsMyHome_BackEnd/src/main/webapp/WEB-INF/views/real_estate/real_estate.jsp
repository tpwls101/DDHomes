<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- header -->
<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <style>
        aside {
            width : 23%;
            /* float: left; */
            align-self: flex-start;
        }

        .main {
            width: 77%;
        }
        section {
            display: flex;
            margin: 10px;
        }
        /* select {
            max-width :200px;
            margin: auto;
        } */
        .selectStyle {
            max-width :200px;
            margin: auto;
        }
        .KakaoMap {
            position:absolute;
            width: 80%;
            height: 85%;
        }
        .resultList {
            text-align: center;
            font-weight: 600;
            font-size: 25px;
        }

    </style>

<section>
    <aside id='aside'>                   <!-- 검색결과리스트 -->
        <div class='resultList' >검색결과리스트</div>

        <table class="table table-hover text-center" style="display: none">
            <tbody id="aptlist">
                <tr>
                    <th>아파트이름</th>
                    <th>층</th>
                    <th>면적</th>
                    <th>법정동</th>
                    <th>거래금액</th>
                </tr>
            </tbody>

        </table>

        <select id='searchResultList' class="form-select" multiple aria-label="Multiple select example" style='height:1200px;'>
        </select>

    </aside>
    
    <div class='main'>        <!-- 본문 -->
        
        <div class='row' style='height: 50px;'>     <!-- 검색관련윈도우 -->
            <div class='selectStyle'>
                <select id='sido'class="form-select col" aria-label="Default select example">
                    <option selected>시도선택</option>
                </select>
            </div>

            <div class='selectStyle'>
                <select id='gugun' class="form-select col" aria-label="Default select example">
                    <option selected>구군선택</option>
                </select>
            </div>

            <div class='selectStyle'>
                <select id='dong' class="form-select col" aria-label="Default select example">
                    <option selected>동선택</option>
                </select>
            </div>

        </div>
        
        <div class='KakaoMap'>
            <div id="map" style="width:98%;height:100%;"></div>
        </div>

    </div>
</section>


<!-- footer -->
<%@ include file="/WEB-INF/views/common/footer.jsp" %>


<!--  javascript  -->

	<!-- 카카오 -->
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=34f52751f3cb84ea31470881321907c3&libraries=services"></script>
    
    <!-- 카카오 맵 관련 script -->
    <script>
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
            mapOption = { 
                center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                level: 3 // 지도의 확대 레벨
            };

        // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
        var map = new kakao.maps.Map(mapContainer, mapOption); 
        
        //////////////////////////////////////////////////////////////////////////////
        // address변수에 주소를 할당하고 함수를 호출시키면 지도가 이동되는 함수
        function searchAddress(address) {
            // 주소-좌표 변환 객체를 생성합니다
            var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
                mapOption = {
                    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                    level: 3 // 지도의 확대 레벨
                };  

            // 지도를 생성합니다    
            var map = new kakao.maps.Map(mapContainer, mapOption); 

            // 주소-좌표 변환 객체를 생성합니다
            var geocoder = new kakao.maps.services.Geocoder();

            // 주소로 좌표를 검색합니다
            geocoder.addressSearch(address, function(result, status) {

	            // 정상적으로 검색이 완료됐으면 
	            if (status === kakao.maps.services.Status.OK) {
	
	                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
	
	                // 결과값으로 받은 위치를 마커로 표시합니다
	                var marker = new kakao.maps.Marker({
	                    map: map,
	                    position: coords
	                });
	
	                // 인포윈도우로 장소에 대한 설명을 표시합니다
	                var infowindow = new kakao.maps.InfoWindow({
	                    content: '<div style="width:150px;text-align:center;padding:6px 0;">선택된 장소</div>'
	                });
	                infowindow.open(map, marker);
	
	                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	                map.setCenter(coords);
	            }
	        });    
        }
     	// 경도, 위도, 아파트이름, 층, 면적, 거래금액, 주소 변수를 넣고 함수를 호출시키면 해당 위치로 지도 이동 및 해당장소에 대한 설명 생성되는 함수.
        function panTo(lat, lng, apartName, List) {
            // 이동할 위도 경도 위치를 생성합니다 
            var coords = new kakao.maps.LatLng(lat, lng);
            
            // 결과값으로 받은 위치를 마커로 표시합니다
            var marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });
            
            var content = `<div style="width:500px;text-align:center;padding:6px 0;">`;
            content += `<div style="font-size: 30px"><strong>` + apartName + `</strong></div>`;
            
            if(List.length>5) {
	            for(i=List.length-5; i<List.length; i++) {
	            	content += `<div>` + List[i].dealYear + `년 `
	            	content += List[i].dealMonth + `월 `
	            	content += List[i].dealDay + `일 / `
	            	content += List[i].floor + `층 / `
	            	content += List[i].area + `㎡ / `
	            	content += List[i].dealAmount  + `원 </div>`
	            }
     		} else {
     			for(i=0; i<List.length; i++) {
	            	content += `<div>` + List[i].dealYear + `년 `
	            	content += List[i].dealMonth + `월 `
	            	content += List[i].dealDay + `일 / `
	            	content += List[i].floor + `층 / `
	            	content += List[i].area + `㎡ / `
	            	content += List[i].dealAmount  + `0000원 </div>`
	            }
     		}
           	content += `</div>`
            
         	// 인포윈도우로 장소에 대한 설명을 표시합니다
            var infowindow = new kakao.maps.InfoWindow({
                content: content
            });
            infowindow.open(map, marker);
            
            // 지도 중심을 부드럽게 이동시킵니다
            // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
            map.panTo(coords);            
        }   
        

        //////////////////////////////////////////////////////////////////////////////

        // HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
        if (navigator.geolocation) {
            
            // GeoLocation을 이용해서 접속 위치를 얻어옵니다
            navigator.geolocation.getCurrentPosition(function(position) {
                
                var lat = position.coords.latitude, // 위도
                    lon = position.coords.longitude; // 경도
                
                var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
                    message = '<div style="padding:5px;">사용자 위치</div>'; // 인포윈도우에 표시될 내용입니다
                
                // 마커와 인포윈도우를 표시합니다
                displayMarker(locPosition, message);
                    
            });
            
        } else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
            
            var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),    
                message = 'geolocation을 사용할수 없어요..'
                
            displayMarker(locPosition, message);
        }

        // 지도에 마커와 인포윈도우를 표시하는 함수입니다
        function displayMarker(locPosition, message) {

            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({  
                map: map, 
                position: locPosition
            }); 
            
            var iwContent = message, // 인포윈도우에 표시할 내용
                iwRemoveable = true;

            // 인포윈도우를 생성합니다
            var infowindow = new kakao.maps.InfoWindow({
                content : iwContent,
                removable : iwRemoveable
            });
            
            // 인포윈도우를 마커위에 표시합니다 
            infowindow.open(map, marker);
            
            // 지도 중심좌표를 접속위치로 변경합니다
            map.setCenter(locPosition);      
        }    
    </script>

	<!-- 시도, 구군, 동 불러오기 스크립트 -->
	<script src="${root}/assets/js/util.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script>
	    let root = "${root}";
	    // 로드시 시도 셀렉터 설정
	    setSido(root);
	
	    // 시도가 바뀌면 구군정보 얻기(util.js)
	    document.querySelector("#sido").addEventListener("change", function () {
	        let val = this[this.selectedIndex].value;
	        setGugun(root, val);
	    });
	
	    // 구군이 바뀌면 동정보 얻기(util.js)
	    document.querySelector("#gugun").addEventListener("change", function () {
	        let val = this[this.selectedIndex].value;
	        setDong(root, val);
	    });
	    
	 	// 동이 바뀌면 아파트 정보 얻어서 리스트로 출력(util.js)
	 	function sidomakeOption(data, area) {
			let json = JSON.parse(data);
			let List = json.houseInfoDtoList
			let sel = document.querySelector(area);
			List.forEach((value) => {
				let opt = document.createElement("option");
				opt.setAttribute("value", value.aptCode);
				opt.setAttribute("value1", value.lat);
				opt.setAttribute("value2", value.lng);
				opt.setAttribute("value3", value.roadName);
				opt.setAttribute("value4", value.jibun);
				opt.innerText = value.apartmentName;
				sel.appendChild(opt);
			});
	    }
	 	
	    document.querySelector("#dong").addEventListener("change", function () {
	    	initOption("searchResultList");
	    	let selectedValue = this[this.selectedIndex].value;
	    	if (selectedValue) {
	            let url = root + "/AptController?action=" + "houseInfo"+"&" + "dongName" + "=" + selectedValue;
	            fetch(url)
	                .then((response) => response.text())
	                .then((data) => sidomakeOption(data, "#searchResultList"));
	        }
	    });

	 	
	   
	 	
	</script>
	
	<!-- 리스트 관련 script -->
	<c:set var="houseInfoDtoList" value="${houseInfoDtoList}"/>
	<script>
		//let houseInfoList="${houseInfoDtoList})";
		// 검색결과리스트에있는 선택이 일어나면 위치 이동 및 정보 나타내기.
		
		
		urlSearch = new URLSearchParams(location.search);
        dongName = urlSearch.get('dongName')
        if(dongName!=null) {
        	ListPrint(dongName);
        }
        
	    function ListPrint(dongName) {
	    	initOption("searchResultList");
            let url = root + "/AptController?action=" + "houseInfo"+"&" + "dongName" + "=" + dongName;

            fetch(url)
                .then((response) => response.text())
                .then((data) => sidomakeOption(data, "#searchResultList"));
	    }
	    
        
        
	 	function mapMove(data) {
			let json = JSON.parse(data);
			//let List = json.houseInfoDtoList
			let List = json.houseDealDtoList
			return List;
	    }

	    document.querySelector("#searchResultList").addEventListener("click", function () {
	    	let apartName = this[this.selectedIndex].innerText;
	    	let aptCode = this[this.selectedIndex].value;
	    	let lat = $("#searchResultList option:selected").attr('value1');
	    	let lng = $("#searchResultList option:selected").attr('value2');
	    	let roadName = $("#searchResultList option:selected").attr('value3');
	    	let jibun = $("#searchResultList option:selected").attr('value4');
	    	if (aptCode) {
	            let url = root + "/AptController?action=" + "houseDeal"+"&" + "aptCode" + "=" + aptCode;
	            fetch(url)
	             	.then((response) => response.text())
	                .then((data) => panTo(lat, lng, apartName, mapMove(data)));
	        }
	    });
 	</script>

</body>
</html>