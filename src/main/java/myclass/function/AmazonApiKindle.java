package myclass.function;

import myclass.model.Kindle;
import myclass.bo.KindleBO;

import com.amazon.advertising.api.sample.SignedRequestsHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.ECS.client.jax.ItemSearchResponse;
import com.ECS.client.jax.Item;

import java.io.StringReader;

import javax.xml.bind.JAXB;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import java.io.StringWriter;

import javax.xml.transform.TransformerException;

import java.lang.Thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("restriction")
public class AmazonApiKindle {
    private static final String SERVICE = "AWSECommerceService";
    private static final String ENDPOINT = "webservices.amazon.co.jp";
    private static final String VERSION = "2011-08-01";
    private static final String OPERATION = "ItemSearch";
    private static final String CONDITION = "All";
    private static final String RESPONSEGROUP = "Large";
    private static final int SLEEP_TIME = 1200;
    private static final int RETRY_COUNT = 10;
    private String awsAccessKeyId;
    private String awsSecretKey;
    private String associateTag;
    private String searchIndex = "Books";
    private String powerPubdate = "";
    private String keywords = "*";
    private String sort = "";
    private String title = "";
    private String powerBinding = "Kindle版";
    private String browseNode = "466280"; //コミック・ラノベ・BL
    private Integer pageNum;
    private static Logger logger = LogManager.getLogger(AmazonApiKindle.class);

    public AmazonApiKindle(String id, String key, String tag){
        awsAccessKeyId = id;
        awsSecretKey = key;
        associateTag = tag;
    }

    public List<Kindle> getKindleList() throws Exception{
        List<Kindle> kindleListAll = new ArrayList<Kindle>();

        List<Kindle> kindleListTemp;
        for(int i = 1; i <= 10; i++){
            pageNum = i;
            kindleListTemp = cleateKindleList();
            kindleListAll.addAll(kindleListTemp);
            if(kindleListTemp.size() != 10){
                break;
            }
            
            Thread.sleep(SLEEP_TIME);
        }
        return kindleListAll;
    }

    private Map<String, String> createParams(){
        Map<String, String> params = new HashMap<String, String>();

        params.put("Service", SERVICE);
        params.put("Version", VERSION);
        params.put("Operation", OPERATION);
        params.put("Condition", CONDITION);
        params.put("ResponseGroup", RESPONSEGROUP);
        params.put("SearchIndex", searchIndex);
        params.put("AssociateTag", associateTag);
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

    public void setSearchIndex(String searchIndex){
        this.searchIndex = searchIndex;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setSort(String sort){
        this.sort = sort;
    }

    public void setPowerBinding(String powerBinding){
        this.powerBinding = powerBinding;
    }

    public void setBrowseNode(String browseNode){
        this.browseNode = browseNode;
    }

    public void setPowerPubdate(String powerPubdate){
        this.powerPubdate = powerPubdate;
    }

    public void setKeywords(String keywords){
        this.keywords = keywords;
    }

    public String getPowerStr(){
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

    private String createRequestUrl(){
        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, awsAccessKeyId, awsSecretKey);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return helper.sign(createParams());
    }

    private String getResponseXml() throws Exception{
        String responseXml = null;
        String requestUrl = createRequestUrl();
        
        int id = KindleBO.requestLog(requestUrl);
        logger.info("REQUEST LOG Number = {}", id);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        for(int i = 0; i < RETRY_COUNT; i++){
            try{
                doc = db.parse(requestUrl);
                break;
            }catch(java.io.IOException e){
                logger.info("503 ERROR RETRY : {}", id, i);
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

    private void transform(Source source, Result result) throws TransformerException{
       TransformerFactory tff = TransformerFactory.newInstance();
       Transformer tf = tff.newTransformer();
       tf.transform(source, result);
    }

    private List<Kindle> cleateKindleList() throws Exception {
        List<Kindle> kindleList = new ArrayList<Kindle>();
        
        String xml = getResponseXml();
		ItemSearchResponse itemSearchResponse = JAXB.unmarshal(new StringReader(xml), ItemSearchResponse.class);

        List<Item> itemList = itemSearchResponse.getItems().get(0).getItem();
        for (Item item : itemList){
            kindleList.add(new Kindle(item));
        }
        return kindleList;
    }
}
