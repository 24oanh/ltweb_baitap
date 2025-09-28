package week2.Dao;

import java.util.List;
import week2.model.Category;

public interface CategoryDao {
    List<Category> getAll();
    Category getById(int id);
    void insert(Category category);
    void update(Category category);
    void delete(int id);
}
