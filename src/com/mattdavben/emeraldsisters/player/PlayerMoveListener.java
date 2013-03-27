package com.mattdavben.emeraldsisters.player;

import com.google.common.eventbus.Subscribe;

public interface PlayerMoveListener {

	@Subscribe
	public void listen(PlayerMoveEvent event);
	
}
