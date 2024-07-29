package domain;

import java.util.List;

import observer.Publisher;
import persistence.LibraryPersistence;
import persistence.model.Book;
import persistence.model.ReferenceBook;
import persistence.model.TextBook;

public class LibraryServiceImpl extends Publisher implements LibraryService {
    private LibraryPersistence model;

    public LibraryServiceImpl(LibraryPersistence model) {
        this.model = model;
    }

    @Override
    public void addBook(Book book) {
        model.addBook(book);
        notifyChange();
    }

    @Override
    public List<Book> getAllBooks() {
        return model.getAllBooks();
    }

    @Override
    public double getTotalPriceTextBook() {
        List<Book> books = model.getAllBooks();
        double total = 0;
        for (Book book : books) {
            if (book instanceof ReferenceBook) {
                continue;
            } else {
                TextBook textBook = (TextBook) book;

                total += textBook.totalPrice();
            }
        }

        return total;
    }

    @Override
    public double getTotalPriceRefeneBook() {
        List<Book> books = model.getAllBooks();
        double total = 0;
        for (Book book : books) {
            if (book instanceof TextBook) {
                continue;
            } else {
                ReferenceBook referenceBook = (ReferenceBook) book;
                total += referenceBook.totalPrice();
                // total += referenceBook.getPrice() * referenceBook.getQuantity() +
                // referenceBook.getTax();
            }
        }

        return total;
    }

    @Override
    public Book findBook(int id) {
        return model.findBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        model.updateBook(book);
        notifyChange();
    }

    @Override
    public void removeBook(int id) {
        model.removeBookById(id);
        notifyChange();
    }

    @Override
    public List<Book> getBooksByPublisher(String publisher) {
        return model.getBooksByPublisher(publisher);
    }

    @Override
    public double getAvgPriceRefeneBook() {
        return model.agvPriceReferenceBook();
    }
}
