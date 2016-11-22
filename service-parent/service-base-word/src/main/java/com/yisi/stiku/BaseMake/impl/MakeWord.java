package com.yisi.stiku.BaseMake.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/*import java.io.InputStream;*/
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
/*import java.util.concurrent.CompletionService;*/
import java.util.concurrent.ConcurrentHashMap;
/*import java.util.concurrent.ExecutorCompletionService;*/
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
/*import javax.xml.transform.TransformerFactory;*/
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.docx4j.TextUtils;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
/*import org.docx4j.model.fields.docproperty.DocPropertyResolver;*/
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
/*import org.docx4j.openpackaging.parts.DocPropsExtendedPart;*/
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.Br;
import org.docx4j.wml.CTBorder;
import org.docx4j.wml.Color;
import org.docx4j.wml.HpsMeasure;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STBorder;
import org.docx4j.wml.STBrType;
import org.docx4j.wml.Style;
import org.docx4j.wml.Styles;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblBorders;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.docx4j.wml.U;
import org.docx4j.wml.UnderlineEnumeration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yisi.stiku.BaseMake.service.IMakeWord;
import com.yisi.stiku.BaseMake.utils.AddingAFooter;
import com.yisi.stiku.BaseMake.utils.AddingAHeader;
import com.yisi.stiku.BaseMake.utils.AddingATable;
import com.yisi.stiku.BaseMake.utils.AddingAnInlineImage;
import com.yisi.stiku.BaseMake.utils.WordBaseConstant;
import com.yisi.stiku.MakeWord.Vo.StandardOneBook;
import com.yisi.stiku.MakeWord.Vo.StandardSegment;
import com.yisi.stiku.MakeWord.Vo.StyleTextVo;
import com.yisi.stiku.MakeWord.Vo.StyleValue;
import com.yisi.stiku.MakeWord.Vo.TableAttribute;
import com.yisi.stiku.MakeWord.Vo.XuanzheOptionVo;
import com.yisi.stiku.MakeWord.serviceutils.WordPublicConstant;
import com.yisi.stiku.utils.OffLineDocBaseConstant;

@Service
public class MakeWord implements IMakeWord{
	
	public final static String SECTION_SPLIT = "@@@@";
/*	private final static String[] SUB_TITLES = {"一、","二、","三、","四、","五、","六、","七、","八、","九、","十、"};
	private final static String ANSWER_TITLE = "答案";*/
	
	
	private Logger log = LoggerFactory.getLogger(MakeWord.class);
/*	private final static InputStream XSL_FILE_NAME = MakeWord.class.getResourceAsStream("MML2OMML.XSL");
	private TransformerFactory transformerFactory;
	private Source xslSource;*/
	private Transformer transformer;
	private ExecutorService executor = Executors.newFixedThreadPool(400);
	/*图片获取任务缓存Map*/
	private static ConcurrentHashMap<String, FutureTask<Boolean>> imgDownloadMap = new ConcurrentHashMap<String, FutureTask<Boolean>>();

	private Future<P> processFuturePara(final WordprocessingMLPackage wordMLPackage,final StyleTextVo pendingText,
			final org.docx4j.wml.ObjectFactory factory,final P existP){
			return executor.submit(new Callable<P>()
				{
					@Override
					public P call() throws Exception {
						return makeSinglePara(wordMLPackage,pendingText,factory,existP);
					}
				}
			);
	}	
	
	private class ParaText{
		P p;
		StyleTextVo text;
		public P getP() {
			return p;
		}
		public StyleTextVo getText() {
			return text;
		}
		public ParaText(P p, StyleTextVo text) {
			super();
			this.p = p;
			this.text = text;
		}
	}
	
	/**
	 * 通过MML2OMML.XSL将mathml转换为ooxml
	 * @param src mathml文本
	 * @return ooxml文本
	 */
	private String TransformString(String src){
	  	String returnString = null;
	    Reader srcReader = new StringReader(src); 
	    Writer resultWriter = new StringWriter();
	    try {  
	        Source source = new StreamSource(srcReader);  
	        Result result = new StreamResult(resultWriter);  
	        transformer.transform(source, result);
	        returnString = resultWriter.toString();
	    } catch (TransformerConfigurationException e) { 
	    	log.error("TransformerConfigurationException:",e);
	    } catch (TransformerException e) {  
	    	log.error("TransformerException:",e);
	    }
	    finally{
	    	try {
				srcReader.close();
				resultWriter.close();
			} catch (IOException e) {
				log.error("关闭读写流失败:",e);
			}
	    }
		return returnString;
   }
   
	private class InitDoc4j implements Runnable{

		@Override
		public void run() {
			try {
				log.info("加载dox4j......");
				WordprocessingMLPackage.createPackage();
				log.info("加载dox4j完成");
			} catch (InvalidFormatException e) {
				log.error("初始加载dox4j类失败");
			}
			
		}
		
	}
	
	/**
	 * 创建xml转换器
	 * 创建本地图片存放目录
	 * @throws TransformerConfigurationException
	 */
    @PostConstruct
    public void initConfig() throws TransformerConfigurationException{
    	InitDoc4j initDoc4j = new InitDoc4j();
        Thread initdoc4j = new Thread(initDoc4j,"InitDocx4J");
        initdoc4j.start();
        log.info("创建图片本地缓存目录......");
        File downloadPath = new File(OffLineDocBaseConstant.LOCAL_IMAGE_CACHE_FULLPATH);
        if (!downloadPath.exists()) downloadPath.mkdirs();
        log.info("图片本地缓存目录初始化完成。");
    }

	@PreDestroy
	public void closeExecutor(){
		executor.shutdown();
		log.info("关闭word处理线程池完成。");
	}
    

	/**
	 * 生成选择题每个选项
	 * @param wordMLPackage
	 * @param pendingPart
	 * @param order
	 * @param needReturn
	 * @return
	 * @throws Exception
	 */
	private List<P> makePerOption(WordprocessingMLPackage wordMLPackage,StyleTextVo pendingPart,String order,
			boolean needReturn,org.docx4j.wml.ObjectFactory factory,
			//List<Future<P>> imgFutureList,
			List<ParaText> imgFutureList,
			boolean containImg) throws Exception{
		List<P> result = new ArrayList<P>();
		String[] textParagraphy = pendingPart.getTxt().split(SECTION_SPLIT);
		int i =0 ;
		for (String paragraphy:textParagraphy){
			if (null!=order&&!"".equals(order)){
				if (i==0) paragraphy = order+".　"+paragraphy;
			}
			P perResult = factory.createP();
			if (containImg){
				perResult = factory.createP();
				imgFutureList.add(new ParaText(perResult,new StyleTextVo(paragraphy,pendingPart.getStyleValue())));
			}else{
				perResult = makeSinglePara(wordMLPackage, new StyleTextVo(paragraphy,pendingPart.getStyleValue()),factory,perResult);
			}

			if (needReturn){
				result.add(perResult);
			}else{
				wordMLPackage.getMainDocumentPart().addObject(perResult);
			}
			i++;
		}
		return result;
	}	
	
	/**
	 * 生成选择题选项部分
	 * @param wordMLPackage
	 * @param optionList 选项文本
	 * @param optionOrderList 选项选择符（A\B\C\D etc.）
	 * @throws Exception
	 */
	private void makeOptionsPart(WordprocessingMLPackage wordMLPackage,List<StyleTextVo> optionList,List<String> optionOrderList,
			//org.docx4j.wml.ObjectFactory factory,List<Future<P>> imgFutureList) throws Exception{
			org.docx4j.wml.ObjectFactory factory,List<ParaText> imgFutureList) throws Exception{
		//long start = System.currentTimeMillis();
		/*检查答案中是否包含图片*/
		boolean includeImg = false;
		for(StyleTextVo option:optionList){
			String txt = option.getTxt();
			Document paradoc = Jsoup.parse(txt);
			Elements imgs = paradoc.getElementsByTag("img");
			if (imgs.size()>0){
				includeImg =  true;
				break;
			}
		}
		/*答案包含图片，不使用表格，每个选项单独成段落或段落群 */
		if (includeImg){
			int i=0;
			for(StyleTextVo option:optionList){
				makePerOption(wordMLPackage,option,optionOrderList.get(i),false,factory,imgFutureList,true);
				i++;
			}
		}
		else/*以表格形式增加选择题选项*/
		{
		    if (null==factory)
		    	factory = Context.getWmlObjectFactory();
			   
		    class OptionAttr{
		    	List<P> option;
		    	int length;
				public List<P> getOption() {
					return option;
				}
				public void setOption(List<P> option) {
					this.option = option;
				}
				public int getLength() {
					return length;
				}
				public void setLength(int length) {
					this.length = length;
				}
				public OptionAttr(List<P> option, int length) {
					super();
					this.option = option;
					this.length = length;
				}
		    }
	
		    Tbl table = factory.createTbl();
		    /*缺省每个选项单独一行*/
		    int rowNum = optionList.size();;
		    int colNum = 3;		    
		    List<OptionAttr> optionAttrList =  new ArrayList<OptionAttr>();
		    int k=0;
		    boolean alreadyNum = false;
		    for(StyleTextVo option:optionList){
		    	List<P> optionAnalyze = makePerOption(wordMLPackage,option,null,true,factory,imgFutureList,false);
		    	/*题目有选项为多段文本,每个选项单独一行*/
		    	if (optionAnalyze.size()>1){
		    		if (!alreadyNum){
			    		alreadyNum = true;
		    		}
		    		optionAttrList.add(new OptionAttr(optionAnalyze,0));
		    	}
		    	else/*题目选项只有一个段落*/
		    	{
					final StringWriter stringWriter = new StringWriter();
				    TextUtils.extractText(optionAnalyze.get(0), stringWriter);
				    final int paragraphLength = stringWriter.toString().length();
				    if (paragraphLength>=30)/*每个选项单独一行*/
				    {
				    	if (!alreadyNum){
					    	alreadyNum = true;
				    	}
				    }
				    else/*每行两个选项*/
				    {
				    	if (!alreadyNum){
					    	colNum = 5;
					    	rowNum = optionList.size()%2==0?optionList.size()/2:optionList.size()/2+1;				    		
				    	}

				    }
				    optionAttrList.add(new OptionAttr(optionAnalyze,paragraphLength));		    		
		    	}
		    	k++;
		    }
		    k=0;
		    for (int row=0;row<rowNum;row++){
		    	if (colNum==3){
		    		Tr tableRow = factory.createTr();
			    	for(int j=0;j<colNum;j++){
			    		if(j==0)
			    			AddingATable.addTableCell(tableRow, "",wordMLPackage,factory); 
			    		if(j==1)
			    			AddingATable.addTableCell(tableRow, optionOrderList.get(row)+".",wordMLPackage,factory);
			    		if(j==2){
			    			int temp =0;
			    			Tc tableCell = null;
			    			for(P p:optionAttrList.get(row).getOption()){
			    				AddingATable.setParagraphyCellAlign(p);
			    				if (temp==0) 
			    					tableCell = AddingATable.addTableCell(tableRow, p,wordMLPackage,factory,null); 
			    				else
			    					AddingATable.tableCellAddP(tableCell,p);
			    				temp++;
			    			}
			    		}
			    	}
			    	table.getContent().add(tableRow);
		    	}
		    	else/*colNum==5*/
		    	{
    				Tr tableRow = factory.createTr();
			    	for(int j=0;j<colNum;j++){
			    		if(j==0)
			    			AddingATable.addTableCell(tableRow, "",wordMLPackage,factory); 
			    		if(j==1)
			    			AddingATable.addTableCell(tableRow, optionOrderList.get(row*2)+".",wordMLPackage,factory);
			    		if(j==2||j==4){
			    			P p = optionAttrList.get(j==2?row*2:row*2+1).getOption().get(0);
			    			AddingATable.setParagraphyCellAlign(p);
			    			Tc tableCell = AddingATable.addTableCell(tableRow, p,wordMLPackage,factory,null);
			    			AddingATable.setCellWidth(tableCell, 4000);
			    		}
			    		if(j==3)
			    			AddingATable.addTableCell(tableRow, optionOrderList.get(row*2+1)+".",wordMLPackage,factory);
			    	}	    				
    				table.getContent().add(tableRow);		    		
		    	}
		    }
		    wordMLPackage.getMainDocumentPart().addObject(table);			
		}
	}	
	
	/**
	 * 拆解文本，生成题干或答案部分的段落列表
	 * @param wordMLPackage
	 * @param pendingPart ooxml格式的文本，段落之间以$$$分隔
	 * @param problemIndex 题目或答案序号，不需增加序号，可设为-1
	 * @throws Exception
	 */
	private void disassemblyForParas(WordprocessingMLPackage wordMLPackage,StyleTextVo pendingPart, int problemIndex,
			org.docx4j.wml.ObjectFactory factory,List<ParaText> imgFutureList) throws Exception{
		String txt = pendingPart.getTxt();
	    String[] textParagraphy = (problemIndex==-1?txt:problemIndex+".　"+txt).split(SECTION_SPLIT);
		for (String paragraphy:textParagraphy){
			P p = factory.createP();
			imgFutureList.add(new ParaText(p,new StyleTextVo(paragraphy,pendingPart.getStyleValue())));
			wordMLPackage.getMainDocumentPart().addObject(p);
		}
	}	
	
	/**
	 * 正文（非标题）文本中每个单独的段落处理,其中可能文字和图片混合
	 * @param wmp
	 * @param pendingTextWithStyle 带格式的ooxml文本一段
	 * @param factory
	 * @param existsP 已存在的段落
	 * @return 更新后的段落
	 * @throws Exception
	 */
	private org.docx4j.wml.P makeSinglePara(WordprocessingMLPackage wmp,StyleTextVo pendingTextWithStyle,
			org.docx4j.wml.ObjectFactory factory,P existsP) throws Exception{
		//long start = System.currentTimeMillis();
		Pattern p=Pattern.compile("<m:oMathPara.*?</m:oMathPara>|<img.*?/>");
		String pureTxt = pendingTextWithStyle.getTxt();
		Matcher m=p.matcher(pureTxt);
		org.docx4j.wml.P singleP = existsP;
		int preEndIndex = 0; 
		while(m.find()){  
			String matchText = m.group();

			if (matchText.startsWith("<m:oMathPara")){
				/*处理从上一个匹配串的结束到到新匹配串开始之间文字*/
				if (preEndIndex<m.start()){
					singleP = addTextToP(pureTxt.substring(preEndIndex, m.start()),factory,singleP,pendingTextWithStyle.getStyleValue());
				}
				/*处理本次匹配的公式*/
				if (null==singleP)	singleP = factory.createP();
				singleP.getContent().add(XmlUtils.unmarshalString(matchText));
				
			}else
			/*处理本次匹配的图片*/
			if (matchText.startsWith("<img"))
			{
				if (preEndIndex<m.start()){
					singleP = addTextToP(pureTxt.substring(preEndIndex, m.start()),factory,singleP,pendingTextWithStyle.getStyleValue());
				}
				Elements imgs = Jsoup.parse(matchText).getElementsByTag("img");
				String imgPath = imgs.first().attr("src");
				singleP = processImg(wmp, imgPath,singleP,factory) ;				
			}

			preEndIndex = m.end();
		}
		if (preEndIndex<pureTxt.length()){
			singleP =  addTextToP(pureTxt.substring(preEndIndex, pureTxt.length()),factory,singleP,pendingTextWithStyle.getStyleValue());			
		}
		if (null!=pendingTextWithStyle.getStyleValue()&&!WordPublicConstant.ALIGN_LEFT.equals(pendingTextWithStyle.getStyleValue().getAlign())){
			setParaJcAlign(singleP,JcEnumeration.fromValue(pendingTextWithStyle.getStyleValue().getAlign()));
		}
		return singleP;		
	}	

	  /**
	   * @Description: 设置段落水平对齐方式
	   */
	  public static void setParaJcAlign(P paragraph, JcEnumeration hAlign) {
	    if (hAlign != null) {
	      PPr pprop = paragraph.getPPr();
	      if (pprop == null) {
	        pprop = new PPr();
	        paragraph.setPPr(pprop);
	      }
	      Jc align = new Jc();
	      align.setVal(hAlign);
	      pprop.setJc(align);
	    }
	  }	
	
	/**
	 * 将纯文本转换为可以增加到段落P上的R
	 * @param factory
	 * @param text 纯文本
	 * @param styleValue 文本的格式
	 * @return 进行了格式设置的文本R
	 */
	public static R makeStylePureSimpleText(ObjectFactory factory,String text,StyleValue styleValue){
		Text txt = factory.createText(); 
		txt.setValue(text);
		R run = factory.createR();
		run.getContent().add(txt);	
		
		if (null!=styleValue){
			RPr rPr = factory.createRPr(); 
	        RFonts runFont = new RFonts(); 
	        runFont.setEastAsia(styleValue.getFontName());
	        runFont.setAscii(styleValue.getFontName());								    
	        rPr.setRFonts(runFont);
	        
	        HpsMeasure size = new HpsMeasure();  
	        size.setVal( BigInteger.valueOf(styleValue.getFontSize()));
	        rPr.setSz(size);
	        
	        if (styleValue.isBold()){
	    	    BooleanDefaultTrue b = new BooleanDefaultTrue();
	    	    b.setVal(true);
	    	    rPr.setB(b);
	        }
		    
	        if (styleValue.isItalic()){
	    	    BooleanDefaultTrue i = new BooleanDefaultTrue();
	    	    i.setVal(true);
	    	    rPr.setI(i);        	
	        }
	        
	        if (styleValue.isUnderline()){
	    	    U u = new U();
	    	    u.setVal(UnderlineEnumeration.SINGLE);
	    	    rPr.setU(u);       	
	        }        
		    run.setRPr(rPr);			
		}
		
	    return run;
	}	
	
	/**
	 * 将文字追加到已有段落中
	 * @param simpleText 纯文本
	 * @param factory
	 * @param existsPara 已存在段落
	 * @param styleValue 文本的格式
	 * @return 更新后段落
	 */
	public org.docx4j.wml.P addTextToP(String simpleText,org.docx4j.wml.ObjectFactory factory,org.docx4j.wml.P existsPara,StyleValue styleValue){
		org.docx4j.wml.P para = existsPara;
		if (null==factory)
			factory = Context.getWmlObjectFactory();
		if (null==para)
			para = factory.createP();
		para.getContent().add(makeStylePureSimpleText(factory, simpleText, styleValue)); 
		return para;
	}

		
	
	/**
	 * 处理标题(不可含有数学公式),其中可能文字和图片混合
	 * @param pendingText 待处理文本
	 * @param wmp
	 * @param styleId 标题的格式，系统定义好的格式，如Heading1等等
	 * @param factory
	 * @return
	 * @throws Exception
	 */
	private org.docx4j.wml.P makeComplexTitle(String pendingText,WordprocessingMLPackage wmp,String styleId,org.docx4j.wml.ObjectFactory factory)  throws Exception{
		//long start = System.currentTimeMillis();
		Document paradoc;
		Pattern p=Pattern.compile("<img.*?/>");  
		Matcher m=p.matcher(pendingText);
		org.docx4j.wml.P titleP = null;
		int preEndIndex = 0; 
		while(m.find()){  
			String temp = m.group();
			if (preEndIndex<m.start()){
				if (null==titleP)
					titleP = wmp.getMainDocumentPart().addStyledParagraphOfText(styleId,pendingText.substring(preEndIndex, m.start()));
				else
					addTextToP(pendingText.substring(preEndIndex, m.start()),null,titleP,null);
			}
			paradoc = Jsoup.parse(temp);
			Elements imgs = paradoc.getElementsByTag("img");
			Element img = imgs.first();
			String imgPath = img.attr("src");
			titleP = processImg(wmp, imgPath,titleP,factory) ;
			preEndIndex = m.end();
		}
		if (preEndIndex<pendingText.length()){
			if (null==titleP)
				titleP = wmp.getMainDocumentPart().addStyledParagraphOfText(styleId,pendingText.substring(preEndIndex, pendingText.length()));
			else
				addTextToP(pendingText.substring(preEndIndex, pendingText.length()),null,titleP,null);			
		}
		return titleP;		
	}

	/**
	 * 分拆内容为段落，返回一个段落的列表
	 * @param tableCell
	 * @param pendingPart ooxml格式的文本，段落之间以SECTION_SPLIT分隔
	 * @param problemIndex 题目或答案序号，不需增加序号，可设为-1
	 * @throws Exception
	 */
	private List<P> disassemblyParas(StyleTextVo pendingPart, int problemIndex,
			org.docx4j.wml.ObjectFactory factory,List<ParaText> imgFutureList){
		List<P> result = new ArrayList<P>();
		String txt = pendingPart.getTxt();
	    String[] textParagraphy = (problemIndex==-1?txt:problemIndex+".　"+txt).split(SECTION_SPLIT);
		for (String paragraphy:textParagraphy){
			P p = factory.createP();
			imgFutureList.add(new ParaText(p,new StyleTextVo(paragraphy,pendingPart.getStyleValue())));
			result.add(p);
		}
		return result;
	}	
	
	/**
	 * 创建一个表格 
	 * @param tableContent 表格的内容
	 * @param tableBorder 表格的属性设置
	 * @param wordMLPackage
	 * @param factory
	 * @return 一个表格
	 */
	 private Tbl addTableWithMergedCellsEnabled(List<List<StyleTextVo>> tableContent,
	    		TableAttribute tableBorder,
	    		WordprocessingMLPackage wordMLPackage,ObjectFactory factory,List<ParaText> imgFutureList) { 
	    	Tbl table = factory.createTbl();
	    	for(int i=0;i<tableContent.size();i++){//行
	    		Tr tableRow1 = factory.createTr();  
	    		List<StyleTextVo> rawRow = tableContent.get(i);
	    		for (int j=0;j<rawRow.size();j++){//列
	    			List<P> cellContent = disassemblyParas(rawRow.get(j),-1,factory,imgFutureList);
	    			Tc tableCell = null;
	    			/*如果当前是表格的最后一行*/
					if (i==(tableContent.size()-1)){
						/*本单元格内容为null，认为本单元格需要合并到上一行的同列单元格中*/
						if (null==rawRow.get(j)){
							tableCell = AddingATable.addMergedColumn(tableRow1, cellContent,wordMLPackage,factory);	
						}else{
							tableCell = AddingATable.addTableCell(tableRow1, cellContent,wordMLPackage,factory,null);
						}
					}else{
						/*本单元格或下一行同一列内容为null，认为本单元格需要合并到上一行的同列单元格中或者是某个合并单元格的开始*/
		    			if (null==rawRow.get(j)||null==tableContent.get(i+1).get(j)){
		    				tableCell = AddingATable.addMergedColumn(tableRow1, cellContent,wordMLPackage,factory);	
		    			}else{
		    				tableCell = AddingATable.addTableCell(tableRow1, cellContent,wordMLPackage,factory,null);
		    			}					
					}
					if (null!=tableBorder.getColumnWidth()){
						if (null!=tableBorder.getColumnWidth().get(j)){
							AddingATable.setCellWidth(tableCell, tableBorder.getColumnWidth().get(j));
						}
					}

	    		}
	    		table.getContent().add(tableRow1);
	    	}
	    	
			TblPr tblPr = table.getTblPr();
			if (tblPr == null){
				tblPr = factory.createTblPr();
				table.setTblPr(tblPr);
			}
		    TblBorders borders = tblPr.getTblBorders();
		    if (borders == null) {
		      borders = new TblBorders();
		      tblPr.setTblBorders(borders);
		    }
		    if (tableBorder.hasButtom()){
		    	CTBorder border = factory.createCTBorder(); 
		    	border.setVal(STBorder.SINGLE);
		    	borders.setBottom(border);
		    }
		    if (tableBorder.hasTop()){
		    	CTBorder border = factory.createCTBorder(); 
		    	border.setVal(STBorder.SINGLE);
		    	borders.setTop(border);
		    }
		    if (tableBorder.hasLeft()){
		    	CTBorder border = factory.createCTBorder(); 
		    	border.setVal(STBorder.SINGLE);
		    	borders.setLeft(border);
		    }
		    if (tableBorder.hasRight()){
		    	CTBorder border = factory.createCTBorder(); 
		    	border.setVal(STBorder.SINGLE);
		    	borders.setRight(border);
		    }	
		    if (tableBorder.hasInnerVertical()){
		    	CTBorder border = factory.createCTBorder(); 
		    	border.setVal(STBorder.SINGLE);
		    	borders.setInsideV(border);
		    }	
		    if (tableBorder.hasInnerHorizne()){
		    	CTBorder border = factory.createCTBorder(); 
		    	border.setVal(STBorder.SINGLE);
		    	borders.setInsideH(border);
		    }		    
		    
	        return table;
	    }	
	
	/**
	 * 处理本段中包含的图片
	 * @param wordMLPackage
	 * @param imgPath1  图片地址，以http开头，为远程地址否则为本地地址
	 * @param existPara 已存在的段落
	 * @return
	 * @throws Exception
	 */
	public org.docx4j.wml.P processImg(WordprocessingMLPackage wordMLPackage,final String imgPath1,P existPara,org.docx4j.wml.ObjectFactory factory) throws Exception{
		long start = System.currentTimeMillis();
		final String imgPath=imgPath1.replace("\\\"", "");
		String localImgPath = imgPath;
		if (imgPath.startsWith("http")){
			String[] imgTmp = imgPath.split("/");
			localImgPath = OffLineDocBaseConstant.LOCAL_IMAGE_CACHE_FULLPATH+imgTmp[imgTmp.length-1]+".jpg";
			/*本地不存在该文件，在图片云上获取*/
			final String fLocalImgPath = localImgPath;
			if (!new File(localImgPath).exists()){
				FutureTask<Boolean> f = imgDownloadMap.get(imgPath);
				if (f==null){
					 /* 本地不存在该图片，图片获取缓存中也没有没有这个任务，
					 * 将一个任务加入缓存 */
					FutureTask<Boolean> ft = new FutureTask<Boolean>(new Callable<Boolean>()
							{
								@Override
								public Boolean call() throws Exception {
									if (!downloadFile(imgPath,fLocalImgPath)){
										return false ;
									}
									return true;
								}
					});
					f = imgDownloadMap.putIfAbsent(imgPath, ft);
					if (null==f){
						log.info("下载图片["+imgPath+".jpg]");
						f = ft;
						//f.run();	
						executor.submit(f);
					}
				}
				f.get();
				imgDownloadMap.remove(imgPath);				
			}			
		}else /*if (imgPath.startsWith("/"))*/{
			if (imgPath.startsWith(WordPublicConstant.IMAGE_PREFIX)){
				localImgPath = imgPath.replace(WordPublicConstant.IMAGE_PREFIX, 
						System.getProperty("user.home") + "/"); 
			}else
			if (!new File(localImgPath).exists()){ 
				throw new Exception(localImgPath+"图片不存在.");
			}
		}/*else throw new Exception(localImgPath+"图片不存在.")*/;
		
		File file = new File(localImgPath);
		byte[] bytes = AddingAnInlineImage.convertImageToByteArray(file); 
		start = System.currentTimeMillis();
		P p = AddingAnInlineImage.crearteImagePara(wordMLPackage, bytes,existPara,factory,imgPath);
		return p;
		
	}	

	/**
	 * 从云上下载图片到本地，包含重试机制
	 * @param remoteFilePath
	 * @param localFilePath
	 * @return
	 * @throws IOException
	 */
	private boolean downloadFile(String remoteFilePath, String localFilePath) throws IOException{
		int retryCount = 3;
		boolean result = false;	
		String tmpFileName = localFilePath+".tmp";
		for (int i=1;i<=retryCount;i++){
			result = downloadFileOnce(remoteFilePath,tmpFileName);
			if (!result){
				if (i<3) log.warn("下载失败，重试.......");
			}else{
				break;
			}
		}
		if (!result) {
			log.warn("文件或图片:"+remoteFilePath+" 无法下载！");
			}
		else FileUtils.copyFile(new File(tmpFileName), new File(localFilePath));
		return result;
	}
	/**
	 * 从云上下载图片到本地实际方法
	 * @param remoteFilePath
	 * @param localFilePath
	 * @return
	 */
	private boolean  downloadFileOnce(String remoteFilePath, String localFilePath) {
		
		boolean result=false;
		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;		
		File f = new File(localFilePath);
		try {
			urlfile = new URL(remoteFilePath);
			httpUrl = (HttpURLConnection) urlfile.openConnection();
			httpUrl.setConnectTimeout(5000);
			httpUrl.setReadTimeout(5000);	
			httpUrl.connect();
			int remoteFileLength = httpUrl.getContentLength();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(f));
			int len = 2048;
			byte[] b = new byte[len];
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
			bis.close();
			bos.close();
			httpUrl.disconnect();
	        int factFileLength = (new FileInputStream(f)).available();
	        if (remoteFileLength<=factFileLength){	
	        	result = true;
	        	log.info("下载成功"+remoteFilePath+"。远程大小："+remoteFileLength+"。本地大小："+factFileLength+"。");
	        }
	        else{
	        	result = false;
	        	log.warn(remoteFilePath+"远程大小："+remoteFileLength+"。本地大小："+factFileLength+"。下载失败");
	        }
	        
		} catch (Exception e) {
			log.error("!!!下载错误:",e);
			result =  false;
		} finally {
			try {
				if (null!=bis ) bis.close();
				if (null!=bos )bos.close();
			} catch (IOException e) {
				log.error("!!!关闭下载通道错误:",e);
			}
		}
		return result;
	}

	@Override
	public boolean makeWordStandardAll(String title, String header, String footer,
			String preface,
			List<String> xuanzheProblemStem,
			Map<Integer, XuanzheOptionVo> xuanzheProblemOptions,
			Map<Integer, XuanzheOptionVo> xuanzheAnswer, 
			List<String> tianKongProblemStem,List<String> tianKongAnswer, 
			List<String> jieDaProblemStem,List<String> jieDaAnswer,
			String postscript,
			String fileName) {
		long startTime = System.currentTimeMillis();
		boolean result = false;
/*		try {
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
			log.info("创建WordprocessingMLPackage，耗时"+(System.currentTimeMillis()-startTime)+"毫秒");
			
			long everyTime = System.currentTimeMillis();
			wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", title);
			ObjectFactory factory = Context.getWmlObjectFactory();
	        Relationship relationship = AddingAFooter.createFooterPart(wordMLPackage,factory,footer);  
	        AddingAFooter.createFooterReference(relationship,wordMLPackage,factory); 		
	        Relationship relationshipHeader = AddingAHeader.createHeaderPart(wordMLPackage,factory,header); 
	        AddingAHeader.createHeaderReference(relationshipHeader,wordMLPackage,factory);
	        if (null!=preface && !"".equals(preface)) {
	        	wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle","给你的寄语");
	        	makeNormalPart(wordMLPackage,preface,-1,factory);
	        }
	        log.info("创建标题、页眉、页脚、前言，耗时"+(System.currentTimeMillis()-everyTime)+"毫秒");
	        int subTitleNo = 0;
	        int problemNo = 0;
	        everyTime = System.currentTimeMillis();
	        if (null!=xuanzheProblemStem && xuanzheProblemStem.size()>0) {
	        	wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle",SUB_TITLES[subTitleNo]+"选择题（共"+xuanzheProblemStem.size()+"小题）");
	        	subTitleNo++;
		        for(int i=0;i<xuanzheProblemStem.size()-1;i++){
		        	problemNo++;
		        	makeNormalPart(wordMLPackage,xuanzheProblemStem.get(i),problemNo,factory);
		        	makeOptionsPart(wordMLPackage,xuanzheProblemOptions.get(i).getOptionContent(),xuanzheProblemOptions.get(i).getOptionFlag(),factory);
		        }
		        log.info("创建选择题"+xuanzheProblemStem.size()+"个，耗时"+(System.currentTimeMillis()-everyTime)+"毫秒"); 	        	
	        }

	        everyTime = System.currentTimeMillis();
	        if (null!=tianKongProblemStem && tianKongProblemStem.size()>0) {
	        	wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle",SUB_TITLES[subTitleNo]+"填空题（共"+tianKongProblemStem.size()+"小题）");
	        	subTitleNo++;
		        for(int i=0;i<tianKongProblemStem.size()-1;i++){
		        	problemNo++;
		        	makeNormalPart(wordMLPackage,tianKongProblemStem.get(i),problemNo,factory);
		        }
		        log.info("创建填空题"+tianKongProblemStem.size()+"个，耗时"+(System.currentTimeMillis()-everyTime)+"毫秒"); 	        	
	        }
	        
	        everyTime = System.currentTimeMillis();
	        if (null!=jieDaProblemStem && jieDaProblemStem.size()>0) {
	        	wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle",SUB_TITLES[subTitleNo]+"解答题（共"+jieDaProblemStem.size()+"小题）");
	        	subTitleNo++;
		        for(int i=0;i<jieDaProblemStem.size()-1;i++){
		        	problemNo++;
		        	makeNormalPart(wordMLPackage,jieDaProblemStem.get(i),problemNo,factory);
		        }
		        log.info("创建解答题"+jieDaProblemStem.size()+"个，耗时"+(System.currentTimeMillis()-everyTime)+"毫秒"); 	        	
	        }
	        
	        everyTime = System.currentTimeMillis();
	        wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle","答                   案");
	        problemNo = 0;
	        if (null!=xuanzheProblemStem && xuanzheProblemStem.size()>0) {
		        for(int i=0;i<xuanzheProblemStem.size()-1;i++){
		        	problemNo++;
		        	makeOptionsPart(wordMLPackage,xuanzheAnswer.get(i).getOptionContent(),xuanzheAnswer.get(i).getOptionFlag(),factory);
		        }
	        }

	        everyTime = System.currentTimeMillis();
	        if (null!=tianKongProblemStem && tianKongProblemStem.size()>0) {
		        for(int i=0;i<tianKongProblemStem.size()-1;i++){
		        	problemNo++;
		        	makeNormalPart(wordMLPackage,tianKongAnswer.get(i),problemNo,factory);
		        }
	        }
	        
	        everyTime = System.currentTimeMillis();
	        if (null!=jieDaProblemStem && jieDaProblemStem.size()>0) {
		        for(int i=0;i<jieDaProblemStem.size()-1;i++){
		        	problemNo++;
		        	makeNormalPart(wordMLPackage,jieDaAnswer.get(i),problemNo,factory);
		        }
	        }	        
	        log.info("创建答案，耗时"+(System.currentTimeMillis()-everyTime)+"毫秒");

	        if (null!=postscript && !"".equals(postscript)) {
	        	wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle","总结");
	        	makeNormalPart(wordMLPackage,postscript,-1,factory);
	        }
	        
	        wordMLPackage.save(new java.io.File(fileName));
	        log.info("文件创建完成，总耗时"+(System.currentTimeMillis()-startTime)+"毫秒");
	        result = true;
		} catch (InvalidFormatException e) {
			log.error("生成word文件错误",e);
			result = false;
		} catch (Exception e) {
			log.error("生成word文件错误",e);
			result = false;
		}*/
		return result;
	}

	private static List<String> makeOptionOrderList(){
		return new ArrayList<String>(Arrays.asList("A","B","C","D","E","F","G","H","I","J"));
	}
	
	private void makeContentZone(WordprocessingMLPackage wmp,List<StandardSegment<?>> contents,int level,
			//ObjectFactory factory,List<Future<P>> imgFutureList) throws Exception{
			ObjectFactory factory,List<ParaText> imgFutureList) throws Exception{
		//long start = System.currentTimeMillis();
		for (StandardSegment content:contents){
			int innerLevel = level;
			if (innerLevel>4) {
				/*文档标题样式控制在4层以内*/
				innerLevel = 4;
			}
			
			/*处理本节点的标题*/
			if (null!=content.getTitle()&&!"".equals(content.getTitle())) {
				if (WordPublicConstant.BREAK_PAGE.equals(content.getTitle())){
					addPageBreak(wmp,factory);
				}
				else{
					makeComplexTitle(content.getTitle(),wmp,"Heading"+innerLevel,factory);
				}
				
			}
			
			/*处理本节点的内容*/
			if (null==content.getContents()||content.getContents().size()==0){
				continue;
			}
			if (content.isLeaf()&&!content.isOptions()){
				if (null!=content.getContents()&&content.getContents().size()>0){
					List<StyleTextVo> stringContent = content.getContents();
		        	for(StyleTextVo s:stringContent){
		        		disassemblyForParas(wmp,s,-1,factory,imgFutureList);
		        	}					
				}
			}else if (content.isTable()){
				wmp.getMainDocumentPart().addObject(
						addTableWithMergedCellsEnabled(content.getContents(), 
								content.getTableAttribute(), wmp, factory,imgFutureList));
			}
			else{
				if (content.isOptions()){
					makeOptionsPart(wmp,content.getContents(),makeOptionOrderList(),factory,imgFutureList);
				}
				else
					makeContentZone(wmp,content.getContents(),innerLevel>4?4:++innerLevel,factory,imgFutureList);
			}
		}
	}

	/**
	 * 添加换页符
	 * @param wmp
	 * @param factory
	 */
	private static void addPageBreak(WordprocessingMLPackage wmp,ObjectFactory factory) {  
        Br breakObj = new Br();  
        breakObj.setType(STBrType.PAGE);  
   
        P paragraph = factory.createP();  
        paragraph.getContent().add(breakObj);  
        wmp.getMainDocumentPart().getJaxbElement().getBody().getContent().add(paragraph);
	}
	
	@Override
	public WordprocessingMLPackage makeStandardOneBook(String title,String header, String footer,
			String fileName, StandardOneBook oneBook,WordprocessingMLPackage wmp) throws Exception {
		
		/*CompletionService<P> completionSerivce = new ExecutorCompletionService<P>(futureProcessWordImg.getExecutor());*/
		List<Future<P>> paraFutureList = new ArrayList<Future<P>>();
		List<ParaText> paraList = new ArrayList<ParaText>();
		
		WordprocessingMLPackage wordMLPackage = wmp;
		if (null==wordMLPackage){
			wordMLPackage = WordprocessingMLPackage.createPackage();
			StyleDefinitionsPart styleDefinitionsPart =  
		            wordMLPackage.getMainDocumentPart().getStyleDefinitionsPart();  
		        Styles styles = styleDefinitionsPart.getJaxbElement();  
		   
		        List<Style>  stylesList = styles.getStyle();  
		        for (Style style : stylesList) {  
		            if (style.getStyleId().startsWith("Heading")|| 
		                    style.getStyleId().equals("Title") ||  
		                    style.getStyleId().equals("Subtitle")) 
		            {  
		            	RPr rpr = style.getRPr();  
		            	rpr.getRFonts().setAsciiTheme(null);  
		            	rpr.getRFonts().setHAnsiTheme(null);
		    	        RFonts runFont = new RFonts(); 
		    	        runFont.setEastAsia("微软雅黑");
		    	        runFont.setAscii("微软雅黑");								    
		    	        rpr.setRFonts(runFont);	
		  		        Color c = new Color();
				        c.setVal("black");
				        rpr.setColor(c);	
			    	    BooleanDefaultTrue b = new BooleanDefaultTrue();
			    	    b.setVal(false);
			    	    rpr.setB(b);				        
		            }  
		        } 			
		}
		if (null!=title&&!"".equals(title))
			wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title",title);
		
		ObjectFactory factory = Context.getWmlObjectFactory();
		
		if (null!=footer&&!"".equals(footer)){
	        Relationship relationship = AddingAFooter.createFooterPart(wordMLPackage,factory,footer);  
	        AddingAFooter.createFooterReference(relationship,wordMLPackage,factory);				
		}

        if (null!=header&&!"".equals(header)){
	        Relationship relationshipHeader = AddingAHeader.createHeaderPart(wordMLPackage,factory,header); 
	        AddingAHeader.createHeaderReference(relationshipHeader,wordMLPackage,factory);		        	
        }
	
		if (null!=oneBook.getCoverList()&&oneBook.getCoverList().size()>0){
			Tbl table = factory.createTbl();
			for (String row:oneBook.getCoverList()){
				Tr tableRow = factory.createTr();
				//org.docx4j.wml.P rowP = factory.createP();
				//paraList.add(new ParaText(rowP,new StyleTextVo(row)));
				//paraFutureList.add(processFuturePara(wordMLPackage,row,factory,rowP));
				//org.docx4j.wml.P rowP = makeSinglePara(wordMLPackage,row,factory);
				AddingATable.addTableCell(tableRow,disassemblyParas(new StyleTextVo(row,new StyleValue(24)), -1,
						factory,paraList),wordMLPackage,factory,JcEnumeration.CENTER);
				table.getContent().add(tableRow);
			}
			wordMLPackage.getMainDocumentPart().addObject(table);
			if (
				null!=oneBook.getPreface()||
				null!=oneBook.getProblemZone()||
				null!=oneBook.getAnswerZone()||
				null!=oneBook.getPostscript()
				){
				addPageBreak(wordMLPackage,factory);
			}
				
		}
		
		if (null!=oneBook.getPreface()){
			
			StandardSegment<StandardSegment<?>> preface = oneBook.getPreface();
			int level = 1;
			if (null!=preface.getTitle()&&!"".equals(preface.getTitle())) {
				wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle",preface.getTitle());
			}
/*        	for(StyleTextVo s:preface.getContents()){
        		disassemblyForParas(wordMLPackage,s,-1,factory,paraList);
        		//makeNormalPart(wordMLPackage,s,-1,factory,paraFutureList);
        	}*/
			makeContentZone(wordMLPackage,preface.getContents(),level,factory,paraList);
			if (
					null!=oneBook.getProblemZone()||
					null!=oneBook.getAnswerZone()||
					null!=oneBook.getPostscript()
					){
					addPageBreak(wordMLPackage,factory);
				}
		}
		
		if (null!=oneBook.getProblemZone()){
			
			StandardSegment<StandardSegment<?>> problemZone = oneBook.getProblemZone();
			int level = 1;
			if (null!=problemZone.getTitle()&&!"".equals(problemZone.getTitle())) {
				makeComplexTitle(problemZone.getTitle(),wordMLPackage,"Heading"+level,factory);
				level ++;
			}
			makeContentZone(wordMLPackage,problemZone.getContents(),level,factory,paraList);
			//makeContentZone(wordMLPackage,problemZone.getContents(),level,factory,paraFutureList);
			if (
					null!=oneBook.getAnswerZone()||
					null!=oneBook.getPostscript()
					){
					addPageBreak(wordMLPackage,factory);
				}			
		}
		
		if (null!=oneBook.getAnswerZone()){
			addPageBreak(wordMLPackage,factory);
			StandardSegment<StandardSegment<?>> answerZone = oneBook.getAnswerZone();
			int level = 1;
			if (null!=answerZone.getTitle()&&!"".equals(answerZone.getTitle())) {
				makeComplexTitle(answerZone.getTitle(),wordMLPackage,"Heading"+level,factory);
				level ++;
			}
			makeContentZone(wordMLPackage,answerZone.getContents(),level,factory,paraList);
			//makeContentZone(wordMLPackage,answerZone.getContents(),level,factory,paraFutureList);	
			if (
					null!=oneBook.getPostscript()
					){
					addPageBreak(wordMLPackage,factory);
				}
		}
		if (null!=oneBook.getPostscript()){
			
			StandardSegment<StyleTextVo> postscript = oneBook.getPostscript();
			if (null!=postscript.getTitle()&&!"".equals(postscript.getTitle())) {
				wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle",postscript.getTitle());
			}
        	for(StyleTextVo s:postscript.getContents()){
        		//makeNormalPart(wordMLPackage,s,-1,factory,paraFutureList);
        		disassemblyForParas(wordMLPackage,s,-1,factory,paraList);
        	}
        	//addPageBreak(wordMLPackage,factory);
		}			
		
		log.info("将待处理段落"+paraList.size()+"个写入Future列表......");
		for(int i=0;i<paraList.size();i++){
			paraFutureList.add(processFuturePara(wordMLPackage,paraList.get(i).getText(),factory,paraList.get(i).getP()));
		}
		log.info("段落实际内容处理中......");
		for(int i=0;i<paraFutureList.size();i++){
			paraFutureList.get(i).get();
		}		
		log.info("段落实际内容处理完成。准备后续处理......");
		if (null!=fileName&&!"".equals(fileName)) {
			log.info("等待写入文件"+fileName+"......");
			if (null!=wordMLPackage){
				wordMLPackage.save(new java.io.File(fileName));
/*				DocPropertyResolver docPropertyResolver = new DocPropertyResolver(wordMLPackage);
				String pages = (String) docPropertyResolver.getValue("Pages");*/
				log.info("word文件写入本地："+fileName);
			}
		}
		
		log.info("线程池大小："+((ThreadPoolExecutor)executor).getLargestPoolSize());
		return wordMLPackage;
	}

	@Override
	public boolean makeStandardBooks(List<String> title,String header, String footer,
			String fileName, List<StandardOneBook> books) {
		try {
			int i =0;
			WordprocessingMLPackage wmp = null;
			ObjectFactory factory = Context.getWmlObjectFactory();
			for(StandardOneBook book:books ){
				if (i==0){
					wmp = makeStandardOneBook(title==null?null:title.get(i),header,footer,"",book,null);
				}else{
					wmp = makeStandardOneBook(title==null?null:title.get(i),"","","",book,wmp);
				}
				if (i==books.size()-1){} else {
					if (null!=wmp)
						addPageBreak(wmp,factory);
				}
				i++;
			}
			if (null!=wmp){
/*				DocPropertyResolver docPropertyResolver = new DocPropertyResolver(wmp);
				String pages = (String) docPropertyResolver.getValue("Pages");*/
				wmp.save(new java.io.File(fileName));
				log.info("word文件写入本地："+fileName);
			}
			return true;
		} catch (Docx4JException e) {
			log.error("生成word文件错误",e);
		} catch (Exception e) {
			log.error("生成word文件错误",e);
		}
		return false;
	}


	
}
