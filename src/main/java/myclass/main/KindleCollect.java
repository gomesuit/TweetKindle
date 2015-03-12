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
        AmazonApiKindle amazonApiKindle = new AmazonApiKindle();
        String searchIndex = "Books";
        amazonApiKindle.setSearchIndex(searchIndex);
        amazonApiKindle.setPowerBinding("Kindle版");

        sortValueList = KindleBO.getSortValue(searchIndex);
        browseNodesList = KindleBO.getBrowseNodes(searchIndex);
        powerPubdatesList = KindleBO.getPowerPubdates(searchIndex);

        for(String sortValue : sortValueList){
            amazonApiKindle.setSort(sortValue);
            
            for(String browseNodes : browseNodesList){
                amazonApiKindle.setBrowseNode(browseNodes);
                
                KindleBO.registerKindleList(amazonApiKindle.getKindleList());
            }
        }

        amazonApiKindle.setSort("salesrank");
        amazonApiKindle.setBrowseNode("2278488051");
        for(String powerPubdate : powerPubdatesList){
            amazonApiKindle.setPowerPubdate(powerPubdate);
            
            KindleBO.registerKindleList(amazonApiKindle.getKindleList());
        }
    }
    public static void collectKindleStore() throws Exception{
    	AmazonApiKindle amazonApiKindle = new AmazonApiKindle();
        String searchIndex = "KindleStore";
        amazonApiKindle.setSearchIndex(searchIndex);

        sortValueList = KindleBO.getSortValue(searchIndex);
        browseNodesList = KindleBO.getBrowseNodes(searchIndex);

        for(String sortValue : sortValueList){
            amazonApiKindle.setSort(sortValue);
            
            for(String browseNodes : browseNodesList){
                amazonApiKindle.setBrowseNode(browseNodes);
                
                KindleBO.registerKindleList(amazonApiKindle.getKindleList());
            }
        }
    }
}
