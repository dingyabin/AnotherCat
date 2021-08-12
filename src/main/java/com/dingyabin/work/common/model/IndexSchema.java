package com.dingyabin.work.common.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:0:09
 */
@Getter
@Setter
public class IndexSchema  implements Serializable {

    private String indexName;

    private String indexType;

    private String indexColumns;

    private String indexComment;

}
