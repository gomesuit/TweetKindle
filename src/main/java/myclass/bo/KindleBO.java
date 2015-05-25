package myclass.bo;

import myclass.model.Kindle;
import myclass.model.KindleMyInfo;
import myclass.model.Tag;
import myclass.model.TagMap;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import myclass.config.MyConfig;
import myclass.data.KindleMapper;
import myclass.data.TagMapper;
import myclass.function.TitleConvert;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KindleBO {
	private static Logger logger = LogManager.getLogger(KindleBO.class);
    private static final String MYBATIS_CONFIG = MyConfig.getConfig("KindleBO.mybatisConfig");
    private static InputStream inputStream;
    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSession session;
    private static KindleMapper kindleMapper;
    private static TagMapper tagMapper;

    public KindleBO(){
        try{
            inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session = sqlSessionFactory.openSession(true);
            kindleMapper = session.getMapper(KindleMapper.class);
            tagMapper = session.getMapper(TagMapper.class);
        }catch(java.io.IOException e){
        	logger.error("create instance error {}", e);
        }
    }

    public void registerKindle(Kindle kindle){
        if(kindleMapper.countKindle(kindle.getAsin()) == 0){
            kindleMapper.insertKindle(kindle);
            registerTag(kindle.getAsin(), kindle.getAuthor());
        }else{
            kindleMapper.updateKindle(kindle);
        }
        if(kindleMapper.countKindleRegist(kindle.getAsin()) == 0){
        	kindleMapper.insertKindleRegist(kindle.getAsin());
        }else{
        	kindleMapper.updateKindleRegist(kindle.getAsin());
        }
        registerKindleMyinfo(new TitleConvert().KindleToKindleMyInfo(kindle));
    }

    public void registerKindleList(List<Kindle> kindleList){
        for (Kindle kindle : kindleList){
            registerKindle(kindle);
        }
    }

    public List<String> getSortValue(String searchIndex){
        return kindleMapper.selectSortValue(searchIndex);
    }

    public List<String> getBrowseNodes(String searchIndex){
        return kindleMapper.selectBrowseNodes(searchIndex);
    }
    
    public List<String> getPowerPubdates(String searchIndex){
        return kindleMapper.selectPowerPubdates(searchIndex);
    }

    public List<Kindle> getTodayKindleList(String date){
        return kindleMapper.selectTodayKindleList(date);
    }

    public Map<String,String> getKindleShinchaku(){
        return kindleMapper.selectKindleShinchaku(getExclusion());
    }

    public Map<String,String> getKindle(String asin){
        return kindleMapper.selectKindle(asin);
    }

    public void updateTweetShinchaku(String asin){
        kindleMapper.updateTweetShinchaku(asin);
    }

    public Map<String,String> getKindleTodaySale(){
        return kindleMapper.selectKindleTodaySales(getExclusion());
    }

    public void updateTweetTodaySales(String asin){
        kindleMapper.updateTweetTodaySales(asin);
    }

    public int requestLog(String request){
        Map<String,String> map = new HashMap<String,String>();

        map.put("request", request);
        kindleMapper.insertRequestLog(map);
        return Integer.parseInt(map.get("id"));
    }

    public Map<String,String> getMinTweetTop3(){
        return kindleMapper.selectMinTweetTop3();
    }

    public void countupTweetTop3(String description){
        kindleMapper.countupTweetTop3(description);
    }

    public List<String> getExclusion(){
        return kindleMapper.selectExclusion();
    }

    public void registerNoImage(String asin){
        kindleMapper.insertNoImage(asin);
    }

    public String getNoImage(){
        return kindleMapper.selectNoImage();
    }

    public void deleteNoImage(String asin){
        kindleMapper.deleteNoImage(asin);
    }

    public List<String> getOldAsinList(int limit){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("limit", limit);
        map.put("yesterday", getYesterday());
        return kindleMapper.selectOldAsinList(map);
    }
    
    public void deleteKindle(String asin){
    	kindleMapper.deleteKindleRegist(asin);
    }

    private Date getYesterday(){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.add(Calendar.DAY_OF_MONTH, -1);
    	
    	return calendar.getTime();
    }

    public boolean isExist(String asin){
        if(kindleMapper.countKindle(asin) == 0){
        	return false;
        }else{
        	return true;
        }
    }

    public List<Kindle> getAllKindleList(){
    	return kindleMapper.selectAllKindleList();
    }

    public void registerKindleMyinfo(KindleMyInfo kindleMyinfo){
        if(kindleMapper.countKindleMyInfo(kindleMyinfo.getAsin()) == 0){
            kindleMapper.insertKindleMyInfo(kindleMyinfo);
            registerTag(kindleMyinfo.getAsin(), kindleMyinfo.getSimpleTitle());
            registerTag(kindleMyinfo.getAsin(), kindleMyinfo.getLabel());
        }else{
            kindleMapper.updateKindleMyInfo(kindleMyinfo);
        }
    }
    
    private void registerTag(String asin, String name){
    	if(name == null){
    		return;
    	}
    	if(name.length() > 50){
    		return;
    	}
    	
    	Tag tag = tagMapper.selectTagByName(name);
    	if(tag == null){
    		tag = new Tag();
    		tag.setName(name);
    		tagMapper.insertTag(tag);
    		TagMap tagMap = new TagMap();
        	tagMap.setAsin(asin);
        	tagMap.setTagId(tag.getId());
        	tagMapper.insertTagMap(tagMap);
    	}else{
    		TagMap tagMap = new TagMap();
        	tagMap.setAsin(asin);
        	tagMap.setTagId(tag.getId());
        	tagMap = tagMapper.selectTagMap(tagMap);
        	if(tagMap == null){
        		tagMap = new TagMap();
            	tagMap.setAsin(asin);
            	tagMap.setTagId(tag.getId());
            	tagMapper.insertTagMap(tagMap);
        	}
    	}    	
    }
}
