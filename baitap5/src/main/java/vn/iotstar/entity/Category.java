package vn.iotstar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã code không được để trống")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Mã code chỉ chứa chữ hoa, số và dấu gạch dưới")
    @Column(unique = true)
    private String code;

    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;

    private String image;

    private Boolean active = true;

    // Constructors
    public Category() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}