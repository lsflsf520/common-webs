package com.yisi.stiku.BaseMake.utils;

import java.math.BigInteger;
import java.util.List;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.Tr;
import org.docx4j.wml.TcPrInner.VMerge;

public class AddingATable {
   
    public static void main (String[] args) throws Docx4JException {  
    	WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
	    ObjectFactory factory = Context.getWmlObjectFactory();
	   
	    Tbl table = factory.createTbl();  
	    Tr tableRow = factory.createTr();  
	   
	    addTableCell(tableRow, "Field 1",wordMLPackage,factory);  
	    addTableCell(tableRow, "Field 2",wordMLPackage,factory);  
	   
	    table.getContent().add(tableRow);  
	   
	    wordMLPackage.getMainDocumentPart().addObject(table);  
	   
	    wordMLPackage.save(new java.io.File("src/main/files/HelloWord4.docx"));  
    }  
   
    public static Tc addTableCell(Tr tableRow, String content,WordprocessingMLPackage wordMLPackage,ObjectFactory factory) {  
	    Tc tableCell = factory.createTc();  
	    tableCell.getContent().add(wordMLPackage.getMainDocumentPart().createParagraphOfText(content));  
	    tableRow.getContent().add(tableCell); 
	    return tableCell;
    }
    
    public static Tc addTableCell(Tr tableRow, P content,WordprocessingMLPackage wordMLPackage,ObjectFactory factory,JcEnumeration align) {  
	    Tc tableCell = factory.createTc();
	    if (null==align) align = JcEnumeration.LEFT;
	    DocxTool.setParaJcAlign(content, align);
	    tableCell.getContent().add(content);  
	    tableRow.getContent().add(tableCell); 
	    return tableCell;
    }    
    
    public static Tc addTableCell(Tr tableRow, List<P> content,WordprocessingMLPackage wordMLPackage,ObjectFactory factory,JcEnumeration align) {  
	    Tc tableCell = factory.createTc();
	    tableRow.getContent().add(tableCell);
	    for(P p:content){
		    if (null==align) align = JcEnumeration.LEFT;
		    DocxTool.setParaJcAlign(p, align);
		    tableCell.getContent().add(p);     	
	    }
	    return tableCell;
    }     
    
    public static void tableCellAddP(Tc tableCell, P content) {  
	    tableCell.getContent().add(content);  
    }   
 
    public static void setCellWidth(Tc tableCell, int width) {  
        TcPr tableCellProperties = new TcPr();  
        TblWidth tableWidth = new TblWidth();  
        tableWidth.setW(BigInteger.valueOf(width));  
        tableCellProperties.setTcW(tableWidth);  
        tableCell.setTcPr(tableCellProperties);  
    } 
    
    public static void setParagraphyCellAlign(P content) {  
    	PPr t = new PPr();
    	content.setPPr(t);
    	Jc align = new Jc();
    	align.setVal(JcEnumeration.LEFT);
    	t.setJc(align);
    }    

    /** 
     *  我们创建一个单元格和单元格属性对象. 
     *  也创建了一个纵向合并对象. 如果合并值不为null, 将它设置到合并对象中. 然后将该对象添加到 
     *  单元格属性并将属性添加到单元格中. 最后设置单元格内容并将单元格添加到行中. 
     *   
     *  如果合并值为'restart', 表明要开始一个新行. 如果为null, 继续按前面的行处理, 也就是合并单元格. 
     */  
    private static Tc addMergedCell(Tr row, String content, String vMergeVal,WordprocessingMLPackage wordMLPackage,ObjectFactory factory) {  
        Tc tableCell = factory.createTc();  
        TcPr tableCellProperties = new TcPr();  
   
        VMerge merge = new VMerge();  
        if(vMergeVal != null){  
            merge.setVal(vMergeVal);  
        }  
        tableCellProperties.setVMerge(merge);  
   
        tableCell.setTcPr(tableCellProperties);  
        if(content != null) {  
                tableCell.getContent().add(  
                wordMLPackage.getMainDocumentPart().  
                    createParagraphOfText(content));  
        }  
   
        row.getContent().add(tableCell); 
        return tableCell;
    } 

    /**
     * 重载 addMergedCell，允许传入的内容为一个段落P的列表
     */  
    private static Tc addMergedCell(Tr row, List<P> content, String vMergeVal,WordprocessingMLPackage wordMLPackage,ObjectFactory factory) {  
        Tc tableCell = factory.createTc();  
        TcPr tableCellProperties = new TcPr();  
   
        VMerge merge = new VMerge();  
        if(vMergeVal != null){  
            merge.setVal(vMergeVal);  
        }  
        tableCellProperties.setVMerge(merge);  
   
        tableCell.setTcPr(tableCellProperties);  
        if(content != null) {  
           for(P p:content){
        	   tableCell.getContent().add(p); 
           }
        }  
        row.getContent().add(tableCell);  
        return tableCell;
    } 
    
    /** 
     *  本方法添加一个合并了其它行单元格的列单元格. 如果传进来的内容是null, 传空字符串和一个为null的合并值. 
     */  
    public static Tc addMergedColumn(Tr row, List<P> content,WordprocessingMLPackage wordMLPackage,ObjectFactory factory) {  
        if (content == null) {  
            return addMergedCell(row, "", null,wordMLPackage,factory);  
        } else {  
        	return addMergedCell(row, content, "restart",wordMLPackage,factory);  
        }  
    } 
    
   
    
}
