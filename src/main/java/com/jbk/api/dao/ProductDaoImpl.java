package com.jbk.api.dao;

import java.util.List;
import com.jbk.api.entity.Product;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	SessionFactory sessionFactory;

	// Save Product
	@Override
	public boolean saveProduct(Product product) {

		boolean isAdded = false;
		Session session = sessionFactory.openSession();
		try {

			Product prd = session.get(Product.class, product.getProductId());
			if (prd == null) {
				Transaction transaction = session.beginTransaction();
				session.save(product);
				transaction.commit();
				isAdded = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return isAdded;

	}

	// Get Product
	@Override
	public List<Product> getAllProduct() {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Product.class);
		List<Product> list = null;
		try {
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	// Update Product
	@Override
	public boolean updateProduct(Product product) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		boolean isUpdated = false;
		try {
			Product existingProduct = session.get(Product.class, product.getProductId());

			if (existingProduct != null) {

				session.evict(existingProduct);
				session.update(product);
				transaction.commit();
				isUpdated = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isUpdated;
	}

	// Delete Product
	@Override
	public boolean deleteProduct(int productId) {
		boolean isDeleted = false;
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			Product product = session.get(Product.class, productId);
			if (product != null) {
				session.delete(product);
				transaction.commit();
				isDeleted = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isDeleted;
	}

	@Override
	public Product getProductById(int productId) {
		Session session = sessionFactory.openSession();
		Product product = null;
		try {
			product = session.get(Product.class, productId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return product;
	}

	@Override
	public List<Product> getMaxPriceProducts() {
		Session session = sessionFactory.openSession();
		List<Product> maxProducts = null;
		double maxPrice = 0;
		try {
			Criteria  maxCriteria = session.createCriteria(Product.class);
			Criteria  eqCriteria = session.createCriteria(Product.class);
			maxCriteria.setProjection(Projections.max("productPrice"));
			List<Double> list = maxCriteria.list();
			maxPrice = list.get(0);
			
			eqCriteria.add(Restrictions.eq("productPrice", maxPrice));
			maxProducts = eqCriteria.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null) {
				session.close();
			}
		}
		return maxProducts;
	}

	@Override
	public int excelToDb(List<Product> list) {
		int addedCount = 0;
		for (Product product : list) {
			boolean isAdded = saveProduct(product);
			
			if(isAdded) {
				addedCount = addedCount+1;
			}
		}
		return addedCount;
	}

}
