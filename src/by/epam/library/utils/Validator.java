package by.epam.library.utils;

import by.epam.library.entity.Book;
import by.epam.library.entity.User;
import by.epam.library.resource.MessageManager;
import java.util.regex.*;

public class Validator {
	public static final Pattern LOGIN_PATTERN = Pattern
			.compile("[\\w\\.]{3,25}@[a-z\\.]{3,10}\\.{1}[a-z]{2,5}");
	public static final Pattern PASSWORD_PATTERN = Pattern.compile("[A-z0-9]{3,20}");
	public static final Pattern NAME_SURNAME_PATTERN = Pattern.compile("[А-ЯA-Zа-яa-z\\s-]{2,45}");
	public static final Pattern NAME_BOOK_PATTERN = Pattern.compile("[\\wА-я\\s\\.,:-?!()\"+]{2,45}");
	public static final Pattern AMOUNT_PATTERN = Pattern.compile("[\\d]{0,3}");

	public static String validateUser(User user) {
		if (!LOGIN_PATTERN.matcher(user.getLogin()).matches()) {
			return MessageManager.INCORRECT_LOGIN;
		}
		if (!PASSWORD_PATTERN.matcher(user.getPassword()).matches()) {
			return MessageManager.INCORRECT_PASSWORD;
		}
		if (user.getName().length()*user.getSurname().length()==0) {
			return null;
		} else if (!NAME_SURNAME_PATTERN.matcher(user.getName()).find()
				&& !NAME_SURNAME_PATTERN.matcher(user.getSurname()).find()) {
			return MessageManager.INCORRECT_NAME_SURNAME;
		}
		return null;

	}

	public static String validateBook(Book book,String amount) {
		if (!NAME_BOOK_PATTERN.matcher(book.getName()).find()) {
			return MessageManager.INCORRECT_NAME_BOOK;
		}
		if (!NAME_SURNAME_PATTERN.matcher(book.getAuthorName()).find()) {
			return MessageManager.INCORRECT_NAME_AUTHOR;
		}
		if (!NAME_SURNAME_PATTERN.matcher(book.getAuthorSurname()).find()) {
			return MessageManager.INCORRECT_SURNAME_AUTHOR;
		}
		if (!AMOUNT_PATTERN.matcher(amount).matches()) {
			return MessageManager.INCORRECT_AMOUNT;
		}
		else{
			book.setAmount(Integer.parseInt(amount));
		}
		return null;

	}

}
