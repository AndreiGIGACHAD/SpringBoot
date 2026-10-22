package ru.example.electroshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.example.electroshop.entity.*;
import ru.example.electroshop.repository.*;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(UserRepository users, CategoryRepository categories, ProductRepository products, PasswordEncoder enc) {
        return args-> {
            AppUser admin=users.findByUsername("admin").orElseGet(()->users.save(new AppUser("admin",enc.encode("admin12345"),Role.ADMIN)));
            if(categories.count()==0) {
                String[][] data= {
                    {
                        "Бытовая техника",
                        "Холодильники, стиральные машины, пылесосы и техника для дома"
                    },
                    {
                        "Цифровые товары",
                        "Смартфоны, планшеты, часы и аксессуары"
                    },
                    {
                        "Комплектующие ПК",
                        "Процессоры, видеокарты, память и накопители"
                    },
                    {
                        "Компьютеры и периферия",
                        "Ноутбуки, мониторы, клавиатуры и мыши"
                    },
                    {
                        "Аудио и видео",
                        "Наушники, колонки, телевизоры и проекторы"
                    },
                    {
                        "Игры и развлечения",
                        "Игровые консоли, контроллеры и VR"
                    }
                };
                for(String[] d:data) {
                    Category c=new Category();
                    c.setName(d[0]);
                    c.setDescription(d[1]);
                    c.setCreatedBy(admin);
                    categories.save(c);
                }
            }
            if(products.count()==0) {
                seedProduct(products,categories,admin,"Робот-пылесос CleanBot X5","Xiaomi","X5","HOME-1001","Бытовая техника",24990,12,"Лазерная навигация, влажная уборка");
                seedProduct(products,categories,admin,"Смартфон Nova Pro","Samsung","Nova Pro","DIG-2001","Цифровые товары",79990,8,"Экран AMOLED, 256 ГБ памяти");
                seedProduct(products,categories,admin,"Видеокарта RTX 5070","MSI","RTX 5070 Ventus","PC-3001","Комплектующие ПК",84990,5,"Видеокарта для игровых и рабочих ПК");
                seedProduct(products,categories,admin,"Монитор UltraView 27","AOC","U27G","PER-4001","Компьютеры и периферия",32990,9,"27 дюймов, высокое разрешение");
                seedProduct(products,categories,admin,"Наушники Pulse ANC","Sony","Pulse ANC","AV-5001","Аудио и видео",15990,20,"Беспроводные наушники с шумоподавлением");
                seedProduct(products,categories,admin,"Игровой контроллер ProPad","Logitech","ProPad 2","GAME-6001","Игры и развлечения",6990,15,"Беспроводной контроллер");
            }
        };
    }
    private void seedProduct(ProductRepository repo,CategoryRepository cats,AppUser admin,String name,String brand,String model,String sku,String category,double price,int stock,String description) {
        Product p=new Product();
        p.setName(name);
        p.setBrand(brand);
        p.setModel(model);
        p.setSku(sku);
        p.setCategory(cats.findAll().stream().filter(c->c.getName().equals(category)).findFirst().orElseThrow());
        p.setPrice(price);
        p.setStock(stock);
        p.setDescription(description);
        p.setCreatedBy(admin);
        repo.save(p);
    }
}
