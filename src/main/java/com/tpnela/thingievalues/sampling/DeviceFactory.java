package com.tpnela.thingievalues.sampling;



import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;

public class DeviceFactory {

	/**
	 * 
	 * @param deviceId
	 * @param bus
	 * @param address
	 * @return
	 * @throws Exception All occuring exceptions bubbling up are
	 *                   recast into a generalised exception, containing the
	 *                   root cause exception.
	 *                   Sorry, not a friend of unchecked exceptions :-)
	 */
	public static I2C getI2Cdevice(String deviceId, int bus, int address) throws Exception {

		Context pi4j = Pi4J.newAutoContext();
		I2CProvider i2CProvider = pi4j.provider("linuxfs-i2c");
		I2CConfig i2cConfig = I2C.newConfigBuilder(pi4j).id(deviceId).bus(bus)
				.device(Integer.parseUnsignedInt("" + address, 16)).build();

		I2C retval;

		try {
			retval = i2CProvider.create(i2cConfig);
		} catch (Exception e) {
			throw new Exception(e.getCause());
		}

		return retval;
	}

}
