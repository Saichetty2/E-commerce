package com.cart.service;

import com.cart.client.ProductClient;
import com.cart.client.SecurityClient;
import com.cart.client.UserClient;
import com.cart.dtos.CartDto;
import com.cart.dtos.CartItemDto;
import com.cart.dtos.ProductDto;
import com.cart.dtos.UserDto;
import com.cart.entities.Cart;
import com.cart.entities.CartItem;
import com.cart.exceptions.CartException;
import com.cart.exceptions.ProductNotFoundException;
import com.cart.exceptions.UserNotFoundException;
import com.cart.repository.CartItemRepository;
import com.cart.repository.CartRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartRepository cartRepository;
 
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductClient productServiceClient;

    @Autowired
    private UserClient userServiceClient;
    
    @Autowired
    private SecurityClient securityClient;

//    @Override
//    public CartDto addItemToCart(String userId, String productId, CartItemDto request) {
//        logger.info("Adding item to cart for userId: {} and productId: {}", userId, productId);
// 
//        // Validate user existence
//        UserDto user;
//        try {
//            user = userServiceClient.getUserById(userId);
//            logger.debug("Fetched user details: {}", user);
//        } catch (Exception e) {
//            logger.error("User not found with ID: {}", userId, e);
//            throw new UserNotFoundException("User not found with ID: " + userId);
//        }
//
//        // Validate product existence
//        ProductDto product;
//        try {
//            product = productServiceClient.getById(productId);
//            logger.debug("Fetched product details: {}", product);
//        } catch (Exception e) {
//            logger.error("Product not found with ID: {}", productId, e);
//            throw new ProductNotFoundException("Product not found with ID: " + productId);
//        }
//
//        // Proceed with cart addition logic
//        Cart cart = cartRepository.findByUserId(userId);
//        if (cart == null) {
//            logger.info("No existing cart found for userId: {}. Creating a new cart.", userId);
//            cart = new Cart();
//            cart.setUserId(userId);
//            cart.setCartCreatedTime(LocalDateTime.now());
//           
//            cart.setItems(new ArrayList<>());
//        }
//
//        Optional<CartItem> existingItemOpt = cart.getItems().stream()
//                .filter(item -> item.getProductId().equals(productId))
//                .findFirst();
//
//        if (existingItemOpt.isPresent()) {
//            CartItem existingItem = existingItemOpt.get();
//            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
//            existingItem.setProductPrice(product.getProductPrice());
//            logger.info("Updated existing item in cart for productId: {}", productId);
//        } else {
//            CartItem newCartItem = new CartItem();
//            newCartItem.setProductId(productId);
//            newCartItem.setProductTitle(product.getProductTitle());
//            newCartItem.setQuantity(request.getQuantity());
//            newCartItem.setProductPrice(product.getProductPrice());
//            newCartItem.setCart(cart);
//            cart.getItems().add(newCartItem);
//            logger.info("Added new item to cart for productId: {}", productId);
//        }
//
//        cart.setTotalCartValue(
//                cart.getItems().stream().mapToDouble(item -> item.getProductPrice() * item.getQuantity()).sum());
//
//        Cart savedCart = cartRepository.save(cart);
//        logger.info("Cart saved successfully for userId: {}", userId);
//        return mapToDto(savedCart);
//    }
    
  @Override
  public CartDto addItemToCart(String userId, String productId, CartItemDto request) {
      logger.info("Adding item to cart for userId: {} and productId: {}", userId, productId);

      System.out.println("This is cart service impl class: "+ userId);
      // Validate user existence
      UserDto user;
      try {
          user = userServiceClient.getUserById(userId);
          logger.debug("Fetched user details: {}", user);
      } catch (Exception e) {
          logger.error("User not found with ID: {}", userId, e);
          throw new UserNotFoundException("User not found with ID: " + userId);
      }

      // Validate product existence
      ProductDto product;
      try {
          product = productServiceClient.getById(productId);
          logger.debug("Fetched product details: {}", product);
      } catch (Exception e) {
          logger.error("Product not found with ID: {}", productId, e);
          throw new ProductNotFoundException("Product not found with ID: " + productId);
      }

      // Proceed with cart addition logic
      Cart cart = cartRepository.findByUserId(userId);
      if (cart == null) {
          logger.info("No existing cart found for userId: {}. Creating a new cart.", userId);
          cart = new Cart();
          cart.setUserId(userId);
          cart.setCartCreatedTime(LocalDateTime.now());
         
          cart.setItems(new ArrayList<>());
      }

      Optional<CartItem> existingItemOpt = cart.getItems().stream()
              .filter(item -> item.getProductId().equals(productId))
              .findFirst();

      if (existingItemOpt.isPresent()) {
          CartItem existingItem = existingItemOpt.get();
          existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
          existingItem.setProductPrice(product.getProductPrice());
          logger.info("Updated existing item in cart for productId: {}", productId);
      } else {
          CartItem newCartItem = new CartItem();
          newCartItem.setProductId(productId);
          newCartItem.setProductTitle(product.getProductTitle());
          newCartItem.setQuantity(request.getQuantity());
          newCartItem.setProductPrice(product.getProductPrice());
          newCartItem.setCart(cart);
          cart.getItems().add(newCartItem);
          logger.info("Added new item to cart for productId: {}", productId);
      }

      cart.setTotalCartValue(
              cart.getItems().stream().mapToDouble(item -> item.getProductPrice() * item.getQuantity()).sum());
      
      System.out.println("CART quantity: "+ cart.getTotalCartValue());
      
      Cart savedCart = cartRepository.save(cart);
      
      System.out.println("This is Cart checking: "+ savedCart.getTotalCartValue()+" "+ savedCart.getItems());
      logger.info("Cart saved successfully for userId: {}", userId);
      return mapToDto(savedCart);
  }
  

    @Override
    public CartDto getCartByUserId(String userId) {
        logger.info("Fetching cart for userId: {}", userId);

        UserDto user;
        try {
            user = userServiceClient.getUserById(userId);
            logger.debug("Fetched user details: {}", user);
        } catch (Exception e) {
            logger.error("Error fetching user with ID: {}", userId, e);
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        Cart cart = cartRepository.findByUserId(userId);
        
        if (cart == null) {
            logger.warn("Your cart is waiting for your items.... No items in the Cart you can add now... Cart not found for userId: {}", userId);
            throw new CartException("Your cart is waiting for your items.... No items in the Cart you can add now...Cart not found for user ID: " + userId);
        }

        CartDto cartDto = mapToDto(cart);
        logger.info("Cart fetched successfully for userId: {}", userId);

//        UserDto userDto = new UserDto();
//        userDto.setUserId(user.getUserId());
//        userDto.setFirstName(user.getFirstName());
//        userDto.setLastName(user.getLastName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPhoneNumber(user.getPhoneNumber());
//        userDto.setCartDto(cartDto);

        return cartDto;
    }

    @Override
    public Cart getCartByCartId(long cartId) {
        logger.info("Fetching cart by cartId: {}", cartId);
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> {
            logger.error("Cart not found with cartId: {}", cartId);
            return new CartException("Cart not found with cart ID: " + cartId);
        });
        logger.info("Cart fetched successfully for cartId: {}", cartId);
        return cart;
    }

    @Override
    public List<Cart> getAllCart() {
        logger.info("Fetching all carts");
        List<Cart> carts = cartRepository.findAll();
        logger.info("Fetched {} carts", carts.size());
        return carts;
    }

    @Override
    public void deleteSingleItem(long cartItemId) {
        logger.info("Deleting single cart item with cartItemId: {}", cartItemId);
        cartItemRepository.deleteById(cartItemId);
        logger.info("Cart item deleted successfully with cartItemId: {}", cartItemId);
    }
    
    
//    CART ITEMS DELETION BASED ON USERS CART BY USERID
    public CartDto deleteCartItemForUser(String userId, long cartItemId) {
        logger.info("Deleting cart item for user ID: {} with cartItemId: {}", userId, cartItemId);

        // Fetch the cart by user ID
        CartDto cartByUserId = getCartByUserId(userId);

        /* below lines can't hit the delete function thats why it is in comment mode 
        
        // Check if the cart item exists in the cart
        boolean itemExists = cartByUserId.getItems().stream()
            .anyMatch(item -> item.getCartItemId() == cartItemId);

        if (!itemExists) {
            throw new EntityNotFoundException("Cart item with ID " + cartItemId + " not found in your cart. Please refresh the page.");
        }
        
        */

        // Delete the cart item
        cartItemRepository.deleteById(cartItemId);
        logger.info("Cart item with ID: {} deleted successfully from the database.", cartItemId);

        // Fetch the updated cart items after deletion
       Cart findByCartId = cartRepository.findByCartId(cartByUserId.getCartId());
       
       List<CartItem> items = findByCartId.getItems();
       
       double updatedTotalAmount = findByCartId.getItems().stream().mapToDouble(item -> item.getProductPrice() * item.getQuantity()).sum();

     
        cartByUserId.setTotalAmount(updatedTotalAmount);

        // Check if the cart is empty and delete the cart if needed
        if (items.isEmpty()) {
            cartRepository.deleteById(cartByUserId.getCartId());
            logger.info("Cart with ID: {} deleted as it is empty.", cartByUserId.getCartId());
        }

        // Return the updated cart
        return cartByUserId;
    }
    
    
//    REDUCE QUANTITY OF THE CARTITEMS OF USERS CART
    public CartDto reduceCartItemQuantityForUser(String userId, long cartItemId) {
        logger.info("Reducing quantity for cart item with ID: {} for user ID: {}", cartItemId, userId);

        // Fetch the cart by user ID
        CartDto cartByUserId = getCartByUserId(userId);

        // Fetch the cart item by cartItemId
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);

        if (cartItemOpt.isEmpty()) {
            throw new EntityNotFoundException("Cart item with ID " + cartItemId + " not found in the cart.");
        }

        CartItem cartItem = cartItemOpt.get();

        // Reduce the quantity or delete the cart item if quantity is 1
        if (cartItem.getQuantity() > 1) {
        	
        		 cartItem.setQuantity(cartItem.getQuantity() - 1);
        	
            cartItemRepository.save(cartItem); // Save the updated quantity
            logger.info("Quantity for cart item with ID: {} reduced to {}", cartItemId, cartItem.getQuantity());
        } else {
        	
            logger.info("Cart item with ID: {} not reduced because as its quantity was 1.", cartItemId);

//            If we want exception use below, as of now i want item with atleast 1 qty
//            so, goes goes out of the else loop automatically
            /* throw new IllegalArgumentException("Cart item with ID: {} not reduced because as its quantity was 1..");
     
        */}

        // Fetch the updated cart details
        Cart findByCartId = cartRepository.findByCartId(cartByUserId.getCartId());
        List<CartItem> items = findByCartId.getItems();

        // Recalculate the total amount
        double updatedTotalAmount = items.stream()
            .mapToDouble(item -> item.getProductPrice() * item.getQuantity())
            .sum();
        cartByUserId.setTotalAmount(updatedTotalAmount);

        // Check if the cart is empty and delete the cart if needed
        if (items.isEmpty()) {
            cartRepository.deleteById(cartByUserId.getCartId());
            logger.info("Cart with ID: {} deleted as it is empty.", cartByUserId.getCartId());
        }

        // Return the updated cart
        return cartByUserId;
    }




    public void deleteCart(long cartId) {
        logger.info("Deleting cart with cartId: {}", cartId);
        cartRepository.deleteById(cartId);
        logger.info("Cart deleted successfully with cartId: {}", cartId);
    }

    private CartDto mapToDto(Cart cart) {
        logger.debug("Mapping cart entity to DTO for cartId: {}", cart.getCartId());
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getCartId());
        cartDto.setUserId(cart.getUserId());
        cartDto.setCreatedDate(cart.getCartCreatedTime());
        cartDto.setItems(cart.getItems());
        cartDto.setTotalAmount(cart.getTotalCartValue());
        logger.debug("Mapping completed for cartId: {}", cart.getCartId());
        return cartDto;
    }
    

}
