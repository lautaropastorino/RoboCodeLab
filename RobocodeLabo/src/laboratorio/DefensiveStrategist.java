package laboratorio;

import java.util.concurrent.ThreadLocalRandom;

import robocode.JuniorRobot;

public class DefensiveStrategist extends Strategist {

	public DefensiveStrategist(JuniorRobot r) {
		super(r);
		// TODO Auto-generated constructor stub
	}
	
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
			 * y bajamos escaneado solo hacia la derecha */
			if (robot.robotX <= 100) {
				int exceso = ThreadLocalRandom.current().nextInt(-20, 21);
				robot.turnTo(0);
				robot.turnGunTo(0);
				robot.ahead((100 + exceso) * move);
				robot.turnGunRight(180);
				robot.back((100 + exceso) * move);
				robot.turnGunLeft(180);
			/* Si no estamos a la izquierda, apuntamos hacia allÃ¡ y vamos. Tambien escaneamos */
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
				// Llegamos hasta el punto Y más alto del mapa.
				robot.ahead(robot.fieldHeight - robot.robotY);
				robot.turnGunRight(90);
				// Ahora recorremos el ancho del mapa.
				robot.turnRight(90);
				robot.ahead(robot.fieldWidth - robot.robotX);
				// Llegamos hasta el punto x más alto del mapa, ahora volvemos.
				robot.turnRight(180);
				robot.ahead(robot.fieldWidth);
				// Vamos hasta el punto y más bajo del mapa.
				robot.turnLeft(90);
				robot.ahead(robot.fieldHeight);
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
		public void onHitWall() {
		}

		@Override
		public void onHitRobot() {
			if (robot.hitRobotBearing < 90) {
				robot.turnBackLeft(100, 90);
			} else {
				robot.turnAheadRight(100, 90);
			}
		}
		
	}
	
	public RobotStrategy getEstrategia() {
		// Si tenemos poca energia, el robot enemigo nos saca mucha diferencia de energia y hay menos de 2 robots
		if ( robot.energy < 10 && ((robot.scannedEnergy - robot.energy) > 50 ) && robot.others <= 2) { return new EscapeStrategy(); }
		else { return new LeftSideWallStrategy(); }
	}
}
