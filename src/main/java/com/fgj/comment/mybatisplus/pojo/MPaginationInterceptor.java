package com.fgj.comment.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisDefaultParameterHandler;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import static java.util.stream.Collectors.joining;

/**
 * 分页拦截器
 *
 * @author fgj
 * @since 2019-04-25
 */
@Setter
@Accessors(chain = true)
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {
                MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class
        })
})
public class MPaginationInterceptor implements Interceptor {

    /**
     * COUNT SQL 解析
     */
    private ISqlParser sqlParser;
    /**
     * 溢出总页数，设置第一页
     */
    private boolean overflow = false;
    /**
     * 方言类型
     */
    private String dialectType = "MYSQL";
    /**
     * 方言实现类
     */
    private String dialectClazz;

    /**
     * 查询SQL拼接Order By
     *
     * @param originalSql 需要拼接的SQL
     * @param page        page对象
     * @param orderBy     是否需要拼接Order By
     * @return ignore
     */
    public static String concatOrderBy(String originalSql, IPage page, boolean orderBy) {
        if (orderBy && (ArrayUtils.isNotEmpty(page.ascs())
            || ArrayUtils.isNotEmpty(page.descs()))) {
            StringBuilder buildSql = new StringBuilder(originalSql);
            String ascStr = concatOrderBuilder(page.ascs(), " ASC");
            String descStr = concatOrderBuilder(page.descs(), " DESC");
            if (StringUtils.isNotEmpty(ascStr) && StringUtils.isNotEmpty(descStr)) {
                ascStr += ", ";
            }
            if (StringUtils.isNotEmpty(ascStr) || StringUtils.isNotEmpty(descStr)) {
                buildSql.append(" ORDER BY ").append(ascStr).append(descStr);
            }
            return buildSql.toString();
        }
        return originalSql;
    }

    /**
     * 拼接多个排序方法
     *
     * @param columns ignore
     * @param orderWord ignore
     */
    private static String concatOrderBuilder(String[] columns, String orderWord) {
        if (ArrayUtils.isNotEmpty(columns)) {
            return Arrays.stream(columns).filter(StringUtils::isNotEmpty)
                .map(i -> i + orderWord).collect(joining(StringPool.COMMA));
        }
        return StringUtils.EMPTY;
    }

    /**
     * Physical Page Interceptor for all the queries with parameter {@link RowBounds}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //获取MappedStatement对象
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameterObject = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);

        // 先判断是不是SELECT操作
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }

        // 针对定义了rowBounds，做为mapper接口方法的参数
        Object paramObj = boundSql.getParameterObject();

        // 判断参数里是否有page对象
        IPage page = null;
        if (paramObj instanceof IPage) {
            page = (IPage) paramObj;
        } else if (paramObj instanceof Map) {
            for (Object arg : ((Map) paramObj).values()) {
                if (arg instanceof IPage) {
                    page = (IPage) arg;
                    break;
                }
            }
        }

        /*
         * 不需要分页的场合，如果 size 小于 0 返回结果集
         */
        if (null == page || page.getSize() < 0) {
            return invocation.proceed();
        }

        String originalSql = boundSql.getSql();
        DbType dbType = DbType.valueOf(dialectType);
        boolean orderBy = true;
//        if (page.isSearchCount()) {
//            SqlInfo sqlInfo = SqlParserUtils.getOptimizeCountSql(page.optimizeCountSql(), sqlParser, originalSql);
//            orderBy = sqlInfo.isOrderBy();
//            this.queryTotal(overflow, sqlInfo.getSql(), mappedStatement, boundSql, page, null);
//            if (page.getTotal() <= 0) {
//                return invocation.proceed();
//            }
//        }

        String buildSql = concatOrderBy(originalSql, page, orderBy);
        DialectModel model = DialectFactory.buildPaginationSql(page, buildSql, dbType, dialectClazz);
        Configuration configuration = mappedStatement.getConfiguration();
        List<ParameterMapping> mappings = new ArrayList<>(boundSql.getParameterMappings());
        MetaObject boundSqlMetaObject = SystemMetaObject.forObject(boundSql);
        Map<String, Object> additionalParameters = (Map<String, Object>) boundSqlMetaObject.getValue("additionalParameters");
        model.consumers(mappings, configuration, additionalParameters);
        boundSqlMetaObject.setValue("sql", model.getDialectSql());
        boundSqlMetaObject.setValue("parameterMappings", mappings);
        Executor executor = (Executor)invocation.getTarget();
        CacheKey cacheKey = executor.createCacheKey(mappedStatement,parameterObject,rowBounds,boundSql);
        return executor.query(mappedStatement,parameterObject,rowBounds,resultHandler,cacheKey,boundSql);
    }

    /**
     * 查询总记录条数
     *
     * @param sql             count sql
     * @param mappedStatement MappedStatement
     * @param boundSql        BoundSql
     * @param page            IPage
     * @param connection      Connection
     */
    protected void queryTotal(boolean overflowCurrent, String sql, MappedStatement mappedStatement, BoundSql boundSql, IPage page, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            DefaultParameterHandler parameterHandler = new MybatisDefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), boundSql);
            parameterHandler.setParameters(statement);
            long total = 0;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getLong(1);
                }
            }
            page.setTotal(total);
            /*
             * 溢出总页数，设置第一页
             */
            long pages = page.getPages();
            if (overflowCurrent && page.getCurrent() > pages) {
                // 设置为第一条
                page.setCurrent(1);
            }
        } catch (Exception e) {
            throw ExceptionUtils.mpe("Error: Method queryTotal execution error of sql : \n %s \n", e, sql);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties prop) {
        String dialectType = prop.getProperty("dialectType");
        String dialectClazz = prop.getProperty("dialectClazz");
        if (StringUtils.isNotEmpty(dialectType)) {
            this.dialectType = dialectType;
        }
        if (StringUtils.isNotEmpty(dialectClazz)) {
            this.dialectClazz = dialectClazz;
        }
    }
}
