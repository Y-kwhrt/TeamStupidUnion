package jp.ac.jc_21.stupidunion.equipmeneger.formdata;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipmentFormData {
	// TODO add Validation
	private Integer id; /* 管理番号 */
	private String type; /* 品名 */
	private String model; /* 型番 */
	private String manufacturer; /* メーカー */
	private String spec; /* 仕様 */

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date purchaceDate; /* 購入日 */
	private int lifespanInYears; /* 耐用年数 */

	private boolean depreciated /* 減価償却 */ = false;
	private boolean unusable /* 使用不能 */ = false;
	private boolean lendable /* 貸出可能 */ = false;
	private String installationLocation /* 設置場所 */ = "東京本社";
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiryDate /* 使用期限 */ = null;
}
