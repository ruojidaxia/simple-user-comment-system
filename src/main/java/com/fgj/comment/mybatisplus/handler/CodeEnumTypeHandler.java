package com.fgj.comment.mybatisplus.handler;

import com.fgj.comment.mybatisplus.enums.CodeEnum;
import com.fgj.comment.mybatisplus.enums.MyCodeEnum;
import com.fgj.comment.mybatisplus.pojo.CodeEnumUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 如果写value = {Integer.class}，也不会报错~~，这就是反射恶心的地方~~，直接我把我的类型参数E给忽略了（跳过了类型检查）
 */
@MappedTypes(value = {MyCodeEnum.class})
public class CodeEnumTypeHandler<E extends Enum<?> & CodeEnum> extends BaseTypeHandler<E> {
    /**
     * 实际传入的类型
     */
    private final Class<E> type;
    public CodeEnumTypeHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i,parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        CodeEnum codeEnum = rs.getObject(columnName,type);
        return rs.wasNull() ? null : CodeEnumUtils.valueOf(codeEnum.getCode(),type);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        CodeEnum codeEnum = rs.getObject(columnIndex,type);
        return rs.wasNull() ? null : CodeEnumUtils.valueOf(codeEnum.getCode(),type);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        CodeEnum codeEnum = cs.getObject(columnIndex,type);
        return cs.wasNull() ? null : CodeEnumUtils.valueOf(codeEnum.getCode(),type);
    }
}
