package com.tpnela.thingievalues.executable;



import com.google.gson.Gson;
import com.pi4j.io.i2c.I2C;
import com.tpnela.thingievalues.sampling.DeviceFactory;
import com.tpnela.thingievalues.sampling.MeasurementSampler;

public class App {

	public static void main(String[] args) {

		Gson gson = new Gson();
		I2C bme280device = null;
		try {

			bme280device = DeviceFactory.getI2Cdevice("BME280", 1, 76);
		} catch (Exception e) {
			System.err.println(e.getCause().getMessage());
			System.exit(1);
		}

		MeasurementSampler driver = new MeasurementSampler(bme280device);

		driver.start(100, 450, 4);

		int displayloopcount = 0;
		while (true) {

			displayloopcount++;
			try {
				System.out.println("#" + displayloopcount + " " + driver.getBuffer().size());
				Thread.sleep(400);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

}
