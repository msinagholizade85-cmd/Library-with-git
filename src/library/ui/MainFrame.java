package library.ui;

import library.service.LibraryService;
import library.model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {

    //fields
    private LibraryService service;
    private JPanel booksPanel;
    private JPanel menuPanel;
    private JPanel readerPanel;
    private Book selectedBook;
    private JTextArea pageArea;
    private List<String> pages;
    private int currentPage;
    // سینا این فیلد هارو جا انداختی !
    private JTextField titleField;
    private JTextField authorField;
    private JTextField publisherField;
    private JTextField yearField;
    //end of fields

    //constructor
    public MainFrame(){

        service = new LibraryService();
        setTitle("Library");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createBooksPanel();

        add(booksPanel);
        setVisible(true);
    }
    //end of constructor

    // show book menu method
    public void showBookMenu() {
        if (booksPanel != null) remove(booksPanel);
        if (menuPanel != null) remove(menuPanel);
        if (readerPanel != null) remove(readerPanel);

        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 2, 10, 10));

        menuPanel.add(new JLabel("Title:"));
        titleField = new JTextField(selectedBook.getTitle());
        menuPanel.add(titleField);

        menuPanel.add(new JLabel("Author:"));
        authorField = new JTextField(selectedBook.getAuthor());
        menuPanel.add(authorField);

        menuPanel.add(new JLabel("Publisher:"));
        publisherField = new JTextField(selectedBook.getPublisher());
        menuPanel.add(publisherField);

        menuPanel.add(new JLabel("Publication Year:"));
        yearField = new JTextField(String.valueOf(selectedBook.getPublicationYear()));
        menuPanel.add(yearField);

        menuPanel.add(new JLabel("Total Lines:"));
        int linesCount = service.countLines(selectedBook);
        menuPanel.add(new JLabel(String.valueOf(linesCount)));

        JButton saveButton = new JButton("Save Metadata");
        saveButton.addActionListener(e -> {
            try {
                int year = Integer.parseInt(yearField.getText());
                boolean success = service.editBookMetadata(
                        selectedBook.getId(),
                        titleField.getText(),
                        authorField.getText(),
                        publisherField.getText(),
                        year
                );
                if (success) {
                    JOptionPane.showMessageDialog(this, "Metadata saved successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Error: Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for the year.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        menuPanel.add(saveButton);

        JButton readButton = new JButton("Read Book");
        readButton.addActionListener(e -> openBook(false));
        menuPanel.add(readButton);

        JButton editButton = new JButton("Edit Book");
        editButton.addActionListener(e -> openBook(true));
        menuPanel.add(editButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            createBooksPanel();
            add(booksPanel);
            revalidate();
            repaint();
        });
        menuPanel.add(backButton);

        add(menuPanel);
        revalidate();
        repaint();
    }
    // end of show book menu method


    //create book panel method
    public void createBooksPanel(){

        if (booksPanel != null){
            remove(booksPanel);
        }
        if (menuPanel != null){
            remove(menuPanel);
        }
        if (readerPanel != null){
            remove(readerPanel);
        }



        List<Book> books = service.getAllBooks();
        booksPanel = new JPanel();
        GridLayout gridLayout= new GridLayout(2, 2, 10, 10);
        booksPanel.setLayout(gridLayout);

        for (Book book : books){
            JButton bookButton = new JButton(book.getTitle());
            bookButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedBook = book;
                    showBookMenu(); // هر موقع این متد رو نوشتی این کامنت رو بردار
                    // سلام سینا من اینو درستش کردم حله !
                }
            });
            booksPanel.add(bookButton);
        }

        JButton refreshButton = new JButton("refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(booksPanel);
                createBooksPanel();
                add(booksPanel);
                revalidate();
                repaint();
            }
        });
        booksPanel.add(refreshButton);

    }
    //end of create book panel method

    //open book method
    public void openBook(boolean editable){

        if (booksPanel != null){
            remove(booksPanel);
        }
        if (menuPanel != null){
            remove(menuPanel);
        }
        if (readerPanel != null){
            remove(readerPanel);
        }

        readerPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        readerPanel.setLayout(borderLayout);

        pageArea = new JTextArea();
        pageArea.setEditable(editable);
        pages = service.getBookPages(selectedBook, 2);
        currentPage = 0;
        pageArea.setLineWrap(true);
        pageArea.setWrapStyleWord(true);
        pageArea.setText(pages.get(currentPage));

        //buttons
        JPanel panelOfButton = new JPanel();

        JButton prevButton = new JButton("<");
        prevButton.addActionListener(e -> {
            if (currentPage > 0) {
                if (editable) {
                    pages.set(currentPage, pageArea.getText()); // ذخیره تغییرات صفحه فعلی قبل از ورق زدن
                }
                currentPage--;
                pageArea.setText(pages.get(currentPage));
            }
        });

        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> {
            if (currentPage < pages.size() - 1) {
                if (editable) {
                    pages.set(currentPage, pageArea.getText()); // ذخیره تغییرات صفحه فعلی قبل از ورق زدن
                }
                currentPage++;
                pageArea.setText(pages.get(currentPage));
            }
        });

        panelOfButton.add(prevButton);
        panelOfButton.add(nextButton);

        //  سینا این اوکی شد !
        if (editable) {
            JButton applyButton = new JButton("Apply");
            applyButton.addActionListener(e -> {
                try {
                    // ذخیره کردن آخرین تغییرات صفحه‌ای که الان روبروی کاربر باز است
                    pages.set(currentPage, pageArea.getText());

                    // چسباندن تمام صفحات به یکدیگر
                    StringBuilder fullContent = new StringBuilder();
                    for (String p : pages) {
                        fullContent.append(p);
                    }

                    // فراخوانی متد سرویس برای ذخیره در فایل
                    boolean success = service.editBookContent(selectedBook.getId(), fullContent.toString());

                    if (success) {
                        JOptionPane.showMessageDialog(this, "Book content updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update book content.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            panelOfButton.add(applyButton);
        }

        // دکمه بازگشت به منوی اطلاعات کتاب
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showBookMenu());
        panelOfButton.add(backButton);

        readerPanel.add(pageArea, BorderLayout.CENTER);
        readerPanel.add(panelOfButton, BorderLayout.SOUTH);

        add(readerPanel);
        revalidate();
        repaint();
        //end of buttons

    }
    //end of open book method

}