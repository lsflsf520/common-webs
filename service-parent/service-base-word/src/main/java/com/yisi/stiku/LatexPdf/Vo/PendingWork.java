package com.yisi.stiku.LatexPdf.Vo;

import java.util.Map;
import java.util.Set;

public class PendingWork {

	final private String header;
	final private String footer;
	final private String fileName;
	final private String templetName;
	final private Map<String, String> contents;
	final private Map<String, String> answers;
	final private Set<Long> problemSet;
	final private boolean showPageNum;
	final private DocAttrVo docAttrVo;
	final private String workDir;
	final private CommonCallbackVo callback;

	public PendingWork(String header, String footer, String fileName,
			String templetName, Map<String, String> contents,
			Map<String, String> answers, Set<Long> problemSet,
			boolean showPageNum, DocAttrVo docAttrVo,
			String workDir, CommonCallbackVo callback) {

		super();
		this.header = header;
		this.footer = footer;
		this.fileName = fileName;
		this.templetName = templetName;
		this.contents = contents;
		this.answers = answers;
		this.problemSet = problemSet;
		this.showPageNum = showPageNum;
		this.docAttrVo = docAttrVo;
		this.workDir = workDir;
		this.callback = callback;
	}

	public CommonCallbackVo getCallback() {

		return callback;
	}

	public String getHeader() {

		return header;
	}

	public String getFooter() {

		return footer;
	}

	public String getFileName() {

		return fileName;
	}

	public String getTempletName() {

		return templetName;
	}

	public Map<String, String> getContents() {

		return contents;
	}

	public Map<String, String> getAnswers() {

		return answers;
	}

	public Set<Long> getProblemSet() {

		return problemSet;
	}

	public boolean isShowPageNum() {

		return showPageNum;
	}

	public DocAttrVo getDocAttrVo() {

		return docAttrVo;
	}

	public String getWorkDir() {

		return workDir;
	}
}
