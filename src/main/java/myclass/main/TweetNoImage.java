package myclass.main;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.StatusUpdate;

import java.util.Map;

import myclass.bo.KindleBO;
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
    private KindleBO kindleBO;
    
	public void execute(JobExecutionContext context) throws JobExecutionException {
		exec();
	}
    
    public void exec(){
    	kindleBO = new KindleBO();
    	
    	String asin = kindleBO.getNoImage();
    	if(asin == null){
    		logger.info("画像更新が一件もありませんでした。");
    	}else{
        	
        	Map<String,String> map = kindleBO.getKindle(asin);
	        try{
	            Twitter twitter = new TwitterFactory().getInstance();
	            String shortUrl = GoogleURLShortener.getShortUrl(map.get("detailPageURL"));
	            String tweetContent = "【画像更新】　#Kindle\n";
	            tweetContent = tweetContent + "『" + cutStr(map.get("title"), 60) + "』\n";
	            tweetContent = tweetContent + "発売日：" + map.get("releaseDate") + "\n";
	            tweetContent = tweetContent + shortUrl;
	    	    StatusUpdate update = new StatusUpdate(tweetContent);
	
/*	            if(map.get("largeImage").length() != 0){
	    	        String filePath = DownloadImage.getImagePath(map.get("largeImage"), map.get("asin"));
	    	        update.setMedia(new File(filePath));
	            }else{
	            	KindleBO.registerNoImage(map.get("asin"));
	            }*/
            	//KindleBO.registerNoImage(map.get("asin"));
	            
				twitter.updateStatus(update);
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
	        	kindleBO.deleteNoImage(map.get("asin"));
	        }
    	}
    	kindleBO = null;
    }

    private String cutStr(String str, int num){
    	if(str.length() <= num){
    		return str;
    	}else{
    		return str.substring(0,num) + "…";
    	}
    }
}
