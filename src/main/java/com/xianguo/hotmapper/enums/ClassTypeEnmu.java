package com.xianguo.hotmapper.enums;

import java.util.Date;
import java.util.List;
import java.util.Map;

public enum ClassTypeEnmu {
	String_CLASS_NAME(String.class),
	Integer_CLASS_NAME(Integer.class),
	Date_CLASS_NAME(Date.class),
	List_CLASS_NAME(List.class),
	Double_CLASS_NAME(Double.class),
	int_CLASS_NAME(int.class),
	double_CLASS_NAME(double.class),
	Map_CLASS_NAME(Map.class),
	Float_CLASS_NAME(Float.class),
	float_CLASS_NAME(float.class),
	Character_CLASS_NAME(Character.class),
	char_CLASS_NAME(char.class);
	
	
	private final Class<?> classes;
 
	public Class<?> getClasses() {
		return classes;
	}
 
	ClassTypeEnmu(Class<?> classes) {
		this.classes = classes;
	}
 
	/**
	 * 根据key获取value
	 * 
	 * @param key
	 *            : 键值key
	 * @return String
	 */
	public static ClassTypeEnmu getEnmuByClass(Class<?> key) {
		ClassTypeEnmu[] enums = ClassTypeEnmu.values();
		for (int i = 0; i < enums.length; i++) {
			if (enums[i].getClasses().equals(key)) {
				return enums[i];
			}
		}
		return null;
	}
}
