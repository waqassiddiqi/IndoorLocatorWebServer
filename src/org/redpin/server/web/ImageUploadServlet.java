package org.redpin.server.web;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.redpin.server.standalone.util.Log;

@WebServlet("/MapUpload")
@MultipartConfig(fileSizeThreshold=1024*1024*10, maxFileSize=1024*1024*50, maxRequestSize=1024*1024*100)
public class ImageUploadServlet extends HttpServlet {
	
	private static final long serialVersionUID = -298827777586784903L;
	private Logger log = Log.getLogger();
	private static final String UPLOAD_DIR = "mapuploads";
	
	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        
        try {
         
	        String fileName = null;
	        for (Part part : request.getParts()) {
	            fileName = getFileName(part);
	            
	            fileName = Long.toString(System.currentTimeMillis()) + "_" + fileName.replace(" ", "_");
	            
	            part.write(uploadFilePath + File.separator + fileName);
	        }
	  
	        log.finer("Image uploaded successfully: " + fileName);
	        
	        response.setContentType("text/plain");
			response.getOutputStream().write(("OK|" + fileName).getBytes());
			response.setStatus(200);
			
        } catch(Exception e) {
        	
        	log.finer("Image upload failed: " + e.getMessage());
        	
        	response.setContentType("text/plain");
			response.getOutputStream().write(("FAILED|" + e.getMessage()).getBytes());
			response.setStatus(500);
        }
    }
  
    /**
     * Utility method to get file name from HTTP header content-disposition
     */
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }
}
