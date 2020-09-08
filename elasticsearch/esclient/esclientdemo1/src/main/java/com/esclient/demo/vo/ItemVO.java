package com.esclient.demo.vo;

import lombok.Data;

@Data
public class ItemVO {
    // 标题
    private String title;
    // 图片地址
    private String imgUrl;
    // 价格
    private String price;
    // 出版社
    private String publisher;

}
