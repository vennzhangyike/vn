package com.hfepay.scancode.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public enum Validator {

	EAMIL(
			"^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"), MOBILE(
			"^((13[0-9])|(15[012356789])|(18[0-9])|(14[57]))[0-9]{8}$");

	Validator(String regex) {
		this.regex = regex;
	}

	String regex;

	public boolean validate(String email) {
		if (StringUtils.isEmpty(email))
			return false;
		Pattern regexPattern = Pattern.compile(regex);
		Matcher matcher = regexPattern.matcher(email);
		return matcher.matches();

	}

}
