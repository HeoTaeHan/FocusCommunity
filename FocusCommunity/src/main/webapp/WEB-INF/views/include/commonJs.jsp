<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<script src="/resources/assets/vendor/aos/aos.js"></script>
  	<script src="/resources/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  	<script src="/resources/assets/vendor/glightbox/js/glightbox.min.js"></script>
  	<script src="/resources/assets/vendor/isotope-layout/isotope.pkgd.min.js"></script>
  	<script src="/resources/assets/vendor/php-email-form/validate.js"></script>
  	<script src="/resources/assets/vendor/purecounter/purecounter.js"></script>
  	<script src="/resources/assets/vendor/swiper/swiper-bundle.min.js"></script>
  	<script src="/resources/assets/vendor/waypoints/noframework.waypoints.js"></script>

  	<!-- Template Main JS File -->
  	<script src="/resources/assets/js/main.js"></script>
  	<script src="/resources/vendor/global/global.min.js"></script>
    <script src="/resources/js/quixnav-init.js"></script>
    <script src="/resources/js/custom.min.js"></script>
	

    <!-- Vectormap -->
    <script src="/resources/vendor/raphael/raphael.min.js"></script>
    <script src="/resources/vendor/morris/morris.min.js"></script>


    <script src="/resources/vendor/circle-progress/circle-progress.min.js"></script>
    <script src="/resources/vendor/chart.js/Chart.bundle.min.js"></script>

    <script src="/resources/vendor/gaugeJS/dist/gauge.min.js"></script>

    <!--  flot-chart js -->
    <script src="/resources/vendor/flot/jquery.flot.js"></script>
    <script src="/resources/vendor/flot/jquery.flot.resize.js"></script>

    <!-- Owl Carousel -->
    <script src="/resources/vendor/owl-carousel/js/owl.carousel.min.js"></script>

    <!-- Counter Up -->
    <script src="/resources/vendor/jqvmap/js/jquery.vmap.min.js"></script>
    <script src="/resources/vendor/jqvmap/js/jquery.vmap.usa.js"></script>
    <script src="/resources/vendor/jquery.counterup/jquery.counterup.min.js"></script>
    
    <!-- Summernote -->
    <script src="/resources/vendor/summernote/js/summernote.min.js"></script>
    <!-- Summernote init -->
    <script src="/resources/js/plugins-init/summernote-init.js"></script>

    <script src="/resources/js/dashboard/dashboard-1.js"></script>
    
    <script src="/resources/vendor/sweetalert2/dist/sweetalert2.min.js"></script>
    <script src="/resources/js/plugins-init/sweetalert.init.js"></script>
    <!-- drag & drop -->
    <script>
	    (function() {
	        
	        var $file  		= document.getElementById("file");
	        var dropZone 	= document.querySelector(".drop-zone");
	
	        var toggleClass = function(className) {
	            
	            var list = ["dragenter", "dragleave", "dragover", "drop"];
	
	            for (var i = 0; i < list.length; i++) {
	                if (className === list[i]) {
	                    dropZone.classList.add("drop-zone-" + list[i]);
	                } else {
	                    dropZone.classList.remove("drop-zone-" + list[i]);
	                }
	            }
	        }
	        
	        var showFiles = function(files) {
	            dropZone.innerHTML = "";
	            for(var i = 0, len = files.length; i < len; i++) {
	                dropZone.innerHTML += "<p>" + files[i].name + "</p>";
	            }
	        }
	
	        var selectFile = function(files) {
	            // input file ????????? ????????? ???????????? ??????
	            $file.files = files;
	            showFiles($file.files);
	        }
	        
	        $file.addEventListener("change", function(e) {
	            showFiles(e.target.files);
	        })
	
	        // ???????????? ????????? ????????? ???????????? ???
	        dropZone.addEventListener("dragenter", function(e) {
	            e.stopPropagation();
	            e.preventDefault();
	
	            toggleClass("dragenter");
	
	        })
	
	        // ???????????? ????????? dropZone ????????? ???????????? ???
	        dropZone.addEventListener("dragleave", function(e) {
	            e.stopPropagation();
	            e.preventDefault();
	
	            toggleClass("dragleave");
	
	        })
	
	        // ???????????? ????????? dropZone ????????? ????????? ?????? ???
	        dropZone.addEventListener("dragover", function(e) {
	            e.stopPropagation();
	            e.preventDefault();
	
	            toggleClass("dragover");
	
	        })
	
	        // ???????????? ????????? ??????????????? ???
	        dropZone.addEventListener("drop", function(e) {
	            e.preventDefault();
	
	            toggleClass("drop");
	
	            var files = e.dataTransfer && e.dataTransfer.files;
	
				alert($file);
	            if (files != null) {
	                if (files.length < 1) {
	                    alert("?????? ????????? ??????");
	                    return;
	                }
	              	if($file != null){
	                	selectFile(files);
	              	} else {
	              		selectFile2(files);
	              	}
	            } else {
	                alert("ERROR");
	            }
	
	        })
	
	    })();
    </script>
</div>    