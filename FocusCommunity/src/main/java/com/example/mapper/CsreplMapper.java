package com.example.mapper;

// DBMapper's Mapper  Class for Csrepl
/**
 *
 * @(#) CsreplMapper.java
 *
 *
 * @version v0.1
 * @date    2021-10-25
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

import com.example.domain.CsreplVO;

public interface CsreplMapper {
////////////////// User Define Code Start //////////////////
    /**
    * Get one Record 
    * @param long csnum, String ctype, long conum
    * @return CsreplMapper 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Select(" Select csnum, ctype, conum, cusid, cnten, crerf, crelv, cresq, trmid, " +
            " cdate " +
            " from Csrepl  " +
            " where csnum = #{csnum} and ctype = #{ctype} and conum = #{conum}  ")
    CsreplVO select(@Param("csnum") long csnum, @Param("ctype") String ctype, @Param("conum") long conum);
    /**
    * Get All Record 
    * @param  List 
    * @return List 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Select(" Select csnum, ctype, conum, cusid, cnten, crerf, crelv, cresq, trmid, " +
            " cdate " +
            " from Csrepl ")
    List<CsreplVO> selectAll();
    
    @Select("	select * from (" +
    		"		Select a.csnum, a.ctype, a.conum, a.cusid, a.cnten, a.crerf, a.crelv, a.cresq, a.trmid, " +
            " 		a.cdate, b.cname " +
            " 		from Csrepl a " +
            "		left outer join cususr b on (a.cusid = b.cusid) " +
            "		order by a.cdate desc, a.crerf DESC, a.cresq ASC "+
            "	) x" +
            " 	where x.csnum = #{csnum} and x.ctype = #{ctype} " +
            " 	ORDER BY x.crerf DESC, x.cresq ASC")
    List<CsreplVO> selectBoards(@Param("csnum") long csnum, @Param("ctype") String ctype);
    /**
    * Add Record 
    * @param CsreplVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Insert(" Insert into Csrepl(" +
            " csnum, ctype, conum, cusid, cnten, crerf, crelv, cresq, trmid, " +
            " cdate" +
            " ) values (" +
            " #{csnum},  #{ctype},  #{conum},  #{cusid},  #{cnten},  #{crerf},  #{crelv},  #{cresq},  #{trmid},  #{cdate}" +
            " )")
    void insert(CsreplVO csrepl);
    /**
    * Update Record 
    * @param CsreplVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Update(" Update Csrepl SET " +
            " csnum = #{csnum}, ctype = #{ctype}, conum = #{conum}, cusid = #{cusid}, cnten = #{cnten} " +
            " where csnum = #{csnum} and ctype = #{ctype} and conum = #{conum} ")
    void update(CsreplVO csrepl);
    /**
    * Delete Record 
    * @param long csnum, String ctype, long conum
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Delete(" Delete From Csrepl " +
    		" where csnum = #{csnum} and ctype = #{ctype} and conum = #{conum} ")
    void delete(@Param("csnum") long csnum, @Param("ctype") String ctype, @Param("conum") long conum);
    /**
    * Get Rows Count 
    * @param long csnum, String ctype, long conum
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Select(" SELECT COUNT(*) from Csrepl " +
            " where csnum = #{csnum} and ctype = #{ctype} and conum = #{conum}   ")
    int count(@Param("csnum") long csnum, @Param("ctype") String ctype, @Param("conum") long conum);

    /**
    * Get All Rows Count 
    * @param void 
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Select(" SELECT COUNT(*) from Csrepl  ")
    int countAll();
    
    @Select(" SELECT COUNT(*) from Csrepl " +
            " where cusid = #{cusid} ")
    int countCusid(String cusid);
    
    @Delete(" Delete From Csrepl " +
    		" where cusid = #{cusid} ")
    void deleteCusid(String cusid);
    
    @Select(" SELECT IFNULL(MAX(conum), 0) + 1 as nextnum from Csrepl " +
    		" where csnum = #{csnum} and ctype = #{ctype} ")
    int getMaxConum(@Param("csnum") long csnum, @Param("ctype") String ctype);
    
    void updateCresqPlusOne(
			@Param("crerf") int crerf, 
			@Param("cresq") int cresq);
    
    @Delete(" Delete From Csrepl " +
    		" where csnum = #{csnum} and ctype = #{ctype} ")
    int deleteCsreplByCsnum(@Param("csnum") int csnum, @Param("ctype") String ctype);
////////////////// User Define  Code  End //////////////////
}// end of CsreplMapper class