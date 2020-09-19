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
		// Le apunto al más cercano
		this.karl.turnGunTo(this.karl.scannedAngle);
		// Disparamos a toda potencoa
		this.karl.fire(3);
		// Si está muy cerca el escaneado
		if (this.karl.scannedDistance <= 50) {
			// Nos movemos más distancia
			move = 3;
		}
	}

	@Override
	public void onHitByBullet() {
	}

	@Override
	public void onHitWall() {
	}

	@Override
	public void run() {
		// Si ya estamos a la izquierda
		if (this.karl.robotX <= 100) {
			// Generamos un modificador aleatorio de distancia
			int exceso = ThreadLocalRandom.current().nextInt(-20, 21);
			// Enderezamos la trompa hacia arriba
			this.karl.turnTo(0);
			// Ponemos el arma hacia arriba
			this.karl.turnGunTo(0);
			// Avanzamos con el modificador aleatorio y con el modificador de distancia
			this.karl.ahead((100 + exceso) * move);
			// Escaneamos en sentido de las agujas del reloj los 180 grados
			this.karl.turnGunRight(180);
			// Retrocedemos 
			this.karl.back((100 + exceso) * move);
			// Escaneamos en sentido contrario a las agujas del reloj 180 grados
			this.karl.turnGunLeft(180);
		// Si no estamos a la izquierda
		} else {
			// Ponemos la trompa hacia la izquierda
			this.karl.turnTo(270);
			this.karl.ahead(100);
			this.karl.turnGunRight(180);
		}
		// Reiniciamos el modificador de distancia
		move = 1;
	}

	@Override
	public void onHitRobot() {
		// Si chocamos de frente
		if (this.karl.hitRobotBearing < 90) {
			// Vamos hacia atrás
			this.karl.turnBackLeft(100, 90);
		} else {
			// Vamos hacia adelante
			this.karl.turnAheadRight(100, 90);
		}
	}


}