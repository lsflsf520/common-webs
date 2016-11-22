package com.yisi.stiku.LatexPdf.Vo;

public class DocAttrVo {

	final private Long ownerId;
	final private int taskOrigintor;
	final private Long magazineId;
	final private String customCommand;

	public Long getOwnerId() {

		return ownerId;
	}

	public int getTaskOrigintor() {

		return taskOrigintor;
	}

	public Long getMagazineId() {

		return magazineId;
	}

	/**
	 * 线下文档的属性
	 * 
	 * @param ownerId
	 *            文档的拥有人id
	 * @param taskOrigintor
	 *            文档发起类型
	 * @param magazineId
	 *            文档的标识id
	 */
	public DocAttrVo(Long ownerId,
			int taskOrigintor, Long magazineId) {

		super();
		this.ownerId = ownerId;
		this.taskOrigintor = taskOrigintor;
		this.magazineId = magazineId;
		this.customCommand = null;
	}

	/**
	 * 线下文档的属性
	 * 
	 * @param ownerId
	 *            文档的拥有人id
	 * @param taskOrigintor
	 *            文档发起类型
	 * @param magazineId
	 *            文档的标识id
	 * @param customCommand
	 *            自定义文档生成命令（见wfhtmltopdf 命令文档）
	 */
	public DocAttrVo(Long ownerId,
			int taskOrigintor, Long magazineId, String customCommand) {

		super();
		this.ownerId = ownerId;
		this.taskOrigintor = taskOrigintor;
		this.magazineId = magazineId;
		this.customCommand = customCommand;
	}

	public String getCustomCommand() {

		return customCommand;
	}
}
