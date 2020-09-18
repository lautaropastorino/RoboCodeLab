package laboratorio;

import java.util.concurrent.ThreadLocalRandom;

public class LowEnergyStrategy implements RobotStrategy {
	
	private Karl karl;
	private int move;
	
	public LowEnergyStrategy(Karl karl) {
		this.karl = karl;
		move = 1;
	}

	@Override
	public void onScannedRobot() {
//		int generatedAngle = ThreadLocalRandom.current().nextInt(karl.scannedAngle - 2, karl.scannedAngle + 3);
//		this.karl.turnGunTo(generatedAngle);
		this.karl.turnGunTo(this.karl.scannedAngle);
		this.karl.fire(3);
		if (this.karl.scannedDistance <= 200) {
			if (this.karl.scannedDistance <= 100) {
				System.out.println("Cerca");
				move = 2;
			} else {
				move = 3;
			}
		}
	}

	@Override
	public RobotStrategy onHitByBullet() {
		this.karl.turnRight(90 - this.karl.hitByBulletBearing);
		return this;
	}

	@Override
	public void onHitWall() {
	}

	@Override
	public void run() {
		int exceso = ThreadLocalRandom.current().nextInt(-20, 21);
		this.karl.ahead((100 + exceso) * move);
		System.out.println("Move = " + move);
		this.karl.turnGunRight(360);
		this.karl.back((100 + exceso) * move);
		move = 1;
	}

	@Override
	public void onHitRobot() {
		if (this.karl.hitRobotBearing < 90) {
			this.karl.turnBackLeft(100, 90);
		} else {
			this.karl.turnAheadRight(100, 90);
		}
	}

}
