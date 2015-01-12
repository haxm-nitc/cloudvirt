package haxm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogFile {
	File logFile;
	String basepath = "logs/";
	BufferedWriter bufferedWriter;
	public LogFile(String path){
		try{
			logFile = new File(basepath + path);
			logFile.createNewFile();
			bufferedWriter = new BufferedWriter(new FileWriter(logFile));
		}catch(IOException e){
			e.printStackTrace();
		}	
		
	}
	public void append(String message){
		try {
			bufferedWriter.write(message);
			bufferedWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void close(){
		try {
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
