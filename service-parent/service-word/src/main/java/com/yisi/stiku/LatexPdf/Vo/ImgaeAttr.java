package com.yisi.stiku.LatexPdf.Vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ImgaeAttr {
	final private ImageBaseAttr imageBaseAttr;
	final private String saveDivisionDir;
	final private String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS").format(new Date());
	
	public ImgaeAttr(float sizeFomula, float retio_px) {
		super();
		this.imageBaseAttr =  new ImageBaseAttr(sizeFomula,retio_px);
		this.saveDivisionDir = "_"+imageBaseAttr.getSizeFomula()+"_"+imageBaseAttr.getRetio_px()+"/";
	}
	public ImgaeAttr() {
		super();
		this.imageBaseAttr = new ImageBaseAttr();
		this.saveDivisionDir = "";
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	@Override
	public String toString() {
		return this.imageBaseAttr.toString();
	}
	public String getSaveDivisionDir() {
		return saveDivisionDir;
	}
	public float getSizeFomula() {
		return this.imageBaseAttr.getSizeFomula();
	}
	public float getRetio_px() {
		return this.imageBaseAttr.getRetio_px();
	}
	public boolean isBaseImage(){
		if ("".equals(this.saveDivisionDir)) 
			return true;
		else
			return false;
	}
	public ImageBaseAttr getImageBaseAttr() {
		return imageBaseAttr;
	}
	
}
