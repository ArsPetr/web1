package services;

import java.io.File;

public class FileManager {
    private static final String ABSOLUT_FILE_PATH = "\\src\\main\\webapp\\static\\images\\";
    public FileManager(){}

    public static void deleteFile (String path){
        File file = new File(ABSOLUT_FILE_PATH + path.substring(15));
        System.out.println(ABSOLUT_FILE_PATH + path.substring(15));


        if (file.delete()) System.out.println("File deleted");

    }
}
