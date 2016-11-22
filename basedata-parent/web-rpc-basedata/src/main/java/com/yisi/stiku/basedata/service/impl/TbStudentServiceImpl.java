package com.yisi.stiku.basedata.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.yisi.stiku.basedata.dao.impl.TbStudentDaoImpl;
import com.yisi.stiku.basedata.entity.TbStudent;
import com.yisi.stiku.basedata.rpc.service.StudentRpcService;
import com.yisi.stiku.common.bean.GlobalResultCode;
import com.yisi.stiku.common.bean.PageInfo;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.BeanUtils;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

@Service
@RpcService(StudentRpcService.class)
public class TbStudentServiceImpl extends BaseServiceImpl<Long, TbStudent> implements StudentRpcService {

	@Resource
	private TbStudentDaoImpl tbStudentDaoImpl;

	@Override
	protected BaseDaoImpl<Long, TbStudent> getBaseDaoImpl() {

		return tbStudentDaoImpl;
	}

	@Override
	public PageImpl<TbStudent> searchStudent(long schoolId, long classId,
			String keyword, int currPage, int maxRows) {

		TbStudent queryStd = new TbStudent();
		queryStd.setSchoolId(schoolId);

		if (classId > 0) {
			queryStd.setClassId(classId);
		}

		// return tbStudentDaoImpl.findByPage(queryStd, new PageInfo(currPage,
		// maxRows), StringUtils.isNotBlank(keyword) ?
		// " (real_name like '%"+keyword+"%' or card_num like '%"+keyword+"%') "
		// : null);
		return searchStudent(queryStd, keyword, currPage, maxRows);
	}

	@Override
	public PageImpl<TbStudent> searchStudent(TbStudent queryCond,
			String keyword, int currPage, int maxRows) {

		return tbStudentDaoImpl.findByPage(queryCond, new PageInfo(currPage, maxRows),
				StringUtils.isNotBlank(keyword) ? " (s.real_name like '%" + keyword + "%' or s.card_num like '%" + keyword
						+ "%') " : null);
	}

	@Override
	public TbStudent findStudentByKaohao(String kaohao) {

		TbStudent queryStd = new TbStudent();
		queryStd.setStudyNum(kaohao);
		List<TbStudent> tbStudentList = tbStudentDaoImpl.findByEntity(queryStd);
		if (tbStudentList != null && tbStudentList.size() > 0) {
			return tbStudentList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public PageImpl<TbStudent> findStudentBySchoolId(Long schoolId, int currPage, int maxRows) {

		TbStudent queryStd = new TbStudent();
		queryStd.setSchoolId(schoolId);
		// return tbStudentDaoImpl.findByPage(queryStd, new PageInfo(currPage,
		// maxRows), StringUtils.isBlank(null)?null:null);
		return searchStudent(queryStd, null, currPage, maxRows);
	}

	@Override
	public List<TbStudent> findStudentBySchoolIdAndClassId(Long schoolId,
			Long classId) {

		TbStudent queryStd = new TbStudent();
		queryStd.setSchoolId(schoolId);
		queryStd.setClassId(classId);

		return tbStudentDaoImpl.findByEntity(queryStd);
	}

	@Override
	public TbStudent findByStudentId(Long studentId) {

		return this.findById(studentId);
	}

	@Override
	public boolean save(TbStudent student) {

		if (student.getId() == null || student.getId() <= 0l) {
			throw new BaseRuntimeException(GlobalResultCode.PARAM_ERROR);
		}

		TbStudent pstd = this.findById(student.getId());
		if (pstd == null) {
			// 如果不存在对应的学生信息，则需要插入一条数据
			this.insert(student);
			return true;
		}

		// 更新数据
		return this.update(student);
	}

	@Override
	public List<TbStudent> findListByStudentIds(Long... studentIds) {

		if (studentIds == null) {
			return new ArrayList<TbStudent>();
		}
		return tbStudentDaoImpl.findListByStudentIds(studentIds);
	}

	@Override
	public Map<Long, TbStudent> findMapByStudentIds(Long... studentIds) {

		List<TbStudent> stdList = findListByStudentIds(studentIds);

		return BeanUtils.buildPK2BeanMap(stdList);
	}

	@Override
	public List<TbStudent> findStudentList(Long schoolId, int gradeYear) {

		TbStudent queryStd = new TbStudent();
		queryStd.setSchoolId(schoolId);
		queryStd.setGradeYear(gradeYear);

		return tbStudentDaoImpl.findByEntity(queryStd);
	}
}
