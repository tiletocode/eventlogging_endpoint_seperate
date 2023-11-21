package com.hyundai.eventlogging2;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Scheduler {
    
	@Scheduled(cron = "0 0 0 * * ?")	
	public void cronJob1() {
		Config config = Config.getConfig();
		try {
			log.info("[File Rolling CronJob] is running");
			
			String dir = config.getString("webhook.cloud.file.path", "out/outputFile1");
			String extension = config.getString("webhook.cloud.file.extension", ".log");
			
			String originPath = dir + extension;
			File printFile = new File(originPath);
			if(printFile.exists()) {
				Files.write(Paths.get(originPath), new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
			}
			
			long currentTimestamp = System.currentTimeMillis();
			long oneDay = 1000L * 60 * 60 * 24;
			int targetSize = config.getInt("webhook.cloud.file.rolling.size", 10);
			if(targetSize < 2) {
				log.warn("webhook.cloud.file.rolling.size set to default value : 2");
				targetSize = 2;
			}
			
			long targetTimestamp = currentTimestamp - (oneDay * targetSize);
			
			String dot = ".";
			String suffix = StringUtils.formatDate(targetTimestamp, config.getString("webhook.cloud.file.rolling.suffix", "yyyyMMdd"));
			String targetFilePath = dir + dot + suffix + extension;
			File targetFile = new File(targetFilePath);
			if(targetFile.exists()) {
				targetFile.delete();
			}
			
			log.info("[File Rolling CronJob] finished");
		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void cronJob2() {
		Config config = Config.getConfig();
		try {
			log.info("[File Rolling CronJob] is running");
			
			String dir = config.getString("webhook.api.file.path", "out/outputFile2");
			String extension = config.getString("webhook.api.file.extension", ".log");
			
			String originPath = dir + extension;
			File printFile = new File(originPath);
			if(printFile.exists()) {
				Files.write(Paths.get(originPath), new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
			}
			
			long currentTimestamp = System.currentTimeMillis();
			long oneDay = 1000L * 60 * 60 * 24;
			int targetSize = config.getInt("webhook.api.file.rolling.size", 10);
			if(targetSize < 2) {
				log.warn("webhook.api.file.rolling.size set to default value : 2");
				targetSize = 2;
			}
			
			long targetTimestamp = currentTimestamp - (oneDay * targetSize);
			
			String dot = ".";
			String suffix = StringUtils.formatDate(targetTimestamp, config.getString("webhook.api.file.rolling.suffix", "yyyyMMdd"));
			String targetFilePath = dir + dot + suffix + extension;
			File targetFile = new File(targetFilePath);
			if(targetFile.exists()) {
				targetFile.delete();
			}
			
			log.info("[File Rolling CronJob] finished");
		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
}