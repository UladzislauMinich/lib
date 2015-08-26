package by.epam.library.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class BasketTag extends TagSupport {
	private int amount;
	private String locale;

	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public int doStartTag() throws JspException {
		try {

			if (locale.equalsIgnoreCase("en")) {
				pageContext.getOut().write("Basket(" + amount + ")");
			} else {
				pageContext.getOut().write("Корзина(" + amount + ")");
			}

		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;

	}

}

	
