package utils;

import javax.imageio.stream.FileImageOutputStream;
import java.io.*;

public class UtilsServer {
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
