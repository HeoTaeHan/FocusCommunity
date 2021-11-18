package com.example.mapper;

// DBMapper's Mapper  Class for Cusbod
/**
 *
 * @(#) CusbodMapper.java
 *
 *
 * @version v0.1
 * @date    2021-10-25
 * @author  TaeHan Heo
 * @since   JDK1.8
 *
 */
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.domain.Criteria;
import com.example.domain.CusbodVO;

public interface CusbodMapper {
////////////////// User Define Code Start //////////////////
    /**
    * Get one Record 
    * @param long csnum, String ctype
    * @return CusbodMapper 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Select(" Select csnum, ctype, cusid, csbjt, ccont, crdcn, cdate, ctmid, crerf, " +
            " crelv, cresq " +
            " from Cusbod  " +
            " where csnum = #{csnum} and ctype = #{ctype}  ")
    CusbodVO select(@Param("csnum") long csnum, @Param("ctype") String ctype);
    /**
    * Get All Record 
    * @param  List 
    * @return List 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Select(" Select csnum, ctype, cusid, csbjt, ccont, crdcn, cdate, ctmid, crerf, " +
            " crelv, cresq " +
            " from Cusbod ")
    List<CusbodVO> selectAll();
    /**
    * Add Record 
    * @param CusbodVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    
    int getNextnum(String ctype);
    
    @Insert(" Insert into Cusbod(" +
            " csnum, ctype, cusid, csbjt, ccont, crdcn, cdate, ctmid, crerf, " +
            " crelv, cresq" +
            " ) values (" +
            " #{csnum},  #{ctype},  #{cusid},  #{csbjt},  #{ccont},  #{crdcn},  #{cdate},  #{ctmid},  #{crerf},  #{crelv}, " +
            "  #{cresq})")
    void insert(CusbodVO cusbod);
    /**
    * Update Record 
    * @param CusbodVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Update(" Update Cusbod SET " +
            " csnum = #{csnum}, ctype = #{ctype}, cusid = #{cusid}, csbjt = #{csbjt}, ccont = #{ccont}, ctmid = #{ctmid} " +
            " where csnum = #{csnum} and ctype = #{ctype} ")
    void update(CusbodVO cusbod);
    /**
    * Delete Record 
    * @param long csnum, String ctype
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Delete(" Delete From Cusbod " +
    		" where csnum = #{csnum} and ctype = #{ctype} ")
    void delete(@Param("csnum") long csnum, @Param("ctype") String ctype);
    /**
    * Get Rows Count 
    * @param long csnum, String ctype
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Select(" SELECT COUNT(*) from Cusbod " +
            " where csnum = #{csnum} and ctype = #{ctype}   ")
    int count(@Param("csnum") long csnum, @Param("ctype") String ctype);

    /**
    * Get All Rows Count 
    * @param void 
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    @Select(" SELECT COUNT(*) from Cusbod  ")
    int countAll();
    
    @Select("	SELECT count(*) from cusbod "
    	+	"	where cusid = #{cusid}")
    int countCusid(String cusid);
    
    @Delete(" Delete From Cusbod " +
    		" where cusid = #{cusid} ")
    void deleteCusid(String cusid);
    
    @Select(" select cusid, count(*) as cnt "
        	+   " from cusbod "
        	+   " group by cusid "
        	+   " order by cusid ")
    List<Map<String, Object>> getCount();
    
    List<CusbodVO> getList(Criteria cri);
    
    List<CusbodVO> getApiList(Criteria cri);
    
    int getListCount(Criteria cri);
    
    List<CusbodVO> getEmpathList(Criteria cri);
    
    int getEmpathListCount(Criteria cri);
    
    List<CusbodVO> getListType(Criteria cri);
    
    int getListTypeCount(Criteria cri);
    
    @Update("UPDATE cusbod"
            + "   SET crdcn = crdcn + 1"
            + "   WHERE csnum = #{csnum} ")
      void updateReadcount(int csnum);
    
    @Select("	Select * from ( " +
    		"		select a.csnum, a.ctype, a.cusid, a.csbjt, a.ccont, a.crdcn, a.cdate, a.ctmid, a.crerf, " +
            " 		a.crelv, a.cresq, b.recom, " +
            "		case when c.uuid is null then " +
            "		substring(left(substring_index(substring_index(ccont, 'src=\"', -1),'<', 1), 4), 2) else c.uuid end as uuid, " +
            "		c.uploadpath, " +
            "		case when c.filename is null then " +
            "		concat('<img style=\"width:50px\" src=\"/img/',substring(substring_index(substring_index(ccont, 'img' , -1),'\"', 1), 2), '\">') else c.filename end as filename,  " +	
            "		case when c.filetype is null then " +
            "		substring(substring_index(substring_index(ccont, 'img' , -1),'\"', 1), 2) else c.filetype end as filetype " +
            " 		from Cusbod  a " +
            "		left outer join ( " +
            "			select csnum, ctype, sum(recom) as recom " +
            "			from crecom " +
            "			group by csnum, ctype "+
            "		) b on (a.csnum = b.csnum and a.ctype = b.ctype) " +
            "		left outer join attach c on (a.csnum = c.bno   and a.cusid = c.cusid and a.ctype = c.ctype) " +
            "	) x" +
            " 	where x.csnum = #{csnum} and x.ctype = #{ctype} and x.cusid = #{cusid} ")
    CusbodVO selectCusbodAndAttatchAndRecom(@Param("csnum") long csnum, @Param("ctype") String ctype, @Param("cusid") String cusid);
    
    @Delete(" Delete From Cusbod " +
    		" where csnum = #{csnum} and ctype = #{ctype} ")
    int deleteCusbodByCsnum(@Param("csnum") int csnum, @Param("ctype") String ctype);
    
    void updateCresqPlusOne(
			@Param("crerf") int crerf, 
			@Param("cresq") int cresq);
    
    CusbodVO selectCusbodAndAttache(int csnum);
////////////////// User Define  Code  End //////////////////
}// end of CusbodMapper class