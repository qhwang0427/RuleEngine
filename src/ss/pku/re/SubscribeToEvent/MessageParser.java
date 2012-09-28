package ss.pku.re.SubscribeToEvent;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

import ss.pku.re.domain.Event;

import cn.edu.pku.ss.bean.DIAMessage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MessageParser implements IMessageParser {
	// 根据收到的json格式字符串解析成事件
	public Event MessageParserStringToEvent(String message) {

		System.out.println(message);
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> ms = gson.fromJson(message, type);
		type = new TypeToken<String>() {
		}.getType();
		System.out.println(ms.get("DeviceID"));
		StringBuilder sb = new StringBuilder();
		sb.append("received event:\n");
		for (String k : ms.keySet()) {
			sb.append("\t").append(k).append(" = ")
					.append(ms.get(k).toString()).append("\n");
		}
		Date date = new Date();
		// 数值封装到事件
		Event mevent = new Event();

		double[] values = null;
		String Value = ms.get(ms.get("Topic"));
		if (Value.contains("|")) {

			String[] svalues = Value.split("\\|");
			values = new double[svalues.length];
			for (int i = 0; i < svalues.length; i++) {
				System.out.println(svalues[i]);
				values[i] = Double.parseDouble(svalues[i]);
			}
		} else {
			values = new double[] { Double.parseDouble(Value) };
		}

		mevent.setValues(values);
		mevent.setDimension(values.length);
		mevent.setEventId(ms.get("DeviceID"));// 传感器号
		mevent.setReceivedTime(ms.get("OccurTime"));
		System.out.println("Notify the client at " + date + " "
				+ date.getTime());
		return mevent;

	}

	// 根据收到的map格式数据解析成事件
	public Event MessageParserMapToEvent(Map<String, String> ms,String topic) {

		//System.out.println(ms.get(DIAMessage.DEVICE_ID));

		Date date = new Date();
		// 数值封装到事件
		Event mevent = new Event();

		double[] values = null;
		String value = ms.get(topic);
		//System.out.println("value: "+value);
		if (value.contains("|")) {

			String[] svalues = value.split("\\|");
			values = new double[svalues.length];
			for (int i = 0; i < svalues.length; i++) {
				System.out.println(svalues[i]);
				values[i] = Double.parseDouble(svalues[i]);
			}
		} else {
			values = new double[] { Double.parseDouble(value) };
		}

		mevent.setValues(values);
		mevent.setDimension(values.length);
		mevent.setEventId(ms.get(DIAMessage.SENSOR_ID));// 传感器号
		mevent.setReceivedTime(ms.get(DIAMessage.OCCUR_TIME));
		//System.out.println("Notify the client at " + date + " " + date.getTime());
		return mevent;

	}

}
