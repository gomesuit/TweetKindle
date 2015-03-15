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
    
    private static List<String> sortValueList;
    private static List<String> browseNodesList;
    private static List<String> powerPubdatesList;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		String[] args = null;
		KindleCollect.main(args);
	}
    
    public static void main(String[] args) {
    	try {
			collectBooks();
	    	collectKindleStore();
		} catch (Exception e) {
            logger.error("Exception of KindleCollect", e);
		}
    }

    public static void collectBooks() throws Exception{
        String searchIndex = "Books";
        String powerBinding = "Kindle版";

        sortValueList = KindleBO.getSortValue(searchIndex);
        browseNodesList = KindleBO.getBrowseNodes(searchIndex);
        powerPubdatesList = KindleBO.getPowerPubdates(searchIndex);
        for(String sortValue : sortValueList){
            for(String browseNodes : browseNodesList){
                for(String powerPubdate : powerPubdatesList){
                    KindleBO.registerKindleList(AmazonApiKindle.getKindleList(searchIndex, powerBinding, sortValue, browseNodes, powerPubdate));
                }
            }
        }
    }
    
    public static void collectKindleStore() throws Exception{
        String searchIndex = "KindleStore";
        
        sortValueList = KindleBO.getSortValue(searchIndex);
        browseNodesList = KindleBO.getBrowseNodes(searchIndex);

        for(String sortValue : sortValueList){
            for(String browseNodes : browseNodesList){
                KindleBO.registerKindleList(AmazonApiKindle.getKindleList(searchIndex, "", sortValue, browseNodes, ""));
            }
        }
    }
}
