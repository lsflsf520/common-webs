function prePage(pageNo, url){
	if(pageNo > 1){
		pageNo--;
		_goPage(url,pageNo);
	}

}
function nextPage(pageNo,total,url){

	if(pageNo<total){
		pageNo++;
		_goPage(url,pageNo);
	}

}

function goPage(url, pageNo){

		_goPage(url,pageNo);

}


function _prePage(pageNo, url){
	if(pageNo > 1){
		pageNo--;
		_goPage(url,pageNo);
	}

}
function _nextPage(pageNo,total,url){

	if(pageNo<total){
		pageNo++;
		_goPage(url,pageNo);
	}

}

function _goPage(url, pageNo){
		
		if(url.toString().indexOf('?') != -1){
			window.location.href = encodeURI(encodeURI(url+"&pageNo="+pageNo)) ;	
		}else{
			window.location.href = encodeURI(encodeURI(url+"?pageNo="+pageNo)) ;	
		}
		

}

function goRanPage(url , pageAmount){
	
	 var pageNo = document.getElementById("_page_no_id").value; 
	 var regNum = /^[0-9]([0-9])*$/;
	 if(!regNum.test(pageNo))
		 {
		 	globalJs.alert("请输入大于0的正整数");
		 	return;
		 }
	 else
		 {
		 	if(parseInt(pageNo)<=0)
			 	{
		 			globalJs.alert("请输入大于0的正整数");
				 	return;
			 	}
		 }
	 if( parseInt(pageNo) > parseInt(pageAmount)){
		 globalJs.alert("总页数为:"+pageAmount+",你输入的为:"+pageNo+",没有这一页");
		 return;
	 }
	 
	 _goPage(url,pageNo);
	 
}

function prePage_ajax(pageNo, url){
	if(pageNo > 1){
		pageNo--;
		globalJs.alert(pageNo); 
		_goPage_ajax(url,pageNo);
	}

}
function nextPage_ajax(pageNo,total,url){

	if(pageNo<total){
		pageNo++;
		_goPage_ajax(url,pageNo);
	}

}

function goPage_ajax(url, pageNo){

		_goPage_ajax(url,pageNo);

}


function _prePage_ajax(pageNo, url){
	if(pageNo > 1){
		pageNo--;
		_goPage_ajax(url,pageNo);
	}

}
function _nextPage_ajax(pageNo,total,url){

	if(pageNo<total){
		pageNo++;
		_goPage_ajax(url,pageNo);
	}

}

function _goPage_ajax(url, pageNo){
		var _action ; 
		if(url.toString().indexOf('?') != -1){
			_action = encodeURI(encodeURI(url+"&pageNo="+pageNo)) ;	
		}else{
			_action = encodeURI(encodeURI(url+"?pageNo="+pageNo)) ;	
		}
		window.location.href=_action;

}

function _goPage_sort_ajax(url, sidx){
	var _action ; 
	if(url.toString().indexOf('?') != -1){
		_action = encodeURI(encodeURI(url+"&sidx="+sidx)) ;	
	}else{
		_action = encodeURI(encodeURI(url+"?sidx="+sidx)) ;	
	}
	window.location.href=_action;
	
}

function goRanPage_ajax(url , pageAmount){
	
	 var pageNo = document.getElementById("_page_no_id").value; 
	
	 
	 if(pageNo ==''){
		 pageNo = 1; 
	 }else if( parseInt(pageNo) > parseInt(pageAmount)){
		 globalJs.alert("浣犺緭鍏ヤ簡"+ pageNo+"椤碉紝 鍏辨湁 "+pageAmount+"椤�");
		 pageNo = 1; 
	 }
	 
	 _goPage_ajax(url,pageNo);
	 
}