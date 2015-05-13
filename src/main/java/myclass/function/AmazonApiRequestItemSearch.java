package myclass.function;

import java.util.HashMap;
import java.util.Map;

import myclass.bo.KindleBO;
import myclass.config.MyConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.amazon.advertising.api.sample.SignedRequestsHelper;

public class AmazonApiRequestItemSearch {
    private static Logger logger = LogManager.getLogger(AmazonApiRequestItemSearch.class);
    
	protected static final String SERVICE = "AWSECommerceService";
	protected static final String ENDPOINT = "webservices.amazon.co.jp";
	protected static final String VERSION = "2011-08-01";
	protected static final String OPERATION = "ItemSearch";
	protected static final String CONDITION = "All";
	protected static final String RESPONSEGROUP = "Large";
	protected static final int SLEEP_TIME = 1200;
	protected static final int RETRY_COUNT = 10;
	protected static final String AWS_ACCESS_KEY_ID = MyConfig.getConfig("KindleCollect.AWS_ACCESS_KEY_ID");
	protected static final String AWS_SECRET_KEY = MyConfig.getConfig("KindleCollect.AWS_SECRET_KEY");
	protected static final String ASSOCIATE_TAG = MyConfig.getConfig("KindleCollect.ASSOCIATE_TAG");
	protected static String searchIndex ="ALL";
	protected static String powerPubdate = "";
	protected static String keywords = "*";
	protected static String sort = "";
	protected static String title = "";
	protected static String powerBinding = "";
	protected static String browseNode = "";
	protected static Integer pageNum = 1;

    @SuppressWarnings("unused")
	private static AmazonApiRequestItemSearch instance = new AmazonApiRequestItemSearch();
	
    private static Map<String, String> createParams(){
        Map<String, String> params = new HashMap<String, String>();

        params.put("Service", SERVICE);
        params.put("Version", VERSION);
        params.put("Operation", OPERATION);
        params.put("Condition", CONDITION);
        params.put("ResponseGroup", RESPONSEGROUP);
        params.put("AssociateTag", ASSOCIATE_TAG);
        params.put("SearchIndex", searchIndex);
        params.put("ItemPage", pageNum.toString());
        params.put("Keywords", keywords);
        params.put("BrowseNode", browseNode);
        if(sort != ""){
            params.put("Sort", sort);
        }
        if(title != ""){
            params.put("Title", title);
        }
        if(getPowerStr() != ""){
            params.put("Power", getPowerStr());
        }

        return params;
    }
    
    private static String createRequestUrl(){
        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return helper.sign(createParams());
    }

    public static String getResponseXml() throws Exception{
        String requestUrl = createRequestUrl();
        //KindleBO.requestLog(requestUrl);
        return MyHttpGet.getResponseXml(requestUrl);
    }
    
    public static String getPowerStr(){
        String powerStr;

        if(powerBinding == ""){
            return "";
        }

        powerStr = "binding:" + powerBinding;
        if(powerPubdate != ""){
            powerStr = powerStr + " and pubdate:" + powerPubdate;
        }

        return powerStr;
    }

}
