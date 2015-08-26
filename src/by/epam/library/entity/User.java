package by.epam.library.entity;


@SuppressWarnings("serial")
public class User extends AbstractEntity {
	private int idUser;
	private String login;
	private String password;
	private String name;
	private String surname;
	private int adminFlag;

	public User(int idUser, String login, String password, String name, String surname,
			int adminFlag) {
		super();
		this.idUser = idUser;
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.adminFlag = adminFlag;
	}

	public User(String login, String password, String name, String surname, int adminFlag) {
		super();
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.adminFlag = adminFlag;
	}

	public User() {
		super();
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(int i) {
				this.adminFlag = i;
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idUser;
		result = prime * result + adminFlag;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
		User other = (User) obj;
		if (idUser != other.idUser)
			return false;
		if (adminFlag != other.adminFlag)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"User [idUser=%d, login=%s, password=%s, name=%s, surname=%s, adminFlag=%b]", idUser, login,
				password, name, surname, adminFlag);
	}

}
