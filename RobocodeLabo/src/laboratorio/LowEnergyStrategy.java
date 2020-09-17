package laboratorio;

import java.util.concurrent.ThreadLocalRandom;

public class LowEnergyStrategy implements RobotStrategy {
	
	private Karl karl;
	
	public LowEnergyStrategy(Karl karl) {
		this.karl = karl;
	}

	@Override
	public void onScannedRobot() {
		int generatedAngle = ThreadLocalRandom.current().nextInt(karl.scannedAngle - 2, karl.scannedAngle + 3);
		karl.turnGunTo(generatedAngle);
		karl.fire(1);	
		
	}

	@Override
	public RobotStrategy onHitByBullet() {
		return this;
		
	}

	@Override
	public void onHitWall() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHitRobot() {
		// TODO Auto-generated method stub
		
	}

}
