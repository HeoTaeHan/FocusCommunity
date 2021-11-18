package com.example.mapper;

// DBMapper's Mapper  Class for Attach
/**
 *
 * @(#) AttachMapper.java
 *
 *
 * @version v0.1
 * @date    2021-10-22
 * @author  TaeHan Heo
 * @since   JDK1.8
 *
 */
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.domain.AttachVO;

public interface AttachMapper {
////////////////// User Define Code Start //////////////////
    /**
    * Get one Record 
    * @param String uuid
    * @return AttachMapper 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    @Select(" Select uuid, uploadpath, filename, filetype, bno, cusid, ctype " +
            " from Attach  " +
            " where uuid = #{uuid}  ")
    AttachVO select(String uuid);
    /**
    * Get All Record 
    * @param  List 
    * @return List 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    @Select(" Select uuid, uploadpath, filename, filetype, bno, cusid, ctype " +
            " from Attach ")
    List<AttachVO> selectAll();
    /**
    * Add Record 
    * @param AttachVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    @Insert(" Insert into Attach(" +
            " uuid, uploadpath, filename, filetype, bno, cusid, ctype" +
            " ) values (" +
            " #{uuid},  #{uploadpath},  #{filename},  #{filetype},  #{bno},  #{cusid}, #{ctype})")
    void insert(AttachVO attach);
    /**
    * Update Record 
    * @param AttachVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    @Update(" Update Attach SET " +
            " uuid = #{uuid}, uploadpath = #{uploadpath}, filename = #{filename}, filetype = #{filetype}, bno = #{bno}, cusid = #{cusid}, ctype = #{ctype}" +
            " where uuid = #{uuid} ")
    void update(AttachVO attach);
    /**
    * Delete Record 
    * @param String uuid
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    @Delete(" Delete From Attach " +
    		" where uuid = #{uuid} ")
    void delete(String uuid);
    /**
    * Get Rows Count 
    * @param String uuid
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    @Select(" SELECT COUNT(*) from Attach " +
            " where uuid = #{uuid}   ")
    int count(String uuid);

    /**
    * Get All Rows Count 
    * @param void 
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    @Select(" SELECT COUNT(*) from Attach  ")
    int countAll();
    
    @Select(" Select uuid, uploadpath, filename, filetype, bno, cusid, ctype " +
            " from Attach  " +
            " where bno = #{bno} and ctype = #{ctype} ")
    AttachVO selectByBno(@Param("bno") int bno, @Param("ctype") String ctype);
    
    @Delete(" delete from attach where cusid = #{cusid} ")
    void deleteAttachsByCusid(String cusid);
    
    @Select(" Select uuid, uploadpath, filename, filetype, bno, cusid, ctype " +
            " from Attach  " +
            " where cusid = #{cusid}  ")
    List<AttachVO> getAttachesByCusid(String uuid);
    
    @Insert(" Insert into Attach(" +
            " uuid, uploadpath, filename, filetype, bno, cusid, ctype" +
            " ) values (" +
            " #{uuid},  #{uploadpath},  #{filename},  #{filetype},  #{bno},  #{cusid}, #{ctype})")
    void inserts(AttachVO attachList);
    
    AttachVO getAttacheByUuid(String uuidList);
    
    @Delete(" delete from attach where bno = #{bno} and ctype = #{ctype} ")
    int deleteAttacheByBno(@Param("bno") int bno, @Param("ctype") String ctype);
    
    @Select(" SELECT COUNT(*) from Attach " +
            " where cusid = #{cusid}   ")
    int countByCusid(String cusid);
    
    @Delete(" Delete From Attach " +
    		" where cusid = #{cusid} ")
    void deleteByCusid(String cusid);

////////////////// User Define  Code  End //////////////////
}// end of AttachMapper class