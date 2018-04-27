package apijson.oss.download.upload;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DownloadController {  
    @RequestMapping(value="/zyg/download/lemmainfo")  
    public void downloadResource(@RequestParam(value = "fileName", required = true) String fileName, HttpServletResponse response) {  
        String dataDirectory = "/data/denglinjie/everydayLemmaInfo/";  
        Path file = Paths.get(dataDirectory, fileName);  
        if (Files.exists(file)) {  
            response.setContentType("application/x-gzip");  
            try {  
                response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));  
                Files.copy(file, response.getOutputStream());  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
    }  
} 