package com.yisi.stiku.MakeWord.Vo;

import java.util.List;

import com.yisi.stiku.MakeWord.serviceutils.WordPublicConstant;
import com.yisi.stiku.common.exception.BaseRuntimeException;

/**
 * word版作业中一个段的标准格式为:<br>
 * 段标题<br>
 * 段内容<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;子段1标题<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;子段1内容<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;......<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;......<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;子段N标题<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;子段N内容<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;孙段1标题<br> 
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;孙段1内容<br> 
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;......<br> 
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;......<br> 
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;孙段N标题<br> 
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;孙段N内容<br>  
 * @author mk
 *
 * @param <S> 段内容
 */
public class StandardSegment<S> {
	
	/*段标题*/
	String title = "";
	
	/*段内容
	 * 1、当S为StyleTextVo，为实际文本内容
	 * 2、当S为List<StyleTextVo>，表格类型内容
	 * 3、当S为StandardSegment，表示需要进一步分解内容*/
	List<S> contents;
	
	/*标明为选项类型*/
	boolean isOptions = false;
	
	/*表格的边框设置，仅在contents为表格时才有用*/
	TableAttribute tableAttribute;
	
	public StandardSegment() {
		super();
	}

/*	public StandardSegment(boolean isOptions) {
		super();
		this.isOptions = isOptions;
	}*/

	public StandardSegment(String title, List<S> contents) {
		super();
		this.title = title;
		this.contents = contents;
	}
	
	/**
	 * @param title 本地标题
	 * @param contents 本段内容
	 * @param isOptions 是否为选择题
	 */
	public StandardSegment(String title, List<S> contents, boolean isOptions) {
		super();
		this.title = title;
		this.contents = contents;
		this.isOptions = isOptions;
	}

	
	public TableAttribute getTableAttribute() {
		return tableAttribute;
	}

	public void setTableBorder(TableAttribute tableAttribute) {
		this.tableAttribute = tableAttribute;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	private void checkContents(List<S> contents) {
		if (null!= contents && contents.size()>0){
			if (contents.get(0) instanceof StyleTextVo
					||contents.get(0) instanceof List
					||contents.get(0) instanceof StandardSegment){
			}else{
				throw new BaseRuntimeException(WordPublicConstant.CONTENT_ERROR,"内容不符合要求。期望是格式文本，列表或段落。");
			}
			if (contents.get(0) instanceof List){
				List<?> detail = (List<?>)contents.get(0);
				if (detail.size()==0)
					throw new BaseRuntimeException(WordPublicConstant.CONTENT_ERROR,"列表内容为空不符合要求。");
				if (detail.get(0) instanceof StyleTextVo){
				}else{
					throw new BaseRuntimeException(WordPublicConstant.CONTENT_ERROR,"列表内容不符合要求。期望是文本。");
				}
			}
		}		
	}
	
	public List<S> getContents() {
		checkContents(contents);		
		return contents;
	}
	public void setContents(List<S> contents) {
		checkContents(contents);
		this.contents = contents;
	}
	
	public boolean isOptions() {
		return isOptions;
	}

	public void setOptions(boolean isOptions) {
		this.isOptions = isOptions;
	}

	public int size(){
		if (null== contents){
			return 0;
		}
		return contents.size();
	}
	
	public boolean isLeaf(){
		if (null== contents||contents.size()==0){
			/*只有标题title有实际内容，同样视为叶子节点*/
			return true;
		}
		if (null!= contents && contents.size()>0 && contents.get(0) instanceof StyleTextVo) 
			return true;
		else
			return false;
	}
	
	public boolean isTable(){
		if (null!= contents && contents.size()>0 && contents.get(0) instanceof List) 
			return true;
		else
			return false;		
	}
	
}
