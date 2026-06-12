package library.util;


import library.model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    //fields
    public static final String BOOKS_TEXT_DIR = "data/books_text/";
    public static final String BOOK_LIST_CSV = "data/books_text/Book_List.txt";
    //end of fields

    //load books from Book_List.txt
    public static List<Book> loadBooksFromCSV(){

        List<Book> books = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(BOOK_LIST_CSV))){

            //read-header
            br.readLine();

            String line = br.readLine();
            while (line != null){
                String[] objects = line.split(",");
                int publicationYear = Integer.parseInt(objects[3]);
                String path = BOOKS_TEXT_DIR + objects[4];

                Book book = new Book(objects[0], path, objects[1], objects[2], publicationYear);
                books.add(book);

                line = br.readLine();
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        return books;

    }
    //end of load book

    //save book to Book_List.txt
    public static void saveBooksToCSV(List<Book> books){

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(BOOK_LIST_CSV))) {

            //header-write
            bw.write("title,author,publisher,year,file");
            bw.newLine();

            for (Book book : books){
                String line = book.getTitle() + "," + book.getAuthor() + "," + book.getPublisher() + "," + book.getPublicationYear() + "," + book.getTextFilePath().replace(BOOKS_TEXT_DIR, "");
                bw.write(line);
                bw.newLine();
            }

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
    //end of save book

    //creat page
    public static List<String> readBookPages(String filePath, int linesPerPage){

        List<String> page = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()){
            page.add("the page not found.");
            return page;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))){

            int counterForLine = 0;
            String linesForPages = "";

            String line = br.readLine();
            while (line != null){

                if (counterForLine == linesPerPage){
                    page.add(linesForPages);
                    counterForLine = 0;
                    linesForPages = "";
                }

                linesForPages = linesForPages + line + "\n";
                counterForLine++;

                line = br.readLine();

            }

            if (!linesForPages.isEmpty()){
                page.add(linesForPages);
            }

        }
        catch (IOException e){
            page.add(e.getMessage());
        }

        return page;

    }
    //end of create page

    //count line
    public static int countLines(String filePath){

        int counterLine = 0;

        File file = new File(filePath);
        if (!file.exists()){
            return 0;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))){

            String line = br.readLine();
            while (line != null){
                counterLine++;
                line = br.readLine();
            }

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        return counterLine;

    }
    //end of count line
    // بخش 4: ویرایش متن کتاب
// سینا اینو من نوشتم تکمیل شد یه تست بگیر !!
    // write book text
    public static void writeBookText(String filePath, String content) throws IOException {
        // بازنویسی کامل فایل با استفاده از FileWriter
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
        }
        //  در صورت بروز خطا Exception به کلاس فراخوان پرتاب می‌شود (در امضای متد ذکر شده است)
    }
    // end of write book text

    // read full text
    public static String readFullText(String filePath) {
        StringBuilder fullText = new StringBuilder();
        File file = new File(filePath);

        if (!file.exists()) {
            return "";
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                fullText.append(line).append("\n");
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ""; // در صورت بروز خطا رشته خالی برگردانده می‌شود
        }

        return fullText.toString();
    }
    // end of read full text

}
