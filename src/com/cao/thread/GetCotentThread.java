package com.cao.thread;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.cao.webcontent.Content;

public class GetCotentThread extends Thread {
	private String web;
	private String file;
	public GetCotentThread(String str,String file){
		this.web = str;
		this.file = file;
	}
	public void run() {
		
		Content content = new Content();
		PatternCompiler compiler = new Perl5Compiler();
		PatternMatcher matcher = new Perl5Matcher();
		Pattern pattern = null;
		String string = "<li\\s*class\\s*=.subject-item.*?</li>";
		try {
			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
			pattern = compiler.compile(string);
			String result = content.webContent(web);
            PatternMatcherInput  input = new PatternMatcherInput(result);
          
            while(matcher.contains(input, pattern)){
            	MatchResult  res = matcher.getMatch();
            	bWriter.write(res.toString());
            }
            bWriter.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (MalformedPatternException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
