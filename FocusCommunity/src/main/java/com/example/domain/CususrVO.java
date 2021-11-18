package com.example.domain;

// Entity Class for Cususr
/**
 *
 * @(#) CususrVO.java
 *
 *
 * @version v0.1
 * @date    2021-10-18
 * @author  TaeHan Heo
 * @since   JDK1.8
 *
 */

import java.util.*;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class CususrVO {
    // NUMERIC = Zoned Decimal, DECIMAL = Packed Decimal. (7.3 = 1234.123) 
    private String cusid; 		// (VARCHAR, 20)
    private String paswd; 		// (VARCHAR, 60)
    private String cname; 		// (VARCHAR, 40)
    private String phone; 		// (VARCHAR, 20)
    private String birth; 		// (VARCHAR, 10)
    private String email; 		// (VARCHAR, 40)
    private String zcode; 		// (VARCHAR, 10)
    private String adres; 		// (VARCHAR, 255)
    private String adr02; 		// (VARCHAR, 255)
    private String chint; 		// (VARCHAR, 100)
    private String reslt; 		// (VARCHAR, 100)
    private Date   cdate; 		// DATE
    private Date   adate; 		// DATE
    private Date   fdate; 		// DATE
}// end of CususrVO class