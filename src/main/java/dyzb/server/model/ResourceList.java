
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
public class ResourceList extends BaseModel {

	/**
	 * 系列版本编号
	 */
	private static final long serialVersionUID = 1L;
	private Integer id; //记录id
	private String title; //姓名
	private String content; //日期
	private String type; //类型

	/**默认构造方法，JSON等解析时必须要有
	 */
	public ResourceList() {
		super();
	}
	public ResourceList(long id) {
		this();
		setId(id);
	}

	public Integer getid() {
		return id;
	}
	public ResourceList setid(Integer id) {
		this.id = id;
		return this;
	}
	public String getcontent() {
		return content;
	}
	public ResourceList setcontent(String content) {
		this.content = content;
		return this;
	}
	public String gettitle() {
		return title;
	}
	public ResourceList settitle(String title) {
		this.title = title;
		return this;
	}
	
	public String getType() {
		return type;
	}
	public ResourceList setType(String type) {
		this.type = type;
		return this;
	}


}
