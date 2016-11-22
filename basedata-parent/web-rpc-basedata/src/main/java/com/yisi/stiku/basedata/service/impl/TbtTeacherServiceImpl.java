package com.yisi.stiku.basedata.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yisi.stiku.basedata.dao.impl.TbtTeacherDaoImpl;
import com.yisi.stiku.basedata.entity.TbtTeacher;
import com.yisi.stiku.basedata.rpc.service.TeacherRpcService;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

@Service
@RpcService(TeacherRpcService.class)
public class TbtTeacherServiceImpl extends BaseServiceImpl<Long, TbtTeacher> implements TeacherRpcService {

	@Resource
	private TbtTeacherDaoImpl tbtTeacherDaoImpl;

	@Override
	protected BaseDaoImpl<Long, TbtTeacher> getBaseDaoImpl() {

		return tbtTeacherDaoImpl;
	}

	@Override
	public List<TbtTeacher> findBySchoolIdAndSubjectId(Long schoolId,
			Long subjectId) {

		// TbtTeacher tbtTeacher = new TbtTeacher();
		// tbtTeacher.setSchoolId(schoolId);
		// tbtTeacher.setSubjectId(subjectId);
		// String orderBySql = "order by rand()";
		// return tbtTeacherDaoImpl.findByEntity(tbtTeacher,orderBySql);

		return tbtTeacherDaoImpl.findBySchoolIdAndSubjectId(schoolId, subjectId);
	}

	public void updateByUserId(TbtTeacher teacher) {

		tbtTeacherDaoImpl.updateByUserId(teacher);
	}

	public void clearSchoolId(long userId) {

		tbtTeacherDaoImpl.clearSchoolId(userId);
	}

	@Override
	public TbtTeacher findByUserId(Long userId) {

		TbtTeacher tbtTeacher = new TbtTeacher();
		tbtTeacher.setUserId(userId);
		List<TbtTeacher> tbtTeacherList = this.findByEntity(tbtTeacher);
		if (tbtTeacherList != null && tbtTeacherList.size() == 1) {
			return tbtTeacherList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public TbtTeacher findByTeacherId(Long id) {

		return this.findById(id);
	}

}