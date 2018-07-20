package com.github.sejoung.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author kim se joung
 *
 */
@Data
public class AudienceADLog {
    private List<InctKc> inctKcs;
    private List<String> userIds;
    private String auid;
    private String keyIp;

    private Date regdate;
}
