package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Cost extends Model {

	@Id
	public Long id;
	@Required // Siteのidと紐づける
	public Long site_id;
	// 単価
	public BigDecimal c_unit_price;
	// 数量
	public BigDecimal c_quantity;

}
