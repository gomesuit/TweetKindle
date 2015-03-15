package myclass.data;

import myclass.model.Kindle;
import java.util.List;
import java.util.Map;

public interface KindleMapper {
  int countKindle(String asin);
  void insertKindle(Kindle kindle);
  void updateKindle(Kindle kindle);
  List<String> selectSortValue(String searchIndex);
  List<String> selectBrowseNodes(String searchIndex);
  List<String> selectPowerPubdates(String searchIndex);
  int insertRequestLog(Map<String,String> map);
  void insertKindleRegist(String asin);
  void updateKindleRegist(String asin);
  List<Kindle> selectTodayKindleList(String date);
  Map<String,String> selectKindleShinchaku();
  Map<String,String> selectKindle(String asin);
  void updateTweetShinchaku(String asin);
  Map<String,String> selectKindleTodaySales();
  void updateTweetTodaySales(String asin);
  Map<String,String> selectMinTweetTop3();
  void countupTweetTop3(String description);
}
