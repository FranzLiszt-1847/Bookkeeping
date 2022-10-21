package com.franzliszt.newbookkeeping.base;

public class RankList {
    private int position;
    private String label;
    private String content;
    private String price;
    private int type;
    public RankList(int position,String label,String content,String price,int type){
        this.position = position;
        this.label = label;
        this.content = content;
        this.price = price;
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
