package com.yisi.stiku.LatexPdf.service;


import com.yisi.stiku.LatexPdf.Vo.CommonCallbackVo;
import com.yisi.stiku.LatexPdf.Vo.DocContentVo;
import com.yisi.stiku.LatexPdf.Vo.DocAttrVo;

public interface ICommonLatexPdfRpcService {
	/**
	 * 生成自定义pdf文件，内容中可包含Latex化的题目，自定义格式时题目id需要用LatexPublicConstants.Split_Symbol进行包围
	 * @param docContentVo 文档的标题，为空则不生成
	 * @param docAttrVo 线下作业固有属性，在rpc服务端成功处理后会原样回传给调用端
	 * @param callback 进行结果通知回调函数
	 * @return rpc服务端是否已成功接收任务，成功true，失败false
	 */
	public String makeCommonLatexPdf(DocContentVo docContentVo,
			DocAttrVo docAttrVo,CommonCallbackVo callback);
}
