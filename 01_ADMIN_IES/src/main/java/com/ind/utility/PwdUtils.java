package com.ind.utility;

import org.apache.commons.lang3.RandomStringUtils;

import com.ind.constants.AppConstants;

public class PwdUtils {

	public static String generateRandomPwd() {

		String characters = AppConstants.ALPHA_NUMERIC;

		String pwd = RandomStringUtils.random(8, characters);

		return pwd;

	}

}