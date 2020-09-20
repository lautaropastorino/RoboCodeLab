package laboratorio;

import java.util.concurrent.ThreadLocalRandom;

public class OneRemainingStrategy implements RobotStrategy {
	
	private Karl karl;
	
	public OneRemainingStrategy(Karl karl) {
		this.karl = karl;
	}

	@Override
	public void run() {
		int generatedMovement = ThreadLocalRandom.current().nextInt(400, 600);
		int step = (int) generatedMovement / 3;
		for (int i = 1; i <= 3; i++) {
			this.karl.ahead(step);
			this.karl.turnGunRight(60);
		}
		for (int j = 1; j <= 3; j++) {
			this.karl.back(step);
			this.karl.turnGunRight(60);
		}
	}

	@Override
	public void onScannedRobot() {
		this.karl.turnGunTo(this.karl.scannedAngle);
		this.karl.fire(3);
	}

	@Override
	public RobotStrategy onHitByBullet() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void onHitWall() {
		
		
	}

	@Override
	public void onHitRobot() {
		// TODO Auto-generated method stub
	}

}
