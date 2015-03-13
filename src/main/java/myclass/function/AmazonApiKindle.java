package myclass.function;

import myclass.model.Kindle;

import java.util.List;
import java.util.ArrayList;

import com.ECS.client.jax.ItemSearchResponse;
import com.ECS.client.jax.Item;

import java.io.StringReader;

import javax.xml.bind.JAXB;

@SuppressWarnings("restriction")
public class AmazonApiKindle {
    @SuppressWarnings("unused")
	private static AmazonApiKindle instance = new AmazonApiKindle();
    
    public AmazonApiKindle(){
    	init();
    }
    
    private static void init(){
    	AmazonApiRequest.searchIndex ="ALL";
    	AmazonApiRequest.powerPubdate = "";
    	AmazonApiRequest.keywords = "*";
    	AmazonApiRequest.sort = "";
    	AmazonApiRequest.title = "";
    	AmazonApiRequest.powerBinding = "";
    	AmazonApiRequest.browseNode = "";
    	AmazonApiRequest.pageNum = 1;
    }

    public static List<Kindle> getKindleList() throws Exception{
        List<Kindle> kindleListAll = new ArrayList<Kindle>();

        List<Kindle> kindleListOneSet;
        for(int i = 1; i <= 10; i++){
        	AmazonApiRequest.pageNum = i;
        	kindleListOneSet = cleateKindleListOneSet();
            kindleListAll.addAll(kindleListOneSet);
            if(kindleListOneSet.size() != 10){
                break;
            }
        }
        return kindleListAll;
    }

    public static List<Kindle> getKindleList(String searchIndex, String powerBinding, String sort, String browseNode, String powerPubdate) throws Exception{
    	init();
    	AmazonApiRequest.searchIndex ="Books";
    	AmazonApiRequest.powerBinding = powerBinding;
    	AmazonApiRequest.sort = sort;
    	AmazonApiRequest.browseNode = browseNode;
    	AmazonApiRequest.powerPubdate = powerPubdate;
        return getKindleList();
    }

    private static List<Kindle> cleateKindleListOneSet() throws Exception {
        List<Kindle> kindleList = new ArrayList<Kindle>();
        
        String xml = AmazonApiRequest.getResponseXml();
		ItemSearchResponse itemSearchResponse = JAXB.unmarshal(new StringReader(xml), ItemSearchResponse.class);

        List<Item> itemList = itemSearchResponse.getItems().get(0).getItem();
        for (Item item : itemList){
            kindleList.add(new Kindle(item));
        }
        return kindleList;
    }
    
    public void setSearchIndex(String searchIndex) {
    	AmazonApiRequest.searchIndex = searchIndex;
	}

	public void setPowerPubdate(String powerPubdate) {
		AmazonApiRequest.powerPubdate = powerPubdate;
	}

	public void setKeywords(String keywords) {
		AmazonApiRequest.keywords = keywords;
	}

	public void setSort(String sort) {
		AmazonApiRequest.sort = sort;
	}

	public void setTitle(String title) {
		AmazonApiRequest.title = title;
	}

	public void setPowerBinding(String powerBinding) {
		AmazonApiRequest.powerBinding = powerBinding;
	}

	public void setBrowseNode(String browseNode) {
		AmazonApiRequest.browseNode = browseNode;
	}
}
