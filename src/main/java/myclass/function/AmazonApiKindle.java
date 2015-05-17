package myclass.function;

import myclass.model.Kindle;

import java.util.List;
import java.util.ArrayList;

import com.ECS.client.jax.ItemLookupResponse;
import com.ECS.client.jax.ItemSearchResponse;
import com.ECS.client.jax.Item;

import java.io.StringReader;

import javax.xml.bind.JAXB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("restriction")
public class AmazonApiKindle {
    private static Logger logger = LogManager.getLogger(AmazonApiKindle.class);
    @SuppressWarnings("unused")
	private static AmazonApiKindle instance = new AmazonApiKindle();
    
    public AmazonApiKindle(){
    	initItemSearch();
    	initItemLookup();
    }

    private static void initItemSearch(){
    	AmazonApiRequestItemSearch.searchIndex = "ALL";
    	AmazonApiRequestItemSearch.powerPubdate = "";
    	AmazonApiRequestItemSearch.keywords = "*";
    	AmazonApiRequestItemSearch.sort = "";
    	AmazonApiRequestItemSearch.title = "";
    	AmazonApiRequestItemSearch.powerBinding = "";
    	AmazonApiRequestItemSearch.browseNode = "";
    	AmazonApiRequestItemSearch.pageNum = 1;
    }
    private static void initItemLookup(){
    	AmazonApiRequestItemLookup.ItemId = "";
    }
    
    public static Kindle getKindle(String asin) throws Exception{
        AmazonApiRequestItemLookup.ItemId = asin;
        return lookupKindle();
    }
    
    private static Kindle lookupKindle() throws Exception {
        String xml = AmazonApiRequestItemLookup.getResponseXml();
		ItemLookupResponse ItemLookupResponse = JAXB.unmarshal(new StringReader(xml), ItemLookupResponse.class);
		List<Item> itemList = ItemLookupResponse.getItems().get(0).getItem();
		if(itemList.size() == 0){
			return null;
		}else{
	        Item item = itemList.get(0);
	        return new Kindle(item);
		}
    }

    public static List<Kindle> getKindleList() throws Exception{
        List<Kindle> kindleListAll = new ArrayList<Kindle>();

        List<Kindle> kindleListOneSet;
        for(int i = 1; i <= 10; i++){
        	AmazonApiRequestItemSearch.pageNum = i;
        	kindleListOneSet = cleateKindleListOneSet();
            kindleListAll.addAll(kindleListOneSet);
            if(kindleListOneSet.size() != 10){
                break;
            }
        }
        return kindleListAll;
    }

    public static List<Kindle> getKindleListOneSet(Integer pageNum) throws Exception{
        List<Kindle> kindleListOneSet;
    	AmazonApiRequestItemSearch.pageNum = pageNum;
    	kindleListOneSet = cleateKindleListOneSet();
        return kindleListOneSet;
    }

    public static List<Kindle> getKindleList(String searchIndex, String powerBinding, String sort, String browseNode, String powerPubdate) throws Exception{
    	initItemSearch();
    	AmazonApiRequestItemSearch.searchIndex =searchIndex;
    	AmazonApiRequestItemSearch.powerBinding = powerBinding;
    	AmazonApiRequestItemSearch.sort = sort;
    	AmazonApiRequestItemSearch.browseNode = browseNode;
    	AmazonApiRequestItemSearch.powerPubdate = powerPubdate;
        logger.info("getKindleList: {}", searchIndex, powerBinding, sort, browseNode, powerPubdate);
        return getKindleList();
    }

    public static List<Kindle> getKindleListTop3(String searchIndex, String powerBinding, String sort, String browseNode, String powerPubdate) throws Exception{
    	initItemSearch();
    	AmazonApiRequestItemSearch.searchIndex =searchIndex;
    	AmazonApiRequestItemSearch.powerBinding = powerBinding;
    	AmazonApiRequestItemSearch.sort = sort;
    	AmazonApiRequestItemSearch.browseNode = browseNode;
    	AmazonApiRequestItemSearch.powerPubdate = powerPubdate;
        logger.info("getKindleListTop3: {}", searchIndex, powerBinding, sort, browseNode, powerPubdate);
        return getKindleListOneSet(1);
    }

    private static List<Kindle> cleateKindleListOneSet() throws Exception {
        List<Kindle> kindleList = new ArrayList<Kindle>();
        
        String xml = AmazonApiRequestItemSearch.getResponseXml();
		ItemSearchResponse itemSearchResponse = JAXB.unmarshal(new StringReader(xml), ItemSearchResponse.class);

        List<Item> itemList = itemSearchResponse.getItems().get(0).getItem();
        for (Item item : itemList){
            kindleList.add(new Kindle(item));
        }
        return kindleList;
    }
    
}
