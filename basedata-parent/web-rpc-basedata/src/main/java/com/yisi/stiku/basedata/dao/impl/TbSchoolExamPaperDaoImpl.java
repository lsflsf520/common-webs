package com.yisi.stiku.basedata.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yisi.stiku.basedata.dao.TbSchoolExamPaperDao;
import com.yisi.stiku.basedata.entity.SchoolExamPaperVO;
import com.yisi.stiku.basedata.entity.TbSchoolExamPaper;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

@Repository
public class TbSchoolExamPaperDaoImpl extends BaseDaoImpl<Long, TbSchoolExamPaper> {
    @Resource
    private TbSchoolExamPaperDao tbSchoolExamPaperDao;

    @Override
    protected BaseDao<Long, TbSchoolExamPaper> getProxyBaseDao() {
        return tbSchoolExamPaperDao;
    }

	public List<TbSchoolExamPaper> findByCreateDtAndSchoolID(Long schoolId,
			String useType, Date createdDt) {
		return tbSchoolExamPaperDao.finfindByCreateDtAndSchoolID(schoolId,useType,createdDt);
	}
	
	public List<TbSchoolExamPaper>  findListBySchooIdAreaId(long schoolId,long areaId){
		return this.tbSchoolExamPaperDao.findListBySchooIdAreaId(schoolId, areaId);
	}

	public List<SchoolExamPaperVO> findBySchoolIdAndAreaId(Long schoolId,
			Long areaId, int gradeYear, Integer startIndex, Integer pagesize) {
		Map paramMap = new HashMap();
		paramMap.put("schoolId",schoolId);
		paramMap.put("areaId", areaId);
		paramMap.put("grade", gradeYear);
		paramMap.put("startIndex", startIndex);
		paramMap.put("pagesize", pagesize);
		if(gradeYear==3){
			return getSqlSessionTemplate().selectList( getMapperNamespace()+ ".findBySchoolIdAndGrade", paramMap);
		}if(gradeYear==0){
			return getSqlSessionTemplate().selectList( getMapperNamespace()+ ".findAllBySchoolIdAndAreaId", paramMap);
		}else{
			return getSqlSessionTemplate().selectList( getMapperNamespace()+ ".findBySchoolIdAndAreaId", paramMap);
		}
	}
	
	public List<SchoolExamPaperVO> findBySchoolIdAndAreaId(Long schoolId,
			Long areaId, int gradeYear) {
		Map paramMap = new HashMap();
		paramMap.put("schoolId",schoolId);
		paramMap.put("areaId", areaId);
		paramMap.put("grade", gradeYear);
		if(gradeYear==3){
			return getSqlSessionTemplate().selectList( getMapperNamespace()+ ".findBySchoolIdAndGrade", paramMap);
		}else{
			return getSqlSessionTemplate().selectList( getMapperNamespace()+ ".findBySchoolIdAndAreaId", paramMap);
		}
	}
	 
}