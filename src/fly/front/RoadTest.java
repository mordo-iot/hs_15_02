package fly.front;

import net.sf.json.JSONObject;
import roadinfo.api.RoadGenerator;

public class RoadTest {

	public static void main(String[] args) {
		RoadGenerator.init();
		RoadGenerator.save(1, 
				"[{\"posId\":1,\"x\":101,\"y\":102},{\"posId\":2,\"x\":201,\"y\":202},{\"posId\":3,\"x\":301,\"y\":302}]", 
				"[{\"x1\":101,\"y1\":102,\"x2\":151,\"y2\":152}, {\"x1\":201,\"y1\":202,\"x2\":151,\"y2\":152}]");
		RoadGenerator.save(2, 
				"[]", 
				"[{\"x1\":101,\"y1\":102,\"x2\":151,\"y2\":152}, {\"x1\":201,\"y1\":202,\"x2\":151,\"y2\":152}]");
		System.out.println("path2target:" + RoadGenerator.find(1, 1, 2));
		System.out.println("currentConfig:" + RoadGenerator.read(2));
		
		
		JSONObject jso = JSONObject.fromObject(RoadGenerator.read(1));
		if (jso != null) {
			if (jso.containsKey("locate")) {
				System.out.println("locate:" + jso.getString("locate"));
			}
			if (jso.containsKey("road")) {
				System.out.println("road:" + jso.getString("road"));
			}
		}
		RoadGenerator.save(1, "", "");
		jso = JSONObject.fromObject(RoadGenerator.read(1));
		if (jso != null) {
			if (jso.containsKey("locate")) {
				System.out.println("locate:" + jso.getString("locate"));
			}
			if (jso.containsKey("road")) {
				System.out.println("road:" + jso.getString("road"));
			}
		}
	}
	
}
