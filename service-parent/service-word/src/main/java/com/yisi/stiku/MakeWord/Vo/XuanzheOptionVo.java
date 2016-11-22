package com.yisi.stiku.MakeWord.Vo;

import java.util.List;

public class XuanzheOptionVo {
	private List<String> optionFlag;/*如 A\B\C\D等*/
	private List<String> optionContent;
	
	public List<String> getOptionFlag() {
		return optionFlag;
	}
	public void setOptionFlag(List<String> optionFlag) {
		this.optionFlag = optionFlag;
	}
	public List<String> getOptionContent() {
		return optionContent;
	}
	public void setOptionContent(List<String> optionContent) {
		this.optionContent = optionContent;
	}
	public XuanzheOptionVo(List<String> optionFlag, List<String> optionContent) {
		super();
		this.optionFlag = optionFlag;
		this.optionContent = optionContent;
	}
	
	
}
