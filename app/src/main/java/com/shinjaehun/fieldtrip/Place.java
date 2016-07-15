package com.shinjaehun.fieldtrip;

import java.io.Serializable;

/**
 * Created by shinjaehun on 2016-05-21.
 */
//public class Place implements Serializable {
public class Place {
    // DB 조회 수를 줄일 수 있고
    // 직관적으로 이해할 수 있어 가독성이 높아
    // Place를 그대로 Serializable 객체로 넘기려고 했으나
    // DB를 업데이트하고 다시 Fragment에서 보여줄 때 입력한 정보가 갱신되지 않는 문제가 발생했다.
    // 이에 다시 Place를 직접 넘기는 대신 place의 ID를 넘기는 방식으로 문제를 해결할 수 있었다.

    private long id;
    private String type;
    private String name;
    private String pic;
    private String location;
    private String description;
    private String detail;

    //이게 여기 있어야 하나 아니면 User 테이블에 있어야 하나...
    private int visited;
    private String theDate;
    private String score;
    private String userInput;
    private String userPhoto;

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

    public int isVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public String getTheDate() {
        return theDate;
    }

    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
