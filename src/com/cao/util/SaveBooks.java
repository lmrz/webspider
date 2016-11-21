package com.cao.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class SaveBooks {
	public void save(String name, String ratingnums, String pl,String type ) {
		Connection conn = null;
		String sql1 = "select name from book where name=?";
		String sql2 = "insert into cy.book values(?,?,?,?)";
		try {
			conn = connectDB.getConnectDB();

			PreparedStatement ps = conn.prepareStatement(sql1);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				PreparedStatement ps1 = conn.prepareStatement(sql2);
				ps1.setString(1, name);
				ps1.setFloat(2, Float.parseFloat(ratingnums));
				ps1.setInt(3, Integer.parseInt(pl));
				ps1.setString(4, type);
				ps1.executeUpdate();
				ps1.close();
			}
             rs.close();
			ps.close();
           conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getToExcel() {
		int row = 1;
		WritableWorkbook workbook;

		String sql = "select * from cy.book where pl>=2000 order by ratingnums desc";
		Connection conn = null;
		conn = connectDB.getConnectDB();
		try {
			OutputStream os = new FileOutputStream("总表.xls");
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("互联网", 0);
			Label label;
			label = new Label(0, 0, "书名");
			sheet.addCell(label);
			label = new Label(1, 0, "评分");
			sheet.addCell(label);
			label = new Label(2, 0, "评价人数");
			sheet.addCell(label);
			label = new Label(3, 0, "标签");
			sheet.addCell(label);
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				label = new Label(0, row, rs.getString("name"));
				sheet.addCell(label);
				label = new Label(1, row, String.valueOf(rs.getFloat("ratingnums")));
				sheet.addCell(label);
				label = new Label(2, row, String.valueOf(rs.getInt("pl")));
				sheet.addCell(label);
				label = new Label(3, row, rs.getString("type"));
				sheet.addCell(label);
				row++;
			}
			rs.close();
			ps.close();
			workbook.write();
			workbook.close();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
System.out.println("导入excel成功！行数："+(row-1));
	}
	
}
