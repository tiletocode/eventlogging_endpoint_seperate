package com.hyundai.eventlogging2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class FilePrinterCloud {
	public void print(WebhookDto dto) throws IOException {

		Config config = Config.getConfig();

		String outFormat = config.getString("webhook.cloud.message.format","[#time][#projectname] [#level] #message");

		outFormat = StringUtils.replace(outFormat, "#title", dto.getTitle());
		outFormat = StringUtils.replace(outFormat, "#projectname", dto.getProjectName());
		outFormat = StringUtils.replace(outFormat, "#level", dto.getLevel());
		outFormat = StringUtils.replace(outFormat, "#oname", dto.getOname());
		outFormat = StringUtils.replace(outFormat, "#message", dto.getMessage());
		outFormat = StringUtils.replace(outFormat, "#time", StringUtils.formatDate(dto.getTime(),
		Config.getConfig().getString("webhook.cloud.message.date.format", "yyyy-MM-dd HH:mm:ss")));
		String dir = config.getString("webhook.cloud.file.path", "out/outputFile1");
		String extension = config.getString("webhook.cloud.file.extension", ".log");
		String fullPath = dir + extension;
		
		//PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File(fullPath), true));
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(fullPath), true), "UTF-8"));
		printWriter.println(outFormat);
		printWriter.close();
		
		printToday(outFormat);
	}
	
	private void printToday(String str) throws IOException {
		Config config = Config.getConfig();

		long currentTimestamp = System.currentTimeMillis();
		String dot = ".";
		String suffix = StringUtils.formatDate(currentTimestamp, config.getString("webhook.cloud.file.rolling.suffix", "yyyyMMdd"));
		String dir = config.getString("webhook.cloud.file.path", "out/outputFile1");
		String extension = config.getString("webhook.file.extension", ".log");
		String fullPath = dir + dot + suffix + extension;
		
		PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File(fullPath), true));
		printWriter.println(str);
		printWriter.close();
	}
}