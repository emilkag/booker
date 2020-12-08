package base;

import org.junit.jupiter.api.BeforeAll;

import java.util.Properties;

import static utils.readfile.ReadFile.readAPropertyFile;

public class BaseTest {

    public static final String BASE_URL = "https://restful-booker.herokuapp.com/";
    protected static final String AUTH = "auth";
    protected static final String BOOKING = "booking";
    protected static String PASSWORD;
    protected static String USERNAME;
    protected static String TOKEN;

    @BeforeAll
    static void beforeAll(){

        Properties properties = readAPropertyFile("D:\\Projekty\\booker\\src\\properties.properties");
        PASSWORD = properties.getProperty("password");
        USERNAME = properties.getProperty("username");

    }


}
