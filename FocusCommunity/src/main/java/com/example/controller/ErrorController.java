package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/error")
public class ErrorController {
	@GetMapping
	public String defaultError() {
		return "error/default";
	} // end of defaultError
	
	@GetMapping("/no-resource0")
	public String noResource() {
		return "error/400errorPage";
	} // end of noResource
	
	@GetMapping("/no-resource1")
	public String noResource1() {
		return "error/403errorPage";
	} // end of noResource1

	@GetMapping("/no-resource2")
	public String noResource2() {
		return "error/404errorPage";
	} // end of noResource2

	@GetMapping("/server-error")
	public String serverError() {
		return "error/500errorPage";
	} // end of serverError
	
	@GetMapping("/server-error2")
	public String serverError2() {
		return "error/503errorPage";
	} // end of serverError2
} // end of ErrorController