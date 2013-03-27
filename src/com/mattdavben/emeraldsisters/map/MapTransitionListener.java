package com.mattdavben.emeraldsisters.map;

import com.google.common.eventbus.Subscribe;

public interface MapTransitionListener {

	@Subscribe
	public void listen(MapTransitionEvent event);

}
