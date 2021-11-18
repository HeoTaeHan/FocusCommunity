package com.example.domain;

// Entity Class for Csrepl
/**
 *
 * @(#) CsreplVO.java
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
public class CsreplVO {
    // NUMERIC = Zoned Decimal, DECIMAL = Packed Decimal. (7.3 = 1234.123) 
    private int    csnum; 		// INT
    private String ctype; 		// (VARCHAR, 20)
    private int    conum; 		// INT
    private String cusid; 		// (VARCHAR, 20)
    private String cnten; 		// TEXT
    private int    crerf; 		// INT
    private int    crelv; 		// INT
    private int    cresq; 		// INT
    private String trmid; 		// (VARCHAR, 20)
    private Date   cdate; 		// DATETIME
    private String cname;
}// end of CsreplVO class