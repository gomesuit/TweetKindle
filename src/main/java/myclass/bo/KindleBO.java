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

    @SuppressWarnings("unused")
	private static KindleBO instance = new KindleBO();

    private KindleBO(){
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

    public static void registerKindle(Kindle kindle){
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
        registerKindleMyinfo(TitleConvert.KindleToKindleMyInfo(kindle));
    }

    public static void registerKindleList(List<Kindle> kindleList){
        for (Kindle kindle : kindleList){
            registerKindle(kindle);
        }
    }

    public static List<String> getSortValue(String searchIndex){
        return kindleMapper.selectSortValue(searchIndex);
    }

    public static List<String> getBrowseNodes(String searchIndex){
        return kindleMapper.selectBrowseNodes(searchIndex);
    }
    
    public static List<String> getPowerPubdates(String searchIndex){
        return kindleMapper.selectPowerPubdates(searchIndex);
    }

    public static List<Kindle> getTodayKindleList(String date){
        return kindleMapper.selectTodayKindleList(date);
    }

    public static Map<String,String> getKindleShinchaku(){
        return kindleMapper.selectKindleShinchaku(getExclusion());
    }

    public static Map<String,String> getKindle(String asin){
        return kindleMapper.selectKindle(asin);
    }

    public static void updateTweetShinchaku(String asin){
        kindleMapper.updateTweetShinchaku(asin);
    }

    public static Map<String,String> getKindleTodaySale(){
        return kindleMapper.selectKindleTodaySales(getExclusion());
    }

    public static void updateTweetTodaySales(String asin){
        kindleMapper.updateTweetTodaySales(asin);
    }

    public static int requestLog(String request){
        Map<String,String> map = new HashMap<String,String>();

        map.put("request", request);
        kindleMapper.insertRequestLog(map);
        return Integer.parseInt(map.get("id"));
    }

    public static Map<String,String> getMinTweetTop3(){
        return kindleMapper.selectMinTweetTop3();
    }

    public static void countupTweetTop3(String description){
        kindleMapper.countupTweetTop3(description);
    }

    public static List<String> getExclusion(){
        return kindleMapper.selectExclusion();
    }

    public static void registerNoImage(String asin){
        kindleMapper.insertNoImage(asin);
    }

    public static String getNoImage(){
        return kindleMapper.selectNoImage();
    }

    public static void deleteNoImage(String asin){
        kindleMapper.deleteNoImage(asin);
    }

    public static List<String> getOldAsinList(int limit){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("limit", limit);
        map.put("yesterday", getYesterday());
        return kindleMapper.selectOldAsinList(map);
    }
    
    public static void deleteKindle(String asin){
    	kindleMapper.deleteKindleRegist(asin);
    }

    private static Date getYesterday(){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.add(Calendar.DAY_OF_MONTH, -1);
    	
    	return calendar.getTime();
    }

    public static boolean isExist(String asin){
        if(kindleMapper.countKindle(asin) == 0){
        	return false;
        }else{
        	return true;
        }
    }

    public static List<Kindle> getAllKindleList(){
    	return kindleMapper.selectAllKindleList();
    }

    public static void registerKindleMyinfo(KindleMyInfo kindleMyinfo){
        if(kindleMapper.countKindleMyInfo(kindleMyinfo.getAsin()) == 0){
            kindleMapper.insertKindleMyInfo(kindleMyinfo);
            registerTag(kindleMyinfo.getAsin(), kindleMyinfo.getSimpleTitle());
            registerTag(kindleMyinfo.getAsin(), kindleMyinfo.getLabel());
        }else{
            kindleMapper.updateKindleMyInfo(kindleMyinfo);
        }
    }
    
    private static void registerTag(String asin, String name){
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
