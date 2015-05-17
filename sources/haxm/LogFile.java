package haxm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * log file class
 *
 */
public class LogFile {
	/**
	 * root path of logs
	 */
	String basepath = "logs/";
	
	/**
	 * reference to the log file
	 */
	File logFile;
	/**
	 * writer for buffer
	 */
	BufferedWriter bufferedWriter;
	
	/**
	 * @param path path of file
	 * constructor
	 */
	public LogFile(String path){
		try{
			logFile = new File(basepath + path);
			logFile.createNewFile();
			bufferedWriter = new BufferedWriter(new FileWriter(logFile));
		}catch(IOException e){
			e.printStackTrace();
		}	
		
	}
	/**
	 * @param message message to be appended
	 * appends content to file
	 */
	public void append(String message){
		try {
			bufferedWriter.write(message);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  close the logfile
	 */
	public void close(){
		try {
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
