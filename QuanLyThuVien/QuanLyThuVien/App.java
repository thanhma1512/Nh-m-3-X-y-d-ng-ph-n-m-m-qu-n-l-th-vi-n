import domain.LibraryService;
import domain.LibraryServiceImpl;
import persistence.LibraryPersistence;
import presentation.LibraryView;

public class App {
    public static void main(String[] args) throws Exception {
        LibraryPersistence model = new LibraryPersistence();
        LibraryServiceImpl ctrl = new LibraryServiceImpl(model);
        LibraryView view = new LibraryView(ctrl);
        ctrl.subcribe(view);
    }
}
