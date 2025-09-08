package week2.Service.impl;

import week2.Dao.CategoryDao;
import week2.Dao.impl.CategoryDaoImpl;
import week2.Service.CategoryService;
import week2.model.Category;
import week2.Controller.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    @Override
    public Category getById(int id) {
        return categoryDao.getById(id);
    }

    @Override
    public boolean insert(Category category) {
        String sql = "INSERT INTO [category] (cate_name, icons) VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Set parameters
            ps.setString(1, category.getName());
            ps.setString(2, category.getImage());
            
            // Debug log
            System.out.println("Executing SQL: " + sql);
            System.out.println("Parameters: cate_name=" + category.getName() + ", icons=" + category.getImage());
            
            // Thực thi câu lệnh
            int rowsAffected = ps.executeUpdate();
            
            System.out.println("Rows affected: " + rowsAffected);
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("SQL Error in insert category: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("General Error in insert category: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public List<Category> findAll() {
	    List<Category> categories = new ArrayList<>();
	    String sql = "SELECT cate_id, cate_name, icons FROM [category] ORDER BY cate_id DESC";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        
	        System.out.println("Executing SQL: " + sql);
	        
	        while (rs.next()) {
	            Category category = new Category();
	            category.setId(rs.getInt("cate_id"));
	            category.setName(rs.getString("cate_name"));
	            category.setImage(rs.getString("icons"));
	            
	            categories.add(category);
	            
	            // Debug log
	            System.out.println("Found category: ID=" + category.getId() + 
	                             ", Name=" + category.getName() + 
	                             ", Image=" + category.getImage());
	        }
	        
	        System.out.println("Total categories found: " + categories.size());
	        
	    } catch (SQLException e) {
	        System.err.println("SQL Error in findAll: " + e.getMessage());
	        e.printStackTrace();
	    } catch (Exception e) {
	        System.err.println("General Error in findAll: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return categories;
	}
	
	// Trong CategoryServiceImpl.java, cần có các method:

	public Category findById(int id) {
	    Category category = null;
	    String sql = "SELECT cate_id, cate_name, icons FROM [category] WHERE cate_id = ?";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setInt(1, id);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                category = new Category();
	                category.setId(rs.getInt("cate_id"));
	                category.setName(rs.getString("cate_name"));
	                category.setImage(rs.getString("icons"));
	                
	                System.out.println("Found category: " + category.getName());
	            }
	        }
	        
	    } catch (Exception e) {
	        System.err.println("Error finding category by ID: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return category;
	}

	public boolean delete(int id) {
	    String sql = "DELETE FROM [category] WHERE cate_id = ?";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setInt(1, id);
	        
	        int rowsAffected = ps.executeUpdate();
	        
	        System.out.println("Delete operation affected " + rowsAffected + " rows");
	        
	        return rowsAffected > 0;
	        
	    } catch (Exception e) {
	        System.err.println("Error deleting category: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean update(Category category) {
	    String sql = "UPDATE [category] SET cate_name = ?, icons = ? WHERE cate_id = ?";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, category.getName());
	        ps.setString(2, category.getImage());
	        ps.setInt(3, category.getId());
	        
	        int rowsAffected = ps.executeUpdate();
	        
	        System.out.println("Update operation affected " + rowsAffected + " rows");
	        
	        return rowsAffected > 0;
	        
	    } catch (Exception e) {
	        System.err.println("Error updating category: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean isNameExists(String name, int excludeId) {
	    String sql = "SELECT COUNT(*) FROM [category] WHERE cate_name = ? AND cate_id != ?";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, name);
	        ps.setInt(2, excludeId);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	        
	    } catch (Exception e) {
	        System.err.println("Error checking name exists: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return false;
	}
}
