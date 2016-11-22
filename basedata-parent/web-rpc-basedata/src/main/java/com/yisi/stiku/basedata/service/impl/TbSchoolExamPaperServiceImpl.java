package com.yisi.stiku.basedata.service.impl;

import java.util.Date;
import java.util.List;

import com.yisi.stiku.basedata.dao.impl.TbSchoolExamPaperDaoImpl;
import com.yisi.stiku.basedata.entity.SchoolExamPaperVO;
import com.yisi.stiku.basedata.entity.TbSchoolExamPaper;
import com.yisi.stiku.basedata.rpc.service.SchoolExamPaperRpcService;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
@RpcService(SchoolExamPaperRpcService.class)
public class TbSchoolExamPaperServiceImpl extends BaseServiceImpl<Long, TbSchoolExamPaper> implements SchoolExamPaperRpcService{
    @Resource
    private TbSchoolExamPaperDaoImpl tbSchoolExamPaperDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbSchoolExamPaper> getBaseDaoImpl() {
        return tbSchoolExamPaperDaoImpl;
    }

	@Override
	public List<TbSchoolExamPaper> findBySchoolId(long schoolId, String useType) {
		TbSchoolExamPaper schoolExamPaper = new TbSchoolExamPaper();
		schoolExamPaper.setSchoolId(schoolId);
		schoolExamPaper.setUseType(useType);
		return tbSchoolExamPaperDaoImpl.findByEntity(schoolExamPaper);
	}

	@Override
	public List<TbSchoolExamPaper> findBycreatedDtAndSchoolId(Long schoolId,
			String useType, Date createdDt) {
		return tbSchoolExamPaperDaoImpl.findByCreateDtAndSchoolID(schoolId,
				useType, createdDt);
	}
	
	public List<TbSchoolExamPaper> findListBySchooIdAreaId(long schoolId, long areaId){
		return tbSchoolExamPaperDaoImpl.findListBySchooIdAreaId(schoolId, areaId);
	}
	
	@Override
	public List<SchoolExamPaperVO> findBySchoolIdAndAreaId(Long schoolId,
			Long areaId,int gradeYear , Integer startIndex, Integer pagesize) {
		return tbSchoolExamPaperDaoImpl.findBySchoolIdAndAreaId(schoolId, areaId,gradeYear,startIndex,pagesize);
	}

	@Override
	public List<SchoolExamPaperVO> findBySchoolIdAndAreaId(Long schoolId,
			Long areaId, int gradeYear) {
		// TODO Auto-generated method stub
		return tbSchoolExamPaperDaoImpl.findBySchoolIdAndAreaId(schoolId, areaId,gradeYear);
	}
}