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

	public void execute(JobExecutionContext context) throws JobExecutionException {
		String[] args = null;
		KindleUpdate.main(args);
	}
    
    public static void main(String[] args) {
    	logger.info("==============Start KindleUpdate==============");
    	try {
    		exec();
		} catch (Exception e) {
            logger.error("Exception of KindleUpdate", e);
		}
    	logger.info("==============End KindleUpdate==============");
    }

    public static void exec(){
    	List<String> asinList = KindleBO.getOldAsinList(UPDATE_NUM);
    	logger.debug("asinList : {}", asinList);
    	for(String asin : asinList){
    		updateInfo(asin);
    	}
    }

	private static void updateInfo(String asin){
		Kindle kindle;
		try {
			kindle = AmazonApiKindle.getKindle(asin);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if(kindle != null){
			KindleBO.registerKindle(kindle);
		}else{
			KindleBO.deleteKindle(asin);
			logger.info("delete : {}", asin);
		}
	}
}
