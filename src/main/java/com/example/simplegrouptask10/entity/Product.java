package com.example.simplegrouptask10.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, message = "title must be min 2 symbol")
    private String title;

    @Min(value = 1, message = "price must be over 0")
    private Integer price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return title.equals(product.title) && price.equals(product.price);
    }

    //todo Не понимаю, почему хэшкод нужно считать только по названию и прайсу. То же самое про иквалс.
    // Я могу предположить, что ты их переопределил таким образом, чтобы реализовать метод добавления и обновления продукта.
    // Но это же частный случай, который может привести к одинаковости объектов с разными id.
    // Это может привести к нехилым проблемам при дальнейшем развитии приложения.
    // Например, мы добавляем в корзину продукт. Потом меняем его прайс. Снова добавляем в корзину этот продукт.
    // В итоге в корзине 2 разных ключа для продуктов с одинаковыми ид,
    // т.к. хэш у продуктов этих разный и они не одинаковы по иквалс. Может так и должно быть, но вряд ли.
    // Другой пример. Добавили продукт. Поменяли значения его атрибутов.
    // Другой продукт обновили до значений наименования и прайса, как у первого продукта. Добавили его в корзину.
    // Смотрим в корзине наличие продуктов по ид второго продукта, а его там и нет, ведь когда мы добавляли его в корзину,
    // инкрементировалось количество первого продукта в корзине, ведь у него такой же хэш и они одинаковы по иквалс.
    // И такие же траблы будут с методом удаления продукта из корзины.
    // Нужно подумать тут:
    // 1. возможно, в корзине нужно хранить только ид продукта, а не сам инстанс продукта.
    // 2. а возможно, нужно менять логику обновления продукта. И обновлять продукты не только в БД, но и в корзине,
    // если такие продукты в неё уже были добавлены.
    @Override
    public int hashCode() {
        return Objects.hash(title, price);
    }
}
