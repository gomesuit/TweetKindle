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

public class TweetTodaySales implements Job 
{
    private static Logger logger = LogManager.getLogger(TweetTodaySales.class);
    
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String[] args = {};
		TweetTodaySales.main(args);
	}
    
    public static void main( String[] args ){

    	Map<String,String> map;
    	if(args.length != 0){
    		map = KindleBO.getKindle(args[0]);
    	}else{
    		map = KindleBO.getKindleTodaySale();
    	}

        if(map == null){
            logger.info("検索結果は一件もありませんでした。");
        }else{
	        try{
	            Twitter twitter = new TwitterFactory().getInstance();
	            String shortUrl = GoogleURLShortener.getShortUrl(map.get("detailPageURL"));
	            String tweetContent = "【本日発売！】　#Kindle\n";
	            tweetContent = tweetContent + "『" + cutStr(map.get("title"), 60) + "』\n";
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
	            logger.info("本日発売ツイートが正常終了しました。{}", map.get("asin"), tweetContent);
	        }catch(Exception e){
	            String message = "";
	            message = message + "ASIN:" + map.get("asin") + "\n";
	            message = message + "TITLE:" + map.get("title") + "\n";
	            message = message + "エラー内容:" + "\n" + e;
	            try {
					MyMail.sendMail("本日発売ツイートエラー通知", message);
				} catch (Exception e1) {
					logger.error("failed to send mail", e1);
				}
	            logger.error("Exception of TweetTodaySales", e);
	        }finally{
	            KindleBO.updateTweetTodaySales(map.get("asin"));
	        }
        }
    }

    private static String cutStr(String str, int num){
    	if(str.length() <= num){
    		return str;
    	}else{
    		return str.substring(0,num) + "…";
    	}
    }
}
