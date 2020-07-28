package jp.ac.jc_21.stupidunion.equipmeneger.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = EquipmentBean.tableName)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentBean {
	static final String tableName = "equipment";

	// TODO division table
	@Id
	@GeneratedValue
	private Integer id; /* 管理番号 */

	// 非Update対象
	@Column(nullable = false)
	private String type; /* 品名 */
	@Column(nullable = false)
	private String model; /* 型番 */
	@Column(nullable = false)
	private String manufacturer; /* メーカー */
	@Column(nullable = false)
	private String spec; /* 仕様 */

	@Column(nullable = false)
	private Date purchaceDate; /* 購入日 */
	@Column(nullable = false)
	private int lifespanInYears; /* 耐用年数 */

	// Update対象
	@Column(nullable = false)
	private boolean isDepreciated /* 減価償却 */;
	@Column(nullable = false)
	private boolean unusable /* 使用不能 */;
	@Column(nullable = false)
	private boolean lendable /* 貸出可能 */;
	@Column(nullable = false)
	private String installationLocation /* 設置場所 */;
	private Date expiryDate /* 使用期限 */;
}
