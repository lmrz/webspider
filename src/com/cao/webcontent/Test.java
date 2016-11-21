package com.cao.webcontent;

import com.cao.book.Books;
import com.cao.util.SaveBooks;

public class Test {
	
	public static void main(String[] args) {
		new Books("互联网","互联网.text").start();
		new Books("算法", "算法.text").start();
		new Books("编程", "编程.text").start();
		new SaveBooks().getToExcel();
	}
     
}
