package jp.ac.jc_21.stupidunion.equipmeneger.formdata;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipmentFormData {
	private Integer id; /* 管理番号 */
	@NotNull
	private String type; /* 品名 */
	@NotBlank
	private String model; /* 型番 */
	@NotBlank
	private String manufacturer; /* メーカー */
	@NotNull
	private String spec; /* 仕様 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private Date purchaceDate; /* 購入日 */
	@NotNull
	@Min(1)
	private int lifespanInYears; /* 耐用年数 */

	private boolean depreciated /* 減価償却 */ = false;
	private boolean unusable /* 使用不能 */ = false;
	private boolean lendable /* 貸出可能 */ = false;
	private String installationLocation /* 設置場所 */ = "東京本社";
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiryDate /* 使用期限 */ = null;
}
