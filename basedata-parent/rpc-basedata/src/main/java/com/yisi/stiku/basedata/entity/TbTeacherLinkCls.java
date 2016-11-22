package com.yisi.stiku.basedata.entity;

import com.yisi.stiku.common.bean.BaseEntity;

public class TbTeacherLinkCls extends BaseEntity<Long> {

	private static final long serialVersionUID = -4982042605528345614L;
	private Long userId;

	// 一个老师可以管理多个不同的班级，但一个班级只能被一个老师管理
	private Long clsId;
	private String clsName;
	// 入学年份
	private Integer gradeYear;

	// 文理科
	private Integer type;

	/**
	 * @return the type
	 */
	public Integer getType() {

		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Integer type) {

		this.type = type;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {

		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {

		this.userId = userId;
	}

	/**
	 * @return the gradeYear
	 */
	public Integer getGradeYear() {

		return gradeYear;
	}

	/**
	 * @param gradeYear
	 *            the gradeYear to set
	 */
	public void setGradeYear(Integer gradeYear) {

		this.gradeYear = gradeYear;
	}

	@Override
	public Long getPK() {

		return userId;
	}

	/**
	 * @return the clsName
	 */
	public String getClsName() {

		return clsName;
	}

	/**
	 * @return the clsId
	 */
	public Long getClsId() {

		return clsId;
	}

	/**
	 * @param clsId
	 *            the clsId to set
	 */
	public void setClsId(Long clsId) {

		this.clsId = clsId;
	}

	/**
	 * @param clsName
	 *            the clsName to set
	 */
	public void setClsName(String clsName) {

		this.clsName = clsName;
	}

}
