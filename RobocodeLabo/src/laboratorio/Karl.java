package laboratorio;
import robocode.*;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/JuniorRobot.html


public class Karl extends JuniorRobot
{
	private RobotStrategy Strategy;
	
	public Karl () {
		Strategy = new MultipleEnemiesStrategy(this);//HighEnergyStrategy(this);
	}
	
	@Override	
	public void run() {
		setColors(red, yellow, red, yellow, yellow);
		this.Strategy.run();
		if (this.others == 2) {
			this.Strategy = new LeftSideStrategy(this);
		} else if (this.others == 1) {
			this.Strategy = new OneRemainingStrategy(this);
		}
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
		this.Strategy = this.Strategy.onHitByBullet();
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