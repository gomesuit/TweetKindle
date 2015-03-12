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

    public List<Kindle> getKindleList() throws Exception{
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

    private List<Kindle> cleateKindleListOneSet() throws Exception {
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
