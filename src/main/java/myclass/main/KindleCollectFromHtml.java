package myclass.main;

import java.util.ArrayList;
import java.util.List;

import myclass.bo.KindleBO;
import myclass.function.AmazonApiKindle;
import myclass.model.Kindle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class KindleCollectFromHtml implements Job {
    private static Logger logger = LogManager.getLogger(KindleCollectFromHtml.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		String[] args = null;
		KindleCollectFromHtml.main(args);
	}
    
    public static void main(String[] args) {
    	logger.info("==============Start KindleCollectFromHtml==============");
    	try {
			exec();
		} catch (Exception e) {
            logger.error("Exception of KindleCollectFromHtml", e);
		}
    	logger.info("==============End KindleCollectFromHtml==============");
    }

    public static void exec() throws Exception{
    	List<String> asinList = AmazonApiKindle.getAsinListFromHtml();
    	logger.debug("asinList {}", asinList);
    	List<Kindle> newKindleList = getNewKindleListFromAsinList(asinList);
    	logger.debug("newKindleList {}", newKindleList);
    	KindleBO.registerKindleList(newKindleList);
    	logger.info("{}件の登録が完了しました。", newKindleList.size());
    }
    
    private static List<Kindle> getNewKindleListFromAsinList(List<String> asinList) {
    	List<Kindle> kindleList = new ArrayList<Kindle>();
    	for(String asin : asinList){
    		if(!KindleBO.isExist(asin)){
    			try {
					kindleList.add(AmazonApiKindle.getKindle(asin));
				} catch (Exception e) {
					logger.error(e);
				}
    		}
    	}
		return kindleList;
    }
}
