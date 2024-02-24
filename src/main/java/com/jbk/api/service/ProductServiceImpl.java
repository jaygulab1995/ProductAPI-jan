package com.jbk.api.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jbk.api.dao.ProductDao;
import com.jbk.api.entity.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao dao;
	int totalProductInSheet = 0;

	@Override
	public boolean saveProduct(Product product) {

		boolean isAdded = dao.saveProduct(product);
		return isAdded;
	}

	@Override
	public List<Product> getAllProduct() {

		List<Product> list = dao.getAllProduct();
		return list;
	}

	@Override
	public boolean updateProduct(Product product) {
		boolean isUpdated = dao.updateProduct(product);
		return isUpdated;
	}

	@Override
	public boolean deleteProduct(int productId) {
		boolean isDeleted = dao.deleteProduct(productId);
		return isDeleted;
	}

	@Override
	public Product getProductById(int productId) {
		Product product = dao.getProductById(productId);
		return product;
	}

	@Override
	public List<Product> getMaxPriceProducts() {
		List<Product> maxPriceProducts = dao.getMaxPriceProducts();
		return maxPriceProducts;
	}

	public List<Product> readExcel(String path) {

		Product product = null;
		List<Product> list = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream(new File(path));

			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);

			totalProductInSheet = sheet.getLastRowNum();
			
			Iterator<Row> rows = sheet.rowIterator();
			int cnt = 0;

			while (rows.hasNext()) {
				Row row = rows.next();
				product = new Product();
				if (cnt == 0) {
					cnt = cnt + 1;
					continue;
				}

				//System.out.println("row" + row.getRowNum());
				
				Iterator<Cell> cells = row.cellIterator();

				while (cells.hasNext()) {

					Cell cell = cells.next();
					int col = cell.getColumnIndex();

					//System.out.println("col" + col);
					
					switch (col) {
					case 0: {
						product.setProductId((int) cell.getNumericCellValue());
						
						break;
					}

					case 1: {
							product.setProductName(cell.getStringCellValue());
						break;
					}

					case 2: {
							product.setProductQty((int)cell.getNumericCellValue());
						break;
					}

					case 3: {
						product.setProductPrice(cell.getNumericCellValue());
						break;
					}

					case 4: {
						product.setProductType(cell.getStringCellValue());
						break;
					}

					default:
						break;
					}
				}
				
				list.add(product);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String uploadSheet(MultipartFile file, HttpSession session) {
		int addedCount = 0;
		String path = session.getServletContext().getRealPath("/uploaded");
		String fileName = file.getOriginalFilename();

		try {
			byte[] data = file.getBytes();
			FileOutputStream fos = new FileOutputStream(new File(path + File.separator + fileName));// File.separator =
																									// "/"
			fos.write(data);

			List<Product> list = readExcel(path + File.separator + fileName);
			
			for (Product product : list) {
				System.out.println(product);
			}
			
			addedCount = dao.excelToDb(list);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Total product in sheet: "+totalProductInSheet+" and Added product count: "+ addedCount;
	}

}
