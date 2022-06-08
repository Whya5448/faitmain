package com.faitmain.domain.product.mapper;

import com.faitmain.domain.product.domain.Cart;
import com.faitmain.global.common.Search;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper{

    @Insert( " INSERT INTO cart(user_id, cart_quantity, product_number, product_name, product_price, product_main_image) " +
             " VALUES (#{userId}, #{cartQuantity}, #{cartProduct.productNumber}, #{cartProduct.productName}, #{cartProduct.price}, #{cartProduct.productMainImage})" )
    void insertCart( Cart cart );

    //INSERT - 장바구니 등록
    void addCart( Cart cart ) throws Exception;

    //SELECT - 장바구니 조회
    Cart getCart( Cart cart ) throws Exception;

    //SELECT - 장바구니 목록 조회
    List<Cart> getCartList( Search search ) throws Exception;

    //SELECT - 장바구니 count
    int getTotalCount( Search search ) throws Exception;

    //UPDATE - 장바구니 수정
    void updateCart( Cart cart ) throws Exception;

    //DELETE - 장바구니 삭제
    void deleteCart( int cartNumber ) throws Exception;

}