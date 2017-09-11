package com.putaoteng.task8.controller;

import com.putaoteng.task8.model.BasicVo;
import com.putaoteng.task8.model.Student;
import com.putaoteng.task8.server.StudentDaoServiceRemote;
import com.putaoteng.task8.utils.other.Storage;
import com.putaoteng.task8.utils.other.Transfer;
import com.putaoteng.task8.utils.remote.CallService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

@Controller
public class StudentController {
	private static final Logger logger = Logger.getLogger(StudentController.class);
	private CallService callService = new CallService();
	private StudentDaoServiceRemote studentDaoServiceRemote;
	@Resource(name="transfer")
	private Transfer transfer;
	@Resource(name = "storage")
	private Storage storage;

	// 主页
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	// 录入信息页
	@RequestMapping(value = "/u/inputdata", method = RequestMethod.GET)
	public String inputData() {
		return "addStudent";
	}

	// 操作页面
	@RequestMapping(value = "/u/operations", method = RequestMethod.GET)
	public String operate(HttpServletRequest request) {
		if (transfer.getToOther().equalsIgnoreCase("ali-to-qiniu")){
			request.getSession().setAttribute("info", "目前的状态是:阿里云迁移到七牛云");
			request.getSession().setMaxInactiveInterval(1);
		} else if (transfer.getToOther().equalsIgnoreCase("qiniu-to-ali")){
			request.getSession().setAttribute("info", "目前的状态是:七牛迁移到阿里云");
			request.getSession().setMaxInactiveInterval(1);
		}

		return "operations";
	}

	// 成功页面
	@RequestMapping(value = "/u/success", method = RequestMethod.GET)
	public String success() {
		return "success";
	}

	// 失败页面
	@RequestMapping(value = "/u/failed", method = RequestMethod.GET)
	public String failed() {
		return "failed";
	}

	// 编辑信息页
	@RequestMapping(value = "/u/editdata", method = RequestMethod.POST)
	public String editData(Model model, Long id) {
	/*	try{
			this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		} catch(Exception  e){
			callService.getBeanAgain();
			this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		}*/

		callService.getBeanAgain();
		this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		Student student = studentDaoServiceRemote.findByPK(id);

		model.addAttribute("student", student);
		return "editStudent";
	}

	// 所有学生列表
	@RequestMapping(value = "/u/list", method = RequestMethod.GET)
	public String findAll(Model model) {
	/*	try{
			this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
			logger.error("----------------------------------------------------------------------try");
		} catch(Exception  e){
			logger.error("----------------------------------------------------------------------catch");
			callService.getBeanAgain();
			this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		}*/
		callService.getBeanAgain();
		this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		List<BasicVo> list = studentDaoServiceRemote.findAll();

		model.addAttribute("studentList", list);
		return "studentList";
	}

	// 按姓名查找列表
	@RequestMapping(value = "/u/student", method = RequestMethod.GET)
	public String findByName(Model model, String name) {
	/*	try{
			this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		} catch(Exception  e){
			callService.getBeanAgain();
			this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		}*/
		callService.getBeanAgain();
		this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		List<BasicVo> list = studentDaoServiceRemote.findByStudentName(name);

		if (list.isEmpty()) {
			return "failed";
		}

		model.addAttribute("studentList", list);
		return "student";
	}

	// 新增学员
	@RequestMapping(value = "/u/student", method = RequestMethod.POST)
	public String save(Model model, Student student) {
		student.setCreateAt(System.currentTimeMillis());
		student.setUpdateAt(System.currentTimeMillis());

	/*	try{
			this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		} catch(Exception  e){
			callService.getBeanAgain();
			this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		}*/
		callService.getBeanAgain();
		this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		int result = studentDaoServiceRemote.save(student);

		return result == 0 ? "failed" : "success";
	}

	// 更新学员数据
	@RequestMapping(value = "/u/student", method = RequestMethod.PUT)
	public String update(Model model, Student student) {
		student.setUpdateAt(System.currentTimeMillis());
		/*try{
			this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		} catch(Exception  e){
			callService.getBeanAgain();
			this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		}*/
		callService.getBeanAgain();
		this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		int result = studentDaoServiceRemote.update(student);
		return result == 0 ? "failed" : "success";
	}

	// 删除学员数据
	@RequestMapping(value = "/u/student", method = RequestMethod.DELETE)
	public String delete(HttpServletRequest request, Long id) {
	/*	try{
			this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		} catch(Exception  e){
			callService.getBeanAgain();
			this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		}*/
		callService.getBeanAgain();
		this.studentDaoServiceRemote = callService.getStudentDaoServiceRemote();
		//获取空间中的文件名
		String fileName = studentDaoServiceRemote.findByPK(id).getImage();
		String[] strArray = fileName.split("/");
		fileName = strArray[strArray.length-3] + "/" + strArray[strArray.length-2] + "/" + strArray[strArray.length-1];

		int result = studentDaoServiceRemote.deleteByPK(id);
		//删除数据的同时也删除云存储空间中的文件
		if (result != 0 && storage.deleteFile(fileName)){
			return "success";
		}
		return "failed";
	}
}
