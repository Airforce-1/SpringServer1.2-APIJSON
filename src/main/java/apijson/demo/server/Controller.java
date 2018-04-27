/*Copyright ©2016 TommyLemon(https://github.com/TommyLemon/APIJSON)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package apijson.demo.server;

import static zuo.biao.apijson.RequestMethod.DELETE;
import static zuo.biao.apijson.RequestMethod.GET;
import static zuo.biao.apijson.RequestMethod.GETS;
import static zuo.biao.apijson.RequestMethod.HEAD;
import static zuo.biao.apijson.RequestMethod.HEADS;
import static zuo.biao.apijson.RequestMethod.POST;
import static zuo.biao.apijson.RequestMethod.PUT;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;

import dyzb.server.model.BaseModel;
import dyzb.server.model.Comment;
import dyzb.server.model.Moment;
import dyzb.server.model.Privacy;
import dyzb.server.model.User;
import dyzb.server.model.Verify;
import zuo.biao.apijson.JSON;
import zuo.biao.apijson.JSONResponse;
import zuo.biao.apijson.Log;
import zuo.biao.apijson.RequestMethod;
import zuo.biao.apijson.StringUtil;
import zuo.biao.apijson.server.JSONRequest;
import zuo.biao.apijson.server.exception.ConditionErrorException;
import zuo.biao.apijson.server.exception.ConflictException;
import zuo.biao.apijson.server.exception.NotExistException;
import zuo.biao.apijson.server.exception.OutOfRangeException;


/**request controller
 * <br > 建议全通过HTTP POST来请求:
 * <br > 1.减少代码 - 客户端无需写HTTP GET,PUT等各种方式的请求代码
 * <br > 2.提高性能 - 无需URL encode和decode
 * <br > 3.调试方便 - 建议使用 APIJSON在线测试工具 或 Postman
 * @author Lemon
 */
@RestController
@RequestMapping("")
public class Controller {
	private static final String TAG = "Controller";

	//通用接口，非事务型操作 和 简单事务型操作 都可通过这些接口自动化实现<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	/**获取
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#GET}
	 */
	@PostMapping(value = "get")
	public String get(@RequestBody String request, HttpSession session) {
		return new DemoParser(GET).setSession(session).parse(request);
	}

	/**计数
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#HEAD}
	 */
	@PostMapping("head")
	public String head(@RequestBody String request, HttpSession session) {
		return new DemoParser(HEAD).setSession(session).parse(request);
	}

	/**限制性GET，request和response都非明文，浏览器看不到，用于对安全性要求高的GET请求
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#GETS}
	 */
	@PostMapping("gets")
	public String gets(@RequestBody String request, HttpSession session) {
		return new DemoParser(GETS).setSession(session).parse(request);
	}

	/**限制性HEAD，request和response都非明文，浏览器看不到，用于对安全性要求高的HEAD请求
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#HEADS}
	 */
	@PostMapping("heads")
	public String heads(@RequestBody String request, HttpSession session) {
		return new DemoParser(HEADS).setSession(session).parse(request);
	}

	/**新增
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#POST}
	 */
	@PostMapping("post")
	public String post(@RequestBody String request, HttpSession session) {
		DemoParser parser = new DemoParser(POST);
		parser.setSession(session);
		parser.setNoVerify(true);
		parser.setNoVerifyLogin(true);
		parser.setNoVerifyContent(true);
		return parser.parse(request);
	}

	/**修改
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#PUT}
	 */
	@PostMapping("put")
	public String put(@RequestBody String request, HttpSession session) {
		DemoParser parser = new DemoParser(PUT);
		parser.setSession(session);
		parser.setNoVerify(true);
		parser.setNoVerifyLogin(true);
		parser.setNoVerifyContent(true);
		return parser.parse(request);
	}

	/**删除
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#DELETE}
	 */
	@PostMapping("delete")
	public String delete(@RequestBody String request, HttpSession session) {
		DemoParser parser = new DemoParser(DELETE);
		parser.setSession(session);
		parser.setNoVerify(true);
		parser.setNoVerifyLogin(true);
		parser.setNoVerifyContent(true);
		return parser.parse(request);
	}





	/**获取
	 * 只为兼容HTTP GET请求，推荐用HTTP POST，可删除
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#GET}
	 */
	@RequestMapping("get/{request}")
	public String openGet(@PathVariable String request, HttpSession session) {
		try {
			request = URLDecoder.decode(request, StringUtil.UTF_8);
		} catch (Exception e) {
			// Parser会报错
		}
		return get(request, session);
	}

	/**计数
	 * 只为兼容HTTP GET请求，推荐用HTTP POST，可删除
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#HEAD}
	 */
	@RequestMapping("head/{request}")
	public String openHead(@PathVariable String request, HttpSession session) {
		try {
			request = URLDecoder.decode(request, StringUtil.UTF_8);
		} catch (Exception e) {
			// Parser会报错
		}
		return head(request, session);
	}
	
	
	@RequestMapping("post/{request}")
	public String openPost(@PathVariable String request, HttpSession session) {
		try {
			request = URLDecoder.decode(request, StringUtil.UTF_8);
		} catch (Exception e) {
			// Parser会报错
		}
		return post(request, session);
	}
	
	@RequestMapping("delete/{request}")
	public String openDelete(@PathVariable String request, HttpSession session) {
		try {
			request = URLDecoder.decode(request, StringUtil.UTF_8);
		} catch (Exception e) {
			// Parser会报错
		}
		return delete(request, session);
	}

	//以上为通用接口，非事务型操作 和 简单事务型操作 都可通过这些接口自动化实现>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	
	
	
	
	

	@RequestMapping("download")  
	public void download(HttpServletResponse res) throws IOException {  
	    OutputStream os = res.getOutputStream();  
	    try {  
	        res.reset();  
	        res.setHeader("Content-Disposition", "attachment; filename=dict.pptx");  
	        res.setContentType("application/octet-stream; charset=utf-8");  
	        os.write(FileUtils.readFileToByteArray(new File("D:/test.pptx")));  
	        os.flush();  
	    } finally {  
	        if (os != null) {  
	            os.close();  
	        }  
	    }  
	} 

	@RequestMapping("giveNews")  
	public String giveNews(HttpServletResponse res) throws IOException {  
	    OutputStream os = res.getOutputStream();  
	    try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  

            /* 读入TXT文件 */  
            String pathname = "D:/1.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径  
            File filename = new File(pathname); // 要读取以上路径的input。txt文件  
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line = "";  
            line = br.readLine();  
            while (line != null) {  
                line = br.readLine(); // 一次读入一行数据  
                System.out.println(line);
            } 
	        return line;
	    } catch (Exception e) {
			// Parser会报错
		} 
	    return "内容";
	}

	 @RequestMapping(value="/fileUpLoad")
	    public String testUpload(HttpServletRequest request,@RequestParam("photo") MultipartFile file) throws Exception{
		 CommonsMultipartFile a;
	        ServletContext servletContext = request.getServletContext();//获取ServletContext的对象 代表当前WEB应用
	        String realPath = servletContext.getRealPath("/uploads");//得到文件上传目的位置的真实路径
	        System.out.println("realPath :"+realPath);
	        File file1 = new File(realPath);
	        if(!file1.exists()){
	            file1.mkdir();   //如果该目录不存在，就创建此抽象路径名指定的目录。 
	        }
	        String prefix = UUID.randomUUID().toString();
	        prefix = prefix.replace("-","");
	        String fileName = prefix+"_"+file.getOriginalFilename();//使用UUID加前缀命名文件，防止名字重复被覆盖
	        
	        InputStream in= file.getInputStream();;//声明输入输出流
	        
	        OutputStream out=new FileOutputStream(new File(realPath+"\\"+fileName));//指定输出流的位置;
	        
	        byte []buffer =new byte[1024];
	        int len=0;
	        while((len=in.read(buffer))!=-1){
	            out.write(buffer, 0, len);
	            out.flush();                //类似于文件复制，将文件存储到输入流，再通过输出流写入到上传位置
	        }                               //这段代码也可以用IOUtils.copy(in, out)工具类的copy方法完成
	                                        
	        out.close();
	        in.close();
	    
	        return "success";
	    }

	 @RequestMapping(value="/uploadMD")
	    public String mdUpload(HttpServletRequest request,@RequestParam("markdown") String newsContent) throws Exception{
		 CommonsMultipartFile a;
	        ServletContext servletContext = request.getServletContext();//获取ServletContext的对象 代表当前WEB应用
	        String realPath = servletContext.getRealPath("/uploads");//得到文件上传目的位置的真实路径
	        System.out.println("realPath :"+realPath);
	        File file1 = new File(realPath);
	        if(!file1.exists()){
	            file1.mkdir();   //如果该目录不存在，就创建此抽象路径名指定的目录。 
	        }
	        String prefix = UUID.randomUUID().toString();
	        prefix = prefix.replace("-","");
	        String fileName = prefix+"_"+"news.txt";//使用UUID加前缀命名文件，防止名字重复被覆盖
	        
	        FileWriter fileWriter = new FileWriter(realPath+"\\"+fileName);
	        fileWriter.write(newsContent);
            fileWriter.flush();
            fileWriter.close();
	    
	        return "success";
	    }
	
	


	public static final String USER_;
	public static final String PRIVACY_;
	public static final String MOMENT_;
	public static final String COMMENT_;
	public static final String VERIFY_; //加下划线后缀是为了避免 Verify 和 verify 都叫VERIFY，分不清
	static {
		USER_ = User.class.getSimpleName();
		PRIVACY_ = Privacy.class.getSimpleName();
		MOMENT_ = Moment.class.getSimpleName();
		COMMENT_ = Comment.class.getSimpleName();
		VERIFY_ = Verify.class.getSimpleName();
	}

	public static final String VERSION = JSONRequest.KEY_VERSION;
	public static final String COUNT = JSONResponse.KEY_COUNT;
	public static final String TOTAL = JSONResponse.KEY_TOTAL;

	public static final String RANGE = "range";

	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String CURRENT_USER_ID = "currentUserId";

	public static final String NAME = "name";
	public static final String PHONE = "phone";
	public static final String PASSWORD = "password";
	public static final String _PASSWORD = "_password";
	public static final String _PAY_PASSWORD = "_payPassword";
	public static final String OLD_PASSWORD = "oldPassword";
	public static final String VERIFY = "verify";

	public static final String SEX = "sex";
	public static final String TYPE = "type";
	public static final String WAY = "way";
	public static final String CONTENT = "content";
	








}
