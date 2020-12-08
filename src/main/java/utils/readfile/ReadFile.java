package utils.readfile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadFile {

    public static Properties readAPropertyFile(String filename){
        BufferedReader bufferedReader = null;
        Properties properties = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
            properties = new Properties();
            properties.load(bufferedReader);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bufferedReader != null;
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return properties;

    }

}
