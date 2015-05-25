package myclass.main;

import myclass.bo.KindleBO;
import myclass.function.AmazonApiKindle;
import myclass.model.Kindle;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class KindleUpdate implements Job {
    private static Logger logger = LogManager.getLogger(KindleUpdate.class);
    private static final int UPDATE_NUM = 500;
    private KindleBO kindleBO;

	public void execute(JobExecutionContext context) throws JobExecutionException {
    	logger.info("==============Start KindleUpdate==============");
    	kindleBO = new KindleBO();
    	try {
    		exec();
		} catch (Exception e) {
            logger.error("Exception of KindleUpdate", e);
		}
    	kindleBO = null;
    	logger.info("==============End KindleUpdate==============");
	}

    public void exec(){
    	List<String> asinList = kindleBO.getOldAsinList(UPDATE_NUM);
    	logger.debug("asinList : {}", asinList);
    	for(String asin : asinList){
    		updateInfo(asin);
    	}
    }

	private void updateInfo(String asin){
		Kindle kindle;
		try {
			kindle = AmazonApiKindle.getKindle(asin);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if(kindle != null){
			kindleBO.registerKindle(kindle);
		}else{
			kindleBO.deleteKindle(asin);
			logger.info("delete : {}", asin);
		}
	}
}
