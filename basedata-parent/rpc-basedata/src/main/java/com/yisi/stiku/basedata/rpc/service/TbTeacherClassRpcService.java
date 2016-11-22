package com.yisi.stiku.basedata.rpc.service;

import com.yisi.stiku.basedata.entity.TbtTeacherClass;

public interface TbTeacherClassRpcService {

	public void newTeacherClsLink(TbtTeacherClass cls);

	public void newTeacherClsLink(TbtTeacherClass cls, Long userId);

	public void deleteTeacherClsLink(Long userId, Long clsId);

	/* 删除该老师管理的所有班级 */
	public void deleteTeacherClsLink(Long userId);

}
