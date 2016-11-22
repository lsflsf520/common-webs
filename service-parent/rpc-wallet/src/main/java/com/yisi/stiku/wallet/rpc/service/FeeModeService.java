package com.yisi.stiku.wallet.rpc.service;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yisi.stiku.basedata.entity.TbStudent;
import com.yisi.stiku.basedata.rpc.service.StudentRpcService;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.DateUtil;
import com.yisi.stiku.common.utils.StudentUtils;
import com.yisi.stiku.common.utils.ThreadUtil;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.wallet.entity.FeeModeBean;
import com.yisi.stiku.wallet.entity.FuncCard;
import com.yisi.stiku.wallet.rpc.constant.FeeMode;

/**
 * 收费模式判定接口
 * @author shangfeng
 *
 */
@Service
public class FeeModeService {
	
	private final static Logger LOG = LoggerFactory.getLogger(FeeModeService.class);

	@Resource
	private FuncCardRpcService funcCardRpcService;
	
	@Resource
	private PayCheckRpcService payCheckRpcService;
	
	@Resource
	private StudentRpcService studentRpcService;
	
	public FeeModeBean getFeeMode(long studentId){
		TbStudent student = studentRpcService.findByStudentId(studentId);
		if(student == null){
			throw new BaseRuntimeException("NOT_EXIST", "该学生账号不存在", "student id " + studentId + " not exists.");
		}
		
		FeeModeBean feeModeBean = null;
		int grade =StudentUtils.getGradeId(student.getGradeYear());
		if(is1800Student(student.getCardNum(), student.getActiveTime())){
			if(grade == 3){
				feeModeBean = new FeeModeBean(FeeMode.M3_1800);
			}else{
				feeModeBean = new FeeModeBean(FeeMode.M12_1800);
			}
		}
		
		Long schoolId = student.getSchoolId();
		if(feeModeBean == null && schoolId != null){
			String[] schoolIdStrArr =ConfigOnZk.getValueArr("web-student/application.properties", "zizhu.print.schoolId");
			if(schoolIdStrArr != null && Arrays.asList(schoolIdStrArr).contains("" + schoolId)){
				if(grade == 3){
					feeModeBean = new FeeModeBean(FeeMode.ZIZHU_PRINT3);
				}else{
					feeModeBean = new FeeModeBean(FeeMode.ZIZHU_PRINT12);
				}
			}
		}
		
		if(feeModeBean == null){
			try{
				FuncCard funcCard = funcCardRpcService.loadByUserId(studentId);
				if(funcCard != null){
					long endTime = payCheckRpcService.getResEndTime(studentId, funcCard.getResInfoId());
					if(endTime > new Date().getTime()){
						feeModeBean = new FeeModeBean(FeeMode.valueOf(funcCard.getDbType().name()), funcCard.getDbType().getDesc(), endTime);
					}
				}
			}catch(Exception e){
				LOG.error(e.getMessage(), e);
			}
		}
		
		if(feeModeBean == null){
			if(grade==3){
				feeModeBean = new FeeModeBean(FeeMode.FREE3);
			}else{
				feeModeBean = new FeeModeBean(FeeMode.FREE12);
			}
		}
		
		return feeModeBean;
	}
	
	private boolean is1800Student(String cardNum, Date activeTime){
		String compareTimeStr = ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "old1800.student.compare.time", "2015-10-15");
		Date compareTime = DateUtil.parseDate(compareTimeStr);
		
		return StringUtils.isNotBlank(cardNum) 
				&& (cardNum.toUpperCase().startsWith("DX") || cardNum.toUpperCase().startsWith("A") || cardNum.toUpperCase().startsWith("T")) 
				&& (activeTime != null && activeTime.getTime() < compareTime.getTime());
	}
}
