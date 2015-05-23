package myclass.data;

import myclass.model.Kindle;
import myclass.model.KindleMyInfo;

import java.util.List;
import java.util.Map;

public interface KindleMapper {
  int countKindle(String asin);
  int countKindleRegist(String asin);
  void insertKindle(Kindle kindle);
  void updateKindle(Kindle kindle);
  List<String> selectSortValue(String searchIndex);
  List<String> selectBrowseNodes(String searchIndex);
  List<String> selectPowerPubdates(String searchIndex);
  int insertRequestLog(Map<String,String> map);
  void insertKindleRegist(String asin);
  void updateKindleRegist(String asin);
  List<Kindle> selectTodayKindleList(String date);
  Map<String,String> selectKindleShinchaku(List<String> wordList);
  Map<String,String> selectKindle(String asin);
  void updateTweetShinchaku(String asin);
  Map<String,String> selectKindleTodaySales(List<String> wordList);
  void updateTweetTodaySales(String asin);
  Map<String,String> selectMinTweetTop3();
  void countupTweetTop3(String description);
  List<String> selectExclusion();
  void insertNoImage(String asin);
  String selectNoImage();
  void deleteNoImage(String asin);
  List<String> selectOldAsinList(Map<String,Object> map);
  void deleteKindleRegist(String asin);
  List<Kindle> selectAllKindleList();
  void insertKindleMyInfo(KindleMyInfo kindleMyinfo);
  void updateKindleMyInfo(KindleMyInfo kindleMyinfo);
  int countKindleMyInfo(String asin);
}
