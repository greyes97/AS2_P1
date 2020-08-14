package com.parcial1.controller;

import com.parcial1.model.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Controlador {
    public static int idCourse;
    public static String nameCourse;

@GetMapping("/")
public String controllerIndex(Model model,String error){

    //ERRORES DE INDEX
    if(error==null){
        model.addAttribute("cursoActivity","start");
        model.addAttribute("cursoQualification","start");
        model.addAttribute("cursoDeleteActivity","start");
    }else if(error.equals("errorActivity")){
        model.addAttribute("cursoActivity",error);
    }else if(error.equals("errorQualification")){
        model.addAttribute("cursoQualification",error);
    }else if(error.equals("errorDeleteAc")){
        model.addAttribute("cursoDeleteActivity",error);
    }
    return "index";
}

@PostMapping("/activity")
    public String insertActivity(HttpServletRequest request, Model model){
    ActivityEntity activity = new ActivityEntity();
    IActivityDao dao = new ActivityDao();
    ICouseDao course = new CourseDao();

    if(request.getParameter("codigo").equals("buscar")){
        CourseEntity courseEntity = new CourseEntity();
        try{
            courseEntity = course.getCourseDao(request.getParameter("txt_courseName"));
            System.out.println("HOla" +courseEntity);

            if(courseEntity != null){
                model.addAttribute("name", courseEntity.getName());
                model.addAttribute("desc", courseEntity.getDescription());
                model.addAttribute("id", courseEntity.getId_course());
                idCourse = courseEntity.getId_course();
                return "activity";
            } else{
                model.addAttribute("cursoError","error");
                return controllerIndex(model,"errorActivity");
            }
        } catch (Exception ex){
            System.out.println(ex);
            return "error";
        }
    }
    return "activity";
}
@PostMapping("/exito")
    public String controllerInsert(HttpServletRequest request,Model model){
    IActivityDao dao = new ActivityDao();
    IAddQualificationDao addDao = new AddQualificationDao();
    ActivityEntity activity = new ActivityEntity();
    if(request.getParameter("codigo").equals("insert")) {
        activity.setName(request.getParameter("txt_name"));
        activity.setDescription(request.getParameter("txt_desc"));
        activity.setQualification(Float.parseFloat(request.getParameter("txt_cal")));
        activity.setId_course(idCourse);
        dao.saveActivityDao(activity);
        return controllerDelete(request,model);
    } else if(request.getParameter("codigo").equals("addCualification")) {

        ResultEntity result = new ResultEntity();
       if(request.getParameter("slec_students").equals("0")||request.getParameter("slec_activity").equals("0")){
           return controllerAddCualification(request,model,"errorSelect");
       }else {
           addDao.validationAdd(Integer.parseInt(request.getParameter("slec_activity")));
           if(Float.parseFloat(request.getParameter("txt_cal"))<=addDao.validationAdd(Integer.parseInt(request.getParameter("slec_activity")))){
               result.setId_student(Integer.parseInt(request.getParameter("slec_students")));
               result.setId_activity(Integer.parseInt(request.getParameter("slec_activity")));
               result.setResult(Float.parseFloat(request.getParameter("txt_cal")));
               if(addDao.saveResult(result)){
                   nameCourse = request.getParameter("txt_delete");
                   System.out.println(request.getParameter("txt_delete"));
                   return controllerAddCualification(request,model,"exito");
               }else {
                   return "error";
               }
           }else {
               return controllerAddCualification(request,model,"errorValidation");
           }
       }
    }else{
        return "error";
    }
}

@PostMapping("/delete")
    public String controllerDelete(HttpServletRequest request,Model model){
    ICouseDao couseDao = new CourseDao();
    nameCourse = request.getParameter("txt_delete");
        try{
            List<ActivityEntity> lista = couseDao.getListActividadCourse(nameCourse);
            if(lista!=null){
                model.addAttribute("lista",lista);
                model.addAttribute("nameCourse",nameCourse);
                return "delete";
            }else{
                return controllerIndex(model,"errorDeleteAc");
            }
        }
        catch (Exception ex){
            return "error";
        }
}
@PostMapping("/deleteEx")
    public String controllerDele(HttpServletRequest request, Model model){
    IActivityDao dao = new ActivityDao();
    ICouseDao couseDao = new CourseDao();

        try {
            dao.deleteActivityDao(Integer.parseInt(request.getParameter("txt_eliminar")));
            model.addAttribute("lista",couseDao.getListActividadCourse(nameCourse));
            model.addAttribute("nameCourse",nameCourse);
            return "delete";
        }catch (Exception ex){
            return "error";
        }

}
@PostMapping("/AddCualification")
    public String controllerAddCualification(HttpServletRequest request,Model model,String whatError){
    IAddQualificationDao add = new AddQualificationDao();
    nameCourse = request.getParameter("txt_delete");
    System.out.println(whatError);
    try{
        List<StudentEntity> lista = add.getListStudent(nameCourse);
        if(lista!=null){
            model.addAttribute("lista",lista);
            model.addAttribute("nameCourse",nameCourse);
            if(whatError == null){
                model.addAttribute("addCurso","start");
            }else {
                model.addAttribute("addCurso",whatError);
            }
            model.addAttribute("activity", add.getListActivity(nameCourse));
            return "score";
        }else{
            return controllerIndex(model,"errorQualification");
        }
    }
    catch (Exception ex){
        return "error";
    }
}

@PostMapping("/error")
    public String controllerError(String whaterror){
    return whaterror;
}

@GetMapping("/descarga")
    public ResponseEntity<Object> downloadFile(HttpServletRequest request) throws IOException
    //ARCHIVO DE DESCARGA
    {

        IActivityDao acta = new ActivityDao();
        IFileControl csv = new CsvFile();

        csv.createFile(acta.getActa(Integer.parseInt(request.getParameter("txt_id"))),request.getParameter("txt_nameCourse"));

        File file = new File("acta_"+request.getParameter("txt_nameCourse")+"_"+request.getParameter("txt_id")+".csv");

        return getObjectResponseEntity(file);


    }

    private ResponseEntity<Object> getObjectResponseEntity(File file) throws FileNotFoundException {
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }

}

