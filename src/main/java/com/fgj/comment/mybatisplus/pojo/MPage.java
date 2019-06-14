package com.fgj.comment.mybatisplus.pojo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * 简单分页模型，针对mysql分页语法 limit begin，size。可以通过设置mysqlBeginOffset自定义begin的偏移值
 *
 */
@ToString
@EqualsAndHashCode
public class MPage<T> implements IPage<T> {

    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();
    /**
     * 总数
     */
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;
    /**
     * 当前页
     */
    private long current = 1;
    /**
     * SQL 排序 ASC 数组
     */
    private String[] ascs;
    /**
     * SQL 排序 DESC 数组
     */
    private String[] descs;
    /**
     * 自动优化 COUNT SQL
     */
    private boolean optimizeCountSql = true;
    /**
     * 是否进行 count 查询
     */
    private boolean isSearchCount = true;

    private long mysqlBeginOffset = 0;

    public MPage() {
        // to do nothing
    }

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public MPage(long current, long size) {
        this(current, size, 0);
    }

    public MPage(long current, long size, long total) {
        this(current, size, total, true);
    }

    public MPage(long current, long size, boolean isSearchCount) {
        this(current, size, 0, isSearchCount);
    }

    public MPage(long current, long size, long total, boolean isSearchCount) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.getPages();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public MPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public MPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public MPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public MPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    @Override
    public String[] ascs() {
        return ascs;
    }

    public MPage<T> setAscs(List<String> ascs) {
        if (CollectionUtils.isNotEmpty(ascs)) {
            this.ascs = ascs.toArray(new String[0]);
        }
        return this;
    }


    /**
     * 升序
     *
     * @param ascs 多个升序字段
     */
    public MPage<T> setAsc(String... ascs) {
        this.ascs = ascs;
        return this;
    }

    @Override
    public String[] descs() {
        return descs;
    }

    public MPage<T> setDescs(List<String> descs) {
        if (CollectionUtils.isNotEmpty(descs)) {
            this.descs = descs.toArray(new String[0]);
        }
        return this;
    }

    /**
     * 降序
     *
     * @param descs 多个降序字段
     */
    public MPage<T> setDesc(String... descs) {
        this.descs = descs;
        return this;
    }

    @Override
    public boolean optimizeCountSql() {
        return optimizeCountSql;
    }

    @Override
    public boolean isSearchCount() {
        if (total < 0) {
            return false;
        }
        return isSearchCount;
    }

    public MPage<T> setSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }

    public MPage<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

    public long getMysqlBeginOffset() {
        return mysqlBeginOffset;
    }

    public void setMysqlBeginOffset(long mysqlBeginOffset) {
        this.mysqlBeginOffset = mysqlBeginOffset;
    }

    @Override
    public long offset() {
        return  (getCurrent()-1)*getSize() + mysqlBeginOffset;
    }
}
