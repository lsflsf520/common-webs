package com.yisi.stiku.basedata.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yisi.stiku.basedata.dao.TbClassDao;
import com.yisi.stiku.basedata.dao.TbStudentDao;
import com.yisi.stiku.basedata.dao.impl.TbClassDaoImpl;
import com.yisi.stiku.basedata.dao.impl.TbStudentDaoImpl;
import com.yisi.stiku.basedata.dao.impl.TblUserDataPrivDaoImpl;
import com.yisi.stiku.basedata.entity.TbClass;
import com.yisi.stiku.basedata.entity.TbStudent;
import com.yisi.stiku.basedata.rpc.service.ClassRpcService;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.StudentUtils;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

@Service
@RpcService(ClassRpcService.class)
public class TbClassServiceImpl extends BaseServiceImpl<Long, TbClass> implements ClassRpcService {

	@Resource
	private TblUserDataPrivDaoImpl tblUserDataPrivDaoImpl;

	@Resource
	private TbClassDaoImpl tbClassDaoImpl;

	@Resource
	private TbStudentDaoImpl tbStudentdaoImpl;
	@Resource
	private TbStudentDao tbStudentDao;
	@Resource
	private TbClassDao tbClassDao;

	@Override
	protected BaseDaoImpl<Long, TbClass> getBaseDaoImpl() {

		return tbClassDaoImpl;
	}

	@Override
	public List<TbClass> loadByShoolId(long schoolId) {

		TbClass tbclass = new TbClass();
		tbclass.setSchoolId(schoolId);

		return tbClassDaoImpl.findByEntity(tbclass);
	}

	@Override
	public List<TbClass> getBySchoolIdAndGradeYear(long schoolId, int gradeYear) {

		TbClass tbclass = new TbClass();
		tbclass.setSchoolId(schoolId);
		tbclass.setGradeYear(gradeYear);

		return tbClassDaoImpl.findByEntity(tbclass);
	}

	@Override
	public List<TbClass> findClassList(Long... classIds) {

		return tbClassDaoImpl.findClassList(Arrays.asList(classIds));
	}

	@Override
	public TbClass updateClassName(long classId, String name, long schoolId, int gradeYear, int type) {

		TbClass tbclass = tbClassDaoImpl.findByPK(classId);

		List<TbClass> classList = tbClassDaoImpl.getNameBytype(name, type, gradeYear, schoolId);
		if (classList != null && classList.size() > 0) {
			return classList.get(0);
		}
		else {
			List<TbStudent> list = tbStudentdaoImpl.getStudentByClassId(classId);
			if (list != null && list.size() > 0) {
				for (TbStudent tbStudent : list) {
					tbStudent.setClassNo15(Integer.parseInt(name));
					tbStudent.setsType(type);
					// studentDao.save(tbStudent);
					tbStudentDao.updateByPK(tbStudent);
				}
			}
			tbclass.setName(name);
			tbclass.setGradeYear(gradeYear);
			tbclass.setType(type);
			tbClassDao.updateByPK(tbclass);
			return null;
		}

	}

	@Override
	public long save(TbClass clazz) {

		if (clazz == null) {
			throw new BaseRuntimeException("NULL_OBJ", "被保存的对象不能为空");
		}

		if (clazz.getId() == null) {
			return this.insertReturnPK(clazz);
		}

		this.update(clazz);
		return clazz.getId();
	}

	public List<TbClass> loadByAvailableShoolId(Long schoolId) {

		TbClass tbclass = new TbClass();
		tbclass.setSchoolId(schoolId);
		// 查询所有班级

		List<TbClass> tbClassList = tbClassDaoImpl.findByEntity(tbclass);

		// 查询已关联的

		List<Long> linkedClasses = tblUserDataPrivDaoImpl.findLinkClasses(schoolId);
		// 过滤已经毕业的
		List<TbClass> validClassList = new ArrayList<TbClass>();
		for (TbClass t : tbClassList) {
			if (StudentUtils.getGradeId(t.getGradeYear()) > 3)
				continue;
			validClassList.add(t);
		}
		List<TbClass> copyResult = new ArrayList<TbClass>(validClassList);

		for (TbClass resultClass : copyResult) {
			if (linkedClasses.contains(resultClass.getId()))
				validClassList.remove(resultClass);
		}

		return validClassList;
	}
}