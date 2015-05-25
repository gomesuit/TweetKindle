package myclass.main;

import myclass.bo.KindleBO;
import myclass.function.AmazonApiKindle;
import myclass.function.DownloadImage;
import myclass.function.MyHttpGet;
import myclass.function.MyMail;
import myclass.function.MyTwitterApi;
import myclass.model.Kindle;
import myclass.model.amazonrss.AmazonBestsellersRss;
import myclass.model.amazonrss.Item;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UploadedMedia;

public class TweetTop3 implements Job {
    private static Logger logger = LogManager.getLogger(TweetTop3.class);
    private KindleBO kindleBO = new KindleBO();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		exec();
	}
    
    public void exec() {
    	logger.info("【Kindleベストセラー】ツイート処理開始");
    	String headerTitle = "【Kindleベストセラー】";
    	String description = "";
		if(isTweet()){
	    	try{
				Map<String,String> map = kindleBO.getMinTweetTop3();
				description = map.get("description");
				headerTitle = "【Kindle" + description + "ベストセラー】";
				
		    	String xml = MyHttpGet.getResponseXml(map.get("url"));
		        AmazonBestsellersRss amazonBestsellersRss = JAXB.unmarshal(new StringReader(xml), AmazonBestsellersRss.class);
		        
		        List<Item> itemList = amazonBestsellersRss.getChannel().getItem();
		        
		        List<Kindle> KindleList = new ArrayList<Kindle>();;
		        KindleList.add(AmazonApiKindle.getKindle(itemList.get(0).getAsin()));
		        KindleList.add(AmazonApiKindle.getKindle(itemList.get(1).getAsin()));
		        KindleList.add(AmazonApiKindle.getKindle(itemList.get(2).getAsin()));
		        
		        Twitter twitter = new TwitterFactory().getInstance();
		        long[] mediaIds = new long[3];
		        String tweetContent = headerTitle + "　#Kindle\n";
		        
		        int i = 0;
				for(Kindle kindle : KindleList){
			        String filePath = DownloadImage.getImagePath(kindle.getLargeImage(), kindle.getAsin());
			        UploadedMedia media = twitter.uploadMedia(new File(filePath));
			        mediaIds[i] = media.getMediaId();
		            //String shortUrl = GoogleURLShortener.getShortUrl(kindle.getDetailPageURL());
			        tweetContent = tweetContent + " ［" + String.valueOf(i+1) + "］" + cutStr(kindle.getTitle(), 20) + "\n";
					i++;
				}
				
				StatusUpdate update = new StatusUpdate(tweetContent);
				update.setMediaIds(mediaIds);
				twitter.updateStatus(update);
				kindleBO.countupTweetTop3(description);
				logger.info(headerTitle + "ツイート正常終了 \n{}", tweetContent);
	    	}catch(Exception e){
	            String message = "";
	            message = message + "エラー内容:" + "\n" + e;
	            try {
					MyMail.sendMail(headerTitle + "ツイートエラー通知", message);
				} catch (Exception e1) {
					logger.error("failed to send mail", e1);
				}
	            logger.error("Exception of TweetTop3", e);
	    	}
		}else{
			logger.info("最終ツイートから2時間経過してません。");
		}
		
    }
    
    private String cutStr(String str, int num){
    	if(str.length() <= num){
    		return str;
    	}else{
    		return str.substring(0,num) + "…";
    	}
    }
    
    private boolean isTweet(){
    	long LastTweetDate;
    	try{
        	LastTweetDate = MyTwitterApi.getLastTweetDate().getTime();
    	}catch(TwitterException e){
    		logger.error("Exception of getLastTweetDate", e);
        	LastTweetDate = new Date().getTime();;
    	}
    	long nowDate = new Date().getTime();
    	long one_hour_time = 1000 * 60 * 60;
    	long diffHour = (nowDate - LastTweetDate) / one_hour_time;
    	
    	if(diffHour >= 2){
    		return true;
    	}else{
    		return false;
    	}
    }
}
