package com.acg12.entity;

/**
 * Created by Administrator on 2018/5/14.
 */
public class SubjectSong extends Param{

    private Integer subjectSongId;
    private Integer subjectId;
    private Integer sId;
    private String title;

    public Integer getSubjectSongId() {
        return subjectSongId;
    }

    public void setSubjectSongId(Integer subjectSongId) {
        this.subjectSongId = subjectSongId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
