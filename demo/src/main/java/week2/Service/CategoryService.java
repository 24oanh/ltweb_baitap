package week2.Service;

import java.util.List;
import week2.model.Category;

public interface CategoryService {
    List<Category> getAll();
    Category getById(int id);
    boolean insert(Category category);
    public boolean update(Category category);
    boolean delete(int id);
	List<Category> findAll();
	public Category findById(int id);
	public boolean isNameExists(String name, int excludeId);
}
