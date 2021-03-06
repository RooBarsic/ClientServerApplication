package server.dataBase;

import common.MyFrame;
import common.bookComponents.Book;
import server.frames.mainFrame.MainFrame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class DataLog {
    public static MainFrame mainFrame;
    private static Users users;
    private static BookSet bookSet;
    private static DataBaseBookController dbsBookController;
    private static DataBaseUsersController dbsUsersController;

    /**
     * return Connection to DataBase
     * @return
     */
    public static Connection getConnection(){
        String DB_Driver = "org.postgresql.Driver";
        String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        try {
            Class.forName(DB_Driver); //Проверяем наличие JDBC драйвера для работы с БД
            Connection connection = DriverManager.getConnection(DB_URL, "roo", "12345");//соединениесБД
            System.out.println("Соединение с СУБД выполнено.");
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // обработка ошибки  Class.forName
            System.out.println("JDBC драйвер для СУБД не найден!");
        } catch (SQLException e) {
            e.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            System.out.println("Ошибка SQL !");
        }
        return null;
    }

    /**
     * set start parameters
     */
    public static void setLogs(){
        users = new Users();
        bookSet = new BookSet();
        dbsBookController = new DataBaseBookController();
        dbsUsersController = new DataBaseUsersController();
    }

    /**
     * ReadBooks from file ( and can open MainFrame )
     * @param openMainFrame
     */
    public static void readBooksFromFile(boolean openMainFrame){
        dbsBookController.readBooks();
        if(openMainFrame) {
            DataLog.mainFrame.setVisibleFalse();
            DataLog.mainFrame = new MainFrame(openMainFrame);
        }
    }

    /**
     * CAll saveBooks method in fileBookController
     */
    public static void saveBooksToFile(){
        dbsBookController.saveBooks();
    }

    /**
     * Add new User to Users data base
     * @param login
     * @param password
     */
    public static void saveNewUser(String login, String password){
        dbsUsersController.saveNewUser(login, password);
    }

    /**
     * Call method readUsers from fillUsersController,
     * for loading users info in begin
     */
    public static void readUsersFromFile(){
        dbsUsersController.readUsers();
    }

    /**
     * Check is user eith such login and password - in data base
     * @param login
     * @param password
     * @return
     */
    public static boolean checkUser(String login, String password){
        return users.checkUser(login, password);
    }

    /**
     * return Users info
     * @return
     */
    public static ArrayList<String> getUsersInfo(){
        return users.getUsersInfo();
    }

    /**
     * 2 - wrong username or Password
     * 0 - username already exist
     * 1 - done
     * @param login
     * @param password
     * @return
     */
    public static int addNewUser(String login, String password){
        return users.addNewUser(login, password);
    }

    /**
     * add book to BookSet
     * @param book
     */
    public static void addBook(Book book) {
        if(book != null)
        bookSet.addBook(book);
    }

    /**
     * return book with key(key) if it in BookSet, else return null
     * @param key
     * @return
     */
    public static Book getBook(String key) {
        return bookSet.getBook(key);
    }

    /**
     * return number of Books
     * @return
     */
    public static int getNumberOfBooks(){
        return bookSet.getNumberOfBooks();
    }

    /**
     * Clean all Collection
     */
    public static void clean(){
        bookSet.clean();
    }

    /**
     * Remove Book with key ke from Collection
     * @param ke - is String
     */
    public static void remove(String ke){
        bookSet.remove(ke);
    }

    /**
     * Remove all books from Collecction which smaller than given book
     * @param bo - is Book
     */
    public static void remove_lower(Book bo){
        bookSet.remove_lower(bo);
    }

    /**
     * Remove all books from Collection which bige than given book
     * @param bo - is Book
     */
    public static void remove_greater(Book bo){
        bookSet.remove_greater(bo);
    }

    /**
     * if given Book is smaller than smalest Book from Collection
     * then add given Book to the Collection
     * @param bo - is Book
     */
    public static void add_if_min(Book bo){
        bookSet.add_if_min(bo);
    }

    /**
     * Load Book to File named fileName
     * @param
     */
    public static List<Book> getBooks(){
        return bookSet.save();
    }

    /**
     * get Book information for showing
     * @return
     */
    public static String[] getBookNames(){
        List<Book> books = getBooks();
        String[] bookNames = new String[books.size()];
        for(int i = 0; i < books.size(); i++){
            bookNames[i] = "bookName: " + books.get(i).getName() + ";       bookJanr: " + books.get(i).getJanr().name() + "    ";
            bookNames[i] += "Color: " + books.get(i).getBookColor().name() + ";  coordinates{x, y} = {" +
                    Integer.toString(books.get(i).getBookCoordinates().getX()) + ", " +
                    Integer.toString(books.get(i).getBookCoordinates().getY()) + " }";
        }
        return bookNames;
    }

    public static Book getBookById(int id){
        List<Book > books = getBooks();
        if((0 <= id) && (id < books.size())) return books.get(id);
        return null;
    }
}