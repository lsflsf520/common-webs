<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/common/global.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
<!--弹出框的js-->
<script language="javascript" type="text/javascript">
    function showDiv(){
    document.getElementById('popDiv').style.display='block';
    document.getElementById('popIframe').style.display='block';
    document.getElementById('t_bg1').style.display='block';
    }
    function closeDiv(){
    document.getElementById('popDiv').style.display='none';
    document.getElementById('t_bg1').style.display='none';
    document.getElementById('popIframe').style.display='none';
    }
</script>

</head>

<body>
<!--wrapper start-->
<div class="wrapper">
	<!--header start-->
    <div class="header">
    	<div class="logo"><a href="${studentDomain }"><img src="${commonResDomain}${base}/images/login.png" width="185" height="35" alt="创数教育" /></a></div>
        <div class="header-right">我有帐号，<a href="${base }/user/login/loginPage">马上登录<img src="${commonResDomain}${base}/images/jt.png" width="11" height="19" alt="箭头"/></a></div>
    </div>
    <!--header end-->
    
    <!--register start-->
    <div class="register">
    	<div class="row">
        	<div class="col-sm-7">
            	<div class="register-left">
                	<div class="row">
                    	<div class="col-sm-5"></div>
                        <div class="col-sm-7">
                        	<ul>
                            	<li class="f-size">
                                	<span>死做题</span>
                                    <span>不如找方法！</span>
                                </li>
                                <li class="f-size1">
                                	<span>创数智能诊断专家帮你</span>
                                    <span>乐享数学，高效提分！</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-5">
              <form id="regForm" action="${base }/user/reg/doReg" method="post">
            	<div class="register-right">
                	<ul>
                	    <li class="red-color">${errorMsg }</li>
                	    <!-- 
                    	<li>
                    	  <div class="row">
                    	        <input type="hidden" id="email" name="email" />
                            	<div class="col-sm-9"><input type="text" id="emailPrefix" name="emailPrefix" value="${emailPrefix }" class="form-control" placeholder="QQ邮箱"></div>
                                <div class="col-sm-3">@qq.com</div>
                          </div>
                        </li>
                         -->
                        <li><input type="text" id="realName" name="realName" class="form-control" placeholder="请输入真实姓名"/></li>
                        <li id="emError" class="red-color"></li>
                    	  <!-- 
                            <div class="dropmenu" style="display:none;">
                            	<ul>
                                	<li><a href="javascript:void(0);">12335252@qq.com</a></li>
                                    <li><a href="javascript:void(0);">12335252@foxmail.com</a></li>
                                    <li><a href="javascript:void(0);">12335252@139.com</a></li>
                                    <li><a href="javascript:void(0);">12335252@163.com</a></li>
                                    <li><a href="javascript:void(0);">12335252@sohu.com</a></li>
                                </ul>
                            </div>
                           
                        <li class="r1">或者<a href="register-tel.html">使用手机注册</a></li>  -->
                        <li>
                          <input type="text" class="form-control" id="bkPwd" onfocus="document.getElementById('bkPwd').style.display='none';document.getElementById('password').style.display='block';document.getElementById('password').focus()"/>
                          <input id="password" name="password" style="display:none;" type="password" class="form-control"  placeholder="密码(6~20个字符)"/>
                        </li>
                        <li id="pwError" class="red-color"></li>
                        <li>
                          <input type="text" class="form-control" id="rtPwd" onfocus="document.getElementById('rtPwd').style.display='none';document.getElementById('retype').style.display='block';document.getElementById('retype').focus()"/>
                          <input id="retype" name="retype" style="display:none;" type="password" class="form-control"  placeholder="确认密码"/>
                        </li>
                        <li id="rpError" class="red-color"></li>
                        <!-- 
                        <li>
                        	<div class="row">
                        		<div class="col-sm-6"><span><input type="text" id="code" name="code" class="form-control"  value="${code }" placeholder="邮箱验证码"/></span></div>
                            	<div class="col-sm-6"><span><button type="button" class="button" onclick="sendEmailCode();">获取验证码</button></span></div>
                            </div>
                        </li>
                         -->
                        <li id="lookCode" style="display:none;"><a href="https://mail.qq.com" target="_blank">去邮箱查看</a></li>
                        <li id="codeError" class="red-color">${codeError }</li>
                        <li><button type="button" class="button1" onclick="doReg();">同意协议并注册</button></li>
                        <li class="agreement"><a href="javascript:showDiv()">创数教育使用协议</a></li>
                    </ul>
                </div>
              </form>
            </div>
        </div>
    </div>
    <!--register end-->
</div>
<!--wrapper end-->

<div id="popDiv" class="agreement-box" style="display:none;">
    <div class="agreement-box-close"><a href="javascript:closeDiv()">&times;</a></div> 
    <div class="agreement-box-con">
    	<ul>
        	<li class="a-title">创数教育使用协议</li>
            <li>一、总则</li>
<li>1.1为了保护网络信息安全，保障公民、法人和其他组织的合法权益，维护国家安全和社会公共利益，根据国家法律法规以及全国人大常委会的相关规定，北京创数教育科技有限公司（以下又称“17大学”）制定并按照本协议提供网络服务。用户应当充分阅读并同意本协议的全部条款并按照页面上的提示完成全部的注册程序（未成年人应与法定监护人共同完成）。用户在注册过程中点击“同意”按钮即表示用户已与17大学达成本协议，用户完全接受本协议项下的全部条款。
<li>1.2 用户注册成功后，17大学将给予每个用户一个用户帐号及相应的密码，该用户帐号和密码由用户负责保管；用户应当对以其用户帐号进行的所有活动和事件负法律责任。 
<li>1.3 用户可以使用17大学网站各个频道单项服务，当用户使用17大学各单项服务时，用户的使用行为视为其对该单项服务的服务条款以及17大学在该单项服务中发出的各类公告、声明或其它类似内容的同意。
<li>1.4 17大学用户协议以及各个频道单项服务条款和公告可由17大学随时更新，且无需另行通知。用户在使用相关服务时，应关注并遵守其所适用的相关条款。用户同意17大学不承担通知义务。17大学的通知、公告、声明或其它类似内容是本协议的一部分。 
<li>1.5用户在使用17提供的各项服务之前，应仔细阅读本服务协议。如用户不同意本服务协议，可以主动取消服务；用户继续使用17大学服务，即视为用户已了解并完全同意本服务协议各项内容，包括17大学对服务协议随时所做的任何修改。 
<li>二、注册信息和隐私保护
<li>2.1 17大学帐号（即用户ID）的所有权归17大学，用户完成注册申请手续后，获得17大学帐号的使用权。用户应提供及时、详尽及准确的个人资料，并不断更新注册资料，符合及时、详尽准确的要求。所有原始键入的资料将引用为注册资料。如果因注册信息不真实而引起的问题，并对问题发生所带来的后果，17大学不负任何责任。 
<li>2.2 用户不应将其帐号、密码转让、出售或出借予他人使用，如果17大学发现使用者并非帐号注册者本人，有权停止继续服务。如用户发现其帐号遭他人非法使用，应立即通知17大学。因黑客行为或用户违反本协议规定导致帐号、密码遭他人非法使用的，由用户本人承担因此导致的损失和一切法律责任，17大学不承担任何责任。 
<li>2.3 17大学不对外公开或向第三方提供单个用户的注册资料，除非： 
<li>（1）事先获得用户的明确授权； 
<li>（2）只有透露你的个人资料，才能提供你所要求的产品和服务； 
<li>（3）根据有关的法律法规要求； 
<li>（4）按照相关政府主管部门的要求； 
<li>（5）为维护17大学的合法权益。 
<li>2.4 在你注册17大学帐户，使用其他17大学产品或服务，访问17大学网页, 或参加促销和有奖游戏时，17大学会收集你的个人
<li>身份识别资料，并会将这些资料用于：改进为你提供的服务及网页内容。 
<li>2.5 如发现用户帐号中含有不雅文字或不恰当名称的，本社区保留取消其用户资格的权利。 
<li>( 1 )请勿以党和国家领导人或其他社会名人的真实姓名、字号、艺名、笔名注册； 
<li>( 2 )请勿以国家机构或其他机构的名称注册； 
<li>( 3 )请勿注册不文明、不健康名字，或包含歧视、侮辱、猥亵类词语的帐号； 
<li>( 4 )请勿注册易产生歧义、引起他人误解或其它不符合法律规定的帐号。 
<li>三、使用规则
<li>3.1 用户在使用17大学服务时，必须遵守中华人民共和国相关法律法规的规定，用户应同意将不会利用本服务进行任何违法或不正当的活动，包括但不限于下列行为∶ 
<li>（1）上载、展示、张贴、传播或以其它方式传送含有下列内容之一的信息： 
<li>1） 反对宪法所确定的基本原则的； 
<li>2） 危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的； 
<li>3） 损害国家荣誉和利益的； 
<li>4） 煽动民族仇恨、民族歧视、破坏民族团结的； 
<li>5） 破坏国家宗教政策，宣扬邪教和封建迷信的； 
<li>6） 散布谣言，扰乱社会秩序，破坏社会稳定的； 
<li>7） 散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的； 
<li>8） 侮辱或者诽谤他人，侵害他人合法权利的； 
<li>9） 含有虚假、有害、胁迫、侵害他人隐私、骚扰、侵害、中伤、粗俗、猥亵、或其它道德上令人反感的内容； 
<li>10）含有中国法律、法规、规章、条例以及任何具有法律效力之规范所限制或禁止的其它内容的； 
<li>（2）不得为任何非法目的而使用网络服务系统； 
<li>（3）不利用17大学服务从事以下活动： 
<li>1） 未经允许，进入计算机信息网络或者使用计算机信息网络资源的； 
<li>2） 未经允许，对计算机信息网络功能进行删除、修改或者增加的； 
<li>3） 未经允许，对进入计算机信息网络中存储、处理或者传输的数据和应用程序进行删除、修改或者增加的； 
<li>4） 故意制作、传播计算机病毒等破坏性程序的； 
<li>5） 其他危害计算机信息网络安全的行为。 
<li>3.2 用户违反本协议或相关的服务条款的规定，导致或产生的任何第三方主张的任何索赔、要求或损失，包括合理的律师费，用户同意赔偿17大学与合作公司、关联公司，并使之免受损害。对此，17大学有权视用户的行为性质，采取包括但不限于删除用户发布信息内容、暂停使用许可、终止服务、限制使用、回收17大学帐号、追究法律责任等措施。对恶意注册17大学帐号或利用17大学帐号进行违法活动、捣乱、骚扰、欺骗以及其他违反本协议的行为，17大学有权回收其帐号。同时，17大学公司会视司法部门的要求，协助调查。 
<li>3.3 用户不得对本服务任何部分或本服务之使用或获得，进行复制、拷贝、出售、转售或用于任何其它商业目的。 
<li>3.4 用户须对自己在使用17大学服务过程中的行为承担法律责任。用户承担法律责任的形式包括但不限于：对受到侵害者进行赔偿，以及在17大学公司首先承担了因用户行为导致的行政处罚或侵权损害赔偿责任后，用户应给予17大学公司等额的赔偿。 
<li>四、服务内容
<li>4.1 17大学网络服务的具体内容由17大学根据实际情况提供。 
<li>4.1 除非本服务协议另有其它明示规定，17大学所推出的新产品、新功能、新服务，均受到本服务协议之规范。 
<li>4.3 为使用本服务，用户必须能够自行经有法律资格对用户提供互联网接入服务的第三方，进入国际互联网，并应自行支付相关服务费用。此外，用户必须自行配备及负责与国际联网连线所需之一切必要装备，包括计算机、数据机或其它存取装置。 
<li>4.4 鉴于网络服务的特殊性，用户同意17大学有权不经事先通知，随时变更、中断或终止部分或全部的网络服务（包括收费网络服务）。17大学不担保网络服务不会中断，对网络服务的及时性、安全性、准确性也都不作担保。 
<li>4.5 17大学需要定期或不定期地对提供网络服务的平台或相关的设备进行检修或者维护，如因此类情况而造成网络服务（包括收费网络服务）在合理时间内的中断，17大学无需为此承担任何责任。17大学保留不经事先通知为维修保养、升级或其它目的暂停本服务任何部分的权利。 
<li>4.6 本服务或第三人可提供与其它国际互联网上之网站或资源之链接。由于17大学无法控制这些网站及资源，用户了解并同意，此类网站或资源是否可供利用，17大学不予负责，存在或源于此类网站或资源之任何内容、广告、产品或其它资料，17大学亦不予保证或负责。因使用或依赖任何此类网站或资源发布的或经由此类网站或资源获得的任何内容、商品或服务所产生的任何损害或损失 ，17大学不承担任何责任。 
<li>4.7 用户同意，17大学有权随时通过电子邮件、手机短信、电话等形式，根据在本站注册并认证用户提供的联系方式，向认证用户、学生及家长发送订单信息、促销活动信息等告知信息。
<li>4.8 用户明确同意其使用17大学网络服务所存在的风险将完全由其自己承担。用户理解并接受下载或通过17大学服务取得的任何信息资料取决于用户自己，并由其承担系统受损、资料丢失以及其它任何风险。
<li>4.9 六个月未登录的帐号，17大学保留收回或关闭的权利。 
<li>4.10 17大学有权于任何时间暂时或永久修改或终止本服务（或其任何部分），而无论其通知与否，17大学对用户和任何第三人均无需承担任何责任。 
<li>4.11 终止服务 
<li>用户同意17大学得基于其自行之考虑，因任何理由，包含但不限于长时间未使用，或17大学认为用户已经违反本服务协议的文字及精神，终止用户的密码、帐号或本服务之使用（或服务之任何部分），并将用户在本服务内任何内容加以移除并删除。用户同意依本服务协议任何规定提供之本服务，无需进行事先通知即可中断或终止，用户承认并同意，17大学可立即关闭或删除用户的帐号及用户帐号中所有相关信息及文件，及/或禁止继续使用前述文件或本服务。此外，用户同意若本服务之使用被中断或终止或用户的帐号及相关信息和文件被关闭或删除，17大学对用户或任何第三人均不承担任何责任。 
<li>五、知识产权和其他合法权益（包括但不限于名誉权、商誉权）
<li>5.1 用户专属权利 
<li>17大学尊重他人知识产权和合法权益，呼吁用户也要同样尊重知识产权和他人合法权益。若用户认为用户的知识产权或其他合法权益被侵犯，请按照以下说明向17大学提供资料。请注意：如果权利通知的陈述失实，权利通知提交者将承担对由此造成的全部法律责任（包括但不限于赔偿各种费用及律师费）。如果上述个人或单位不确定网络上可获取的资料是否侵犯了其知识产权和其他合法权益，17大学建议该个人或单位首先咨询专业人士。 
<li>为了17大学有效处理上述个人或单位的权利通知，请使用以下格式（包括各条款的序号）： 
<li>（1）权利人对涉嫌侵权内容拥有知识产权或其他合法权益和/或依法可以行使知识产权或其他合法权益的权属证明； 
<li>（2）请充分、明确地描述被侵犯了知识产权或其他合法权益的情况并请提供涉嫌侵权的第三方网址（如果有）。 
<li>（3）请指明涉嫌侵权网页的哪些内容侵犯了第2项中列明的权利。 
<li>（4）请提供权利人具体的联络信息，包括姓名、身份证或护照复印件（对自然人）、单位登记证明复印件（对单位）、通信地址、电话码、传真和电子邮件。 
<li>（5）请提供涉嫌侵权内容在信息网络上的位置（如指明您举报的含有侵权内容的出处，即：指网页地址或网页内的位置）以便我们与用户举报的含有侵权内容的网页的所有权人/管理人联系。 
<li>（6）请在权利通知中加入如下关于通知内容真实性的声明： “我保证，本通知中所述信息是充分、真实、准确的，如果本权利通知内容不完全属实，本人将承担由此产生的一切法律责任。” 
<li>（7）请用户签署该文件，如果是依法成立的机构或组织，请加盖公章。 
<li>请用户把以上资料和联络方式书面发往以下地址：　　 
<li>中国北京市朝阳区小营路银座九号大厦2层，17大学，邮政编码：100101。 
<li>5.2 用户承诺对于用户通过17大学服务（包括但不限于资源管理等）上传到17大学网站上可公开获取区域的所有内容 (即属于《中华人民共和国著作权法》规定的作品，包括但不限于文字、图片、音乐、电影、表演和录音录像制品和电脑程序等) 不侵犯任何第三人的知识产权，未经相关权利人之事先书面同意，用户不得以任何方式上传、发布、修改、传播或复制任何受著作权保护的材料、商标或属于其他人的专有信息。用户承诺对上传的所有内容均享有完整的知识产权，或者已经得到相关权利人的合法授权；如用户违反本条规定造成17大学被第三人索赔的，用户应全额补偿17大学一切费用(包括但不限于各种赔偿费、诉讼代理费及为此支出的其它合理费用)。 
<li>5.3 当第三方认为用户发表或者上传于17大学网站的信息侵犯其权利，并根据《信息网络传播权保护条例》或者相关法律规定向17大学发送权利通知书时，用户同意17大学可以自行判断决定删除涉嫌侵权信息，除非用户提交书面证据材料排除侵权的可能性，17大学将不会自动恢复上述删除的信息。 
<li>5.4 如用户在使用网络服务时违反上述任何规定，17大学有权要求用户改正或直接采取一切必要的措施(包括但不限于删除用户张贴的内容、暂停或终止用户使用网络服务的权利)以减轻用户不当行为而造成的影响。 
<li>5.5用户同意17大学在全世界范围内不限形式和载体地享有用户上传所有内容免费的、永久性的、不可撤销的、独家唯一的和完全再许可的权利，包括但不限于修改、复制、发行、展览、改编、汇编、出版、翻译、信息网络传播、广播、表演和再创作及著作权法等法律法规确定的其他权利，用户特别授权17大学以自己名义单独对第三方的侵权行为提起诉讼并获得全额赔偿。17大学无须为此向用户给予任何报酬或承担任何义务，也无须另行通知。 
<li>5.6 17大学拥有本网站内所有资料的版权。任何被授权的浏览、复制、打印和传播属于本网站内的资料必须符合以下条件： 
<li>（1） 所有的资料和图象均以获得信息为目的； 
<li>（2） 所有的资料和图象均不得用于商业目的； 
<li>（3） 所有的资料、图象及其任何部分都必须包括此版权声明； 
<li>（4） 本网站（www.17zuoye.com）所有的产品、技术与所有程序均属于17大学知识产权，在此并未授权。 
<li>（5） 未经17大学许可，任何人不得擅自（包括但不限于：以非法的方式复制、传播、展示、镜像、上载、下载）使用。否则，一起
<li>作业将依法追究法律责任。
<li>六、免责声明 
<li>6.1鉴于网络服务的特殊性，17大学不保证网络服务的及时性、安全性和准确性，用户同意17大学有权不经事先通知，随时变更、中断或终止部分或全部的网络服务而无论同意与否，17大学对用户和任何第三人均无需承担任何责任。 
<li>6.2对于经由17大学服务而传送的内容，17大学不保证前述内容的正确性、完整性或品质。用户在接受有关服务时，有可能会接触到令人不快、不适当或令人厌恶的内容。在任何情况下17大学均不对任何内容负责，包括但不限于任何内容发生任何错误或纰漏以及衍生的任何损失或损害。17大学有权（但无义务）自行拒绝或删除经由本服务提供的任何内容。用户使用上述内容，应自行承担风险。 
<li>6.3用户可通过17大学有关网络服务获得第三方的某些内容，或者17大学可能为方便用户而提供通往第三方网站的链接，但17大学不负责检查或评估任何该等第三方材料、产品、服务或网站内容的准确性，并且，17大学对此不作保证、不承担任何责任、也不负有任何义务。用户对此自行加以判断，并承担因使用该等内容而引起的所有风险，包括但不限于因对内容的正确性、完整性或实用性的依赖而产生的风险。 
<li>6.4用户经由17大学服务与广告商进行通讯联系或商业往来或参与促销活动，完全属于用户与广告商之间的行为，与17大学没有任何关系，若因商业行为所产生之任何损害或损失，17大学不承担任何责任。 
<li>6.5用户明确同意其使用17大学服务所存在的风险及其后果将完全由其自己承担，17大学对用户不承担任何责任。如因用户违反有关法律、法规或本协议项下的任何条款而给17大学或任何其他第三人造成损失，用户同意承担由此造成的损害赔偿责任。
<li>七、青少年用户特别提示
<li>青少年用户必须遵守全国青少年网络文明公约： 
<li>要善于网上学习，不浏览不良信息；要诚实友好交流，不侮辱欺诈他人；要增强自护意识，不随意约会网友；要维护网络安全，不破坏网络秩序；要有益身心健康，不沉溺虚拟时空。
<li>八、其他
<li>8.1 本协议的订立、执行和解释及争议的解决均应适用中华人民共和国法律。 
<li>8.2 如双方就本协议内容或其执行发生任何争议，双方应尽量友好协商解决；协商不成时，任何一方均可向17大学所在地的人民法院 提起诉讼。 
<li>8.3 17大学未行使或执行本服务协议任何权利或规定，不构成对前述权利或权利之放弃。 
<li>8.4 如本协议中的任何条款无论因何种原因完全或部分无效或不具有执行力，本协议的其余条款仍应有效并且有约束力。 </li>
            <li class="t-right">--创数教育</li>
        </ul>
    </div>  
</div>
    <div id="t_bg1" class="t_bg1" style="display:none;"></div>
    <iframe id='popIframe' class='popIframe' frameborder='0' ></iframe>
</body>

<script>
  function sendEmailCode(){
	  var emailPrefix = $("#emailPrefix").val();
	  if(!emailPrefix || /\s/.test(emailPrefix)){
		  $("#emError").text("QQ邮箱不能为空且不能包含空字符！");
		  return;
	  }
	  
	  if(/\w+@qq.com$/.test(emailPrefix)){
		  emailPrefix = emailPrefix.replace("@qq.com", "");
		  $("#emailPrefix").val(emailPrefix);
	  }
	  
	  var email = emailPrefix + "@qq.com";
	  $("#emError").text("");
	  $.ajax({
		  url : "${base}/valid/emailcode/send?email=" + email,
		  type : "GET",
		  success : function(data){
			  if(data && data.type && data.type != "success"){
				  $("#lookCode").css("display", "none");
				  $("#codeError").text(data.message);
				  return;
			  }
			  $("#lookCode").css("display", "block");
		  }
	  });
  }
  
  function doReg(){
	//  var emailPrefix = $("#emailPrefix").val();
	  var realName = $("#realName").val();
	  var password = $("#password").val();
	  var retype = $("#retype").val();
	  var code = $("#code").val();
	  /* if(!emailPrefix || /\s/.test(emailPrefix)){
		  $("#emError").text("QQ邮箱不能为空且不能包含空字符！");
		  return;
	  } 
	  
	  if(/\w+@qq.com$/.test(emailPrefix)){
		  emailPrefix = emailPrefix.replace("@qq.com", "");
		  $("#emailPrefix").val(emailPrefix);
	  }
	  
	  var email = emailPrefix + "@qq.com";*/
	  
	  if(!realName || !/^[\u4e00-\u9fa5]+$/.test(realName) || realName.length < 2 || realName.length > 10){
		  $("#emError").text("真实姓名为2到10个汉字");
		  return;
	  }
	  
	  $("#emError").text("");
	  if(!password || password.length < 6 || /\s/.test(password)){
		  $("#pwError").text("密码不能为空，且长度不能小于6个非空字符");
		  return false;
	  }
	  $("#pwError").text("");
	  if(retype != password){
		  $("#rpError").text("两次密码输入不一致，请重新输入密码");
		  return false;
	  }
	  $("#rpError").text("");
	  /* if(!code || code.length != 6){
		  $("#codeError").text("验证码不能为空且长度为6位数");
		  return false;
	  }
	  $("#codeError").text(""); */
	  
	  //$("#email").val(email);
	  $("#regForm").submit();
  }
</script>

</html>
