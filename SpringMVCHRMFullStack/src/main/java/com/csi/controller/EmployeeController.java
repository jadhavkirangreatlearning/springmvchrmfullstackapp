package com.csi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csi.model.Employee;
import com.csi.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeServiceImpl;

	@GetMapping("/list")
	public String listEmployee(Model model) {
		model.addAttribute("empList", employeeServiceImpl.findAll());

		return "listEmployee";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {

		Employee employee = new Employee();

		model.addAttribute("employee", employee);

		return "addEmployee";

	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(Model model, @RequestParam("empId") int empId) {

		Employee employee = employeeServiceImpl.findById(empId);

		model.addAttribute("employee", employee);

		return "addEmployee";
	}

	@PostMapping("/save")
	public String save(@RequestParam("empId") int empId, @RequestParam("empName") String empName,
			@RequestParam("empAddress") String empAddress, @RequestParam("empContactNumber") long empContactNumber,
			@RequestParam("empSalary") double empSalary) {

		Employee employee = new Employee();

		if (empId != 0) {
			employee = employeeServiceImpl.findById(empId);

			employee.setEmpId(empId);
			employee.setEmpName(empName);
			employee.setEmpAddress(empAddress);
			employee.setEmpContactNumber(empContactNumber);
			employee.setEmpSalary(empSalary);
		} else {
			employee = new Employee(empName, empAddress, empContactNumber, empSalary);
		}

		employeeServiceImpl.save(employee);
		return "redirect:/employees/list";
	}

	@GetMapping("/delete")
	public String deleteById(@RequestParam("empId") int empId) {

		employeeServiceImpl.deleteById(empId);

		return "redirect:/employees/list";
	}

	@GetMapping("/deleteall")
	public String deleteAll() {
		employeeServiceImpl.deleteAll();

		return "redirect:/employees/list";
	}

}
