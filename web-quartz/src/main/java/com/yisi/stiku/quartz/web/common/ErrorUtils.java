package com.yisi.stiku.quartz.web.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ErrorUtils {
	public static List<WebMessage> buildErrorMessage(BindingResult result) {

		if (result == null) {
			return null;
		}
		List<ObjectError> objectErrors = result.getAllErrors();
		if (objectErrors == null) {
			return null;
		}
		List<WebMessage> messages = new ArrayList<WebMessage>(
				objectErrors.size());
		for (ObjectError error : objectErrors) {
			messages.add(new WebMessage(error.getCodes(), error
					.getDefaultMessage()));

		}
		return messages;

	}
}
