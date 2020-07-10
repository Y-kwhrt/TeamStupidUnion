package jp.ac.jc_21.stupidunion.equipmeneger.formdata;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SampleBookFormData {
	private Integer id;
	private String title;
	private String writter;
	private String publisher;
	private Integer price;
}
