package com.game.gamemodule.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
    Created by Arti on 11/6/21
*/
public class GameModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("preview")
    @Expose
    private String preview;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("isLock")
    @Expose
    private String isLock;
    @SerializedName("userPlay")
    @Expose
    private String userPlay;
    @SerializedName("screenOrientation")
    @Expose
    private String screenOrientation;
    @SerializedName("isAd")
    @Expose
    private String isAd;
    @SerializedName("isTop")
    @Expose
    private String isTop;
    @SerializedName("isActive")
    @Expose
    private String isActive;

    @SerializedName("game_list")
    @Expose
    private List<GameModel> gameList = null;

    public GameModel(String id, String name, String link, String preview, String screenOrientation, String isAd, String category, String tags, String isLock) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.preview = preview;
        this.screenOrientation = screenOrientation;
        this.isAd = isAd;
        this.category = category;
        this.tags = tags;
        this.isLock = isLock;
    }

    public List<GameModel> getGameList() {
        return gameList;
    }

    public void setGameList(List<GameModel> gameList) {
        this.gameList = gameList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public String getUserPlay() {
        return userPlay;
    }

    public void setUserPlay(String userPlay) {
        this.userPlay = userPlay;
    }

    public String getScreenOrientation() {
        return screenOrientation;
    }

    public void setScreenOrientation(String screenOrientation) {
        this.screenOrientation = screenOrientation;
    }

    public String getIsAd() {
        return isAd;
    }

    public void setIsAd(String isAd) {
        this.isAd = isAd;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}

