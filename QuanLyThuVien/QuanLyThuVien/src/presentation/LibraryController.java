package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import command.AddCommand;
import command.Command;
import command.CommandProcessor;
import persistence.model.Book;
import persistence.model.ReferenceBook;
import persistence.model.TextBook;

public class LibraryController implements ActionListener, ListSelectionListener {
    private CommandProcessor commandProcessor;
    private LibraryView view;

    public LibraryController(LibraryView view) {
        this.commandProcessor = CommandProcessor.makeCommandProcessor();
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
            switch (command) {
                case "Thêm":
                    addBook();
                    break;
                case "Search":
                    searchPublisher();
                    break;
                case "Sửa":
                    editBook();
                    break;
                case "Tìm kiếm":
                    findBook();
                    break;
                case "Xóa":
                    removeBook();
                    break;
                case "AGV":
                    getAvgPriceRefrenceBook();
                    break;
                case "Tổng thành tiền sách giáo khoa":
                    getTotalPriceTextBook();
                    break;
                case "Tổng thành tiền sách tham khảo":
                    getTotalPriceRefrenceBook();
                    break;
                default:

                    handleTypeComboBoxChange();
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleTypeComboBoxChange() {
        if (view.getTypeComboBox().getSelectedItem().equals("Sách giáo khoa")) {
            view.getTaxField().setEditable(false);
            view.getConditionComboBox().setEnabled(true);
        } else if (view.getTypeComboBox().getSelectedItem().equals("Sách tham khảo")) {
            view.getConditionComboBox().setEnabled(false);
            view.getTaxField().setEditable(true);
        }
    }

    private void addBook() throws ParseException {
        try {
            double price = Double.parseDouble(view.getPriceField().getText());
            String publisher = view.getPriceField().getText();
            int quantity = Integer.parseInt(view.getQuantityField().getText());
            String createAt = view.getCreateAtField().getText();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(createAt);
            // Kiểm tra dữ liệu
            if (view.getPriceField().getText().isEmpty() || publisher.isEmpty()
                    || view.getQuantityField().getText().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ giá trị.");
            } else {
                if (view.getTypeComboBox().getSelectedItem().equals("Sách giáo khoa")) {
                    String condition = view.getConditionComboBox().getSelectedItem().toString();
                    TextBook newTextBook = new TextBook(1, price, quantity, publisher, date, condition);
                    Command command = new AddCommand(view.getLibraryServirce(), newTextBook);
                    command.execute();

                } else {
                    int tax = Integer.parseInt(view.getTaxField().getText());
                    ReferenceBook newBook = new ReferenceBook(1, price, quantity, publisher, date, tax);
                    Command command = new AddCommand(view.getLibraryServirce(), newBook);
                    command.execute();
                }

                // Clear input fields
                view.getPriceField().setText("");
                view.getQuantityField().setText("");
                view.getTaxField().setText("");
                view.getPriceField().setText("01-08-2024");
                view.getCreateAtField().setText("");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đúng dữ liệu số cho giá, số lượng.");
        }
    }

    public void refreshBookTable() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getBookTable().getModel();
        // Clear existing data in the table
        tableModel.setRowCount(0);
        List<Book> books = view.getLibraryServirce().getAllBooks();
        for (Book book : books) {
            Object[] rowData = new Object[8]; // Assuming 8 columns in your table model
            rowData[0] = book.getId();
            rowData[1] = book.getPrice();
            rowData[2] = book.getQuantity();
            rowData[3] = book.getPublisher();
            rowData[4] = book.getCreatedAt();

            if (book instanceof TextBook) {
                rowData[6] = ((TextBook) book).getCondition();
                rowData[5] = null; // Or a default value for Tax column
            } else if (book instanceof ReferenceBook) {
                rowData[6] = null; // Or a default value for Condition column
                rowData[5] = ((ReferenceBook) book).getTax();
            }
            tableModel.addRow(rowData);
        }
    }

    public void searchPublisher() {
        String publisherStr = JOptionPane.showInputDialog(view, "Vui lòng nhập tên nhà xuất bản:");
        if (publisherStr != null && !publisherStr.isEmpty()) {
            List<Book> books = view.getLibraryServirce().getBooksByPublisher(publisherStr);
            if (books != null && !books.isEmpty()) {
                displayBookTableByPublisher(books);
            } else {
                JOptionPane.showMessageDialog(view, "Không tìm thấy nhà xuất bản: " + publisherStr);
            }
        }
    }

    public void displayBookTableByPublisher(List<Book> books) {
        DefaultTableModel tableModel = (DefaultTableModel) view.getBookTable().getModel();
        // Clear existing data in the table
        tableModel.setRowCount(0);
        for (Book book : books) {
            Object[] rowData = new Object[8]; // Assuming 8 columns in your table model
            rowData[0] = book.getId();
            rowData[1] = book.getPrice();
            rowData[2] = book.getQuantity();
            rowData[3] = book.getPublisher();
            rowData[4] = book.getCreatedAt();

            if (book instanceof TextBook) {
                rowData[6] = ((TextBook) book).getCondition();
                rowData[5] = null; // Or a default value for Tax column
            } else if (book instanceof ReferenceBook) {
                rowData[6] = null; // Or a default value for Condition column
                rowData[5] = ((ReferenceBook) book).getTax();
            }
            tableModel.addRow(rowData);
        }
    }

    public void editBook() throws ParseException {
        int selectedRow = view.getBookTable().getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = Integer.parseInt(view.getIdBookField().getText());
                double price = Double.parseDouble(view.getPriceField().getText());
                String publisher = view.getPublisherField().getText();
                int quantity = Integer.parseInt(view.getQuantityField().getText());
                String createAt = view.getCreateAtField().getText();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(createAt);

                if (view.getTypeComboBox().getSelectedItem().equals("Sách giáo khoa")) {
                    String condition = view.getConditionComboBox().getSelectedItem().toString();
                    TextBook textBook = new TextBook(id, price, quantity, publisher, date, condition);
                    view.getLibraryServirce().updateBook(textBook);
                } else {
                    double tax = Double.parseDouble(view.getTaxField().getText());
                    ReferenceBook referenceBook = new ReferenceBook(id, price, quantity, publisher, date, tax);
                    view.getLibraryServirce().updateBook(referenceBook);
                }
                clearInputFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view,
                        "Invalid input for price, quantity, or ID. Please enter valid numbers.");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Please select a book to edit.");
        }

    }

    public void clearInputFields() {
        view.getIdBookField().setText("");
        view.getPriceField().setText("");
        view.getQuantityField().setText("");
        view.getTaxField().setText("");
        view.getPublisherField().setText("");
        view.getCreateAtField().setText("01-08-2024");
    }

    public void showSelectedBookInfo() {
        int selectedRow = view.getBookTable().getSelectedRow();
        if (selectedRow != -1) {
            int bookId = (int) view.getBookTable().getValueAt(selectedRow, 0);
            Book book = view.getLibraryServirce().findBook(bookId);
            if (book != null) {
                populateInputFields(book);
            }
        }
    }

    public void populateInputFields(Book book) {
        view.getIdBookField().setText(String.valueOf(book.getId()));
        view.getPriceField().setText(String.valueOf(book.getPrice()));
        view.getQuantityField().setText(String.valueOf(book.getQuantity()));
        view.getPublisherField().setText(book.getPublisher());
        view.getCreateAtField().setText(String.valueOf(book.getCreatedAt()));
        if (book instanceof TextBook) {
            view.getConditionComboBox().setSelectedItem(((TextBook) book).getCondition());
            view.getTypeComboBox().setSelectedIndex(0);
            view.getTaxField().setText("0");
        } else if (book instanceof ReferenceBook) {
            view.getTypeComboBox().setSelectedIndex(1);
            view.getTaxField().setText(String.valueOf(((ReferenceBook) book).getTax()));
        }
    }

    public void removeBook() {
        int selectedRow = view.getBookTable().getSelectedRow();
        if (selectedRow != -1) {
            int bookId = (int) view.getBookTable().getValueAt(selectedRow, 0);
            int response = JOptionPane.showConfirmDialog(
                    view,
                    "Bạn có muốn xóa sách này không?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                view.getLibraryServirce().removeBook(bookId);
                refreshBookTable();
                clearInputFields();
            }
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn sách để xóa.");
        }
    }

    public void findBook() {
        try {
            int bookId = Integer.parseInt(view.getSearchField().getText());
            if (view.getSearchField().getText().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập mã sách.");
                return;
            }
            if (bookId > 0) {
                Book book = view.getLibraryServirce().findBook(bookId);
                if (book != null) {
                    DefaultTableModel tableModel = (DefaultTableModel) view.getBookTable().getModel();
                    // Clear existing data in the table
                    tableModel.setRowCount(0);
                    Object[] rowData = new Object[8]; // Assuming 8 columns in your table model
                    rowData[0] = book.getId();
                    rowData[1] = book.getPrice();
                    rowData[2] = book.getQuantity();
                    rowData[3] = book.getPublisher();
                    rowData[4] = book.getCreatedAt();

                    if (book instanceof TextBook) {
                        rowData[6] = ((TextBook) book).getCondition();
                        rowData[5] = null; // Or a default value for Tax column
                    } else if (book instanceof ReferenceBook) {
                        rowData[6] = null; // Or a default value for Condition column
                        rowData[5] = ((ReferenceBook) book).getTax();
                    }
                    tableModel.addRow(rowData);
                } else {
                    JOptionPane.showMessageDialog(view, "Không tìm thấy sách với mã này.");
                }
            } else {
                JOptionPane.showMessageDialog(view, "Mã sách không hợp lệ.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Mã sách là kiểu số.");
        }
    }

    public void getTotalPriceTextBook() {
        double total = view.getLibraryServirce().getTotalPriceTextBook();
        view.getPriceTextBooksField().setText(total + "");
    }

    public void getTotalPriceRefrenceBook() {
        double total = view.getLibraryServirce().getTotalPriceRefeneBook();
        view.getPriceReferenceBooksField().setText(total + "");
    }

    public void getAvgPriceRefrenceBook() {
        double total = view.getLibraryServirce().getAvgPriceRefeneBook();
        view.getAgvPriceReferenceBookField().setText(total + "");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            showSelectedBookInfo();
        }
    }
}
