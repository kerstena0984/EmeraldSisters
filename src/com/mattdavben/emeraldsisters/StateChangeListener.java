package com.mattdavben.emeraldsisters;

import com.google.common.eventbus.Subscribe;
import com.mattdavben.emeraldsisters.map.MapTransitionEvent;

public interface StateChangeListener {

	@Subscribe
	public void listen(StateChangeEvent event);

}
