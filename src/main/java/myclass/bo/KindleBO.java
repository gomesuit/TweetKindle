package myclass.bo;

import myclass.model.Kindle;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import myclass.config.MyConfig;
import myclass.data.KindleMapper;

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

    @SuppressWarnings("unused")
	private static KindleBO instance = new KindleBO();

    private KindleBO(){
        try{
            inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session = sqlSessionFactory.openSession(true);
            kindleMapper = session.getMapper(KindleMapper.class);
        }catch(java.io.IOException e){
        	logger.error("create instance error {}", e);
        }
    }

    public static void registerKindle(Kindle kindle){
        if(kindleMapper.countKindle(kindle.getAsin()) == 0){
            kindleMapper.insertKindle(kindle);
            kindleMapper.insertKindleRegist(kindle.getAsin());
        }else{
            kindleMapper.updateKindle(kindle);
            kindleMapper.updateKindleRegist(kindle.getAsin());
        }
    }

    public static void registerKindleList(List<Kindle> kindleList){
        for (Kindle kindle : kindleList){
        	//logger.info(kindle.getTitle());
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
        return kindleMapper.selectKindleShinchaku();
    }

    public static Map<String,String> getKindle(String asin){
        return kindleMapper.selectKindle(asin);
    }

    public static void updateTweetShinchaku(String asin){
        kindleMapper.updateTweetShinchaku(asin);
    }

    public static Map<String,String> getKindleTodaySale(){
        return kindleMapper.selectKindleTodaySales();
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

    //private static String getDateTime(){
    //    Calendar calendar = Calendar.getInstance();
    //    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    //    sdf.setTimeZone(TimeZone.getTimeZone("JST"));
    //    return sdf.format(calendar.getTime());
    //}
}
