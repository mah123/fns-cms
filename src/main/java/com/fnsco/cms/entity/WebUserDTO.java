package com.fnsco.cms.entity;

import java.util.Date;

public class WebUserDTO extends DTO {
    private Integer id;

    private Integer type;

    private String name;

    private String password;

    private String realName;

    private String mobile;

    private Integer sex;

    private String aliasName;

    private String department;

    private Integer agentId;

    private String remark;

    private Date modifyTime;

    private Integer modifyUserId;

    private String tokenId;

    /**
     * tokenId
     *
     * @return the tokenId
     * @since CodingExample Ver 1.0
     */

    public String getTokenId() {
        return tokenId;
    }

    /**
     * tokenId
     *
     * @param tokenId the tokenId to set
     * @since CodingExample Ver 1.0
     */

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName == null ? null : aliasName.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    @Override
    public String toString() {
        return "WebUserDTO{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex=" + sex +
                ", aliasName='" + aliasName + '\'' +
                ", department='" + department + '\'' +
                ", agentId=" + agentId +
                ", remark='" + remark + '\'' +
                ", modifyTime=" + modifyTime +
                ", modifyUserId=" + modifyUserId +
                ", tokenId='" + tokenId + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + name.hashCode();
        result = result * 31 + id;
        return result;
    }
}
