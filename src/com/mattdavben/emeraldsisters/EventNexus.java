package com.mattdavben.emeraldsisters;

import com.google.common.eventbus.EventBus;
import com.google.inject.Singleton;

@Singleton
public class EventNexus {
	private static EventBus bus = new EventBus();

	public static void post(Object event) {
		bus.post(event);
	}
	
	public static void register(Object listener) {
		bus.register(listener);
	}
}
