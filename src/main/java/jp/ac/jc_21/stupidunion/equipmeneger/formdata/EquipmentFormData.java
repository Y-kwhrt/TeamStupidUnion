package jp.ac.jc_21.stupidunion.equipmeneger.formdata;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipmentFormData {
	// TODO add Validation
	private Integer id;          /* 管理番号 */
	private String type;         /* 品名 */
	private String model;        /* 型番 */
	private String manufacturer; /* メーカー */
	private String spec;         /* 仕様 */
	
	private String purchaceDate; /* 購入日 */
	private int    lifespanInYears; /* 耐用年数 */
}
