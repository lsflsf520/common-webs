package com.yisi.stiku.statdata.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.statdata.dao.ClassFenbanProblemDataDao;
import com.yisi.stiku.statdata.entity.ClassFenbanProblemData;
import com.yisi.stiku.statdata.entity.FenZuTeach;
import com.yisi.stiku.statdata.entity.Report;

@Repository
public class ClassFenbanProblemDataDaoImpl extends BaseDaoImpl<Integer, ClassFenbanProblemData> {

	@Resource
	private ClassFenbanProblemDataDao classFenbanProblemDataDao;

	@Override
	protected BaseDao<Integer, ClassFenbanProblemData> getProxyBaseDao() {

		return classFenbanProblemDataDao;
	}

	public Report queryClassFenZuTeaching(ClassFenbanProblemData data)
	{

		Report report = new Report();
		report.setWholeclassFenZuTeaching(getWhole(data, "avg_diff_score_percent"));
		// report.setExcellClassFenZuTeaching(getWhole(data,
		// "excellent_diff_score_percent"));
		report.setExcellClassFenZuTeaching(getExcell(data, "excellent_score_percent"));
		report.setPassClassFenZuTeaching(getWhole(data, "pass_diff_score_percent"));
		report.setUnPassClassFenZuTeaching(getWhole(data, "unpass_diff_score_percent"));
		return report;
	}

	private FenZuTeach getWhole(ClassFenbanProblemData data, String colum) {

		FenZuTeach fz = new FenZuTeach();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("schoolId", data.getSchoolId());
		paramMap.put("classId", data.getClassId());
		paramMap.put("weektestId", data.getWeektestId());
		paramMap.put("colum", colum);

		List<ClassFenbanProblemData> hugeByWhole = this.getSqlSessionTemplate().selectList(
				getMapperNamespace() + ".findScorePensentHuge", paramMap);
		fz.setScorePensentHuge(hugeByWhole);

		List<ClassFenbanProblemData> normalByWhole = this.getSqlSessionTemplate().selectList(
				getMapperNamespace() + ".findScorePensentNormal", paramMap);

		fz.setScorePensentNormal(normalByWhole);

		List<ClassFenbanProblemData> lowByWhole = this.getSqlSessionTemplate().selectList(
				getMapperNamespace() + ".findScorePensentLow", paramMap);
		fz.setScorePensentLow(lowByWhole);

		return fz;
	}

	private FenZuTeach getExcell(ClassFenbanProblemData data, String colum) {

		FenZuTeach fz = new FenZuTeach();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("schoolId", data.getSchoolId());
		paramMap.put("classId", data.getClassId());
		paramMap.put("weektestId", data.getWeektestId());
		paramMap.put("colum", colum);

		List<ClassFenbanProblemData> scorePensentLowToFull = this.getSqlSessionTemplate().selectList(
				getMapperNamespace() + ".findScorePensentLowToFull", paramMap);
		fz.setScorePensentLowToFull(scorePensentLowToFull);

		List<ClassFenbanProblemData> scorePensentFull = this.getSqlSessionTemplate().selectList(
				getMapperNamespace() + ".findScorePensentFull", paramMap);

		fz.setScorePensentFull(scorePensentFull);

		return fz;
	}

}