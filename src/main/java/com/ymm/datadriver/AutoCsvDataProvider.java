package com.ymm.datadriver;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;

/**
 * @author jared.gu
 */
public class AutoCsvDataProvider implements Iterator<Object[]> {
	CSVReader reader = null;
	private Class<?>[] parameterTypes;
	private Converter[] parameterConverters;
	private String[] last;
	public int sum = 0;
	private static final String ENCODING = "UTF-8";

	private boolean needDatePrepare = false;
	private String datePrepareClassString = StringUtils.EMPTY;
	private String datePrepareMethodString = StringUtils.EMPTY;

	public AutoCsvDataProvider(Class<?> cls, Method method, String csvFilePath) {
		InputStream is = cls.getClassLoader().getResourceAsStream(csvFilePath);
		try {
			InputStreamReader isr = new InputStreamReader(is, ENCODING);
			reader = new CSVReader(isr, ',', '\"', 1);
			parameterTypes = method.getParameterTypes();
			int len = parameterTypes.length;
			parameterConverters = new Converter[len];
			for (int i = 0; i < len; i++) {
				parameterConverters[i] = ConvertUtils.lookup(parameterTypes[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean hasNext() {
		try {
			last = reader.readNext();
		} catch (Exception e) {
			try {
				reader.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return last != null;
	}

	public Object[] next() {
		String[] next;
		if (last != null) {
			next = last;
		} else {
			next = getNextLine();
		}
		last = null;
		String[] intermediate;
		if (needDatePrepare) {
			intermediate = prepareData(next);
		} else {
			intermediate = next;
		}

		Object[] args = parseLine(intermediate);

		String params = StringUtils.EMPTY;
		for (Object o : args) {
			params = params + o.toString() + ", ";
		}

		return args;
	}

	private Object[] parseLine(String[] svals) {
		int len = svals.length;
		Object[] result = new Object[len];
		++sum;
		for (int i = 0; i < len; i++) {
			result[i] = parameterConverters[i].convert(parameterTypes[i], svals[i]);
		}
		return result;
	}

	private String[] getNextLine() {
		if (last == null) {
			try {
				last = reader.readNext();
			} catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
		return last;
	}

	public void remove() {
	}

	private String[] prepareData(String[] input) {
		String[] output = input;
		try {
			Class<?> prepareClass = Class.forName(datePrepareClassString);
			Method prepareMethod = prepareClass.getMethod(datePrepareMethodString, String[].class);
			if (Modifier.isStatic(prepareMethod.getModifiers())) {
				output = (String[]) prepareMethod.invoke(null, (Object) input);
			} else {
				output = (String[]) prepareMethod.invoke(prepareClass.newInstance(), (Object) input);
			}
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException");
			e.printStackTrace();
		} catch (SecurityException e) {
			System.err.println("SecurityException");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.err.println("NoSuchMethodException");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.err.println("IllegalArgumentException");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.err.println("IllegalAccessException");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			System.err.println("InvocationTargetException");
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.err.println("InstantiationException");
			e.printStackTrace();
		}
		return output;
	}
}