package org.firstinspires.ftc.teamcode.globals;

import java.util.Map;

public class constants {
	public enum Alliance {
		BLUE,
		RED,
		UNKNOWN
	}

	public enum HiveSide {
		BLUE,
		RED,
		UNKNOWN
	}

	public enum Driver {
		J,
		F,
		P,
		UNKNOWN
	}

	public static final Map<String, String> hardwareStrings = Map.ofEntries(
			Map.entry("fL", "frontLeft"),
			Map.entry("fR", "frontRight"),
			Map.entry("bL", "backLeft"),
			Map.entry("bR", "backRight"),
			Map.entry("intakeMotor", "intakeMotor"),
			Map.entry("pollenMotor", "pollenMotor"),
			Map.entry("pollenDoor", "pollenDoor"),
			Map.entry("pollenHood", "pollenHood"),
			Map.entry("nectarMotor", "nectarMotor"),
			Map.entry("nectarDoor", "nectarDoor"),
			Map.entry("nectarHood", "nectarHood"),
			Map.entry("flowerRamp","flowerRamp"),
			Map.entry("pinpoint", "pinpoint")
	);

	public static final double INTAKESPEED = 1000; //RPM
}
