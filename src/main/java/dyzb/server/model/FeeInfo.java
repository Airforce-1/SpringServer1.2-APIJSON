
package dyzb.server.model;

import static zuo.biao.apijson.RequestRole.ADMIN;
import static zuo.biao.apijson.RequestRole.UNKNOWN;


import zuo.biao.apijson.MethodAccess;
/**党费缴纳信息
 * @author cxs
 */
@MethodAccess(
		POST = {},
		DELETE = {ADMIN}
		)
public class FeeInfo extends BaseModel {

	/**
	 * 系列版本编号
	 */
	private static final long serialVersionUID = 1L;
	private Integer id; //记录id
	private String name; //姓名
	private String date; //日期
	private Integer fee; //费用

	/**默认构造方法，JSON等解析时必须要有
	 */
	public FeeInfo() {
		super();
	}
	public FeeInfo(long id) {
		this();
		setId(id);
	}

	public Integer getid() {
		return id;
	}
	public FeeInfo setid(Integer id) {
		this.id = id;
		return this;
	}
	public String getDate() {
		return date;
	}
	public FeeInfo setDate(String date) {
		this.date = date;
		return this;
	}
	public String getName() {
		return name;
	}
	public FeeInfo setName(String name) {
		this.name = name;
		return this;
	}
	
	public Integer getFee() {
		return fee;
	}
	public FeeInfo setFee(Integer fee) {
		this.fee = fee;
		return this;
	}


}
