package com.majikarpets.ui.styles.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.majikarpets.ui.Component;
import com.majikarpets.ui.styles.Interpolator;
import com.majikarpets.ui.styles.Property;
import com.majikarpets.ui.styles.parser.InvalidPropertyValueException;

public class FilePropertyType implements PropertyType {
	
	private static final Pattern classpathPattern = Pattern.compile("^classpath\\(\\s*'([\\w\\-/\\.\\)\\(\\[\\]]+)'\\s*\\)$");
	private static final Pattern internalPattern = Pattern.compile("^internal\\(\\s*'([\\w\\-/\\.\\)\\(\\[\\]]+)'\\s*\\)$");
	private static final Pattern externalPattern = Pattern.compile("^external\\(\\s*'([\\w\\-/\\.\\)\\(\\[\\]]+)'\\s*\\)$");
	private static final Pattern localPattern = Pattern.compile("^local\\(\\s*'([\\w\\-/\\.\\)\\(\\[\\]]+)'\\s*\\)$");
	private static final Pattern absolutePattern = Pattern.compile("^absolute\\(\\s*'([\\w\\-/\\.\\)\\(\\[\\]]+)'\\s*\\)$");

	@Override
	public Object parseValue(String prop) throws InvalidPropertyValueException {
		Matcher m = classpathPattern.matcher(prop);
		if (m.matches()) {
			String path = m.group(1);
			try {
				return Gdx.files.classpath(path);
			} catch (GdxRuntimeException ex) {
				throw new InvalidPropertyValueException("The classpath resource: '" + path + "' could not be located.");
			}
		}
		m = internalPattern.matcher(prop);
		if (m.matches()) {
			String path = m.group(1);
			try {
				return Gdx.files.internal(path);
			} catch (GdxRuntimeException ex) {
				throw new InvalidPropertyValueException("The internal resource: '" + path + "' could not be located.");
			}
		}
		m = externalPattern.matcher(prop);
		if (m.matches()) {
			String path = m.group(1);
			try {
				return Gdx.files.external(path);
			} catch (GdxRuntimeException ex) {
				throw new InvalidPropertyValueException("The external resource: '" + path + "' could not be located.");
			}
		}
		m = localPattern.matcher(prop);
		if (m.matches()) {
			String path = m.group(1);
			try {
				return Gdx.files.local(path);
			} catch (GdxRuntimeException ex) {
				throw new InvalidPropertyValueException("The local resource: '" + path + "' could not be located.");
			}
		}
		m = absolutePattern.matcher(prop);
		if (m.matches()) {
			String path = m.group(1);
			try {
				return Gdx.files.absolute(path);
			} catch (GdxRuntimeException ex) {
				throw new InvalidPropertyValueException("The absolute resource: '" + path + "' could not be located.");
			}
		}
		throw new InvalidPropertyValueException("Invalid file property value: \"" + prop + "\"");
	}

	@Override
	public boolean verifyValue(Object val) {
		return val != null && val instanceof FileHandle;
	}

	@Override
	public String stringifyValue(Object val) {
		FileHandle h = (FileHandle)val;
		return h.type().name().toLowerCase() + "( '" + h.path() + "' )";
	}

	@Override
	public Class<?> getMostGenericType() {
		return FileHandle.class;
	}

	@Override
	public boolean isInterpolatable() {
		return false;
	}

	@Override
	public Interpolator getInterpolator(Object start, Object end, Property prop, Component context) {
		throw new RuntimeException("File type properties are not interpolatable.");
	}

}
