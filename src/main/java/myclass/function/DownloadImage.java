package myclass.function;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import myclass.config.MyConfig;

public class DownloadImage {
    private static String SAVE_DIR = MyConfig.getConfig("DownloadImage.saveDir");
    
    @SuppressWarnings("unused")
	private static DownloadImage instance = new DownloadImage();

    public static String getImagePath(String imageUrl, String imageName) throws Exception {
        String imagePath = SAVE_DIR + imageName + ".jpg";

        URL url = new URL(imageUrl);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setAllowUserInteraction(false);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestMethod("GET");
        conn.connect();

        int httpStatusCode = conn.getResponseCode();

        if(httpStatusCode != HttpURLConnection.HTTP_OK){
            throw new Exception();
        }

        DataInputStream dataInStream = new DataInputStream(conn.getInputStream());

        DataOutputStream dataOutStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(imagePath)));

        byte[] b = new byte[4096];
        int readByte = 0;

        while(-1 != (readByte = dataInStream.read(b))){
            dataOutStream.write(b, 0, readByte);
        }

        dataInStream.close();
        dataOutStream.close();

        return imagePath;
    }
}