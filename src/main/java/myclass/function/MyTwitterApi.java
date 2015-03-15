package myclass.function;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class MyTwitterApi {
    @SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(MyTwitterApi.class);

    @SuppressWarnings("unused")
	private static MyTwitterApi instance = new MyTwitterApi();
    
	public static Date getLastTweetDate() throws TwitterException {
	    Twitter twitter = TwitterFactory.getSingleton();
	    List<Status> statuses = twitter.getUserTimeline();
        return statuses.get(0).getCreatedAt();
	}
}
