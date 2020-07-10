package jp.ac.jc_21.stupidunion.equipmeneger.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ac.jc_21.stupidunion.equipmeneger.formdata.SampleBookFormData;
import jp.ac.jc_21.stupidunion.equipmeneger.service.SampleBookService;

@org.springframework.stereotype.Controller
@RequestMapping("books")
public class SampleBookController {
	@Autowired
	SampleBookService bookService;

	@ModelAttribute
	SampleBookFormData setUpForm() {
		return new SampleBookFormData();
	}

	@GetMapping
	String list(Model model) {
		model.addAttribute("books", bookService.findAll());
		return "books/list";
	}

	@PostMapping(path = "create")
	String create(SampleBookFormData form, Model model) {
		bookService.create(form);
		return "redirect:/books";
	}

	@PostMapping(path = "edit", params = "form")
	String editForm(@RequestParam Integer id, SampleBookFormData form) {
		SampleBookFormData bookForm = bookService.findOne(id);
		BeanUtils.copyProperties(bookForm, form);
		return "books/edit";
	}

	@PostMapping(path = "edit")
	String edit(@RequestParam Integer id, SampleBookFormData form) {
		bookService.update(form);
		return "redirect:/books";
	}

	@PostMapping(path = "delete")
	String delete(@RequestParam Integer id) {
		bookService.delete(id);
		return "redirect:/books";
	}

	@PostMapping(path = "edit", params = "goToTop")
	String goToTop() {
		return "redirect:/books";
	}
}
