package jp.ac.jc_21.stupidunion.equipmeneger.service;

import java.util.List;

import jp.ac.jc_21.stupidunion.equipmeneger.formdata.EquipmentFormData;

public interface IEquipmentService {
	public EquipmentFormData create(EquipmentFormData formData);

	public EquipmentFormData update(EquipmentFormData formData);

	public void delete(Integer id);

	public List<EquipmentFormData> findAll();

	public EquipmentFormData findOne(Integer id);
}
