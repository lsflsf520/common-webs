package com.yisi.stiku.basedata.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yisi.stiku.basedata.dao.impl.TbtTeacherClassDaoImpl;
import com.yisi.stiku.basedata.entity.TbtTeacherClass;
import com.yisi.stiku.basedata.rpc.service.TbTeacherClassRpcService;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

@Service
@RpcService(TbTeacherClassRpcService.class)
public class TbtTeacherClassServiceImpl extends BaseServiceImpl<Long, TbtTeacherClass> implements TbTeacherClassRpcService {

	@Resource
	private TbtTeacherClassDaoImpl tbtTeacherClassDaoImpl;

	@Override
	protected BaseDaoImpl<Long, TbtTeacherClass> getBaseDaoImpl() {

		return tbtTeacherClassDaoImpl;
	}

	@Override
	public void newTeacherClsLink(TbtTeacherClass cls, Long userId)
	{

		Long teacherId = queryTeacherIdByUserId(userId);
		cls.setTeacherId(teacherId);
		tbtTeacherClassDaoImpl.insert(cls);
	}

	private Long queryTeacherIdByUserId(Long userId) {

		return tbtTeacherClassDaoImpl.findTeacherIdByUserId(userId);
	}

	@Override
	public void newTeacherClsLink(TbtTeacherClass cls) {

		tbtTeacherClassDaoImpl.insert(cls);
	}

	@Override
	public void deleteTeacherClsLink(Long userId, Long clsId) {

		Long teacherId = queryTeacherIdByUserId(userId);
		List<Long> clsIds = new ArrayList<Long>();
		clsIds.add(clsId);
		tbtTeacherClassDaoImpl.deleteByTeacherIdAndClassId(teacherId, clsIds);

	}

	@Override
	public void deleteTeacherClsLink(Long userId) {

		Long teacherId = queryTeacherIdByUserId(userId);
		tbtTeacherClassDaoImpl.deleteByTeacherId(teacherId);
	}

}