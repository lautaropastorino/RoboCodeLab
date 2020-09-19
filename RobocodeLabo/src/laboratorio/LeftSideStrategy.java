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
		if (this.karl.scannedDistance <= 50) {
			move = 3;
		}
	}

	@Override
	public void onHitByBullet() {
		if (this.karl.robotX > 100) {
			this.karl.turnRight(90 - this.karl.hitByBulletBearing);
		}
	}

	@Override
	public void onHitWall() {
	}

	@Override
	public void run() {
		int exceso = ThreadLocalRandom.current().nextInt(-20, 21);
		if (this.karl.robotX <= 100) {
			this.karl.turnTo(0);
			this.karl.turnGunTo(0);
			this.karl.ahead((100 + exceso) * move);
			this.karl.turnGunRight(180);
			this.karl.back((100 + exceso) * move);
			this.karl.turnGunLeft(180);
		} else {
			this.karl.turnTo(270);
			this.karl.ahead(100);
			this.karl.turnGunRight(180);
		}
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