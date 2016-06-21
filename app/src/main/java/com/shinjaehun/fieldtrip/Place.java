package com.shinjaehun.fieldtrip;

import java.io.Serializable;

/**
 * Created by shinjaehun on 2016-05-21.
 */
public class Place {

    private long id;
    private String type;
    private String name;
    private String pic;
    private String location;
    private String description;
    private String detail;

    //이게 여기 있어야 하나 아니면 User 테이블에 있어야 하나...
    private String userInput;

    public Place() {

    }

    public Place(String type, String name, String pic, String map, String location, String description) {
        this.type = type;
        this.name = name;
        this.pic = pic;
        this.location = location;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


}
