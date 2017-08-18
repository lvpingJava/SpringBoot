package com.example.springBoot.controller;

import com.example.springBoot.bean.FileBean;
import com.example.springBoot.bean.Gps;
import com.example.springBoot.bean.Person;
import com.example.springBoot.util.ExcelExportHelper;
import com.example.springBoot.util.ExcelReadHelper;
import com.example.springBoot.util.PositionUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lvping on 2017/8/17.
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String index() {
        return "Hello World123";
    }

    @RequestMapping("/index")
    public String index(Model model){
        Person single=new Person("aa",1);
        List<Person> people=new ArrayList<Person>();
        Person p1=new Person("bb",2);
        Person p2=new Person("cc",3);
        Person p3=new Person("dd",4);
        people.add(p1);
        people.add(p2);
        people.add(p3);
        model.addAttribute("singlePerson",single);
        model.addAttribute("people",people);
        return "/index";
    }

    @RequestMapping(value = "/fileUplaod", method = RequestMethod.POST)
    public void fileUplaod(HttpServletRequest req, MultipartFile file) {
            try {
                //获取文件名
                String fileName=file.getOriginalFilename();

                //这个myfile是MultipartFile的
                String[] strs={"lat","lng"};
                List<Object> list= ExcelReadHelper.excelRead(file,strs, FileBean.class);
                List<Object> list2=new ArrayList<Object>();
                list.forEach(fileBean->{
                    Gps gcj2 = PositionUtil.bd09_To_Gcj02(((FileBean)fileBean).getLat(), ((FileBean)fileBean).getLng());
                    FileBean fileBean1=new FileBean();
                    fileBean1.setLat(gcj2.getWgLat());
                    fileBean1.setLng(gcj2.getWgLon());
                    list2.add(fileBean1);
                });

                ExcelExportHelper ex=new ExcelExportHelper();
                HSSFWorkbook workbook=ex.exportExcel(strs,list2,"abcdedfgd");

                OutputStream out = new FileOutputStream("D:\\fileupload\\"+fileName+new Date().getTime()+".xls");
                workbook.write(out);


                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
               /* BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();*/
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

}
