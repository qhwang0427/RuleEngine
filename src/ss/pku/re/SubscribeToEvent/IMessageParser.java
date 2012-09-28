package ss.pku.re.SubscribeToEvent;

import ss.pku.re.domain.Event;

public interface IMessageParser {
	public Event MessageParserStringToEvent(String message);
}
