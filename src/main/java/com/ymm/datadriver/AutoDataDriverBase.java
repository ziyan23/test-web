package com.ymm.datadriver;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.DataProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 数据驱动读取csv文件的数据
 * @author jared.gu
 */
public class AutoDataDriverBase {

	@DataProvider(name = "CsvDataProvider")
	public static Iterator<?> getCsvDataProvider(Method method) {
		return getDataProvider(method, "csv");
	}

	public static Iterator<?> getDataProvider(Method method, String fileExtension) {
		String filePath = StringUtils.EMPTY;
		String fileName;
//		String clsName = method.getDeclaringClass().getSimpleName();
		String packageName = method.getDeclaringClass().getName();

		String[] packagePath = packageName.split("\\.");

		filePath += packagePath[packagePath.length-1];
//		for (int i = 3; i < packagePath.length; i++) {
//			filePath += packagePath[i] + "/";
//		}
//
//		if (clsName.contains("NormalTest")) {
//			filePath = filePath + clsName.split("NormalTest")[0];
//		} else if (clsName.contains("funcExceptionTest")) {
//			filePath = filePath + "funcExp/" + clsName.split("funcExceptionTest")[0];
//		} else {
//			filePath = filePath + clsName + "/";
//		}
		fileName = method.getName() + "." + fileExtension;

		filePath = filePath + "/" + fileName;

		try {
			List<Object> args = new ArrayList<Object>();
			args.add(method.getDeclaringClass());
			args.add(method);
			args.add(filePath);
			Class<?> cls = Class.forName("com.datadriver.Auto" + StringUtils.capitalize(fileExtension) + "DataProvider");
			return (Iterator<?>) cls.getConstructors()[0].newInstance(args.toArray());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}
}