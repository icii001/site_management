package forms;

import javax.persistence.Id;

import play.data.validation.Constraints.Required;

public class tempSite {
	@Id
	public Long id;
	@Required
	public String siteName;
	public String address;
	public String tel;
	public String remarks;

}
