package myclass.function;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import myclass.bo.KindleBO;
import myclass.config.MyConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import com.amazon.advertising.api.sample.SignedRequestsHelper;


public class AmazonApiRequest {
    private static Logger logger = LogManager.getLogger(AmazonApiRequest.class);
    
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
	private static AmazonApiRequest instance = new AmazonApiRequest();
	
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
        String responseXml = null;
        String requestUrl = createRequestUrl();
        
        int id = KindleBO.requestLog(requestUrl);
        //logger.info("REQUEST LOG Number = {}", id);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        for(int i = 0; i < RETRY_COUNT; i++){
            try{
                doc = db.parse(requestUrl);
                break;
            }catch(java.io.IOException e){
                //logger.info("503 ERROR RETRY : {}", id, i);
                Thread.sleep(SLEEP_TIME);
            }
        }
        DOMSource source = new DOMSource(doc);
        StringWriter swriter = new StringWriter();
        StreamResult result = new StreamResult(swriter);
        transform(source, result);
        responseXml = swriter.toString();
        
        return responseXml;
    }

    private static void transform(Source source, Result result) throws TransformerException{
       TransformerFactory tff = TransformerFactory.newInstance();
       Transformer tf = tff.newTransformer();
       tf.transform(source, result);
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

	public String getSearchIndex() {
		return searchIndex;
	}


	public void setSearchIndex(String searchIndex) {
		AmazonApiRequest.searchIndex = searchIndex;
	}


	public String getPowerPubdate() {
		return powerPubdate;
	}


	public void setPowerPubdate(String powerPubdate) {
		AmazonApiRequest.powerPubdate = powerPubdate;
	}


	public String getKeywords() {
		return keywords;
	}


	public void setKeywords(String keywords) {
		AmazonApiRequest.keywords = keywords;
	}


	public String getSort() {
		return sort;
	}


	public void setSort(String sort) {
		AmazonApiRequest.sort = sort;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		AmazonApiRequest.title = title;
	}


	public String getPowerBinding() {
		return powerBinding;
	}


	public void setPowerBinding(String powerBinding) {
		AmazonApiRequest.powerBinding = powerBinding;
	}


	public String getBrowseNode() {
		return browseNode;
	}


	public void setBrowseNode(String browseNode) {
		AmazonApiRequest.browseNode = browseNode;
	}


	public Integer getPageNum() {
		return pageNum;
	}


	public void setPageNum(Integer pageNum) {
		AmazonApiRequest.pageNum = pageNum;
	}

}
