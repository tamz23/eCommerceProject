package com.VithuProj.RestController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.omg.CORBA.IRObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.VithuProj.Models.Student;
import com.VithuProj.Service.StudentService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.json.*;

@Controller
@RequestMapping(value="/student")
public class StudentRestController {
	
	private static final ArrayList<Element> folderInfo = null;

	@Autowired
	StudentService student;
	
	private final String USER_AGENT = "Mozilla/5.0";
	
	SimpleDateFormat SimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat SimpleDateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
	
	@RequestMapping (value="/fstudent" ,method=RequestMethod.GET)
	public List FetchStudent(){
		
		
		List stud = student.fetchstudent();
		return stud;		
	}
	
	
	@RequestMapping(value="/fetchStudentCrit",method=RequestMethod.GET)
	public List<Student> fetchStudentCrit(){
		List<Student> stu = student.fetchStudentCrit();
		return stu;
	}
	
	@RequestMapping(value="/fstudenthql",method= RequestMethod.GET)
public List<Student> fetchstudenthql() throws ParseException {
	
	List<Student> s= student.fetchstudenthql();
	
	System.out.println("!------------------------------------------------------------------!");
	/*for(int i=0;i<s.size();i++){
		System.out.println(s.get(i).getNative()+", "+s.get(i).getNative().toUpperCase());
		//System.out.println(s.get(i).getNative().toString().concat( ", ".toString().concat(s.get(i).getNative().toUpperCase())));
		
		String nat = s.get(i).getNative().toUpperCase();
		
		s.get(i).setNative(nat);
		
		//String dob = s.get(i).getDob().toString();
		
		//Date a = SimpleDateFormat.parse(dob);
		
		//String b = SimpleDateFormat.format(s.get(i).getDob());
		
		Date bq = SimpleDateFormat1.parse(s.get(i).getDob());
		String reqFormat = SimpleDateFormat2.format(bq );
		s.get(i).setDob(reqFormat);
		
	}
*/	
	return s; 
}
	@RequestMapping(value="/updateStudent/{fname}/{id}",method=RequestMethod.POST)
	public int updateStudent(@PathVariable("fname") String frstname,@PathVariable("id") Integer id  ){
		int a = student.updateStudent(frstname, id);
		return a;
	}
	
	
	@RequestMapping(value="/updateStudentHbm", method= RequestMethod.POST)
	public @ResponseBody Student updateStudentHbm(@RequestBody Student stu){
	
		return student.updateStudentHbm(stu);
		
	}
	
	@RequestMapping(value="/bulkInsert", method=RequestMethod.POST)
	public void bulkInsert() throws ClassNotFoundException, SQLException{
		student.bulkInsert();
	}
	
	@RequestMapping(value="/saveStudentModel", method=RequestMethod.POST )
	public Student saveStudentModel(@RequestBody Student student1){
		Student s = student.saveStudentModel(student1);
		return s;
	}
	
	/*@RequestMapping(value="/saveStudentMdl/{fName}/{lName}/{nAtive}", method=RequestMethod.POST )
	public Student saveStudentMdl(@ModelAttribute Student student1){
		return student.saveStudentModel(student1);
	}*/
	
	/*@RequestMapping(value="/saveStudentMdl/{stu}", method=RequestMethod.PUT )
	public Student saveStudentMdl(@ModelAttribute("stu") Student student1){
		Student stu =  student.saveStudentModel(student1);
		return stu;
	}*/
	
	@RequestMapping(value="/saveStudentFormAction" , method=RequestMethod.POST)
	public String saveMdlFormAction(HttpServletRequest req, ModelMap model){
		
		//HttpServletRequest re =req;
		
		try{
			String firstName = req.getParameter("fName");
			String lastName = req.getParameter("lName");
			String nAtive = req.getParameter("nAt");
			
			Student stu = new Student();
			
			
			stu.setfName(firstName);
			stu.setlName(null);
			stu.setnAtive(nAtive);
			Student Savedstu = student.saveStudentModel(stu);
			
			
				return "student";
			
			
		}
		
		catch(Exception e){
			return "login";
		}
	}
	
	@RequestMapping(value="/saveStudentFormActionModelAttr", method = RequestMethod.POST )
	public void saveMdlFormActionModelAttr(@Valid Student stu, BindingResult res ){
		
		if(res.hasErrors()){
			System.out.println("ERROR");
		}
		else {
			System.out.println("Succes");
		}
		
	}
	
	@RequestMapping(value="/readSite", method=RequestMethod.GET)
	public void readASite() throws IOException, JSONException{
		
		
		// GENERATE EXCEL
		
		String filename = "C:/Vithu Dream/CarModels.xls" ;
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("CarVariants");  

        HSSFRow rowhead = sheet.createRow((short)0);
        rowhead.createCell(0).setCellValue("Year");
        rowhead.createCell(1).setCellValue("CarName");
        rowhead.createCell(2).setCellValue("Model");
        rowhead.createCell(3).setCellValue("Variant");
		
		int startYear = 2014;
		int rownum = 1;
		 
		while(startYear <= 2017){
			
			//try{
				
				System.out.println("**********"+startYear+"*********");
				String url = "https://portal.gfforsikring.dk/Webservices/CalculatorSPA/AutoLookup/ListCarBrands/"+startYear+"?type=json";

				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				// optional default is GET
				con.setRequestMethod("GET");

				//add request header
				con.setRequestProperty("User-Agent", USER_AGENT);

				int responseCode = con.getResponseCode();
				System.out.println("\nSending 'GET' request to URL : " + url);

				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
					
					System.out.println(inputLine);
					JSONArray jObject  = new JSONArray(inputLine);
					
					for(int i=0;i<jObject.length();i++){ //Looping through cars
						
						JSONObject jobj = new JSONObject(jObject.get(i).toString());
						System.out.println(jobj);

						String url1 = "https://portal.gfforsikring.dk/Webservices/CalculatorSPA/AutoLookup/ListCarModels/"+startYear+"/" +jobj.getInt("id")+"?type=json";

						URL obj1 = new URL(url1);
						HttpURLConnection con1 = (HttpURLConnection) obj1.openConnection();

						// optional default is GET
						con1.setRequestMethod("GET");

						//add request header
						con1.setRequestProperty("User-Agent", USER_AGENT);

						int responseCode1 = con.getResponseCode();
						System.out.println("\nSending 'GET' request to URL : " + url1);
						//System.out.println("Response Code : " + responseCode1);

						BufferedReader in1 = new BufferedReader(
						        new InputStreamReader(con1.getInputStream()));
						String inputLine1;
						StringBuffer response1 = new StringBuffer();
								
						while((inputLine1 = in1.readLine()) != null){
							
							JSONArray jObject1  = new JSONArray(inputLine1);
							
							for(int j=0;j<jObject1.length();j++){ //Looping through cars
								
								JSONObject jobj1 = new JSONObject(jObject1.get(j).toString());
								System.out.println(jobj1);
								
								String url2 = "https://portal.gfforsikring.dk/Webservices/CalculatorSPA/AutoLookup/ListCarVariant/"+startYear+"/"+jobj1.getInt("id")+"?type=json";

								URL obj2 = new URL(url2);
								HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();

								// optional default is GET
								con2.setRequestMethod("GET");

								//add request header
								con2.setRequestProperty("User-Agent", USER_AGENT);

								int responseCode2 = con.getResponseCode();
								System.out.println("\nSending 'GET' request to URL : " + url2);
								//System.out.println("Response Code : " + responseCode1);

								BufferedReader in2 = new BufferedReader(
								        new InputStreamReader(con2.getInputStream()));
								String inputLine2;
								StringBuffer response2 = new StringBuffer();
										
								while((inputLine2 = in2.readLine()) != null){
									
									JSONArray jObject2  = new JSONArray(inputLine2);
									
									for(int k=0;k<jObject2.length();k++){ //Looping through models
										
										JSONObject jobj2 = new JSONObject(jObject2.get(k).toString());
										
										System.out.println("VARIANT : "+jobj2);

							            HSSFRow row = sheet.createRow(rownum);
							            row.createCell(0).setCellValue(startYear);
							            row.createCell(1).setCellValue(jobj.get("value").toString());
							            row.createCell(2).setCellValue(jobj1.get("value").toString());
							            row.createCell(3).setCellValue(jobj2.get("value").toString());

							            FileOutputStream fileOut = new FileOutputStream(filename);
							            workbook.write(fileOut);
							            fileOut.close();
							            System.out.println("Your excel file has been generated!");
							            
							            rownum++;
									}
								}

							}
						}
						
					}
					
				}
				
				in.close();

				//print result
				System.out.println(response.toString());
				startYear++;
			
			/*}
			
			catch(Exception ex){
				System.out.println(ex.getLocalizedMessage());
			}*/
		}			
	}

	@RequestMapping(value="/task", method=RequestMethod.GET)
	public void task2() throws IOException, JSONException{
		
		StringBuilder sb = new StringBuilder();  

		String url = "https://www.gjensidige.dk/gj-webshop/rest/api/car/dict/buildCarDetailsDictionary";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream ());

		//{"name":"PRIVATE_CAR","brand":null,"model":null,"variant":null}
		
		//Create JSONObject here
	    JSONObject jsonParam = new JSONObject();
	    jsonParam.put("name", "PRIVATE_CAR");
	    jsonParam.put("brand", "");
	    jsonParam.put("model", "");
	    jsonParam.put("variant", "");
	    
		wr.writeBytes(jsonParam.toString());

		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
			
		}
		
		System.out.println(response);
	}
	
	
	@RequestMapping(value="/task1", method=RequestMethod.GET)
	public void task() throws IOException, JSONException{
		
		StringBuilder sb = new StringBuilder();  
		
		String url = "https://www.gjensidige.dk/gj-webshop/rest/api/car/dict/buildCarDetailsDictionary";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// optional default is GET
		
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		
		DataOutputStream wr = new DataOutputStream(con.getOutputStream ());
		
		//{"name":"PRIVATE_CAR","brand":null,"model":null,"variant":null}
		
		//Create JSONObject here
	    JSONObject jsonParam = new JSONObject();
	    jsonParam.put("name", "PRIVATE_CAR");
	  /*  jsonParam.put("brand", "");
	    jsonParam.put("model", "");
	    jsonParam.put("variant", "");*/
	    
		wr.writeBytes(jsonParam.toString());
		
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		System.out.println(response);
	}
	
	@RequestMapping(value="task3", method=RequestMethod.GET)
	public Object request() throws JSONException{
		
		String url = "http://www.gjensidige.dk/gj-webshop/rest/api/car/dict/buildCarDetailsDictionary";	
		
		 JSONObject jsonParam = new JSONObject();
		 jsonParam.put("name", "PRIVATE_CAR");
		
		 Object obj = jsonParam;
		 
		 RestTemplate restTemplate = new RestTemplate();
		 Object result = restTemplate.postForEntity(url, obj.toString(),  String.class);
		 return result;
		
	}
	
	@RequestMapping(value="task4", method=RequestMethod.GET)
	public ClientResponse request1() throws JSONException{
		
		String url = "http://www.gjensidige.dk/gj-webshop/rest/api/car/dict/buildCarDetailsDictionary";	
		
		JSONObject jsonParam = new JSONObject();
		 jsonParam.put("name", "PRIVATE_CAR");
		
		 com.sun.jersey.api.client.Client client = new com.sun.jersey.api.client.Client();

		ClientResponse cr = client
				.resource(url)
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.entity(jsonParam)
				.post(ClientResponse.class);
		
		return cr;
		
	}

	@RequestMapping(value="/task5", method=RequestMethod.GET)
	public JSONObject fetchUrl(){
		
		// GENERATE EXCEL
		
		String filename = "C:/Vithu Dream/CarModels_P2_Init.xls" ;
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("CarVariants");  

        HSSFRow rowhead = sheet.createRow((short)0);
        rowhead.createCell(0).setCellValue("Brand");
        rowhead.createCell(1).setCellValue("Model");
        rowhead.createCell(2).setCellValue("Variant");
        rowhead.createCell(3).setCellValue("YearRange");
        rowhead.createCell(4).setCellValue("Year");
		
		try {

			Client client = Client.create();

			WebResource webResource = client
			   .resource("https://www.gjensidige.dk/gj-webshop/rest/api/car/dict/buildCarDetailsDictionary");

			//name", "PRIVATE_CAR")
			
			String input = "{\"name\":\"PRIVATE_CAR\"}";

			ClientResponse response = webResource.type("application/json")
			   .post(ClientResponse.class, input);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}

			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
			
			JSONObject jObject  = new JSONObject(output);
			
			//System.out.println(jObject.get("body"));
			
			JSONArray  jObject1 = new JSONArray(jObject.get("body").toString());	
			
			//System.out.println(jObject1);
			
			int counter=0; int rownum=0;
			for(int i=0;i<jObject1.length();i++){
				
				//if(i>=7){
				
				rownum++;
				
				
				JSONObject obj = new JSONObject(jObject1.get(i).toString());
				
				System.out.println("******"+(i+1)+" ******"+obj.get("value"));
				
				//System.out.println(jObject1.get(i));
				//System.out.println(obj.get("key"));
				
				if(obj.get("key").toString().equals("0")){
					counter = 99;
				}
				
				
				if(counter != 99 && !obj.get("key").toString().equals("0")){
				
					try{
						WebResource webResource1 = client
								   .resource("https://www.gjensidige.dk/gj-webshop/rest/api/car/dict/buildCarDetailsDictionary");

								//name", "PRIVATE_CAR")
								
								String input1 = "{\"name\":\"PRIVATE_CAR\",\"brand\":"+obj.get("key")+"}";

								//System.out.println(input1);
								
								ClientResponse response1 = webResource1.type("application/json")
								   .post(ClientResponse.class, input1);

								if (response1.getStatus() != 200) {
									throw new RuntimeException("Failed : HTTP error code : "
									     + response1.getStatus());
								}

								//System.out.println("BillModel .... \n");
								String output1 = response1.getEntity(String.class);
								//System.out.println(output1);
								
								JSONObject jObject3  = new JSONObject(output1);
								
								//System.out.println(jObject.get("body"));
								
								JSONArray  jObject4 = new JSONArray(jObject3.get("body").toString());	
								
								for(int j=0;j<jObject4.length();j++){
									
									JSONObject obj1 = new JSONObject(jObject4.get(j).toString());
									
									//System.out.println(jObject4.get(j));
									
									try{
										
										WebResource webResource2 = client
												   .resource("https://www.gjensidige.dk/gj-webshop/rest/api/car/dict/buildCarDetailsDictionary");

												//name", "PRIVATE_CAR")
												
												String input2 = "{\"name\":\"PRIVATE_CAR\",\"brand\":"+obj.get("key")+",\"model\":"+obj1.get("key")+"}";

												//System.out.println(input2);
												
												ClientResponse response2 = webResource2.type("application/json")
												   .post(ClientResponse.class, input2);

												if (response2.getStatus() != 200) {
													throw new RuntimeException("Failed : HTTP error code : "
													     + response2.getStatus());
												}

												//System.out.println("BillModel .... \n");
												String output2 = response2.getEntity(String.class);
												//System.out.println(output2);
													
												JSONObject jObject5  = new JSONObject(output2);
												
												//System.out.println(jObject.get("body"));
												
												JSONArray  jObject6 = new JSONArray(jObject5.get("body").toString());	
												
												for(int k=0;k<jObject6.length();k++){
													
													JSONObject obj2 = new JSONObject(jObject6.get(k).toString());
													
													//System.out.println(jObject6.get(k));
												
													try {
														WebResource webResource3 = client
																   .resource("https://www.gjensidige.dk/gj-webshop/rest/api/car/dict/buildCarDetailsDictionary");

																//name", "PRIVATE_CAR")
																
																String input3 = "{\"name\":\"PRIVATE_CAR\",\"brand\":"+obj.get("key")+",\"model\":"+obj1.get("key")+",\"variant\":"+obj2.get("key")+"}";                           

																//System.out.println(input3);
																
																ClientResponse response3 = webResource3.type("application/json")
																   .post(ClientResponse.class, input3);

																if (response3.getStatus() != 200) {
																	throw new RuntimeException("Failed : HTTP error code : "
																	     + response3.getStatus());
																}

																//System.out.println("BillModel .... \n");
																String output3 = response3.getEntity(String.class);
																//System.out.println(output3);

																JSONObject jObject7  = new JSONObject(output3);
																
																//System.out.println(jObject.get("body"));
																
																JSONArray  jObject8 = new JSONArray(jObject7.get("body").toString());	
																
																for(int l=0;l<jObject8.length();l++){
																	
																	JSONObject obj3 = new JSONObject(jObject8.get(l).toString());
																	
																	//System.out.println(jObject8.get(l));
																
																	try{
																		WebResource webResource4 = client
																				   .resource("https://www.gjensidige.dk/gj-webshop/rest/api/car/dict/buildCarDetailsDictionary");

																				//name", "PRIVATE_CAR")
																				
																				String input4 = "{\"name\":\"PRIVATE_CAR\",\"brand\":"+obj.get("key")+",\"model\":"+obj1.get("key")+",\"variant\":"+obj2.get("key")+",\"years\":\""+obj3.get("value")+"\"}";                           

																				//System.out.println(input4);
																				
																				ClientResponse response4 = webResource4.type("application/json")
																				   .post(ClientResponse.class, input4);

																				if (response4.getStatus() != 200) {
																					throw new RuntimeException("Failed : HTTP error code : "
																					     + response4.getStatus());
																				}

																				//System.out.println("BillModel .... \n");
																				String output4 = response4.getEntity(String.class);
																				//System.out.println(output4);

																				JSONObject jObject9  = new JSONObject(output4);
																				
																				//System.out.println(jObject.get("body"));
																				
																				JSONArray  jObject10 = new JSONArray(jObject9.get("body").toString());	
																				
																				for(int m=0;m<jObject10.length();m++){
																					
																					JSONObject obj4 = new JSONObject(jObject10.get(m).toString());
																					
																					//System.out.println(jObject10.get(m));
																							

																			        
																			        HSSFRow row = sheet.createRow(rownum);
																		            row.createCell(0).setCellValue(obj.get("value").toString());
																		            row.createCell(1).setCellValue(obj1.get("value").toString());
																		            row.createCell(2).setCellValue(obj2.get("value").toString());
																		            row.createCell(3).setCellValue(obj3.get("value").toString());
																		            row.createCell(4).setCellValue(obj4.get("value").toString());

																		            FileOutputStream fileOut = new FileOutputStream(filename);
																		            workbook.write(fileOut);
																		            fileOut.close();
																		            //System.out.println("Your excel file has been generated!");
																		            
																		            rownum++;
																				}
																	}
																	catch(Exception ex){
																		System.out.println(ex.getLocalizedMessage()+"brand: "+obj.get("value")+"model: "+obj1.get("value")+ "variant: "+obj2.get("value")+"years: "+obj3.get("value"));
																	}
																	
																}
																
													}
													catch(Exception ex){
														System.out.println(ex.getLocalizedMessage()+"brand: "+obj.get("value")+"model: "+obj1.get("value")+ "variant: "+obj2.get("value"));
													}
												}		

									}
									catch(Exception ex){
										System.out.println(ex.getMessage()+"brand: "+obj.get("value")+"model: "+obj1.get("value"));
									}
								}
								
					}
					catch(Exception ex){
						System.out.println("ERROR: "+ex.getMessage()+" Brand:"+  obj.get("value") );
					}

				//}
				
				}
			}
			//}
			
			return jObject;

		  } catch (Exception e) {

			e.printStackTrace();
			return null;
		  }
		

		}
		
	

	@RequestMapping(value="/task6", method=RequestMethod.GET)
	public JSONObject fetchUrlForbrands(){
		
		// GENERATE EXCEL
		
		String filename = "C:/Vithu Dream/CarModels_P2Brands.xls" ;
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("CarVariants");  

        HSSFRow rowhead = sheet.createRow((short)0);
        rowhead.createCell(0).setCellValue("Brand");
        //rowhead.createCell(1).setCellValue("Model");
        //rowhead.createCell(2).setCellValue("Variant");
        //rowhead.createCell(3).setCellValue("YearRange");
        //rowhead.createCell(4).setCellValue("Year");
		
		try {

			Client client = Client.create();

			WebResource webResource = client
			   .resource("https://www.gjensidige.dk/gj-webshop/rest/api/car/dict/buildCarDetailsDictionary");

			//name", "PRIVATE_CAR")
			
			String input = "{\"name\":\"PRIVATE_CAR\"}";

			ClientResponse response = webResource.type("application/json")
			   .post(ClientResponse.class, input);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}

			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
			
			JSONObject jObject  = new JSONObject(output);
			
			//System.out.println(jObject.get("body"));
			
			JSONArray  jObject1 = new JSONArray(jObject.get("body").toString());	
			
			//System.out.println(jObject1);
			
			int counter=0; int rownum=0;
			for(int i=0;i<jObject1.length();i++){
				
				
				rownum++;
				
				
				JSONObject obj = new JSONObject(jObject1.get(i).toString());
				
				System.out.println("******"+(i+1)+" ******"+obj.get("value"));
				
																						        HSSFRow row = sheet.createRow(rownum);
																		            row.createCell(0).setCellValue(obj.get("value").toString());
																		            //row.createCell(1).setCellValue(obj1.get("value").toString());
																		            //row.createCell(2).setCellValue(obj2.get("value").toString());
																		            //row.createCell(3).setCellValue(obj3.get("value").toString());
																		            //row.createCell(4).setCellValue(obj4.get("value").toString());

																		            FileOutputStream fileOut = new FileOutputStream(filename);
																		            workbook.write(fileOut);
																		            fileOut.close();
																		            //System.out.println("Your excel file has been generated!");
																		            
																		            rownum++;
																				}
																									


		  } catch (Exception e) {

			e.printStackTrace();
			return null;
		  }
		return null;
		

		}
		
	
	@RequestMapping(value="parseSite", method=RequestMethod.GET)
	public void parseWebsite() throws IOException{
		
		 Document doc = Jsoup.connect("https://www.booking.com/hotel/fr/talisman-bed-and-breakfast.en-gb.html?aid=356980;label=gog235jc-hotel-XX-fr-talismanNbedNandNbreakfast-unspec-in-com-L%3Aen-O%3Aabn-B%3Achrome-N%3AXX-S%3Abo-U%3AXX-H%3As;sid=4fe81254d0753e8978b37e61832bc8b8;dist=0&sb_price_type=total&type=total&").get();  
         String title = doc.title();  
         System.out.println("title is: " + title);
         
         Elements loginform = doc.select("div.facilitiesChecklist > *");
 		
         for(Element el :loginform ){ 
        	 System.out.println(el.tagName() +"- " + el.getAllElements().select("h5").text() );
        	 
        	 Elements ele = el.getAllElements().select("ul > li");
        	 
        	 for(Element ell : ele){
        		 System.out.println(ell.tagName() +" - "+ell.text() );
        	 }
        	 	 
         }
         
         System.out.println(doc.getElementById("checkin_policy").getElementsByTag("p").text() );
         System.out.println(doc.getElementById("checkout_policy").getElementsByTag("p").text() );
         System.out.println(doc.getElementById("cancellation_policy").getElementsByTag("p").text() );
         
         System.out.println(doc.getElementById("children_policy").getElementsByTag("p").text() );
         System.out.println(doc.getElementsByClass("description").get(0).getElementsByTag("p").text() );
         
	}
	
	@RequestMapping(value="/googleSearch",method=RequestMethod.POST)
	public void fetch(@RequestBody HashMap<String, String> qry) throws IOException{
		
		String google = "http://www.google.com/search?q=";
		String search = qry.get("q");
		String charset = "UTF-8";
		String userAgent = "Mozilla/5.0"; // Change this to your company's name and bot homepage!

		Elements links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select(".g>.r>a");

		System.out.println(links.size());
		
		//Elements links = Jsoup.connect(google).get();
		
		for (Element link : links) {
		    String title = link.text();
		    String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
		    url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

		    if (!url.startsWith("http")) {
		        continue; // Ads/news/etc.
		    }

		    if(url.contains("booking.com") ){
		    	System.out.println("Title: " + title);
			    System.out.println("URL: " + url);
			    
			    Document bookLnk = Jsoup.connect(url).get();
			    
			    System.out.println( bookLnk.getElementById("hp_hotel_name").text() );
			    
		    }
		    
		}
	}

	@RequestMapping(value="/readExcel", method=RequestMethod.GET) 
	public void readExcel() throws IOException{
		
		
		
		 String excelFilePath = "C:/Vithu Dream/booking.com/Image Collection 1000_1.xlsx";
	        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	         
	        Workbook workbook = new XSSFWorkbook(inputStream);
	        Sheet firstSheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = firstSheet.iterator();
	        int rowNum = 1; int dum=0;
	        while (iterator.hasNext()) {
	        	dum++;
	        	if(dum>501){
            		break;
            	}
            	
	        	
	            Row nextRow = iterator.next();
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	           
	            String searchkeyword=""; String crawledURL="";
	            for(int i=0;i<4;i++){
	            
	            	
	            	
	            	if(i==0){
	            		Cell cell = cellIterator.next();
	            	}
	            	if(i> 0){
	            		
	            		Cell cell = cellIterator.next();

		            	switch (cell.getCellType()) {
	                    case Cell.CELL_TYPE_STRING:
	                    	//searchkeyword = (searchkeyword.equals(""))? (cell.getStringCellValue().trim().replace(' ', '+')): (searchkeyword+ "+"+cell.getStringCellValue().trim().replace(' ', '+') );
	                    	
	                    	searchkeyword = (searchkeyword.equals(""))? (cell.getStringCellValue()): (searchkeyword+ "+"+cell.getStringCellValue() );
	                    	
	                        //System.out.print(cell.getStringCellValue().replace(' ', '+'));
	                        break;
	                    case Cell.CELL_TYPE_NUMERIC:
	                    	
	                    	Double a = cell.getNumericCellValue() ;
	                    	String[] b = StringUtils.split(String.valueOf(a), ".");
	                    	System.out.println(b[0]);
	                    	
	                    	//searchkeyword = (String) ((searchkeyword.equals(""))? (StringUtils.replace( String.valueOf( b[0]), " ", "+")): (searchkeyword+ "+"+ StringUtils.replace( String.valueOf( b[0]), " ", "+") ));
	                    	
	                    	searchkeyword = (String) ((searchkeyword.equals(""))? ( String.valueOf( b[0])): (searchkeyword+ "+"+  String.valueOf( b[0])));
	                    	
	                        //System.out.print(String.valueOf( cell.getNumericCellValue()) );
	                        break;
		            	}

	            		
	            	}
	            		 

		            
	            }
	            
	            // Connect with Booking.com
	            
	            String google = "http://www.google.com/search?q=";
	    		String search = searchkeyword;
	    		String charset = "UTF-8";
	    		String userAgent = "Googlebot/2.1 (+http://www.google.com/bot.html)"; // Change this to your company's name and bot homepage!
	    		
	    		
	    		System.out.println("RowNum: "+ ++rowNum + searchkeyword);
	    		
	    		Elements links = null;
	    		
	    		try {
	    			 links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent)
	    					 //.followRedirects(true)
	    					 .get().select(".g>.r>a");	
	    			 System.out.println(links.size());
	 	    		
	    			 nextRow.createCell(15).setCellValue(searchkeyword);
	    			 
	 	    		//Elements links = Jsoup.connect(google).get();
	    			 int countOfBookingSite = 0; int countOfTrivagoSite = 0; int countOfHotelsSite = 0;
	 	    		for (Element link : links) {
	 	    		    String title = link.text();
	 	    		    String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
	 	    		    url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

	 	    		    if (!url.startsWith("http")) {
	 	    		        continue; // Ads/news/etc.
	 	    		    }
	 	    		    
	 	    		  /*  if(url.contains("travelguru.com")){
	 	    		    	
	 	    		    	countOfBookingSite++;
	 	    		    	nextRow.createCell(17+countOfBookingSite).setCellValue(url); 
	 	    		    	 nextRow.createCell(17+countOfBookingSite).setCellValue(url); 
	 	    		    }
	 	    		    
	 	    		    if(url.contains("yatra.com")){
	 	    		    	
	 	    		    	countOfTrivagoSite++;
	 	    		    	System.out.println("Title: " + title);
	 	    			    System.out.println("URL: " + url);
	 	    			    
	 	    			    //nextRow.createCell(19).setCellValue(countOfBookingSite); 
	 		    		    nextRow.createCell(27+countOfTrivagoSite).setCellValue(url); 
	 	    		    }
	 	    		    
	 	    		   if(url.contains("makemytrip")){
	 	    		    	
	 	    			   countOfTrivagoSite++;
	 	    		    	System.out.println("Title: " + title);
	 	    			    System.out.println("URL: " + url);
	 	    			    
	 	    			    //nextRow.createCell(19).setCellValue(countOfBookingSite); 
	 		    		    nextRow.createCell(32+countOfTrivagoSite).setCellValue(url);
	 	    			   
	 	    		    }*/
	 	    		    
	 	    		    if(url.contains("booking.com") ){
	 	    		    	
	 	    		    	countOfBookingSite++;
	 	    		    	System.out.println("Title: " + title);
	 	    			    System.out.println("URL: " + url);
	 	    			  
	 	    			    
	 		    		    nextRow.createCell(17+countOfBookingSite).setCellValue(url); 
	 	    			    
	 	    		    }
	 	    		    
	 	    		   if(url.contains("agoda.") ){
	 	    		    	
	 	    			   countOfTrivagoSite++;
	 	    		    	System.out.println("Title: " + title);
	 	    			    System.out.println("URL: " + url);
	 	    			    
	 	    			    //nextRow.createCell(19).setCellValue(countOfBookingSite); 
	 		    		    nextRow.createCell(27+countOfTrivagoSite).setCellValue(url); 
	 	    			    
	 	    		    }
	 	    		   
	 	    		  if(url.contains("hotels.com") ){
	 	    		    	
	 	    		    	countOfHotelsSite++;
	 	    		    	System.out.println("Title: " + title);
	 	    			    System.out.println("URL: " + url);
	 	    			    
	 	    			    //nextRow.createCell(22).setCellValue(countOfBookingSite); 
	 		    		    nextRow.createCell(32+countOfHotelsSite).setCellValue(url); 
	 	    			    
	 	    		    }
	 	    		    
	 	    		  //  System.out.println("countOfBooking.com count "+ countOfBookingSite );
	 	    	  
	 	    		}
	 	    		nextRow.createCell(13).setCellValue(searchkeyword);
	    		}
	    	
	    		catch(HttpStatusException ex){
	    			System.out.println("FETCH error: " + ex.getMessage());
	    			
	    			 /*FileOutputStream fileOut = new FileOutputStream(excelFilePath);
	    	            workbook.write(fileOut);
	    	            fileOut.close();*/
	    	            break;
	    		}
	    		catch(Exception ex){
	    			System.out.println("exception: "+  ex.getMessage());
	    			/* FileOutputStream fileOut = new FileOutputStream(excelFilePath);
	    	            workbook.write(fileOut);
	    	            fileOut.close();*/
	    	          //  break;
	    			
	    		}
	    		
	            
	            System.out.println("***********************************************");
	        
	        }
	         
	        FileOutputStream fileOut = new FileOutputStream(excelFilePath);
            workbook.write(fileOut);
            fileOut.close();
	        
	        workbook.close();
	        inputStream.close();
		
	}
	
	@RequestMapping(value="/checkLang", method=RequestMethod.GET)
	public void checkLang() throws IOException{
		
		
		String excelFilePath = "C:/Vithu Dream/booking.com/Booking_langCheck.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
         
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
           
            String searchkeyword=""; String crawledURL="";
            for(int i=0;i<2;i++){
            	
            	if(i==0){
            		Cell cell = cellIterator.next();
            	}
            	if(i> 0){
            		
            		Cell cell = cellIterator.next();
            		
            		if(cell.getStringCellValue().startsWith("http")){
            			//Document bookLnk = Jsoup.connect(cell.getStringCellValue()).get();
            			
            			Elements bookLnks = Jsoup.connect(cell.getStringCellValue()).get().getElementsByTag("img");

            			int conCnt=0;
            			for(Element el : bookLnks){
            				System.out.println(el.absUrl("src"));
            				
            				if(el.absUrl("src").contains("http")){
            					nextRow.createCell(conCnt+4).setCellValue(el.absUrl("src"));
                				conCnt++;
            				}
            				
            			}
            
            			 FileOutputStream fileOut = new FileOutputStream(excelFilePath);
            		        workbook.write(fileOut);
            		        fileOut.close();
            		        
            			
            			/*Element a = bookLnk.getElementById("current_language");
            			System.out.println(a.text());
            			if(bookLnk.getElementById("hp_hotel_name") != null){
            				
            				System.out.println("Hotel name: "+ bookLnk.getElementById("hp_hotel_name").text() );
            				
            			}
            			else {
            				
            				System.out.println("not a valid link");
            				
            			}*/
            			System.out.println("************************************************");            		
            		}          		
            	}
            }
        }  
	
       
        workbook.close();
        inputStream.close();

        
	}
	
	@RequestMapping(value="/chk", method=RequestMethod.GET)
	public void fetch() throws IOException{

		String path= "C:/Vithu Dream/booking.com/output/";
		String excelFilePath = "C:/Vithu Dream/booking.com/URLs.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
         
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        
        String url = ""; int  i =0;
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
           
            String searchkeyword=""; String crawledURL="";
           
            
            	Cell cell = cellIterator.next();
        		 
        		url = cell.getStringCellValue();
        		Elements bookLnk = null;
        		try{
        			
        			Document docu = Jsoup.connect(url).maxBodySize(0).get();
        			 bookLnk = docu.getElementsByTag("img");
        			 
        			
        			 
        			 int k=1;
             		for (Element el : bookLnk) {

     					if (el.absUrl("src").contains("/images/hotel/")) {

     						String actualURl = el.absUrl("src");
     						
     						System.out.println(actualURl);
     						
     						String a = "/hotel/";
     						String b1[] = actualURl.split(a);

     						//System.out.println("b1[0] "+b1[0]);
     						//System.out.println("b1[1] "+ b1[1]);
     						String c[] = b1[1].split("/");
     						
     						String Urls="";
     						if(c.length == 3){
     							Urls = b1[0] + a + "max1024x768" + "/" + c[1] + "/" + c[2];
     						}
     						if(c.length == 2){
     							Urls = b1[0] + a + "max1024x768" + "/" + c[1];
     						}
     						
     						System.out.println(Urls);

     						URL url1 = new URL(Urls);
     						InputStream is = url1.openStream();
     						
     						File f = new File(path+"/imgfolder"+(i+1));
     						f.mkdirs();
     						
     						OutputStream os = new FileOutputStream(path +"/imgfolder"+ (i+1) +"/image"+(k) + ".jpg");

     						byte[] b = new byte[2048];
     						int length;

     						while ((length = is.read(b)) != -1) {
     							os.write(b, 0, length);
     						}

     						is.close();
     						os.close();
     						k++;
    					}
             		}

             		
             		 try{
        				 Elements a = 	docu.getElementById("showMap2").getElementsByTag("span");
        				 //String hotelAddress1 = 	docu.getElementsByClass("hp_address_subtitle").get(0).getElementsByTag("span").get(1).text();;
        				 
        				 for(Element aa : a){
        					 
        					 System.out.println(aa.text());
        					 
        				 }
        				 
            			 /*System.out.println("HotelAddress: "+hotelAddress);
            			 FileWriter fw = new FileWriter(path +"/imgfolder"+ (i+1) +"/textfile.txt");
            			 BufferedWriter bw = new BufferedWriter(fw);
            			 bw.write(hotelAddress);
            			 bw.newLine();
            			 bw.write(hotelAddress1);
            			 bw.close();
            			 fw.close();*/
        			 }
        			 catch(Exception ex){
        				 System.out.println("No address info " + ex.getLocalizedMessage());
        			 }
             		
        		}
        		catch(Exception ex){
        			System.out.println(ex.getMessage());
        		}
        		

        		
            i++;
        }
		
		
				
	}
	
	@RequestMapping(value="/accessImages", method=RequestMethod.GET)
	@ResponseBody
	public String[] acessImagesfromFolder(){
		
		File file = new File("C:/Users/tamil/Desktop/vithuSampleProj/src/main/webapp/WEB-INF/img/output");
        File[] files = file.listFiles();
        
        String[] fNames = new String[files.length];
        
        for(int i=0; i <files.length;i++){
        	System.out.println("folderName: "+files[i].getName() );
        	fNames[i]= files[i].getName();
        	
        	/*if(files[i].isDirectory()){																																			
        		
        		File subfile = new File("C:/Users/tamil/Desktop/vithuSampleProj/src/main/webapp/WEB-INF/img/output/"+files[i].getName());
                File[] subfiles = subfile.listFiles();
                for(File sf: subfiles){
                	System.out.println("fileName: "+ sf.getName());
                }
        		
        	}*/
        	
        }
		System.out.println("folder count: "+ files.length);
		return fNames;
	}
	
	
	@RequestMapping(value="/accessIma/{foldNam}", method=RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> acessImagesfromSingleFolder(@PathVariable("foldNam") String folderName) throws IOException{
		
		String path = "C:/Users/tamil/Desktop/vithuSampleProj/src/main/webapp/WEB-INF/img/output/";
		
		File file = new File(path+folderName);
        File[] files = file.listFiles();
        String addressOnFile="";
        
        boolean isSubFolderPresent =false;
        for(File f : files){
        	if(f.isDirectory()){
        		isSubFolderPresent = true;
        		break;
        	}	
        }
        
        String[] fNames = new String[files.length];
        
        HashMap<String, Object> info = new HashMap<String, Object>();
        String addr="";
        
        int subfolderCount = 0; List<HashMap<String, String>> folderInfo = new ArrayList();
        
        
        
        if (isSubFolderPresent == false) { // NO SUB FOLDER

        	
			for (int i = 0; i < files.length; i++) {
				System.out.println("fileName: " + files[i].getName());

				fNames[i] = files[i].getName();

				HashMap<String,String> foldInfo = new HashMap<String, String>();
				if (files[i].getName().contains(".txt")) {

					String fileName = files[i].getName();
					try {

						FileReader inputFile = new FileReader(path + folderName + "/" + fileName);
						BufferedReader bufferReader = new BufferedReader(inputFile);
						String line;
						
						while ((line = bufferReader.readLine()) != null) {
							System.out.println(line);
							
							if(i==0){
								addressOnFile = line;
							}
							if(i==1){
								foldInfo.put(fileName, line);
							}
							
						}
						addr = line;
						bufferReader.close();
					} catch (Exception e) {
						System.out.println("Error while reading file line by line:" + e.getMessage());
					}
				}
				folderInfo.add(foldInfo);
			}
			info.put("scenario", "singleProd");
			info.put("fileAddress", addressOnFile);
			info.put("address", addr);
			info.put("files", fNames);
			return info;

		} else { // WITH Subfolder

			for (int i = 0; i < files.length; i++) {

				HashMap<String,String> foldInfo = new HashMap<String, String>();
				
				if (files[i].getName().contains(".txt")) {

					String fileName = files[i].getName();
					try {

						FileReader inputFile = new FileReader(path + folderName + "/" + fileName);
						BufferedReader bufferReader = new BufferedReader(inputFile);
						String line;
						
						while ((line = bufferReader.readLine()) != null) {
							System.out.println(line);
							addressOnFile = line;
							foldInfo.put("folderName", "main");
							foldInfo.put("addressInfo", line);	
						}
						addr = line;
						bufferReader.close();
					} catch (Exception e) {
						System.out.println("Error while reading file line by line:" + e.getMessage());
					}
					
					folderInfo.add(foldInfo);
				}
				
				if (files[i].isDirectory()) {

					File sfile = new File(path + folderName + "/" + files[i].getName());
					
					File[] sfiles = sfile.listFiles();
					
					for (int j = 0; j < sfiles.length; j++) {
					
						HashMap<String,String> foldInfo1 = new HashMap<String, String>();
						if (sfiles[j].getName().contains(".txt")) {

							String sfileName = sfiles[j].getName();
							//try {

								FileReader sinputFile = new FileReader(
										path + folderName + "/" + files[i].getName() + "/" + sfileName);
								System.out.println(path + folderName + "/" + files[i].getName() + "/" + sfileName);
								BufferedReader sbufferReader = new BufferedReader(sinputFile);
								String sline;
								int cnt=0;
								while ((sline = sbufferReader.readLine()) != null) {
									System.out.println(sline);
									if(cnt==0){
										addressOnFile = sline;
									}
									if(cnt==1){
										foldInfo1.put("folderName", files[i].getName());
										foldInfo1.put("addressInfo", sline);	
									}
									
									cnt++;
								}
								sbufferReader.close();
							/*} catch (Exception e) {
								System.out.println("Error while reading file line by line:" + e.getMessage());
							}*/
								
								folderInfo.add(foldInfo1);
						}
						
					}

				}

			}

			info.put("scenario", "multiProd");
			info.put("fileAddress", addressOnFile);
			info.put("address", "");
			info.put("files", folderInfo);
			return info;

		}
        
	}

	
	@RequestMapping(value="/accessImageFromMain/{foldNam}", method=RequestMethod.GET)
	@ResponseBody
	public String[] acessImagesfromMainFolder(@PathVariable("foldNam") String folderName) throws IOException{
		
		String path = "C:/Users/tamil/Desktop/vithuSampleProj/src/main/webapp/WEB-INF/img/output/";

		File file = new File(path + folderName);
		File[] files = file.listFiles();

		String[] fNames = new String[files.length];

		HashMap<String, Object> info = new HashMap<String, Object>();
		String addr = "";

		for (int i = 0; i < files.length; i++) {
			System.out.println("fileName: " + files[i].getName());

			fNames[i] = files[i].getName();
			
		}
		
		return fNames;

	}
	
	@RequestMapping(value="/accessIma/{foldNam}/{sub}", method=RequestMethod.GET)
	@ResponseBody
	public String[] acessImagesfromSubFolder(@PathVariable("foldNam") String folderName, @PathVariable("sub") String subfoldName) throws IOException{
		
		String path = "C:/Users/tamil/Desktop/vithuSampleProj/src/main/webapp/WEB-INF/img/output/";
		
		File file = new File(path+folderName);
        File[] files = file.listFiles();
        
        
        String[] fNames = new String[files.length];
        
        HashMap<String, Object> info = new HashMap<String, Object>();
        String addr="";
        
        int subfolderCount = 0; List<HashMap<String, String>> folderInfo = new ArrayList();

        
		for (int i = 0; i < files.length; i++) {
			System.out.println("fileName: " + files[i].getName());

			fNames[i] = files[i].getName();
			
		}
              
		return fNames;
	}

	
	@RequestMapping(value="/saveimage/{Type}/{foName}/{curImg}/{saveImage}/{saveImgCnt}/{reason}", method=RequestMethod.POST)
	@ResponseBody
	public String saveFoundImage(@PathVariable("Type") String type ,@PathVariable("foName") String foldname,
									@PathVariable("curImg") String fileNam, @PathVariable("saveImage") String saveImage, 
									@PathVariable("saveImgCnt") Integer saveImageCount, @PathVariable("reason") String reason	) throws IOException {
		
		saveImage = saveImage.replace("$", ".");
		
		System.out.println("C:/Users/tamil/Desktop/vithuSampleProj/src/main/webapp/WEB-INF/img/output/"+foldname+"/"+fileNam);
		File source = new File("C:/Users/tamil/Desktop/vithuSampleProj/src/main/webapp/WEB-INF/img/output/"+foldname+"/"+fileNam);
		boolean isRenamed =	source.renameTo(new File("C:/Users/tamil/Desktop/vithuSampleProj/src/main/webapp/WEB-INF/img/output/"+foldname+"/"+ saveImage+".jpg"));
		System.out.println("saveImage "+ saveImage+".jpg");
		File sourceNew = null;
		if(isRenamed){
			sourceNew = new File("C:/Users/tamil/Desktop/vithuSampleProj/src/main/webapp/WEB-INF/img/output/"+foldname+"/"+ saveImage+".jpg");
		}
		
		
		File dest = null;
		
		if(type.equals("found")){
			 dest = new File("C:/Vithu Dream/booking.com/Delivery/Found");
		}
		else if(type.equals("partFound")){
			 dest = new File("C:/Vithu Dream/booking.com/Delivery/partFound");
		}
		
		try {
		    FileUtils.copyFileToDirectory(sourceNew, dest);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		
		String excelFilePath = "C:/Vithu Dream/booking.com/Delivery/output.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
         
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        Row row = sheet.createRow (saveImageCount);
		row.createCell(0).setCellValue(foldname);
		row.createCell(1).setCellValue(type);
		if(type.equals("partFound")){	
			row.createCell(2).setCellValue(reason);
		}
		
		
		FileOutputStream fileOut = new FileOutputStream(excelFilePath);
		workbook.write(fileOut);
		fileOut.close();

		
		
		return "saved";
	}

	@RequestMapping(value="/trackIgnoredItem/{foldName}/{rowCount}", method=RequestMethod.POST)
	@ResponseBody
	public String trackIgnoredItem(@PathVariable("foldName") String foldName,@PathVariable("rowCount") Integer rowCount  ) throws Exception{
		
		String excelFilePath = "C:/Vithu Dream/booking.com/Delivery/output.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
         
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(1);

        Row row = sheet.createRow (rowCount);
		row.createCell(0).setCellValue(foldName);
		row.createCell(1).setCellValue("ignored");
		
		FileOutputStream fileOut = new FileOutputStream(excelFilePath);
		workbook.write(fileOut);
		fileOut.close();

		return "saved";

	}
	
	@RequestMapping(value="/multiUrl", method=RequestMethod.POST)
	public void saveimagesFomMultiUrls() throws IOException{
		
		String excelFilePath = "C:/Vithu Dream/booking.com/URLs.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
         
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        
        String url1 = ""; int  i =0;
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
           
            String searchkeyword=""; String crawledURL="";
           
            
            	Cell cell = cellIterator.next();
        		 
        		url1 = cell.getStringCellValue();
        		Elements bookLnk = null;
        		try{
        			
        			Document docu = Jsoup.connect(url1).maxBodySize(0).get();
        			 bookLnk = docu.getElementsByTag("img");
             		
             		 try{
        				 Elements a = 	docu.getElementById("showMap2").getElementsByTag("span");
        				 //String hotelAddress1 = 	docu.getElementsByClass("hp_address_subtitle").get(0).getElementsByTag("span").get(1).text();;
        				 
        				 for(Element aa : a){
        					 
        					 if(aa.hasClass("hp_address_subtitle")){
        						 System.out.println(aa.text());
        						 nextRow.createCell(3).setCellValue(aa.text());
        					 }
        					 
        				 }
        				 
            			 /*System.out.println("HotelAddress: "+hotelAddress);
            			 FileWriter fw = new FileWriter(path +"/imgfolder"+ (i+1) +"/textfile.txt");
            			 BufferedWriter bw = new BufferedWriter(fw);
            			 bw.write(hotelAddress);
            			 bw.newLine();
            			 bw.write(hotelAddress1);
            			 bw.close();
            			 fw.close();*/
        			 }
        			 catch(Exception ex){
        				 System.out.println("No address info " + ex.getLocalizedMessage());
        			 }
             		
        		}
        		catch(Exception ex){
        			System.out.println(ex.getMessage());
        		}

        		
		

		 FileOutputStream fileOut = new FileOutputStream(excelFilePath);
	        workbook.write(fileOut);
	        fileOut.close();

        		
            i++;
        }

	}

	/**
	 * @param fileName
	 * @throws IOException
	 */
	@RequestMapping(value="/readExcelNew", method=RequestMethod.POST) 
	public void readExcelNew(@RequestBody HashMap<String,String> fileName ) throws IOException{
		
		    String path= "C:/Vithu Dream/booking.com/output/";
		 	String excelFilePath = "C:/Vithu Dream/booking.com/"+fileName.get("fileName");
	        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	         
	        Workbook workbook = new XSSFWorkbook(inputStream);
	        Sheet firstSheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = firstSheet.iterator();
	        int rowNum = 1, folderCounter=0; 
	        String itemId = ""; String address="",city="",zip="" ;
	        while (iterator.hasNext()) {
	        	
	        	
	            Row nextRow = iterator.next();
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	           
	            String searchkeyword=""; String rawAddress ="";
	            for(int i=0;i<=4;i++){
	            	
	            	if(i==0){
	            		Cell cell = cellIterator.next();

	            	switch (cell.getCellType()) {	
	            	case Cell.CELL_TYPE_STRING:

	            		itemId = cell.getStringCellValue();
	            		System.out.println(cell.getStringCellValue());
	            		
                    	break;
                    case Cell.CELL_TYPE_NUMERIC:
                    	
                    	Double a = cell.getNumericCellValue() ;
                    	String[] b = StringUtils.split(String.valueOf(a), ".");
                    	System.out.println(b[0]);
                    	itemId = b[0];
                    	
                        break;
	            	}

                    	
	            	}
	            	if(i> 0){
	            		
	            		Cell cell = cellIterator.next();

		            	switch (cell.getCellType()) {
	                    case Cell.CELL_TYPE_STRING:
	                    	searchkeyword = (searchkeyword.equals(""))? (cell.getStringCellValue()): (searchkeyword+ "+"+cell.getStringCellValue() );
	                    	rawAddress = (searchkeyword.equals(""))? (cell.getStringCellValue()): (searchkeyword+ " "+cell.getStringCellValue() );
	                    	
	                    	if(i==2){
	                    		address = cell.getStringCellValue();
	                    	}
	                    	if(i==4){
	                    		city = cell.getStringCellValue();
	                    	}
	                    	break;
	                    case Cell.CELL_TYPE_NUMERIC:
	                    	
	                    	Double a = cell.getNumericCellValue() ;
	                    	String[] b = StringUtils.split(String.valueOf(a), ".");
	                    	System.out.println(b[0]);
	                    	
	                    	searchkeyword = (String) ((searchkeyword.equals(""))? ( String.valueOf( b[0])): (searchkeyword+ "+"+  String.valueOf( b[0])));
	                    	rawAddress = (String) ((searchkeyword.equals(""))? ( String.valueOf( b[0])): (searchkeyword+ " "+  String.valueOf( b[0])));
	                    	
	                    	if(i==3){
	                    		zip = String.valueOf( b[0]);
	                    	} 
	                    	
	                        break;
		            	}
	            	}
	            }
	            
	            // Connect with Booking.com
	            
	            String google = "http://www.google.com/search?q=";
	    		String search = searchkeyword;
	    		String charset = "UTF-8";
	    		String userAgent = "Googlebot/2.1 (+http://www.google.com/bot.html)"; // Change this to your company's name and bot homepage!
	    		
	    		
	    		System.out.println("RowNum: "+ ++rowNum + searchkeyword);
	    		
	    		Elements links = null; 
	    		
	    		try {
	    			 links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent)
	    					 //.followRedirects(true)
	    					 .get().select(".g>.r>a");	
	    			 System.out.println(links.size());
	 	    		
	    			 nextRow.createCell(15).setCellValue(searchkeyword);
	    			 
	 	    		//Elements links = Jsoup.connect(google).get();
	    			int countOfBookingSite = 0 ;
	    			
	    			List addrLst = new ArrayList();
	    			
	 	    		for (Element link : links) {
	 	    		    String title = link.text();
	 	    		    String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
	 	    		    url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

	 	    		    if (!url.startsWith("http")) {
	 	    		        continue; // Ads/news/etc.
	 	    		    }
	 	    		    
	 	    		    
	 	    		    if(url.contains("booking.com") ){
	 	    		    	
	 	    		    	countOfBookingSite++;
	 	    		    	System.out.println("Title: " + title);
	 	    			    System.out.println("URL: " + url);
	 	    			
	 	    			   
	 	    			    
	 	    			   Document docu = Jsoup.connect(url).maxBodySize(0).get();
	 	        		   Elements imgLnks = docu.getElementsByTag("img");
	 	        		   String hotelAdd="";	 
	 	        		   try{
	         				 Elements hotelAddress = 	docu.getElementById("showMap2").getElementsByTag("span");
	         				 
	         				 String doesAddrMatch ="N",doesZipMatch="N",doesCitymatch="N";
	         				 for(Element aa :hotelAddress){
	         					 
								if (aa.hasClass("hp_address_subtitle")) {
									System.out.println(aa.text());
									hotelAdd = aa.text();
									nextRow.createCell(5).setCellValue(aa.text());

									System.out.println("onSite: "+hotelAdd);
									
									System.out.println("addressOnExcel "+address  );
									System.out.println("cityOnExcel "+city  );
									System.out.println("zipOnExcel "+ zip  );
									
									if (hotelAdd.contains(address)) {
										doesAddrMatch = "Y";
										System.out.println("address matches");
									}
									if (hotelAdd.contains(zip)) {
										doesZipMatch = "Y";
										System.out.println("zip matches");
									}
									if (hotelAdd.contains(city)) {
										doesCitymatch = "Y";
										System.out.println("city matches");
									}

									boolean alreadyCraweled=false;
								if(addrLst.size() > 0){
									if(!addrLst.contains(hotelAdd)){
										addrLst.add(hotelAdd);
										alreadyCraweled = false;
									}
									else{
										alreadyCraweled = true;
									}
								}
								else{
									addrLst.add(hotelAdd);
									alreadyCraweled = false;
								}
								
								
									if (alreadyCraweled == false && (doesAddrMatch.equals("Y") || (doesZipMatch.equals("Y")))) {

										int k = 1;
										for (Element el : imgLnks) {

											if (k > 40) {
												break;
											}

											if (el.absUrl("src").contains("/images/hotel/")) {

												String actualURl = el.absUrl("src");

												System.out.println(actualURl);

												String a = "/hotel/";
												String b1[] = actualURl.split(a);

												String c[] = b1[1].split("/");

												String Urls = "";
												if (c.length == 3) {
													Urls = b1[0] + a + "max1024x768" + "/" + c[1] + "/" + c[2];
												}
												if (c.length == 2) {
													Urls = b1[0] + a + "max1024x768" + "/" + c[1];
												}

												System.out.println(Urls);

												try{
													URL url1 = new URL(Urls);
													InputStream is = url1.openStream();
													File f = new File(path + "/" + itemId );
													f.mkdirs();

													OutputStream os = null;
													if (countOfBookingSite == 1) {
														os = new FileOutputStream(path + "/" + itemId 
																+ "/"+itemId+"_" + (k) + ".jpg");
													} else {
														File sf = new File(path + "/" + itemId 
																+ "/subfolder" + (countOfBookingSite));
														sf.mkdirs();
														os = new FileOutputStream(
																path + "/" + itemId + "/subfolder"
																		+ (countOfBookingSite) + "/"+ itemId+"_" + (k) + ".jpg");
													}
													
													
													byte[] b = new byte[2048];
													int length;

													while ((length = is.read(b)) != -1) {
														os.write(b, 0, length);
													}

													is.close();
													os.close();
													k++;
		
													
												}
												catch(Exception ex){
													System.out.println("Urls :"+Urls+" "+ ex.getLocalizedMessage());
												}
												
												
												
											}
										}

										nextRow.createCell(5 + countOfBookingSite).setCellValue(url);

										FileOutputStream fileOut = new FileOutputStream(excelFilePath);
										workbook.write(fileOut);
										fileOut.close();
										

										System.out.println("HotelAddress: " + aa.text());
										FileWriter fw = null;

										if (countOfBookingSite == 1) {

											fw = new FileWriter(
													path + "/" + itemId  + "/textfile.txt");
										} else {

											fw = new FileWriter(path + "/" + itemId  + "/subfolder"
													+ (countOfBookingSite) + "/textfile.txt");
										}

										BufferedWriter bw = new BufferedWriter(fw);
										bw.write(address+" "+zip+""+city);
										bw.newLine();
										bw.write(aa.text());
										bw.close();
										fw.close();

										break;

									}

								}
	         					 
	         				 }
	         				
	         				System.out.println("HotelAddress: "+ hotelAdd);
	 	    	            FileWriter fw = null;
	 	    	            
							if (countOfBookingSite == 1) {

								fw = new FileWriter(path + "/"+itemId  + "/textfile.txt");
							} else {

								fw = new FileWriter(path + "/"+itemId + "/subfolder"
										+ (countOfBookingSite) + "/textfile.txt");
							}

							BufferedWriter bw = new BufferedWriter(fw);
							bw.write(rawAddress);
							bw.newLine();
							bw.write(hotelAdd);
							bw.close();
							fw.close();
	             			   

	             			 
	         			 }
	         			 catch(Exception ex){
	         				 System.out.println(ex.getLocalizedMessage());
	         				 
	         			    
	         			 }
	 	    	            
	 	    		    }
	 	     
	 	    		}
	 	    		
	 	    		folderCounter++;
	 	    		nextRow.createCell(13).setCellValue(searchkeyword);
	    		}
	    	
	    		catch(HttpStatusException ex){
	    			System.out.println("FETCH error: " + ex.getMessage());
	    			
	    			
	    		}
	    		catch(Exception ex){
	    			System.out.println("exception: "+  ex.getMessage());
	    		}
	    	   System.out.println("***********************************************");
	            
	        }
	         
	        FileOutputStream fileOut = new FileOutputStream(excelFilePath);
            workbook.write(fileOut);
            fileOut.close();
	        
	        workbook.close();
	        inputStream.close();
		
	}

	Elements el=null;
	Document docu = null;
	@RequestMapping(value="/fetchImages/{cnt}", method=RequestMethod.GET)
	@ResponseBody
	public int fetchLimitedImages(@PathVariable("cnt") int cnt ) throws IOException{
		
		
		String path = "C:/Users/tamil/Desktop/vithuSampleProj/src/main/webapp/WEB-INF/img/output";
		String url ="https://www.booking.com/hotel/bg/green-life-beach-resort.en-gb.html";
		
		if(cnt==0){
			docu = Jsoup.connect(url).maxBodySize(0).get();
			el = docu.getElementsByTag("img");
		}
		
		   
		
		   String iurl = el.get(cnt).attr("src");
		   
		   URL url1 = new URL(iurl);
				InputStream is = url1.openStream();
				
				
				OutputStream os = null;
				
			    os = new FileOutputStream(path +"/image"+(cnt+1) + ".jpg");
				

				byte[] b = new byte[2048];
				int length;

				while ((length = is.read(b)) != -1) {
					os.write(b, 0, length);
				}

				
				is.close();
				os.close();
	
			return cnt;	
	}
			 
  			 	    		        
	@RequestMapping(value="/readExcel1", method=RequestMethod.GET) 
	public void readExcel1() throws IOException{
		
		
		
		 String excelFilePath = "C:/Vithu Dream/booking.com/sam.xlsx";
	        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	         
	        Workbook workbook = new XSSFWorkbook(inputStream);
	        Sheet firstSheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = firstSheet.iterator();
	        int rowNum = 1; int dum=0;
	        while (iterator.hasNext()) {
	        	
	            Row nextRow = iterator.next();
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	           
	            String searchkeyword=""; String crawledURL=""; String zip="",city="",address="" ;
	            for(int i=0;i<=4;i++){
	            	
	            	if(i==0){
	            		Cell cell = cellIterator.next();
	            	}
	            	if(i> 0){
	            		
	            		Cell cell = cellIterator.next();
	            		
	            		if(i==2){
	            			address = cell.getStringCellValue();
	            		}
	            		if(i==4){
	            			city = cell.getStringCellValue();
	            		}
	            		
		            	switch (cell.getCellType()) {
	                    case Cell.CELL_TYPE_STRING:
	                    	//searchkeyword = (searchkeyword.equals(""))? (cell.getStringCellValue().trim().replace(' ', '+')): (searchkeyword+ "+"+cell.getStringCellValue().trim().replace(' ', '+') );
	                    	
	                    	searchkeyword = (searchkeyword.equals(""))? (cell.getStringCellValue()): (searchkeyword+ "+"+cell.getStringCellValue() );
	                    	
	                        //System.out.print(cell.getStringCellValue().replace(' ', '+'));
	                        break;
	                    case Cell.CELL_TYPE_NUMERIC:
	                    	
	                    	Double a = cell.getNumericCellValue() ;
	                    	String[] b = StringUtils.split(String.valueOf(a), ".");
	                    	System.out.println(b[0]);
	                    	
	                    	//searchkeyword = (String) ((searchkeyword.equals(""))? (StringUtils.replace( String.valueOf( b[0]), " ", "+")): (searchkeyword+ "+"+ StringUtils.replace( String.valueOf( b[0]), " ", "+") ));
	                    	
	                    	searchkeyword = (String) ((searchkeyword.equals(""))? ( String.valueOf( b[0])): (searchkeyword+ "+"+  String.valueOf( b[0])));
	                    	zip = String.valueOf( b[0]);
	                        //System.out.print(String.valueOf( cell.getNumericCellValue()) );
	                        break;
		            	}

	            		
	            	}
	            		 

		            
	            }
	            
	            // Connect with Booking.com
	            
	            String google = "http://www.google.com/search?q=";
	    		String search = searchkeyword;
	    		String charset = "UTF-8";
	    		String userAgent = "Googlebot/2.1 (+http://www.google.com/bot.html)"; // Change this to your company's name and bot homepage!
	    		
	    		
	    		System.out.println("RowNum: "+ ++rowNum + searchkeyword);
	    		
	    		Elements links = null;
	    		
	    		try {
	    			 links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent)
	    					 //.followRedirects(true)
	    					 .get().select(".g>.r>a");	
	    			 System.out.println(links.size());
	 	    		 
	    			 nextRow.createCell(15).setCellValue(searchkeyword);
	    			 
	 	    		//Elements links = Jsoup.connect(google).get();
	    			 int countOfBookingSite = 0; int countOfTrivagoSite = 0; int countOfHotelsSite = 0;
	 	    		for (Element link : links) {
	 	    		    String title = link.text();
	 	    		    String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
	 	    		    url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

	 	    		    if (!url.startsWith("http")) {
	 	    		        continue; // Ads/news/etc.
	 	    		    }
	 	    		    
	 	    		    
	 	    		    if(url.contains("booking.com") ){
	 	    		    	
	 	    		    	countOfBookingSite++;
	 	    		    	System.out.println("Title: " + title);
	 	    			    System.out.println("URL: " + url);
	 	    			  

		 	        		   
		 	        		String hotelAdd="";	 
		 	        		   
		 	        		Document docu = Jsoup.connect(url).get();
		         			Elements hotelAddress = 	docu.getElementById("showMap2").getElementsByTag("span");
		         				 
		         			for(Element aa :hotelAddress){
		         					 
		         			   if(aa.hasClass("hp_address_subtitle")){
		         						 System.out.println(aa.text());
		         						hotelAdd = aa.text();
		         						 nextRow.createCell(5).setCellValue(aa.text());
		         						 
		         			   }
		         			}			 
	 	    			    
	 		    		    nextRow.createCell(17+countOfBookingSite).setCellValue(url); 
	 		    		    countOfBookingSite++;
	 		    		    nextRow.createCell(17+countOfBookingSite).setCellValue(hotelAdd);
	 		    		    countOfBookingSite++;
	 		    		   if(hotelAdd.contains(address)){
		 		    			  nextRow.createCell(17+countOfBookingSite).setCellValue("address exists");
		 		    		    }
	 		    		    
	 		    		   countOfBookingSite++;
	 		    		    if(hotelAdd.contains(zip)){
	 		    			  nextRow.createCell(17+countOfBookingSite).setCellValue("zip exists");
	 		    		    }
	 		    		    countOfBookingSite++;
	 		    		    if(hotelAdd.contains(city)){
	 		    			  nextRow.createCell(17+countOfBookingSite).setCellValue("city exists");
	 		    		    }
	 	    		    }
	 	    		    
	 	    		
	 	    		}
	 	    		nextRow.createCell(13).setCellValue(searchkeyword);
	    		}
	    	
	    		catch(HttpStatusException ex){
	    			System.out.println("FETCH error: " + ex.getMessage());
	    			
	    			 /*FileOutputStream fileOut = new FileOutputStream(excelFilePath);
	    	            workbook.write(fileOut);
	    	            fileOut.close();*/
	    	            break;
	    		}
	    		catch(Exception ex){
	    			System.out.println("exception: "+  ex.getMessage());
	    			/* FileOutputStream fileOut = new FileOutputStream(excelFilePath);
	    	            workbook.write(fileOut);
	    	            fileOut.close();*/
	    	          //  break;
	    			
	    		}
	    		
	            
	            System.out.println("***********************************************");
	        
	        }
	         
	        FileOutputStream fileOut = new FileOutputStream(excelFilePath);
            workbook.write(fileOut);
            fileOut.close();
	        
	        workbook.close();
	        inputStream.close();
		
	}	
	
}
