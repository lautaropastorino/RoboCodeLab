package laboratorio;

import java.util.concurrent.ThreadLocalRandom;

public class LeftSideStrategy implements RobotStrategy{
	private Karl karl;
	private int move;
	
	public LeftSideStrategy(Karl karl) {
		this.karl = karl;
		move = 1;
	}

	@Override
	public void onScannedRobot() {
		this.karl.turnGunTo(this.karl.scannedAngle);
		this.karl.fire(3);
		// Si está muy cerca el escaneado
		if (this.karl.scannedDistance <= 50) {
			// Nos movemos más distancia
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
		if (this.karl.robotX <= 100) {
			int exceso = ThreadLocalRandom.current().nextInt(-20, 21);
			this.karl.turnTo(0);
			this.karl.turnGunTo(0);
			this.karl.ahead((100 + exceso) * move);
			this.karl.turnGunRight(180);
			this.karl.back((100 + exceso) * move);
			this.karl.turnGunLeft(180);
		/* Si no estamos a la izquierda, apuntamos hacia allá y vamos. Tambien escaneamos */
		} else {
			this.karl.turnTo(270);
			this.karl.ahead(100);
			this.karl.turnGunRight(180);
		}
		// Reiniciamos el modificador de distancia
		move = 1;
	}

	@Override
	public void onHitRobot() {
		// Si chocamos de frente
		if (this.karl.hitRobotBearing < 90) {
			this.karl.turnBackLeft(100, 90);
		} else {
			this.karl.turnAheadRight(100, 90);
		}
	}


}