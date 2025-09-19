package TMDT.example.TMDT.Cart.Service;

import TMDT.example.TMDT.Cart.DTO.CartRequest;

public interface CartService {
    void addItemToCart(CartRequest cartRequest);
    void removeItemFromCart(Long variantId);
    void getAllItemsFromCart();
}
