package laboratorio;

import java.util.concurrent.ThreadLocalRandom;

public class MultipleEnemiesStrategy implements RobotStrategy {
	
	private Karl karl;
	private int move;
	
	public MultipleEnemiesStrategy(Karl karl) {
		this.karl = karl;
		move = 1;
	}

	@Override
	public void onScannedRobot() {
		this.karl.turnGunTo(this.karl.scannedAngle);
		this.karl.fire(3);
		if (this.karl.scannedDistance <= 50) {
			move = 3;
		}
	}

	@Override
	public void onHitByBullet() {
		// Nos ponemos perpendiculares al ángulo de donde vino la bala
		if (this.karl.others <= 4) {
			this.karl.turnRight(90 - this.karl.hitByBulletBearing);
		}
	}

	@Override
	public void onHitWall() {
	}

	@Override
	public void run() {
		// Generamos aleatoriamente un modificador de distancia
		int exceso = ThreadLocalRandom.current().nextInt(-20, 21);
		this.karl.ahead((100 + exceso) * move);
		this.karl.turnGunRight(180);
		this.karl.back((100 + exceso) * move);
		move = 1;
	}

	@Override
	public void onHitRobot() {
		// Si nos chocamos de frente
		if (this.karl.hitRobotBearing < 90) {
			this.karl.turnBackLeft(100, 90);
		} else {
			this.karl.turnAheadRight(100, 90);
		}
	}


}
