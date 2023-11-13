/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Mostafa Atta
 */
public class ObjectChecker {

	public static boolean isEmptyOrNull(Object object) {
		if (object == null) {
			return true;
		}
		if (object instanceof Collection) {
			return ((Collection<?>) object).isEmpty();
		}
		if (object instanceof Map) {
			return ((Map) object).isEmpty();
		}
		if (object instanceof String) {
			return ((String) object).isEmpty();
		}
		if (object instanceof StringBuilder) {
			return ((StringBuilder) object).length() == 0;
		}
		return false;
	}

	public static boolean isNotEmptyOrNull(Object object) {
		return !isEmptyOrNull(object);
	}

	public static boolean isNotEmptyOrZero(Integer value) {
		return value != null && value != 0;
	}

	public static boolean isEmptyOrZero(BigDecimal value) {
		return isEmptyOrNull(value) || isZero(value);
	}

	public static boolean isZero(BigDecimal value) {
		return BigDecimal.ZERO.compareTo(value) == 0;
	}

	public static boolean areEqual(Object object1, Object object2) {
		if (object1 == object2) {
			return true;
		}
		if (object1 == null && object2 != null) {
			return false;
		}
		if (object1 == null && object2 == null) {
			return true;
		}
		if (object1 instanceof Enum) {
			object1 = object1.toString();
		}
		if (object2 instanceof Enum) {
			object2 = object2.toString();
		}
		if (object1 instanceof BigDecimal && object2 instanceof BigDecimal) {
			return ((BigDecimal) object1).compareTo((BigDecimal) object2) == 0;
		}
		return object1.equals(object2);
	}

	public static boolean isNotEmptyOrZero(BigDecimal value) {
		return isNotEmptyOrNull(value) && isNotZero(value);
	}
	public static boolean isNotZero(BigDecimal value) {
		return !isZero(value);
	}
	public static boolean areNotEqual(Object object1, Object object2) {
		return !areEqual(object1, object2);
	}

}
