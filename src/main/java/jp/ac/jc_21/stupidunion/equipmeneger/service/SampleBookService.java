package jp.ac.jc_21.stupidunion.equipmeneger.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.jc_21.stupidunion.equipmeneger.bean.SampleBookBean;
import jp.ac.jc_21.stupidunion.equipmeneger.formdata.SampleBookFormData;
import jp.ac.jc_21.stupidunion.equipmeneger.repository.SampleBookRepository;

@Service
public class SampleBookService {
	@Autowired
	SampleBookRepository bookRepository;

	public SampleBookFormData create(SampleBookFormData bookForm) {
		SampleBookBean bookBean = new SampleBookBean();
		BeanUtils.copyProperties(bookForm, bookBean);
		bookRepository.save(bookBean);
		return bookForm;
	}

	public SampleBookFormData update(SampleBookFormData bookForm) {
		SampleBookBean bookBean = new SampleBookBean();
		BeanUtils.copyProperties(bookForm, bookBean);
		bookRepository.save(bookBean);
		return bookForm;
	}

	public void delete(Integer id) {
		bookRepository.deleteById(id);
	}

	public List<SampleBookFormData> findAll() {
		List<SampleBookBean> beanList = bookRepository.findAll();
		List<SampleBookFormData> formList = new ArrayList<SampleBookFormData>();
		for (SampleBookBean bookBean : beanList) {
			SampleBookFormData bookForm = new SampleBookFormData();
			BeanUtils.copyProperties(bookBean, bookForm);
			formList.add(bookForm);
		}
		return formList;
	}

	public SampleBookFormData findOne(Integer id) {
		SampleBookFormData bookForm = new SampleBookFormData();
		bookRepository
			.findById(id)
			.ifPresent(bookBean -> BeanUtils.copyProperties(bookBean, bookForm));
		return bookForm;
	}
}
