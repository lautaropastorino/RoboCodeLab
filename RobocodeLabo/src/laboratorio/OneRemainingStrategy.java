package laboratorio;

import java.util.concurrent.ThreadLocalRandom;

public class OneRemainingStrategy implements RobotStrategy {
	
	private Karl karl;
	
	public OneRemainingStrategy(Karl karl) {
		this.karl = karl;
	}

	@Override
	public void run() {
		int generatedMovement = ThreadLocalRandom.current().nextInt(300, 600);
		this.karl.ahead(generatedMovement);
		this.karl.turnGunRight(360);
		generatedMovement = ThreadLocalRandom.current().nextInt(300, 600);
		this.karl.back(generatedMovement);
		this.karl.turnGunLeft(360);
	}

	@Override
	public void onScannedRobot() {
		this.karl.turnGunTo(this.karl.scannedAngle);
		this.karl.fire(2);
	}

	@Override
	public RobotStrategy onHitByBullet() {
		// TODO Auto-generated method stub
		return this;
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
