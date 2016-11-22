package com.yisi.stiku.MakeWord.Vo;

import java.util.List;

public class TableAttribute {
	private boolean top = false;
	private boolean buttom  = false;
	private boolean left  = false;
	private boolean right  = false;
	private boolean innerVertical  = false;
	private boolean innerHorizne  = false;
    /*1000大约为4个字符或者1.5-1.7厘米
     * 标准word文档的宽度在16厘米或者41个字符
     * 一个字符为240左右*/
	private List<Integer> columnWidth = null;
	
	public TableAttribute() {
		super();
	}

	public TableAttribute(boolean top, boolean buttom, boolean left,
			boolean right, boolean innerVertical, boolean innerHor) {
		super();
		this.top = top;
		this.buttom = buttom;
		this.left = left;
		this.right = right;
		this.innerVertical = innerVertical;
		this.innerHorizne = innerHor;
	}
	
	public TableAttribute(boolean top, boolean buttom, boolean left,
			boolean right, boolean innerVertical, boolean innerHorizne,
			List<Integer> columnWidth) {
		super();
		this.top = top;
		this.buttom = buttom;
		this.left = left;
		this.right = right;
		this.innerVertical = innerVertical;
		this.innerHorizne = innerHorizne;
		this.columnWidth = columnWidth;
	}

	public List<Integer> getColumnWidth() {
		return columnWidth;
	}

	public void setColumnLength(List<Integer> columnWidth) {
		this.columnWidth = columnWidth;
	}

	public boolean hasTop() {
		return top;
	}
	public void setTop(boolean top) {
		this.top = top;
	}
	public boolean hasButtom() {
		return buttom;
	}
	public void setButtom(boolean buttom) {
		this.buttom = buttom;
	}
	public boolean hasLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean hasRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean hasInnerVertical() {
		return innerVertical;
	}
	public void setInnerVertical(boolean innerVertical) {
		this.innerVertical = innerVertical;
	}
	public boolean hasInnerHorizne() {
		return innerHorizne;
	}
	public void setInnerHorizne(boolean innerHorzine) {
		this.innerHorizne = innerHorzine;
	}
	
}
