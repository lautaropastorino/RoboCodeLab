package laboratorio;

import robocode.JuniorRobot;

public abstract class Strategist {
	protected JuniorRobot robot;
	
	public Strategist(JuniorRobot r) {
		robot = r;
	}
	
	public RobotStrategy getEstrategia() {
		return null;
	}
}
