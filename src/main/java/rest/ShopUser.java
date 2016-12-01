/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;
import com.sun.jersey.multipart.FormDataMultiPart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import entity.Shop;
import java.io.BufferedOutputStream;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.annotation.security.RolesAllowed;
import javax.servlet.annotation.MultipartConfig;
import static rest.ShopRest.gson;
import security.IUserFacade;
import security.UserFacadeFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 * REST Web Service
 *
 * @author TimmosQuadros
 */
@Path("shop")
//@RolesAllowed("User")
@MultipartConfig(maxFileSize = 16177215)
public class ShopUser {

    @Context
    private UriInfo context;
    private IUserFacade facade;

    /**
     * Creates a new instance of ShopUser
     */
    public ShopUser() {
        facade = UserFacadeFactory.getInstance();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("user/edit")
    public void editShop(String content) throws Exception {
        Shop s = gson.fromJson(content, Shop.class);

//        if (s.getCategory() == null && s.getName() == null && s.getStreet() == null && s.getHouseNumber() == null && s.getGooglePlaceId() == null && s.getRating() == 0.0) {
            facade.updateShop(s);
//        }
    }
    
       @POST
       @Path("upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_JSON})
    public void handleFileUpload(String file){
//    public @ResponseBody String handleFileUpload( @RequestParam("name") String name,
//            @RequestParam("file") MultipartFile file){
		         System.out.println("REST" + file);
		//String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		String rootDirectory = "C:\\Users\\cherr\\Desktop\\Netbeans_Projects\\Module 3\\CA3";
		System.out.println("Root Directory "+rootDirectory);
//		try {
//			file.transferTo(new File(rootDirectory  + file.getOriginalFilename()));
//		} catch (IllegalStateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (!file.isEmpty()) {
//            try {
//                byte[] bytes = file.getBytes();
//                BufferedOutputStream stream =
//                        new BufferedOutputStream(new FileOutputStream(new File(name)));
//                stream.write(bytes);
//                stream.close();
//                return "You successfully uploaded " + name + "!";
//            } catch (Exception e) {
//                return "You failed to upload " + name + " => " + e.getMessage();
//            }
//        } else {
//            return "You failed to upload " + name + " because the file was empty.";
//        }
//return Response.status(200).build();
    }

	
    
//    @POST
//    @Consumes({MediaType.MULTIPART_FORM_DATA})
//    @Path("upload")
//    public void uploadFile(String content) throws Exception {
//        
//    }
//
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("user/edit")
//    public void editShop(String content) throws Exception {
//        Shop s = gson.fromJson(content, Shop.class);
//
////        if (s.getCategory() == null && s.getName() == null && s.getStreet() == null && s.getHouseNumber() == null && s.getGooglePlaceId() == null && s.getRating() == 0.0) {
//            facade.updateShop(s);
////        }
//    }
//    
//       @POST
//       @Path("upload")
//    @Consumes({MediaType.MULTIPART_FORM_DATA})
//    @Produces({MediaType.MULTIPART_FORM_DATA})
////    public @ResponseBody void handleFileUpload(MultipartFile file){
////    public @ResponseBody String handleFileUpload( @RequestParam("name") String name,
////            @RequestParam("file") MultipartFile file){
//		
//		//String rootDirectory = request.getSession().getServletContext().getRealPath("/");
//		String rootDirectory = "C:\\Users\\cherr\\Desktop\\Netbeans_Projects\\Module 3\\CA3";
//		System.out.println("Root Directory "+rootDirectory);
////		try {
////			file.transferTo(new File(rootDirectory  + file.getOriginalFilename()));
////		} catch (IllegalStateException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		if (!file.isEmpty()) {
////            try {
////                byte[] bytes = file.getBytes();
////                BufferedOutputStream stream =
////                        new BufferedOutputStream(new FileOutputStream(new File(name)));
////                stream.write(bytes);
////                stream.close();
////                return "You successfully uploaded " + name + "!";
////            } catch (Exception e) {
////                return "You failed to upload " + name + " => " + e.getMessage();
////            }
////        } else {
////            return "You failed to upload " + name + " because the file was empty.";
////        }
//    }
//
//	
//    
////    @POST
////    @Consumes({MediaType.MULTIPART_FORM_DATA})
////    @Path("upload")
////    public void uploadFile(String content) throws Exception {
////        
////    }
//    
////    @POST
////    @Consumes({MediaType.MULTIPART_FORM_DATA})
////    @Path("upload")
////    public void uploadFile(
////        //create a folder
////        //get the path
////        //store in db
////        @FormDataParam("file") InputStream uploadedInputStream,
////        @FormDataParam("file") FormDataContentDisposition fileDetail,
////        @FormDataParam("path") String path) {
////
////
////    // Path format //10.217.14.97/Installables/uploaded/
////    System.out.println("path::"+path);
////    String uploadedFileLocation = path
////            + fileDetail.getFileName();
////
////    // save it
////    writeToFile(uploadedInputStream, uploadedFileLocation);
////
////    String output = "File uploaded to : " + uploadedFileLocation;
////
////    Response.status(200).entity(output).build();
////    
////    
////
////}
//    
//
//// save uploaded file to new location
//private void writeToFile(InputStream uploadedInputStream,
//        String uploadedFileLocation) {
//
//    try {
//        OutputStream out = new FileOutputStream(new File(
//                uploadedFileLocation));
//        int read = 0;
//        byte[] bytes = new byte[1024];
//
//        out = new FileOutputStream(new File(uploadedFileLocation));
//        while ((read = uploadedInputStream.read(bytes)) != -1) {
//            out.write(bytes, 0, read);
//        }
//        out.flush();
//        out.close();
//    } catch (IOException e) {
//
//        e.printStackTrace();
//    }
//
//   }
//
   }
