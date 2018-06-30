package org.acg12.entity;


/**
 * Created by Administrator on 2018/4/11.
 */
public class SubjectStaff extends Param{

    private Integer subjectStaffId;
    private Integer subjectId;
    private Integer sId;
    private Integer personId;
    private Integer pId;
    private String name;
    private String job;

    public Integer getSubjectStaffId() {
        return subjectStaffId;
    }

    public void setSubjectStaffId(Integer subjectStaffId) {
        this.subjectStaffId = subjectStaffId;
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

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }



}
