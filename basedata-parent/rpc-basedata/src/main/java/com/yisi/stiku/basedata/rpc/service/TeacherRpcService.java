package com.yisi.stiku.basedata.rpc.service;

import java.util.List;

import com.yisi.stiku.basedata.entity.TbtTeacher;

public interface TeacherRpcService {

	List<TbtTeacher> findBySchoolIdAndSubjectId(Long schoolId, Long subjectId);

	TbtTeacher findByUserId(Long userId);

	TbtTeacher findByTeacherId(Long id);
	
}
