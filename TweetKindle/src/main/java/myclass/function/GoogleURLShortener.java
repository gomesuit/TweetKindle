package myclass.function;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import myclass.config.MyConfig;
import net.arnx.jsonic.JSON;

import java.util.Map;

public class GoogleURLShortener {
    private static final String API_KEY = MyConfig.getConfig("GoogleURLShortener.apiKey");
    private static final String ENDPOINT = "https://www.googleapis.com/urlshortener/v1/url?key=" + API_KEY;

    public static String getShortUrl(String longUrl) throws Exception{
        HttpPost post = new HttpPost(ENDPOINT);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity("{'longUrl': '" + longUrl + "'}", "UTF-8"));
		HttpResponse response = new DefaultHttpClient().execute(post);

        String responseText = EntityUtils.toString(response.getEntity());
        Map map = JSON.decode(responseText, Map.class);
        
        return (String)map.get("id");
    }
}