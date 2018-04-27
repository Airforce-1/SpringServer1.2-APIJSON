/*Copyright ©2016 TommyLemon(https://github.com/TommyLemon/APIJSON)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package apijson.demo.server;

import com.alibaba.fastjson.JSONObject;

import dyzb.server.model.Privacy;
import dyzb.server.model.ResourceList;
import dyzb.server.model.User;
import dyzb.server.model.FeeInfo;
import zuo.biao.apijson.RequestMethod;
import zuo.biao.apijson.StringUtil;
import zuo.biao.apijson.server.AbstractSQLConfig;
import zuo.biao.apijson.server.SQLConfig;


/**党建系统SQL配置
 * @author Lemon cxs
 */
public class PartyBuildingSQLConfig extends AbstractSQLConfig {

	//表名映射，隐藏真实表名，对安全要求很高的表可以这么做
	static {
		//用户表
		TABLE_KEY_MAP.put(User.class.getSimpleName(), "user");
		TABLE_KEY_MAP.put(FeeInfo.class.getSimpleName(), "feeinfo");
		TABLE_KEY_MAP.put(ResourceList.class.getSimpleName(), "resourcelist");
	}

	@Override
	public String getDBUri() {
		return "jdbc:mysql://localhost:3306"; //TODO 改成你自己的
	}
	@Override
	public String getDBAccount() {
		return "root"; //TODO 改成你自己的
	}
	@Override
	public String getDBPassword() {
		return "887196800"; //TODO 改成你自己的
	}
	@Override
	public String getSchema() {
		String s = super.getSchema();
		return StringUtil.isEmpty(s, true) ? "mydb" : s; //TODO 改成你自己的
	}
	

	public PartyBuildingSQLConfig() {
		this(RequestMethod.GET);
	}
	public PartyBuildingSQLConfig(RequestMethod method) {
		super(method);
	}
	public PartyBuildingSQLConfig(RequestMethod method, String table) {
		super(method, table);
	}
	public PartyBuildingSQLConfig(RequestMethod method, int count, int page) {
		super(method, count, page);
	}


	/**获取SQL配置
	 * @param table
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public static SQLConfig newSQLConfig(RequestMethod method, String table, JSONObject request) throws Exception {
		return newSQLConfig(method, table, request, new Callback() {

			@Override
			public PartyBuildingSQLConfig getSQLConfig(RequestMethod method, String table) {
				return new PartyBuildingSQLConfig(method, table);
			}
		});
	}


}
