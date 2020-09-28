package laboratorio;
import robocode.*;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/JuniorRobot.html


public class Karl extends JuniorRobot
{
	private RobotStrategy Strategy;
	private Strategist Strategist;
	
	public Karl () {}
	
	@Override	
	public void run() {
		setColors(red, yellow, red, yellow, yellow);
		if (this.energy >= 20) {
			this.Strategist = new DefensiveStrategist(this);
		} else {
			this.Strategist = new OffensiveStrategist(this);
		} 
		Strategy = Strategist.getEstrategia();
		this.Strategy.run();
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	@Override
	public void onScannedRobot() {
		this.Strategy.onScannedRobot();
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	@Override
	public void onHitByBullet() {
		this.Strategy.onHitByBullet();
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	@Override
	public void onHitWall() {
		this.Strategy.onHitWall();
	}	
	
	@Override
	public void onHitRobot() {
		this.Strategy.onHitRobot();
	}
}