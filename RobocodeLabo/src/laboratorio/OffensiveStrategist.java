package laboratorio;

import java.util.concurrent.ThreadLocalRandom;

import robocode.JuniorRobot;

public class OffensiveStrategist extends Strategist {
	
	public OffensiveStrategist(JuniorRobot r) {
		super(r);
		// TODO Auto-generated constructor stub
	}

	private class HighMobilityStrategy implements RobotStrategy {

		@Override
		public void run() {
			int generatedMovement = ThreadLocalRandom.current().nextInt(400, 600);
			int step = (int) generatedMovement / 3;
			for (int i = 1; i <= 3; i++) {
				robot.ahead(step);
				robot.turnGunRight(60);
			}
			for (int j = 1; j <= 3; j++) {
				robot.back(step);
				robot.turnGunRight(60);
			}
		}

		@Override
		public void onScannedRobot() {
			robot.turnGunTo(robot.scannedAngle);
			robot.fire(3);
		}

		@Override
		public void onHitByBullet() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onHitWall() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onHitRobot() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class ShootAndKiteStrategy implements RobotStrategy {
		private int move;
		
		{
			move = 1;
		}
		
		@Override
		public void run() {
			// Generamos aleatoriamente un modificador de distancia
			int exceso = ThreadLocalRandom.current().nextInt(-20, 21);
			robot.ahead((100 + exceso) * move);
			robot.turnGunRight(180);
			robot.back((100 + exceso) * move);
			move = 1;
		}

		@Override
		public void onScannedRobot() {
			robot.turnGunTo(robot.scannedAngle);
			robot.fire(3);
			if (robot.scannedDistance <= 50) {
				move = 3;
			}
		}

		@Override
		public void onHitByBullet() {
			// Nos ponemos perpendiculares al ángulo de donde vino la bala
			robot.turnRight(90 - robot.hitByBulletBearing);
		}

		@Override
		public void onHitWall() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onHitRobot() {
			// Si nos chocamos de frente
			if (robot.hitRobotBearing < 90) {
				robot.turnBackLeft(100, 90);
			} else {
				robot.turnAheadRight(100, 90);
			}
		}
		
	}
	
	public RobotStrategy getEstrategia() {
		if (robot.energy > 75) {
			return new ShootAndKiteStrategy();
		} else {
			return new HighMobilityStrategy();
		}
	}
}
