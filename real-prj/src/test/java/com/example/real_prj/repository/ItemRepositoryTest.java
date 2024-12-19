package com.example.real_prj.repository;

import com.example.real_prj.constant.ItemSellStatus;
import com.example.real_prj.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;
    
    @Test
    @DisplayName("상품 저장 테스트") // 테스트 이름
    public void createItemTest(){
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemName("테스트 상품" + i);
            item.setPrice(1000 * i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            if(i % 2 == 0) item.setItemSellStatus(ItemSellStatus.SELL);
            else item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item saveItem = itemRepository.save(item);
            log.info("Item 저장 : {}", saveItem);
        }
    }
    
    @Test
    @DisplayName("상품 조회 테스트")
    public void findByItemNameTest(){
        this.createItemTest(); // 10개의 상품을 생성
        List<Item> itemList = itemRepository.findByItemName("테스트 상품5");
        for(Item item : itemList){
            log.info("상품 조회 테스트 : {}",item);
        }
    }

    @Test
    @DisplayName("상품명 OR 상품 상세 설명 조회")
    public void findByItemNameOrItemDetailTest(){
        this.createItemTest(); // 10개의 상품을 생성
        List<Item> itemList = itemRepository.findByItemNameOrItemDetail("테스트 상품5","테스트 상품 상세 설명1");
        for(Item item : itemList){
            log.info("상품명 OR 상품 상세 설명 조회 테스트 : {}",item);
        }
    }

    @Test
    @DisplayName("가격 5000 미만 상품 조회")
    public void findByItemPriceLessThanTest(){
        this.createItemTest(); // 10개의 상품을 생성
        List<Item> itemList = itemRepository.findByPriceLessThan(5000);
        for(Item item : itemList){
            log.info("가격 5000 미만 상품 조회 테스트 : {}",item);
        }
    }

    @Test
    @DisplayName("가격 이상 및 상품 상태 조회")
    public void findByPriceGreaterThanEqualAndItemSellStatusTest(){
        this.createItemTest(); // 10개의 상품을 생성
        List<Item> itemList = itemRepository.findByPriceGreaterThanEqualAndItemSellStatus(5000, ItemSellStatus.SELL);
        for(Item item : itemList){
            log.info("가격 이상 및 상품 상태 조회 : {}",item);
        }
    }

    @Test
    @DisplayName("가격 내림차순 정렬 조회")
    public void findAllByOrderByPriceAscTest(){
        this.createItemTest(); // 10개의 상품을 생성
        List<Item> itemList = itemRepository.findAllByOrderByPriceAsc();
        for(Item item : itemList){
            log.info("가격 내림차순 정렬 조회 : {}",item);
        }
    }

    @Test
    @DisplayName("특정 키워드 검색 조회")
    public void findByItemNameLike(){
        this.createItemTest(); // 10개의 상품을 생성
        List<Item> itemList = itemRepository.findByItemNameLike("%상품10%");
        for(Item item : itemList){
            log.info("특정 키워드 검색 조회 : {}",item);
        }
    }

    @Test
    @DisplayName("이름 AND 가격 조회")
    public void findByItemNameAndPrice(){
        this.createItemTest(); // 10개의 상품을 생성
        List<Item> itemList = itemRepository.findByItemNameAndPrice("테스트 상품5",4000);
        for(Item item : itemList){
            log.info("이름 AND 가격 조회 테스트: {}",item);
        }
    }

}