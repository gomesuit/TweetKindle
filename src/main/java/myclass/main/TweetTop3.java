package myclass.main;

import myclass.function.AmazonApiKindle;
import myclass.function.DownloadImage;
import myclass.function.GoogleURLShortener;
import myclass.function.MyHttpGet;
import myclass.model.Kindle;
import myclass.model.amazonrss.AmazonBestsellersRss;
import myclass.model.amazonrss.Item;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.UploadedMedia;

public class TweetTop3 {
    private static Logger logger = LogManager.getLogger(TweetTop3.class);
    
    public static void main(String[] args) throws Exception {
    	
    	String xml = MyHttpGet.getResponseXml("http://www.amazon.co.jp/gp/rss/bestsellers/digital-text/2293143051/ref=zg_bs_2293143051_rsslink");
        AmazonBestsellersRss amazonBestsellersRss = JAXB.unmarshal(new StringReader(xml), AmazonBestsellersRss.class);
        
        List<Item> itemList = amazonBestsellersRss.getChannel().getItem();
        
        List<Kindle> KindleList = new ArrayList<Kindle>();;
        KindleList.add(AmazonApiKindle.getKindle(itemList.get(0).getAsin()));
        KindleList.add(AmazonApiKindle.getKindle(itemList.get(1).getAsin()));
        KindleList.add(AmazonApiKindle.getKindle(itemList.get(2).getAsin()));
        
        Twitter twitter = new TwitterFactory().getInstance();
        long[] mediaIds = new long[3];
        String tweetContent = "【Kindleコミックベストセラー】　#Kindle\n";
        
        int i = 0;
		for(Kindle kindle : KindleList){
	        String filePath = DownloadImage.getImagePath(kindle.getLargeImage(), kindle.getAsin());
	        UploadedMedia media = twitter.uploadMedia(new File(filePath));
	        mediaIds[i] = media.getMediaId();
            //String shortUrl = GoogleURLShortener.getShortUrl(kindle.getDetailPageURL());
	        tweetContent = tweetContent + String.valueOf(i+1) + ".『" + cutStr(kindle.getTitle(), 30) + "』\n";
			i++;
		}
		logger.info("{}",tweetContent);
		
		StatusUpdate update = new StatusUpdate(tweetContent);
		update.setMediaIds(mediaIds);
		Status status = twitter.updateStatus(update);
		logger.info("{}", status);
    }
    
    private static String cutStr(String str, int num){
    	if(str.length() <= num){
    		return str;
    	}else{
    		return str.substring(0,num) + "…";
    	}
    }
}
