package jp.ac.jc_21.stupidunion.equipmeneger.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ac.jc_21.stupidunion.equipmeneger.formdata.EquipmentFormData;
import jp.ac.jc_21.stupidunion.equipmeneger.service.IEquipmentService;

@org.springframework.stereotype.Controller
@RequestMapping(EquipmentController.urlRoot)
public class EquipmentController {
	static final String urlRoot = "equipment";
	static final String urlList   = "list";
	static final String urlCreate = "create";
	static final String urlEdit   = "edit";
	static final String urlDelete = "delete";
	
	@Autowired
	IEquipmentService equipmentService;
	
	@ModelAttribute
	EquipmentFormData setUpForm() {
		return new EquipmentFormData();
	}

	// Pages
	@GetMapping
	String list(Model model) {
		model.addAttribute("equipments", equipmentService.findAll());
		return urlRoot +"/"+ urlList;
	}
	@PostMapping(path = urlEdit, params = "form")
	String editForm(@RequestParam Integer id, EquipmentFormData form) {
		EquipmentFormData findForm = equipmentService.findOne(id);
		BeanUtils.copyProperties(findForm, form);
		return urlRoot +"/"+ urlEdit;
	}
	@PostMapping(path = urlEdit, params = "goToTop")
	String goToTop() {
		return "redirect:/" + urlRoot;
	}
	
	// APIs
	@PostMapping(path = urlCreate)
	String create(@Validated EquipmentFormData form, BindingResult result, Model model) {
		if(result.hasErrors())
			return list(model);
		equipmentService.create(form);
		return "redirect:/" + urlRoot;
	}
	@PostMapping(path = urlEdit)
	String edit(@RequestParam Integer id, @Validated EquipmentFormData form, BindingResult result) {
		if (result.hasErrors())
			return editForm(id, form);
		equipmentService.update(form);
		return "redirect:/" + urlRoot;
	}
	@PostMapping(path = urlDelete)
	String delete(@RequestParam Integer id) {
		equipmentService.delete(id);
		return "redirect:/" + urlRoot;
	}

}
