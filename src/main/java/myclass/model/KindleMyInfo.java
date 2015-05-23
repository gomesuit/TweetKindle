package myclass.model;

public class KindleMyInfo {
    protected String asin;
    protected String tileTitle;
    protected String simpleTitle;
    protected String Label;
    protected Boolean isBulkBuying;
    protected Boolean isLimitedFree;
    protected Boolean isNovel;
    protected Boolean isMagazine;
    
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public String getTileTitle() {
		return tileTitle;
	}
	public void setTileTitle(String tileTitle) {
		this.tileTitle = tileTitle;
	}
	public String getSimpleTitle() {
		return simpleTitle;
	}
	public void setSimpleTitle(String simpleTitle) {
		this.simpleTitle = simpleTitle;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public Boolean getIsBulkBuying() {
		return isBulkBuying;
	}
	public void setIsBulkBuying(Boolean isBulkBuying) {
		this.isBulkBuying = isBulkBuying;
	}
	public Boolean getIsLimitedFree() {
		return isLimitedFree;
	}
	public void setIsLimitedFree(Boolean isLimitedFree) {
		this.isLimitedFree = isLimitedFree;
	}
	public Boolean getIsNovel() {
		return isNovel;
	}
	public void setIsNovel(Boolean isNovel) {
		this.isNovel = isNovel;
	}
	public Boolean getIsMagazine() {
		return isMagazine;
	}
	public void setIsMagazine(Boolean isMagazine) {
		this.isMagazine = isMagazine;
	}
}
