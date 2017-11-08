package com.youjiniot.utils;

import com.youjiniot.domain.Manager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {
	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}
	public static Object getUser() {
		return  getSubjct().getPrincipal();
	}
	//public static int getUserId() {
	//	return getUser().getId();
	//}
	public static void logout() {
		getSubjct().logout();
	}
}
