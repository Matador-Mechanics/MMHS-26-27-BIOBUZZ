package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Utility;
import org.firstinspires.ftc.teamcode.globals.constants.Driver;
@Utility
public class DriverConfig extends OpMode {
	public int line = 0;

	@Override
	public void init() {

	}

	@Override
	public void init_loop() {
		switch (line) {
			case 0:
				telemetry.addData("\n", "-> Jose\n    Fernando\n    Programming");
				break;
			case 1:
				telemetry.addData("\n", "    Jose\n-> Fernando\n    Programming");
				break;
			case 2:
				telemetry.addData("\n", "    Jose\n    Fernando\n-> Programming");
				break;
		}
		telemetry.update();
		if (gamepad1.dpadDownWasPressed()) {
			line++;
		}
		if (gamepad1.dpadUpWasPressed()) {
			line--;
		}
	}

	@Override
	public void start() {
		switch (line) {
			case 0:
				blackboard.put("Driver", Driver.J);
				break;
			case 1:
				blackboard.put("Driver", Driver.F);
				break;
			case 2:
				blackboard.put("Driver", Driver.P);
				break;
			default:
				blackboard.put("Driver", Driver.UNKNOWN);
				break;
		}
		telemetry.addData("Driver", blackboard.get("Driver"));
		telemetry.update();
	}

	@Override
	public void loop() {

	}
}
