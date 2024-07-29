package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import domain.LibraryService;
import observer.Subcriber;
import persistence.model.Book;
import persistence.model.ReferenceBook;
import persistence.model.TextBook;

public class LibraryView extends JFrame implements Subcriber {
    private JTable bookTable;

    public LibraryService getLibraryServirce() {
        return libraryServirce;
    }

    private LibraryController controller;
    private LibraryService libraryServirce;
    private DefaultTableModel tableModel;
    private JComboBox<String> typeComboBox, conditionComboBox;
    private JLabel conditionLabel, taxLabel, priceLable, quantityLable, publisherLable, createAtLable,
            typeLabel, idBookLabel,
            priceTextBooks, priceReferenceBooks, agvPriceReferenceBookLabel;
    private JTextField idBookField, taxField, priceField, quantityField, publisherField, createAtField,
            priceTextBooksField, priceReferenceBooksField, agvPriceReferenceBookField;
    private JButton addButton, removeButton, editButton, findButton, totalPriceTextBookButton,
            totalPriceReferenceBookButton, agvPriceButton;

    // search
    private JLabel searchLabel;
    private JTextField searchField;
    private JButton searchButton;

    public LibraryView(LibraryService service) {
        this.libraryServirce = service;
        this.controller = new LibraryController(this);
        // Initialize components
        idBookLabel = new JLabel("Mã sách:");
        priceLable = new JLabel("Giá:");
        quantityLable = new JLabel("Số lượng:");
        publisherLable = new JLabel("Nhà xuất bản:");
        createAtLable = new JLabel("Ngày nhập:");
        typeLabel = new JLabel("Loại sách:");
        priceTextBooks = new JLabel("Tổng thành tiền của sách giáo khoa: ");
        priceReferenceBooks = new JLabel("Tổng thành tiền của sách tham khảo: ");
        taxLabel = new JLabel("Thuế(dành cho sách tham khảo): ");
        conditionLabel = new JLabel("Trạng thái(dành cho sách giáo khoa):");
        agvPriceReferenceBookLabel = new JLabel("Trung bình cộng đơn giá của sách tham khảo:");

        idBookField = new JTextField(10);
        idBookField.setEditable(false);
        priceField = new JTextField(10);
        quantityField = new JTextField(10);
        publisherField = new JTextField(20);
        createAtField = new JTextField(20);
        createAtField.setText("01-80-2024");
        priceReferenceBooksField = new JTextField(20);
        priceReferenceBooksField.setEditable(false);
        priceTextBooksField = new JTextField(20);
        priceTextBooksField.setEditable(false);
        agvPriceReferenceBookField = new JTextField(20);
        agvPriceReferenceBookField.setEditable(false);
        taxField = new JTextField(10);

        typeComboBox = new JComboBox<>();
        typeComboBox.addItem("Sách giáo khoa");
        typeComboBox.addItem("Sách tham khảo");
        conditionComboBox = new JComboBox<>();
        conditionComboBox.addItem("Sách cũ");
        conditionComboBox.addItem("Sách mới");

        addButton = new JButton("Thêm");
        removeButton = new JButton("Xóa");
        editButton = new JButton("Sửa");
        findButton = new JButton("Tìm kiếm");
        totalPriceTextBookButton = new JButton("Tổng thành tiền sách giáo khoa");
        totalPriceReferenceBookButton = new JButton("Tổng thành tiền sách tham khảo");
        agvPriceButton = new JButton("AGV");

        // search
        searchLabel = new JLabel("Tìm kiếm theo mã sách");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        // Table setup
        String[] columnNames = { "Mã sách", "Giá", "Số lượng", "Nhà xuất bản", "Ngày nhập", "Thuế",
                "Trạng thái" };
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);

        // Button actions
        typeComboBox.addActionListener(controller);

        addButton.addActionListener(controller);

        totalPriceReferenceBookButton.addActionListener(controller);
        totalPriceTextBookButton.addActionListener(controller);

        removeButton.addActionListener(controller);
        editButton.addActionListener(controller);
        searchButton.addActionListener(controller);
        agvPriceButton.addActionListener(controller);
        findButton.addActionListener(controller);

        // search
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Table selection action
        bookTable.getSelectionModel().addListSelectionListener(controller);

        // Layout setup
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(idBookLabel, gbc);
        gbc.gridx++;
        inputPanel.add(idBookField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(priceLable, gbc);
        gbc.gridx++;
        inputPanel.add(priceField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(quantityLable, gbc);
        gbc.gridx++;
        inputPanel.add(quantityField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(typeLabel, gbc);
        gbc.gridx++;
        inputPanel.add(typeComboBox, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(publisherLable, gbc);
        gbc.gridx++;
        inputPanel.add(publisherField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(conditionLabel, gbc);
        gbc.gridx++;
        inputPanel.add(conditionComboBox, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(taxLabel, gbc);
        gbc.gridx++;
        inputPanel.add(taxField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(createAtLable, gbc);
        gbc.gridx++;
        inputPanel.add(createAtField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(searchLabel, gbc);
        gbc.gridx++;
        inputPanel.add(searchField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(findButton);
        buttonPanel.add(searchButton);

        JPanel textBooksPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel referenceBooksPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel agvPricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        agvPricePanel.add(agvPriceReferenceBookLabel);
        agvPricePanel.add(agvPriceReferenceBookField);
        agvPricePanel.add(agvPriceButton);
        textBooksPanel.add(priceTextBooks);
        textBooksPanel.add(priceTextBooksField);
        referenceBooksPanel.add(priceReferenceBooks);
        referenceBooksPanel.add(priceReferenceBooksField);
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));
        totalPanel.add(textBooksPanel);
        totalPanel.add(totalPriceTextBookButton);
        totalPanel.add(agvPricePanel);
        totalPanel.add(referenceBooksPanel);
        totalPanel.add(totalPriceReferenceBookButton);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(bookTable), BorderLayout.CENTER);
        mainPanel.add(totalPanel, BorderLayout.EAST);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        // search
        // mainPanel.add(searchPanel);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.setTitle("Quản lý thư viện");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 600);
        this.add(mainPanel);
        this.setVisible(true);
        controller.refreshBookTable();
    }

    public JTable getBookTable() {
        return bookTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JComboBox<String> getTypeComboBox() {
        return typeComboBox;
    }

    public JComboBox<String> getConditionComboBox() {
        return conditionComboBox;
    }

    public JTextField getIdBookField() {
        return idBookField;
    }

    public JTextField getTaxField() {
        return taxField;
    }

    public JTextField getPriceField() {
        return priceField;
    }

    public JTextField getQuantityField() {
        return quantityField;
    }

    public JTextField getPublisherField() {
        return publisherField;
    }

    public JTextField getCreateAtField() {
        return createAtField;
    }

    public JTextField getPriceTextBooksField() {
        return priceTextBooksField;
    }

    public JTextField getPriceReferenceBooksField() {
        return priceReferenceBooksField;
    }

    public JTextField getAgvPriceReferenceBookField() {
        return agvPriceReferenceBookField;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    @Override
    public void update() {
        controller.refreshBookTable();
    }

}
