package myclass.main;

import myclass.function.AmazonApiKindle;
import myclass.function.DownloadImage;
import myclass.function.GoogleURLShortener;
import myclass.function.MyMail;
import myclass.model.Kindle;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class TweetOne implements Job {
    private static Logger logger = LogManager.getLogger(TweetOne.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		String[] args = {};
		TweetOne.main(args);
	}
    
    public static void main(String[] args) {
    	logger.info("【商品紹介】ツイート処理開始");
    	
    	if(args.length == 0){
    		logger.info("引数が指定されていません。");
    	}else{
	    	try{
		        Kindle kindle = AmazonApiKindle.getKindle(args[0]);
	
	            Twitter twitter = new TwitterFactory().getInstance();
	            String shortUrl = GoogleURLShortener.getShortUrl(kindle.getDetailPageURL());
	            String tweetContent = "【商品紹介！】　#Kindle\n";
	            tweetContent = tweetContent + "『" + cutStr(kindle.getTitle(), 60) + "』\n";
	            tweetContent = tweetContent + "発売日：" + kindle.getReleaseDate() + "\n";
	            tweetContent = tweetContent + shortUrl;
	    	    StatusUpdate update = new StatusUpdate(tweetContent);
		        
		        
		        String filePath = DownloadImage.getImagePath(kindle.getLargeImage(), kindle.getAsin());
		        update.setMedia(new File(filePath));
		        
				Status status = twitter.updateStatus(update);
	            logger.info("新着ツイートが正常終了しました。{}", kindle.getAsin(), tweetContent);
	    	}catch(Exception e){
	            String message = "";
	            message = message + "エラー内容:" + "\n" + e;
	            try {
					MyMail.sendMail("新着ツイートエラー通知", message);
				} catch (Exception e1) {
					logger.error("failed to send mail", e1);
				}
	            logger.error("Exception of TweetTop3", e);
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
