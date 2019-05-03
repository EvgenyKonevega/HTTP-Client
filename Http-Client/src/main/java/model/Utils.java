package model;

import java.util.regex.Pattern;

public class Utils {
	private static Pattern hostPattern = Pattern.compile("(((.+\\.)?(.+)\\.[a-z]{2,4})|localhost)(:\\d+)?");
	private static Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}(:\\d+)?");

	public static String findPort(String text) {
		String result = "8080";
		int st = text.indexOf(":");
		int end = text.indexOf("/");
		if (text.startsWith("1") && text.contains(":")) {
			if ((end - st > 1) && (st > -1)) {
				result = text.substring(st + 1, end);
			} 
		}else if(text.startsWith("1") && !(text.contains(":"))) {
			result = "8080";
		}

		if (text.contains("http")) {
			if ((end - st > 1) && (st > -1)) {
				result = text.substring(st + 1, end);
			} else {
				result = "80";
			}
		}
		System.out.println("port " + result);
		return result;
	}
}
