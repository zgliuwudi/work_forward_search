package com.esclient.demo.service;

import com.esclient.demo.vo.ItemVO;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {

    /**
     * 创建索引
     * @param indexName
     * @return
     */
    boolean createItemIndex(String indexName);

    /**
     * 搜索指定字段中包含指定关键字的文档，并高亮
     * @param indexName
     * @param field
     * @param keyword
     * @return
     */
    List<Map<String, Object>> highLightSearch(String indexName, String field, String keyword, Integer pageNum, Integer pageSize);

    /**
     * 批量插入
     * @param indexName
     * @param itemVOList
     */
    void bulkSave(String indexName, List<ItemVO> itemVOList);
}
