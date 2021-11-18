package com.example.domain;

// Entity Class for Cusbod
/**
 *
 * @(#) CusbodVO.java
 *
 *
 * @version v0.1
 * @date    2021-10-25
 * @author  TaeHan Heo
 * @since   JDK1.8
 *
 */

import java.util.*;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class CusbodVO {
    // NUMERIC = Zoned Decimal, DECIMAL = Packed Decimal. (7.3 = 1234.123) 
    private Long		csnum; 		// INT
    private String 		ctype; 		// (VARCHAR, 20)
    private String 		cusid; 		// (VARCHAR, 20)
    private String 		csbjt; 		// (VARCHAR, 50)
    private String   	ccont; 		// TEXT
    private int    		crdcn; 		// INT
    private Date  	    cdate; 		// DATETIME
    private String 		ctmid; 		// (VARCHAR, 20)
    private int    		crerf; 		// INT
    private int    		crelv; 		// INT
    private int			cresq; 		// INT
    private Integer		num;
    private String		uuid;
    private String		uploadpath;
    private String		filename;
    private	String		filetype;
    private String		cname;
    private Integer		recom;
	private AttachVO	attach;
	private Integer		cnt;
}// end of CusbodVO class