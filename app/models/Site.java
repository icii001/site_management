package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Site extends Model {

	@Id
	public Long id;
	@Required
	public String siteName;
	public String address;
	@MaxLength(12)
	public String tel;
	public String remarks;

    public Site(Long id,String siteName, String address,
    		String tel,String remarks) {
    	this.id = id;
        this.siteName = siteName;
        this.address = address;
        this.tel = tel;
        this.remarks = remarks;
    }

}
