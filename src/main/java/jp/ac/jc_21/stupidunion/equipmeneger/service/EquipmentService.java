package jp.ac.jc_21.stupidunion.equipmeneger.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.jc_21.stupidunion.equipmeneger.bean.EquipmentBean;
import jp.ac.jc_21.stupidunion.equipmeneger.formdata.EquipmentFormData;
import jp.ac.jc_21.stupidunion.equipmeneger.repository.IEquipmentRepository;

@Service
public class EquipmentService implements IEquipmentService {
	@Autowired
	IEquipmentRepository repository;

	@Override
	public EquipmentFormData create(EquipmentFormData formData) {
		EquipmentBean bean = new EquipmentBean();
		BeanUtils.copyProperties(formData, bean);
		repository.save(bean);
		return formData;
	}
	@Override
	public EquipmentFormData update(EquipmentFormData formData) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<EquipmentFormData> findAll() {
		List<EquipmentBean> beanList = repository.findAll();
		List<EquipmentFormData> formDataList = new ArrayList<EquipmentFormData>();
		for (EquipmentBean bean : beanList) {
			EquipmentFormData formData = new EquipmentFormData();
			BeanUtils.copyProperties(bean, formData);
			formDataList.add(formData);
		}
		return formDataList;
	}
	@Override
	public EquipmentFormData findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}
