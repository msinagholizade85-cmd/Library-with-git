package library.service;

import library.model.Book;
import library.util.FileManager;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class LibraryService {

    //fields
    private final List<Book> books;
    //سینا اینم اصلاح خورد چون پیشنهاد خود اینتلیجی بود که تغییر نکنه !
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

    // save books method
    public void saveBooks() {
        FileManager.saveBooksToCSV(this.books);
    }
    // end of save books method

    // edit book metadata method
    public boolean editBookMetadata(int bookId, String newTitle, String newAuthor, String newPublisher, Integer newYear) {
        Optional<Book> optionalBook = findBookById(bookId);
// مطالب زیر رو دقیق نوشتم سینا ؟! یه چک بکن !
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            // بررسی تهی یا خالی نبودن برای عنوان
            if (newTitle != null && !newTitle.trim().isEmpty()) {
                book.setTitle(newTitle);
            }

            // بررسی تهی یا خالی نبودن برای نویسنده
            if (newAuthor != null && !newAuthor.trim().isEmpty()) {
                book.setAuthor(newAuthor);
            }

            // بررسی تهی یا خالی نبودن برای ناشر
            if (newPublisher != null && !newPublisher.trim().isEmpty()) {
                book.setPublisher(newPublisher);
            }

            // بررسی داشتن مقدار برای سال انتشار
            if (newYear != null) {
                book.setPublicationYear(newYear);
            }

            // ذخیره تغییرات پس از اعمال
            saveBooks();
            return true;
        }

        return false; // اگر کتاب پیدا نشود
    }
    // end of edit book metadata method

    // read full text method
    public String readFullText(int bookId) {
        Optional<Book> optionalBook = findBookById(bookId);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return FileManager.readFullText(book.getTextFilePath());
        }

        return ""; // اگر کتاب پیدا نشود
    }
    // end of read full text method

    // edit book content method
    public boolean editBookContent(int bookId, String newContent) throws IOException {
        Optional<Book> optionalBook = findBookById(bookId);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            // استفاده از کلاس FileManager برای جایگزینی متن ( البته اینو میشد یطور دیگه نوشت سینا ! )
            FileManager.writeBookText(book.getTextFilePath(), newContent);
            return true;
        }

        return false; // اگر کتاب پیدا نشود
    }
    // end of edit book content method

}
