package com.example.domain;

// Entity Class for Attach
/**
 *
 * @(#) AttachVO.java
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
public class AttachVO {
    // NUMERIC = Zoned Decimal, DECIMAL = Packed Decimal. (7.3 = 1234.123) 
    private String uuid; 		// (VARCHAR, 36)
    private String uploadpath; 		// (VARCHAR, 10)
    private String filename; 		// (VARCHAR, 255)
    private String filetype; 		// (VARCHAR, 1)
    private int    bno; 		// INT
    private String cusid; 		// (VARCHAR, 20)
    private String ctype;
}// end of AttachVO class