package richbar.com.github.treasurepyramid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;

import java.time.LocalDateTime;

/**
 * Created by Rich Y on 03.08.2017.
 */
public class Logger {
	
	private ApplicationLogger logger;
	private LogLevel loglevel = LogLevel.NORMAL;
	boolean fixedLevel = false, centered = false;
	
	Logger(ApplicationLogger logger){
		this.logger = logger;
		init();
	}
	
	Logger(ApplicationLogger logger, LogLevel level){
		this.logger = logger;
		loglevel = level;
		init();
	}
	
	public enum LogLevel {DEBUG, NORMAL, WARN, ERROR}
	void setLogLevel(LogLevel level){
		loglevel = level;
		switch(loglevel){
			case DEBUG:
				Gdx.app.setLogLevel(Application.LOG_DEBUG);
				break;
			case NORMAL:
				Gdx.app.setLogLevel(Application.LOG_NONE);
				break;
			case WARN:
				Gdx.app.setLogLevel(Application.LOG_INFO);
				break;
			case ERROR:
				Gdx.app.setLogLevel(Application.LOG_ERROR);
				break;
		}
	}
	
	public ApplicationLogger getLogger() {
		return logger;
	}
	
	public void log(String msg){
		log(loglevel, msg);
	}
	
	public void log(LogLevel level, String msg){
		String[] splitMsg = msg.split("\r\n|\r|\n");
		int linecount = splitMsg.length;
		for(int x = 0; x < linecount; x++) {
			int i = 0, lastSpace;
			String line;
			while (i < splitMsg[x].length()) {
				line = splitMsg[x].substring(i, Math.min(i + 79, splitMsg[x].length()));
				lastSpace = line.length();
				if(line == null) continue;
				else if(line.equals("|-")) {
					line();
					i+=2;
					continue;
				} else if(line.equals("|v")) {
					rip();
					i+=2;
					continue;
				} else if(line.equals("||")) {
					line();
					i+=2;
					continue;
				} else if(line.equals("|c")) {
					i+=2;
					centered = !centered;
					continue;
				} else if(line.equals("|")) {
					i+=1;
					plog("                                                                                 ");
					continue;
				}
				if(splitMsg[x].length() - i > 79) {
					lastSpace = line.lastIndexOf(" ");
					if (lastSpace <= 0) lastSpace = line.length();
				}
				line = line.substring(0, lastSpace);
				
				if(centered) {
					line = new String(new char[(80 - lastSpace)/2]).replace("\0", " ") + line;
					line += new String(new char[(79 - lastSpace)/2]).replace("\0", " ");
				}else
					line += new String(new char[79 - lastSpace]).replace("\0", " ");
				
				plog( (centered?" ":"  ") + line + (centered?" ":""), level);
				
				i += lastSpace;
			}
		}
		centered = false;
	}
	
	private void plog(String msg){
		plog(msg, loglevel);
	}
	
	private void plog(String msg, LogLevel level){
		switch(fixedLevel?loglevel : level){
			case DEBUG:
				logger.debug(getLoggerTag(), "|" + msg + "|");
				break;
			case NORMAL:
			case WARN:
				logger.log(getLoggerTag(), "|" + msg + "|");
				break;
			case ERROR:
				logger.error(getLoggerTag(), "|" + msg + "|");
				break;
		}
	}
	
	public String getLoggerTag() {
		return "<" + LocalDateTime.now() + (loglevel.equals(LogLevel.NORMAL)? "": "> " + loglevel.name()) + "> \\ Treasure Pyramid /";
	}
	
	void init(){
		plog( "|-------------------------------------------------------------------------------|");
		plog( "        ______)                              _____                               ");
		plog( "       (, /                                 (, /   )                   ,   /)    ");
		plog( "         /   __   _  _   _       __   _      _/__ /      __  _  ___      _(/     ");
		plog( "      ) /   / (__(/_(_(_/_)_(_(_/ (__(/_     /      (_/_/ (_(_(_// (__(_(_(_     ");
		plog( "     (_/                                  ) /      .-/                           ");
		plog( "                                         (_/      (_/                            ");
		plog( "|-------------------------------------------------------------------------------|");
		plog( "|      Developer & Creator Marco Dittrich - https://www.patreon.com/RichY       |");
		plog( "|                                                                               |");
		plog( "|  Version: 0.0.1A                                                              |");
		plog( "|||---------------------------------------------------------------------------|||");
		plog( "                                                                                 ");
		plog( "  Other Developers:                                                              ");
		plog( "                                                                                 ");
		plog( "|||---------------------------------------------------------------------------|||");
	}
	
	void destroy(){
		plog( "                                                                                 ");
		plog( "                                       EOG                                       ");
		rip();
	}
	
	public void rip(){
		plog("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
	}
	
	public void line(){
		plog("|-------------------------------------------------------------------------------|");
	}
	
	public void line2(){
		plog("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
	}
	
}
