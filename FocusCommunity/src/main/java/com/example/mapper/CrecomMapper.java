package com.example.mapper;

// DBMapper's Mapper  Class for Crecom
/**
 *
 * @(#) CrecomMapper.java
 *
 *
 * @version v0.1
 * @date    2021-10-25
 * @author  TaeHan Heo
 * @since   JDK1.8
 *
 */

import java.util.*;
import org.apache.ibatis.annotations.*;

import com.example.domain.CrecomVO;

public interface CrecomMapper {
////////////////// User Define Code Start //////////////////
    /**
    * Get one Record 
    * @param long csnum, String ctype, String cusid
    * @return CrecomMapper 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Select(" Select csnum, ctype, cusid, recom " +
            " from Crecom  " +
            " where csnum = #{csnum} and ctype = #{ctype} and cusid = #{cusid}  ")
    CrecomVO select(@Param("csnum") long csnum, @Param("ctype") String ctype, @Param("cusid") String cusid);
    /**
    * Get All Record 
    * @param  List 
    * @return List 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Select(" Select csnum, ctype, cusid, recom " +
            " from Crecom ")
    List<CrecomVO> selectAll();
    /**
    * Add Record 
    * @param CrecomVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Insert(" Insert into Crecom(" +
            " csnum, ctype, cusid, recom" +
            " ) values (" +
            " #{csnum},  #{ctype},  #{cusid},  #{recom})")
    void insert(CrecomVO crecom);
    /**
    * Update Record 
    * @param CrecomVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Update(" Update Crecom SET " +
            " csnum = #{csnum}, ctype = #{ctype}, cusid = #{cusid}, recom = #{recom}" +
            " where csnum = #{csnum} and ctype = #{ctype} and cusid = #{cusid} ")
    void update(CrecomVO crecom);
    /**
    * Delete Record 
    * @param long csnum, String ctype, String cusid
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Delete(" Delete From Crecom " +
    		" where csnum = #{csnum} and ctype = #{ctype} and cusid = #{cusid} ")
    void delete(@Param("csnum") long csnum, @Param("ctype") String ctype, @Param("cusid") String cusid);
    /**
    * Get Rows Count 
    * @param long csnum, String ctype, String cusid
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Select(" SELECT COUNT(*) from Crecom " +
            " where csnum = #{csnum} and ctype = #{ctype} and cusid = #{cusid}   ")
    int count(@Param("csnum") long csnum, @Param("ctype") String ctype, @Param("cusid") String cusid);

    /**
    * Get All Rows Count 
    * @param void 
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Select(" SELECT COUNT(*) from Crecom  ")
    int countAll();
    
    @Select(" SELECT COUNT(*) from Crecom " +
            " where cusid = #{cusid}   ")
    int countCusid(String cusid);
    
    @Delete(" Delete From Crecom " +
    		" where cusid = #{cusid} ")
    void deleteCusid(String cusid);
    
    @Select(" SELECT sum(recom) from Crecom " +
            " where csnum = #{csnum}   ")
    int sumCsnum(long csnum);
////////////////// User Define  Code  End //////////////////
}// end of CrecomMapper class