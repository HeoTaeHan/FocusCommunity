package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/tos/*")
public class TOSController {
	@GetMapping("/privacy")
	public void privacy() {} // end of privacy
	
	@GetMapping("/conditions")
	public void conditions() {} // end of conditions
	
	@GetMapping("/policy")
	public void policy() {} // end of policy
	
	@GetMapping("/teenager")
	public void teenager() {} // end of teenager
}// end of TOSController