package laboratorio;
import java.util.concurrent.ThreadLocalRandom;

public class HighEnergyStrategy implements RobotStrategy {
	
	private Karl karl;
	
	public HighEnergyStrategy(Karl karl) {
		this.karl = karl;
	}

	@Override
	public void onScannedRobot() {
		int generatedAngle = ThreadLocalRandom.current().nextInt(karl.scannedAngle - 2, karl.scannedAngle + 3);
		karl.turnGunTo(generatedAngle);
		karl.fire(3);	
		if (this.karl.scannedDistance <= 25) {
			this.karl.turnAheadRight(100, this.karl.scannedBearing + 180);
		}
	}

	@Override
	public RobotStrategy onHitByBullet() {
		this.karl.turnAheadLeft(80, 90);
		if (this.karl.energy <= 50) {
//			return new LowEnergyStrategy(this.karl);
			return this;
		}
		else {
			return this;
		}
	}

	@Override
	public void onHitWall() {
		this.karl.turnRight(karl.hitWallAngle + 10);
	}

	@Override
	public void run() {
		this.karl.ahead(100);
		this.karl.turnGunRight(90);
		this.karl.back(50);
		this.karl.turnGunRight(90);
	}

	@Override
	public void onHitRobot() {
		// TODO Auto-generated method stub
		
	}

}
