package com.franzliszt.newbookkeeping.base;

public class ViewBar {
    private int width;
    private int height;
    private String label;
    private String num;
    private String price;
    public ViewBar( String label,String num,String price,int width,int height){
        this.label = label;
        this.num = num;
        this.price = price;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
