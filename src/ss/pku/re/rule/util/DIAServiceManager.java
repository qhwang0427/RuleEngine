package ss.pku.re.rule.util;

import ss.pku.re.domain.Event;
import ss.pku.re.rule.util.domain.Service;
import ss.pku.re.service.DIAService;
import ss.pku.re.service.DIAServiceImplForCommand;

public class DIAServiceManager {

	private static DIAService diaService = new DIAServiceImplForCommand();

	public static void sendEvent(Event event) {
		diaService.sendEvent(event);
	}
	
	public static void sendService(Service service) {
		diaService.sendService(service);
	}

	public static void sendService(Service service, Event event) {
		diaService.sendService(service, event);
	}
}
