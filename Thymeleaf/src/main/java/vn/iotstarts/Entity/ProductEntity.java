package vn.iotstarts.Entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name", length = 200, columnDefinition = "nvarchar(200) not null")
    private String name;

    private Double price;

    // Nhiều sản phẩm thuộc về 1 category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false) // Khóa ngoại trỏ về CategoryEntity.categoryId
    private CategoryEntity category;
}
