package com.yisi.stiku.LatexPdf.Vo;

public class ImageBaseAttr {
	public static final float Size_Formula =  200f;
	public static final float Retio_px =  0.09f;
	
	final private float sizeFomula;
	final private float retio_px;
	public ImageBaseAttr(float sizeFomula, float retio_px) {
		super();
		this.sizeFomula = sizeFomula;
		this.retio_px = retio_px;
	}
	public ImageBaseAttr() {
		super();
		this.sizeFomula = Size_Formula;
		this.retio_px = Retio_px;
	}
	public float getSizeFomula() {
		return sizeFomula;
	}
	public float getRetio_px() {
		return retio_px;
	}
	@Override
	public String toString() {
		return "_"+sizeFomula+"_"+retio_px+"/";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(retio_px);
		result = prime * result + Float.floatToIntBits(sizeFomula);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageBaseAttr other = (ImageBaseAttr) obj;
		if (Float.floatToIntBits(retio_px) != Float
				.floatToIntBits(other.retio_px))
			return false;
		if (Float.floatToIntBits(sizeFomula) != Float
				.floatToIntBits(other.sizeFomula))
			return false;
		return true;
	}
	
}
