package com.faitmain.domain.product.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.faitmain.domain.product.domain.Product;
import com.faitmain.global.common.Image;
import com.faitmain.global.common.Search;

public interface ProductService {

	//상품 등록
	public void addProduct(Product product, MultipartHttpServletRequest mRequest) throws Exception;
	
	//상품 추가 이미지 등록
	public void addProductImage(Image image) throws Exception;
		
	//상품 상세 조회
	public Product getProduct(int productNumber) throws Exception;
	
	public int getProductQuantity(int productNumber) throws Exception;
	
	//상품 목록 조회
	public Map<String, Object> getProductList(Map<String, Object> map) throws Exception;
	
	//유저별 상품 목록 조회
	public Map<String, Object> getProductListByStoreId(String storeId) throws Exception;
	
	//상품 수정
	public void updateProduct(Product product, MultipartHttpServletRequest mRequest) throws Exception;
	
	public void updateProductOption(Product product) throws Exception;
	
	//상품 추가 이미지 수정
	public void updateProductImage(Image image) throws Exception;
	
	//상품 상태 수정
	public void updateProductStatus(Product product) throws Exception;
	
	//상품 삭제
	public void deleteProduct(int productNumber) throws Exception;
	
	public void deleteProductOption(int productNumber) throws Exception;
	
	//DELETE - 상품 추가 이미지 삭제
	public void deleteProductImage(int imageNumber) throws Exception;
}
