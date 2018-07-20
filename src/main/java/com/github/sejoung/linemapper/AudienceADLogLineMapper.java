package com.github.sejoung.linemapper;

import org.springframework.batch.item.file.LineMapper;

import com.github.sejoung.model.AudienceADLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AudienceADLogLineMapper implements LineMapper<AudienceADLog> {

    @Override
    public AudienceADLog mapLine(String line, int lineNumber) throws Exception {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        AudienceADLog aulog = gson.fromJson(line, AudienceADLog.class);
        return aulog;
    }

}
