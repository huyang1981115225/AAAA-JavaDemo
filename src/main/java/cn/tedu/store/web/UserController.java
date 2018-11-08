package cn.tedu.store.web;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.bean.ResponseResult;
import cn.tedu.store.bean.User;
import cn.tedu.store.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;
	
	/**
	 * 跳转到注册页面
	 * @return
	 */
	@RequestMapping("/showRegister.do")
	public String showRegister(){
		return "register";
	}
	
	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	@RequestMapping("/register.do")
	@ResponseBody
	public ResponseResult<Void> register(User user){
		ResponseResult<Void> rr = new ResponseResult<Void>();
		try {
			userService.register(user);
			rr.setState(1);
			rr.setMessage("注册成功");
		} catch (Exception e) {
			rr.setState(0);
			rr.setMessage("注册失败");
		}
		return rr;
	}
	
	/**
	 * 显示用户列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list.do")
	public String userList(Model model){
		List<User> users = userService.findAll(); 
		model.addAttribute("users", users);
		return "user_list";
	}
	
	@RequestMapping("userEdit.do")
	public String userEdit(Integer id,Model model){
		User user = userService.findUserById(id);
		model.addAttribute("user", user);
		return "user_edit";
	}
	
	@RequestMapping("updateUser.do")
	@ResponseBody
	public ResponseResult<Void> updateUser(User user){
		ResponseResult<Void> rr = new ResponseResult<Void>();;
		try {
			userService.updateUser(user);
			rr.setState(1);
			rr.setMessage("修改成功");
		} catch (Exception e) {
			rr.setState(0);
			rr.setMessage("修改失败");
		}
		return rr;
	}
	
	/**
	 * 跳转到上传到图片页面
	 * 
	 * @return
	 */
	@RequestMapping("/testUploadImg.do")
	public String uploadImg() {
		return "upload_img";
	}
	
	/**
	 * 测试上传图片
	 * 
	 * @param file
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/uploadImg.do")
	public String goUpload(MultipartFile file, HttpSession session) throws IllegalStateException, IOException {
		// 获取上传文件的名字
		String fileName = file.getOriginalFilename();

		// 获取服务器的真实路径
		String contextPath = session.getServletContext().getRealPath("/upload");
		String path = contextPath + "/" + file.getOriginalFilename();
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		// 上传文件
		file.transferTo(f);
		return "uploadImg_success";
	}
	
	/**
	 * 跳转到上传到图片页面
	 * 
	 * @return
	 */
	@RequestMapping("/testUploadImg2.do")
	public String uploadImg2() {
		System.out.println("进入img2...");
		return "upload_img2";
	}
	
	/**
	 * 跳转到上传图片页面(支持多选)
	 * @return
	 */
	@RequestMapping("/testUploadImg3.do")
	public String uploadImg3() {
		System.out.println("进入img3...");
		return "upload_img3";
	}
	
	@RequestMapping("/uploadImg3.do")
	public String goUpload3(@RequestParam("files") MultipartFile[] files, HttpSession session)
			throws IllegalStateException, IOException {
		for (MultipartFile file : files) {
			// 获取上传文件的名字
			String fileName = file.getOriginalFilename();
			// 获取服务器的真实路径
			String contextPath = session.getServletContext().getRealPath("/upload");
			String path = contextPath + "/" + file.getOriginalFilename();		
			 File f = new File(path);
			 // 上传文件
				if (!f.exists()) {
					f.mkdirs();
				}
			 file.transferTo(f);
		}
		return "uploadImg_success";
	}
	
	/**
	 * 跳转到上传表格导入数据页面
	 * 
	 * @return
	 */
	@RequestMapping("/testUploadExcel.do")
	public String uploadExcel() {
		return "upload_excel";
	}
	
	/**
	 * 测试上传Excel导入数据
	 * @param excelFile
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/uploadExcel.do")
	public String goUploadExcel(MultipartFile excelFile) throws IOException{
		System.out.println("上传excel文件的名字为："+excelFile.getOriginalFilename());
		// 工作簿: 就是一个Excel文件对象
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
		XSSFSheet sheet = workbook.getSheetAt(0);
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			XSSFRow row = sheet.getRow(rowNum);
			User user = new User();
			XSSFCell name = row.getCell(0);
			user.setName(name.toString());
			
			XSSFCell password = row.getCell(1);
			user.setPassword(password.toString());
			
			XSSFCell sex = row.getCell(2);
			user.setSex(sex.toString());
			
			XSSFCell phoneNum = row.getCell(3);
			user.setPhoneNum(phoneNum.toString());
			
			userService.register(user);
		}
		return "uploadExcel_success";
	}
	
	/**
	 * 下载Excel
	 */
	@RequestMapping("/excel.do")
	@ResponseBody
	public byte[] excel(HttpServletResponse response) throws Exception {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		String filename = URLEncoder.encode("表格.xlsx", "utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		byte[] body = getExcel();
		return body;
	}

	/**
	 * 生成Excel文件
	 */
	public byte[] getExcel() throws IOException {
		// 工作簿: 就是一个Excel文件对象
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 在工作簿创建一个 Hello 工作表
		XSSFSheet sheet = workbook.createSheet("Hello");
		// 在工作表中添加一行: 参数是行编号 0 1 2
		// 参数相同的话, 会覆盖以前的行
		XSSFRow row = sheet.createRow(0);
		// 在行中可以添加很多格子,参数是格子的位置序号
		XSSFCell cell = row.createCell(0);
		// 在格子中添加数据
		cell.setCellValue("Hello World!");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		workbook.close();
		out.close();
		byte[] bytes = out.toByteArray();
		return bytes;
	}

	/**
	 * 下载图片
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/img.do", produces = "image/png")
	@ResponseBody
	public byte[] demo(HttpServletResponse response) throws Exception {
		// response.setContentType("image/png");
		String filename = URLEncoder.encode("图片.png", "utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		// 获取png图片的数据
		byte[] bytes = getImage();
		return bytes;
	}

	/**
	 * 生成图片
	 * 
	 * @return
	 * @throws IOException
	 */
	private byte[] getImage() throws IOException {
		BufferedImage img = new BufferedImage(100, 30, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = img.createGraphics();
		g.setColor(Color.white);
		g.drawString("Hello World", 3, 28);
		// "酒瓶"用于装byte数据
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// 将png图片的数据(byte)倒入"酒瓶out"
		ImageIO.write(img, "png", out);
		// 为"酒瓶"盖上盖子
		out.close();
		// 从"酒瓶"中倒出byte数据
		byte[] bytes = out.toByteArray();
		return bytes;
	}

	/**
	 * 定时任务测试
	 * 
	 * @return
	 */
	@RequestMapping("/testTimePlan1.do")
	public String testTimePlan1() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				User user = new User();
				user.setName("User" + (int) (Math.random() * 10));
				user.setPassword(DigestUtils.md5Hex("123456"));
				user.setSex("男");
				user.setPhoneNum("18764567876");
				userService.register(user);
			}
		}, 5000, 3000);
		return "timer1_success";
	}

	/**
	 * 定时任务(类似于闹钟)
	 */
	@RequestMapping("/testTimePlan2.do")
	public String testTimePlan2() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 16);
		calendar.set(Calendar.MINUTE, 41);
		calendar.set(Calendar.SECOND, 0);

		Date time = calendar.getTime();

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				User user = new User();
				user.setName("拉布拉多");
				user.setPassword(DigestUtils.md5Hex("654321"));
				user.setSex("男");
				user.setPhoneNum("18764567876");
				userService.register(user);
				System.err.println("16:41任务触发！");
			}
		}, time, 1000 * 60 * 60 * 24);
		return "timer2_success";
	}

	/**
	 * 测试导出数据
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/testImport.do")
	public byte[] testImport() throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("用户表");
		int index = 0;
		XSSFRow row = sheet.createRow(index++);
		String[] titles = { "用户名", "密码", "性别", "手机号" };

		for (int i = 0; i < titles.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(titles[i]);
		}

		List<User> users = userService.findAll();
		for (User user : users) {
			row = sheet.createRow(index++);

			XSSFCell cell = row.createCell(0);
			cell.setCellValue(user.getName());

			cell = row.createCell(1);
			cell.setCellValue(user.getPassword());

			cell = row.createCell(2);
			cell.setCellValue(user.getSex());

			cell = row.createCell(3);
			cell.setCellValue(user.getPhoneNum());

		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		workbook.close();
		out.close();
		byte[] bytes = out.toByteArray();
		return bytes;
	}

	/**
	 * 下载导出的Excel
	 */
	@RequestMapping("/downExcel.do")
	@ResponseBody
	public byte[] downExcel(HttpServletResponse response) throws Exception {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		String filename = URLEncoder.encode("用户表.xlsx", "utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		byte[] body = testImport();
		return body;
	}
}
