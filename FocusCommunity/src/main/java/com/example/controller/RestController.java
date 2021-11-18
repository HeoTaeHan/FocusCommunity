package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/*")
public class RestController {
	@GetMapping("/selectOneBoard")
	public void selectOneBoard() {
		
	}// end of selectOneBoard
	
	@GetMapping("/selectPagingBoards")
	public void selectPagingBoards() {
		
	} // end of selectPagingBoards
	
	@GetMapping("/insertBoard")
	public void insertBoard() {
		
	} // end of insertBoard
	
	@GetMapping("/updateBoard")
	public void updateBoard() {
		
	} // end of updateBoard
	
	@GetMapping("/deleteBoard")
	public void deleteBoard() {
		
	} // end of deleteBoard
} // end of RestController