package myclass.function;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import myclass.model.KindleMyInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TitleConvert {
    @SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(TitleConvert.class);
    
	public KindleMyInfo KindleToKindleMyInfo(String asin, String title) {
		KindleMyInfo kindleMyinfo = new KindleMyInfo();
    	
    	kindleMyinfo.setAsin(asin);
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
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("\\[雑誌\\]");
    	buffer.append("|[０１２３４５６７８９0-9]+年[０１２３４５６７８９0-9]+月号");
    	buffer.append("|月刊");
    	buffer.append("|No\\.[0-9]+（[0-9]+年）");
    	buffer.append("|[0-9]+月新刊");
    	buffer.append("|[0-9]+月号");
    	buffer.append("|Amusement Life Magazine");
    	String regex = buffer.toString();
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(title);
    	
    	if (m.find()){
  				return true;
    		}else{
    			return false;
    	}
    }

    private boolean isAdult(String title){
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("BL☆美少年ブック");
    	buffer.append("|BLスイートノベル");
    	buffer.append("|BOYS FAN");
    	buffer.append("|Ficus");
    	buffer.append("|TL濡恋コミックス");
    	buffer.append("|♂BL♂らぶらぶコミックス");
    	buffer.append("|もえまん");
    	buffer.append("|りぼんマスコットコミックスDIGITAL");
    	buffer.append("|エロマンガ島");
    	buffer.append("|ディアプラス・コミックス");
    	buffer.append("|ドＭ");
    	buffer.append("|ドS");
    	buffer.append("|ビーボーイコミックス");
    	buffer.append("|ラブきゅんコミック");
    	buffer.append("|リア×ロマ");
    	buffer.append("|ＡＶ男優");
    	buffer.append("|ｽｷして\\?桃色日記");
    	buffer.append("|e\\-Color Comic");
    	buffer.append("|フラワーコミックス");
    	buffer.append("|えっち");
    	buffer.append("|女体化");
    	buffer.append("|潮吹き");
    	buffer.append("|挿入");
    	buffer.append("|ハーレクインコミックス");
    	buffer.append("|S●X");
    	buffer.append("|乙女チック");
    	buffer.append("|moment");
    	buffer.append("|アソコ");
    	buffer.append("|肌恋");
    	buffer.append("|コミックHIMEクリピンク");
    	buffer.append("|レイプ");
    	buffer.append("|ぶんか社コミックス S\\*girl Selection");
    	buffer.append("|男の娘");
    	buffer.append("|男子寮");
    	buffer.append("|シトロンコミックス");
    	buffer.append("|Ｈしちゃ");
    	buffer.append("|女の体");
    	buffer.append("|イキすぎ");
    	buffer.append("|エッチ");
    	buffer.append("|淫ら");
    	buffer.append("|侍侍");
    	buffer.append("|スケベ");
    	buffer.append("|ヤりすぎ");
    	buffer.append("|ヤリすぎ");
    	buffer.append("|QooPA");
    	buffer.append("|乙女ドルチェ・コミックス");
    	buffer.append("|喘ぐ");
    	buffer.append("|素股");
    	buffer.append("|GUSH mania COMICS");
    	buffer.append("|MIU 恋愛MAX COMICS");
    	buffer.append("|秋水社\\/MAHK");
    	buffer.append("|オトナの恋");
    	buffer.append("|蜜恋");
    	buffer.append("|ビーボーイデジタルコミックス");
    	buffer.append("|花音コミックス");
    	buffer.append("|絶対恋愛Sweet");
    	buffer.append("|濡れ");
    	buffer.append("|バンブーコミックス 恋愛天国☆恋パラコレクション");
    	buffer.append("|アクリコミック");
    	buffer.append("|乙蜜");
    	buffer.append("|ヤクザ");
    	buffer.append("|お尻");
    	buffer.append("|♂");
    	buffer.append("|前立腺");
    	buffer.append("|女装");
    	buffer.append("|ビンビン");
    	buffer.append("|処女");
    	buffer.append("|セイシ");
    	buffer.append("|汁濁");
    	buffer.append("|ペロペロ");
    	buffer.append("|エロ");
    	buffer.append("|ペロペロ男子図鑑");
    	buffer.append("|モロ見え");
    	buffer.append("|アレが");
    	buffer.append("|オシオキ");
    	buffer.append("|ビンカンきゅんGirls");
    	buffer.append("|男装");
    	buffer.append("|凌辱");
    	buffer.append("|犯され");
    	buffer.append("|孕む");
    	buffer.append("|エロ");
    	buffer.append("|おっぱい");
    	buffer.append("|くぱぁ");
    	buffer.append("|Ｈする");
    	buffer.append("|Ｓ系");
    	buffer.append("|おっきすぎ");
    	buffer.append("|ぶっかけ");
    	buffer.append("|絶頂");
    	buffer.append("|ビュル");
    	buffer.append("|ノンケ");
    	buffer.append("|性教育");
    	buffer.append("|股間");
    	buffer.append("|姦");
    	buffer.append("|むっちりプルコミ");
    	buffer.append("|挿し");
    	buffer.append("|バンブーコミックス COLORFULセレクト");
    	buffer.append("|あすかコミックスCL-DX");
    	buffer.append("|何度も");
    	buffer.append("|朝まで");
    	buffer.append("|寝取り");
    	buffer.append("|孕ませ");
    	buffer.append("|鬼畜");
    	buffer.append("|白濁");
    	buffer.append("|drapコミックス");
    	
    	buffer.append("|マンガ日本の歴史");
    	String regex = buffer.toString();
    	
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
    	StringBuffer buffer = new StringBuffer();

    	String result = string;
    	buffer.append("【】");
    	buffer.append("|\\[\\]");
    	buffer.append("|〔〕");
    	buffer.append("|<>");
    	buffer.append("|<>");
    	buffer.append("|〈〉");
    	buffer.append("|（）");
    	buffer.append("|\\(\\)");
    	buffer.append("|［］");
    	result = result.replaceAll(buffer.toString(), "");

    	result = result.trim();
		
		return result;
    }

    private String deleteModifier(String string){
    	StringBuffer buffer = new StringBuffer();

    	String result = string;
    	result = result.replaceAll("　", " ");
    	
    	buffer.append("【期間限定 無料お試し版】");
    	buffer.append("|【期間限定無料】");
    	buffer.append("|\\[まとめ買い\\]");
    	buffer.append("|\\[まとめ買い\\]");
    	buffer.append("|【フルカラー】");
    	buffer.append("|【カラーコミック】");
    	buffer.append("|【合本版】");
    	buffer.append("|\\[カラー版\\]");
    	buffer.append("|\\[モノクロ版\\]");
    	buffer.append("|\\[無料版\\]");
    	buffer.append("|〔完〕");
    	buffer.append("|<特典付>");
    	buffer.append("|【新装版】");
    	buffer.append("|【特別付録付】");
    	buffer.append("|【電子早読み版】");
    	buffer.append("|【電子限定おまけ付き】");
    	buffer.append("|【無料連載版】");
    	buffer.append("|【日・英翻訳版】");
    	buffer.append("|【電子版】");
    	buffer.append("|\\-電子版\\-");
    	buffer.append("|【電子特別版】");
    	buffer.append("|【カラーイラスト版】");
    	buffer.append("|【電子限定版】");
    	buffer.append("|【電子版限定】");
    	buffer.append("|【電子再編版】");
    	buffer.append("|【お試し版】");
    	buffer.append("|【全巻セット】");
    	buffer.append("|【無料】");
    	buffer.append("|【無料版】");
    	buffer.append("|【おためし版】");
    	buffer.append("|【アクセスコード付き】");
    	buffer.append("|【通常版】");
    	buffer.append("|【分冊版】");
    	buffer.append("|【期間限定】");
    	buffer.append("|【イラスト入り】");
    	buffer.append("|【デジタル・修正版】");
    	buffer.append("|【マンガ版】");
    	buffer.append("|【マンガ訳】");
    	buffer.append("|【フルカラーコミック】");
    	buffer.append("|【フルカラー特装版】");
    	buffer.append("|【特典小冊子つき】");
    	buffer.append("|【第１話お試し読み】 ");
    	buffer.append("|【電子オリジナル】");
    	buffer.append("|【イラスト付】");
    	buffer.append("|【電子限定】");
    	buffer.append("|【電子限定かきおろし付】");
    	buffer.append("|【電子再編】");
    	buffer.append("|【期間限定】");
    	buffer.append("|【電子限定かきおろしペーパー付】");
    	buffer.append("|【試し読み小冊子】");
    	buffer.append("|【第１話無料配信版】");
    	buffer.append("|【電子版お試し読み小冊子】");
    	buffer.append("|【デジタル版限定描き下ろしマンガ付】");
    	buffer.append("|【電子限定完全版】");
    	buffer.append("|【期間限定無料お試し版】");
    	buffer.append("|〈豪華特典版〉");
    	buffer.append("|〔新装版〕");
    	buffer.append("|（完全版）");
    	buffer.append("|〔完全版〕");
    	buffer.append("|〔ワイド〕");
    	buffer.append("|（フルカラー）");
    	buffer.append("|〈電子特別版〉");
    	buffer.append("|（イラスト完全版）");
    	buffer.append("|\\(コミック版\\)");
    	buffer.append("|リマスター版");
    	buffer.append("|\\[シリーズ\\]");
    	buffer.append("|\\[コミック\\]");
    	buffer.append("|［合本版］");
    	buffer.append("|新装版");
    	buffer.append("|完全版");
    	buffer.append("|無料ダイジェスト版");
    	buffer.append("|オールカラー版");
    	buffer.append("|カラー版");
    	buffer.append("|モノクロ版");
    	buffer.append("|無料お試し版");
    	buffer.append("|合体版");
    	result = result.replaceAll(buffer.toString(), " ");
    	result = result.trim();
		
		return result;
    }

    private String getSimpleTitle(String title){
    	String result = getExceptLabelFromTitle(title);
    	result = getExceptPreparedTitle(result);

    	result = result.replaceAll("　", " ");
    	result = result.trim();
    	result = deleteModifier(result);

    	StringBuffer buffer = new StringBuffer();
    	buffer.append("[第全 ][１２３４５６７８９０0-9]+巻");
    	buffer.append("|[（\\(][１２３４５６７８９０0-9]+[）\\)]");
    	buffer.append("| [１２３４５６７８９０0-9]+$");
    	buffer.append("|\\([１２３４５６７８９０0-9]+\\-[１２３４５６７８９０0-9]+\\)$");
    	buffer.append("|[.１２３４５６７８９０0-9]+巻$");
    	buffer.append("| [上中下]巻");
    	buffer.append("|[（\\(][上中下][）\\)]");
    	buffer.append("| [上中下]$");
    	buffer.append("|\\[[上中下]\\]$");
    	buffer.append("|[（\\(][前中後]編[）\\)]$");
    	buffer.append("| \\[vV\\]\\[oO\\]\\[lL\\]\\.[１２３４５６７８９０0-9]+$");
    	result = result.replaceAll(buffer.toString(), "");
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
