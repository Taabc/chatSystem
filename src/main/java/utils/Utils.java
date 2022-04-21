package utils;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Utils {
    public static byte[] load_picture_from_file(String pic_url){
        try{
            File f = new File(pic_url);
            InputStream is = new FileInputStream(f);
            byte[] b = new byte[(int)f.length()];
            is.read(b);
            is.close();
            return b;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean write_picture_to_file(byte[] pic_date,String filePath){
        try {
            FileImageOutputStream imageOutputStream = new FileImageOutputStream(new File(filePath));
            imageOutputStream.write(pic_date,0,pic_date.length);
            imageOutputStream.flush();
            imageOutputStream.close();
//            FileOutputStream fos = new FileOutputStream(new File("d:\\"+fileName),true);
//            fos.write(pic_date,0,pic_date.length);
//            fos.flush();
//            fos.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean write_temp_picture_to_file(byte[] pic_date,String filePath){
        try {
            FileImageOutputStream imageOutputStream = new FileImageOutputStream(new File(filePath));
            imageOutputStream.write(pic_date,0,pic_date.length);
            imageOutputStream.flush();
            imageOutputStream.close();
//            FileOutputStream fos = new FileOutputStream(new File("d:\\"+fileName),true);
//            fos.write(pic_date,0,pic_date.length);
//            fos.flush();
//            fos.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public static byte[] load_file_from_file(String file_url){
        try{
            File f = new File(file_url);
            InputStream is = new FileInputStream(f);
            byte[] b = new byte[(int)f.length()];
            is.read(b);
            is.close();
            return b;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean write_file_to_file(byte[] file_date,String filePath){
        try {
            File file = new File(filePath);
            File fileParent = file.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            FileOutputStream fos=null;
            fos=new FileOutputStream(file);
            fos.write(file_date);

//            FileImageOutputStream imageOutputStream = new FileImageOutputStream(new File(filePath));
//            imageOutputStream.write(pic_date,0,pic_date.length);
            fos.flush();
            fos.close();
//            FileOutputStream fos = new FileOutputStream(new File("d:\\"+fileName),true);
//            fos.write(pic_date,0,pic_date.length);
//            fos.flush();
//            fos.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
