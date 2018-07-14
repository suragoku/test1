package com.blueoptima.bean;

import java.util.Objects;

/**
 * Created by Suranjay on 12/07/18.
 */
public class UserDetail {

    private String loginId;
    private Long id ;
    private String email ;
    private String company ;
    private String name ;
    private String location ;
    private Long followers ;
    private Long followering ;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getFollowers() {
        return followers;
    }

    public void setFollowers(Long followers) {
        this.followers = followers;
    }

    public Long getFollowering() {
        return followering;
    }

    public void setFollowering(Long followering) {
        this.followering = followering;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetail that = (UserDetail) o;
        return Objects.equals(loginId, that.loginId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginId);
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "loginId='" + loginId + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", followers=" + followers +
                ", followering=" + followering +
                '}';
    }
}
