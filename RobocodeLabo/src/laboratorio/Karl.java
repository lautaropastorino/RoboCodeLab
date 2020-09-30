package laboratorio;
import robocode.*;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/JuniorRobot.html


public class Karl extends JuniorRobot
{
	private RobotStrategy Strategy;
	private Strategists EstrategaPrincipal;
	private Strategist EstrategaConcreto;
	
	public Karl () {
		EstrategaPrincipal = new Strategists(this); 
	}
	
	@Override	
	public void run() {
		setColors(red, yellow, red, yellow, yellow);
		if (this.energy <= 30) { EstrategaConcreto = this.EstrategaPrincipal.getEstrategaDefensiva(); } 
		else { EstrategaConcreto = this.EstrategaPrincipal.getEstrategaOfensiva(); } 
		this.Strategy = this.EstrategaConcreto.obtenerEstrategia();
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