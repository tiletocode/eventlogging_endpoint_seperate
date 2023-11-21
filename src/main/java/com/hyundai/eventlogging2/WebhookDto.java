package com.hyundai.eventlogging2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookDto {
  private String metricName;
  private int pcode;
  private String level;
  private String metricValue;
  private long oid;
  private String title;
  private String message;
  private String uuid;
  private String metricThreshold;
  private String oname;
  private String projectName;
  private String status;
  private long time;

  public void nullReplace(WebhookDto dto) {
    Config config = Config.getConfig();
    String nullReplace = config.getString("webhook.message.nullreplace", "empty_value");
    if (dto.getLevel() == null) {
      dto.setLevel(nullReplace);
    }
    if (dto.getOname() == null) {
      dto.setOname(nullReplace);
    }
    if (dto.getMessage() == null) {
      dto.setMessage(nullReplace);
    }
  }
}