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
    
	public KindleMyInfo KindleToKindleMyInfo(Kindle kindle) {
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
    	kindleMyinfo.setIsAdult(isAdult(title));
    	
    	return kindleMyinfo;
	}

	private boolean isBulkBuying(String title){
    	String regex = "^\\[まとめ買い\\]";
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(title);
    	
    	if (m.find()){
  				return true;
    		}else{
    			return false;
    	}
    }

    private boolean isMagazine(String title){
    	String regex = "\\[雑誌\\]";
    	regex += "|[０１２３４５６７８９0-9]+年[０１２３４５６７８９0-9]+月号";
    	regex += "|月刊";
    	regex += "|No\\.[0-9]+（[0-9]+年）";
    	regex += "|[0-9]+月新刊";
    	regex += "|[0-9]+月号";
    	regex += "|Amusement Life Magazine";
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(title);
    	
    	if (m.find()){
  				return true;
    		}else{
    			return false;
    	}
    }

    private boolean isAdult(String title){
    	String regex = "";
    	regex += "BL☆美少年ブック";
    	regex += "|BLスイートノベル";
    	regex += "|BOYS FAN";
    	regex += "|Ficus";
    	regex += "|TL濡恋コミックス";
    	regex += "|♂BL♂らぶらぶコミックス";
    	regex += "|もえまん";
    	regex += "|りぼんマスコットコミックスDIGITAL";
    	regex += "|エロマンガ島";
    	regex += "|ディアプラス・コミックス";
    	regex += "|ドＭ";
    	regex += "|ドS";
    	regex += "|ビーボーイコミックス";
    	regex += "|ラブきゅんコミック";
    	regex += "|リア×ロマ";
    	regex += "|ＡＶ男優";
    	regex += "|ｽｷして\\?桃色日記";
    	regex += "|e\\-Color Comic";
    	regex += "|フラワーコミックス";
    	regex += "|えっち";
    	regex += "|女体化";
    	regex += "|潮吹き";
    	regex += "|挿入";
    	regex += "|ハーレクインコミックス";
    	regex += "|S●X";
    	regex += "|乙女チック";
    	regex += "|moment";
    	regex += "|アソコ";
    	regex += "|肌恋";
    	regex += "|コミックHIMEクリピンク";
    	regex += "|レイプ";
    	regex += "|ぶんか社コミックス S\\*girl Selection";
    	regex += "|男の娘";
    	regex += "|男子寮";
    	regex += "|シトロンコミックス";
    	regex += "|Ｈしちゃ";
    	regex += "|女の体";
    	regex += "|イキすぎ";
    	regex += "|エッチ";
    	regex += "|淫ら";
    	regex += "|侍侍";
    	regex += "|スケベ";
    	regex += "|ヤりすぎ";
    	regex += "|ヤリすぎ";
    	regex += "|QooPA";
    	regex += "|乙女ドルチェ・コミックス";
    	regex += "|喘ぐ";
    	regex += "|素股";
    	regex += "|GUSH mania COMICS";
    	regex += "|MIU 恋愛MAX COMICS";
    	regex += "|秋水社\\/MAHK";
    	regex += "|オトナの恋";
    	regex += "|蜜恋";
    	regex += "|ビーボーイデジタルコミックス";
    	regex += "|花音コミックス";
    	regex += "|絶対恋愛Sweet";
    	regex += "|濡れ";
    	regex += "|バンブーコミックス 恋愛天国☆恋パラコレクション";
    	regex += "|アクリコミック";
    	regex += "|乙蜜";
    	regex += "|ヤクザ";
    	regex += "|お尻";
    	regex += "|♂";
    	regex += "|前立腺";
    	regex += "|女装";
    	regex += "|ビンビン";
    	regex += "|処女";
    	regex += "|セイシ";
    	regex += "|汁濁";
    	regex += "|ペロペロ";
    	regex += "|エロ";
    	regex += "|ペロペロ男子図鑑";
    	regex += "|モロ見え";
    	regex += "|アレが";
    	regex += "|オシオキ";
    	regex += "|ビンカンきゅんGirls";
    	regex += "|男装";
    	regex += "|凌辱";
    	regex += "|犯され";
    	regex += "|孕む";
    	regex += "|エロ";
    	regex += "|おっぱい";
    	regex += "|くぱぁ";
    	regex += "|Ｈする";
    	regex += "|Ｓ系";
    	regex += "|おっきすぎ";
    	regex += "|ぶっかけ";
    	regex += "|絶頂";
    	regex += "|ビュル";
    	regex += "|ノンケ";
    	regex += "|性教育";
    	regex += "|股間";
    	regex += "|姦";
    	regex += "|むっちりプルコミ";
    	regex += "|挿し";

    	regex += "|マンガ日本の歴史";
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(title);
    	
    	if (m.find()){
  				return true;
    		}else{
    			return false;
    	}
    }

    private boolean isNovel(String title){    	
    	String regex = ".*文庫.*";
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(title);
    	
    	if (m.find()){
  				return true;
    		}else{
    			return false;
    	}
    }

    private boolean isLimitedFree(String title){
    	String regex = "期間限定.*無料";
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(title);
    	
    	if (m.find()){
  				return true;
    		}else{
    			return false;
    	}
    }

    private String getLabel(String title){
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

    private String getExceptLabelFromTitle(String title){
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

    private String deleteParentheses(String string){

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

    private String deleteModifier(String string){

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
    	result = result.replaceAll("オールカラー版", "");
    	result = result.replaceAll("カラー版", "");
    	result = result.replaceAll("モノクロ版", "");
    	result = result.replaceAll("無料お試し版", "");
    	result = result.replaceAll("合体版", "");
    	result = result.trim();
		
		return result;
    }

    private String getSimpleTitle(String title){
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
    	result = result.replaceAll(" \\[vV\\]\\[oO\\]\\[lL\\]\\.[１２３４５６７８９０0-9]+$", "");
    	
    	result = result.trim();
    	result = result.replaceAll("[:：]$", "");
    	result = result.replaceAll(">$", "");
    	
    	result = deleteParentheses(result);
    	
    	result = result.trim();
    	
		return result;
    }
    
    private String getTileTitle(String title){
    	String TileTitle = getExceptLabelFromTitle(title);
    	TileTitle = getExceptPreparedTitle(TileTitle);
    	
    	return TileTitle;
    }
    
    private String getPreparedTitle(String title){
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
    
    private String getExceptPreparedTitle(String title){    	
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
