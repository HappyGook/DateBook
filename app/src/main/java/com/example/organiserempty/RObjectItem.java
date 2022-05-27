package com.example.organiserempty;

public class RObjectItem {
    private String header;
    private String comp;


    public RObjectItem(String header, String comp) {
        this.header=header;
        this.comp=comp;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }
}
