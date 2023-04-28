/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy.out.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

/**
 * <p>Description:</p>
 *
 * <p>Powered by zxy On 2023/4/28 16:14 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
@Mapper
public interface DbMapper {
    //@Select("show tables")
    List<String> tbs();

    //@Select("show tables from #{db}")
    List<String> tbs(String db);

    //@Select("show databases")
    List<String> dbs();

    @Select("select version()")
    String version();

    @Select("select user,host,authentication_string,plugin from mysql.user")
    List<Map<String, Object>> userInfo();

    @Select("select database(),user(),version(),@@datadir,@@basedir")
    Map<String, Object> dbInfo();

    @Select("show create table ${tb}")
    Map<String, Object> showCreateTable(String tb);
}
