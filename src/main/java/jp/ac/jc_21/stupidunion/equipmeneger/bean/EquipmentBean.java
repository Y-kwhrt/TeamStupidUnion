package jp.ac.jc_21.stupidunion.equipmeneger.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "equipments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentBean {
	@Id
	@GeneratedValue
	private Integer id;          /* 管理番号 */
	@Column(nullable = false)
	private String type;         /* 品名 */
	private String model;        /* 型番 */ 
	private String manufacturer; /* メーカー */
	private String spec;         /* 仕様 */
	
	private String purchaceDate; /* 購入日 */
	
	private int     lifespanInYears;      /* 耐用年数 */
	private boolean isDepreciated;        /* 減価償却 */
	private boolean unusable;             /* 使用不能 */
	private boolean lendable;             /* 貸出可能 */
	private String  installationLocation; /* 設置場所 */
	private String  expiryDate;           /* 使用期限 */
}
