package com.wsa.model;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Data
public class Volunteer extends User implements Serializable {
    private static final long serialVersionUID = -7392731117458485888L;
    private Long id;
    private Long userId;
    private String experience;
    private Long firstAid;
    private Long dbs;
    private Long kudosPoints;
    private Long eventCount;
    private String level;
    @Transient
    private Long roleId;
    @Transient
    private Long volunteerId;
    @Transient
    private String roleName;
    @Transient
    private List<Credential> credentialList;

    @Data
    public static class Credential implements Serializable {
        private static final long serialVersionUID = 1L;
        private String credentialName;
        private String credentialUrl;
    }
}
