package by.epam.library.entity;

import java.util.List;

@SuppressWarnings("serial")
public class Order extends AbstractEntity{
	private int idOrder;
	private User user;
	private List<Book> books;
	private int status;
	private String date;
	
	public Order() {
		super();
	}
	public Order(int idOrder, User user, List<Book> books,String date) {
		super();
		this.idOrder = idOrder;
		this.user = user;
		this.books = books;
		this.date = date;
	}
	
	public int getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int i) {
		this.status = i;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((books == null) ? 0 : books.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + idOrder;
		result = prime * result + status;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (books == null) {
			if (other.books != null)
				return false;
		} else if (!books.equals(other.books))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (idOrder != other.idOrder)
			return false;
		if (status != other.status)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Order [idOrder=" + idOrder + ", user=" + user + ", books=" + books + ", status="
				+ status + ", date=" + date + "]";
	}

	

	
	
}
