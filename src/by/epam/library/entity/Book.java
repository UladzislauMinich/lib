/**
 * 
 */
package by.epam.library.entity;

/**
 * @author user_tp
 * 
 */
@SuppressWarnings("serial")
public class Book extends AbstractEntity {
	private int idBook;
	private String name;
	private String authorName;
	private String authorSurname;
	private String genre;
	private String description;
	private int amount;
	private String impritYear;
	private boolean onlyReadingHall;
	private String picturePath;

	public Book() {
		super();
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorSurname() {
		return authorSurname;
	}

	public void setAuthorSurname(String authorSurname) {
		this.authorSurname = authorSurname;
	}

	public int getIdBook() {
		return idBook;
	}

	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getImpritYear() {
		return impritYear;
	}

	public void setImpritYear(String impritYear) {
		this.impritYear = impritYear;
	}

	public boolean isOnlyReadingHall() {
		return onlyReadingHall;
	}

	public void setOnlyReadingHall(boolean onlyReadingHall) {
		this.onlyReadingHall = onlyReadingHall;
	}

	public void setOnlyReadingHall(String parameter) {
		if (parameter.equalsIgnoreCase("yes")) {
			this.onlyReadingHall = true;
		} else if (parameter.equalsIgnoreCase("no")) {
			this.onlyReadingHall = false;
		}
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
		result = prime * result + ((authorSurname == null) ? 0 : authorSurname.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + idBook;
		result = prime * result + ((impritYear == null) ? 0 : impritYear.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Book other = (Book) obj;
		if (amount != other.amount)
			return false;
		if (authorName == null) {
			if (other.authorName != null)
				return false;
		} else if (!authorName.equals(other.authorName))
			return false;
		if (authorSurname == null) {
			if (other.authorSurname != null)
				return false;
		} else if (!authorSurname.equals(other.authorSurname))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (idBook != other.idBook)
			return false;
		if (impritYear == null) {
			if (other.impritYear != null)
				return false;
		} else if (!impritYear.equals(other.impritYear))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (onlyReadingHall != other.onlyReadingHall)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [idBook=" + idBook + ", name=" + name + ", authorName=" + authorName
				+ ", authorSurname=" + authorSurname + ", genre=" + genre + ", description=" + description
				+ ", amount=" + amount + ", impritYear=" + impritYear + ", onlyReadingHall="
				+ onlyReadingHall + "]";
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	
}
