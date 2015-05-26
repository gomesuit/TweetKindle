package myclass.main;

import java.util.List;
import java.util.Map;

import myclass.bo.KindleBO;
import myclass.function.TitleConvert;
import myclass.model.KindleMyInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class KindleTitleConvertBatch implements Job {
    private static Logger logger = LogManager.getLogger(KindleTitleConvertBatch.class);
    private KindleBO kindleBO = new KindleBO();

	public void execute(JobExecutionContext context) throws JobExecutionException {
    	logger.info("==============Start KindleTitleConvertBatch==============");
    	kindleBO = new KindleBO();
    	try {
    		exec();
		} catch (Exception e) {
            logger.error("Exception of KindleTitleConvertBatch", e);
		}
    	kindleBO = null;
    	logger.info("==============End KindleTitleConvertBatch==============");
	}

	public void exec() throws Exception{
		List<Map<String, String>> kindleList = new KindleBO().getAllKindleList();
    	
    	for(Map<String, String> kindle : kindleList){
    		KindleMyInfo kindleMyinfo = new TitleConvert().KindleToKindleMyInfo(kindle.get("asin"), kindle.get("title"));
    		kindleBO.registerKindleMyinfo(kindleMyinfo);
    	}
    	kindleList = null;
    }
    
    
}
