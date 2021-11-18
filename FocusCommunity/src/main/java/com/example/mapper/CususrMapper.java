package com.example.mapper;

// DBWrapper's Wrapper  Class for Cususr
/**
 *
 * @(#) CususrMapper.java
 *
 *
 * @version v0.1
 * @date    2021-10-18
 * @author  TaeHan Heo
 * @since   JDK1.8
 *
 */
import java.util.List;
import java.util.Vector;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.domain.AttachVO;
import com.example.domain.Criteria;
import com.example.domain.CususrVO;

public interface CususrMapper {
////////////////// User Define Code Start //////////////////
    /**
    * Get one Record 
    * @param String cusid
    * @return CususrMapper 
    * @author TaeHan Heo 
    * @date 2021-10-18
    */
    @Select(" Select cusid, paswd, cname, phone, birth, email, zcode, adres, adr02, " +
            " chint, reslt, cdate, adate, fdate " +
            " from Cususr  " +
            " where cusid = #{cusid}  ")
    CususrVO select(String cusid);
    /**
    * Get All Record 
    * @param  void 
    * @return List 
    * @author TaeHan Heo 
    * @date 2021-10-18
    */
    @Select(" Select cusid, paswd, cname, phone, birth, email, zcode, adres, adr02, " +
            " chint, reslt, cdate, adate, fdate " +
            " from Cususr ")
    List<CususrVO> selectAll();
    /**
    * Add Record 
    * @param CususrVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-18
    */
    @Insert(" Insert into Cususr(" +
            " cusid, paswd, cname, phone, birth, email, zcode, adres, adr02, " +
            " chint, reslt, cdate, adate, fdate" +
            " ) values (" +
            " #{cusid},  #{paswd},  #{cname},  #{phone},  #{birth},  #{email},  #{zcode},  #{adres},  #{adr02},  #{chint}, " +
            "  #{reslt},  #{cdate},  #{adate},  #{fdate})")
    void insert(CususrVO cususr);
    /**
    * Update Record 
    * @param CususrVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-18
    */
    @Update(" Update Cususr SET " +
            " cusid = #{cusid}, paswd = #{paswd}, cname = #{cname}, phone = #{phone}, birth = #{birth}, email = #{email}, zcode = #{zcode}, adres = #{adres}, adr02 = #{adr02}, chint = #{chint}, " +
            " reslt = #{reslt}, cdate = #{cdate}, adate = #{adate}, fdate = #{fdate}" +
            " where cusid = #{cusid} ")
    void update(CususrVO cususr);
    /**
    * Delete Record 
    * @param String cusid
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-18
    */
    @Delete(" Delete From Cususr " +
    		" where cusid = #{cusid} ")
    void delete(String cusid);
    /**
    * Get Rows Count 
    * @param String cusid
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-18
    */
    @Select(" SELECT COUNT(*) from Cususr " +
            " where cusid = #{cusid}   ")
    int count(String cusid);

    /**
    * Get All Rows Count 
    * @param void 
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-18
    */
    @Select(" SELECT COUNT(*) from Cususr  ")
    int countAll();
    
    @Select(" select count(*) from cususr where email = #{email} ")
    int countByEmail(String email);
    
    List<CususrVO> getCususrPageWhereOption(Criteria cri);
    
    int countAdmin(Criteria cri);
////////////////// User Define  Code  End //////////////////
}// end of CususrMapper class