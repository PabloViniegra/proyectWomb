package net.juanxxiii.demo.services;

import lombok.extern.java.Log;
import net.juanxxiii.demo.database.entities.*;
import net.juanxxiii.demo.database.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Log
public class QueryService {

    private final CountriesRepository countriesRepository;
    private final UsersRepository usersRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductsRepository productsRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public QueryService(CountriesRepository countriesRepository,
                        UsersRepository usersRepository,
                        CategoriesRepository categoriesRepository,
                        ProductsRepository productsRepository,
                        BrandRepository brandRepository) {
        this.countriesRepository = countriesRepository;
        this.usersRepository = usersRepository;
        this.categoriesRepository = categoriesRepository;
        this.productsRepository = productsRepository;
        this.brandRepository = brandRepository;
    }

    public List<Countries> getCountriesList() {
        return countriesRepository.findAll();
    }

    public Countries getCountry(int id) {
        return countriesRepository.findById(id).orElse(null);
    }

    public Countries getCountryByName(String name) {
        return countriesRepository.findByName(name);
    }

    public List<Users> getUsersList() {
        return usersRepository.findAll();
    }

    public Users getUser(int id) {
        return usersRepository.findById(id).orElse(null);
    }

    public Users saveUser(Users newuser) {
        if (newuser.getCountry() != null) {
            Users user = usersRepository.findById(newuser.getCountry().getId()).orElse(null);
            newuser.setCountry(user.getCountry());
        }
        return usersRepository.save(newuser);

    }

    public int updateUsers(Users newuser, int id) {
        return usersRepository.findById(id).map(user -> {
            if (newuser.getCountry() != null) {
                Countries country = countriesRepository.findById(newuser.getCountry().getId()).orElse(null);
                newuser.setCountry(country);
            }
            return usersRepository.updateUser(newuser.getEmail(),newuser.getLastname(), newuser.getName(), newuser.getPassword(), newuser.getUsername(), newuser.getId());
        }).orElse(-1);
    }

    public void deleteUser(int id) {
        usersRepository
                .delete(Objects
                        .requireNonNull(usersRepository
                                .findById(id)
                                .orElse(null)));
    }


    public List<Categories> getCategoriesList() {
        return categoriesRepository.findAll();
    }

    public Categories getCategory(int id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    public Categories getCategoryByName(String name) {
        return categoriesRepository.findCategoryByName(name);
    }

    public Categories saveCategory(Categories newcategory) {
        return categoriesRepository.save(newcategory);
    }

    public void deleteCategory(int id) {
        categoriesRepository
                .delete(Objects
                        .requireNonNull(categoriesRepository
                                .findById(id)
                                .orElse(null)));
    }

    public List<Products> getProductsList() {
        return productsRepository.findAll();
    }

    public Products getProduct(int id) {
        return productsRepository.findById(id).orElse(null);
    }

    public Products saveProduct(Products newproduct) {
        if (newproduct.getCategory() != null) {
            Categories category = categoriesRepository.findById(newproduct.getCategory().getId()).orElse(categoriesRepository.save(newproduct.getCategory()));
            newproduct.setCategory(category);
        }
        if (newproduct.getBrand() != null) {
            Brand brand = brandRepository.findById(newproduct.getBrand().getId()).orElse(brandRepository.save(newproduct.getBrand()));
            newproduct.setBrand(brand);
        }
        return productsRepository.save(newproduct);
    }

    public void deleteProduct(int id) {
        productsRepository
                .delete(Objects
                        .requireNonNull(productsRepository
                                .findById(id)
                                .orElse(null)));
    }

    public int updateProducts(Products product, int id) {
        return productsRepository.findById(id).map(prod -> {
            if (product.getCategory() != null) {
                Categories categories = categoriesRepository.findById(product.getCategory().getId()).orElse(null);
                product.setCategory(categories);
            }
            if (product.getBrand() != null) {
                Brand brand = brandRepository.findById(product.getBrand().getId()).orElse(null);
                product.setBrand(brand);
            }
            return productsRepository.updateProduct(product.getName(),product.getImage(), product.getId());
        }).orElse(-1);
    }

    public List<Brand> getBrandList() {
        return brandRepository.findAll();
    }

    public Brand getBrand(int id) {
        return brandRepository.findById(id).orElse(null);
    }

    public Brand getBrandByName(String name) {
        return brandRepository.getBrandByName(name);
    }

    public Brand saveBrand(Brand newbrand) {
        return brandRepository.save(newbrand);
    }

    public void deleteBrand(int id) {
        brandRepository
                .delete(Objects
                        .requireNonNull(brandRepository
                                .findById(id)
                                .orElse(null)));
    }


}
