/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.miniproject.ultis;

/**
 *
 * @author Luu Bao
 */
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUltis {

    public static String generateUniqueFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return timeStamp + "_" + originalFileName;
    }

    public static boolean deleteFile(String fileName){
        File file = new File(fileName);
        if(file.exists()){
            if(file.delete()){
                System.out.println("File delete success fully");
                return true;
            } else {
                System.out.println("File delete faild");
                return false;
            }
        }
        return false;
    }
}
