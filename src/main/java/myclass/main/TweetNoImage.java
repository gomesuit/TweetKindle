package myclass.main;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.StatusUpdate;

import java.io.File;
import java.util.Map;

import myclass.bo.KindleBO;
import myclass.function.DownloadImage;
import myclass.function.GoogleURLShortener;
import myclass.function.MyMail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TweetNoImage implements Job 
{
    private static Logger logger = LogManager.getLogger(TweetNoImage.class);
    
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String[] args = {};
		TweetNoImage.main(args);
	}
    
    public static void main( String[] args ){
    	String asin = KindleBO.getNoImage();
    	if(asin == null){
    		logger.info("画像更新が一件もありませんでした。");
    	}else{
        	
        	Map<String,String> map = KindleBO.getKindle(asin);
	        try{
	            Twitter twitter = new TwitterFactory().getInstance();
	            String shortUrl = GoogleURLShortener.getShortUrl(map.get("detailPageURL"));
	            String tweetContent = "【画像更新】　#Kindle\n";
	            tweetContent = tweetContent + "『" + map.get("title") + "』\n";
	            tweetContent = tweetContent + "発売日：" + map.get("releaseDate") + "\n";
	            tweetContent = tweetContent + shortUrl;
	    	    StatusUpdate update = new StatusUpdate(tweetContent);
	
	            if(map.get("largeImage").length() != 0){
	    	        String filePath = DownloadImage.getImagePath(map.get("largeImage"), map.get("asin"));
	    	        update.setMedia(new File(filePath));
	            }else{
	            	KindleBO.registerNoImage(map.get("asin"));
	            }
	            
				Status status = twitter.updateStatus(update);
	            logger.info("画像更新ツイートが正常終了しました。{}", map.get("asin"), tweetContent);
	        }catch(Exception e){
	            String message = "";
	            message = message + "ASIN:" + map.get("asin") + "\n";
	            message = message + "TITLE:" + map.get("title") + "\n";
	            message = message + "エラー内容:" + "\n" + e;
	            try {
					MyMail.sendMail("画像更新ツイートエラー通知", message);
				} catch (Exception e1) {
					logger.error("failed to send mail", e1);
				}
	            logger.error("Exception of TweetNoImage", e);
	        }finally{
	            KindleBO.deleteNoImage(map.get("asin"));
	        }
    	}
    }
}
