package com.yisi.stiku.intg.web.action;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yisi.stiku.common.utils.DateUtil;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.web.util.OperationResult;
import com.yisi.stiku.web.util.WebUtils;

/**
 * @author shangfeng
 *
 */
@Controller
@RequestMapping("/file")
public class FileUploadController {

	private final static Logger LOG = LoggerFactory.getLogger(FileUploadController.class);

	@RequestMapping("uploadAjax")
	public void uploadAjax(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {

		String fileName = file.getOriginalFilename();
		// String fileName = new Date().getTime()+".jpg";
		Date currDate = new Date();
		String relativeDir = DateUtil.formatDate(currDate, "yyyy/dd");

		File targetFile = new File(ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "upload.base.dir") + "/" + relativeDir,
				fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
			WebUtils.writeJson(OperationResult.buildSuccessResult("上传成功", relativeDir + "/" + fileName), request, response);
			return;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		WebUtils.writeJson(OperationResult.buildFailureResult("上传失败", fileName), request, response);
	}

}
