package com.cao.book;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.cao.thread.AllBooks;
import com.cao.util.SaveBooks;


public class Books extends Thread {
	int count = 0;
	private static int row = 1;// 行数
	private String sfile;// 把网页上的内书籍内容存入text文件（未筛选）；
	private String type;// 抓取的书籍类别

	public Books(String type, String sfile) {
		this.type = type;
		this.sfile = sfile;
	}

	@Override
	public void run() {
		File file = new File(sfile);

		if (!file.exists() || file.length() < 1) {
			AllBooks books = new AllBooks(type, sfile);
			books.start();
			try {
				Thread.sleep(10000);// 设置当前线程休眠时间（等待将数据写入text文件）
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 用正则表达式筛选书籍信息
		PatternCompiler compiler = new Perl5Compiler();
		PatternMatcher matcher = new Perl5Matcher();
		Pattern pattern = null;
		String regex = ".*?title\\s*=\\s*\"([^\"]*)\".*?rating_nums\\s*\">([^<]*)<.*?([0-9]{1,9})人评价";
		String content = "";
		try {
			pattern = compiler.compile(regex);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sfile)));
			String line;
			while ((line = reader.readLine()) != null) {
				content += line.toString();
			}
			PatternMatcherInput input = new PatternMatcherInput(content);

			while (matcher.contains(input, pattern)) {
				MatchResult result = matcher.getMatch();
				//同步保护
				synchronized (this) {
					new SaveBooks().save(result.group(1).toString(), result.group(2).toString(), result.group(3).toString(),type);
					Books.row++;
				}
				count++;
			}
			
			System.out.println("总书本:"+row);
			reader.close();
		} 
		catch (MalformedPatternException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
