package myclass.main;

import myclass.bo.KindleBO;
import myclass.function.AmazonApiKindle;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class KindleCollect implements Job {
    private static Logger logger = LogManager.getLogger(KindleCollect.class);
    
    private List<String> sortValueList;
    private List<String> browseNodesList;
    private List<String> powerPubdatesList;
    private KindleBO kindleBO = new KindleBO();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		exec();
	}
    
    public void exec() {
    	logger.info("==============Start KindleCollect==============");
    	try {
			collectBooks();
	    	collectKindleStore();
		} catch (Exception e) {
            logger.error("Exception of KindleCollect", e);
		}
    	logger.info("==============End KindleCollect==============");
    }

    public void collectBooks() throws Exception{
    	KindleBO kindleBO = new KindleBO();
        String searchIndex = "Books";
        String powerBinding = "Kindle版";

        sortValueList = kindleBO.getSortValue(searchIndex);
        browseNodesList = kindleBO.getBrowseNodes(searchIndex);
        powerPubdatesList = kindleBO.getPowerPubdates(searchIndex);
        for(String sortValue : sortValueList){
            for(String browseNodes : browseNodesList){
                for(String powerPubdate : powerPubdatesList){
                	kindleBO.registerKindleList(AmazonApiKindle.getKindleList(searchIndex, powerBinding, sortValue, browseNodes, powerPubdate));
                }
            }
        }
    }
    
    public void collectKindleStore() throws Exception{
        String searchIndex = "KindleStore";
        
        sortValueList = kindleBO.getSortValue(searchIndex);
        browseNodesList = kindleBO.getBrowseNodes(searchIndex);

        for(String sortValue : sortValueList){
            for(String browseNodes : browseNodesList){
            	kindleBO.registerKindleList(AmazonApiKindle.getKindleList(searchIndex, "", sortValue, browseNodes, ""));
            }
        }
    }
}
