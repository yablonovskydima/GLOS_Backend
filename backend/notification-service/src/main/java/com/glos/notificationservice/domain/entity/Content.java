package com.glos.notificationservice.domain.entity;

public class Content
{
    private String title;
    private String mainText;


    public String getTitle() {
        return title;
    }

    public String getMainText() {
        return mainText;
    }

    public Content(String title, String mainText) {
        this.title = title;
        this.mainText = mainText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }



    public Content() {
    }
}
