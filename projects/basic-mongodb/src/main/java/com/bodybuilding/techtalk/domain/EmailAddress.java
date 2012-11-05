/**
 * 
 */
package com.bodybuilding.techtalk.domain;

import java.util.regex.Pattern;


/**
 * @author martin
 * 
 */
public class EmailAddress {

	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

	private final String email;

	public EmailAddress(String email) {
		
		this.email = email;
	}

	public static boolean isValid(String candidate) {
		return candidate == null ? false : PATTERN.matcher(candidate).matches();
	}

	@Override
	public String toString() {
		return email;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EmailAddress)) {
			return false;
		}

		EmailAddress that = (EmailAddress) obj;
		return this.email.equals(that.email);
	}

	@Override
	public int hashCode() {
		return email.hashCode();
	}

}
