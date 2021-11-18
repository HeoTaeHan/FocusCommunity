package com.example.domain;

// Entity Class for Crecom
/**
 *
 * @(#) CrecomVO.java
 *
 *
 * @version v0.1
 * @date    2021-10-22
 * @author  TaeHan Heo
 * @since   JDK1.8
 *
 */

import java.util.*;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class CrecomVO {
    // NUMERIC = Zoned Decimal, DECIMAL = Packed Decimal. (7.3 = 1234.123) 
    private int    csnum; 		// INT
    private String cusid; 		// (VARCHAR, 20)
    private String ctype;
    private int    recom; 		// INT
}// end of CrecomVO class