/** 共有数据 begin*/
var qinniuURL="http://up.qiniu.com";
var pub={
		qUpload:false,
		aUpload:false,
		problemId:'-1',
		sequence:'',
		path:'',
		answerPath:'',
		type:0,
		answer:'default',
		callback:function(){
			
		},
};
//plupload中为我们提供了mOxie对象
//有关mOxie的介绍和说明请看：https://github.com/moxiecode/moxie/wiki/API
//如果你不想了解那么多的话，那就照抄本示例的代码来得到预览的图片吧
function previewImage(file,callback){//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
	if(!file || !/image\//.test(file.type)) return; //确保文件是图片
	if(file.type=='image/gif'){//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
		var fr = new mOxie.FileReader();
		fr.onload = function(){
			callback(fr.result);
			fr.destroy();
			fr = null;
		}
		fr.readAsDataURL(file.getSource());
	}else{
		var preloader = new mOxie.Image();
		preloader.onload = function() {
			//preloader.downsize( 300, 300 );//先压缩一下要预览的图片,宽300，高300
			var imgsrc = preloader.type=='image/jpeg' ? preloader.getAsDataURL('image/jpeg',80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
			callback && callback(imgsrc); //callback传入的参数为预览图片的url
			preloader.destroy();
			preloader = null;
		};
		preloader.load( file.getSource() );
	}	
}
/** 共有数据 end*/

//问题上传
var upQuestion={
		uploader:undefined,
		tokens:[],
		keys:[],
		index:0,
		option:function(){
			return{  // General settings
				runtimes : 'html5,silverlight,html4',
				browse_button : 'browseQImg', // you can pass in id...
		        url : qinniuURL,
		        chunk_size : '1mb',
		        unique_names : true,
		        filters : {
		            max_file_size : '1mb',
					// Specify what files to browse for
		            mime_types: [
		                {title : "Image files", extensions : "jpg,gif,png"},
		            ],
				prevent_duplicates : true //不允许选取重复文件
		        },
				multipart_params: {
					  'token':this.token,
					  'key':this.key,
				},
				flash_swf_url : COMMON_RES_DOMAIN+'/assets/plugins/plupload/js/Moxie.swf',
				silverlight_xap_url : COMMON_RES_DOMAIN+'/assets/plugins/plupload/js/Moxie.xap',
		        // Post init events, bound after the internal events
		        init : {

					BeforeUpload: function(up, file) {
						console.log(up);
						console.log(file);
						var index = upQuestion.index;
						var token = upQuestion.tokens[index];
						var key = upQuestion.keys[index];
						upQuestion.uploader.setOption('multipart_params',{ 'token':token,'key':key});
						console.log(key);
						console.log(token);
						upQuestion.index=index+1;
					},
		 
		            UploadProgress: function(up, file) {
		                fileDom.uploadProgress(file);
		            },
		 
		            FilesAdded: function(up, files) {
		                // Called when files are added to queue
		                plupload.each(files, function(file) {
							console.log(file);
							var li='';
							previewImage(file,function(imgsrc){
								li='<li id="'+file.id+'" class="box-pic"><img id="img_'+file.id+'" src="'+imgsrc+'" alt="'+file.name+'"><a onclick="viewDom.deleteImg('+file.id+')" href="JavaScript:void(0);">X</a></li>';
								$("#question_list").append(li);
								var result = fileDom.verificationFile(file);
								if(!result){
									fileDom.deleteFile(file);
								}
							});
		                });
		            },
		 
		            FilesRemoved: function(up, files) {
		                plupload.each(files, function(file) {
							$("#"+file.id).remove();
		                });
		            },
		 

					UploadComplete: function(up, files) {
						pub.qUpload=true;
						fileDom.save();
					},
		            Error: function(up, args) {
					   layer.msg(args.message, {icon: 5});
					   $('a[id="cancel"]').show();
		            }
		        }
			}
		},
		init:function(){
			upQuestion.uploader = new plupload.Uploader(upQuestion.option());
			upQuestion.uploader.init();
		},
};
var upAnswer={
		uploader:undefined,
		tokens:[],
		keys:[],
		index:0,
		init:function(){
			upAnswer.uploader = new plupload.Uploader(upAnswer.option());
			upAnswer.uploader.init();
		},	
		option:function(){
			return{  // General settings
				runtimes : 'html5,silverlight,html4',
				browse_button : 'browseAImg', // you can pass in id...
		        url : qinniuURL,
		        chunk_size : '1mb',
		        unique_names : true,
		        filters : {
		            max_file_size : '1mb',
					// Specify what files to browse for
		            mime_types: [
		                {title : "Image files", extensions : "jpg,gif,png"},
		            ],
					prevent_duplicates : true //不允许选取重复文件
		        },
				multipart_params: {
					  'token':this.token,
					  'key':this.key,
				},
				flash_swf_url : COMMON_RES_DOMAIN+'/assets/plugins/plupload/js/Moxie.swf',
				silverlight_xap_url : COMMON_RES_DOMAIN+'/assets/plugins/plupload/js/Moxie.xap',
		         
		        // Post init events, bound after the internal events
		        init : {

					BeforeUpload: function(up, file) {
						console.log(up);
						console.log(file);
						var index = upAnswer.index;
						var token = upAnswer.tokens[index];
						var key = upAnswer.keys[index];
						upAnswer.uploader.setOption('multipart_params',{ 'token':token,'key':key});
						console.log(key);
						console.log(token);
						upAnswer.index=index+1;
					},
		 
		            UploadProgress: function(up, file) {
		               fileDom.uploadProgress(file);
		            },
		            FilesAdded: function(up, files) {
		                // Called when files are added to queue
		                plupload.each(files, function(file) {
							console.log(file);
							var li='';
							previewImage(file,function(imgsrc){
								li='<li id="'+file.id+'" class="box-pic"><img id="img_'+file.id+'" src="'+imgsrc+'" alt="'+file.name+'"><a onclick="viewDom.deleteImg('+file.id+')" href="JavaScript:void(0);">X</a></li>';
								$("#answer_list").append(li);
								var result = fileDom.verificationFile(file);
								if(!result){
									fileDom.deleteFile(file);
								}
							});
		                });
		            },
		 
		            FilesRemoved: function(up, files) {
		                plupload.each(files, function(file) {
							$("#"+file.id).remove();
		                });
		            },
		 
					UploadComplete: function(up, files) {
						pub.aUpload=true;
						fileDom.save();
					},
		            Error: function(up, args) {
					   layer.msg(args.message, {icon: 5});
					   $('a[id="cancel"]').show();
		            }
		        }
			}
		},
};
var uploadAjax={
		getUpToken:function(method,params){
			$.ajax({
		        type: "post",
		        url: WEB_ROOT+"/ProblemController/getUpToken"+params,
		        async :true,
		        global: false,
		        data: "{}",
		        contentType: "application/json; charset=utf-8",
		        dataType: "json",
		        success: function (map) {
		        	if(method){
		        		method(map);
		        	}
		        }
		    });
		},
		save:function(method,params){
			$.ajax({
		        type: "post",
		        url: WEB_ROOT+"/ProblemController/save"+params,
		        async :true,
		        global: false,
		        data: "{}",
		        contentType: "application/json; charset=utf-8",
		        dataType: "json",
		        success: function (map) {
		        	if(method){
		        		method(map);
		        	}
		        }
		    });
		}
};
/**文件操作*/
var fileDom={
		save:function(){
			if(pub.qUpload&&pub.aUpload){
				var params="?";
				params+='problemId='+pub.problemId+'&';
				params+='sequence='+pub.sequence+'&';
				params+='path='+pub.path+'&';
				params+='answerPath='+pub.answerPath+'&';
				params+='type='+pub.type+'&';
				params+='answer='+pub.answer+'&';
				uploadAjax.save(pub.callback,params);
				pub = {
					qUpload:false,
					aUpload:false,
					problemId:'-1',
					sequence:'',
					path:'',
					answerPath:'',
					type:0,
					answer:'default',
					callback:function(){
						
					},
				}
			}
		},
		deleteFile:function(file){
			upQuestion.uploader.removeFile(file);
			upAnswer.uploader.removeFile(file);
		},
		addFile:function(){
			
		},
		//上传验证
		verificationUpload:function(){
			pub.type=$("#type").val();
			pub.answer=$("#answer").val();
			//验证是选择题是否选择答案
			if(pub.type==0&&pub.answer=='default'){
				layer.msg('请选择选择题答案', {icon: 5});
				return false;
			}
			var qFiles = upQuestion.uploader.files;
			var aFiles = upAnswer.uploader.files;
			if(qFiles.length==0||aFiles.length==0){
				layer.msg('未上传题目或图片,请检查', {icon: 5});
				return false;
			}
			return true;
		},
		//文件验证
		verificationFile:function(file){
			//验证文件高度宽度
			var width = $("#img_"+file.id).width();
			if(width<680||width>720){
				layer.msg('添加失败!图片宽度应在680-720之间.', {
				    offset: 0,
				    shift: 6
				});
				return false;
			}
			return true;
		},
		//上传文件
		uploadFiles:function(){
			if(fileDom.verificationUpload()==false){
				return;
			}
			layer.open({
			    type: 1,
			    area: ['800px','400px'],
			    title: false,
			    closeBtn: false,
			    shadeClose: false,
			    content: viewDom.getProgressView()
			});
			var qFiles = upQuestion.uploader.files;
			var aFiles = upAnswer.uploader.files;
			var params='?';
				params+="question_count="+qFiles.length+'&';
				params+="answer_count="+aFiles.length+'&';
				params+="type=0";
			uploadAjax.getUpToken(fileDom.setUpToken,params);
		},
		//赋值
		setUpToken:function(map){
		
			pub.sequence=map.sequence;
			pub.path=map.question.path;
			pub.answerPath=map.answer.path;
			
			upQuestion.tokens=map.question.upLoadTokens;
			upQuestion.keys=map.question.imgNames;
			upAnswer.tokens=map.answer.upLoadTokens;
			upAnswer.keys=map.answer.imgNames;
			
			upQuestion.uploader.start();
			upAnswer.uploader.start();
		},
		uploadProgress:function(file){
			var QueueProgress_q = upQuestion.uploader.total;
			var QueueProgress_a = upAnswer.uploader.total;
			var str = QueueProgress_q.percent;
			var str1 = QueueProgress_a.percent;
			var percent = (Number(str)+Number(str1))/2+"%";
			console.log('上传进度：'+percent);
			viewDom.uploadProgressView(percent,'正在上传...');
		},
};
/**界面操作*/
var viewDom ={
	eventInit:function(){
		//上传按钮
		$("#startUploadImg").click(function(){
			fileDom.uploadFiles();
		});
		//取消按钮
		$('a[id="cancel"]').click(function(){
			layer.closeAll("page");
		});
	},
	deleteImg:function(file){
		upQuestion.uploader.removeFile(file);
		upAnswer.uploader.removeFile(file);
		layer.msg('删除成功!', {
		    offset: 0,
		    shift: 6
		});
	},
	getView:function(){
		var html="";
		html+='<div>';
		//html+='<!--question-input-title start-->';
		//html+='<div  class="question-input-title">试题录入</div>';
		//html+='<!--question-input-title end-->';
		html+='<!--question-input-item start-->';
		html+='<div class="question-input-item">';
		//html+='<div class="q-title">第1题</div>';
		html+='<div class="q-con">';
		html+='<ul>';
		html+='<li><span><i>试题类型：</i><select id="type"><option value="0" >选择</option><option value="1" >填空</option><option value="2" >解答</option></select></span></li>';
		html+='<li><span><i>题目：</i><a id="browseQImg" href="JavaScript:void(0);">+添加图片</a></span></li>';
		html+='<div id="question_list">';
		//html+='<li id="problem_list" class="box-pic"><img src="" width="588" height="46" alt="题目"><a href="#">X</a></li>';
		html+='</div>';
		html+='<li><span><i>正确答案：</i><select id="answer"><option value="default">请选择正确答案</option><option value="1">A</option><option value="10">B</option><option value="100">C</option><option value="1000">D</option></select></span> <span>';//<i>分值：</i> <input type="text" name="score">分</span></li>';
		html+='<li><span><i>解析：</i><a id="browseAImg" href="JavaScript:void(0);">+添加图片</a></span></li> ';
		html+='<div id="answer_list">';
		//html+=' <li id="answer_list" class="box-pic"><img src="" width="508" height="166" alt="解析"><a href="#">X</a></li>';
		html+='</div>';
		//html+='<li class="f-del"><a href="#" class="q-del">删除试题</a></li>';
		html+='</ul>';
		html+='</div>';
		html+='</div>';
		html+='<!--question-input-item end-->';
	    /*
		html+='<!--question-input-item start-->';
		html+='<div class="question-input-item">';
		html+='<div class="q-title">第2题</div>';
		html+='<div class="q-con">';
		html+='<ul>';
		html+='<li><span><i>试题类型：</i><select><option>填空</option></select></span></li>';
		html+='<li><span><i>题目：</i><a href="#">+添加图片</a></span></li>';
		html+='<li class="box-pic"><img src="" width="588" height="46" alt="题目"><a href="#">X</a></li>';
		html+='<li><span><i>分值：</i> <input type="text" name="">分</span></li>';
		html+='<li><span><i>解析：</i><a href="#">+添加图片</a></span></li>';
		html+='<li class="box-pic"><img src="" width="508" height="166" alt="解析"><a href="#">X</a></li>';
		html+=' <li class="f-del"><a href="#" class="q-del">删除试题</a></li>';
		html+='</ul>';
		html+='</div>';
		html+='<div class="q-btn"><a href="#">添加试题</a></div>';
		html+='</div>';
		html+='<!--question-input-item end-->';
	    */
		html+=' <!--question-input-bottom start-->';
		html+='<div class="question-input-bottom">';
		html+='<a id="startUploadImg" href="JavaScript:void(0);" class="q-tj1">提交</a><a id="cancel" href="JavaScript:void(0);" class="q-qx">取消</a>';
		html+='</div>';
		html+='<!--question-input-bottom end-->';
		html+='</div>';
		return html;
	},
	getProgressView:function(){
		var html="<div>";
			//html+='<div class="question-input-title">上传进度</div>';
			html+='<div class="q-info">';
			html+='<ul>';
			html+='<li><span><div id="progress_bar" style="width:1%;"></div></span><i id="progress_text" >1%</i></li>';
			html+='<li id="result_text">正在上传...</li>';
			html+=' </ul>';
			html+='</div>';
			html+='<div class="question-input-bottom" style="margin-top:30px;">';
			html+='<a id="cancel" style="display:none;" href="JavaScript:void(0);" class="q-qx">关闭</a>';
			html+='</div>';
		html+='</div>';
		return html;
	},
	uploadProgressView:function(progress,message){
		if(message&&message!=''){
			$("#result_text").html(message);
		}
		$("#progress_text").html(progress);
		$("#progress_bar").css('width',progress);
		if(progress=='100%'){
			$("#result_text").html('恭喜你上传成功！');
			$('a[id="cancel"]').show();
			viewDom.eventInit();
		}
	},
	/**试题类型*/
	selectFn:function(){
		//类型
		$("#type").change(function(){
			if($(this).val()==0||$(this).val()=='0'){
				$("#answer").prop("disabled",false);
			}else{
				$("#answer").prop("disabled",true);
			}
		});
	}
};
var uploadProblem=function(callback,problem_Id){
	pub.callback=callback;//回调函数
	console.log('begin');
	//当problemId存在时,表示为修改图片
	pub.problemId=problem_Id;
	layer.open({
		title:'试题录入',
	    type: 1,
	    area: ['1000px','600px'],
	    fix: false, //不固定
	    maxmin: true,
	    //shadeClose: true,
	    content: viewDom.getView()
	});
	upQuestion.init();
	upAnswer.init();
	viewDom.eventInit();
	viewDom.selectFn();
}