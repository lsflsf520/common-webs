package com.yisi.stiku.LatexPdf.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.Character.UnicodeBlock;

import javax.imageio.ImageIO;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yisi.stiku.LatexPdf.Vo.ImgaeAttr;
import com.yisi.stiku.LatexPdf.utils.LatexPdfBaseConstant;
import com.yisi.stiku.utils.OffLineDocBaseConstant;

public class LatexToImageUtils {

//	private static final float SIZE_FOMULA = 25;
//	private static final float Pdf_SIZE_FOMULA = 200;
	private static Logger logger = LoggerFactory.getLogger(LatexToImageUtils.class);

	/**
	 * 获取不同系统下的输出目录
	 * 
	 * @return
	 */
	/*
	 * public static String getOutputDir(){ String outputDir =
	 * System.getProperty("user.home")+"/latextohtml"; return outputDir; }
	 */
	/**
	 * 将公式转化成图片，并返回信息
	 * 
	 * @param fomuleStr
	 *            latex公式字符串
	 * @param imageName
	 *            输出图片名字
	 * @param tag
	 *            分类标签
	 * @return 图片信息
	 */
	public ImageVO convertLatex2Image(String fomuleStr, String imageName, String tag, long problemId,ImgaeAttr imageAttr) {

		// 确保tag不为空
		if (tag == null) {
			tag = "default";
		}
		try {
			// 过滤不支持的tex符号
			fomuleStr = this.filterUnsupportTexSymbols(fomuleStr);
			TeXFormula.registerExternalFont(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS, "黑体");
			TeXFormula fomule = new TeXFormula(fomuleStr);
			TeXIcon ti = fomule.createTeXIcon(TeXConstants.STYLE_DISPLAY, imageAttr.getSizeFomula(), TeXFormula.SERIF);
			BufferedImage b = new BufferedImage(ti.getIconWidth(),
					ti.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			ti.paintIcon(null, b.getGraphics(), 0, 0);
			String imageDirName = LatexPdfBaseConstant.PDF_TEMP_IMG_FULLPATH
					+ (problemId % OffLineDocBaseConstant.DIR_COUNTS) + "/" +imageAttr.getSaveDivisionDir()+ tag;
			// 判断文件夹是否存在
			File dir = new File(imageDirName);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// 将BufferedImage变量写入文件中。
			String filepath = imageDirName + "/" + imageName;
			File imgFile = new File(filepath);
			ImageIO.write(b, "png", imgFile);
			// logger.debug("公式转换成功，存储图片:"+imgFile.getAbsolutePath());
			ImageVO vo = new ImageVO(imgFile.getAbsolutePath().replace("\\", "/"), imageName, "png", b.getWidth(),
					b.getHeight(), b);
			return vo;
		} catch (Exception e) {
			logger.error("公式转换失败,tag:" + tag, e);
			// e.printStackTrace();
		}
		return null;
	}

	public String filterUnsupportTexSymbols(String formula) {

		return formula.replace("\\hfill", "");
	}

	/**
	 * 生成结果反馈类
	 * 
	 * @author Xiang
	 *
	 */
	public class ImageVO {

		private String filepath;
		private String filename;
		private String filetype;
		private float width;
		private float height;
		private BufferedImage image;

		public ImageVO(String filepath, String filename, String filetype,
				float width, float height, BufferedImage image) {

			super();
			this.filepath = filepath;
			this.filename = filename;
			this.filetype = filetype;
			this.width = width;
			this.height = height;
			this.image = image;
		}

		public String getFilepath() {

			return filepath;
		}

		public void setFilepath(String filepath) {

			this.filepath = filepath;
		}

		public String getFilename() {

			return filename;
		}

		public void setFilename(String filename) {

			this.filename = filename;
		}

		public String getFiletype() {

			return filetype;
		}

		public void setFiletype(String filetype) {

			this.filetype = filetype;
		}

		public float getWidth() {

			return width;
		}

		public void setWidth(float width) {

			this.width = width;
		}

		public float getHeight() {

			return height;
		}

		public void setHeight(float height) {

			this.height = height;
		}

		public BufferedImage getImage() {

			return image;
		}

		public void setImage(BufferedImage image) {

			this.image = image;
		}

	}

}
