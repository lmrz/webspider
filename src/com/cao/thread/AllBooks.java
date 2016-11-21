package com.cao.thread;

public class AllBooks extends Thread {
    private String type;
    private String savePlace;
    public AllBooks( String type,String  save) {
    this.type=type;
    this.savePlace = save;
    }
	public void run() {

		for (int i = 0; i <= 600; i += 20) {
			String string = "https://book.douban.com/tag/"+type+"?start=" + i + "&type=T";
			GetCotentThread algbook = new GetCotentThread(string, savePlace);
			algbook.start();
		}
	}

}
