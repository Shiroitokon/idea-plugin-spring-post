package com.geeboo.shiroito.utils;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;


public class SignUtil {


	public static String sign(Map<String, String> params,  String secretKey) {
		if (params == null) {
			return "";
		}

		// 第一步：参数排序
		Set<String> keySet = params.keySet();
		List<String> paramNames = new ArrayList<>(keySet);
		Collections.sort(paramNames);

		// 第二步：把所有参数名和参数值串在一起
		StringBuilder paramNameValue = new StringBuilder();
		for (String paramName : paramNames) {
			if (paramName.equals("sign")) {
				continue;
			}
			if (params.get(paramName) != null) {
				String paramValue = String.valueOf(params.get(paramName));

				paramNameValue.append(paramName).append("=").append(paramValue)
							.append("&");
			}
		}
		paramNameValue.append("secret_key=").append(secretKey);

		// 第三步：使用MD5
		String source = paramNameValue.toString();
		return DigestUtils.md5Hex(source).toUpperCase();
	}


}
