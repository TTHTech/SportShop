package com.example.sportshop.service;


import com.example.sportshop.model.Cart;
import com.example.sportshop.model.Customer;
import com.example.sportshop.model.Item;
import com.example.sportshop.model.Product;

import com.example.sportshop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ProductService productService;

    public List<Cart> getAllCarts(){
        return cartRepository.findAll();
    }

    public Cart getCart(long id){
        return this.cartRepository.findCartById(id);
    }

    public void saveCart(Cart cart){
        cartRepository.save(cart);
    }

    public void updateQuantityCart(long cartId, long itemId, int hieuso){
        Cart cart = getCart(cartId);
        Item item = this.itemService.getItemById(itemId);
        System.out.println(">>>check cart: " + cart.getItems());
        System.out.println(">>>check this item: " + item.getProduct().getName());

        int sl = item.getQuantity()+ hieuso;
        if (sl < 0) {
            sl = 1;
        }
        if (sl == 0)
        {
            deleteItemById(cartId, itemId);
            this.cartRepository.save(cart);
            return;
        }

        item.setQuantity(sl);
        this.itemService.saveItem(item);
        this.cartRepository.save(cart);
    }

    public void deleteItemById(long cartId, long itemId) {
        Cart cart = getCart(cartId);
        if (cart != null) {
            // Tim Item can xoa trong Cart
            Item itemToRemove = null;
            for (Item item : cart.getItems()) {
                if (item.getId() == itemId) {
                    itemToRemove = item;
                    break;
                }
            }

            if (itemToRemove != null) {
                cart.getItems().remove(itemToRemove);
                itemService.deleteById(itemId);
                cartRepository.save(cart);
            } else {
                throw new IllegalArgumentException("Item with ID " + itemId + " does not exist in the cart.");
            }
        } else {
            throw new IllegalArgumentException("Cart with ID " + cartId + " does not exist.");
        }
    }

    public void addToCart(long cartId, long productId){
        Cart cart = cartRepository.findCartById(cartId);
        Product product = productService.getProductById(productId);

        long itemId = containsItem(cart, productId);
        //da ton tai san pham nay trong list item
        if ( itemId != 0){
            System.out.println("San pham da ton tai trong gio hang -> tang so luong len 1");
            updateQuantityCart(cartId, itemId, 1);
        } else {
            System.out.println("San pham chua ton tai trong gio hang -> tao moi");
            Item item = new Item(product, 1);
            cart.getItems().add(item);
            itemService.saveItem(item);
            cartRepository.save(cart);
        }
    }

    public long containsItem(Cart cart, long productId) {
        for (Item item : cart.getItems()) {
            if (item.getProduct().getId() == productId) {
                return item.getId();
            }
        }
        return 0;
    }

    //tìm cái cart bằng customer (khi đăng nhập thì tìm cái cart cho nó)
    public Cart findCart(Customer customer){
        List<Cart> allCarts = getAllCarts();
        for (Cart cart : allCarts){
            if (customer.getId() == cart.getCustomer().getId()){
                return cart;
            }
        }
        return null;
    }

}
