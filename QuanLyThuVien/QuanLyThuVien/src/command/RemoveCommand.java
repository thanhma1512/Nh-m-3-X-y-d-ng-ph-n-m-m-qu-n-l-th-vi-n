package command;

import domain.LibraryService;

public class RemoveCommand extends Command {
    private int bookId;

    public RemoveCommand(LibraryService libraryService, int bookId) {
        super(libraryService);
        this.bookId = bookId;
    }

    @Override
    public void execute() {
        libraryService.removeBook(bookId);
    }

}
