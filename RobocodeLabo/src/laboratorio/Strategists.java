package laboratorio;

import java.util.concurrent.ThreadLocalRandom;

import robocode.JuniorRobot;

public class Strategists {
	
	private JuniorRobot robot;
	private final OffensiveStrategist offensiveStrat = new OffensiveStrategist();
	private final DefensiveStrategist defensiveStrat = new DefensiveStrategist();
	
	public Strategists(JuniorRobot r) {
		robot = r;
	}
	
	private class OffensiveStrategist implements Strategist {

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
		
		@Override
		public RobotStrategy obtenerEstrategia() {
			if (robot.others >= 2) {
				return new ShootAndKiteStrategy();
			} else {
				return new HighMobilityStrategy();
			}
		}
		
	}
	
	private class DefensiveStrategist implements Strategist {
		
		private class LeftSideWallStrategy implements RobotStrategy {
			
			private int move;
			
			{
				move = 1;
			}

			@Override
			public void onScannedRobot() {
				robot.turnGunTo(robot.scannedAngle);
				robot.fire(3);
				// Si esta muy cerca el escaneado
				if (robot.scannedDistance <= 50) {
					// Nos movemos mas distancia
					move = 3;
				}
			}

			@Override
			public void onHitByBullet() {
			}

			@Override
			public void onHitWall() {
			}

			@Override
			public void run() {
				/* Si ya estamos a la izquierda nos ponemos mirando hacia arriba y subimos
				 * y bajamos escaneando solo hacia la derecha */
				if (robot.robotX <= 100) {
					int exceso = ThreadLocalRandom.current().nextInt(-20, 21);
					robot.turnTo(0);
					robot.turnGunTo(0);
					robot.ahead((100 + exceso) * move);
					robot.turnGunRight(180);
					robot.back((100 + exceso) * move);
					robot.turnGunLeft(180);
				/* Si no estamos a la izquierda, apuntamos hacia allá y vamos. Tambien escaneamos */
				} else {
					robot.turnTo(270);
					robot.ahead(100);
					robot.turnGunRight(180);
				}
				// Reiniciamos el modificador de distancia
				move = 1;
			}

			@Override
			public void onHitRobot() {
				// Si chocamos de frente
				if (robot.hitRobotBearing < 90) {
					robot.turnBackLeft(100, 90);
				} else {
					robot.turnAheadRight(100, 90);
				}
			}
		}
		
		private class EscapeStrategy implements RobotStrategy{

			@Override
			public void run() {
				if (robot.robotX <= 100) {
					robot.turnTo(0);
					robot.turnGunTo(0);
					// Llegamos hasta el punto Y m�s alto del mapa.
					robot.ahead(robot.fieldHeight - robot.robotY - 30);
					robot.turnGunRight(90);
					// Ahora recorremos el ancho del mapa.
					robot.turnRight(90);
					robot.ahead(robot.fieldWidth - robot.robotX - 30);
					// Llegamos hasta el punto x m�s alto del mapa, ahora volvemos.
					robot.turnRight(180);
					robot.ahead(robot.fieldWidth - 60);
					// Vamos hasta el punto y m�s bajo del mapa.
					robot.turnLeft(90);
					robot.ahead(robot.fieldHeight - 60);
					robot.turnGunRight(180);
				} else {
					robot.turnTo(270);
					robot.ahead(100);
				}
			}

			@Override
			public void onScannedRobot() {}

			@Override
			public void onHitByBullet() {}

			@Override
			public void onHitWall() {}

			@Override
			public void onHitRobot() {
				if (robot.hitRobotBearing < 90) {
					robot.turnBackLeft(100, 90);
				} else {
					robot.turnAheadRight(100, 90);
				}
			}
			
		}

		@Override
		public RobotStrategy obtenerEstrategia() {
			// Si tenemos poca energia, el robot enemigo nos saca mucha diferencia de energia y hay menos de 2 robots
			if ( robot.energy < 10 && ((robot.scannedEnergy - robot.energy) > 50 ) && robot.others <= 2) { return new EscapeStrategy(); }
			else { return new LeftSideWallStrategy(); }
		}
		
	}
	
	public Strategist getEstrategaOfensiva() { return this.offensiveStrat; }
	
	public Strategist getEstrategaDefensiva() { return this.defensiveStrat; }
	
	
}
