package com.majikarpets.td.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.badlogic.gdx.utils.IntMap;
import com.majikarpets.td.GameRuntimeException;
import com.majikarpets.td.StreamSerializable;


/**
 * Contains all the information about a given level
 * @author Aaron Cake
 */
public class Level implements StreamSerializable {
	
	/** A map of ground types to speed modifiers */
	private static IntMap<Float> groundTypeSpeedModifiers = new IntMap<Float>();
	
	/**
	 * Sets the speed modifier for a given ground type.
	 * @param groundType the ground type id
	 * @param speedModifier the speed modifier. Should not be <= 0
	 */
	public void setGroundTypeSpeedModifier(int groundType, float speedModifier) {
		if (speedModifier <= 0)
			throw new GameRuntimeException("Speed modifier must be greater than 0");
		groundTypeSpeedModifiers.put(groundType, speedModifier);
	}
	
	/** The width of the level */
	private final int width;
	
	/** The height of the level */
	private final int height;
	
	/** The width in cells of the border area (non viewable, non-playable) area of the game */
	private int border;
	
	/** The cells of the grid */
	private Cell[][] cells;
	
	/** The total number of teams in the level */
	private int totalTeams;
	
	/** The base locations for each team */
	private List<GridCoordinate> teamBaseLocations;
	
	/**
	 * A list of grid coordinates for each team
	 * containing the spawnable locations for each team.
	 */
	private List<List<GridCoordinate>> teamSpawnableAreas;
	
	/**
	 * Create a new level with a given number of teams
	 * @param width the width of the map in cells
	 * @param height the height of the map in cells
	 * @param teams the number of teams
	 */
	public Level(int width, int height, int teams) {
		this.width = width;
		this.height = height;
		cells = new Cell[width][height];
		border = 0;
		teamBaseLocations = new ArrayList<GridCoordinate>();
		teamSpawnableAreas = new ArrayList<List<GridCoordinate>>();
		for (int i=0; i<teams; i++) {
			
		}
	}
	
	/**
	 * Adds a team to the Level with no base and no spawn locations
	 * @return the team index
	 */
	public int addTeam() {
		return this.addTeam(null, null);
	}
	
	/**
	 * Adds a team to the Level with the given base location and spawn locations
	 * @param baseLocation the location of the base, or null if they have no base
	 * @param spawnLocations the spawn locations for this team
	 * @return the team index
	 */
	public int addTeam(GridCoordinate baseLocation, List<GridCoordinate> spawnLocations) {
		teamBaseLocations.add(baseLocation);
		teamSpawnableAreas.add(spawnLocations == null ? new ArrayList<GridCoordinate>() : spawnLocations);
		return totalTeams++;
	}
	
	/**
	 * Sets the team base location.
	 * If baseLocation is null, it effectively removes the base from the team
	 * @param team the team index
	 * @param baseLocation the location of the base, or null
	 */
	public void setTeamBaseLocation(int team, GridCoordinate baseLocation) {
		if (team >= totalTeams)
			throw new GameRuntimeException("Team number " + team + " not defined for level.");
		teamBaseLocations.set(team, baseLocation);
	}
	
	/**
	 * Gets the location of the team base, or null
	 * @param team the team index
	 * @return the base location, or null
	 */
	public GridCoordinate getTeamBaseLocation(int team) {
		return teamBaseLocations.get(team);
	}
	
	/**
	 * Marks an area as either spawnable, or not spawnable for a given team.
	 * @param team the team index
	 * @param isSpawnable true to mark the area as spawnable, false to mark it as not spawnable
	 * @param top the minimum y coordinate
	 * @param left the minimum x coordinate
	 * @param bottom the maximum y coordinate
	 * @param right the maximum x coordinate
	 */
	public void setSpawnableAreaForTeam(int team, boolean isSpawnable, int top, int left, int bottom, int right) {
		if (team >= totalTeams)
			throw new GameRuntimeException("Team number " + team + " not defined for level.");
		List<GridCoordinate> spawnLocations = teamSpawnableAreas.get(team);
		for (int x = left; x < right; x++) {
			for (int y = top; y < bottom; y++) {
				GridCoordinate gc = new GridCoordinate(x, y);
				if (isSpawnable) {
					if (!spawnLocations.contains(gc))
						spawnLocations.add(gc);
				} else {
					spawnLocations.remove(gc);
				}
			}
		}
	}
	
	
	/**
	 * Get the cell at a specific integer location.
	 * @param x the x coordinate of the cell
	 * @param y the y coordinate of the cell
	 * @return the cell
	 */
	public Cell getCellAt(int x, int y) {
		return cells[x][y];
	}
	
	/**
	 * Sets the ground type for a given rectangular area
	 * @param top the minimum y coordinate
	 * @param left the minimum x coordinate
	 * @param bottom the maximum y coordinate
	 * @param right the maximum x coordinate
	 */
	public void setGroundTypeForArea(int groundType, int top, int left, int bottom, int right) {
		for (int x = left; x < right; x++) {
			for (int y = top; y < bottom; y++) {
				cells[x][y].setGroundType(groundType);
			}
		}
	}
	
	/**
	 * Sets the buildable property for a given rectangular area
	 * @param top the minimum y coordinate
	 * @param left the minimum x coordinate
	 * @param bottom the maximum y coordinate
	 * @param right the maximum x coordinate
	 */
    public void setIsBuildableForArea(boolean isBuildable, int top, int left, int bottom, int right) {
    	for (int x = left; x < right; x++) {
			for (int y = top; y < bottom; y++) {
				cells[x][y].buildable = isBuildable;
			}
		}
	}
    
    /**
	 * Sets the walkable property for a given rectangular area
	 * @param top the minimum y coordinate
	 * @param left the minimum x coordinate
	 * @param bottom the maximum y coordinate
	 * @param right the maximum x coordinate
	 */
    public void setIsWalkableForArea(boolean isWalkable, int top, int left, int bottom, int right) {
    	for (int x = left; x < right; x++) {
			for (int y = top; y < bottom; y++) {
				cells[x][y].walkable = isWalkable;
			}
		}
	}
	
	/**
	 * Gets an array of grid coordinates that correspond to the 
	 * spawnable areas of the map for the given team.
	 * @param id the team id
	 * @return the list of spawnabale areas
	 */
	public List<GridCoordinate> getSpawnableAreaForTeam(int id) {
		return teamSpawnableAreas.get(id);
	}

	@Override
	public void serializeBinary(DataOutputStream out) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deserializeBinary(DataInputStream in) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * An integer x and y coordinate corresponding with a specific cell of the grid.
	 * @author Aaron Cake
	 */
	public static class GridCoordinate {
		
		/** The x coordinate */
		public final int x;
		/** The y coordinate */
		public final int y;
		
		/**
		 * Create a new grid coordinate with the given x and y
		 * @param x The x coordinate
		 * @param y The y coordinate
		 */
		public GridCoordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null || !(obj instanceof GridCoordinate)) return false;
			GridCoordinate o = (GridCoordinate)obj;
			return (this.x == o.x && this.y == o.y);
		}
		
	}
	
	/**
	 * A cell represents a specific integer location on the grid.
	 * @author Aaron Cake
	 */
	public static class Cell {
		
		/** Whether or not this cell is buildable */
		public boolean buildable;
		
		/** Whether or not this cell is walkable */
		public boolean walkable;
		
		/** The speed modifier for units moving through this cell */
		public float speedModifier;
		
		/** The type of ground at this cell. Used for rendering, etc.*/
		public int groundType;
		
		/**
		 * Create a new Cell
		 * @param buildable whether or not the cell is buildable
		 * @param walkable whether or not the cell is walkable
		 * @param groundType the ground type at this cell
		 */
		public Cell(boolean buildable, boolean walkable, int groundType) {
			this.buildable = buildable;
			this.walkable = walkable;
			this.groundType = groundType;
			Float speedMod = groundTypeSpeedModifiers.get(groundType);
			speedModifier = speedMod == null ? 1 : speedMod;
		}
		
		/**
		 * Creates a new default cell that is neither walkable 
		 * nor buildable, with a ground type of 0
		 */
		public Cell() {
			this(false, false, 0);
		}
		
		/**
		 * Sets the ground type and corresponding speed modifier
		 * @param groundType the ground type id
		 */
		public void setGroundType(int groundType) {
			this.groundType = groundType;
			Float speedMod = groundTypeSpeedModifiers.get(groundType);
			speedModifier = speedMod == null ? 1 : speedMod;
		}
		
	}
	

}
