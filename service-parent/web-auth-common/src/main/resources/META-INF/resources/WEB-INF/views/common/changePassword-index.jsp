<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/views/common/taglibs.jsp"%>
<!--wrapper start-->
<div class="wrapper">
    <!--c-main start-->
    <div class="c-main"  id="password" style="display: block; border:0; box-shadow:0 0 0 #fff;">
        <!--change-password start-->
        <div class="change-password">
            <ul>
                <li>
                    <span>旧密码:</span><input type="password" name="" class="form-control" id="oldPassword" />
                </li>
                <li>
                    <span>新密码:</span><input type="password" name="" class="form-control" id="newPassword"/>
                </li>
                <li>
                    <span>确认密码:</span><input type="password" name="" class="form-control"  id="newPassword2" />
                </li>
                <li class="color-red" id="tips"></li>
            </ul>
        </div>
        <!--change-password end-->
        
        <div class="next" style="margin-top:25px;"><a  onclick="veriData();" class="next-btn">提交</a></div>
    </div>
    <!--c-mian end-->
    
     <!--c-main start-->
    <div class="c-main" id="succeed" style="display:none; border:0; box-shadow:0 0 0 #fff;">
        <!--change-password start-->
        <div class="change-password-suceed">
            <ul>
                <li><img src="${commonResDomain}/assets/images/icon.png" width="45" height="45"  alt="图标" /></li>
                <li>恭喜您，修改密码成功！</li>
            </ul>
        </div>
        <!--change-password end-->
    </div>
    <!--c-mian end-->
</div>
<!--wrapper end-->
<script type="text/javascript">

function veriData(){
	var oldpassword = $("#oldPassword").val();
	var newpassword = $("#newPassword").val();
	var newpassword2 = $("#newPassword2").val();
      if(!oldpassword){
    	  $('#tips').html('请输入旧密码'); 
    	  return;
      }
      if(!newpassword || newpassword.length < 6 || newpassword.length > 20 || /\s/.test(newpassword)) { 
			 $("#tips").html("");
			 $('#tips').html('密码不能为空，且长度为6到20个非空字符');
			
			 return;
      }
      if(!newpassword2){ 
			 $("#tips").html("");
			 $('#tips').html('请输入确认密码');
		
			 return;
     }
      
      if(newpassword!=newpassword2){
    	  $('#tips').html('再次输入密码不一致'); 
    	  return;
      }
	 //提交查看旧密码是输入正确
	   $.ajax({
	         url:'${msDomain}/auth/user/changePwd',
	         data:{oldpassword:oldpassword,
	               newpassword2:newpassword2
	         },    
	         type:'POST',               
	         dataType:'jsonp',            
	         success:function (data){
	        	 
	             if(data && data.type == 'success'){
	            	 $("#tips").html("");
	            	 $('#oldPassword').val('');
		             document.getElementById('succeed').style.display='block';
		             document.getElementById('password').style.display='none';
		           
	             } else { 
		             document.getElementById("tips").innerHTML=data.message ? data.message : "旧密码错误,请重新输入";
		             $('#oldPassword').val('');
		             $('#newPassword').val('');
		             $('#newPassword2').val('');
	             }
	         },
	         error:function() {   

	         }
	     });
	}
  

</script>
  
