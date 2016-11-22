package com.yisi.stiku.quartz.dao.entity;

public enum TaskState {

	/* 待运行 */
	PENDING_RUN(1),

	/* 正在运行 */
	RUNNING(2),

	/* 人工中断 */
	MANUAL_SUSPEND(3),

	/* 失败 */
	FAILED(4),

	/* 成功 */
	SUCCESS(5);

	private Integer index;

	private TaskState(Integer index) {
		this.index = index;
	}

	public Integer getIndex() {
		return index;
	}
}
