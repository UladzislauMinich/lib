package by.epam.library.command.enums;

import by.epam.library.command.ActionCommand;
import by.epam.library.command.AddBookToBasketCommand;
import by.epam.library.command.ChangeLanguageCommand;
import by.epam.library.command.DeleteBookFromBasketCommand;
import by.epam.library.command.DeleteOrderCommand;
import by.epam.library.command.LoginCommand;
import by.epam.library.command.LogoutCommand;
import by.epam.library.command.MakeOrderCommand;
import by.epam.library.command.SearchCommand;
import by.epam.library.command.ShowAllBookCommand;
import by.epam.library.command.ShowBookByGenreCommand;
import by.epam.library.command.ShowBookInformationCommand;
import by.epam.library.command.ShowOrderCommand;
import by.epam.library.command.SignUpCommand;
import by.epam.library.command.admin.AddBookCommand;
import by.epam.library.command.admin.CompleteOrderCommand;
import by.epam.library.command.admin.DeleteBookCommand;
import by.epam.library.command.admin.DeleteUserCommand;
import by.epam.library.command.admin.EditBookCommand;
import by.epam.library.command.admin.ShowAllUserCommand;
import by.epam.library.command.navigation.ToAddCommand;
import by.epam.library.command.navigation.ToBasketCommand;
import by.epam.library.command.navigation.ToEditCommand;
import by.epam.library.command.navigation.ToIndexCommand;
import by.epam.library.command.navigation.ToMainCommand;
import by.epam.library.command.navigation.ToSignUpCommand;

public enum CommandEnum {
	LOGIN {
		{
			this.command = new LoginCommand();
		}
	},
	LOGOUT {
		{
			this.command = new LogoutCommand();
		}
	},
	SIGNUP {
		{
			this.command = new SignUpCommand();
		}
	},
	TO_SIGNUP{
		{
			this.command = new ToSignUpCommand();
		}
	},
	TO_INDEX{
		{
			this.command = new ToIndexCommand();
		}
	},
	TO_MAIN{
		{
			this.command = new ToMainCommand();
		}
	},
	TO_EDIT{
		{
			this.command = new ToEditCommand();
		}
	},
	TO_BASKET
	{
		{
			this.command = new ToBasketCommand();
		}
	},
	TO_ADD
	{
		{
			this.command = new ToAddCommand();
		}
	},
	CHANGE_LANGUAGE {
		{
		this.command = new ChangeLanguageCommand();
		}
	},
	SHOW_ALL_BOOK{
		{
			this.command = new ShowAllBookCommand();
		}
	},
	SHOW_BOOK_BY_GENRE{
		{
			this.command = new ShowBookByGenreCommand();
		}
	},
	SHOW_ORDER{
		{
			this.command = new ShowOrderCommand();

		}
	},
	SHOW_BOOK_INFORMATION{
		{
			this.command = new ShowBookInformationCommand();
		}
	},
	EDIT_BOOK{
		{
			this.command = new EditBookCommand();
		}
	},
	ADD_BOOK{
		{
			this.command = new AddBookCommand();
		}
	},
	DELETE_BOOK{
		{
			this.command = new DeleteBookCommand();
		}
	},
	DELETE_BOOK_FROM_BASKET{
		{
			this.command = new DeleteBookFromBasketCommand();
		}
	},
	SHOW_ALL_USER{
		{
			this.command = new ShowAllUserCommand();
		}
	},
	DELETE_USER{
		{
			this.command = new DeleteUserCommand();
		}
	},
	DELETE_ORDER{
		{
			this.command = new DeleteOrderCommand();
		}
	},
	COMPLETE_ORDER{
		{
			this.command = new CompleteOrderCommand();
		
		}
	},
	ADD_BOOK_TO_BASKET{
		{
			this.command = new AddBookToBasketCommand();
		}
	},
	MAKE_ORDER{
		{
			this.command = new MakeOrderCommand();
		}
	},
	SEARCH{
		{
			this.command = new SearchCommand();
		}
	};
	ActionCommand command;

	public ActionCommand getCurrentCommand() {
		return command;
	}
}
