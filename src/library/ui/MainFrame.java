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
                    //showBookMenu(); //هر موقع این متد رو نوشتی این کامنت رو بردار
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
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 0){
                    currentPage--;
                    pageArea.setText(pages.get(currentPage));
                }
            }
        });

        JButton nextButton = new JButton(">");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage < pages.size() - 1){
                    currentPage++;
                    pageArea.setText(pages.get(currentPage));
                }
            }
        });
        //end of buttons

        if (editable){
            //این بخش رو اضافه کن مربوط به متدهایی هست که نوشتی
        }

        panelOfButton.add(prevButton);
        panelOfButton.add(nextButton);

        readerPanel.add(pageArea, BorderLayout.CENTER);
        readerPanel.add(panelOfButton, BorderLayout.SOUTH);

        add(readerPanel);
        revalidate();
        repaint();

    }
    //end of open book method

}