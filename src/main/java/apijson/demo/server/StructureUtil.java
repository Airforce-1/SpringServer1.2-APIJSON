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

import zuo.biao.apijson.JSON;
import zuo.biao.apijson.Log;
import zuo.biao.apijson.RequestMethod;
import zuo.biao.apijson.server.SQLConfig;
import zuo.biao.apijson.server.SQLCreator;
import zuo.biao.apijson.server.SQLExecutor;
import zuo.biao.apijson.server.Structure;


/**结构校验；用于校验请求字段和响应字段是否合法？
 * @author Lemon
 */
public class StructureUtil {
	private static final String TAG = "Structure";


	static final String responseString = "{\"User\":{\"REMOVE\": \"phone\", \"REPLACE\":{\"sex\":2}, \"ADD\":{\"name\":\"api\"}}, \"PUT\":{\"Comment:to\":{}}}";
	/**测试
	 * @throws Exception
	 */
	public static void test() throws Exception {
		//难道每一次结构校验就生成一个SQL配置和SQL执行器？然后都包括在SQLCreator里面
		SQLCreator creator = new SQLCreator() {
			
			@Override
			public SQLConfig createSQLConfig() {
				return new apijson.demo.server.PartyBuildingSQLConfig();
			}
			
			@Override
			public SQLExecutor createSQLExecutor() {
				return new apijson.demo.server.PartyBuildingSQLExecutor();
			}
		};
		

		JSONObject response;
		try {
			response = JSON.parseObject("{\"User\":{\"id\":1}}");
			Log.d(TAG, "test  parseResponse = " + Structure.parseResponse(RequestMethod.GET, "", JSON.parseObject(responseString), response, creator, null));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	
	
}
