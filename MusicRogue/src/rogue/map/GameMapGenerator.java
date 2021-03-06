package rogue.map;

import java.util.Random;

import rogue.entity.Entity;
import rogue.entity.EnvironmentEntity;

public class GameMapGenerator {
	private static Random randomInt = new Random();

	private static int getRandomDimension(int min, int max) {
		int value = Math.abs(randomInt.nextInt(max - 1));
		while (value < min) {
			value = Math.abs(randomInt.nextInt(max - 1));
		}
		return value;
	}

	private static void buildRoom(int wmin, int wmax, int lmin, int lmax,
			EnvironmentEntity[][] map, int startX, int startY) {
		// Width is y axis, length is x axis.
		int width = getRandomDimension(wmin, wmax);
		int length = getRandomDimension(lmin, lmax);
		for (int i = startY; i < startY + width && i < map.length; i++) {
			for (int j = startX; j < startX + length && j < map.length; j++) {
				Position position = new Position(j, i);
				EnvironmentEntity ground = new EnvironmentEntity(
						EnvironmentEntity.EnvironmentType.FLOOR, position);
				map[i][j] = ground;
			}
		}
	}

	private static void buildMultipleRooms(EnvironmentEntity[][] map) {
		int numberOfRooms = 350;

		for (int i = 0; i < numberOfRooms; i++) {
			int startX = randomInt.nextInt(63);
			int startY = randomInt.nextInt(63);
			buildRoom(3, 7, 3, 7, map, startX, startY);

		}

	}

	private static void buildCorridors(EnvironmentEntity[][] map)
	{
		for (int i = 0; i < map.length; i+=3) {
			for(int j=0; j<map.length; j++)
			{
				Position position = new Position(j, i);
				EnvironmentEntity ground = new EnvironmentEntity(
					EnvironmentEntity.EnvironmentType.FLOOR, position);
				map[i][j] = ground;
			}
		}
		for (int i = 0; i < map.length; i++) {
			for(int j=0; j<map.length; j+=3)
			{
				Position position = new Position(j, i);
				EnvironmentEntity ground = new EnvironmentEntity(
					EnvironmentEntity.EnvironmentType.FLOOR, position);
				map[i][j] = ground;
			}
		}
	}
	
	private static void buildWalls(EnvironmentEntity[][] map) {
		int x = map.length;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < x; j++) {
				if (map[i][j] == null) {
					Position position = new Position(j, i);
					EnvironmentEntity ground = new EnvironmentEntity(
							EnvironmentEntity.EnvironmentType.WALL, position);
					map[i][j] = ground;
				}
			}
		}
		for (int i = 0; i < x; i++) {
			Position position = new Position(0, i);
			EnvironmentEntity ground = new EnvironmentEntity(
					EnvironmentEntity.EnvironmentType.WALL, position);
			map[i][0] = ground;
		}
		for (int i = 0; i < x; i++) {
			Position position = new Position(x-1, i);
			EnvironmentEntity ground = new EnvironmentEntity(
					EnvironmentEntity.EnvironmentType.WALL, position);
			map[i][x - 1] = ground;
		}
		for (int i = 0; i < x; i++) {
			Position position = new Position(i, 0);
			EnvironmentEntity ground = new EnvironmentEntity(
					EnvironmentEntity.EnvironmentType.WALL, position);
			map[0][i] = ground;
		}
		for (int i = 0; i < x; i++) {
			Position position = new Position(i, x-1);
			EnvironmentEntity ground = new EnvironmentEntity(
					EnvironmentEntity.EnvironmentType.WALL, position);
			map[x - 1][i] = ground;
		}
	}

	public static EnvironmentEntity[][] generateBottomLayer(int x) {
		EnvironmentEntity[][] mapBottomLayer = new EnvironmentEntity[x][x];
		buildMultipleRooms(mapBottomLayer);
		buildCorridors(mapBottomLayer);
		buildWalls(mapBottomLayer);

		return mapBottomLayer;
	}

	public static Entity[][] generateTopLayer(int x) {
		Entity[][] mapTopLayer = new Entity[x][x];
		return mapTopLayer;
	}

	public static void main(String[] args) {
		// This is just to test that I didn't fucking erupt everything.
		EnvironmentEntity[][] mapTest;
		mapTest = generateBottomLayer(32);
		for (int i = 0; i < mapTest.length; i++) {
			for (int j = 0; j < mapTest.length; j++) {
				if (mapTest[i][j] != null) {
					System.out.print(mapTest[i][j].getCharRep());
				} else {
					System.out.print("0");
				}
			}
			System.out.println();
		}

	}

}
