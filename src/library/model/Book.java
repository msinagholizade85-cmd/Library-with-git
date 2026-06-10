package library.model;

public class Book {

    //fields
    private int id;
    private String title;
    private String textFilePath;
    private String author;
    private String publisher;
    private int publicationYear;

    private static int counter = 1;
    //end-of-fields

    //constructor
    public Book(String title, String textFilePath, String author, String publisher, int publicationYear){

        this.id = counter++;
        this.title = title;
        this.textFilePath = textFilePath;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;

    }
    //end-of-constructor

    //getter-methods
    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getTextFilePath(){
        return this.textFilePath;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getPublisher(){
        return this.publisher;
    }

    public int getPublicationYear(){
        return this.publicationYear;
    }
    //end-of-getter-methods

    //setter-methods
    public void setTitle(String title){
        this.title = title;
    }

    public void setTextFilePath(String textFilePath){
        this.textFilePath = textFilePath;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setPublisher(String publisher){
        this.publisher = publisher;
    }

    public void setPublicationYear(int publicationYear){
        this.publicationYear = publicationYear;
    }
    //end-of-setter-methods

    @Override
    public String toString(){
        return "[" + this.id + "] " + this.title + " | " + this.author + " | " + this.publisher + " | " + this.publicationYear;
    }

}
