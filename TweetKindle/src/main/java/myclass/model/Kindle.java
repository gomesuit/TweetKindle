package myclass.model;

import com.ECS.client.jax.Item;
import com.ECS.client.jax.ItemAttributes;

public class Kindle {
    protected String asin;
    protected String author;
    protected String binding;
    protected String format;
    protected Boolean isAdultProduct;
    protected String label;
    protected String manufacturer;
    protected String productGroup;
    protected String productTypeName;
    protected String publicationDate;
    protected String publisher;
    protected String releaseDate;
    protected String title;
    protected String content;
    protected String detailPageURL;
    protected String largeImage;

    public Kindle(){}
    public Kindle(Item item){
    	ItemAttributes itemAttributes = item.getItemAttributes();
    	
        this.setAsin(item.getASIN());
        if(itemAttributes.getAuthor().size() != 0){
        	this.setAuthor(itemAttributes.getAuthor().get(0));
        }else{
        	this.setAuthor("");
        }
        this.setBinding(itemAttributes.getBinding());
        if(itemAttributes.getFormat().size() != 0){
        	this.setFormat(itemAttributes.getFormat().get(0));
        }else{
        	this.setFormat("");
        }
        this.setIsAdultProduct(itemAttributes.isIsAdultProduct());
        this.setLabel(itemAttributes.getLabel());
        this.setManufacturer(itemAttributes.getManufacturer());
        this.setProductGroup(itemAttributes.getProductGroup());
        this.setProductTypeName(itemAttributes.getProductTypeName());
        this.setPublicationDate(itemAttributes.getPublicationDate());
        this.setPublisher(itemAttributes.getPublisher());
        this.setReleaseDate(itemAttributes.getReleaseDate());
        this.setTitle(itemAttributes.getTitle());
        if(item.getEditorialReviews() != null){
        	this.setContent(item.getEditorialReviews().getEditorialReview().get(0).getContent());
        }else{
        	this.setContent("");
        }
        this.setDetailPageURL(item.getDetailPageURL());
        if(item.getLargeImage() != null){
        	this.setLargeImage(item.getLargeImage().getURL());
        }else{
        	this.setLargeImage("");
        }
    }

    public String getAsin() {
        return asin;
    }
    public void setAsin(String asin) {
        this.asin = asin;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getBinding() {
        return binding;
    }
    public void setBinding(String binding) {
        this.binding = binding;
    }
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public Boolean getIsAdultProduct() {
        return isAdultProduct;
    }
    public void setIsAdultProduct(Boolean isAdultProduct) {
        this.isAdultProduct = isAdultProduct;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public String getProductGroup() {
        return productGroup;
    }
    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }
    public String getProductTypeName() {
        return productTypeName;
    }
    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }
    public String getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getDetailPageURL() {
        return detailPageURL;
    }
    public void setDetailPageURL(String detailPageURL) {
        this.detailPageURL = detailPageURL;
    }
    public String getLargeImage() {
        return largeImage;
    }
    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public void showAllData(){
        System.out.println("{");
        System.out.println("    asin: " + asin);
        System.out.println("    author: " + author);
        System.out.println("    binding: " + binding);
        System.out.println("    format: " + format);
        System.out.println("    isAdultProduct: " + isAdultProduct);
        System.out.println("    label: " + label);
        System.out.println("    manufacturer: " + manufacturer);
        System.out.println("    productGroup: " + productGroup);
        System.out.println("    productTypeName: " + productTypeName);
        System.out.println("    publicationDate: " + publicationDate);
        System.out.println("    publisher: " + publisher);
        System.out.println("    releaseDate: " + releaseDate);
        System.out.println("    title: " + title);
        System.out.println("    content: " + content);
        System.out.println("    detailPageURL: " + detailPageURL);
        System.out.println("    largeImage: " + largeImage);
        System.out.println("}");
    }
}
