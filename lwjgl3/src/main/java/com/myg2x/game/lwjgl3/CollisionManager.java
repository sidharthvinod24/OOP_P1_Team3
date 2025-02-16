package com.myg2x.game.lwjgl3;

import java.util.ArrayList;


public class CollisionManager {

	private ArrayList<Entity> collisionList;
	
	CollisionManager()
	{
		collisionList = new ArrayList<Entity>();
	}
	
	public void addEntity(Entity e)
	{
		collisionList.add(e);
	}
	
	public void removeEntity(Entity e)
	{
		collisionList.remove(e);
	}
	
	public void collisionSound(AudioManager audioManager) {
		for (Entity entity : collisionList) {
			if (entity instanceof Player) {
				if (((Player) entity).checkBlocked()) {
					audioManager.playSoundEffect("collision", 0.3f);
				}
			}
		}
	}
	
//	public ArrayList<Entity> collisionsList(Entity exclusion) {
//
//		ArrayList<Entity> need_check = new ArrayList<Entity>();
//		for(Entity e : collisionList) {
//			if (e == exclusion) {
//				continue;
//			}
//			else {
//				need_check.add(e);
//			}
//			
//		}
//		return need_check;
//	}
	
	
	

	
	
}
