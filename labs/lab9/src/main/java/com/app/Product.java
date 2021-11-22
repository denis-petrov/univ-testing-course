package com.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("old_price")
    @Expose
    private Integer oldPrice;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("keywords")
    @Expose
    private Object keywords;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("hit")
    @Expose
    private Integer hit;
    @SerializedName("cat")
    @Expose
    private String cat;

    public Integer getId() {
        return id;
    }

    public Product setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Product setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Integer oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public Product setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Object getKeywords() {
        return keywords;
    }

    public void setKeywords(Object keywords) {
        this.keywords = keywords;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getHit() {
        return hit;
    }

    public Product setHit(Integer hit) {
        this.hit = hit;
        return this;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public Product clone(Product product) {
        this.id = product.id;
        this.categoryId = product.categoryId;
        this.title = product.title;
        this.alias = product.alias;
        this.content = product.content;
        this.price = product.price;
        this.oldPrice = product.oldPrice;
        this.status = product.status;
        this.keywords = product.keywords;
        this.description = product.description;
        this.img = product.img;
        this.hit = product.hit;
        this.cat = product.cat;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "   \"id\": " + "\"" + id + "\",\n" +
                "   \"categoryId\": " + "\"" + categoryId + "\",\n" +
                "   \"title\": " + "\"" + title + "\",\n" +
                "   \"alias\": " + "\"" + alias + "\",\n" +
                "   \"content\": " + "\"" + content + "\",\n" +
                "   \"price\": " + "\"" + price + "\",\n" +
                "   \"oldPrice\": " + "\"" + oldPrice + "\",\n" +
                "   \"status\": " + "\"" + status + "\",\n" +
                "   \"keywords\": " + "\"" + keywords + "\",\n" +
                "   \"description\": " + "\"" + description + "\",\n" +
                "   \"img\": " + "\"" + img + "\",\n" +
                "   \"hit\": " + "\"" + hit + "\",\n" +
                '}';
    }
}
