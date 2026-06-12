package library.service;

import library.model.Book;
import library.util.FileManager;

import java.util.List;
import java.util.Optional;

public class LibraryService {

    //fields
    private List<Book> books;
    //end of fields

    //constructor
    public LibraryService(){
        this.books = FileManager.loadBooksFromCSV();
        FileManager.saveBooksToCSV(this.books);
    }
    //end of constructor

    //get all book method
    public List<Book> getAllBooks(){
        return this.books;
    }
    //end of get all book method

    //find book method
    public Optional<Book> findBookById(int id){
        for (Book book : this.books){
            if (book.getId() == id){
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }
    //end of find book method

    //get book method
    public List<String> getBookPages(Book book, int linesPerPage){
        return FileManager.readBookPages(book.getTextFilePath(), linesPerPage);
    }
    //end of get book method

    //count line method
    public int countLines(Book book){
        return FileManager.countLines(book.getTextFilePath());
    }
    //end of count line method


}
