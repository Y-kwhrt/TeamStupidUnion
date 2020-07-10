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
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleBookBean {
  @Id
  @GeneratedValue
  private Integer id ;
  @Column(nullable = false)
  private String title;
  private String writter;
  private String publisher;
  private Integer price;
}
