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
    private KindleBO kindleBO;

	public void execute(JobExecutionContext context) throws JobExecutionException {
    	logger.info("==============Start KindleCollectFromHtml==============");
    	kindleBO = new KindleBO();
    	try {
			exec();
		} catch (Exception e) {
            logger.error("Exception of KindleCollectFromHtml", e);
		}
    	kindleBO = null;
    	logger.info("==============End KindleCollectFromHtml==============");
	}

    public void exec() throws Exception{
    	List<String> asinList = AmazonApiKindle.getAsinListFromHtml();
    	logger.debug("asinList {}", asinList);
    	List<Kindle> newKindleList = getNewKindleListFromAsinList(asinList);
    	logger.debug("newKindleList {}", newKindleList);
    	kindleBO.registerKindleList(newKindleList);
    	logger.info("{}件の登録が完了しました。", newKindleList.size());
    	asinList = null;
    	newKindleList = null;
    }
    
    private List<Kindle> getNewKindleListFromAsinList(List<String> asinList) {
    	List<Kindle> kindleList = new ArrayList<Kindle>();
    	for(String asin : asinList){
    		if(!kindleBO.isExist(asin)){
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
