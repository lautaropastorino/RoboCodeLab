package laboratorio;

public interface RobotStrategy {
	
	public void run();
	
	public void onScannedRobot();
	
	public RobotStrategy onHitByBullet();
	
	public void onHitWall();
	
	public void onHitRobot();

}
