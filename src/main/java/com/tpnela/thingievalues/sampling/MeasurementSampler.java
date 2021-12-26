package com.tpnela.thingievalues.sampling;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.google.gson.Gson;
import com.pi4j.common.Metadata;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;

public class MeasurementSampler {

	private I2C device;
	Samplingthread s;

	public MeasurementSampler(I2C device) {
		this.device = device;

	}

	public void start(int samplecount, int sampledelay, int buffersize) {

		this.s = new Samplingthread(samplecount, sampledelay, buffersize);
		new Thread(s).start();

	}

	public I2C getDevice() {
		return device;
	}

	public ArrayList<DataNode> getBuffer() {
		return this.s.getBuffer();
	}

	class Samplingthread extends Thread implements Runnable {
		private ArrayList<DataNode> buffer;

		int samplecount;
		int sampledelay;
		int buffersize;

		private Samplingthread(int samplecount, int sampledelay, int buffersize) {
			this.samplecount = samplecount;
			this.sampledelay = sampledelay;
			this.buffersize = buffersize;
		}

		@Override
		public void run() {
			System.out.println("Initialising buffer...");
			buffer = new ArrayList<DataNode>();
			System.out.println("Initialised buffer.");

			for (int samplerun = 0; samplerun < this.samplecount; samplerun++) {
				System.out.println("Entering samplerun [" + samplerun + "]...");

				this.addNode(this.getSample());

				if (samplerun < samplecount - 1) {
					try {
						System.out.println("Entering sleep of  [" + this.sampledelay + "] mSecs ...");
						Samplingthread.sleep(this.sampledelay);
						System.out.println("Woke up.");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}

		private synchronized ArrayList<DataNode> getBuffer() {
			if (buffer == null) {
				return new ArrayList<DataNode>();
			} else {
				return buffer;
			}
		}

		private synchronized void addNode(DataNode sample) {
			System.out.println("Adding DataNode to buffer...");
			this.buffer.add(sample);
			System.out.println("Added DataNode to buffer.");

			if (this.buffer.size() > this.buffersize) {
				this.buffer.remove(0);
				System.out.println("Removed oldest node from buffer.");
			}

		}

		private DataNode getSample() {
			// TODO: Add actual logic
			return new DataNode();
		}

	};

}
