package myclass.function;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import myclass.model.Kindle;
import myclass.model.KindleMyInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TitleConvert {
    @SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(TitleConvert.class);

    @SuppressWarnings("unused")
	private static TitleConvert instance = new TitleConvert();
    
	public static KindleMyInfo KindleToKindleMyInfo(Kindle kindle) {
		KindleMyInfo kindleMyinfo = new KindleMyInfo();
		String title = kindle.getTitle();
    	
    	kindleMyinfo.setAsin(kindle.getAsin());
    	kindleMyinfo.setIsBulkBuying(isBulkBuying(title));
    	kindleMyinfo.setIsLimitedFree(isLimitedFree(title));
    	kindleMyinfo.setIsNovel(isNovel(title));
    	kindleMyinfo.setLabel(getLabel(title));
    	kindleMyinfo.setSimpleTitle(getSimpleTitle(title));
    	kindleMyinfo.setTileTitle(getTileTitle(title));
    	kindleMyinfo.setIsMagazine(isMagazine(title));
    	
    	return kindleMyinfo;
	}

	private static boolean isBulkBuying(String title){
    	String regex = "^\\[まとめ買い\\]";
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(title);
    	
    	if (m.find()){
  				return true;
    		}else{
    			return false;
    	}
    }

    private static boolean isMagazine(String title){
    	String regex = "\\[雑誌\\]";
    	regex += "|[０１２３４５６７８９0-9]+年[０１２３４５６７８９0-9]+月号";
    	regex += "|月刊";
    	regex += "|No\\.[0-9]+（[0-9]+年）";
    	regex += "|[0-9]+月新刊";
    	regex += "|[0-9]+月号";
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(title);
    	
    	if (m.find()){
  				return true;
    		}else{
    			return false;
    	}
    }

    private static boolean isNovel(String title){    	
    	String regex = ".*文庫.*";
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(title);
    	
    	if (m.find()){
  				return true;
    		}else{
    			return false;
    	}
    }

    private static boolean isLimitedFree(String title){
    	String regex = "期間限定.*無料";
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(title);
    	
    	if (m.find()){
  				return true;
    		}else{
    			return false;
    	}
    }

    private static String getLabel(String title){
    	if(isBulkBuying(title)){
    		return null;
    	}
    	String result = title;
    	result = deleteModifier(result);
    	result = deleteParentheses(result);
    	
    	String regex = "\\((.+?)\\)";
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(result);
    	
		List<String> list = new ArrayList<String>();
		while (m.find()) {
            list.add(m.group(1));
        }
		
		String label;
		if(list.size() == 0){
			return null;
		}else{
			label = list.get(list.size() - 1);
		}
		
		try {
			Integer.parseInt(label);
			return null;
		} catch (NumberFormatException e) {
			//数値ではないためラベルとして認識する
			return label;
		}
    }

    private static String getExceptLabelFromTitle(String title){
    	if(isBulkBuying(title)){
    		return title;
    	}

    	String result = title;
    	String label = getLabel(title);
    	if(label != null){
        	result = result.replaceAll(label, "");
        	result = deleteParentheses(result);
        	result = result.trim();
    	}
		
		return result;
    }

    private static String deleteParentheses(String string){

    	String result = string;
    	result = result.replaceAll("【】", "");
    	result = result.replaceAll("\\[\\]", "");
    	result = result.replaceAll("〔〕", "");
    	result = result.replaceAll("<>", "");
    	result = result.replaceAll("<>", "");
    	result = result.replaceAll("〈〉", "");
    	result = result.replaceAll("（）", "");
    	result = result.replaceAll("\\(\\)", "");
    	result = result.replaceAll("［］", "");
    	result = result.trim();
		
		return result;
    }

    private static String deleteModifier(String string){

    	String result = string;
    	result = result.replaceAll("　", " ");
    	result = result.replaceAll("【期間限定 無料お試し版】", "");
    	result = result.replaceAll("【期間限定無料】", "");
    	result = result.replaceAll("\\[まとめ買い\\]", "");
    	result = result.replaceAll("\\[まとめ買い\\]", "");
    	result = result.replaceAll("【フルカラー】", "");
    	result = result.replaceAll("【カラーコミック】", "");
    	result = result.replaceAll("【合本版】", "");
    	result = result.replaceAll("\\[カラー版\\]", "");
    	result = result.replaceAll("\\[モノクロ版\\]", "");
    	result = result.replaceAll("\\[無料版\\]", "");
    	result = result.replaceAll("〔完〕", "");
    	result = result.replaceAll("<特典付>", "");
    	result = result.replaceAll("【新装版】", "");
    	result = result.replaceAll("【特別付録付】", "");
    	result = result.replaceAll("【電子早読み版】", "");
    	result = result.replaceAll("【電子限定おまけ付き】", "");
    	result = result.replaceAll("【無料連載版】", "");
    	result = result.replaceAll("【日・英翻訳版】", "");
    	result = result.replaceAll("【電子版】", "");
    	result = result.replaceAll("\\-電子版\\-", "");
    	result = result.replaceAll("【電子特別版】", "");
    	result = result.replaceAll("【カラーイラスト版】", "");
    	result = result.replaceAll("【電子限定版】", "");
    	result = result.replaceAll("【電子版限定】", "");
    	result = result.replaceAll("【電子再編版】", "");
    	result = result.replaceAll("【お試し版】", "");
    	result = result.replaceAll("【全巻セット】", "");
    	result = result.replaceAll("【無料】", "");
    	result = result.replaceAll("【無料版】", "");
    	result = result.replaceAll("【おためし版】", "");
    	result = result.replaceAll("【アクセスコード付き】", "");
    	result = result.replaceAll("【通常版】", "");
    	result = result.replaceAll("【分冊版】", "");
    	result = result.replaceAll("【期間限定】", "");
    	result = result.replaceAll("【イラスト入り】", "");
    	result = result.replaceAll("【デジタル・修正版】", "");
    	result = result.replaceAll("【マンガ版】", "");
    	result = result.replaceAll("【マンガ訳】", "");
    	result = result.replaceAll("【フルカラーコミック】", "");
    	result = result.replaceAll("【フルカラー特装版】", "");
    	result = result.replaceAll("【特典小冊子つき】", "");
    	result = result.replaceAll("【第１話お試し読み】 ", "");
    	result = result.replaceAll("【電子オリジナル】", "");
    	result = result.replaceAll("【イラスト付】", "");
    	result = result.replaceAll("【電子限定】", "");
    	result = result.replaceAll("【電子限定かきおろし付】", "");
    	result = result.replaceAll("【電子再編】", "");
    	result = result.replaceAll("【期間限定】", "");
    	result = result.replaceAll("【電子限定かきおろしペーパー付】", "");
    	result = result.replaceAll("【試し読み小冊子】", "");
    	result = result.replaceAll("【第１話無料配信版】", "");
    	result = result.replaceAll("【電子版お試し読み小冊子】", "");
    	result = result.replaceAll("【デジタル版限定描き下ろしマンガ付】", "");
    	result = result.replaceAll("【電子限定完全版】", "");
    	result = result.replaceAll("【期間限定無料お試し版】", "");
    	result = result.replaceAll("〈豪華特典版〉", "");
    	result = result.replaceAll("〔新装版〕", "");
    	result = result.replaceAll("（完全版）", "");
    	result = result.replaceAll("〔完全版〕", "");
    	result = result.replaceAll("〔ワイド〕", "");
    	result = result.replaceAll("（フルカラー）", "");
    	result = result.replaceAll("〈電子特別版〉", "");
    	result = result.replaceAll("（イラスト完全版）", "");
    	result = result.replaceAll("\\(コミック版\\)", "");
    	result = result.replaceAll("リマスター版", "");
    	result = result.replaceAll("\\[シリーズ\\]", "");
    	result = result.replaceAll("\\[コミック\\]", "");
    	result = result.replaceAll("［合本版］", "");
    	result = result.replaceAll("新装版", "");
    	result = result.replaceAll("完全版", "");
    	result = result.replaceAll("無料ダイジェスト版", "");
    	result = result.replaceAll("カラー版", "");
    	result = result.replaceAll("モノクロ版", "");
    	result = result.replaceAll("無料お試し版", "");
    	result = result.replaceAll("合体版", "");
    	result = result.trim();
		
		return result;
    }

    private static String getSimpleTitle(String title){
    	String result = getExceptLabelFromTitle(title);
    	result = getExceptPreparedTitle(result);

    	result = result.replaceAll("　", " ");
    	result = deleteModifier(result);
    	
    	result = result.trim();
    	result = result.replaceAll("[第全 ][１２３４５６７８９０0-9]+巻", "");
    	result = result.replaceAll("[（\\(][１２３４５６７８９０0-9]+[）\\)]", "");
    	result = result.replaceAll(" [１２３４５６７８９０0-9]+$", "");
    	result = result.replaceAll("\\([１２３４５６７８９０0-9]+\\-[１２３４５６７８９０0-9]+\\)$", "");
    	result = result.replaceAll("[.１２３４５６７８９０0-9]+巻$", "");
    	result = result.replaceAll(" [上中下]巻", "");
    	result = result.replaceAll("[（\\(][上中下][）\\)]", "");
    	result = result.replaceAll(" [上中下]$", "");
    	result = result.replaceAll("\\[[上中下]\\]$", "");
    	result = result.replaceAll("[（\\(][前中後]編[）\\)]$", "");
    	result = result.replaceAll(" vol\\.[１２３４５６７８９０0-9]+$", "");
    	
    	result = result.trim();
    	result = result.replaceAll("[:：]$", "");
    	result = result.replaceAll(">$", "");
    	
    	result = deleteParentheses(result);
    	
    	result = result.trim();
    	
		return result;
    }
    
    private static String getTileTitle(String title){
    	String TileTitle = getExceptLabelFromTitle(title);
    	TileTitle = getExceptPreparedTitle(TileTitle);
    	
    	return TileTitle;
    }
    
    private static String getPreparedTitle(String title){
    	String regex = "<(.+?)>";
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(title);
    	
		List<String> list = new ArrayList<String>();
		while (m.find()) {
            list.add(m.group(1));
        }
		
		if(list.size() == 0){
			return null;
		}else{
			return list.get(list.size() - 1);
		}
    }
    
    private static String getExceptPreparedTitle(String title){    	
    	String preparedTitle = getPreparedTitle(title);
    	
    	if(preparedTitle != null){
        	if(title.indexOf(preparedTitle) != -1){
        		return title.replaceAll("<" + preparedTitle + ">", "");
        	}else{
        		return title;
        	}
    	}else{
    		return title;
    	}
    }
}
