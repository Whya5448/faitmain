package com.faitmain.domain.customer.service;


import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.intellij.lang.annotations.JdkConstants.TreeSelectionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faitmain.domain.customer.domain.BanStatus;
import com.faitmain.domain.customer.domain.Customer;
import com.faitmain.domain.customer.mapper.CustomerMapper;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.common.Image;

import lombok.RequiredArgsConstructor;


@Service("customerServiceImpl")				//비즈니스 로직을 처리하는 서비스 클래스를 나타내는 어노테이션(해당 어노테이션 사용하여 스프링의 MVC의 서비스임을 나타냄)
@RequiredArgsConstructor
@Transactional(readOnly = false)			// 선언적 트랜잭션, 적용된 범위에서는 트랜잭션 기능이 포함된 프록시 객체가 생성되어 자동으로 commit 혹은 rollback을 진행
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerMapper customerMapper;	//데이터베이스에 접근하는 DAO bean을 선언 
	
	
	public List<Customer> getCustomerBoardList() throws Exception{
		return customerMapper.getCustomerBoardList();	//게시글 리스트 반환
	}
	
	public Customer getCustomerBoard(int boardNumber) {
		return customerMapper.getCustomerBoard(boardNumber);
	}
	
	@Transactional
	public void addCustomerBoard(Customer customer) {
		 customerMapper.addCustomerBoard(customer);
	}
	
	@Transactional
	public Object updateCustomerBoard(Customer customer) {
		return customerMapper.updateCustomerBoard(customer);
	}
	
	@Transactional
	public void deleteCustomerBoard(int boardNumber) {
		customerMapper.deleteCustomerBoard(boardNumber);
	}
	
	public int getViewCount(int boardNumber) {
		return customerMapper.getViewCount(boardNumber);
	}
	
	public void updateViewCount(int boardNumber, int temp) {
		customerMapper.updateViewCount(boardNumber, temp);
	}

//	public CustomerServiceImpl(CustomerMapper customerMapper) {
//		this.customerMapper = customerMapper;
//	}
//	
//	public CustomerServiceImpl() {
//	}
//	
//	@Override
//	public int addCustomerBoard(Customer customer) throws Exception {
//		customerMapper.addCustomerBoard(customer);
//		return customerMapper.addCustomerBoard(customer);
//		
//	}
//	
//	@Override
//	public void addCustomerBoard(Image image) throws Exception {
//		customerMapper.addCustomerBoardImage(image);
//		
//	}
//	
//	@Override
//	public Customer getCustomerBoard(int boardNumber) throws Exception{ 
//		return customerMapper.getCustomerBoard(boardNumber); 
//	}
//	  
//	@Override
//	public int updateCustomerBoard(Customer customer) throws Exception{ 
//		 return customerMapper.updateCustomerBoard(customer);
//	  
//	  }
//	  
//	@Override
//	public List<Customer> getCustomerBoardList() throws Exception {
//		  return customerMapper.getCustomerBoardList(); 		//사용자 요청을 처리하기 위한 비즈니스 로직을 구현, 데이터를 조회하도록 CustomerMapper class의 getCustomerBoardList 메소드 호출
//	}
//	  
//	@Override
//	public int deleteCustomerBoard(int boardNumber) throws Exception {
//		  return customerMapper.deleteCustomerBoard(boardNumber);
//	  
//	}
//	
//	@Override
//	public int updateBanStatus(BanStatus banStatus) throws Exception{ 
//		  return customerMapper.updateBanStatus(banStatus); 
//	}
//
//	@Override
//	public BanStatus updateBanStatus(int reportNumber) throws Exception {
//		
//		return null;
//	}
//

	
	
}
