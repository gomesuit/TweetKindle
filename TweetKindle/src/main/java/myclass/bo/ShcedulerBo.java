package myclass.bo;

import java.util.List;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import myclass.config.MyConfig;
import myclass.data.SchedulerMapper;
import myclass.model.MyScheduler;

public class ShcedulerBo {
	private static Logger logger = LogManager.getLogger(ShcedulerBo.class);
    private static final String MYBATIS_CONFIG = MyConfig.getConfig("ShcedulerBo.mybatisConfig");
    private static InputStream inputStream;
    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSession session;
    private static SchedulerMapper schedulerMapper;

    @SuppressWarnings("unused")
	private static ShcedulerBo instance = new ShcedulerBo();

    private ShcedulerBo(){
        try{
            inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session = sqlSessionFactory.openSession(true);
            schedulerMapper = session.getMapper(SchedulerMapper.class);
        }catch(java.io.IOException e){
        	logger.error("create instance error {}", e);
        }
    }

    public static List<MyScheduler> getSchedulerList(){
        return schedulerMapper.selectSchedulerList();
    }
}
