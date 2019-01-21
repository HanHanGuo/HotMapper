package com.xianguo.hotmapper.util;

import com.xianguo.hotmapper.enums.AnalysusTypeEnmu;

public class FieldNameUtil {

	/**
	 * 首字母大写驼峰
	 * 
	 * @author:鲜果
	 * @date:2019年1月16日 void
	 */
	public static String INITIALS_UP_HUMP(String name) {
		char[] names = name.toCharArray();
		names[0] = (char) (names[0] - 32);
		return new String(names);
	}
	
	/**
	 * 下划线后一位大写
	 * @author:鲜果
	 * @date:2019年1月17日
	 * void
	 */
	public static String UNDERLINE_UP(String name) {
		char[] temp = name.toCharArray();
		for(int i = 0;i<temp.length;i++) {
			if(i == 0 && i == temp.length - 1) {
				continue;
			}
			if(temp[i] == 95 && temp[i+1] >= 97 && temp[i+1] <= 122) {
				temp[i+1] -= 32;
			}
		}
		return new String(temp);
	}
	
	/**
	 * 下划线
	 * 
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @return String
	 */
	public static String UNDERLINE(String name) {
		char[] temp = name.toCharArray();
		int upNum = 0;
		for (int i = 0; i < temp.length; i++) {
			if (i == 0) {
				continue;
			}
			if (temp[i] >= 65 && temp[i] <= 90 && i < temp.length - 1) {
				upNum++;
			}
		}
		char[] names = new char[temp.length + upNum];
		for (int i = 0, index = 0; i < names.length; i++, index++) {
			if (i == 0) {
				names[i] = temp[index];
				continue;
			}
			if (temp[index] >= 65 && temp[index] <= 90 && i < names.length - 1) {
				names[i] = 95;
				i++;
				names[i] = (char) (temp[index] + 32);
				continue;
			}
			names[i] = temp[index];
		}
		return new String(names);
	}

	/**
	 * 首字大写下划线
	 * 
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param name
	 * @return String
	 */
	public static String INITIALS_UP_UNDERLINE(String name) {
		return UNDERLINE(INITIALS_UP_HUMP(name));
	}

	/**
	 * 驼峰
	 * 
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param name
	 * @return String
	 */
	public static String HUMP(String name) {
		return name;
	}

	/**
	 * 全大写下划线
	 * 
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param name
	 * @return String
	 */
	public static String UP_UNDERLINE(String name) {
		return UNDERLINE(name).toUpperCase();
	}
	
	/**
	 * 首字母大写单词大写下划线
	 * @author:鲜果
	 * @date:2019年1月17日
	 * @param name
	 * @return
	 * String
	 */
	public static String INITIALS_UP_WORD_UP_UNDERLINE(String name) {
		return UNDERLINE_UP(UNDERLINE(INITIALS_UP_HUMP(name)));
	}
	

	/**
	 * 单词大写下划线
	 * @author:鲜果
	 * @date:2019年1月17日
	 * @param name
	 * @return
	 * String
	 */
	private static String WORD_UP_UNDERLINE(String name) {
		return UNDERLINE_UP(UNDERLINE(name));
	}

	/**
	 * 根据风格转换字段名称
	 * 
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param style
	 * @param name
	 * @return String
	 */
	public static String nameToDataBaseName(AnalysusTypeEnmu style, String name) {
		switch (style) {
		case HUMP:
			return HUMP(name);
		case INITIALS_UP_HUMP:
			return INITIALS_UP_HUMP(name);
		case INITIALS_UP_UNDERLINE:
			return INITIALS_UP_UNDERLINE(name);
		case NONE:
			return name;
		case UNDERLINE:
			return UNDERLINE(name);
		case UP_UNDERLINE:
			return UP_UNDERLINE(name);
		case INITIALS_UP_WORD_UP_UNDERLINE:
			return INITIALS_UP_WORD_UP_UNDERLINE(name);
		case WORD_UP_UNDERLINE:
			return WORD_UP_UNDERLINE(name);
		}
		return name;
	}
}
