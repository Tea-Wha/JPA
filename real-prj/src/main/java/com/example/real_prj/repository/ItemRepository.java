package com.example.real_prj.repository;

import com.example.real_prj.constant.ItemSellStatus;
import com.example.real_prj.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // 기본적인 CRUD는 이미 구현되어 있음
    // 상품명 조회
    List<Item> findByItemName(String itemName);

    //상품명이나 상품 상세 설명으로 조회하기 : OR
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);

    // 설정한 가격 : 5000 미만의 상품 조회하기
    List<Item> findByPriceLessThan(int price);

    // 상품 판매 상태를 짝수는 SELL, 홀수는 SOLD_OUT으로 변경하고,
    // 가격이 5000원 이상이고 판매 중인 상품만 조회하기
    List<Item> findByPriceGreaterThanEqualAndItemSellStatus(int price, ItemSellStatus itemSellStatus);

    // 상품 가격에 대한 내림 차순 정렬하기
    List<Item> findAllByOrderByPriceAsc();

    // 상품 이름에 특정 키워드가 포함된 상품 검색
    List<Item> findByItemNameLike(String itemName);

    // 상품명과 상품의 가격으로 검색 및 일치하는 상품 검색 (AND)
    List<Item> findByItemNameAndPrice(String itemName, int price);
}
