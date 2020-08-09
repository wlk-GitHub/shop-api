package com.fh.shop.api.brand.mapper;

import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.ServerResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface IBrandMapper {

    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "name",property = "name")
    })


    @Insert("insert into mall_brand (name) values(#{name})")
    public void addBrand(Brand brand);


    @Select("select id,name from mall_brand")
    List<Brand> findList();

    @Delete("delete from mall_brand where id = (#{id})")
    void delete(Integer id);

    @Update("update mall_brand set name=(#{name}) where id=(#{id})")
    void update(Brand brand);

/*    @Delete("<script>delete from mall_brand where id in \n" +
            "\t<foreach collection=\"list\" item=\"id\" open=\"(\" separator=\",\" close=\")\">\n" +
            "\t\t#{id}\t\n" +
            "\t</foreach></script>")*/
    void deleteBatch(List<Long> idList);
}
