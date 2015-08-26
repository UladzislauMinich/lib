package by.epam.library.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Coder {
    	/**
    	 * Hash md5.
    	 *
    	 * @param text
    	 *            the text
    	 * @return the string
    	 */
    	public static String hashMD5(String text) {
    		return DigestUtils.md5Hex(text);
    	}
}
