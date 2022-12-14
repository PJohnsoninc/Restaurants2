package business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import business.beans.Employee;
import business.repository.EmployeeRepository;

/**
 * @author peterjohnson - pmjohnson5
 * CIS175-Fall 2022
 * October 28, 2022
 */

@Controller
public class WebController {
	@Autowired
	EmployeeRepository repo;
	
	@GetMapping({"/", "viewAll"})
	public String viewAllEmployees(Model model) {
		if(repo.findAll().isEmpty()) {
			return addNewEmployee(model);
		}
		model.addAttribute("employees", repo.findAll());
		return "results";
	}
	
	@GetMapping("/inputEmployee")
	public String addNewEmployee(Model model) {
		Employee e = new Employee();
		model.addAttribute("newEmployee", e);
		return "input";
	}
	
	@GetMapping("/edit/{id}")
	public String showUpdateEmployee(@PathVariable("id") long id, Model model) {
		Employee e = repo.findById(id).orElse(null);
		model.addAttribute("newEmployee", e);
		return "input";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		Employee e = repo.findById(id).orElse(null);
		repo.delete(e);
		return viewAllEmployees(model);
	}
		

	@PostMapping("/inputEmployee")
	public String addNewEmployee(@ModelAttribute Employee e, Model model) {
		repo.save(e);
		return viewAllEmployees(model);
	}
	
	@PostMapping("/update/{id}")
	public String reviseEmployee(Employee e, Model model) {
		repo.save(e);
		return viewAllEmployees(model);
	}
	
}

