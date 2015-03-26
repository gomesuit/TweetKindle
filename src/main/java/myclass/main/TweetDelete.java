package myclass.main;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TweetDelete {
	private static Logger logger = LogManager.getLogger(TweetDelete.class);

	public static void main(String[] args) throws TwitterException {
	    Twitter twitter = TwitterFactory.getSingleton();
	    List<Status> statuses = twitter.getUserTimeline();
	    for(Status status : statuses){
		    logger.info("{}",status.getCreatedAt(), status.getId(), status.getText());
	    }
	}

}
